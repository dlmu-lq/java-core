package jvm;

public class CatchExceptionFinally {
    private int tryBlock;
    private int catchBlock;
    private int finallyBlock;
    private int methodExit;

    public int test() {
        for (int i = 0; i < 100; i++) {
            try {
                tryBlock = 0;
                if (i < 50) {
                    continue;
                } else if (i < 80) {
                    break;
                } else {
                    return 1;
                }
            } catch (Exception e) {
                catchBlock = 1;
                return 2;
            } finally {
                finallyBlock = 2;
                return 3;
            }
        }
        methodExit = 3;
        return 4;
    }

    public static void main(String[] args) {
        System.out.println(new CatchExceptionFinally().test());
    }
}
