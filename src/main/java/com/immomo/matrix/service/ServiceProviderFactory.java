package com.immomo.matrix.service;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.immomo.matrix.util.ClassLoaderUtils;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class ServiceProviderFactory {
    private static final Log LOG = LogFactory.getLog(ServiceProviderFactory.class);

    private static ConcurrentMap<String, ServiceProvider> services = new ConcurrentHashMap<String, ServiceProvider>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource("matrix_server.properties"));

            for (Object key : properties.keySet()) {
                String serviceName = (String) key;
                String serviceInstanceName = (String) properties.get(key);
                ServiceProvider serviceProvider = new ServiceProvider(serviceName, serviceInstanceName);
                services.put(serviceName, serviceProvider);
            }

            LOG.info(services.size() + " services initialized OK:\n" + services.keySet());
        } catch (Exception e) {
            LOG.error("Service initialized failed!", e);
        }
    }

    public static ServiceProvider getInstance(String serviceName) {
        if (!services.containsKey(serviceName)) {
            // TODO: 异常处理
            return null;
        }

        return services.get(serviceName);
    }

}
