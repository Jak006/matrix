package com.immomo.matrix.remoting.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.ssl.SslHandler;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.ServiceProvider;
import com.immomo.matrix.ServiceProviderFactory;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class MatrixSSLServerHandler extends SimpleChannelHandler {
    private static final Log LOG = LogFactory.getLog(MatrixSSLServerHandler.class);

    private ServiceProviderFactory serviceProviderFactory;

    public MatrixSSLServerHandler(String propertyFile) {
        serviceProviderFactory = new ServiceProviderFactory(propertyFile);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        final SslHandler sslHandler = ctx.getPipeline().get(SslHandler.class);
        sslHandler.handshake();

        super.channelConnected(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request) e.getMessage();
        String serviceName = request.getServiceName();
        ServiceProvider serviceProvider = serviceProviderFactory.getInstance(serviceName);

        // Process the business logic.
        Response response = serviceProvider.handleRequest(request);
        LOG.debug("Request: " + request + ", response: " + response);
        ctx.getChannel().write(response);
    }

}
