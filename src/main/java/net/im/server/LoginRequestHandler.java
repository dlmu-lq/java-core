package net.im.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import net.im.provide.User;
import net.im.provide.request.LoginRequestPacket;
import net.im.provide.response.LoginResponsePacket;

/**
 * @author liqiang
 * @description 登录请求处理
 * @date 2020/9/10 下午11:25
 */
@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        log.info("服务端开始登录, {}", loginRequestPacket);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if(valid(loginRequestPacket)){
            User user = new User();
            user.setUserName(loginRequestPacket.getUserName());
            SessionUtils.bindSession(user, ctx.channel());
            loginResponsePacket.setSuccess(true);
            log.info("服务端登录成功:{}", user);
        }else{
            log.info("服务端登录失败...");
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("验证失败");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        // TODO 用户名密码验证
//        return !StringUtil.isNullOrEmpty(loginRequestPacket.getImCode()) &&
//                !StringUtil.isNullOrEmpty(loginRequestPacket.getImToken());
        return true;
    }
}
