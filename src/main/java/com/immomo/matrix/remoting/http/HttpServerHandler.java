package com.immomo.matrix.remoting.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.remoting.tcp.TcpServerHandler;
import com.immomo.matrix.service.PropertyBasedServiceProviderFactory;
import com.immomo.matrix.service.ServiceInstance;
import com.immomo.matrix.service.ServiceProviderFactory;
import com.immomo.matrix.util.ErrorResponseUtils;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class HttpServerHandler extends SimpleChannelHandler {
    private static final Log LOG = LogFactory.getLog(TcpServerHandler.class);

    private ServiceProviderFactory serviceProviderFactory;

    public HttpServerHandler(String propertyFile) {
        serviceProviderFactory = new PropertyBasedServiceProviderFactory(propertyFile);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request) e.getMessage();
        String serviceName = request.getServiceName();
        ServiceInstance serviceInstance = serviceProviderFactory.getInstance(serviceName);
        if (serviceInstance == null) {
            Response response = ErrorResponseUtils.buildServiceNotFoundResponse(request.getId(), serviceName);
            ctx.getChannel().write(response);
            return;
        }

        // Process business logic.
        Response response = serviceInstance.handleRequest(request);
        LOG.debug("Request: " + request + ", response: " + response);
        ctx.getChannel().write(response);
    }

}
