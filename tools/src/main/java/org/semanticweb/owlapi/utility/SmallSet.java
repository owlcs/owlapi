package org.semanticweb.owlapi.utility;

import static org.semanticweb.owlapi.utilities.OWLAPIPreconditions.checkNotNull;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nullable;

/**
 * This class provides a compact implementation of a very small sets - less than or equal to three
 * elements
 *
 * @param <T> set element type
 */
public class SmallSet<T> extends AbstractSet<T> {

    @Nullable
    T element1;
    @Nullable
    T element2;
    @Nullable
    T element3;

    /**
     * @param collection collection to copy
     */
    public SmallSet(Collection<T> collection) {
        if (collection.size() > 3) {
            throw new IllegalArgumentException(
                "Trying to create a small set with too many elements - max 3, requested: "
                    + collection.size());
        }
        for (T t : collection) {
            add(t);
        }
    }

    @Override
    public boolean add(@Nullable T t) {
        checkNotNull(t, "SmallSet cannot store null values");
        if (contains(t)) {
            return false;
        }
        if (element1 == null) {
            element1 = t;
            return true;
        } else if (element2 == null) {
            element2 = t;
            return true;
        } else if (element3 == null) {
            element3 = t;
            return true;
        } else {
            throw new IllegalStateException("cannot store more than 3 elements in a small set");
        }
    }

    @Override
    public boolean remove(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        int oHash = o.hashCode();
        if (checkMatch(o, oHash, element1)) {
            element1 = null;
            return true;
        } else if (checkMatch(o, oHash, element2)) {
            element2 = null;
            return true;
        } else if (checkMatch(o, oHash, element3)) {
            element3 = null;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        int oHash = o.hashCode();
        return checkMatch(o, oHash, element1) || checkMatch(o, oHash, element2)
            || checkMatch(o, oHash, element3);
    }

    protected boolean checkMatch(Object o, int oHash, @Nullable T element) {
        return element != null && oHash == element.hashCode() && o.equals(element);
    }

    @Override
    public Iterator<T> iterator() {
        return stream().iterator();
    }

    @Override
    public Stream<T> stream() {
        return Stream.of(element1, element2, element3).filter(Objects::nonNull);
    }

    @Override
    public int size() {
        int count = 0;
        if (element1 != null) {
            count++;
        }
        if (element2 != null) {
            count++;
        }
        if (element3 != null) {
            count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("#<SmallSet: %s,%s,%s>", element1, element2, element3);
    }
}
