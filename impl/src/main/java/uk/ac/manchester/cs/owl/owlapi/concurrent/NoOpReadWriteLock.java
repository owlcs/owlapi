package uk.ac.manchester.cs.owl.owlapi.concurrent;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
public class NoOpReadWriteLock implements ReadWriteLock, Serializable {

    private static final NoOpLock NO_OP_LOCK = new NoOpLock();

    @Override
    public Lock readLock() {
        return NO_OP_LOCK;
    }

    @Override
    public Lock writeLock() {
        return NO_OP_LOCK;
    }
}
