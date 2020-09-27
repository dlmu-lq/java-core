package top.itlq.java.net.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import top.itlq.java.net.rpc.api.InvokerProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:36
 */
public class ObjectProxy {
    public static <T> T getProxy(Class<T> type){
        return (T) Proxy.newProxyInstance(ObjectProxy.class.getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        NioEventLoopGroup group = new NioEventLoopGroup();

                        new Bootstrap()
                                .group(group)
                                .channel(NioServerSocketChannel.class)
                                .handler(new ChannelInitializer<Channel>() {
                                    @Override
                                    protected void initChannel(Channel ch) throws Exception {
                                        ch.pipeline().addLast(new LengthFieldPrepender(4));
                                        ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {
                                            @Override
                                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                // todo

                                            }
                                        });
                                        InvokerProtocol invokerProtocol = new InvokerProtocol();
                                        invokerProtocol.setClazz(type);
                                        invokerProtocol.setMethodName(method.getName());
                                        invokerProtocol.setParamTypes(method.getParameterTypes());
                                        invokerProtocol.setParamValues(args);
//                                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
//                                            @Override
//                                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//                                                ctx.writeAndFlush(invokerProtocol);
//                                            }
//                                        });
                                        ch.writeAndFlush(invokerProtocol);
                                    }
                                })
                                .connect(new InetSocketAddress(8080));
                        return null;
                    }
                });
    }
}
