package strategies;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorExclusionStrategy implements ExclusionStrategy {
    private final Lock lock;

    public MonitorExclusionStrategy() {
        lock = new ReentrantLock();
    }

    @Override
    public void acquire() {
        lock.lock();
    }

    @Override
    public void release() {
        lock.unlock();
    }

    @Override
    public boolean tryAcquire() {
        try {
            return lock.tryLock(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}