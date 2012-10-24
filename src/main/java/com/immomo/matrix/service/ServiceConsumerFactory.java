package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumerFactory {

    public static Object newInstance(String applicationName, String serviceName) {
        InvocationHandler handler = new ServiceConsumer(applicationName, serviceName);

        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(serviceName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + serviceName);
        }

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { interfaceClass }, handler);
    }

}
