package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.math.RandomUtils;

import com.immomo.matrix.util.ClassLoaderUtils;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceConsumerFactory {
    private static Map<String, List<URI>> serverURIs = new HashMap<String, List<URI>>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource("matrix_client.properties"));
            String[] applications = properties.getProperty("Applications").split(",|;");

            for (String applicationName : applications) {
                String[] urls = properties.getProperty(applicationName).split(",|;");

                List<URI> list = new ArrayList<URI>();
                for (String url : urls) {
                    list.add(new URI(url));
                }
                serverURIs.put(applicationName, list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Object getInstance(String applicationName, String serviceName) throws ClassNotFoundException {
        Class<?> serviceClass = Class.forName(serviceName);
        return getInstance(applicationName, serviceClass);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String applicationName, Class<T> serviceClass) {
        List<URI> uris = serverURIs.get(applicationName);
        if (uris == null || uris.isEmpty()) {
            throw new NullPointerException("URL not found for application: " + applicationName);
        }

        URI uri = uris.get(RandomUtils.nextInt(uris.size()));
        InvocationHandler handler = new ServiceConsumer(applicationName, serviceClass.getName(), uri);
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { serviceClass },
                handler);
    }

}
