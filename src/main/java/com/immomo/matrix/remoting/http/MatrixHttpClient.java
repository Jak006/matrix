package com.immomo.matrix.remoting.http;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.ResponseFuture;
import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.exception.MatrixException;
import com.immomo.matrix.remoting.AbstractMatrixClient;
import com.immomo.matrix.remoting.MatrixChannelStatus;
import com.immomo.matrix.util.NamedThreadFactory;
import com.immomo.matrix.util.RequestBuilder;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class MatrixHttpClient extends AbstractMatrixClient {

    private static final ChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(new NamedThreadFactory("MatrixHTTPChannelBoss", true)),
            Executors.newCachedThreadPool(new NamedThreadFactory("MatrixHTTPChannelWorker", true)));

    private ClientBootstrap bootstrap;
    private volatile Channel channel;

    public MatrixHttpClient(URI targetURI) throws InvalidTargetURIException {
        super(targetURI);

        bootstrap = new ClientBootstrap(channelFactory);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setPipelineFactory(new HttpClientPipelineFactory());

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(getHost(), getPort()));
        channel = future.getChannel();

        setStatus(MatrixChannelStatus.CONNECTED);
    }

    @Override
    public Object invoke(String serviceName, Method method, Object[] args) throws MatrixException {
        ResponseFuture responseFuture = invokeAsyn(serviceName, method, args);
        Response response = responseFuture.get();

        if (response.isError()) {
            // TODO
        }

        return response.getPayload();
    }

    @Override
    public ResponseFuture invokeAsyn(String serviceName, Method method, Object[] args) throws MatrixException {
        Request request = RequestBuilder.build(serviceName, method, args);
        ResponseFuture responseFuture = new ResponseFuture(channel, request, getTimeout());
        channel.write(request);

        return responseFuture;
    }

    @Override
    public void destroy() {
        channel.close();
        bootstrap.releaseExternalResources();
    }

}
