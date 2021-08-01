package net.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author liqiang
 * @description
 * @date 2020/9/19 上午9:49
 */
public class AIOServer {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Object o) {
                // 简单处理
                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    Future<Integer> read = asynchronousSocketChannel.read(byteBuffer);
                    read.get();
                    byteBuffer.flip();
                    Future<Integer> write = asynchronousSocketChannel.write(byteBuffer);
                    // safe?
                    byteBuffer.flip();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable throwable, Object o) {

            }
        });
        serverSocketChannel.bind(new InetSocketAddress(8082));
    }
}
