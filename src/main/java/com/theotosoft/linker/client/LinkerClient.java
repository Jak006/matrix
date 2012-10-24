package com.theotosoft.linker.client;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.theotosoft.linker.Request;
import com.theotosoft.linker.util.ClassLoaderUtils;
import com.theotosoft.linker.util.RequestBuilder;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class LinkerClient {
    private static Map<String, List<String>> applicationHosts = new HashMap<String, List<String>>();

    private static ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                    Executors.newCachedThreadPool()));
    private static ChannelFuture future;

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource("linker_client.properties"));
            String[] applications = properties.getProperty("Applications").split(",|;");

            for (String applicationName : applications) {
                String[] hosts = properties.getProperty(applicationName).split(",|;");
                applicationHosts.put(applicationName, Arrays.asList(hosts));
            }

            // TODO: choose a host to connect.
            bootstrap.setPipelineFactory(new LinkerClientPipelineFactory());
            future = bootstrap.connect(new InetSocketAddress(10010));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Invoke service.
     */
    public static Object invoke(String applicationName, String serviceName, Method method, Object[] args) {
        Request request = RequestBuilder.build(serviceName, method, args);
        ChannelFuture responseFuture = future.getChannel().write(request);
        while (!responseFuture.isDone()) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private LinkerClient() {
        // Let it be invisible.
    }

}
