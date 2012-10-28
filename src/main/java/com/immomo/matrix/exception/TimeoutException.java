package com.immomo.matrix.exception;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class TimeoutException extends MatrixException {
    private static final long serialVersionUID = -2714408087604910464L;

    public TimeoutException(String message, int timeElapsed) {
        super(message);
    }

}
