package base.concurrency_21.sharedResource;

public abstract class IntGenerator {
    // 原子性，可见性都保证；
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel(){
        canceled = true;
    }

    public boolean isCanceled(){
        return canceled;
    }
}
