package com.immomo.matrix.server;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class LinkServerPipelineFactory implements ChannelPipelineFactory {

    private ChannelHandler handler = new LinkerServerHandler();

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new ObjectEncoder());
        pipeline.addLast("decode",
                        new ObjectDecoder(ClassResolvers.softCachingConcurrentResolver(Thread.currentThread().getContextClassLoader())));
        pipeline.addLast("handler", handler);

        return pipeline;
    }

}
