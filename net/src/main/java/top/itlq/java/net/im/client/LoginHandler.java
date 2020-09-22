package top.itlq.java.net.im.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.LoginUtils;
import top.itlq.java.net.im.provide.Protocol;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;
import top.itlq.java.net.im.provide.request.MessagePacket;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiang
 * @description 处理登录响应
 * @date 2020/9/10 下午11:24
 */
@Slf4j
public class LoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    /**
     * 连接建立时进行登录
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端开始登录...");
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket("test", "test");
//        ByteBuf encode = Protocol.encode(ctx.alloc(), loginRequestPacket);
//        ctx.channel().writeAndFlush(loginRequestPacket);
//        startSendMessageThread(ctx.channel());

        startConsoleThread(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if(loginResponsePacket.getSuccess()){
            log.info("客户端登录成功...");
            LoginUtils.markAsLogin(ctx.channel());
        }else{
            log.info("客户端登录失败，原因:{}", loginResponsePacket.getReason());
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
                    MessagePacket messagePacket = new MessagePacket();
                    messagePacket.setContent(message);
                    channel.writeAndFlush(Protocol.encode(messagePacket));
                }
            }
        }).start();
    }

    private void startConsoleThread(Channel channel){
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (!Thread.interrupted()){
                if(!LoginUtils.hasLogin(channel)){
                    log.info("输入用户名进行登录：");
                    String userName = scanner.nextLine();
                    LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                    loginRequestPacket.setUserName(userName);
                    channel.writeAndFlush(loginRequestPacket);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    Long toUserId = scanner.nextLong();
                    String content = scanner.next();
                    MessagePacket messagePacket = new MessagePacket();
                    messagePacket.setToUserId(toUserId);
                    messagePacket.setContent(content);
                    channel.writeAndFlush(messagePacket);
                }
            }
        }).start();

    }
}
