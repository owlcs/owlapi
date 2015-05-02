package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.PriorityCollectionSorting;
import org.semanticweb.owlapi.util.PriorityCollection;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 09/04/15
 * A priority collection that supports concurrent reading and writing through a
 * {@link ReadWriteLock}
 * 
 * @param <T>
 *        type in the collection
 */
public class ConcurrentPriorityCollection<T extends Serializable> extends
    PriorityCollection<T> {

    private final Lock readLock;
    private final Lock writeLock;

    /**
     * Constructs a {@link ConcurrentPriorityCollection} using the specified
     * {@link ReadWriteLock}
     * 
     * @param readWriteLock
     *        The {@link java.util.concurrent.locks.ReadWriteLock} that should
     *        be used for locking.
     * @param sorting
     *        sorting criterion
     */
    public ConcurrentPriorityCollection(@Nonnull ReadWriteLock readWriteLock,
        PriorityCollectionSorting sorting) {
        super(sorting);
        verifyNotNull(readWriteLock);
        this.readLock = readWriteLock.readLock();
        this.writeLock = readWriteLock.writeLock();
    }

    @Override
    public boolean isEmpty() {
        readLock.lock();
        try {
            return super.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int size() {
        readLock.lock();
        try {
            return super.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void set(Iterable<T> c) {
        writeLock.lock();
        try {
            super.set(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add(Iterable<T> c) {
        writeLock.lock();
        try {
            super.add(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void set(T... c) {
        writeLock.lock();
        try {
            super.set(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add(T... c) {
        writeLock.lock();
        try {
            super.add(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void add(T c) {
        writeLock.lock();
        try {
            super.add(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void remove(T... c) {
        writeLock.lock();
        try {
            super.remove(c);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            super.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return copyIterable().iterator();
    }

    @Override
    public PriorityCollection<T> getByMIMEType(@Nonnull String mimeType) {
        readLock.lock();
        try {
            return super.getByMIMEType(mimeType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String toString() {
        readLock.lock();
        try {
            return super.toString();
        } finally {
            readLock.unlock();
        }
    }

    private Iterable<T> copyIterable() {
        readLock.lock();
        try {
            List<T> copy = new ArrayList<>();
            for (Iterator<T> it = super.iterator(); it.hasNext();) {
                T element = it.next();
                copy.add(element);
            }
            return copy;
        } finally {
            readLock.unlock();
        }
    }
}
