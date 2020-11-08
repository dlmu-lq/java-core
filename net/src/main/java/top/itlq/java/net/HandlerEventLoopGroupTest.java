package top.itlq.java.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ThreadFactory;

/**
 * @author liqiang04
 * @description TODO
 * @date 2020/11/4 12:42 下午
 */
public class HandlerEventLoopGroupTest {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup(8);
        new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(8))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(group, new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                                System.out.println(Thread.currentThread());
                                ctx.writeAndFlush("got msg: " + msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
