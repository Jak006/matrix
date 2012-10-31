package com.immomo.matrix.sample;

import com.immomo.matrix.remoting.MatrixServer;
import com.immomo.matrix.remoting.netty.NettyServer;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class SSLServer {

    public static void main(String[] args) {
        MatrixServer server = new NettyServer(true);
        server.start();
    }

}
