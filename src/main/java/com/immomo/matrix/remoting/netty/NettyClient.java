package com.immomo.matrix.remoting.netty;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.ResponseFuture;
import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.exception.MatrixException;
import com.immomo.matrix.remoting.AbstractMatrixClient;
import com.immomo.matrix.remoting.MatrixChannelStatus;
import com.immomo.matrix.remoting.MatrixClient;
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

    public NettyClient(final String targetURI) throws InvalidTargetURIException {
        super(targetURI);

        bootstrap = new ClientBootstrap(channelFactory);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("tcpNoDelay", true);

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encode", new ObjectEncoder());
                pipeline.addLast(
                        "decode",
                        new ObjectDecoder(ClassResolvers.softCachingConcurrentResolver(Thread.currentThread()
                                .getContextClassLoader())));
                pipeline.addLast("handler", new MatrixClientHandler());
                return pipeline;
            }
        });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(getHost(), getPort()));
        channel = future.getChannel();

        setStatus(MatrixChannelStatus.CONNECTED);
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

    @Override
    public void close() {
        channel.close();
    }

}
