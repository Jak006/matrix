package com.immomo.matrix.server;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.util.ClassLoaderUtils;
import com.immomo.matrix.util.MethodUtils;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class LinkerServerHandler extends SimpleChannelHandler {
    private static ConcurrentMap<String, Method> methodCache = new ConcurrentHashMap<String, Method>();
    private static ConcurrentMap<String, Object> serviceInstances = new ConcurrentHashMap<String, Object>();

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource("linker_server.properties"));

            for (Object key : properties.keySet()) {
                String serviceName = (String) key;

                try {
                    Class<?> clazz = Class.forName(serviceName);
                    for (Method method : clazz.getMethods()) {
                        methodCache.put(MethodUtils.getMethodKey(method.getName(), method.getParameterTypes()), method);
                    }

                    clazz = Class.forName((String) properties.get(key));
                    Object serviceInstance = clazz.newInstance();
                    serviceInstances.put(serviceName, serviceInstance);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Request request = (Request) e.getMessage();
        String serviceName = request.getServiceName();
        Object serviceInstance = serviceInstances.get(serviceName);

        // Process the business logic.
        Method method = methodCache.get(MethodUtils.getMethodKey(request.getMethodName(), request.getMethodArgSigs()));
        Object result = method.invoke(serviceInstance, request.getMethodArgs());
        System.out.println("Process result: " + result);

        // Set and write response.
        Response response = new Response();
        response.setPayload(result);
        ctx.getChannel().write(response);
    }

}
