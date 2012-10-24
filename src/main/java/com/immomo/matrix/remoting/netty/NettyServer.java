package com.immomo.matrix.remoting.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.immomo.matrix.remoting.MatrixServer;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class NettyServer implements MatrixServer {
    private static final Log LOG = LogFactory.getLog(NettyServer.class);
    private static int port = 10010;

    @Override
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        final ChannelHandler serverHandler = new MatrixServerHandler();
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            @Override
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("encode", new ObjectEncoder());
                pipeline.addLast(
                        "decode",
                        new ObjectDecoder(ClassResolvers.softCachingConcurrentResolver(Thread.currentThread()
                                .getContextClassLoader())));
                pipeline.addLast("handler", serverHandler);

                return pipeline;
            }
        });

        bootstrap.bind(new InetSocketAddress(port));
        LOG.info("Matrix Server started at " + port + ".");
    }

    @Override
    public void stop() {
        // Do nothing.
    }

}
