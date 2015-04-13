package uk.ac.manchester.cs.owl.owlapi.concurrent;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 13/04/15
 */
public class NoOpLock_TestCase {

    private NoOpLock lock;

    @Before
    public void setUp() throws Exception {
        lock = new NoOpLock();
    }

    @Test
    public void shouldNotReturnNullCondition() {
        assertThat(lock.newCondition(), is(not(nullValue())));
    }

    @Test
    public void shouldReturn_true_When_tryLock() {
        assertThat(lock.tryLock(), is(true));
    }

    @Test
    public void shouldReturn_true_When_tryLockWithTimeOut() throws InterruptedException {
        assertThat(lock.tryLock(3, mock(TimeUnit.class)), is(true));
    }
}
