package top.itlq.java.concurrent.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TestAQS {

    private static final Logger log = LoggerFactory.getLogger(TestAQS.class);

    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        new Thread(()->{
            myLock.lock();
            // 不可重入
//            myLock.lock();
            try {
                log.info("locking...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                log.info("unlocking...");
                myLock.unlock();
            }
        }, "t1").start();

        new Thread(()->{
            myLock.lock();
            try {
                log.info("locking...");
            }finally {
                log.info("unlocking...");
                myLock.unlock();
            }
        }, "t2").start();
    }
}

// 不可重入锁
class MyLock implements Lock {

    // 独占锁 同步器类
    class MySync extends AbstractQueuedSynchronizer{

        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)){
                // 加上了锁，并设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition(){
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();


    // 加锁，不成功进入等待
    @Override
    public void lock() {
        sync.acquire(1);
    }

    // 加锁，可打断
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    // 尝试加锁（只一次）
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    // 带超时
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    // 解锁
    @Override
    public void unlock() {
        sync.release(1);
    }

    // 条件变量
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
