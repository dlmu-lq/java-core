package concurrent.mode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交替执行输出，此处使用ReentrantLock的多个条件变量执行，
 * 同样可以使用用对象锁的wait notify执行
 * 同样可以使用park unpark将下一个开启的线程传入先park当前执行；
 */
public class TestExecuteTurn {
    public static void main(String[] args) {
        Await await = new Await(5);
        Condition condition1 = await.newCondition();
        Condition condition2 = await.newCondition();
        Condition condition3 = await.newCondition();
        new Thread(()->{
            await.print("a", condition1, condition2);
        }).start();
        new Thread(()->{
            await.print("b", condition2, condition3);
        }).start();
        new Thread(()->{
            await.print("c", condition3, condition1);
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        await.lock();
        try {
            condition1.signal();
        }finally {
            await.unlock();
        }
    }
}

class Await extends ReentrantLock{

    private int loopNum;

    public Await(int loopNum){
        this.loopNum = loopNum;
    }

    public void print(String str, Condition condition, Condition nextCondition){
        for(int i=0;i<loopNum;i++){
            lock();
            try {
                try {
                    condition.await();
                    System.out.print(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nextCondition.signal();
            }finally {
                unlock();
            }
        }
    }
}
