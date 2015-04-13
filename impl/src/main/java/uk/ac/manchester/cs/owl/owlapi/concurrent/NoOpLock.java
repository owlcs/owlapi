package uk.ac.manchester.cs.owl.owlapi.concurrent;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 13/04/15
 */
class NoOpLock implements Lock, Serializable {

    public static final NoOpCondition NO_OP_CONDITION = new NoOpCondition();

    @Override
    public void lock() {
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return true;
    }

    @Override
    public boolean tryLock(long time, @Nonnull TimeUnit unit) throws InterruptedException {
        return true;
    }

    @Override
    public void unlock() {
    }

    @Override
    @Nonnull
    public Condition newCondition() {
        return NO_OP_CONDITION;
    }

    private static class NoOpCondition implements Condition, Serializable {

        @Override
        public void await() throws InterruptedException {
        }

        @Override
        public void awaitUninterruptibly() {
        }

        @Override
        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            return 0;
        }

        @Override
        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            return true;
        }

        @Override
        public boolean awaitUntil(@Nonnull Date deadline) throws InterruptedException {
            return true;
        }

        @Override
        public void signal() {
        }

        @Override
        public void signalAll() {
        }
    }
}
