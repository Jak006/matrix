package com.immomo.matrix.sample;

import com.immomo.matrix.remoting.MatrixServer;
import com.immomo.matrix.remoting.http.MatrixHttpServer;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class HttpServer {

    public static void main(String[] args) {
        MatrixServer server = new MatrixHttpServer();
        server.start();
    }

}
