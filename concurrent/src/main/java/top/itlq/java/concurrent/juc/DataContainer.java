package top.itlq.java.concurrent.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataContainer {

    private static final Logger log = LoggerFactory.getLogger(DataContainer.class);
    private Object data;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    public Object read(){
        log.info("尝试获取读锁");
        readLock.lock();
        try {
            log.info("获取读锁成功");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            readLock.unlock();
            log.info("释放读锁");
        }
        return data;
    }

    public void write(Object data){
        log.info("尝试获取写锁");
        writeLock.lock();
        try {
            log.info("获取写锁成功");
            this.data = data;
        }finally {
            writeLock.unlock();
            log.info("释放写锁");
        }
    }

    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        new Thread(()->{
            dataContainer.read();
        }, "t1").start();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            dataContainer.write(null);
        }, "t2").start();
    }
}
