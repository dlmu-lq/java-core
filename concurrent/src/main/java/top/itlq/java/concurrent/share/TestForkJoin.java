package top.itlq.java.concurrent.share;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class TestForkJoin {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new MyTask(5)));
    }
}

// 1-n 整数和
class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n == 1){
            return 1;
        }
        // 任务拆分，可以拆成多个来执行；
        // jdk8 之后，使用Stream更好；
        MyTask t1 = new MyTask(n - 1);
        // 让一个线程执行
        t1.fork();
        return t1.join() + n;
    }
}