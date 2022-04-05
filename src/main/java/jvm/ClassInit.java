package jvm;


import java.util.concurrent.TimeUnit;

/**
 * @author liqiang04
 * @description TODO
 * @date 2021/7/28 12:50 下午
 */
public class ClassInit {

    private String oa;

    private String ob;

    private Integer oc;

    private Integer od;

    private static ClassInit classInit;


    private static final String b = new String("测试b");

    private static final String a = "测试a";

    private static final int c = 22;

    private static final Integer d = 22;
    static {
        classInit = new ClassInit();
    }

    public ClassInit() {
        oa = a;
        ob = b;
        oc = c;
        od = d;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(classInit.oa);
        System.out.println(classInit.ob);
        System.out.println(classInit.oc);
        System.out.println(classInit.od);
        TimeUnit.HOURS.sleep(1);
    }

}
