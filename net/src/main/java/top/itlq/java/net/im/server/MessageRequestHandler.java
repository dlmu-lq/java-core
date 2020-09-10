package top.itlq.java.net.im.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.Protocol;
import top.itlq.java.net.im.provide.request.MessageRequestPacket;
import top.itlq.java.net.im.provide.response.MessageResponsePacket;

/**
 * @author liqiang
 * @description 消息请求处理
 * @date 2020/9/10 下午11:28
 */
@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        log.info("服务端收到消息：{}", messageRequestPacket.getContent());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setContent(String.format("收到你的消息了：%s", messageRequestPacket.getContent()));
        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
