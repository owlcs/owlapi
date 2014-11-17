package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.OWLObject;

/** A few util methods for common stream operations. */
public class OWLAPIStreamUtils {

    public static <T> Set<T> asSet(Stream<T> s) {
        Set<T> set = new LinkedHashSet<>();
        s.forEach(x -> set.add(x));
        return set;
    }

    public static <T> Set<T> asSet(Stream<?> s, Class<T> type) {
        Set<T> set = new LinkedHashSet<>();
        s.forEach(x -> set.add((T) x));
        return set;
    }

    public static <T> List<T> asList(Stream<T> s) {
        List<T> set = new ArrayList<>();
        s.forEach(x -> set.add(x));
        return set;
    }

    public static <T> List<T> asList(Stream<?> s, Class<T> type) {
        List<T> set = new ArrayList<>();
        s.forEach(x -> set.add((T) x));
        return set;
    }

    public static boolean contains(Stream<?> s, Object o) {
        return s.anyMatch(x -> x.equals(o));
    }

    public static <T> boolean add(Stream<T> s, Collection<? super T> c) {
        AtomicBoolean b = new AtomicBoolean(false);
        s.forEach(x -> b.compareAndSet(false, c.add(x)));
        return b.get();
    }

    public static <T> boolean add(Collection<? super T> c, Stream<T> s) {
        AtomicBoolean b = new AtomicBoolean(false);
        s.forEach(x -> b.compareAndSet(false, c.add(x)));
        return b.get();
    }

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

    public static int compareStreams(Stream<? extends OWLObject> set1,
            Stream<? extends OWLObject> set2) {
        return compareIterators(set1.sorted().iterator(), set2.sorted()
                .iterator());
    }

    public static int compareIterators(Iterator<? extends OWLObject> thisIt,
            Iterator<? extends OWLObject> otherIt) {
        while (thisIt.hasNext() && otherIt.hasNext()) {
            OWLObject o1 = thisIt.next();
            OWLObject o2 = otherIt.next();
            int diff = o1.compareTo(o2);
            if (diff != 0) {
                return diff;
            }
        }
        return Boolean.compare(thisIt.hasNext(), otherIt.hasNext());
    }

    public static boolean equalIterators(Iterator<? extends OWLObject> thisIt,
            Iterator<? extends OWLObject> otherIt) {
        while (thisIt.hasNext() && otherIt.hasNext()) {
            if (!thisIt.next().equals(otherIt.next())) {
                return false;
            }
        }
        return thisIt.hasNext() == otherIt.hasNext();
    }

    public static boolean equalStreams(Stream<? extends OWLObject> thisIt,
            Stream<? extends OWLObject> otherIt) {
        return equalIterators(thisIt.iterator(), otherIt.iterator());
    }

    public static int compareLists(List<? extends OWLObject> list1,
            List<? extends OWLObject> list2) {
        return compareIterators(list1.iterator(), list2.iterator());
    }
}
