package com.immomo.matrix.remoting.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.ssl.SslHandler;

import com.immomo.matrix.Response;
import com.immomo.matrix.ResponseFuture;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class MatrixSSLClientHandler extends SimpleChannelHandler {

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        final SslHandler sslHandler = ctx.getPipeline().get(SslHandler.class);
        sslHandler.handshake();

        super.channelConnected(ctx, e);
    }

    /**
     * Receive the {@link Response}, and set it into {@link ResponseFuture}.
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        ResponseFuture.responseReceived(e.getChannel(), response);
    }

}
