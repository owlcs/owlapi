/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class CollectionFactory {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CollectionFactory.class.getName());
    private static final AtomicInteger EXPECTEDTHREADS = new AtomicInteger(8);

    /**
     * Sort the input collection; if the ordering is unstable and an error is
     * thrown (due to the use of TimSort in JDK 1.7 and newer), catch it and
     * leave the collection unsorted. NOTE: use this method if ordering is
     * desirable but not necessary.
     * 
     * @param <T>
     *        list type
     * @param toReturn
     *        list to sort
     */
    public static <T extends Comparable<T>> void sortOptionallyComparables(
            @Nonnull List<T> toReturn) {
        try {
            Collections.sort(toReturn);
        } catch (IllegalArgumentException e) {
            // catch possible sorting misbehaviour
            if (!e.getMessage().contains(
                    "Comparison method violates its general contract!")) {
                throw e;
            }
            // otherwise print a warning and leave the list unsorted
            LOGGER.warn(
                    "Misbehaving triple comparator, leaving triples unsorted",
                    e);
        }
    }

    /**
     * Sort the input collection; if the ordering is unstable and an error is
     * thrown (due to the use of TimSort in JDK 1.7 and newer), catch it and
     * leave the collection unsorted. NOTE: use this method if ordering is
     * desirable but not necessary.
     * 
     * @param toReturn
     *        list to sort
     * @return sorted input list
     */
    @Nonnull
    public static <T extends OWLObject> List<T> sortOptionally(
            @Nonnull List<T> toReturn) {
        try {
            Collections.sort(toReturn);
        } catch (IllegalArgumentException e) {
            // catch possible sorting misbehaviour
            if (!e.getMessage().contains(
                    "Comparison method violates its general contract!")) {
                throw e;
            }
            // otherwise print a warning and leave the list unsorted
            LOGGER.warn(
                    "Misbehaving triple comparator, leaving triples unsorted",
                    e);
        }
        return toReturn;
    }

    /**
     * Sort a copy of the input collection; if the ordering is unstable and an
     * error is thrown (due to the use of TimSort in JDK 1.7 and newer), catch
     * it and leave the collection unsorted. NOTE: use this method if ordering
     * is desirable but not necessary.
     * 
     * @param toReturn
     *        collection to sort
     * @param <T>
     *        list type
     * @return sorted copy of the input, if no errors are raised. Copy of the
     *         original otherwise.
     */
    @Nonnull
    public static <T extends Comparable<T>> List<T> sortOptionallyComparables(
            @Nonnull Collection<T> toReturn) {
        List<T> list = new ArrayList<>(toReturn);
        try {
            Collections.sort(list);
        } catch (IllegalArgumentException e) {
            // catch possible sorting misbehaviour
            if (!e.getMessage().contains(
                    "Comparison method violates its general contract!")) {
                throw e;
            }
            // otherwise print a warning and leave the list unsorted
            LOGGER.warn(
                    "Misbehaving triple comparator, leaving triples unsorted",
                    e);
        }
        return list;
    }

    /**
     * Sort a copy of the input collection; if the ordering is unstable and an
     * error is thrown (due to the use of TimSort in JDK 1.7 and newer), catch
     * it and leave the collection unsorted. NOTE: use this method if ordering
     * is desirable but not necessary.
     * 
     * @param toReturn
     *        collection to sort
     * @param <T>
     *        list type
     * @return sorted copy of the input, if no errors are raised. Copy of the
     *         original otherwise.
     */
    @Nonnull
    public static <T extends OWLObject> List<T> sortOptionally(
            @Nonnull Collection<T> toReturn) {
        return sortOptionally(new ArrayList<>(toReturn));
    }

    /**
     * Sort a copy of the input collection; if the ordering is unstable and an
     * error is thrown (due to the use of TimSort in JDK 1.7 and newer), catch
     * it and leave the collection unsorted. NOTE: use this method if ordering
     * is desirable but not necessary.
     * 
     * @param toReturn
     *        collection to sort
     * @param <T>
     *        list type
     * @return sorted copy of the input, if no errors are raised. Copy of the
     *         original otherwise.
     */
    @Nonnull
    public static <T extends OWLObject> List<T> sortOptionally(
            @Nonnull Stream<T> toReturn) {
        return sortOptionally(asList(toReturn));
    }

    /**
     * @param value
     *        the number of expected threads that will access threadsafe
     *        collections; useful for increasing the concurrency in
     *        ConcurrentHashMaps
     */
    public static void setExpectedThreads(int value) {
        EXPECTEDTHREADS.set(value);
    }

    /** @return The current number of expected threads. */
    public static int getExpectedThreads() {
        return EXPECTEDTHREADS.get();
    }

    /**
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> createSet() {
        return new HashSet<>(0);
    }

    /**
     * @return fresh non threadsafe list
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> List<T> createList() {
        return new ArrayList<>();
    }

    /**
     * @return fresh threadsafe list
     * @param <T>
     *        content type
     */
    @Nonnull
    public static <T> List<T> createSyncList() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * @param i
     *        iterable
     * @return list from iterable
     * @param <T>
     *        type
     */
    @Nonnull
    public static <T> List<T> list(Iterable<T> i) {
        return Lists.newArrayList(i);
    }

    /**
     * @param i
     *        iterable
     * @return list from iterable
     * @param <T>
     *        type
     */
    @Nonnull
    @SafeVarargs
    public static <T> List<T> list(T... i) {
        return Lists.newArrayList(i);
    }

    /**
     * @param i
     *        iterable
     * @return list from iterable
     * @param <T>
     *        type
     */
    @Nonnull
    public static <T> List<T> list(T i) {
        return Collections.singletonList(i);
    }

    /**
     * @param c
     *        values to add to the set
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> createSet(@Nonnull Collection<T> c) {
        return new HashSet<>(c);
    }

    /**
     * @param initialCapacity
     *        initial capacity for the new set
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> createSet(int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * @return fresh map
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     */
    @Nonnull
    public static <K, V> Map<K, V> createMap() {
        return new HashMap<>();
    }

    /**
     * @return a new weak hashmap wrapped as a synchronized map
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     */
    public static <K, V> Map<K, WeakReference<V>> createSyncWeakMap() {
        return Collections
                .synchronizedMap(new WeakHashMap<K, WeakReference<V>>());
    }

    /**
     * @param elements
     *        values to add to the set
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    @SafeVarargs
    public static <T> Set<T> createSet(@Nonnull T... elements) {
        return Sets.newHashSet(elements);
    }

    /**
     * @param element
     *        value to add to the set
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> createSet(@Nonnull T element) {
        Set<T> result = createSet();
        result.add(element);
        return result;
    }

    /**
     * @return fresh threadsafe set
     * @param <T>
     *        set type
     */
    @Nonnull
    public static <T> Set<T> createSyncSet() {
        ConcurrentHashMap<T, Boolean> internalMap = createSyncMap();
        return Collections.newSetFromMap(internalMap);
    }

    /**
     * @return fresh threadsafe hashmap
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     */
    @Nonnull
    public static <K, V> ConcurrentHashMap<K, V> createSyncMap() {
        return new ConcurrentHashMap<>(16, 0.75F, EXPECTEDTHREADS.get());
    }

    /**
     * @param source
     *        the collection to lazily copy
     * @return a lazy defensive copy for source; the source collection will not
     *         be copied until a method that modifies the collection gets
     *         called, e.g., add(), addAll()
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> getCopyOnRequestSet(Collection<T> source) {
        return getCopyOnRequestSetFromMutableCollection(source);
    }

    /**
     * @param source
     *        source collection
     * @return copy on request that builds a list from the input set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> getCopyOnRequestSetFromMutableCollection(
            @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<>(source, true);
    }

    /**
     * @param source
     *        the source collection, expected to be immutable
     * @return copy on request that does not build a list immediately
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> copy(@Nullable Collection<T> source) {
        return getCopyOnRequestSetFromImmutableCollection(source);
    }

    /**
     * @param source
     *        the source collection, expected to be mutable; the backing list is
     *        created immediately
     * @return copy on request that builds a list immediately
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> copyMutable(@Nullable Collection<T> source) {
        return getCopyOnRequestSetFromMutableCollection(source);
    }

    /**
     * @param source
     *        the source collection, expected to be immutable
     * @return copy on request that does not build a list immediately
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> getCopyOnRequestSetFromImmutableCollection(
            @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<>(source, false);
    }

    /**
     * a set implementation that uses a delegate collection for all read-only
     * operations and makes a copy if changes are attempted. Useful for cheap
     * defensive copies: no costly rehashing on the original collection is made
     * unless changes are attempted. Changes are not mirrored back to the
     * original collection, although changes to the original set BEFORE changes
     * to the copy are reflected in the copy. If the source collection is not
     * supposed to change, then this collection behaves just like a regular
     * defensive copy; if the source collection can change, then this collection
     * should be built from a cheap copy of the original collection. For
     * example, if the source collection is a set, it can be copied into a list;
     * the cost of the copy operation from set to list is approximately 1/3 of
     * the cost of copying into a new HashSet. This is not efficient if the most
     * common operations performed on the copy are contains() or containsAll(),
     * since they are more expensive for lists wrt sets; a counter for these
     * calls is maintained by the collection, so if a large number of
     * contains/containsAll calls takes place, the delegate is turned into a
     * regular set. This implementation is not threadsafe even if the source set
     * is: there is no lock during the copy, and the new set is not threadsafe.
     * 
     * @param <T>
     *        the type contained
     */
    public static class ConditionalCopySet<T> implements Set<T> {

        private static final int MAXCONTAINS = 10;
        private boolean copyDone = false;
        protected Collection<T> delegate;
        private int containsCounter = 0;

        /**
         * @param source
         *        initial elements
         * @param listCopy
         *        true if a copy must be made
         */
        public ConditionalCopySet(@Nonnull Collection<T> source,
                boolean listCopy) {
            if (listCopy) {
                delegate = new ArrayList<>(source);
            } else {
                delegate = source;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof ConditionalCopySet) {
                return delegate
                        .containsAll(((ConditionalCopySet<?>) obj).delegate)
                        && ((ConditionalCopySet<?>) obj).delegate
                                .containsAll(delegate);
            }
            if (obj instanceof Collection) {
                return delegate.containsAll((Collection<?>) obj)
                        && ((Collection<?>) obj).containsAll(delegate);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return delegate.hashCode();
        }

        @Override
        public String toString() {
            return delegate.toString();
        }

        @Override
        public boolean add(T e) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.addAll(c);
        }

        @Override
        public void clear() {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>();
            }
            delegate.clear();
        }

        @Override
        public boolean contains(Object o) {
            containsCounter++;
            if (containsCounter >= MAXCONTAINS && !copyDone) {
                checkDelegate();
            }
            return delegate.contains(o);
        }

        private void checkDelegate() {
            // many calls to contains, inefficient if the delegate is not a
            // set
            if (!(delegate instanceof Set)) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            containsCounter++;
            if (containsCounter >= MAXCONTAINS && !copyDone) {
                checkDelegate();
            }
            return delegate.containsAll(c);
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @Nonnull
        @Override
        public Iterator<T> iterator() {
            return delegate.iterator();
        }

        @Override
        public boolean remove(Object o) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.remove(o);
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.retainAll(c);
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @Nonnull
        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @Nonnull
        @Override
        public <Type> Type[] toArray(Type[] a) {
            return delegate.toArray(a);
        }
    }
}
