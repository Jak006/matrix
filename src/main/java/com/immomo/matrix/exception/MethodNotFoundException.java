package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-11-28
 * 
 */
public class MethodNotFoundException extends MatrixException {
    private static final long serialVersionUID = -1297579221296382803L;

    public MethodNotFoundException(String methodName) {
        super(methodName);
    }
}
