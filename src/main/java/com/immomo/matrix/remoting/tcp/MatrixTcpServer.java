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
 * @since Oct 18, 2012
 * 
 */
public class MatrixTcpServer implements MatrixServer {
    private static final Log LOG = LogFactory.getLog(MatrixTcpServer.class);
    private int port = 10011;
    private ChannelFactory channelFactory;

    public MatrixTcpServer() {
        this(10011);
    }

    public MatrixTcpServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        channelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.setPipelineFactory(new TcpServerPipelineFactory());
        bootstrap.setOption("sendBufferSize", 8 * 1024);
        bootstrap.setOption("receiveBufferSize", 8 * 1024);

        bootstrap.bind(new InetSocketAddress(port));
        LOG.info("Matrix TCP Server started at " + port + ".");
    }

    @Override
    public void stop() {
        channelFactory.releaseExternalResources();
        LOG.info("Matrix TCP Server stoped.");
    }

}
