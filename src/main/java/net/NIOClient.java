package net;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();
        new Thread(()->{
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8081));
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                while (true){
                    socketChannel.write(ByteBuffer.wrap(LocalDate.now().toString().getBytes()));
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
//        new Thread(()->{
//            SocketChannel socketChannel = null;
//            try {
//                socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
//                while (true){
//                    socketChannel.write(ByteBuffer.wrap(LocalDate.now().toString().getBytes()));
//                    TimeUnit.SECONDS.sleep(2);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
        new Thread(()->{
            while (true){
                try {
                    int select = selector.select();
                    System.out.println("selector.select():" + select);
                    selector.selectedKeys().forEach(selectionKey -> {
                        if(selectionKey.isReadable()){
                            SocketChannel channel = (SocketChannel)selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            try {
                                channel.read(byteBuffer);
                                byteBuffer.flip();
                                System.out.printf("got message:%s\n", Charset.defaultCharset().decode(byteBuffer).toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
