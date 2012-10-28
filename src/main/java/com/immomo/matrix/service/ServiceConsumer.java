package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;

import com.immomo.matrix.remoting.MatrixClient;
import com.immomo.matrix.remoting.netty.NettyClient;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumer implements InvocationHandler {

    private String applicationName;
    private String serviceName;
    private URI requestURI;

    public ServiceConsumer(String applicationName, String serviceName, URI requestURI) {
        this.applicationName = applicationName;
        this.serviceName = serviceName;
        this.requestURI = requestURI;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MatrixClient client = new NettyClient(requestURI);
        return client.invoke(applicationName, serviceName, method, args);
    }

}
