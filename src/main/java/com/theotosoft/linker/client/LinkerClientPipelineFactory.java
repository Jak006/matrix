package com.theotosoft.linker.client;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class LinkerClientPipelineFactory implements ChannelPipelineFactory {

    private ChannelHandler handler = new LinkerClientHandler();

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
