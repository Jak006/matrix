package com.immomo.matrix;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.immomo.matrix.remoting.MatrixClient;
import com.immomo.matrix.remoting.MatrixClientFactory;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumer implements InvocationHandler {

    private String applicationName;
    private String serviceName;
    private String requestURI;

    public ServiceConsumer(String applicationName, String serviceName, String requestURI) {
        this.applicationName = applicationName;
        this.serviceName = serviceName;
        this.requestURI = requestURI;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MatrixClient client = MatrixClientFactory.getInstance(requestURI);
        return client.invoke(applicationName, serviceName, method, args);
    }

}
