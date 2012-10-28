package com.immomo.matrix.remoting.netty;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

import com.immomo.matrix.Response;
import com.immomo.matrix.remoting.MatrixChannelStatus;
import com.immomo.matrix.remoting.MatrixClient;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class MatrixClientHandler extends SimpleChannelHandler {
    private static final Log LOG = LogFactory.getLog(MatrixClientHandler.class);

    private final MatrixClient client;

    public MatrixClientHandler(URI uri, MatrixClient client) {
        if (client == null) {
            throw new NullPointerException("client");
        }

        this.client = client;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        client.setStatus(MatrixChannelStatus.EXCEPTION);
        super.exceptionCaught(ctx, e);

    }

    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
        client.setStatus(MatrixChannelStatus.REQUEST_SENT);
        super.writeComplete(ctx, e);
    }

    /**
     * Receive the {@link Response}, and set it to {@link MatrixClient}.
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Response response = (Response) e.getMessage();
        e.getChannel().close();

        LOG.debug(response);
        client.setResponse(response);
    }

}
