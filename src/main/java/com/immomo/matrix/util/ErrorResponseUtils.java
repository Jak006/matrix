package com.immomo.matrix.util;

import com.immomo.matrix.Response;

/**
 * @author mixueqiang
 * @since 2012-11-28
 * 
 */
public class ErrorResponseUtils {

    // Error code.
    public static final String EX_ILLEGAL_ARGUMENT = "IllegalArgument";
    public static final String EX_INVALID_TARGET_URI = "InvalidTargeURI";
    public static final String EX_INVOCATION_TARGET = "InvocationTarget";
    public static final String EX_METHOD_NOT_FOUND = "_MethodNotFound";
    public static final String EX_REMOTING_CLIENT = "Remoting";
    public static final String EX_SERVICE_NOT_FOUND = "ServiceNotFound";
    public static final String EX_TIMEOUT = "Timeout";
    public static final String EX_UNKNOWN = "UnknownException";
    public static final String EX_URI_NOT_FOUND = "UriNotFound";

    public static Response buildResponse(long id, String errorCode, String msg) {
        Response resp = new Response(id);
        resp.setErrorAndMessage(errorCode, msg);
        return resp;
    }

    public static Response buildIllegalArgumentResponse(long id, String msg) {
        return buildResponse(id, EX_ILLEGAL_ARGUMENT, msg);
    }

    public static Response buildInvocationTargetExResponse(long id, String msg) {
        return buildResponse(id, EX_INVOCATION_TARGET, msg);
    }

    public static Response buildMethodNotFoundResponse(long id, String methodName) {
        return buildResponse(id, EX_METHOD_NOT_FOUND, methodName);
    }

    public static Response buildServiceNotFoundResponse(long id, String serviceName) {
        return buildResponse(id, EX_SERVICE_NOT_FOUND, serviceName);
    }

    public static Response buildTimeoutResponse(long id, String msg) {
        return buildResponse(id, EX_TIMEOUT, msg);
    }

    public static Response buildUnknownExResponse(long id, String msg) {
        return buildResponse(id, EX_UNKNOWN, msg);
    }

    private ErrorResponseUtils() {
    }

}
