package concurrent.share;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(TestReentrantLock.class);

    private static final ReentrantLock ROOM = new ReentrantLock();
    private static final Condition hotPotCondition = ROOM.newCondition();
    private static final Condition sleepCondition = ROOM.newCondition();
    private static volatile boolean hotPotReady = false;
    private static volatile boolean sleepWell = false;

    public static void main(String[] args) {
        new Thread(()->{
            ROOM.lock();
            try{
                while (!hotPotReady){
                    log.info("没吃火锅，不工作...");
                    try {
                        hotPotCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                hotPotReady = false;
                log.info("吃火锅...");
                log.info("开始工作...");
            }finally {
                ROOM.unlock();
            }
        }, "小梁").start();

        new Thread(()->{
            ROOM.lock();
            try {
                while (!sleepWell){
                    log.info("没睡好，睡觉...");
                    try {
                        sleepCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                ROOM.unlock();
            }
            log.info("睡醒了...");
            log.info("开始工作...");
        }, "小李").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ROOM.lock();
        try {
            hotPotReady = true;
            hotPotCondition.signal();
        }finally {
            ROOM.unlock();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ROOM.lock();
        try {
            sleepWell = true;
            sleepCondition.signal();
        }finally {
            ROOM.unlock();
        }
    }
}
