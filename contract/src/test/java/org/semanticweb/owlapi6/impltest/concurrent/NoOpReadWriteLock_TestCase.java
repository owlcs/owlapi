package org.semanticweb.owlapi6.impltest.concurrent;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.impl.concurrent.NoOpReadWriteLock;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
public class NoOpReadWriteLock_TestCase {

    private NoOpReadWriteLock lock;

    @Before
    public void setUp() {
        lock = new NoOpReadWriteLock();
    }

    @Test
    public void shouldNotReturnNullForReadLock() {
        assertNotNull(lock.readLock());
    }

    @Test
    public void shouldNotReturnNullForWriteLock() {
        assertNotNull(lock.writeLock());
    }
}
