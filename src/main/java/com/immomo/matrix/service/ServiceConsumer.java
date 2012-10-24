package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;

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
    private URL requestURL;

    public ServiceConsumer(String applicationName, String serviceName, URL requestURL) {
        this.applicationName = applicationName;
        this.serviceName = serviceName;
        this.requestURL = requestURL;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MatrixClient client = new NettyClient(requestURL);
        return client.invoke(applicationName, serviceName, method, args);
    }

}
