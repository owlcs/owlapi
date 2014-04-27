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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitCollectionVisitor;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitVisitor;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

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
public class MapPointer<K, V extends OWLAxiom> implements Serializable {

    private static final long serialVersionUID = 40000L;
    private final Multimap<K, V> map;
    @Nullable
    private final AxiomType<?> type;
    @Nullable
    private final OWLAxiomVisitorEx<?> visitor;
    private boolean initialized;
    @Nonnull
    protected final Internals i;

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
    public MapPointer(@Nullable AxiomType<?> t,
            @Nullable OWLAxiomVisitorEx<?> v, boolean initialized,
            @Nonnull Internals i) {
        type = t;
        visitor = v;
        map = LinkedHashMultimap.create();
        this.initialized = initialized;
        this.i = checkNotNull(i, "i cannot be null");
    }

    /** @return true if initialized */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * init the map pointer
     * 
     * @return the map pointer
     */
    @SuppressWarnings({ "unchecked", "null" })
    public MapPointer<K, V> init() {
        if (initialized) {
            return this;
        }
        initialized = true;
        if (visitor == null) {
            return this;
        }
        assert visitor != null;
        if (visitor instanceof InitVisitor) {
            for (V ax : (Set<V>) i.getAxiomsByType().getValues(type)) {
                K key = ax.accept((InitVisitor<K>) visitor);
                // this can only be null because the visitor return nulls in
                // methods that do not declare it
                if (key != null) {
                    map.put(key, ax);
                }
            }
        } else {
            for (V ax : (Set<V>) i.getAxiomsByType().getValues(type)) {
                Collection<K> keys = ax
                        .accept((InitCollectionVisitor<K>) visitor);
                for (K key : keys) {
                    map.put(key, ax);
                }
            }
        }
        return this;
    }

    @Nonnull
    @Override
    public String toString() {
        return initialized + map.toString();
    }

    /** @return keyset */
    @Nonnull
    public Set<K> keySet() {
        init();
        return CollectionFactory.getCopyOnRequestSetFromMutableCollection(map
                .keySet());
    }

    /**
     * @param key
     *        key to look up
     * @return value
     */
    @Nonnull
    public Set<V> getValues(K key) {
        init();
        return CollectionFactory.getCopyOnRequestSetFromMutableCollection(map
                .get(key));
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    public boolean hasValues(K key) {
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
    public boolean put(K key, V value) {
        // lazy init: no elements added until a recall is made
        if (!initialized) {
            return false;
        }
        return map.put(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @param value
     *        value to remove
     * @return true if removal happens
     */
    public boolean remove(K key, V value) {
        if (!initialized) {
            return false;
        }
        return map.remove(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    public boolean containsKey(K key) {
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
    public boolean contains(K key, V value) {
        init();
        return map.containsEntry(key, value);
    }

    /** @return all values contained */
    @Nonnull
    public Set<V> getAllValues() {
        init();
        return new HashSet<V>(map.values());
    }

    /** @return number of mapping contained */
    public int size() {
        init();
        return map.size();
    }

    /** @return true if empty */
    public boolean isEmpty() {
        init();
        return map.size() == 0;
    }
}
