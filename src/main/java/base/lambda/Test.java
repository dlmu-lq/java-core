package base.lambda;

import java.util.Arrays;

/**
 * @author liqiang
 * @description
 * @date 2022/4/4 12:38
 */
public class Test {
    public static void main(String[] args) {
        Arrays.stream(new int[]{1, 2, 3})
                .map(i -> i + 1)
                .forEach(System.out::println);
    }

    public void run() {

    }
}
