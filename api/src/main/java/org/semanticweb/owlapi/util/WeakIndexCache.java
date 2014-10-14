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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * A weakly linked cache - elements in the cache can be garbage collected.
 * 
 * @param <K>
 *        key type
 * @param <V>
 *        value type
 */
public class WeakIndexCache<K, V> implements Serializable {

    private static final long serialVersionUID = 40000L;
    protected transient Map<K, WeakReference<V>> prefixCache = CollectionFactory
            .createSyncWeakMap();

    /**
     * @param s
     *        the cache key
     * @param v
     *        the cache value
     * @return the cached value
     */
    @Nonnull
    public V cache(@Nonnull K s, @Nonnull V v) {
        WeakReference<V> w = prefixCache.get(s);
        if (w != null) {
            V toReturn = w.get();
            if (toReturn != null) {
                return toReturn;
            }
        }
        // need to add the new key and return it
        prefixCache.put(s, new WeakReference<>(v));
        return v;
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        prefixCache = CollectionFactory.createSyncWeakMap();
    }

    /**
     * @param k
     *        the key
     * @return the value
     */
    public V get(K k) {
        WeakReference<V> w = prefixCache.get(k);
        if (w != null) {
            return w.get();
        }
        return null;
    }

    /**
     * @param k
     *        the key to check
     * @return true if the cache contains k as a key; note that, due to the
     *         nature of this cache, by the time the method returns the key may
     *         no longer be in the map.
     */
    public boolean contains(K k) {
        WeakReference<V> w = prefixCache.get(k);
        if (w != null) {
            V toReturn = w.get();
            if (toReturn != null) {
                return true;
            }
        }
        return false;
    }

    /** empty the cache. */
    public void clear() {
        prefixCache.clear();
    }
}
