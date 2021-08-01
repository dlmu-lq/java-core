package concurrent.share;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCAS {
    public static void main(String[] args) {
        Account2 account2 = new Account2(10000);
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i=0;i<1000;i++){
            new Thread(()->{
                account2.withDraw(10);
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account2.get());
    }
}

class Account2{

    private AtomicInteger balance = new AtomicInteger();

    public Account2(int balance){
        this.balance.set(balance);
    }

    public int get(){
        return balance.get();
    }

    // CAS 自旋；
    public void withDraw(int amount){
//        while (true){
//            int prev = balance.get();
//            int next = prev - amount;
//            if(balance.compareAndSet(prev, next)){
//                break;
//            }
//        }
        balance.getAndAdd(-1 * amount);
    }
}