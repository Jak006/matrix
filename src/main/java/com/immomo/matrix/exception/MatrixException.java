package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class MatrixException extends Exception {
    private static final long serialVersionUID = -2706753519264524806L;

    public MatrixException(String message) {
        super(message);
    }

    public MatrixException(Throwable cause) {
        super(cause);
    }

    public MatrixException(String message, Throwable cause) {
        super(message, cause);
    }

}
