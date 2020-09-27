package top.itlq.java.net.tomcat.response;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channel;

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
    NtServletResponse(ChannelHandlerContext channelHandlerContext){
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void write(byte[] data) {
            channelHandlerContext.writeAndFlush(data);
    }
}
