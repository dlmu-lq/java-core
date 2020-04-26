package top.itlq.thinking.java.io_18.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerNIO1 {

    static List<SocketChannel> socketChannels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);
        while (true){
            socketChannels.forEach(socketChannel -> {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                try {
                    if(socketChannel.read(byteBuffer) > 0){
                        byteBuffer.flip();
                        System.out.println("got message: " + new String(byteBuffer.array()));
                        byteBuffer.flip();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            SocketChannel serverSocket = serverSocketChannel.accept();
            if(Objects.nonNull(serverSocket)){
                System.out.println("connect success");
                serverSocket.configureBlocking(false);
                socketChannels.add(serverSocket);
                System.out.println("current socketChannelSize: " + socketChannels.size());
            }
        }
    }
}
