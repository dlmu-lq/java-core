package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiang
 * @description 不使用select的NIO server
 * @date 2020/10/11 上午9:54
 */
public class NIOSimpleServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 保存客户端连接
        List<SocketChannel> socketChannelList = new ArrayList<>();
        // 开启服务端连接
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);

        while (true){
            TimeUnit.MILLISECONDS.sleep(2000);
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println("connect success");
                socketChannel.configureBlocking(false);
                socketChannelList.add(socketChannel);
            }
            for (SocketChannel socketChannel1 : socketChannelList) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                // 涉及系统调用，且需要不停的调用
                int read = socketChannel1.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();
                    System.out.println(String.format("got message: %s", new String(byteBuffer.array())));
                }
            }
        }
    }
}
