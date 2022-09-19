package org.semanticweb.owlapi.impltest.concurrent;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.impl.concurrent.NoOpReadWriteLock;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
class NoOpReadWriteLock_TestCase {

    private final NoOpReadWriteLock lock = new NoOpReadWriteLock();

    @Test
    void shouldNotReturnNullForReadLock() {
        assertNotNull(lock.readLock());
    }

    @Test
    void shouldNotReturnNullForWriteLock() {
        assertNotNull(lock.writeLock());
    }
}
