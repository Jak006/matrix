package com.immomo.matrix.remoting;

import java.lang.reflect.Method;
import java.net.URL;

import com.immomo.matrix.Response;

/**
 * @author mixueqiang
 * @since 2012-10-24
 * 
 */
public interface MatrixClient {

    /**
     * Get the URL requested.
     */
    URL getRequestURL();

    /**
     * Get the timeout on this request.
     */
    int getTimeout();

    /**
     * Invoke service.
     */
    Object invoke(String applicationName, String serviceName, Method method, Object[] args);

    /**
     * Set the response.
     */
    void setResponse(Response response);

    /**
     * Set the status of the {@link MatrixClient}.
     */
    void setStatus(MatrixChannelStatus status);

    /**
     * Close the connection on this client.
     */
    void close();

}
