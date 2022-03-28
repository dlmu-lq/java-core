package concurrent.juc;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

/**
 * @author liqiang04
 * @description TODO
 * @date 2022/3/25 11:42 上午
 */
public class TestCompletableFuture2 {

    public static void main(String[] args) {
        CompletableFuture<String> f = CompletableFuture.supplyAsync(() -> "test");
//        f.cancel(true);  // Line 8.
        f.whenComplete((o, throwable) -> {
            System.out.println("whenComplete: o: " + o);
            System.out.println("whenComplete: throwable: " + throwable);
        });
        try {
            f.complete("result");
            f.join();
        } catch (CancellationException e) {
            System.out.println("CancellationException was thrown at call to f.join()");
            e.printStackTrace(System.out);
        }
    }

}
