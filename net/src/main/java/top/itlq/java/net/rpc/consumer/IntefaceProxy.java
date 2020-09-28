package top.itlq.java.net.rpc.consumer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import top.itlq.java.net.rpc.api.InvokerProtocol;

import javax.xml.ws.Holder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liqiang
 * @description
 * @date 2020/9/22 上午8:36
 */
public class IntefaceProxy {
    public static <T> T getProxy(Class<T> type){
        return (T) Proxy.newProxyInstance(IntefaceProxy.class.getClassLoader(),
                new Class[]{type}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        NioEventLoopGroup group = new NioEventLoopGroup();
                        Holder holder = new Holder();
                        ChannelFuture connect = new Bootstrap()
                                .group(group)
                                .channel(NioSocketChannel.class)
                                .option(ChannelOption.TCP_NODELAY, true)
                                .handler(new ChannelInitializer<Channel>() {
                                    @Override
                                    protected void initChannel(Channel ch) throws Exception {
                                        ch.pipeline().addLast(new LengthFieldPrepender(4));
                                        ch.pipeline().addLast(new ObjectEncoder());
                                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                                            @Override
                                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                                super.write(ctx, msg, promise);
                                            }
                                        });
                                        ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                        ch.pipeline().addLast(new SimpleChannelInboundHandler<Object>() {

                                            @Override
                                            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                holder.value = msg;
                                            }
                                        });
//                                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                                            // 连接建立完成后发送数据
//                                            @Override
//                                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                                InvokerProtocol invokerProtocol = new InvokerProtocol();
//                                                invokerProtocol.setClazzName(type.getSimpleName());
//                                                invokerProtocol.setMethodName(method.getName());
//                                                invokerProtocol.setParamTypes(method.getParameterTypes());
//                                                invokerProtocol.setParamValues(args);
//                                                // todo 与 ch.writeAndFlush 不同点
//                                                ctx.writeAndFlush(invokerProtocol);
//                                            }
//                                        });
                                    }
                                })
                                .connect("127.0.0.1", 8080).sync();

                        InvokerProtocol invokerProtocol = new InvokerProtocol();
                        invokerProtocol.setClazzName(type.getSimpleName());
                        invokerProtocol.setMethodName(method.getName());
                        invokerProtocol.setParamTypes(method.getParameterTypes());
                        invokerProtocol.setParamValues(args);
                        // todo 与 ch.writeAndFlush 不同点
                        connect.channel().writeAndFlush(invokerProtocol).sync();

                        // todo 生命周期
                        connect.channel().closeFuture().sync();

                        return holder.value;
                    }
                });
    }
}
