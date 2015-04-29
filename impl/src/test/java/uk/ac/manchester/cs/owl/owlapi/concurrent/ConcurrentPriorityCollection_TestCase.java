package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.mockito.Mockito.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.PriorityCollectionSorting;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("javadoc")
public class ConcurrentPriorityCollection_TestCase<T extends Serializable> {

    private ConcurrentPriorityCollection<T> collection;

    @Mock
    private ReadWriteLock readWriteLock;

    @Mock
    private Lock readLock, writeLock;

    @Mock
    private T element;

    private PriorityCollectionSorting hasOntologyLoaderConfiguration = PriorityCollectionSorting.NEVER;

    private Iterable<T> iterable;


    @Before
    public void setUp() {
        when(readWriteLock.readLock()).thenReturn(readLock);
        when(readWriteLock.writeLock()).thenReturn(writeLock);
        iterable = Arrays.asList(element);
        collection = new ConcurrentPriorityCollection<>(readWriteLock, hasOntologyLoaderConfiguration);
    }

    @Test
    public void shouldCall_isEmpty_WithReadLock() {
        collection.isEmpty();
        InOrder inOrder = inOrder(readLock, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldCall_getByMimeType_WithReadLock() {
        collection.getByMIMEType("MT");
        InOrder inOrder = inOrder(readLock, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldCall_size_WithReadLock() {
        collection.size();
        InOrder inOrder = inOrder(readLock, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldCall_clear_WithWriteLock() {
        collection.clear();
        InOrder inOrder = inOrder(writeLock, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldCall_add_WithWriteLock() {
        collection.add(element);
        InOrder inOrder = inOrder(writeLock, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldCall_add_iterable_WithWriteLock() {
        collection.add(iterable);
        InOrder inOrder = inOrder(writeLock, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldCall_set_iterable_WithWriteLock() {
        collection.set(iterable);
        verify(writeLock, atLeastOnce()).lock();
        verify(writeLock, atLeastOnce()).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldCall_remove_iterable_WithWriteLock() {
        collection.remove(element);
        InOrder inOrder = inOrder(writeLock, writeLock);
        inOrder.verify(writeLock).lock();
        inOrder.verify(writeLock).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldCall_iterator_WithReadLock() {
        collection.iterator();
        InOrder inOrder = inOrder(readLock, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }
}
