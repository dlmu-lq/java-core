package base.concurrency_21;

/**
 * @author liqiang
 * @description
 * @date 2022/4/5 22:16
 */
public class Singleton {

    private volatile Singleton INSTANCE;

    private Singleton(){}

    public Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (this) {
                if (INSTANCE == null) {
                    // 不是在字节码上加个lock，而是在本地代码曾名，即时编译完的代码会加个lock
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }

}
