package concurrent.thread;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author liqiang
 * @description
 * @date 2022/3/27 18:45
 */
public class ThreadState {

    public static void main(String[] args) throws InterruptedException, IOException {

        File file = new File(ThreadState.class.getClassLoader().getResource("").getPath() + "test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        Thread thread = new Thread(() -> {
            try {
//                TimeUnit.SECONDS.sleep(100);
//                File file = new File(ThreadState.class.getClassLoader().getResource("/test.txt").getPath());
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
                FileOutputStream outputStream = new FileOutputStream(file);

                outputStream.write(new byte[1024 * 1024 * 100]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread.start();
        while (true) {
            TimeUnit.MILLISECONDS.sleep(10);
            System.out.println(thread.getState());
        }
    }

}
