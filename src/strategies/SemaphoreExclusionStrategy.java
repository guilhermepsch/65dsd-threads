package strategies;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExclusionStrategy implements ExclusionStrategy {
    private final Semaphore semaphore;

    public SemaphoreExclusionStrategy(int permits) {
        semaphore = new Semaphore(permits);
    }

    @Override
    public void acquire() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void release() {
        semaphore.release();
    }

    @Override
    public boolean tryAcquire() {
        try {
            return semaphore.tryAcquire(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}