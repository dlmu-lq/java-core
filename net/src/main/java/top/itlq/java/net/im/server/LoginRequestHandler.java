package top.itlq.java.net.im.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.itlq.java.net.im.provide.Protocol;
import top.itlq.java.net.im.provide.request.LoginRequestPacket;
import top.itlq.java.net.im.provide.response.LoginResponsePacket;

/**
 * @author liqiang
 * @description 登录请求处理
 * @date 2020/9/10 下午11:25
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
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
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        // TODO 用户名密码验证
        return "test".equals(loginRequestPacket.getImCode()) &&
                "test".equals(loginRequestPacket.getImToken());
    }
}
