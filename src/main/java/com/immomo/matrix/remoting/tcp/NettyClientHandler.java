package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.immomo.matrix.Response;
import com.immomo.matrix.ResponseFuture;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class NettyClientHandler extends SimpleChannelHandler {

    /**
     * Receive the {@link Response}, and set it into {@link ResponseFuture}.
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        ResponseFuture.responseReceived(e.getChannel(), response);
    }

}
