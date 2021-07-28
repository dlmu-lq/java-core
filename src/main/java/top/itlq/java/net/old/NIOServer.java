package top.itlq.java.net.old;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector acceptSelector = Selector.open();
        Selector iOWriteSelector = Selector.open();
        // 接收连接线程
        new Thread(()->{
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost", 8081));
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
                                SelectionKey readKey = channel.register(iOWriteSelector, SelectionKey.OP_READ);
                                System.out.println("readKey:" + readKey);
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
                    iOWriteSelector.select(500);
                    Set<SelectionKey> selectionKeys = iOWriteSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        try {
                            if (selectionKey.isReadable()) {
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                SocketChannel channel = (SocketChannel) selectionKey.channel();
                                int read = channel.read(byteBuffer);
                                if(read <= 0){
                                    channel.close();
                                }else{
                                    byteBuffer.flip();
                                    String o = Charset.defaultCharset().decode(byteBuffer).toString();
                                    log.info("got message: {}", o);
                                    SelectionKey writeKey = channel.register(iOWriteSelector, SelectionKey.OP_WRITE);
                                    System.out.println("writeKey:" + writeKey);
                                    writeKey.attach(String.format("got your message%s", o));
                                }
                            }
                            if(selectionKey.isWritable()){
                                SocketChannel channel = (SocketChannel)selectionKey.channel();
                                String content = (String)selectionKey.attachment();
                                if(Objects.nonNull(content)){
                                    channel.write(ByteBuffer.wrap(content.getBytes()));
                                    selectionKey.attach(null);
                                }
                            }
                        }finally {
                            // 要移除？ todo
                            iterator.remove();
//                            selectionKey.interestOps(SelectionKey.OP_READ);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
