package com.immomo.matrix.sample;

import com.immomo.matrix.remoting.MatrixServer;
import com.immomo.matrix.remoting.netty.NettyServer;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class Bootstrap {

    public static void main(String[] args) {
        MatrixServer server = new NettyServer();
        server.start();
    }

}
