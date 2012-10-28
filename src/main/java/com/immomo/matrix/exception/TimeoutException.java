package com.immomo.matrix.exception;

import com.immomo.matrix.Request;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class TimeoutException extends MatrixException {
    private static final long serialVersionUID = -2714408087604910464L;

    public TimeoutException(Request request, long elapsedTime) {
        super("TimeoutException, request: " + request + ", elapsed time:" + elapsedTime);
    }

}
