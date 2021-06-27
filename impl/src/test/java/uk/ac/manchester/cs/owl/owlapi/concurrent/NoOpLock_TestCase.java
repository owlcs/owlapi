package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
class NoOpLock_TestCase {

    private NoOpLock lock;

    @BeforeEach
    void setUp() {
        lock = new NoOpLock();
    }

    @Test
    void shouldNotReturnNullCondition() {
        assertThat(lock.newCondition(), is(not(nullValue())));
    }

    @Test
    void shouldReturn_true_When_tryLock() {
        assertTrue(lock.tryLock() == true);
    }

    @Test
    void shouldReturn_true_When_tryLockWithTimeOut() {
        assertTrue(lock.tryLock(3, TimeUnit.MILLISECONDS) == true);
    }
}
