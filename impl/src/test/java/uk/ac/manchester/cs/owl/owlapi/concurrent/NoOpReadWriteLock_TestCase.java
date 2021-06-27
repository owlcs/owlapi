package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
class NoOpReadWriteLock_TestCase {

    private NoOpReadWriteLock lock;

    @BeforeEach
    void setUp() {
        lock = new NoOpReadWriteLock();
    }

    @Test
    void shouldNotReturnNullForReadLock() {
        assertThat(lock.readLock(), is(not(nullValue())));
    }

    @Test
    void shouldNotReturnNullForWriteLock() {
        assertThat(lock.writeLock(), is(not(nullValue())));
    }
}
