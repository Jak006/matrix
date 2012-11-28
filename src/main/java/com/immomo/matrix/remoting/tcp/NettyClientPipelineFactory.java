package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class NettyClientPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new NettyEncoder());
        pipeline.addLast("decode", new NettyDecoder());
        pipeline.addLast("handler", new NettyClientHandler());
        return pipeline;
    }
}
