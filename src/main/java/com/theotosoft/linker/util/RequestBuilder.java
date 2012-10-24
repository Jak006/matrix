package com.theotosoft.linker.util;

import java.lang.reflect.Method;

import com.theotosoft.linker.Request;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public final class RequestBuilder {

    public static Request build(String serviceName, Method method, Object[] args) {
        Request request = new Request();
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setMethodArgSigs(MethodUtils.getMethodArgSigs(method.getParameterTypes()));
        request.setMethodArgs(args);

        return request;
    }

}
