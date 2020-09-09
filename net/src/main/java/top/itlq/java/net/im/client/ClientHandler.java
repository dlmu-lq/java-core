package top.itlq.java.net.im.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import top.itlq.java.net.im.provide.AbstractPacket;
import top.itlq.java.net.im.provide.AttributeKeys;
import top.itlq.java.net.im.provide.LoginUtils;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;
import top.itlq.java.net.im.provide.Protocol;
import top.itlq.java.net.im.provide.request.MessageRequestPacket;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;
import top.itlq.java.net.im.provide.response.MessageResponsePacket;

import java.util.Scanner;

/**
 * @author liqiang
 * @description 客户端IO事件的相关回调
 * @date 2020/9/8 下午10:45
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        log.info("客户端开始登录...");
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket("test", "test");
        ByteBuf encode = Protocol.encode(ctx.alloc(), loginRequestPacket);
        ctx.channel().writeAndFlush(encode);
        // 开启输入线程
        startSendMessageThread(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("客户端读取数据...");
        ByteBuf byteBuf = (ByteBuf) msg;
        AbstractPacket packet = Protocol.decode(byteBuf);
        // 登录响应
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            if(loginResponsePacket.getSuccess()){
                log.info("客户端登录成功...");
                LoginUtils.markAsLogin(ctx.channel());
            }else{
                log.info("客户端登录失败，原因:{}", loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            log.info("客户端收到消息：{}", messageResponsePacket.getContent());
        }
    }

    // 简要的写一个根据控制台发送消息
    private void startSendMessageThread(Channel channel){
        new Thread(()->{
            while (!Thread.interrupted()){
                if(LoginUtils.hasLogin(channel)){
                    log.info("输入消息回车发送：");
                    Scanner scanner = new Scanner(System.in);
                    String message = scanner.nextLine();
                    MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                    messageRequestPacket.setContent(message);
                    channel.writeAndFlush(Protocol.encode(messageRequestPacket));
                }
            }
        }).start();
    }
}
