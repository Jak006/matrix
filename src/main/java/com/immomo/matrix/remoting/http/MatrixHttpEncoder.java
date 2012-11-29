package com.immomo.matrix.remoting.http;

import java.net.URI;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpMessage;
import org.jboss.netty.handler.codec.http.HttpMessageEncoder;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;

import com.immomo.matrix.Request;
import com.immomo.matrix.Response;
import com.immomo.matrix.serializer.MatrixSerializerFactory;

/**
 * @author mixueqiang
 * @since Nov 29, 2012
 * 
 */
public class MatrixHttpEncoder extends HttpMessageEncoder {
    static final byte SP = 32;
    static final byte CR = 13;
    static final byte LF = 10;

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (msg instanceof Request) {
            URI uri = new URI("http://127.0.0.1:10012?_timeout=1000");
            HttpRequest httpRequest = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString());
            httpRequest.setHeader(HttpHeaders.Names.HOST, "127.0.0.1");
            httpRequest.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
            httpRequest.setHeader(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);

            byte[] bytes = MatrixSerializerFactory.getSerializer("HESSIAN").serialize(msg);
            httpRequest.setHeader(HttpHeaders.Names.CONTENT_LENGTH, bytes.length);
            httpRequest.setContent(new BigEndianHeapChannelBuffer(bytes));
            return super.encode(ctx, channel, httpRequest);

        } else if (msg instanceof Response) {
            HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            byte[] bytes = MatrixSerializerFactory.getSerializer("HESSIAN").serialize(msg);
            httpResponse.setHeader(HttpHeaders.Names.CONTENT_LENGTH, bytes.length);
            httpResponse.setContent(new BigEndianHeapChannelBuffer(bytes));
            return super.encode(ctx, channel, httpResponse);

        }

        return null;
    }

    @Override
    protected void encodeInitialLine(ChannelBuffer buf, HttpMessage message) throws Exception {
        if (message instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) message;
            buf.writeBytes(request.getMethod().toString().getBytes("ASCII"));
            buf.writeByte(SP);
            buf.writeBytes(request.getUri().getBytes("ASCII"));
            buf.writeByte(SP);
            buf.writeBytes(request.getProtocolVersion().toString().getBytes("ASCII"));
            buf.writeByte(CR);
            buf.writeByte(LF);

        } else if (message instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) message;
            buf.writeBytes(response.getProtocolVersion().toString().getBytes("ASCII"));
            buf.writeByte(SP);
            buf.writeBytes(String.valueOf(response.getStatus().getCode()).getBytes("ASCII"));
            buf.writeByte(SP);
            buf.writeBytes(String.valueOf(response.getStatus().getReasonPhrase()).getBytes("ASCII"));
            buf.writeByte(CR);
            buf.writeByte(LF);

        }

    }

}
