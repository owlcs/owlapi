package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLObject;

/** A few util methods for common stream operations. */
public class OWLAPIStreamUtils {

    /**
     * @param s
     *        stream to turn to set. The stream is consumed by this operation.
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asSet(Stream<T> s) {
        Set<T> set = new LinkedHashSet<>();
        s.forEach(x -> set.add(x));
        return set;
    }

    /**
     * @param s
     *        stream to turn to set. The stream is consumed by this operation.
     * @param type
     *        force return type to be exactly T
     * @param <T>
     *        type of return collection
     * @return set including all elements in the stream
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> asSet(Stream<?> s, @SuppressWarnings("unused") Class<T> type) {
        Set<T> set = new LinkedHashSet<>();
        s.forEach(x -> set.add((T) x));
        return set;
    }

    /**
     * @param s
     *        stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asList(Stream<T> s) {
        List<T> set = new ArrayList<>();
        s.forEach(x -> set.add(x));
        return set;
    }

    /**
     * @param s
     *        stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asListNullsForbidden(Stream<T> s) {
        List<T> set = new ArrayList<>();
        s.forEach(x -> set.add(checkNotNull(x)));
        return set;
    }

    /**
     * @param s
     *        stream to turn to list. The stream is consumed by this operation.
     * @param type
     *        force return type to be exactly T
     * @param <T>
     *        type of return collection
     * @return list including all elements in the stream
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(Stream<?> s, @SuppressWarnings("unused") Class<T> type) {
        List<T> set = new ArrayList<>();
        s.forEach(x -> set.add((T) x));
        return set;
    }

    /**
     * @param s
     *        stream to check for containment. The stream is consumed at least
     *        partially by this operation
     * @param o
     *        object to search
     * @return true if the stream contains the object
     */
    public static boolean contains(Stream<?> s, Object o) {
        return s.anyMatch(x -> x.equals(o));
    }

    /**
     * @param s
     *        stream of elements to add
     * @param c
     *        collection to add to
     * @return true if any element in the stream is added to the collection
     */
    public static <T> boolean add(Stream<T> s, Collection<? super T> c) {
        AtomicBoolean b = new AtomicBoolean(false);
        s.forEach(x -> b.compareAndSet(false, c.add(x)));
        return b.get();
    }

    /**
     * @param s
     *        stream of elements to add
     * @param c
     *        collection to add to
     * @return true if any element in the stream is added to the collection
     */
    public static <T> boolean add(Collection<? super T> c, Stream<T> s) {
        return add(s, c);
    }

    /**
     * @param set1
     *        collection to compare
     * @param set2
     *        collection to compare
     * @return negative value if set1 comes before set2, positive value if set2
     *         comes before set1, 0 if the two sets are equal or incomparable.
     */
    public static int compareCollections(Collection<? extends OWLObject> set1, Collection<? extends OWLObject> set2) {
        SortedSet<? extends OWLObject> ss1;
        if (set1 instanceof SortedSet) {
            ss1 = (SortedSet<? extends OWLObject>) set1;
        } else {
            ss1 = new TreeSet<>(set1);
        }
        SortedSet<? extends OWLObject> ss2;
        if (set2 instanceof SortedSet) {
            ss2 = (SortedSet<? extends OWLObject>) set2;
        } else {
            ss2 = new TreeSet<>(set2);
        }
        return compareIterators(ss1.iterator(), ss2.iterator());
    }

    /**
     * Compare streams through iterators (sensitive to order)
     * 
     * @param set1
     *        stream to compare
     * @param set2
     *        stream to compare
     * @return negative value if set1 comes before set2, positive value if set2
     *         comes before set1, 0 if the two sets are equal or incomparable.
     */
    public static int compareStreams(Stream<? extends OWLObject> set1, Stream<? extends OWLObject> set2) {
        return compareIterators(set1.sorted().iterator(), set2.sorted().iterator());
    }

    /**
     * Compare iterators element by element (sensitive to order)
     * 
     * @param set1
     *        iterator to compare
     * @param set2
     *        iterator to compare
     * @return negative value if set1 comes before set2, positive value if set2
     *         comes before set1, 0 if the two sets are equal or incomparable.
     */
    public static int compareIterators(Iterator<? extends OWLObject> set1, Iterator<? extends OWLObject> set2) {
        while (set1.hasNext() && set2.hasNext()) {
            OWLObject o1 = set1.next();
            OWLObject o2 = set2.next();
            int diff = o1.compareTo(o2);
            if (diff != 0) {
                return diff;
            }
        }
        return Boolean.compare(set1.hasNext(), set2.hasNext());
    }

    /**
     * Check iterator contents for equality (sensitive to order)
     * 
     * @param set1
     *        iterator to compare
     * @param set2
     *        iterator to compare
     * @return true if the iterators have the same content, false otherwise.
     */
    public static boolean equalIterators(Iterator<? extends OWLObject> set1, Iterator<? extends OWLObject> set2) {
        while (set1.hasNext() && set2.hasNext()) {
            if (!set1.next().equals(set2.next())) {
                return false;
            }
        }
        return set1.hasNext() == set2.hasNext();
    }

    /**
     * Check streams for equality (sensitive to order)
     * 
     * @param set1
     *        stream to compare
     * @param set2
     *        stream to compare
     * @return true if the streams have the same content, false otherwise.
     */
    public static boolean equalStreams(Stream<? extends OWLObject> set1, Stream<? extends OWLObject> set2) {
        return equalIterators(set1.iterator(), set2.iterator());
    }

    /**
     * Check lists for equality (sensitive to order)
     * 
     * @param set1
     *        list to compare
     * @param set2
     *        list to compare
     * @return true if the lists have the same content, false otherwise.
     */
    public static int compareLists(List<? extends OWLObject> set1, List<? extends OWLObject> set2) {
        return compareIterators(set1.iterator(), set2.iterator());
    }

    /**
     * Annotated wrapper for Stream.empty()
     * 
     * @return empty stream
     */
    public static <T> Stream<T> empty() {
        return Stream.empty();
    }
}
