package org.semanticweb.owlapi.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

/**
 * A collection that is sorted by HasPriority annotation on its members
 * 
 * @author ignazio
 * @param <T>
 *        type of the collection
 */
public class PriorityCollection<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 40000L;
    private List<T> delegate = new ArrayList<T>();

    /**
     * @param c
     *        collection of elements to set. Existing elements will be removed,
     *        and the priority collection will be sorted by HasPriority.
     */
    public void set(Collection<T> c) {
        clear();
        delegate.addAll(c);
        sort();
    }

    private void sort() {
        Collections.sort(delegate, new HasPriorityComparator<T>());
    }

    public void add(T... c) {
        for (T t : c) {
            delegate.add(0, t);
        }
        sort();
    }

    public void add(Collection<T> c) {
        for (T t : c) {
            delegate.add(t);
        }
        sort();
    }

    public void remove(T... c) {
        for (T t : c) {
            delegate.remove(t);
        }
    }

    public void clear() {
        delegate.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.unmodifiableIterator(delegate.iterator());
    }
}
