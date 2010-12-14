package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

/*
 * This has the same thread safety as HashSet.  The read operations are thread safe but the write operations 
 * expect to be synchronized by the caller. Only one writer can safely access this class at a time and during the
 * write operation no readers can execute.  As usual the iterator method presents special challenges because it is 
 * unsynchronized.
 */
public abstract class LazySet<E> implements Set<E> {
    private AtomicReference<FutureTask<Set<E>>> referenceToDelegate = new AtomicReference<FutureTask<Set<E>>>();
    private volatile boolean copyGivenOut = false;

    protected abstract Set<E> build();
    
    private Set<E> getDelegate() {
        try {
            FutureTask<Set<E>> getDelegateTask = referenceToDelegate.get();
            return getDelegateTask.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void ensureIsBuilt() {
        FutureTask<Set<E>> task = referenceToDelegate.get();
        if (task == null) {
            FutureTask<Set<E>> myTask = new FutureTask<Set<E>>(new Callable<Set<E>>() {

                public Set<E> call() throws Exception {
                   return build();
                } 
            });
            if (referenceToDelegate.compareAndSet(null, myTask)) {
                myTask.run();
            }
        }
    }
    
    /*
     * Only called on write operations and thus expects that synchronization is done by the indirectly 
     * calling routine.
     */
    private void checkCopyOnWrite() {
        if (copyGivenOut) {
            final Set<E> delegate = getDelegate();
            FutureTask<Set<E>> copyTask = new FutureTask<Set<E>>(new Callable<Set<E>>() {
                public Set<E> call() throws Exception {
                    return new HashSet<E>(delegate);
                }
            });
            referenceToDelegate.set(copyTask);
            copyTask.run();
            copyGivenOut = false;
        }
    }
    
    private boolean isBuilt() {
        return referenceToDelegate.get() != null;
    }
    
    
    /* *********************************************************
     * Readers - these operations are thread-safe
     */

    public boolean contains(Object o) {
        ensureIsBuilt();
        return getDelegate().contains(o);
    }

    public boolean containsAll(Collection<?> c) {
        ensureIsBuilt();
        return getDelegate().containsAll(c);
    }

    public boolean isEmpty() {
        ensureIsBuilt();
        return getDelegate().isEmpty();
    }

    public Iterator<E> iterator() {
        ensureIsBuilt();
        return getDelegate().iterator();
    }

    public int size() {
        ensureIsBuilt();
        return getDelegate().size();
    }

    public Object[] toArray() {
        ensureIsBuilt();
        return getDelegate().toArray();
    }

    public <T> T[] toArray(T[] a) {
        ensureIsBuilt();
        return getDelegate().toArray(a);
    }
    
    public Set<E> clone() {
        ensureIsBuilt();
        copyGivenOut = true;
        return Collections.unmodifiableSet(getDelegate());
    }


    public boolean equals(Object o) {
        ensureIsBuilt();
        return getDelegate().equals(o);
    }

    public int hashCode() {
        ensureIsBuilt();
        return getDelegate().hashCode();
    }


    /* *********************************************************
     * Writer Operations - this code must be protected by an external lock.
     *                     only one writer at a time is premitted and while a
     *                     write operation is in progress there can be no  readers.
     */

    public boolean add(E e) {
        ensureIsBuilt();
        checkCopyOnWrite();
        return getDelegate().add(e);
    }

    public void lazyAdd(E e) {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().add(e);
        }
    }

    public boolean addAll(Collection<? extends E> c) {
        ensureIsBuilt();
        checkCopyOnWrite();
        return getDelegate().addAll(c);
    }

    public void lazyAddAll(Collection<? extends E> c) {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().addAll(c);
        }
    }


    public void clear() {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().clear();
        }
    }

    public boolean remove(Object o) {
        ensureIsBuilt();
        checkCopyOnWrite();
        return getDelegate().remove(o);
    }

    public void LazyRemove(Object o) {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().remove(o);
        }
    }


    public boolean removeAll(Collection<?> c) {
        ensureIsBuilt();
        checkCopyOnWrite();
        return getDelegate().removeAll(c);
    }

    public void LazyRemoveAll(Collection<?> c) {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().removeAll(c);
        }
    }


    public boolean retainAll(Collection<?> c) {
        ensureIsBuilt();
        checkCopyOnWrite();
        return getDelegate().retainAll(c);
    }

    public void LazyRetainAll(Collection<?> c) {
        if (isBuilt()) {
            checkCopyOnWrite();
            getDelegate().retainAll(c);
        }
    }



}
