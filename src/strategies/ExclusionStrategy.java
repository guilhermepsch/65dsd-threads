package strategies;

public interface ExclusionStrategy {
    void acquire();
    void release();
    boolean tryAcquire();
}