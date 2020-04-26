package top.itlq.tools.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncTest {

    /**
     * 默认在主线程订阅，单线程阻塞操作
     */
    @Test
    void test1() {
        Mono.just(1).map(a -> {
            System.out.println(1);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        })
                .subscribe(i -> {
                    System.out.println(Thread.currentThread() + " :" + i);
                });

        Mono.just(2).map(a -> {
            System.out.println(2);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        })
                .subscribe(i -> {
                    System.out.println(Thread.currentThread() + " :" + i);
                });

    }

    /**
     *
     * @throws InterruptedException
     */
    @Test
    void test2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Mono.just(1).map(a -> {
            System.out.println(1);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        })
                .subscribeOn(Schedulers.elastic())
                .subscribe(i -> {
                    System.out.println(Thread.currentThread() + " :" + i);
                    countDownLatch.countDown();
                });

        Mono.just(2).map(a -> {
            System.out.println(2);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        })
                .subscribeOn(Schedulers.elastic())
                .subscribe(i -> {
                    System.out.println(Thread.currentThread() + " :" + i);
                    countDownLatch.countDown();
                });
        countDownLatch.await();

    }

    @Test
    void test3() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Flux.just(1,2)
                .publishOn(Schedulers.elastic())
                .map(a -> {
                    System.out.println(a);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                })
                .subscribeOn(Schedulers.elastic())
                .subscribe(i -> {
                    System.out.println(Thread.currentThread() + " :" + i);
                    countDownLatch.countDown();
                });
        countDownLatch.await();
    }


}
