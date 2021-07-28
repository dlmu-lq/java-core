package top.itlq.thinking.java.chapter08_reuse;

import java.math.BigDecimal;
import java.util.Map;

public class Sub extends Super{
//    public int test(int a){ // 编译失败
    public void test(int a){
    }

    public static void main(String[] args) {
        int i = 12;
        i += i -= i *= i;
        System.out.println(i);

        System.out.println(Character.isDigit('A'));

        // 先根据长度判断范围，再用大于他的进行转换

//        System.out.println(Long.parseLong("111111111111111111111111111111111111111111111111111111"));

        // 不知道长度范围，可用BigDecimal
        System.out.println(new BigDecimal("111111111111111111111111111111111111111111111111111111").compareTo(new BigDecimal(Short.MAX_VALUE)));

//        Map.of()
    }
}
