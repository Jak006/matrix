package com.theotosoft.linker.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.theotosoft.linker.Response;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class LinkerClientHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        System.out.println(response.getPayload());

    }

}
