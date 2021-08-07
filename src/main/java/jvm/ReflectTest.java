package jvm;

import java.lang.reflect.Method;

public class ReflectTest {
    public static void target(int i) {
        // 空方法
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("jvm.ReflectTest");
        Method method = klass.getMethod("target", int.class);

        // 为什么循环外创建数组，更慢
        Object[] arg = new Object[1]; // 在循环外构造参数数组
        arg[0] = 127;

        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }

            method.invoke(null, 1);
        }
    }
}