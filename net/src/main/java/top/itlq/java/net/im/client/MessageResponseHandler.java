package top.itlq.java.net.im.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.response.MessageResponsePacket;

/**
 * @author liqiang
 * @description 消息解析
 * @date 2020/9/10 下午11:28
 */
@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        log.info("客户端收到消息：{}", messageResponsePacket.getContent());
    }
}
