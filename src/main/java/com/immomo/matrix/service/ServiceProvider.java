package com.immomo.matrix.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.util.MethodUtils;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceProvider {
    private static final Log LOG = LogFactory.getLog(ServiceProvider.class);

    private Map<String, Method> methods = new HashMap<String, Method>();
    private Object serviceInstance;

    public ServiceProvider(String serviceName, String serviceInstanceName) {
        try {
            Class<?> clazz = Class.forName(serviceName);
            for (Method method : clazz.getMethods()) {
                String methodKey = MethodUtils.getMethodKey(method.getName(), method.getParameterTypes());
                this.methods.put(methodKey, method);
            }

            clazz = Class.forName(serviceInstanceName);
            this.serviceInstance = clazz.newInstance();

        } catch (Exception e) {
            LOG.error("Service initialized failed! Service Name: " + serviceName, e);
        }
    }

    /**
     * Process the service request.
     * <p>
     * TODO: Timeout process.
     */
    public Response handleRequest(Request request) {
        final Response response = new Response();
        response.setId(request.getId());

        final String methodName = request.getMethodName();
        final String[] methodArgSigs = request.getMethodArgSigs();

        String methodKey = MethodUtils.getMethodKey(methodName, methodArgSigs);
        Method method = methods.get(methodKey);
        if (method == null) {
            response.setErrorAndMessage("NoSuchMethodException: " + methodName);
        }

        try {
            final Object[] methodArgs = request.getMethodArgs();
            Object result = method.invoke(serviceInstance, methodArgs);
            response.setPayload(result);

        } catch (IllegalArgumentException e) {
            response.setErrorAndMessage("IllegalArgumentException: " + e.getMessage());
        } catch (InvocationTargetException e) {
            response.setErrorAndMessage("InvocationTargetException: " + e.getMessage());
        } catch (Throwable t) {
            response.setErrorAndMessage("Unknown Error/Exception: " + t.getMessage());
        }

        return response;
    }

}
