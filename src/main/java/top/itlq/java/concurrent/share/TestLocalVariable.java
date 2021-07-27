package top.itlq.java.concurrent.share;

import java.util.concurrent.TimeUnit;

/**
 * 局部变量，但其引用的对象脱离了线程的范围，线程不安全；
 */
public class TestLocalVariable {

    public static void main(String[] args) {
        new TestLocalVariable().test1();
    }

    public void test1(){
        Counter counter = new Counter();
        test2(counter);
        test2(counter);
        test2(counter);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.getCount());
    }

    // 如果定义为final/private即可在此类中保证不被覆盖，保证线程安全，String的类 final
    public void test2(Counter counter){
        for(int i=0;i<10000;i++){
            counter.increment();
        }
    }
}

class SubClass extends TestLocalVariable{

    public static void main(String[] args) {
        new SubClass().test1();
    }

    // 子类创建线程使局部变量脱离了调用线程范围；导致线程不安全
    public void test2(final Counter counter) {
        new Thread(()->{
            for(int i=0;i<10000;i++){
                counter.increment();
            }
        }).start();
    }
}

class Counter{
    private int count;

    public int getCount() {
        return count;
    }

    public void increment(){
        count++;
    }

    public void decrement(){
        count--;
    }
}