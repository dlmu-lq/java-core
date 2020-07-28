package top.itlq.java.net.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = ctx.alloc().buffer();

        byteBuf.writeBytes("你好".getBytes(StandardCharsets.UTF_8));

        log.info("客户端发送消息：{}", "你好");
        ctx.channel().writeAndFlush(byteBuf);
        super.channelActive(ctx);
    }
}
