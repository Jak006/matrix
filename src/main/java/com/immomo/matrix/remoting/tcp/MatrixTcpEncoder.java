package com.immomo.matrix.remoting.tcp;

import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.immomo.matrix.serializer.MatrixSerializerFactory;

/**
 * @author mixueqiang
 * @since 2012-11-29
 * 
 */
public class MatrixTcpEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        byte[] bytes = MatrixSerializerFactory.getSerializer("HESSIAN").serialize(msg);
        ChannelBufferOutputStream bout = new ChannelBufferOutputStream(dynamicBuffer(ctx.getChannel().getConfig()
                .getBufferFactory()));
        bout.writeInt(bytes.length);
        bout.write(bytes);
        bout.flush();
        bout.close();

        ChannelBuffer encoded = bout.buffer();
        return encoded;
    }

}
