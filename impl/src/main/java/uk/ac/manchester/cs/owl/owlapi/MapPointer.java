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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

import com.google.common.collect.Iterables;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitCollectionVisitor;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitVisitor;
import uk.ac.manchester.cs.owl.owlapi.util.collections.SmallSet;

/**
 * * Objects that identify contained maps - so that getting the keys of a
 * specific map does not require a specific method for each map nor does it
 * require the map to be copied and returned.
 * 
 * @author ignazio
 * @param <K>
 *        key
 * @param <V>
 *        value
 */
public class MapPointer<K, V extends OWLAxiom> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75F;
    private static final int DEFAULT_INITIAL_CAPACITY = 5;
    @Nullable
    private final AxiomType<?> type;
    @Nullable
    private final OWLAxiomVisitorEx<?> visitor;
    private boolean initialized;
    @Nonnull
    protected final Internals i;
    private SoftReference<Set<IRI>> iris;
    private int size = 0;
    private final THashMap<K, Collection<V>> map = new THashMap<>(17, 0.75F);
    private boolean neverTrimmed = true;

    /**
     * @param t
     *        type of axioms contained
     * @param v
     *        visitor
     * @param initialized
     *        true if initialized
     * @param i
     *        internals containing this pointer
     */
    public MapPointer(@Nullable AxiomType<?> t, @Nullable OWLAxiomVisitorEx<?> v, boolean initialized,
        @Nonnull Internals i) {
        type = t;
        visitor = v;
        this.initialized = initialized;
        this.i = checkNotNull(i, "i cannot be null");
    }

    /**
     * @param e
     *        entity
     * @return true if an entity with the same iri as the input exists in the
     *         collection
     */
    public synchronized boolean containsReference(OWLEntity e) {
        return map.containsKey(e);
    }

    /**
     * @param e
     *        IRI
     * @return true if an entity with the same iri as the input exists in the
     *         collection
     */
    public synchronized boolean containsReference(IRI e) {
        Set<IRI> set = null;
        if (iris != null) {
            set = iris.get();
        }
        if (set == null) {
            set = initSet();
        }
        return set.contains(e);
    }

    private Set<IRI> initSet() {
        Set<IRI> set = CollectionFactory.createSet();
        for (K k : map.keySet()) {
            if (k instanceof OWLEntity) {
                set.add(((OWLEntity) k).getIRI());
            } else if (k instanceof IRI) {
                set.add((IRI) k);
            }
        }
        iris = new SoftReference<>(set);
        return set;
    }

    /**
     * @return true if initialized
     */
    public synchronized boolean isInitialized() {
        return initialized;
    }

    /**
     * init the map pointer
     * 
     * @return the map pointer
     */
    @SuppressWarnings({ "unchecked", "null" })
    public synchronized MapPointer<K, V> init() {
        if (initialized) {
            return this;
        }
        initialized = true;
        if (visitor == null) {
            return this;
        }
        if (visitor instanceof InitVisitor) {
            for (V ax : (Collection<V>) i.getAxiomsByType().getValues(type)) {
                K key = ax.accept((InitVisitor<K>) visitor);
                // this can only be null because the visitor return nulls in
                // methods that do not declare it
                if (key != null) {
                    putInternal(key, ax);
                }
            }
        } else {
            for (V ax : (Collection<V>) i.getAxiomsByType().getValues(type)) {
                Collection<K> keys = ax.accept((InitCollectionVisitor<K>) visitor);
                for (K key : keys) {
                    putInternal(key, ax);
                }
            }
        }
        return this;
    }

    @Nonnull
    @Override
    public synchronized String toString() {
        return initialized + map.toString();
    }

    /**
     * @return keyset
     */
    @Nonnull
    public synchronized Iterable<K> keySet() {
        init();
        Set<K> keySet = map.keySet();
        assert keySet != null;
        return keySet;
    }

    /**
     * @param key
     *        key to look up
     * @return value
     */
    @Nonnull
    public synchronized List<V> getValues(K key) {
        init();
        return get(key);
    }

    /**
     * @param <T>
     *        type of key
     * @param filter
     *        filter to satisfy
     * @param key
     *        key
     * @return set of values
     */
    @Nonnull
    public synchronized <T> Collection<OWLAxiom> filterAxioms(@Nonnull OWLAxiomSearchFilter filter, @Nonnull T key) {
        init();
        List<OWLAxiom> toReturn = new ArrayList<>();
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            Collection<V> collection = map.get(at);
            if (collection != null) {
                for (OWLAxiom ax : collection) {
                    assert ax != null;
                    if (filter.pass(ax, key)) {
                        toReturn.add(ax);
                    }
                }
            }
        }
        return toReturn;
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    public synchronized boolean hasValues(K key) {
        init();
        return map.containsKey(key);
    }

    /**
     * @param key
     *        key to add
     * @param value
     *        value to add
     * @return true if addition happens
     */
    public synchronized boolean put(K key, V value) {
        // lazy init: no elements added until a recall is made
        if (!initialized) {
            return false;
        }
        iris = null;
        return putInternal(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @param value
     *        value to remove
     * @return true if removal happens
     */
    public synchronized boolean remove(K key, V value) {
        if (!initialized) {
            return false;
        }
        iris = null;
        return removeInternal(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    @Nonnull
    public synchronized Boolean containsKey(K key) {
        init();
        return map.containsKey(key);
    }

    /**
     * @param key
     *        key to look up
     * @param value
     *        value to look up
     * @return true if key and value are contained
     */
    public synchronized boolean contains(K key, V value) {
        init();
        return containsEntry(key, value);
    }

    /**
     * @return all values contained
     */
    @Nonnull
    public synchronized Iterable<V> getAllValues() {
        init();
        return values();
    }

    /**
     * @return number of mapping contained
     */
    public synchronized int size() {
        init();
        if (neverTrimmed) {
            trimToSize();
        }
        return size;
    }

    /**
     * @return true if empty
     */
    public synchronized boolean isEmpty() {
        init();
        return size == 0;
    }

    private boolean putInternal(K k, V v) {
        Collection<V> set = map.get(k);
        if (set == null) {
            set = Collections.singleton(v);
            map.put(k, set);
            size++;
            return true;
        }
        if (set.size() == 1) {
            if (set.contains(v)) {
                return false;
            } else {
                set = new SmallSet<>(set);
                map.put(k, set);
            }
        } else if (set.size() == 3) {
            if (set.contains(v)) {
                return false;
            } else {
                set = makeSet(set, v);
                map.put(k, set);
                size++;
                return true;
            }
        }
        boolean added = set.add(v);
        if (added) {
            size++;
        }
        return added;
    }

    private boolean containsEntry(K k, V v) {
        Collection<V> t = map.get(k);
        if (t == null) {
            return false;
        }
        return t.contains(v);
    }

    private boolean removeInternal(K k, V v) {
        if (neverTrimmed) {
            trimToSize();
        }
        Collection<V> t = map.get(k);
        if (t == null) {
            return false;
        }
        if (t.size() == 1) {
            if (t.contains(v)) {
                map.remove(k);
                size--;
                return true;
            } else {
                return false;
            }
        }
        boolean removed = t.remove(v);
        if (removed) {
            size--;
        }
        if (t.isEmpty()) {
            map.remove(k);
        }
        return removed;
    }

    private static class THashSetForSet<E> extends THashSet<E> {

        private boolean constructing = true;

        public THashSetForSet(Collection<E> set, E toAdd, int capacity, float load) {
            super(capacity, load);
            for (E e : set) {
                add(e);
            }
            add(toAdd);
            constructing = false;
        }

        @Override
        protected boolean equals(Object notnull, Object two) {
            // shortcut: during construction from a set, no element is
            // duplicate. The extra element is also guaranteed to be unique,
            // given the use made in this class.
            if (constructing) {
                return notnull == two;
            }
            return super.equals(notnull, two);
        }
    }

    private Collection<V> makeSet(Collection<V> collection, V extra) {
        if (neverTrimmed) {
            List<V> list = new ArrayList<>(collection);
            list.add(extra);
            return list;
        }
        return new THashSetForSet<>(collection, extra, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @Nonnull
    private Iterable<V> values() {
        return Iterables.concat(map.values());
    }

    @Nonnull
    private List<V> get(K k) {
        Collection<V> t = map.get(k);
        if (t == null) {
            return CollectionFactory.emptyList();
        }
        return new ArrayList<>(t);
    }

    /**
     * Trims the capacity of the map entries . An application can use this
     * operation to minimize the storage of the map pointer instance.
     */
    private static AtomicLong totalInUse = new AtomicLong(0);
    private static AtomicLong totalAllocated = new AtomicLong(0);

    /**
     * Trim internal map to size.
     */
    public synchronized void trimToSize() {
        if (initialized) {
            map.trimToSize();
            neverTrimmed = false;
            for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
                Collection<V> set = entry.getValue();
                if (set instanceof ArrayList) {
                    THashSet<V> value = new THashSet<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
                    value.addAll(set);
                    entry.setValue(value);
                    size = size - set.size() + value.size();
                    value.trimToSize();
                } else if (set instanceof THashSet) {
                    THashSet<V> vs = (THashSet<V>) set;
                    vs.trimToSize();
                    totalInUse.addAndGet(set.size());
                    totalAllocated.addAndGet(vs.capacity());
                } else if (set instanceof SmallSet<?>) {
                    totalInUse.addAndGet(set.size());
                    totalAllocated.addAndGet(3);
                } else {
                    totalInUse.addAndGet(1);
                    totalAllocated.addAndGet(1);
                }
            }
        }
    }

    static void resetCounts() {
        totalAllocated.set(0);
        totalInUse.set(0);
    }

    static long getTotalInUse() {
        return totalInUse.get();
    }

    static long getTotalAllocated() {
        return totalAllocated.get();
    }
}
