package com.immomo.matrix.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.immomo.matrix.Request;
import com.immomo.matrix.client.LinkerClientPipelineFactory;

/**
 * @author mixueqiang
 * @since 2012-10-19
 * 
 */
public class LinkerClientTest {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new LinkerClientPipelineFactory());
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(10010));

        Request request = new Request();
        request.setServiceName("com.theotosoft.linker.sample.HelloWorldService");
        request.setMethodName("sayHello");
        request.setMethodArgSigs(new String[0]);
        request.setMethodArgs(new Object[0]);

        future.getChannel().write(request);
    }

}
