package org.semanticweb.owlapi.util;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.HasComponents;
import org.semanticweb.owlapi.model.OWLObject;

/**
 * A few util methods for common stream operations.
 */
public class OWLAPIStreamUtils {

    private OWLAPIStreamUtils() {}

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asSet(Stream<T> s) {
        Set<T> set = new LinkedHashSet<>();
        add(set, s);
        return set;
    }

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return set including all elements in the stream
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> asSet(Stream<?> s, @SuppressWarnings("unused") Class<T> type) {
        Set<T> set = new LinkedHashSet<>();
        add(set, s.map(x -> (T) x));
        return set;
    }

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @return set including all elements in the stream
     */
    public static <T> Set<T> asUnorderedSet(Stream<T> s) {
        return s.collect(toSet());
    }

    /**
     * @param s stream to turn to set. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return set including all elements in the stream
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> asUnorderedSet(Stream<?> s,
        @SuppressWarnings("unused") Class<T> type) {
        return s.map(x -> (T) x).collect(toSet());
    }

    /**
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asList(Stream<T> s) {
        List<T> set = new ArrayList<>();
        add(set, s);
        return set;
    }

    /**
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @return list including all elements in the stream
     */
    public static <T> List<T> asListNullsForbidden(Stream<T> s) {
        return asList(s.map(OWLAPIPreconditions::checkNotNull));
    }

    /**
     * @param s stream to turn to list. The stream is consumed by this operation.
     * @param type force return type to be exactly T
     * @param <T> type of return collection
     * @return list including all elements in the stream
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> asList(Stream<?> s, @SuppressWarnings("unused") Class<T> type) {
        return asList(s.map(x -> (T) x));
    }

    /**
     * @param s stream to turn to map. The stream is consumed by this operation.
     * @param f function to create the key
     * @param <T> type of key
     * @param <Q> type of input and value
     * @return map including all elements in the stream, keyed by f
     */
    public static <T, Q> Map<T, Q> asMap(Stream<Q> s, Function<Q, T> f) {
        return asMap(s, f, v -> v);
    }

    /**
     * @param s stream to turn to map. The stream is consumed by this operation.
     * @param key function to create the key
     * @param val function to create the value
     * @param <K> type of key
     * @param <V> type of value
     * @param <Q> type of input
     * @return map including all elements in the stream, keyed by key and valued by val
     */
    public static <K, V, Q> Map<K, V> asMap(Stream<Q> s, Function<Q, K> key, Function<Q, V> val) {
        return s.collect(Collectors.toConcurrentMap(v -> key.apply(v), v -> val.apply(v)));
    }

    /**
     * @param s stream to check for containment. The stream is consumed at least partially by this
     * operation
     * @param o object to search
     * @return true if the stream contains the object
     */
    public static boolean contains(Stream<?> s, Object o) {
        return s.anyMatch(x -> x.equals(o));
    }

    /**
     * @param s stream of elements to add
     * @param c collection to add to
     * @return true if any element in the stream is added to the collection
     */
    public static <T> boolean add(Collection<? super T> c, Stream<T> s) {
        int size = c.size();
        s.forEach(c::add);
        return c.size() != size;
    }

    /**
     * @param set1 collection to compare
     * @param set2 collection to compare
     * @return negative value if set1 comes before set2, positive value if set2 comes before set1, 0
     * if the two sets are equal or incomparable.
     */
    public static int compareCollections(Collection<? extends OWLObject> set1,
        Collection<? extends OWLObject> set2) {
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
     * @param set1 stream to compare
     * @param set2 stream to compare
     * @return negative value if set1 comes before set2, positive value if set2 comes before set1, 0
     * if the two sets are equal or incomparable.
     */
    public static int compareStreams(Stream<?> set1, Stream<?> set2) {
        return compareIterators(set1.sorted().iterator(), set2.sorted().iterator());
    }

    /**
     * Compare iterators element by element (sensitive to order)
     *
     * @param set1 iterator to compare
     * @param set2 iterator to compare
     * @return negative value if set1 comes before set2, positive value if set2 comes before set1, 0
     * if the two sets are equal or incomparable.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static int compareIterators(Iterator<?> set1, Iterator<?> set2) {
        while (set1.hasNext() && set2.hasNext()) {
            Object o1 = set1.next();
            Object o2 = set2.next();
            int diff;
            if (o1 instanceof Stream && o2 instanceof Stream) {
                diff = compareIterators(((Stream<?>) o1).iterator(), ((Stream<?>) o2).iterator());
            } else if (o1 instanceof Collection && o2 instanceof Collection) {
                diff = compareIterators(((Collection<?>) o1).iterator(),
                    ((Collection<?>) o2).iterator());
            } else if (o1 instanceof Comparable && o2 instanceof Comparable) {
                diff = ((Comparable) o1).compareTo(o2);
            } else {
                throw new IllegalArgumentException("Incomparable types: '" + o1 + "' with class "
                    + o1.getClass() + ", '" + o2 + "' with class " + o2.getClass()
                    + " found while comparing iterators");
            }
            if (diff != 0) {
                return diff;
            }
        }
        return Boolean.compare(set1.hasNext(), set2.hasNext());
    }

    /**
     * Check iterator contents for equality (sensitive to order)
     *
     * @param set1 iterator to compare
     * @param set2 iterator to compare
     * @return true if the iterators have the same content, false otherwise.
     */
    public static boolean equalIterators(Iterator<?> set1, Iterator<?> set2) {
        while (set1.hasNext() && set2.hasNext()) {
            Object o1 = set1.next();
            Object o2 = set2.next();
            if (o1 instanceof Stream && o2 instanceof Stream) {
                if (!equalStreams((Stream<?>) o1, (Stream<?>) o2)) {
                    return false;
                }
            } else {
                if (!o1.equals(o2)) {
                    return false;
                }
            }
        }
        return set1.hasNext() == set2.hasNext();
    }

    /**
     * Check streams for equality (sensitive to order)
     *
     * @param set1 stream to compare
     * @param set2 stream to compare
     * @return true if the streams have the same content, false otherwise.
     */
    public static boolean equalStreams(Stream<?> set1, Stream<?> set2) {
        return equalIterators(set1.iterator(), set2.iterator());
    }

    /**
     * Check lists for equality (sensitive to order)
     *
     * @param set1 list to compare
     * @param set2 list to compare
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

    /**
     * @param root the root for the invisit
     * @return recursive invisit of all components included in the root component; includes the root
     * and all intermediate nodes. Annotations and other groups of elements will be represented as
     * streams or collections, same as if the accessor method on the object was used.
     */
    public static Stream<?> allComponents(HasComponents root) {
        List<Stream<?>> streams = new ArrayList<>();
        streams.add(Stream.of(root));
        root.components().forEach(o -> {
            if (o != root) {
                if (o instanceof HasComponents) {
                    streams.add(allComponents((HasComponents) o));
                } else {
                    streams.add(Stream.of(o));
                }
            }
        });
        return streams.stream().flatMap(x -> x);
    }

    /**
     * @param root the root for the invisit
     * @return recursive invisit of all components included in the root component; includes the root
     * and all intermediate nodes. Streams will be flattened.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Stream<?> flatComponents(HasComponents root) {
        List streams = new ArrayList<>();
        streams.add(root);
        root.components().filter(o -> o != root).forEach(o -> flatIteration(streams, o));
        return streams.stream();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected static void flatIteration(List streams, Object o) {
        if (o instanceof Stream) {
            ((Stream<?>) o).forEach(o1 -> flatIteration(streams, o1));
        } else if (o instanceof Collection) {
            ((Collection<?>) o).forEach(o1 -> flatIteration(streams, o1));
        } else if (o instanceof HasComponents) {
            streams.add(o);
            ((HasComponents) o).components().forEach(o1 -> flatIteration(streams, o1));
        } else {
            streams.add(o);
        }
    }

    /**
     * @param size size of matrix
     * @return a stream of coordinates for a triangular matrix of size {@code size}. For input 3,
     * the values are (1,2), (1,3), (2,3)
     */
    public static Stream<int[]> pairs(int size) {
        List<int[]> values = new ArrayList<>((size * size - size) / 2);
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                values.add(new int[]{i, j});
            }
        }
        return values.stream();
    }

    /**
     * @param size size of matrix
     * @return a stream of coordinates for a symmetric matrix of size {@code size}, excluding main
     * diagonal. For input 3, the values are (1,2), (1,3), (2,3), (2,1),(3,1), (3,2)
     */
    public static Stream<int[]> allPairs(int size) {
        List<int[]> values = new ArrayList<>(size * size - size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    values.add(new int[]{i, j});
                }
            }
        }
        return values.stream();
    }

    /**
     * @param input collection to partition
     * @return a stream of elements for a triangular matrix of size {@code l.size()}, where l is the
     * list corresponding to the input collection. For input of length 3, the values are
     * (l.get(1),l.get(2)), (l.get(1),l.get(3)), (l.get(2),l.get(3))
     */
    public static <T> Stream<Pair<T>> pairs(Collection<T> input) {
        List<T> l;
        if (input instanceof List) {
            l = (List<T>) input;
        } else {
            l = new ArrayList<>(input);
        }
        int size = l.size();
        List<Pair<T>> values = new ArrayList<>((size * size - size) / 2);
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                values.add(pair(l.get(i), l.get(j)));
            }
        }
        return values.stream();
    }

    /**
     * @param input collection to partition
     * @return a stream of coordinates for a symmetric matrix of size {@code l.size()}, where l is
     * the list corresponding to the input collection, excluding main diagonal. For input 3, the
     * values are (l.get(1),l.get(2)), (l.get(1),l.get(3)), (l.get(2),l.get(3)),
     * (l.get(2),l.get(1)),(l.get(3),l.get(1)), (l.get(3),l.get(2))
     */
    public static <T> Stream<Pair<T>> allPairs(Collection<T> input) {
        List<T> l;
        if (input instanceof List) {
            l = (List<T>) input;
        } else {
            l = new ArrayList<>(input);
        }
        int size = l.size();
        List<Pair<T>> values = new ArrayList<>(size * size - size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    values.add(pair(l.get(i), l.get(j)));
                }
            }
        }
        return values.stream();
    }

    /**
     * @param input collection to partition
     * @return a stream of elements for a triangular matrix of size {@code l.size()}, where l is the
     * list corresponding to the input collection. For input of length 3, the values are
     * (l.get(1),l.get(2)), (l.get(1),l.get(3)), (l.get(2),l.get(3))
     */
    public static <T> Stream<Pair<T>> pairs(Stream<T> input) {
        return pairs(asList(input));
    }

    /**
     * @param input collection to partition
     * @return a stream of coordinates for a symmetric matrix of size {@code l.size()}, where l is
     * the list corresponding to the input collection, excluding main diagonal. For input 3, the
     * values are (l.get(1),l.get(2)), (l.get(1),l.get(3)), (l.get(2),l.get(3)),
     * (l.get(2),l.get(1)),(l.get(3),l.get(1)), (l.get(3),l.get(2))
     */
    public static <T> Stream<Pair<T>> allPairs(Stream<T> input) {
        return allPairs(asList(input));
    }

    /**
     * @param i first
     * @param j second
     * @return pair of (i,j)
     */
    public static <T> Pair<T> pair(T i, T j) {
        return new Pair<>(i, j);
    }

    /**
     * Class for pairwise partition
     *
     * @param <T> type
     */
    public static class Pair<T> {

        /**
         * First element.
         */
        public final T i;
        /**
         * Second element.
         */
        public final T j;

        /**
         * @param i first
         * @param j second
         */
        public Pair(T i, T j) {
            this.i = i;
            this.j = j;
        }
    }
}
