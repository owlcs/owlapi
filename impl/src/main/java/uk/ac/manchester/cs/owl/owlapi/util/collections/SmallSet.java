package uk.ac.manchester.cs.owl.owlapi.util.collections;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class provides a compact implementation of a very small sets - less than
 * or equal to three elements
 * 
 * @param <T>
 *        set element type
 */
public class SmallSet<T> extends AbstractSet<T> {

    T element1;
    T element2;
    T element3;

    /**
     * default constructor
     */
    public SmallSet() {}

    /**
     * @param collection
     *        collection to copy
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
    public boolean add(T t) {
        if (t == null) {
            throw new NullPointerException("SmallSet cannot store null values");
        }
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
            throw new IllegalStateException(
                    "cannot store more than 3 elements in a small set");
        }
    }

    @Override
    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        int oHash = o.hashCode();
        if (element1 != null && oHash == element1.hashCode()
                && o.equals(element1)) {
            element1 = null;
            return true;
        } else if (element2 != null && oHash == element2.hashCode()
                && o.equals(element2)) {
            element2 = null;
            return true;
        } else if (element3 != null && oHash == element3.hashCode()
                && o.equals(element3)) {
            element3 = null;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        int oHash = o.hashCode();
        if (element1 != null && oHash == element1.hashCode()
                && o.equals(element1)) {
            return true;
        } else if (element2 != null && oHash == element2.hashCode()
                && o.equals(element2)) {
            return true;
        } else if (element3 != null && oHash == element3.hashCode()
                && o.equals(element3)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int cp = 1;

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove");
            }

            @Override
            public boolean hasNext() {
                switch (cp) {
                    case 1:
                        if (element1 != null) {
                            return true;
                        } else {
                            cp++;
                        }
                    case 2:
                        if (element2 != null) {
                            return true;
                        } else {
                            cp++;
                        }
                    case 3:
                        if (element3 != null) {
                            return true;
                        } else {
                            cp++;
                        }
                    default:
                        return false;
                }
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No Next Element");
                }
                switch (cp++) {
                    case 1:
                        return element1;
                    case 2:
                        return element2;
                    case 3:
                        return element3;
                }
                throw new IllegalStateException(
                        "Iterator pointing past end of virtual array");
            }
        };
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
        return String.format("#<SmallSet: %s,%s,%s>", element1, element2,
                element3);
    }
}
