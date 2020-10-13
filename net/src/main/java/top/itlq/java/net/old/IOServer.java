package top.itlq.java.net.old;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统阻塞服务端
 */
@Slf4j
public class IOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true){
            final Socket socket = serverSocket.accept();
            new Thread(()->{
                try {
                    InputStream inputStream = socket.getInputStream();
                    while (true){
                        byte[] bytes = new byte[1024];
                        if (inputStream.read(bytes) > 0){
                            log.info("got message: {}", new String(bytes));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
