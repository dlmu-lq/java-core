package top.itlq.java.net.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import top.itlq.java.net.rpc.api.InvokerProtocol;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:36
 */
public class RegistryServer {

    private static final String PROVIDER_PACKAGE = "top/itlq/java/net/rpc/provider";
    private static final Map<String, Object> SERVICES = new HashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        RegistryServer registryServer = new RegistryServer();
        registryServer.scan(PROVIDER_PACKAGE);
        registryServer.startServer();
    }

    private void startServer() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ChannelFuture localhost = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        // ObjectEncoder类，如何找到类型的
                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        ch.pipeline().addLast(new ObjectEncoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<InvokerProtocol>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, InvokerProtocol msg) throws Exception {
                                String clazzName = msg.getClazzName();
                                Object o = SERVICES.get(clazzName);
                                Method method = o.getClass().getMethod(msg.getMethodName(), msg.getParamTypes());
                                ctx.writeAndFlush(method.invoke(o, msg.getParamValues())).sync();
                                // 不关闭，客户端不能返回
                                ctx.close();
                            }
                        });
                    }
                })
                .bind(8080).sync();
        localhost.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("CONNECT SUCCESS");
            }
        });
    }

    private void scan(String dir) throws IOException, URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource(dir);
        File file = new File(resource.getFile());
        File[] files = file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                scan(dir + "/" + f.getName());
            }else{
                String clazzName = dir.replace("/", ".") + "." + f.getName().replace(".class", "");
                try {
                    Class<?> clazz = Class.forName(clazzName);
                    Class<?> clazzInterface = clazz.getInterfaces()[0];
                    SERVICES.put(clazzInterface.getSimpleName(), clazz.newInstance());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
