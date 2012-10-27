package com.immomo.matrix.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
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
    private static Map<String, List<URL>> applicationURLs = new HashMap<String, List<URL>>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource("matrix_client.properties"));
            String[] applications = properties.getProperty("Applications").split(",|;");

            for (String applicationName : applications) {
                String[] urls = properties.getProperty(applicationName).split(",|;");

                List<URL> list = new ArrayList<URL>();
                for (String url : urls) {
                    list.add(new URL(url));
                }
                applicationURLs.put(applicationName, list);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Object getInstance(String applicationName, String serviceName) {
        List<URL> urls = applicationURLs.get(applicationName);
        if (urls == null || urls.isEmpty()) {
            throw new NullPointerException("application urls is null!");
        }

        URL url = urls.get(RandomUtils.nextInt(urls.size()));
        InvocationHandler handler = new ServiceConsumer(applicationName, serviceName, url);

        Class<?> interfaceClass = null;
        try {
            interfaceClass = Class.forName(serviceName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + serviceName);
        }

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { interfaceClass },
                handler);
    }

}
