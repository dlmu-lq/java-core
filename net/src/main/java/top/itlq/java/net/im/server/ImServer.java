package top.itlq.java.net.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * netty使用nio实现的server
 */
@Slf4j
public class ImServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        log.info("服务端建立新连接...");
//                        ch.pipeline().addLast(new StringDecoder());
//                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                log.info("got message: {}", msg);
//                            }
//                        });
                        ch.pipeline().addLast(new ServerHandler());
                    }
                });

        bind(serverBootstrap, 8080);

    }

    private static void bind(ServerBootstrap serverBootstrap, int port){
        ChannelFuture bindFuture = serverBootstrap.bind(port);
        bindFuture.addListener(future -> {
            if(future.isSuccess()){
                log.info("服务端启动成功，端口：{}", port);
            }else{
                log.info("服务端启动失败，端口：{}", port);
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
