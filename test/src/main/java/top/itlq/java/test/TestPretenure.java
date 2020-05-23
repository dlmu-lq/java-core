package top.itlq.java.test;

/**
 * VM 参数：-Xmn10m -Xms20m -Xmx20m -XX:PretenureSizeThreshold=4194304 -XX:+PrintGCDetails
 * 4M=4194304
 */
public class TestPretenure {
    private static int BYTE_NUM_1M = 1024 * 1024;
    public static void main(String[] args) {
        byte[] a = new byte[5 * BYTE_NUM_1M];
    }
}
