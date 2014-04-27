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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class CollectionFactory {

    private static final AtomicInteger expectedThreads = new AtomicInteger(8);

    /**
     * @param value
     *        the number of expected threads that will access threadsafe
     *        collections; useful for increasing the concurrency in
     *        ConcurrentHashMaps
     */
    public static void setExpectedThreads(int value) {
        expectedThreads.set(value);
    }

    /** @return The current number of expected threads. */
    public static int getExpectedThreads() {
        return expectedThreads.get();
    }

    /**
     * @return fresh non threadsafe set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> createSet() {
        // TODO large number of sets stay very small, wasting space
        return new HashSet<T>();
    }

    /**
     * @return fresh non threadsafe list
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> List<T> createList() {
        return new ArrayList<T>();
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
        return new HashSet<T>(c);
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
        return new HashSet<T>(initialCapacity);
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
        return new HashMap<K, V>();
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
    public static <T> Set<T> createSet(@Nonnull T... elements) {
        Set<T> result = createSet();
        for (T t : elements) {
            result.add(t);
        }
        return result;
    }

    /**
     * @return fresh threadsafe set
     * @param <T>
     *        set type
     */
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
        return new ConcurrentHashMap<K, V>(16, 0.75F, expectedThreads.get());
    }

    /**
     * this class implements a Set using a ConcurrentHashMap as backing
     * structure; compared to the default HashSet implementation, this structure
     * is threadsafe without being completely synchronized, so it offers better
     * performances.
     * 
     * @param <T>
     *        type of collection
     */
    private static final class SyncSet<T> implements Set<T> {

        private final ConcurrentHashMap<T, Set<T>> backingMap;

        public SyncSet(ConcurrentHashMap<T, Set<T>> map) {
            backingMap = map;
        }

        public SyncSet() {
            this(new ConcurrentHashMap<T, Set<T>>());
        }

        public SyncSet(@Nonnull Collection<T> delegate) {
            this();
            for (T d : delegate) {
                add(d);
            }
        }

        @Override
        public boolean add(T e) {
            return backingMap.put(e, this) == null;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            boolean toReturn = false;
            for (T o : c) {
                toReturn = toReturn || add(o);
            }
            return toReturn;
        }

        @Override
        public void clear() {
            backingMap.clear();
        }

        @Override
        public boolean contains(Object o) {
            return backingMap.containsKey(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            boolean toReturn = true;
            for (Object o : c) {
                toReturn = toReturn && contains(o);
                if (!toReturn) {
                    return toReturn;
                }
            }
            return toReturn;
        }

        @Override
        public boolean isEmpty() {
            return backingMap.isEmpty();
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Iterator<T> iterator() {
            return backingMap.keySet().iterator();
        }

        @Override
        public int size() {
            return backingMap.size();
        }

        @Override
        public boolean remove(Object o) {
            return backingMap.remove(o) != null;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean toReturn = false;
            for (Object o : c) {
                toReturn = toReturn || remove(o);
            }
            return toReturn;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean toReturn = false;
            for (Map.Entry<T, Set<T>> e : backingMap.entrySet()) {
                if (!c.contains(e.getKey())) {
                    toReturn = true;
                    backingMap.remove(e.getKey());
                }
            }
            return toReturn;
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Object[] toArray() {
            return backingMap.keySet().toArray();
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public <Type> Type[] toArray(Type[] a) {
            return backingMap.keySet().toArray(a);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj instanceof SyncSet) {
                return this.backingMap.keySet().equals(
                        ((SyncSet) obj).backingMap.keySet());
            }
            if (obj instanceof Collection) {
                return new HashSet<T>(this).equals(obj);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return backingMap.hashCode();
        }
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
    @SuppressWarnings("null")
    @Nonnull
    public static <T> Set<T> getCopyOnRequestSetFromMutableCollection(
            @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<T>(source, true);
    }

    /**
     * @param source
     *        the source collection
     * @return copy on request that does not build a list immediately
     * @param <T>
     *        axiom type
     */
    @SuppressWarnings("null")
    @Nonnull
    public static <T> Set<T> getCopyOnRequestSetFromImmutableCollection(
            @Nullable Collection<T> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }
        return new ConditionalCopySet<T>(source, false);
    }

    /**
     * @param source
     *        initial values
     * @return a threadsafe copy on request set
     * @param <T>
     *        axiom type
     */
    @Nonnull
    public static <T> Set<T> getThreadSafeCopyOnRequestSet(
            @Nonnull Set<T> source) {
        return new ThreadSafeConditionalCopySet<T>(source);
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

        private static final int maxContains = 10;
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
                this.delegate = new ArrayList<T>(source);
            } else {
                this.delegate = source;
            }
        }

        @SuppressWarnings("rawtypes")
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
                        .containsAll(((ConditionalCopySet) obj).delegate)
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
        public boolean add(T arg0) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
            return delegate.add(arg0);
        }

        @Override
        public boolean addAll(Collection<? extends T> arg0) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
            return delegate.addAll(arg0);
        }

        @Override
        public void clear() {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>();
            }
            delegate.clear();
        }

        @Override
        public boolean contains(Object arg0) {
            containsCounter++;
            if (containsCounter >= maxContains && !copyDone) {
                checkDelegate();
            }
            return delegate.contains(arg0);
        }

        private void checkDelegate() {
            // many calls to contains, inefficient if the delegate is not a
            // set
            if (!(delegate instanceof Set)) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
        }

        @Override
        public boolean containsAll(Collection<?> arg0) {
            containsCounter++;
            if (containsCounter >= maxContains && !copyDone) {
                checkDelegate();
            }
            return delegate.containsAll(arg0);
        }

        @Override
        public boolean isEmpty() {
            return delegate.isEmpty();
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Iterator<T> iterator() {
            return delegate.iterator();
        }

        @Override
        public boolean remove(Object arg0) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
            return delegate.remove(arg0);
        }

        @Override
        public boolean removeAll(Collection<?> arg0) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
            return delegate.removeAll(arg0);
        }

        @Override
        public boolean retainAll(Collection<?> arg0) {
            if (!copyDone) {
                copyDone = true;
                delegate = new LinkedHashSet<T>(delegate);
            }
            return delegate.retainAll(arg0);
        }

        @Override
        public int size() {
            return delegate.size();
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Object[] toArray() {
            return delegate.toArray();
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public <Type> Type[] toArray(Type[] arg0) {
            return delegate.toArray(arg0);
        }
    }

    /**
     * this class behaves like ConditionalCopySet except it is designed to be
     * threadsafe; multiple thread access is regulated by a readwritelock;
     * modifications will create a copy based on SyncSet.
     * 
     * @param <T>
     *        the type contained
     */
    public static class ThreadSafeConditionalCopySet<T> implements Set<T> {

        private static final int maxContains = 10;
        private final AtomicBoolean copyDone = new AtomicBoolean(false);
        @Nonnull
        private Collection<T> delegate;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();
        private final AtomicInteger containsCounter = new AtomicInteger(0);

        /**
         * @param source
         *        initial values
         */
        public ThreadSafeConditionalCopySet(@Nonnull Collection<T> source) {
            this.delegate = new ArrayList<T>(checkNotNull(source));
        }

        @Override
        public boolean equals(Object obj) {
            try {
                readLock.lock();
                if (obj == null) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                if (obj instanceof ConditionalCopySet) {
                    return delegate
                            .equals(((ConditionalCopySet<?>) obj).delegate);
                }
                if (obj instanceof Set) {
                    return delegate.equals(obj);
                }
                return false;
            } finally {
                readLock.unlock();
            }
        }

        @Override
        public int hashCode() {
            try {
                readLock.lock();
                return delegate.hashCode();
            } finally {
                readLock.unlock();
            }
        }

        @Override
        public boolean add(T arg0) {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>(delegate);
                }
                return delegate.add(arg0);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public boolean addAll(Collection<? extends T> arg0) {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>(delegate);
                }
                return delegate.addAll(arg0);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public void clear() {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>();
                }
                delegate.clear();
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public boolean contains(Object arg0) {
            /*
             * note: the check on the number of contains and on the copyDone
             * flag is intentionally not made inside a lock; these operations
             * will not stop other threads from accessing the collection, and
             * the lack of synchronization means that a few contains/containsAll
             * calls might not be recorded; the result is that the copy
             * optimization would be delayed. This is not an issue, since
             * maxContains is only a rough estimate.
             */
            if (containsCounter.incrementAndGet() >= maxContains
                    && !copyDone.get()) {
                try {
                    writeLock.lock();
                    // many calls to contains, inefficient if the delegate
                    // is not a set
                    // copyDone is doublechecked, but here it's protected by
                    // the write
                    // lock as in all other instances in which its value is
                    // changed
                    if (!(delegate instanceof Set)) {
                        if (!copyDone.getAndSet(true)) {
                            delegate = new SyncSet<T>(delegate);
                        }
                    }
                    // skip the second portion of the method: no need to
                    // reacquire
                    // the lock, it's already a write lock
                    return delegate.contains(arg0);
                } finally {
                    writeLock.unlock();
                }
            }
            try {
                readLock.lock();
                return delegate.contains(arg0);
            } finally {
                readLock.unlock();
            }
        }

        @Override
        public boolean containsAll(Collection<?> arg0) {
            if (containsCounter.incrementAndGet() >= maxContains
                    && !copyDone.get()) {
                try {
                    writeLock.lock();
                    // many calls to contains, inefficient if the delegate
                    // is not a set
                    // copyDone is doublechecked, but here it's protected by
                    // the write
                    // lock as in all other instances in which its value is
                    // changed
                    if (!(delegate instanceof Set)) {
                        if (!copyDone.getAndSet(true)) {
                            delegate = new SyncSet<T>(delegate);
                        }
                    }
                    // skip the second portion of the method: no need to
                    // reacquire
                    // the lock, it's already a write lock
                    return delegate.containsAll(arg0);
                } finally {
                    writeLock.unlock();
                }
            }
            try {
                readLock.lock();
                return delegate.containsAll(arg0);
            } finally {
                readLock.unlock();
            }
        }

        @Override
        public boolean isEmpty() {
            try {
                readLock.lock();
                return delegate.isEmpty();
            } finally {
                readLock.unlock();
            }
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Iterator<T> iterator() {
            try {
                readLock.lock();
                return delegate.iterator();
            } finally {
                readLock.unlock();
            }
        }

        @Override
        public boolean remove(Object arg0) {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>(delegate);
                }
                return delegate.remove(arg0);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public boolean removeAll(Collection<?> arg0) {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>(delegate);
                }
                return delegate.removeAll(arg0);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public boolean retainAll(Collection<?> arg0) {
            try {
                writeLock.lock();
                if (!copyDone.getAndSet(true)) {
                    delegate = new SyncSet<T>(delegate);
                }
                return delegate.retainAll(arg0);
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public int size() {
            try {
                readLock.lock();
                return delegate.size();
            } finally {
                readLock.unlock();
            }
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public Object[] toArray() {
            try {
                readLock.lock();
                return delegate.toArray();
            } finally {
                readLock.unlock();
            }
        }

        @SuppressWarnings("null")
        @Nonnull
        @Override
        public <Type> Type[] toArray(Type[] arg0) {
            try {
                readLock.lock();
                return delegate.toArray(arg0);
            } finally {
                readLock.unlock();
            }
        }
    }
}
