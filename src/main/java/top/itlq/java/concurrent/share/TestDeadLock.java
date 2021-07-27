package top.itlq.java.concurrent.share;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestDeadLock {

    private static final Logger logger = LoggerFactory.getLogger(TestDeadLock.class);

    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (A){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    logger.info(Thread.currentThread().getName() + " 执行操作");
                }
            }
        }, "t1").start();
        new Thread(()->{
            synchronized (B){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    logger.info(Thread.currentThread().getName() + " 执行操作");
                }
            }
        }, "t2").start();
    }
}
