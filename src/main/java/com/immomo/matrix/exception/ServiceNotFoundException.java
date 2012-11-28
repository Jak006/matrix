package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-11-28
 * 
 */
public class ServiceNotFoundException extends MatrixException {
    private static final long serialVersionUID = -2922345862705541078L;

    public ServiceNotFoundException(String serviceName) {
        super(serviceName);
    }

}
