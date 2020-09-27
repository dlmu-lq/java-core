package top.itlq.java.net.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.FileUtils;
import sun.nio.ch.IOUtil;
import top.itlq.java.net.tomcat.request.LlServletRequest;
import top.itlq.java.net.tomcat.request.NtServletRequest;
import top.itlq.java.net.tomcat.response.LlServletResponse;
import top.itlq.java.net.tomcat.response.NtServletResponse;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiang
 * @description 简单的tomcat server
 * @date 2020/9/21 下午10:48
 */
@Slf4j
public class TomcatServer {

    private static final String PROPERTIES_CLASSPATH = "web.properties";
    private static final Map<String, Servlet> URL_SERVLET = new HashMap<>();

    public static void main(String[] args) throws IOException {
        TomcatServer tomcatServer = new TomcatServer();
        tomcatServer.loadConfig();
//        tomcatServer.startBioServer();
        tomcatServer.startNettyServer();
    }

    /**
     * 加载web.properties配置
     */
    private void loadConfig(){
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResource(PROPERTIES_CLASSPATH).openStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            br.lines().forEach(line->{
//                String[] split = line.split("=");
//            });
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((s1, s2)->{
                try {
                    Object o = Class.forName((String) s2).newInstance();
                    URL_SERVLET.put((String) s1, (Servlet) o);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startBioServer() throws IOException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        while (true){
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(()->{
                try {
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    LlServletRequest servletRequest = new LlServletRequest(inputStream);
                    LlServletResponse servletResponse = new LlServletResponse(outputStream);
                    Servlet servlet = URL_SERVLET.get(servletRequest.getUrl());
                    if(Objects.nonNull(servlet)) {
                        servlet.doService(servletRequest, servletResponse);
                    }else{
                        log.error("wrong url:{}", servletRequest.getUrl());
                    }

                    outputStream.flush();
                    outputStream.close();

                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void startNettyServer(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new HttpRequestDecoder());
                        ch.pipeline().addLast(new HttpResponseEncoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                NtServletRequest servletRequest = new NtServletRequest(msg);
                                NtServletResponse servletResponse = new NtServletResponse(ctx);

                                Servlet servlet = URL_SERVLET.get(servletRequest.getUrl());
                                if(Objects.nonNull(servlet)) {
                                    servlet.doService(servletRequest, servletResponse);
                                }else{
                                    log.error("wrong url:{}", servletRequest.getUrl());
                                }
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
