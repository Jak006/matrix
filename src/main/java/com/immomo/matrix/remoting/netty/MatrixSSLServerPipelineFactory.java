package com.immomo.matrix.remoting.netty;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.ssl.SslHandler;

import com.immomo.matrix.remoting.netty.ssl.SecureSslContextFactory;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class MatrixSSLServerPipelineFactory implements ChannelPipelineFactory {

    private MatrixServerHandler serverHandler;

    public MatrixSSLServerPipelineFactory() {
        serverHandler = new MatrixServerHandler("matrix_server.properties");
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        // Add SSL support.
        SSLEngine engine = SecureSslContextFactory.getServerContext().createSSLEngine();
        engine.setUseClientMode(false);
        pipeline.addLast("ssl", new SslHandler(engine));

        pipeline.addLast("encode", new ObjectEncoder());
        pipeline.addLast("decode", new ObjectDecoder(ClassResolvers.softCachingConcurrentResolver( //
                Thread.currentThread().getContextClassLoader() //
                )));
        pipeline.addLast("handler", serverHandler);

        return pipeline;
    }

}
