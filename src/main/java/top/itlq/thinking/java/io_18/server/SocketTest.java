package top.itlq.thinking.java.io_18.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

//        socket.getOutputStream().write("test".getBytes());
//        socket.close();

        Scanner scanner = new Scanner(System.in);
        while (true){
            String str = scanner.next();
            socket.getOutputStream().write(str.getBytes());
        }
    }
}
