package com.immomo.matrix.remoting.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;

import com.immomo.matrix.Request;
import com.immomo.matrix.serializer.MatrixSerializerFactory;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class MatrixHttpRequestDecoder extends HttpRequestDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state)
            throws Exception {
        Object obj = super.decode(ctx, channel, buffer, state);
        if (obj == null) {
            return null;
        }

        HttpRequest httpRequest = (HttpRequest) obj;
        ChannelBuffer channelBuffer = httpRequest.getContent();

        Request request = (Request) MatrixSerializerFactory.getSerializer("HESSIAN").deserialize(channelBuffer.array());
        return request;
    }

}
