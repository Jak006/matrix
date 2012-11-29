package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class TcpServerPipelineFactory implements ChannelPipelineFactory {

    private TcpServerHandler serverHandler;

    public TcpServerPipelineFactory() {
        serverHandler = new TcpServerHandler("matrix_server.properties");
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

        pipeline.addLast("encode", new MatrixTcpEncoder());
        pipeline.addLast("decode", new MatrixTcpDecoder());
        pipeline.addLast("handler", serverHandler);

        return pipeline;
    }

}
