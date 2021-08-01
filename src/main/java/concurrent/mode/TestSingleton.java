package concurrent.mode;

public class TestSingleton {

    private TestSingleton(){

    }

    // 此处必须加volatile下面解释
    private static volatile TestSingleton testSingleton;

    public static TestSingleton getInstance(){
        if(testSingleton == null){
            synchronized (TestSingleton.class){
                // synchronized 并不能保证指令不被重排序，它只是通过控制这一段代码同一时间只能被一个线程执行而解决了有序性问题；
                // 这里因为外层还有testSingleton的读操作，导致synchronized内部的代码被重排序后，与另一个线程外部判断null出现交错；
                if(testSingleton == null){
                    testSingleton = new TestSingleton(); // 这句代码在字节码对应四条指令：（序）
                                            // new（创建）；dup（复制）；invokespecial（构造器）；putstatic（赋值）
                                            // 构造器和复制操作可能被重排，导致另一线程在外层拿到了未构造的对象
                                            // volatile 写屏障导致赋值操作前的构造操作不能跑到赋值操作之前，解决了问题；
                }
            }
        }
        return testSingleton;
    }
}
