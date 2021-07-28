package top.itlq.java.concurrent.thread.local;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 关于ThreadLocal被回收的问题
 * key创建为弱引用，使用弱引用是为了在外部threadLocal设为null后能够正常回收
 * 而value不能使用弱引用解决，只能remove
 */
@Slf4j
public class ThreadLocalTest {
//    @Test
//    void test1() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
//        ThreadLocal<Fruit> threadLocal = ThreadLocal.withInitial(()->new Fruit("黄桃"));
//        log.info(String.valueOf(threadLocal.get()));
//        Object threadLocals = getObjectFromDeclaredField(Thread.currentThread(), "threadLocals");
//        Object table = getObjectFromDeclaredField(threadLocals, "table");
//        log.info(String.valueOf(Arrays.toString((Object[]) table)));
//        TimeUnit.SECONDS.sleep(1);
//        byte[] a = new byte[1024 * 1024 * 2];
//        System.gc();
//    }

    @Test
    void testWeakReference() throws InterruptedException {
        WeakReference<Fruit> weakReference = new WeakReference<>(new Fruit("peach"));
        log.info(String.valueOf(weakReference.get()));
        TimeUnit.SECONDS.sleep(1);
        byte[] a = new byte[1024 * 1024 * 2];
        System.gc();
        log.info(String.valueOf(weakReference.get()));
    }

    Object getObjectFromDeclaredField(Object o, String str) throws IllegalAccessException, NoSuchFieldException {
        Field field1 = o.getClass().getDeclaredField(str);
        field1.setAccessible(true);
        return field1.get(o);
    }
}
