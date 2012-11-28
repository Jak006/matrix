package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class NettyClientPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new ObjectEncoder());
        pipeline.addLast("decode", new ObjectDecoder(ClassResolvers.softCachingConcurrentResolver( //
                Thread.currentThread().getContextClassLoader() //
                )));
        pipeline.addLast("handler", new NettyClientHandler());
        return pipeline;
    }

}
