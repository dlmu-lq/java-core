package base.concurrency_21.performance;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多个线程操作对象时，可使用乐观锁；
 * 当模型有性能需求且可以接受一些失败操作时使用
 */
public class FastSimulation39 {
    private static final int GENES_SIZE = 100000;
    private static final int GENE_LENGTH = 30;
    private static final int EVOLUTE_NUMS = 30;
    private static int[][] GENES = new int[GENES_SIZE][GENE_LENGTH];
    private static Random random = new Random();
    private static Lock lock = new ReentrantLock();
    static {
        for(int i=0;i<GENES_SIZE;i++){
            GENES[i] = new int[GENE_LENGTH];
            for(int ii=0;ii<GENE_LENGTH;ii++){
                GENES[i][ii] = random.nextInt(1000);
            }
        }
    }
    static class Evolute implements Runnable{
        @Override
        public void run() {
            int index = random.nextInt(GENES_SIZE);
            int previous = index - 1;
            if(index < 0)
                previous = GENE_LENGTH - 1;
            int next = index + 1;
            if(index >= GENE_LENGTH){
                next = 0;
            }
            lock.lock();
            try{
                for(int i=0;i<GENE_LENGTH;i++){
                    int oldValue = GENES[index][i];
                    int newValue = oldValue + GENES[previous][i] + GENES[next][i];
                    GENES[index][i] = newValue / 3;
                    System.out.println("oldValue: " + oldValue + "; newValue: " + newValue);
                }
            }finally {
                lock.unlock();
            }
        }
    }
    public static void main(String...args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<EVOLUTE_NUMS;i++){
            executorService.execute(new Evolute());
        }
        executorService.shutdown();
    }
}
