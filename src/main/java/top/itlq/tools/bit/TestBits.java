package top.itlq.tools.bit;

import org.junit.jupiter.api.Test;

public class TestBits {
    /**
     * silly
     */
    @Test
    void test1(){
        int n = 11;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        do {
            int a = n % (1 << (i + 1)) / (1 << i);
            if(a == 1){
                sb.insert(0, 1);
                n -= (1 << i);
            }else{
                if(n == (1 << i)){
                    sb.insert(0, 1);
                    break;
                }else{
                    sb.insert(0, 0);
                }
            }
            i++;
        } while (n > 0);
        System.out.println(sb.toString());
    }

    /**
     * 找最后一位，移位
     */
    @Test
    void test2(){
        int n = 11;
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, n & 1);
            n = n >>> 1;
        }while (n > 0);
        System.out.println(sb.toString());
    }
}
