package top.itlq.java.net.old;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector acceptSelector = Selector.open();
        Selector readSelector = Selector.open();
        // 接收连接线程
        new Thread(()->{
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
                while (true){
                    // 此处为阻塞
                    acceptSelector.select();
                    Set<SelectionKey> selectionKeys = acceptSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            try {
                                SocketChannel channel = ((ServerSocketChannel) selectionKey.channel()).accept();
                                channel.configureBlocking(false);
                                channel.register(readSelector, SelectionKey.OP_READ);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                // todo 需要移除连接
                                iterator.remove();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

        // 处理读取数据线程
        new Thread(()->{
            while (true){
                try {
                    // 此处应为非阻塞才能感知新连接注册的变化？
                    readSelector.select(500);
                    Set<SelectionKey> selectionKeys = readSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        try {
                            if (selectionKey.isReadable()) {
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                SocketChannel channel = (SocketChannel) selectionKey.channel();
                                channel.read(byteBuffer);
                                byteBuffer.flip();
                                log.info("got message: {}", Charset.defaultCharset().decode(byteBuffer).toString());
                                channel.write(ByteBuffer.wrap("got your message".getBytes()));
                            }
                        }finally {
                            // 要移除？ todo
                            iterator.remove();
                            selectionKey.interestOps(SelectionKey.OP_READ);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
