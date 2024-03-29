package base.concurrency_21.threadBasic;

/**
 * 一个Runnable对象
 */
public class LiftOff implements Runnable{
    private int countDown = 10;
    private static int taskCount = 0;
    private final int taskId = taskCount++;

    @Override
    public void run(){
        while (countDown > 0){
            System.out.println("#" + taskId + " " + countDown--);
            Thread.yield();
        }
    }
}
