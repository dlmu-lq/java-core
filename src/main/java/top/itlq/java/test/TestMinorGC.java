package top.itlq.java.test;


/**
 * -Xss 每个线程的栈大小
 * 堆内存
 *      -Xms 初始堆大小(默认物理内存1/64)
 *      -Xmx 最大堆大小(默认物理内存1/4)
 *      -Xmn 新生代大小 推荐为堆的3/8
 *  -XX:PretenureSizeThreshold 对象超过多大直接在年老代分配内存
 *  -XX:+PrintHeapAtGC -> -Xlog:gc+heap=trace 打印GC前后的详细堆栈信息
 *  -XX:+PrintGCDetails 打印GC详情
 *  javac -encoding utf-8 top/itlq/java/test/TestGC.java
 *  java -Xmn10m -Xms50m -Xmx50m -Xlog:gc+heap=trace top.itlq.java.test.TestGC
 *  TODO GC日志没看懂
 */
public class TestMinorGC {

    private static int BYTE_NUM_1M = 1024 * 1024;

    public static void main(String[] args) {
        byte[] a = new byte[BYTE_NUM_1M * 2],
                b = new byte[BYTE_NUM_1M * 2],
                c = new byte[BYTE_NUM_1M * 2],
                d = new byte[BYTE_NUM_1M * 4];
    }
}
