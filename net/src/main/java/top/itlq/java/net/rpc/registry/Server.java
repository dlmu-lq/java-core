package top.itlq.java.net.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import top.itlq.java.net.rpc.api.IComputeRpcService;
import top.itlq.java.net.rpc.api.InvokerProtocol;
import top.itlq.java.net.rpc.provider.ComputeRpcServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:36
 */
public class Server {

    private static final String PROVIDER_PACKAGE = "top/itlq/java/net/rpc/provider";
    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.scan(PROVIDER_PACKAGE);
        server.startServer();
    }

    private void startServer(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 4, 4));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<InvokerProtocol>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, InvokerProtocol msg) throws Exception {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080);
    }

    private void scan(String dir) throws IOException {
        URL resource = this.getClass().getClassLoader().getResource(dir);
        SERVICES.put(IComputeRpcService.class, new ComputeRpcServiceImpl());
    }
}
