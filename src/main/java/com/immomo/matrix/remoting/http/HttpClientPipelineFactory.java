package com.immomo.matrix.remoting.http;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class HttpClientPipelineFactory implements ChannelPipelineFactory {

    @Override
    public ChannelPipeline getPipeline() {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("encode", new MatrixHttpEncoder());
        pipeline.addLast("decode", new MatrixHttpResponseDecoder());
        pipeline.addLast("handler", new HttpClientHandler());

        return pipeline;
    }
}
