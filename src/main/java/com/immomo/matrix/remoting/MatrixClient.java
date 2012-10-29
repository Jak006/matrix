package com.immomo.matrix.remoting;

import java.lang.reflect.Method;
import java.net.URI;

import com.immomo.matrix.exception.MatrixException;

/**
 * @author mixueqiang
 * @since 2012-10-24
 * 
 */
public interface MatrixClient {

    /**
     * Get the URL requested.
     */
    URI getRequestURI();

    /**
     * Get the timeout on this request.
     */
    int getTimeout();

    /**
     * Invoke service.
     */
    Object invoke(String applicationName, String serviceName, Method method, Object[] args) throws MatrixException;

    /**
     * Set the status of the {@link MatrixClient}.
     */
    void setStatus(MatrixChannelStatus status);

    /**
     * Close the connection on this client.
     */
    void close();

}
