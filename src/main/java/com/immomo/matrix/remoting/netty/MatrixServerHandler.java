package com.immomo.matrix.remoting.netty;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.service.ServiceProvider;
import com.immomo.matrix.service.ServiceProviderFactory;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class MatrixServerHandler extends SimpleChannelHandler {
    private static final Log LOG = LogFactory.getLog(MatrixServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request) e.getMessage();
        String serviceName = request.getServiceName();
        ServiceProvider serviceProvider = ServiceProviderFactory.getInstance(serviceName);

        // Process the business logic.
        Response response = serviceProvider.handleRequest(request);
        LOG.debug("Request: " + request + ", response: " + response);
        ctx.getChannel().write(response);
    }

}
