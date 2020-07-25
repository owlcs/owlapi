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

import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class CollectionFactory {

    private static final AtomicInteger EXPECTEDTHREADS = new AtomicInteger(1);

    private CollectionFactory() {}

    /**
     * @return The current number of expected updating threads.
     */
    public static int getExpectedThreads() {
        return EXPECTEDTHREADS.get();
    }

    /**
     * @param value the number of expected threads that will update threadsafe collections; useful
     *        for increasing the concurrency in ConcurrentHashMap instances created by this factory
     */
    public static void setExpectedThreads(int value) {
        EXPECTEDTHREADS.set(value);
    }

    /**
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet() {
        return new HashSet<>(0);
    }

    /**
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createLinkedSet() {
        return new LinkedHashSet<>(0);
    }

    /**
     * @param <T> axiom type
     * @return fresh non threadsafe list
     */
    public static <T> List<T> createList() {
        return new ArrayList<>();
    }

    /**
     * @param elements values to add to the list
     * @param <T> axiom type
     * @return fresh non threadsafe list
     */
    @SafeVarargs
    public static <T> List<T> createList(T... elements) {
        List<T> l = new ArrayList<>();
        for (T t : elements) {
            l.add(t);
        }
        return l;
    }

    /**
     * @param <T> content type
     * @return fresh threadsafe list
     */
    public static <T> List<T> createSyncList() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * @param i iterable
     * @param <T> type
     * @return list from iterable
     */
    public static <T> List<T> list(Iterable<T> i) {
        List<T> l = new ArrayList<>();
        for (T t : i) {
            l.add(t);
        }
        return l;
    }

    /**
     * @param i iterable
     * @param <T> type
     * @return list from iterable
     */
    @SafeVarargs
    public static <T> List<T> list(T... i) {
        return createList(i);
    }

    /**
     * @param i iterable
     * @param <T> type
     * @return list from iterable
     */
    public static <T> List<T> list(T i) {
        return Collections.singletonList(i);
    }

    /**
     * @param c values to add to the set
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(Collection<T> c) {
        return new HashSet<>(c);
    }

    /**
     * @param initialCapacity initial capacity for the new set
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * @param <K> key type
     * @param <V> value type
     * @return fresh map
     */
    public static <K, V> Map<K, V> createMap() {
        return new HashMap<>();
    }

    /**
     * @param <K> key type
     * @param <V> value type
     * @return a new weak HashMap wrapped as a synchronized map
     */
    public static <K, V> Map<K, WeakReference<V>> createSyncWeakMap() {
        return Collections.synchronizedMap(new WeakHashMap<K, WeakReference<V>>());
    }

    /**
     * @param elements values to add to the set
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    @SafeVarargs
    public static <T> Set<T> createSet(T... elements) {
        return new HashSet<>(list(elements));
    }

    /**
     * @param element value to add to the set
     * @param <T> axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(T element) {
        Set<T> result = createSet();
        result.add(element);
        return result;
    }

    /**
     * @param <T> set type
     * @return fresh threadsafe set
     */
    public static <T> Set<T> createSyncSet() {
        ConcurrentHashMap<T, Boolean> internalMap = createSyncMap();
        return Collections.newSetFromMap(internalMap);
    }

    /**
     * @param <K> key type
     * @param <V> value type
     * @return fresh threadsafe HashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> createSyncMap() {
        return new ConcurrentHashMap<>(16, 0.75F, EXPECTEDTHREADS.get());
    }

    /**
     * @param source the collection to lazily copy
     * @param <T> axiom type
     * @return a lazy defensive copy for source; the source collection will not be copied until a
     *         method that modifies the collection gets called, e.g., add(), addAll()
     */
    public static <T> Set<T> getCopyOnRequestSet(Collection<T> source) {
        return getCopyOnRequestSetFromMutableCollection(source);
    }

    /**
     * @param source source collection
     * @param <T> axiom type
     * @return copy on request that builds a list from the input set
     */
    public static <T> Set<T> getCopyOnRequestSetFromMutableCollection(
        @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<>(source, true);
    }

    /**
     * @param source the source collection, expected to be immutable
     * @param <T> axiom type
     * @return copy on request that does not build a list immediately
     */
    public static <T> Set<T> copy(@Nullable Collection<T> source) {
        return getCopyOnRequestSetFromImmutableCollection(source);
    }

    /**
     * @param source the source collection, expected to be mutable; the backing list is created
     *        immediately
     * @param <T> axiom type
     * @return copy on request that builds a list immediately
     */
    public static <T> Set<T> copyMutable(@Nullable Collection<T> source) {
        return getCopyOnRequestSetFromMutableCollection(source);
    }

    /**
     * @param source the source collection, expected to be immutable
     * @param <T> axiom type
     * @return copy on request that does not build a list immediately
     */
    public static <T> Set<T> getCopyOnRequestSetFromImmutableCollection(
        @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<>(source, false);
    }

    /**
     * a set implementation that uses a delegate collection for all read-only operations and makes a
     * copy if changes are attempted. Useful for cheap defensive copies: no costly rehashing on the
     * original collection is made unless changes are attempted. Changes are not mirrored back to
     * the original collection, although changes to the original set BEFORE changes to the copy are
     * reflected in the copy. If the source collection is not supposed to change, then this
     * collection behaves just like a regular defensive copy; if the source collection can change,
     * then this collection should be built from a cheap copy of the original collection. For
     * example, if the source collection is a set, it can be copied into a list; the cost of the
     * copy operation from set to list is approximately 1/3 of the cost of copying into a new
     * HashSet. This is not efficient if the most common operations performed on the copy are
     * contains() or containsAll(), since they are more expensive for lists wrt sets; a counter for
     * these calls is maintained by the collection, so if a large number of contains/containsAll
     * calls takes place, the delegate is turned into a regular set. This implementation is not
     * threadsafe even if the source set is: there is no lock during the copy, and the new set is
     * not threadsafe.
     *
     * @param <T> the type contained
     */
    public static class ConditionalCopySet<T> implements Set<T> {

        private static final int MAXCONTAINS = 10;
        protected Collection<T> delegate;
        private boolean copyDone = false;
        private int containsCounter = 0;

        /**
         * @param source initial elements
         * @param listCopy true if a copy must be made
         */
        public ConditionalCopySet(Collection<T> source, boolean listCopy) {
            if (listCopy) {
                delegate = new ArrayList<>(source);
            } else {
                delegate = source;
            }
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof ConditionalCopySet) {
                return delegate.containsAll(((ConditionalCopySet<?>) obj).delegate)
                    && ((ConditionalCopySet<?>) obj).delegate.containsAll(delegate);
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
        public boolean add(@Nullable T e) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.add(e);
        }

        @Override
        public boolean addAll(@Nullable Collection<? extends T> c) {
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
        public boolean contains(@Nullable Object o) {
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
        public boolean containsAll(@Nullable Collection<?> c) {
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

        @Override
        public Iterator<T> iterator() {
            return delegate.iterator();
        }

        @Override
        public boolean remove(@Nullable Object o) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.remove(o);
        }

        @Override
        public boolean removeAll(@Nullable Collection<?> c) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<>(delegate);
            }
            return delegate.removeAll(c);
        }

        @Override
        public boolean retainAll(@Nullable Collection<?> c) {
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

        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @Override
        public <Q> Q[] toArray(@Nullable Q[] a) {
            return delegate.toArray(a);
        }
    }
}
