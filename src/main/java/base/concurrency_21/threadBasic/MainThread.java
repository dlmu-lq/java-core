package base.concurrency_21.threadBasic;

/**
 * 调用运行 Runnable
 */
public class MainThread {
    public static void main(String...args){
        LiftOff launch = new LiftOff();
        launch.run();
    }
}
