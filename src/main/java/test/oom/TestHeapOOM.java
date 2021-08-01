package test.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试堆溢出
 * VM 参数：-Xms20m -Xmx20m
 */
public class TestHeapOOM {
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        while (true){
            list.add(0L);
        }
    }
}
