package com.immomo.matrix.remoting;

/**
 * @author mixueqiang
 * @since 2012-10-24
 * 
 */
public interface MatrixChannelHandler {

    void messageSent(MatrixChannel channel, Object message);

    void messageReceived(MatrixChannel channel, Object message);

    void exceptionCaught(MatrixChannel channel, Throwable exception);

}
