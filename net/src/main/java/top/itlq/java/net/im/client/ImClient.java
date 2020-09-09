package top.itlq.java.net.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * netty实现的socket客户端
 */
@Slf4j
public class ImClient {
    public static void main(String[] args) throws InterruptedException {
        // 建立引导
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
         connect(bootstrap, "localhost", 8080);
    }

    private static void connect(Bootstrap bootstrap, String host, Integer port){
        ChannelFuture channelFuture = bootstrap.connect(host, port);
        channelFuture.addListener(future -> {
            if(future.isSuccess()){
                log.info("连接成功");
            }else{
                log.info("连接失败");
                // 增加重试逻辑
                connect(bootstrap, host, port);
            }
        });
    }
}
