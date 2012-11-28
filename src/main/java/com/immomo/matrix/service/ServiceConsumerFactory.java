package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.immomo.matrix.addressing.AddressingPool;
import com.immomo.matrix.addressing.AddressingService;
import com.immomo.matrix.exception.URINotFoundException;
import com.immomo.matrix.loadbalance.LoadBalanceStrategy;
import com.immomo.matrix.loadbalance.RandomStrategy;
import com.immomo.matrix.remoting.MatrixClient;
import com.immomo.matrix.remoting.MatrixClientFactory;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumerFactory {

    private Map<String, Object> proxies = new ConcurrentHashMap<String, Object>();
    private AddressingService addressingPool;

    /**
     * Use default {@link RandomStrategy} for service addressing.
     */
    public ServiceConsumerFactory(String propertyFile) {
        this(propertyFile, new RandomStrategy());
    }

    public ServiceConsumerFactory(String propertyFile, LoadBalanceStrategy loadBalanceStrategy) {
        addressingPool = new AddressingPool(propertyFile, loadBalanceStrategy);
    }

    public void destroy() {
        MatrixClientFactory.destroy();
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(String applicationName, Class<T> serviceClass) throws URINotFoundException {
        final String serviceName = serviceClass.getName();
        final String key = applicationName + "_" + serviceName;

        if (proxies.containsKey(key)) {
            return (T) proxies.get(key);
        }

        InvocationHandler handler = new MatrixInvocationHandler(applicationName, serviceClass.getName());
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[] { serviceClass }, handler);
        proxies.put(key, proxy);
        return (T) proxies.get(key);
    }

    public Object getInstance(String applicationName, String serviceName) throws ClassNotFoundException,
            URINotFoundException {
        Class<?> serviceClass = Class.forName(serviceName);
        return getInstance(applicationName, serviceClass);
    }

    /**
     * Matrix invocation handler.
     */
    class MatrixInvocationHandler implements InvocationHandler {
        private String applicationName;
        private String serviceName;

        public MatrixInvocationHandler(String applicationName, String serviceName) {
            this.applicationName = applicationName;
            this.serviceName = serviceName;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Avoid remote invocation for toString method in debug mode.
            if ("toString".equals(method.getName())) {
                return proxy.getClass().getName();
            }

            // Service addressing here.
            String requestURI = addressingPool.getAddress(applicationName, serviceName);

            MatrixClient client = MatrixClientFactory.getInstance(requestURI);
            return client.invoke(serviceName, method, args);
        }
    }

}
