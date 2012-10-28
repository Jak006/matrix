package com.immomo.matrix;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.immomo.matrix.addressing.AddressingPool;
import com.immomo.matrix.addressing.AddressingService;
import com.immomo.matrix.exception.URINotFoundException;
import com.immomo.matrix.loadbalance.RandomStrategy;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumerFactory {

    private AddressingService addressingPool;

    /**
     * Use default {@link RandomStrategy} for service addressing.
     */
    public ServiceConsumerFactory(String propertyFile) {
        this(propertyFile, new RandomStrategy());
    }

    public ServiceConsumerFactory(String propertyFile, LoadBalanceStrategy strategy) {
        this.addressingPool = new AddressingPool(propertyFile, strategy);
    }

    public Object getInstance(String applicationName, String serviceName) throws ClassNotFoundException,
            URINotFoundException {
        Class<?> serviceClass = Class.forName(serviceName);
        return getInstance(applicationName, serviceClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(String applicationName, Class<T> serviceClass) throws URINotFoundException {
        final String serviceName = serviceClass.getName();
        // Service addressing here.
        final String uri = addressingPool.getAddress(applicationName, serviceName);

        InvocationHandler handler = new ServiceConsumer(applicationName, serviceClass.getName(), uri);
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { serviceClass },
                handler);
    }

}
