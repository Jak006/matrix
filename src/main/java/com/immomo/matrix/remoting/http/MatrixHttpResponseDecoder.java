package com.immomo.matrix.remoting.http;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseDecoder;

import com.immomo.matrix.Response;
import com.immomo.matrix.serializer.MatrixSerializerFactory;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class MatrixHttpResponseDecoder extends HttpResponseDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, State state)
            throws Exception {
        Object obj = super.decode(ctx, channel, buffer, state);
        if (obj == null) {
            return null;
        }

        HttpResponse httpResponse = (HttpResponse) obj;
        ChannelBuffer channelBuffer = httpResponse.getContent();

        Response response = (Response) MatrixSerializerFactory.getSerializer("HESSIAN").deserialize(
                channelBuffer.array());
        return response;
    }

}
