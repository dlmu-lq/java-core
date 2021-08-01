package net.tomcat.response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author liqiang
 * @description
 * @date 2020/9/27 上午7:40
 */
public class NtServletResponse implements ServletResponse{

    private ChannelHandlerContext channelHandlerContext;

    /**
     * netty
     * @param channelHandlerContext
     */
    public NtServletResponse(ChannelHandlerContext channelHandlerContext){
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void write(byte[] data) {
//        String response = "HTTP/1.1 200 OK\n" +
//                "Content-Type:text/plain;\n" +
//                "\r\n" +
//                new String(data);
//        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
//        buffer.writeBytes(response.getBytes());
//        channelHandlerContext.writeAndFlush(buffer);

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(data);
        HttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, buffer);
        channelHandlerContext.writeAndFlush(httpResponse);

        // http与tcp不同，不关闭客户端不会响应出内容
        channelHandlerContext.close();
    }
}
