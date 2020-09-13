package top.itlq.java.net.im.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.User;
import top.itlq.java.net.im.provide.request.MessagePacket;

/**
 * @author liqiang
 * @description 消息请求处理
 * @date 2020/9/10 下午11:28
 */
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<MessagePacket> {

//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket messagePacket) throws Exception {
//        log.info("服务端收到消息：{}", messagePacket.getContent());
//        MessagePacket messageResponsePacket = new MessagePacket();
//        messageResponsePacket.setContent(String.format("收到你的消息了：%s", messagePacket.getContent()));
//        ctx.channel().writeAndFlush(messageResponsePacket);
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket messagePacket) throws Exception {
        log.info("服务端收到消息:{}", messagePacket);
        Long toUserId = messagePacket.getToUserId();
        Channel channel = SessionUtils.getChannel(toUserId);
        User user = SessionUtils.getUser(ctx.channel());
        messagePacket.setFromUserName(user.getUserName());
        messagePacket.setFromUserId(user.getId());
        channel.writeAndFlush(messagePacket);
    }
}
