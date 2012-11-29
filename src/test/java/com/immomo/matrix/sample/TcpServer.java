package com.immomo.matrix.sample;

import com.immomo.matrix.remoting.MatrixServer;
import com.immomo.matrix.remoting.tcp.MatrixTcpServer;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class TcpServer {

    public static void main(String[] args) {
        MatrixServer server = new MatrixTcpServer();
        server.start();
    }

}
