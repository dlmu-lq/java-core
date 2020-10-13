import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统阻塞服务端
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true){
            // 阻塞
            final Socket socket = serverSocket.accept();
//            new Thread(()->{
                try {
                    InputStream inputStream = socket.getInputStream();
                    while (true){
                        byte[] bytes = new byte[1024];
                        // 阻塞
                        if (inputStream.read(bytes) > 0){
                            System.out.println(String.format("got message: %s", new String(bytes)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }).start();
        }
    }
}
