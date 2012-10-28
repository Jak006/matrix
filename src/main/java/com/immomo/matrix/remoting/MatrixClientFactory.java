package com.immomo.matrix.remoting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.remoting.netty.NettyClient;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class MatrixClientFactory {
    private static final ConcurrentMap<String, MatrixClient> clients = new ConcurrentHashMap<String, MatrixClient>();

    public static MatrixClient getInstance(String requestURI) {
        if (clients.containsKey(requestURI)) {
            return clients.get(requestURI);
        }

        try {
            MatrixClient client = new NettyClient(requestURI);
            clients.putIfAbsent(requestURI, client);

        } catch (InvalidTargetURIException e) {
            e.printStackTrace();
        }

        return clients.get(requestURI);
    }

}
