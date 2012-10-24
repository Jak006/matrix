package com.theotosoft.linker.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.theotosoft.linker.Server;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class LinkerServer implements Server {
    private static final Log LOG = LogFactory.getLog(LinkerServer.class);
    private static int port = 10010;

    @Override
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        
        bootstrap.setPipelineFactory(new LinkServerPipelineFactory());
        bootstrap.bind(new InetSocketAddress(port));
        LOG.info("Linker Server started at " + port + ".");
    }

    @Override
    public void stop() {
        // Do nothing.
    }

}
