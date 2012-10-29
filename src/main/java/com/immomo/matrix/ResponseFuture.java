package com.immomo.matrix;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;

import com.immomo.matrix.exception.TimeoutException;
import com.immomo.matrix.util.NamedThreadFactory;

/**
 * Responsible for receiving the {@link Response}s.
 * <p>
 * A single thread will start to scan the response futures for timeout
 * exception.
 * 
 * @author mixueqiang
 * @since 2012-10-29
 * 
 */
public class ResponseFuture {
    private static final Log LOG = LogFactory.getLog(ResponseFuture.class);

    private static final Map<Long, Channel> channels = new ConcurrentHashMap<Long, Channel>();
    private static final Map<Long, ResponseFuture> responseFutures = new ConcurrentHashMap<Long, ResponseFuture>();
    private static final ScheduledExecutorService timeoutScanner = Executors
            .newSingleThreadScheduledExecutor(new NamedThreadFactory("ResponseTimeoutScanner", true));

    static {
        // Start the response timeout scanner.
        timeoutScanner.scheduleWithFixedDelay(new TimeoutScanner(), 10, 30, TimeUnit.MILLISECONDS);
    }

    public static void responseReceived(Channel channel, Response response) {
        long id = response.getId();

        try {
            ResponseFuture future = responseFutures.remove(id);
            if (future != null) {
                future.setResponse(response);
            } else {
                LOG.warn("Timeout response received: " + response + //
                        ", channel: [" + channel.getLocalAddress() + " <-- " + channel.getRemoteAddress() + "].");
            }

        } finally {
            channels.remove(id);
        }
    }

    private final Channel channel;
    private final Request request;
    private final int timeout;
    private final long startTime = System.currentTimeMillis();
    private final Lock waitLock = new ReentrantLock();
    private final Condition waitCondition = waitLock.newCondition();

    private volatile Response response;

    public ResponseFuture(Channel channel, Request request, int timeout) {
        this.channel = channel;
        this.request = request;
        this.timeout = timeout;

        channels.put(request.getId(), channel);
        responseFutures.put(request.getId(), this);
    }

    /**
     * Get the response with the configured timeout setting.
     */
    public Response get() throws TimeoutException {
        return get(timeout);
    }

    /**
     * Get the response with a specified timeout value.
     */
    public Response get(int timeout) throws TimeoutException {
        if (isDone()) {
            return response;
        }

        long start = System.currentTimeMillis();
        waitLock.lock();
        try {
            while (!isDone()) {
                waitCondition.await(timeout, TimeUnit.MILLISECONDS);
                if (isDone() || isTimeout(start, timeout)) {
                    break;
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            waitLock.unlock();
        }

        if (!isDone()) {
            throw new TimeoutException(request, System.currentTimeMillis() - startTime);
        }

        return response;
    }

    public Channel getChannel() {
        return channel;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setResponse(Response response) {
        waitLock.lock();
        try {
            this.response = response;
            if (waitCondition != null) {
                waitCondition.signal();
            }

        } finally {
            waitLock.unlock();
        }
    }

    private boolean isDone() {
        return response != null;
    }

    private boolean isTimeout(long start, int timeout) {
        return System.currentTimeMillis() - start > timeout;
    }

    /**
     * Scan all {@link ResponseFuture} to find out the timeout exception.
     */
    private static class TimeoutScanner implements Runnable {

        @Override
        public void run() {
            try {
                for (ResponseFuture future : responseFutures.values()) {
                    if (future == null || future.isDone()) {
                        continue;
                    }

                    if (System.currentTimeMillis() - future.getStartTime() > future.getTimeout()) {
                        Response timeoutResponse = new Response();
                        // TODO: Add more info.
                        timeoutResponse.setErrorAndMessage("TimeoutException");

                        ResponseFuture.responseReceived(future.getChannel(), timeoutResponse);
                    }
                }

            } catch (Throwable t) {
                LOG.warn("TimeoutScanner executed error!", t);
            }
        }
    }

}
