package base.concurrency_21.threadBasic;

import java.util.concurrent.*;

class ExceptionTask implements Runnable{

    @Override
    public void run() {
        throw new RuntimeException("线程异常");
    }
}

// 线程异常捕获处理器
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("caught in myUncaughtExceptionHandler" + e);
    }
}

class HandlerThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        // 在线程上附着异常捕获处理器，会在线程因未捕获的异常而濒临死亡时被调用
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return thread;
    }
}

public class CaptureUncaughtException {
    public static void main(String[]args){
        // 不能捕获
        try {
            new Thread(new ExceptionTask()).start();
        }catch (Exception e){
            System.out.println("caught in main thread");
        }
        // 可以捕获
        new HandlerThreadFactory().newThread(new ExceptionTask()).start();
        // 线程池
        ExecutorService executorService = Executors.newCachedThreadPool(new HandlerThreadFactory());
        // 可以捕获，如果抛出异常线程会死掉，下次再重新创建？
        executorService.execute(new ExceptionTask());
        // 只能在get结果时捕获，如果不get结果，将导致异常被吞，线程不会死
        Future future = executorService.submit(new ExceptionTask());
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("submit时只能在future.get()中获得异常，不能使用ExceptionHandler");
        }
        executorService.shutdown();
        // 可以设置Thread的静态域，默认未捕获异常处理器，在线程无自己的 UncaughtExceptionHandler 时被调用
//        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }
}
