package top.itlq.java.net.old;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(()->{
            Socket socket = null;
            try {
                socket = new Socket("localhost", 8080);
                OutputStream outputStream = socket.getOutputStream();
                while (true){
                    outputStream.write(LocalDate.now().toString().getBytes());
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
            Socket socket = null;
            try {
                socket = new Socket("localhost", 8080);
                OutputStream outputStream = socket.getOutputStream();
                while (true){
                    outputStream.write(LocalDate.now().toString().getBytes());
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
