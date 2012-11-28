package com.immomo.matrix.remoting.tcp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

import com.immomo.matrix.serializer.MatrixSerializerFactory;

/**
 * @author mixueqiang
 * @since 2012-11-29
 * 
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder {

    public NettyDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        ChannelBuffer frame = (ChannelBuffer) super.decode(ctx, channel, buffer);
        if (frame == null) {
            return null;
        }

        byte[] bytes = frame.array();
        return MatrixSerializerFactory.getSerializer("HESSIAN").deserialize(bytes);
    }

}
