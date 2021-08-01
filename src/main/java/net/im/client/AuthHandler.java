package net.im.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import net.im.provide.LoginUtils;

/**
 * @author liqiang04
 * @description 校验登录
 * @date 2020/9/13 11:17 上午
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(LoginUtils.hasLogin(ctx.channel())){
            log.info("已登录，移除校验逻辑");
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }else{
            log.info("校验未登录，关闭连接");
            ctx.channel().close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("authHandler removed");
        super.handlerRemoved(ctx);
    }
}
