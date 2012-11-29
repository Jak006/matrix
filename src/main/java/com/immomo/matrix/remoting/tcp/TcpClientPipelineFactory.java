package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class TcpClientPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new MatrixTcpEncoder());
        pipeline.addLast("decode", new MatrixTcpDecoder());
        pipeline.addLast("handler", new TcpClientHandler());

        return pipeline;
    }

}
