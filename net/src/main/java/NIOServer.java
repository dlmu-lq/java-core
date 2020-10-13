import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        // 接收连接线程
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            // 此处为阻塞
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    try {
                        SocketChannel channel = ((ServerSocketChannel) selectionKey.channel()).accept();
                        channel.configureBlocking(false);
                        SelectionKey readKey = channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("connect success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    // 涉及系统调用
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(String.format("got message: %s", new String(byteBuffer.array())));
                    // 写，为什么要
//                    SelectionKey writeKey = channel.register(selector, SelectionKey.OP_WRITE);
//                    writeKey.attach(String.format("got your message: %s", new String(byteBuffer.array())).getBytes());
                    // 这里非阻塞写入不一定能够全部写完成
//                    ByteBuffer byteBufferWrite = ByteBuffer.wrap(String.format("got your message: %s", new String(byteBuffer.array())).getBytes());
//                    channel.write(byteBufferWrite);
                }
                else if (selectionKey.isWritable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.wrap((byte[]) selectionKey.attachment());
                    channel.write(byteBuffer);
//                    channel
                }
                // 不移除会循环处理
                iterator.remove();
            }
        }
    }
}
