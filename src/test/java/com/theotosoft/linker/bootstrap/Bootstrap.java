package com.theotosoft.linker.bootstrap;

import com.theotosoft.linker.server.LinkerServer;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class Bootstrap {

    public static void main(String[] args) {
        new LinkerServer().start();
    }

}
