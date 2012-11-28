package com.immomo.matrix.remoting.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.immomo.matrix.remoting.MatrixServer;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class NettyServer implements MatrixServer {
    private static final Log LOG = LogFactory.getLog(NettyServer.class);
    private int port = 10010;
    private ServerBootstrap bootstrap;

    public NettyServer() {
        this(10010);
    }

    public NettyServer(int port) {
        this.port = port;
    }

    @Override
    public void startServer() {
        ChannelFactory channelFacotry = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(channelFacotry);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setPipelineFactory(new NettyServerPipelineFactory());

        bootstrap.bind(new InetSocketAddress(port));
        LOG.info("Matrix Server started at " + port + ".");
    }

    @Override
    public void stopServer() {
        bootstrap.releaseExternalResources();
    }

}