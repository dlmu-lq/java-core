package top.itlq.thinking.java.concurrency_21.threadBasic;

/**
 * 调用运行 Runnable
 */
public class MainThread {
    public static void main(String...args){
        LiftOff launch = new LiftOff();
        launch.run();
    }
}
