package com.immomo.matrix.service.provider.factory;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.immomo.matrix.service.provider.ServiceInstance;
import com.immomo.matrix.service.provider.ServiceProviderFactory;
import com.immomo.matrix.util.ClassLoaderUtils;

/**
 * @author mixueqiang
 * @since Nov 28, 2012
 * 
 */
public class PropertyBasedServiceProviderFactory extends ServiceProviderFactory {
    private static final Log LOG = LogFactory.getLog(PropertyBasedServiceProviderFactory.class);

    /**
     * Load the property file, initialize and register all services.
     */
    public PropertyBasedServiceProviderFactory(String propertyFile) {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource(propertyFile));

            for (Object key : properties.keySet()) {
                String serviceName = (String) key;
                String serviceInstanceName = (String) properties.get(key);
                ServiceInstance serviceInstance = new ServiceInstance(serviceName, serviceInstanceName);

                registerServiceInstance(serviceName, serviceInstance);
            }

        } catch (Exception e) {
            LOG.error("Service initialized failed!", e);
        }
    }

}
