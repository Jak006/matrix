package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class NettyServerPipelineFactory implements ChannelPipelineFactory {

    private NettyServerHandler serverHandler;

    public NettyServerPipelineFactory() {
        serverHandler = new NettyServerHandler("matrix_server.properties");
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("encode", new NettyEncoder());
        pipeline.addLast("decode", new NettyDecoder());
        pipeline.addLast("handler", serverHandler);

        return pipeline;
    }
}
