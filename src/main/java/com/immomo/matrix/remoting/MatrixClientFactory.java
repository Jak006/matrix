package com.immomo.matrix.remoting;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.remoting.http.MatrixHttpClient;
import com.immomo.matrix.remoting.tcp.MatrixTcpClient;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class MatrixClientFactory {

    private static ConcurrentMap<String, MatrixClient> clients = new ConcurrentHashMap<String, MatrixClient>();

    public static void destroy() {
        for (String requestURI : clients.keySet()) {
            MatrixClient matrixClient = clients.remove(requestURI);
            if (matrixClient != null) {
                matrixClient.destroy();
            }
        }
    }

    public static void destroy(String requestURI) {
        MatrixClient matrixClient = clients.remove(requestURI);
        if (matrixClient != null) {
            matrixClient.destroy();
        }
    }

    public static MatrixClient getInstance(String requestURI) {
        if (clients.containsKey(requestURI)) {
            return clients.get(requestURI);
        }

        try {
            URI targetURI = new URI(requestURI);

            MatrixClient client = null;
            if ("http".equals(targetURI.getScheme())) {
                client = new MatrixHttpClient(targetURI);
            } else {
                client = new MatrixTcpClient(targetURI);
            }

            clients.putIfAbsent(requestURI, client);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidTargetURIException e) {
            e.printStackTrace();
        }

        return clients.get(requestURI);
    }

}
