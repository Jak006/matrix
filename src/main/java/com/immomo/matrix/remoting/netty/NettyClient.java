package com.immomo.matrix.remoting.netty;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
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
import com.immomo.matrix.remoting.MatrixClient;
import com.immomo.matrix.util.NamedThreadFactory;
import com.immomo.matrix.util.RequestBuilder;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class NettyClient extends AbstractMatrixClient implements MatrixClient {

    private static final ChannelFactory channelFactory = new NioClientSocketChannelFactory(
            Executors.newCachedThreadPool(new NamedThreadFactory("MatrixNettyChannelBoss", true)),
            Executors.newCachedThreadPool(new NamedThreadFactory("MatrixNettyChannelWorker", true)));

    private ClientBootstrap bootstrap;
    private volatile Channel channel;

    public NettyClient(String targetURI) throws InvalidTargetURIException {
        super(targetURI);

        bootstrap = new ClientBootstrap(channelFactory);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);

        if (isSsl()) {
            bootstrap.setPipelineFactory(new MatrixSSLClientPipelineFactory());
        } else {
            bootstrap.setPipelineFactory(new MatrixClientPipelineFactory());
        }

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(getHost(), getPort()));
        channel = future.getChannel();

        setStatus(MatrixChannelStatus.CONNECTED);
    }

    @Override
    public void destroy() {
        channel.close();
    }

    @Override
    public Object invoke(String applicationName, String serviceName, Method method, Object[] args)
            throws MatrixException {
        Request request = RequestBuilder.build(serviceName, method, args);
        ResponseFuture responseFuture = new ResponseFuture(channel, request, getTimeout());
        channel.write(request);

        Response response = responseFuture.get();
        // TODO: Analyze response.
        return response.getPayload();
    }

}
