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
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 建立引导
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        new Thread(()->{
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8081);
            channelFuture.addListener(future -> {
                if(future.isSuccess()){
                    log.info("连接成功");
                }else{
                    log.info("连接失败");
                    // 增加重试逻辑
                }
            });
            Channel channel = channelFuture.channel();
            while (true){
                // 此处必须toString，不然发送的是对象？
                channel.writeAndFlush(LocalDateTime.now().toString());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);

        new Thread(()->{
            Channel channel = bootstrap.connect("localhost", 8080).channel();
            while (true){
                // 此处必须toString，不然发送的是对象？
                channel.writeAndFlush(LocalDateTime.now().toString());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void connect(Bootstrap bootstrap, String host, Integer port){
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
