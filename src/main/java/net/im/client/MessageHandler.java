package net.im.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import net.im.provide.request.MessagePacket;

/**
 * @author liqiang
 * @description 消息解析
 * @date 2020/9/10 下午11:28
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<MessagePacket> {

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket messagePacket) throws Exception {
//        log.info("客户端收到消息：{}", messagePacket.getContent());
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket messagePacket) throws Exception {
        log.info("{}:{}", messagePacket.getFromUserName(), messagePacket.getContent());
    }
}
