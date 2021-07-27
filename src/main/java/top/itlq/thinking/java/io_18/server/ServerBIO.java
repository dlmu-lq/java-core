package top.itlq.thinking.java.io_18.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * blocking IO 实现的网络服务
 */
public class ServerBIO {

    public static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true){
            // 阻塞
            System.out.println("等待连接...");
            // 阻塞等待连接
            Socket socket = serverSocket.accept();
            System.out.println("连接成功...");
            // 一个请求连接后，既创建一个线程
            threadPoolExecutor.submit(()->{
                byte[] bytes = new byte[1024];
                try {
                    //没数据时与accept一样阻塞导致线程浪费；
                    System.out.println("等待接收数据...");
                    socket.getInputStream().read(bytes);
                    System.out.println("接收数据成功...");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(new String(bytes));
            });
        }
    }
}
