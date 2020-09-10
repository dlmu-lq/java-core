package top.itlq.java.net.im.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.LoginUtils;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;

/**
 * @author liqiang
 * @description 处理登录响应
 * @date 2020/9/10 下午11:24
 */
@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if(loginResponsePacket.getSuccess()){
            log.info("客户端登录成功...");
            LoginUtils.markAsLogin(ctx.channel());
        }else{
            log.info("客户端登录失败，原因:{}", loginResponsePacket.getReason());
        }
    }
}
