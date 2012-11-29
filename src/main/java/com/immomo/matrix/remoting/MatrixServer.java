package com.immomo.matrix.remoting;

/**
 * @author mixueqiang
 * @since 2012-10-24
 * 
 */
public interface MatrixServer {
    public static final String COMPONENT_NAME = "MATRIX_SERVER";

    /**
     * Start the server.
     */
    void start();

    /**
     * Stop the server.
     */
    void stop();

}
