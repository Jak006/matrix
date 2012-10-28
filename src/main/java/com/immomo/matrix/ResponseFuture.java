package com.immomo.matrix;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;

import com.immomo.matrix.exception.TimeoutException;

/**
 * @author mixueqiang
 * @since 2012-10-29
 * 
 */
public class ResponseFuture {
    private static final Log LOG = LogFactory.getLog(ResponseFuture.class);
    private static final Map<Long, Channel> channels = new ConcurrentHashMap<Long, Channel>();
    private static final Map<Long, ResponseFuture> responseFutures = new ConcurrentHashMap<Long, ResponseFuture>();

    private final Channel channel;
    private final Request request;
    private final int timeout;
    private final Lock waitLock = new ReentrantLock();
    private final Condition waitCondition = waitLock.newCondition();

    private volatile Response response;

    public ResponseFuture(Channel channel, Request request, int timeout) {
        this.channel = channel;
        this.request = request;
        this.timeout = timeout; // TODO

        channels.put(request.getId(), channel);
        responseFutures.put(request.getId(), this);
    }

    public Response get() throws TimeoutException {
        return get(timeout);
    }

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
            throw new TimeoutException(request, System.currentTimeMillis() - start);
        }

        return response;
    }

    private boolean isDone() {
        return response != null;
    }

    private boolean isTimeout(long start, int timeout) {
        return System.currentTimeMillis() - start > timeout;
    }

}
