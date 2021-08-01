package test;

/**
 * VM 参数：-Xmn10m -Xms20m -Xmx20m -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1
 */
public class TextMaxTenuring {
    private static int BYTE_NUM_1M = 1024 * 1024;
    public static void main(String[] args) {
        byte[] a = new byte[BYTE_NUM_1M / 4];
        byte[] b = new byte[4 * BYTE_NUM_1M];
        byte[] c = new byte[4 * BYTE_NUM_1M];
        c = null;
        c = new byte[4 * BYTE_NUM_1M];
    }
}
