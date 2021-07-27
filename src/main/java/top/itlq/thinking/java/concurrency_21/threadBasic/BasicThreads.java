package top.itlq.thinking.java.concurrency_21.threadBasic;

/**
 * 通过Thread创建线程执行任务
 */
public class BasicThreads {
    public static void main(String...args){
        for(int i=0;i<5;i++){
            new Thread(new LiftOff()).start();
        }
        System.out.println("main thread");
    }
}
