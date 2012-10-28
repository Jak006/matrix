package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class URINotFoundException extends MatrixException {
    private static final long serialVersionUID = 2978107645383990015L;

    public URINotFoundException(String applicationName) {
        super("URI not found for application: " + applicationName);
    }

    public URINotFoundException(String applicationName, String serviceName) {
        super("URI not found for application: " + applicationName + ", service: " + serviceName);
    }

}