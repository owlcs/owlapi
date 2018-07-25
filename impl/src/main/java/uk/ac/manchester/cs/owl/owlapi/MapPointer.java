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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

import com.carrotsearch.hppcrt.cursors.ObjectCursor;
import com.carrotsearch.hppcrt.maps.ObjectObjectHashMap;
import com.carrotsearch.hppcrt.procedures.ObjectProcedure;
import com.carrotsearch.hppcrt.sets.ObjectHashSet;

import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitCollectionVisitor;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitVisitor;
import uk.ac.manchester.cs.owl.owlapi.util.collections.SmallSet;

/**
 * * Objects that identify contained maps - so that getting the keys of a specific map does not
 * require a specific method for each map nor does it require the map to be copied and returned.
 * 
 * @author ignazio
 * @param <K> key
 * @param <V> value
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
    private final ObjectObjectHashMap<K, Collection<V>> map = new ObjectObjectHashMap<>(17, 0.75F);
    private boolean neverTrimmed = true;

    /**
     * @param t type of axioms contained
     * @param v visitor
     * @param initialized true if initialized
     * @param i internals containing this pointer
     */
    public MapPointer(@Nullable AxiomType<?> t, @Nullable OWLAxiomVisitorEx<?> v,
        boolean initialized, @Nonnull Internals i) {
        type = t;
        visitor = v;
        this.initialized = initialized;
        this.i = checkNotNull(i, "i cannot be null");
    }

    /**
     * @param e entity
     * @return true if an entity with the same iri as the input exists in the collection
     */
    public synchronized boolean containsReference(K e) {
        return map.containsKey(e);
    }

    /**
     * @param e IRI
     * @return true if an entity with the same iri as the input exists in the collection
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
        Consumer<ObjectCursor<K>> k = q -> consumer(set, q);
        map.keys().forEach(k);
        iris = new SoftReference<>(set);
        return set;
            }

    protected void consumer(Set<IRI> set, ObjectCursor<K> k) {
        if (k.value instanceof OWLEntity) {
            set.add(((OWLEntity) k.value).getIRI());
        } else if (k.value instanceof IRI) {
            set.add((IRI) k.value);
        }
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
        List<K> keySet = new ArrayList<>();
        ObjectProcedure<K> predicate = keySet::add;
        map.keys().forEach(predicate);
        assert keySet != null;
        return keySet;
    }

    /**
     * @param key key to look up
     * @return value
     */
    @Nonnull
    public synchronized List<V> getValues(K key) {
        init();
        return get(key);
    }

    /**
     * @param <T> type of key
     * @param filter filter to satisfy
     * @param key key
     * @return set of values
     */
    @Nonnull
    public synchronized <T> Collection<OWLAxiom> filterAxioms(@Nonnull OWLAxiomSearchFilter filter,
        @Nonnull T key) {
        init();
        List<OWLAxiom> toReturn = new ArrayList<>();
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            Collection<V> collection = map.get((K) at);
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
     * @param key key to look up
     * @return true if there are values for key
     */
    public synchronized boolean hasValues(K key) {
        init();
        return map.containsKey(key);
    }

    /**
     * @param key key to add
     * @param value value to add
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
     * @param key key to look up
     * @param value value to remove
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
     * @param key key to look up
     * @return true if there are values for key
     */
    public synchronized boolean containsKey(K key) {
        init();
        return map.containsKey(key);
    }

    /**
     * @param key key to look up
     * @param value value to look up
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
            // trimToSize();
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

    private boolean putInternal(@Nullable K k, V v) {
        if (k == null) {
            return false;
        }
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
                set = new HPPCSet(set, v);
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
            // trimToSize();
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

    @Nonnull
    private Iterable<V> values() {
        List<V> values = new ArrayList<>();
        ObjectProcedure<? super Collection<V>> p = values::addAll;
        map.values().forEach(p);
        return values;
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
     * Trim internal map to size.
     */
    public synchronized void trimToSize() {
        // if (initialized) {
        // map.trimToSize();
        // neverTrimmed = false;
        // for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
        // Collection<V> set = entry.getValue();
        // if (set instanceof ArrayList) {
        // THashSet<V> value =
        // new THashSet<>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        // value.addAll(set);
        // entry.setValue(value);
        // size = size - set.size() + value.size();
        // value.trimToSize();
        // } else if (set instanceof THashSet) {
        // THashSet<V> vs = (THashSet<V>) set;
        // vs.trimToSize();
        // totalInUse.addAndGet(set.size());
        // totalAllocated.addAndGet(vs.capacity());
        // } else if (set instanceof SmallSet<?>) {
        // totalInUse.addAndGet(set.size());
        // totalAllocated.addAndGet(3);
        // } else {
        // totalInUse.addAndGet(1);
        // totalAllocated.addAndGet(1);
        // }
        // }
        // }
    }
}


class HPPCSet<S> implements Collection<S> {
    private ObjectHashSet<S> delegate;

    public HPPCSet() {
        delegate = new ObjectHashSet<>();
    }

    public HPPCSet(int initialCapacity) {
        delegate = new ObjectHashSet<>(initialCapacity);
    }

    public HPPCSet(int initialCapacity, double loadFactor) {
        delegate = new ObjectHashSet<>(initialCapacity, loadFactor);
    }

    public HPPCSet(Collection<S> container) {
        delegate = new ObjectHashSet<>(container.size() + 1);
        addAll(container);
    }

    public HPPCSet(Collection<S> container, S s) {
        delegate = new ObjectHashSet<>(container.size() + 1);
        addAll(container);
        add(s);
            }

    @Override
    public int size() {
        return delegate.size();
        }

        @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
            }

    @Override
    public boolean contains(@Nullable Object o) {
        return delegate.contains((S) o);
        }

    @Override
    public Iterator<S> iterator() {
        final ObjectHashSet<S>.EntryIterator iterator = delegate.iterator();
        return new Iterator<S>() {

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
    }

            @Override
            public S next() {
                return iterator.next().value;
        }
        };
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <T> T[] toArray(@Nullable T[] a) {
        throw new UnsupportedOperationException("Not suppoerted for " + getClass());
        }

    @Override
    public boolean add(@Nullable S e) {
        return delegate.add(e);
    }

    @Override
    public boolean remove(@Nullable Object o) {
        return delegate.remove((S) o);
    }

    @Override
    public boolean containsAll(@Nullable Collection<?> c) {
        for (Object o : c) {
            if (!delegate.contains((S) o)) {
                return false;
            }
        }
        return true;
                }

    @Override
    public boolean addAll(@Nullable Collection<? extends S> c) {
        boolean toReturn = false;
        for (S s : c) {
            if (add(s)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    @Override
    public boolean removeAll(@Nullable Collection<?> c) {
        boolean toReturn = false;
        for (Object s : c) {
            if (remove(s)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    @Override
    public boolean retainAll(@Nullable Collection<?> c) {
        return delegate.retainAll(new HPPCSet(verifyNotNull(c)).delegate) > 0;
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }
}
