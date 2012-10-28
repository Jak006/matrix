package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class InvalidTargetURIException extends MatrixException {
    private static final long serialVersionUID = -7138558878137528606L;

    public InvalidTargetURIException(String targetURI) {
        super("Invalid target URI: " + targetURI);
    }

}
