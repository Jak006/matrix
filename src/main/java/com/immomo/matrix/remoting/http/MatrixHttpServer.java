package com.immomo.matrix.remoting.http;

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
 * @since Nov 29, 2012
 * 
 */
public class MatrixHttpServer implements MatrixServer {
    private static final Log LOG = LogFactory.getLog(MatrixHttpServer.class);
    private int port = 10012;
    private ChannelFactory channelFactory;

    public MatrixHttpServer() {
        this(10012);
    }

    public MatrixHttpServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        channelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());
        ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
        bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
        bootstrap.setOption("sendBufferSize", 8 * 1024);
        bootstrap.setOption("receiveBufferSize", 8 * 1024);

        bootstrap.bind(new InetSocketAddress(port));
        LOG.info("Matrix HTTP Server started at " + port + ".");
    }

    @Override
    public void stop() {
        channelFactory.releaseExternalResources();
        LOG.info("Matrix HTTP Server stoped.");
    }

}
