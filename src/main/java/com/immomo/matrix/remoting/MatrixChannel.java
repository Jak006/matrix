package com.immomo.matrix.remoting;

import java.net.InetSocketAddress;

/**
 * @author mixueqiang
 * @since 2012-10-24
 * 
 */
public interface MatrixChannel {

    MatrixChannelHandler getChannelProcessor();

    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    void send(Object message) throws RemotingException;

    void close();

    void close(int timeout);

    boolean isClosed();

}
