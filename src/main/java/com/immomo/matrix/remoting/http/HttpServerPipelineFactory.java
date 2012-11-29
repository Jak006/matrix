package com.immomo.matrix.remoting.http;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class HttpServerPipelineFactory implements ChannelPipelineFactory {

    private HttpServerHandler serverHandler;

    public HttpServerPipelineFactory() {
        serverHandler = new HttpServerHandler("matrix_server.properties");
    }

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new MatrixHttpEncoder());
        pipeline.addLast("encoder", new MatrixHttpRequestDecoder());
        pipeline.addLast("handler", serverHandler);

        return pipeline;
    }

}