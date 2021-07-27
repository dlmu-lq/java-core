package top.itlq.java.concurrent.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestPool {

    private static final Logger log = LoggerFactory.getLogger(TestPool.class);

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1, (queue, task)->{
            // 1、死等
//            queue.put(task);
            // 2、
//            queue.offer(task, 3, TimeUnit.SECONDS);
            // 3、放弃
//            log.info("放弃执行");
            // 4 抛出异常
//            throw new RuntimeException("任务执行失败: " + task);
            // 5 主线程自己执行
            task.run();
        });
        for(int i=0;i<4;i++){
            final int j = i;
            threadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("" + j);
            });
        }
    }
}

// 线程池
class ThreadPool{

    private static final Logger log = LoggerFactory.getLogger(ThreadPool.class);

    // 任务队列
    private BlockingQueue<Runnable> blockingQueue;

    // 核心线程数量；
    private int corePoolSize;

    // 线程容器
    private HashSet<Worker> workers = new HashSet<>();

    // 获取任务超时时间
    private long timeout;

    // 超时时间单位
    private TimeUnit timeUnit;

    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int corePoolSize, long timeout, TimeUnit timeUnit, int taskQueueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.corePoolSize = corePoolSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = new BlockingQueue<>(taskQueueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        // 当线程数数没超过 coreSize 时，直接交给workers
        synchronized (workers){
            if(workers.size() < corePoolSize){
                Worker worker = new Worker(task);
                log.info("添加worker: " + worker + ";任务: " + task);
                workers.add(worker);
                worker.start();
            }else{
                // 1、队列满之后一直等
                // 2、带超时等待
                // 3、放弃任务执行
                // 4、抛出异常
                // 5、让调用者自己执行任务；
//                blockingQueue.put(task);
                blockingQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread{

        private Runnable task;

        public Worker(Runnable target) {
            task = target;
        }

        @Override
        public void run() {
            // 执行任务
            // 1） task不为空，执行
            // 2）执行完毕，从任务队列中获取并执行
//            while (task != null || (task = blockingQueue.take()) != null){
            while (task != null || (task = blockingQueue.poll(timeout, timeUnit)) != null){
                try {
                    log.info("执行任务: " + task);
                    task.run();
                }finally {
                    // 执行完毕，task需要结束，赋值为null
                    task = null;
                }
            }
            synchronized (workers){
                workers.remove(this);
                log.info("移除worker: " + this);
            }
        }
    }

}

@FunctionalInterface
interface RejectPolicy<T>{
    void reject(BlockingQueue<T> queue, T task);
}

// 任务队列
class BlockingQueue<T>{

    private static final Logger log = LoggerFactory.getLogger(BlockingQueue.class);

    // 保存容器，可用ArrayDeque
    private Deque<T> deque = new ArrayDeque<>();

    // 队列容量
    private int capacity;

    // 保护容器并发访问的锁；
    private ReentrantLock lock = new ReentrantLock();

    // 锁的条件变量，队列为空时，用于await和signal
    private Condition emtpyCondition = lock.newCondition();

    // 锁的条件变量，队列满时，用于await和signal
    private Condition fullCondition = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 从队列中取任务
    public T take(){
        lock.lock();
        try {
            // 循环防止虚假唤醒
            while(deque.isEmpty()){
                try {
                    emtpyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T re = deque.removeFirst();
            fullCondition.signal();
            return re;
        }finally {
            lock.unlock();
        }
    }

    // 从对列中取任务，带超时；
    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            // 循环防止虚假唤醒
            while(deque.isEmpty()){
                if(nanos <= 0){
                    return null;
                }
                try {
                    nanos = emtpyCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T re = deque.removeFirst();
            fullCondition.signal();
            return re;
        }finally {
            lock.unlock();
        }
    }

    // 向队列放任务
    public void put(T t){
        lock.lock();
        try {
            while (deque.size() >= capacity){
                try {
                    log.info("任务等待进入队列: " + t);
                    fullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("任务进入队列: " + t);
            deque.add(t);
            emtpyCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    // 向队列添加任务， 带超时
    public boolean offer(T t, long timeout, TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while (deque.size() >= capacity){
                if(nanos <= 0){
                    return false;
                }
                try {
                    log.info("任务等待进入队列: " + t);
                    nanos = fullCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.info("任务进入队列: " + t);
            deque.add(t);
            emtpyCondition.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T task){
        lock.lock();
        try {
            if(deque.size() == capacity){
                rejectPolicy.reject(this, task);
            }else{
                log.info("任务进入队列: " + task);
                deque.add(task);
                emtpyCondition.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}