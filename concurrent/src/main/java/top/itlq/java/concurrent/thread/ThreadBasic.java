package top.itlq.java.concurrent.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程的方法
 */
@Slf4j
public class ThreadBasic {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        log.info("main start");
        FutureTask<String> task = new FutureTask<>(()->{
            TimeUnit.SECONDS.sleep(1);
            log.info("task return");
            return "test";
        });
        new Thread(task,  "t1").start();
        // 如果没用线程执行，就去获取，直接阻塞
        log.info(task.get());
    }
}
