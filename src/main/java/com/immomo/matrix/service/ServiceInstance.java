package com.immomo.matrix.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.util.ErrorResponseUtils;
import com.immomo.matrix.util.MethodUtils;

/**
 * 服务实例：维护了业务服务的服务实例。所有的服务请求都最终交由这里处理。
 * 
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class ServiceInstance {
    private static final Log LOG = LogFactory.getLog(ServiceInstance.class);

    private Map<String, Method> methods = new HashMap<String, Method>();
    private Object serviceInstance;

    public ServiceInstance(String serviceName, String serviceInstanceName) {
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
     * TODO: Exception, timeout handling.
     */
    public Response handleRequest(Request request) {
        final long id = request.getId();
        final String methodName = request.getMethodName();
        final String[] methodArgSigs = request.getMethodArgSigs();

        String methodKey = MethodUtils.getMethodKey(methodName, methodArgSigs);
        Method method = methods.get(methodKey);
        if (method == null) {
            return ErrorResponseUtils.buildMethodNotFoundResponse(id, methodName);
        }

        try {
            final Response response = new Response(id);
            final Object[] methodArgs = request.getMethodArgs();
            Object result = method.invoke(serviceInstance, methodArgs);
            response.setPayload(result);
            return response;

        } catch (IllegalArgumentException e) {
            return ErrorResponseUtils.buildIllegalArgumentResponse(id, e.getMessage());
        } catch (InvocationTargetException e) {
            return ErrorResponseUtils.buildInvocationTargetExResponse(id, e.getMessage());
        } catch (Throwable t) {
            return ErrorResponseUtils.buildUnknownExResponse(id, t.getMessage());
        }
    }

}
