package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.immomo.matrix.client.LinkerClient;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumer implements InvocationHandler {

    private String applicationName;
    private String serviceName;

    public ServiceConsumer(String applicationName, String serviceName) {
        this.applicationName = applicationName;
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return LinkerClient.invoke(applicationName, serviceName, method, args);
    }

}
