package top.itlq.java.net.im.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.AbstractPacket;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;
import top.itlq.java.net.im.provide.Protocol;
import top.itlq.java.net.im.provide.request.MessagePacket;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;

/**
 * @author liqiang
 * @description 服务端IO事件的相关回调
 * @date 2020/9/8 下午10:51
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务端读取数据...");
        ByteBuf data = (ByteBuf) msg;
        AbstractPacket packet = Protocol.decode(data);
        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            log.info("服务端开始登录, {}", loginRequestPacket.getImCode());
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            if(valid(loginRequestPacket)){
                log.info("服务端登录成功...");
//                ctx.channel().attr();
                loginResponsePacket.setSuccess(true);
            }else{
                log.info("服务端登录失败...");
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("验证失败");
            }
            ctx.channel().writeAndFlush(Protocol.encode(ctx.alloc(), loginResponsePacket));
        }else if(packet instanceof top.itlq.java.net.im.provide.request.MessagePacket){
            top.itlq.java.net.im.provide.request.MessagePacket messagePacket = (top.itlq.java.net.im.provide.request.MessagePacket) packet;
            log.info("服务端收到消息：{}", messagePacket.getContent());
            MessagePacket messageResponsePacket = new MessagePacket();
            messageResponsePacket.setContent(String.format("收到你的消息了：%s", messagePacket.getContent()));
            ctx.channel().writeAndFlush(Protocol.encode(ctx.alloc(), messageResponsePacket));
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        // TODO 用户名密码验证
        return "test".equals(loginRequestPacket.getImCode()) &&
                "test".equals(loginRequestPacket.getImToken());
    }
}
