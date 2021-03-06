package com.immomo.matrix.exception;

import java.net.InetSocketAddress;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class RemotingException extends MatrixException {
    private static final long serialVersionUID = -5632027511182622422L;

    private InetSocketAddress localAddress;
    private InetSocketAddress remoteAddress;

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message) {
        super(message);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, Throwable cause) {
        super(cause);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public RemotingException(InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message,
            Throwable cause) {
        super(message, cause);

        this.localAddress = localAddress;
        this.remoteAddress = remoteAddress;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

}
