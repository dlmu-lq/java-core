package net.old;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NIOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(()->{
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
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
        new Thread(()->{
            SocketChannel socketChannel = null;
            try {
                socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
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
    }
}
