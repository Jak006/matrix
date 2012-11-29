package com.immomo.matrix.remoting.http;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.immomo.matrix.Response;
import com.immomo.matrix.ResponseFuture;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class HttpClientHandler extends SimpleChannelHandler {

    /**
     * Receive the {@link Response}, and set it into {@link ResponseFuture}.
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        ResponseFuture.responseReceived(e.getChannel(), response);
    }

}
