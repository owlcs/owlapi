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
package org.semanticweb.owlapi6.utility;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class CollectionFactory {

    private static final AtomicInteger EXPECTEDTHREADS = new AtomicInteger(8);

    private CollectionFactory() {}

    /**
     * @return The current number of expected threads.
     */
    public static int getExpectedThreads() {
        return EXPECTEDTHREADS.get();
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

    /**
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet() {
        return new HashSet<>(0);
    }

    /**
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createLinkedSet() {
        return new LinkedHashSet<>(0);
    }

    /**
     * @param c
     *        values to add to the set
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(Collection<T> c) {
        return new HashSet<>(c);
    }

    /**
     * @param initialCapacity
     *        initial capacity for the new set
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return fresh map
     */
    public static <K, V> Map<K, V> createMap() {
        return new HashMap<>();
    }

    /**
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return a new weak hashmap wrapped as a synchronized map
     */
    public static <K, V> Map<K, WeakReference<V>> createSyncWeakMap() {
        return Collections.synchronizedMap(new WeakHashMap<K, WeakReference<V>>());
    }

    /**
     * @param elements
     *        values to add to the set
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    @SafeVarargs
    public static <T> Set<T> createSet(T... elements) {
        return new HashSet<>(Arrays.asList(elements));
    }

    /**
     * @param element
     *        value to add to the set
     * @param <T>
     *        axiom type
     * @return fresh non threadsafe set
     */
    public static <T> Set<T> createSet(@Nullable T element) {
        Set<T> result = createSet();
        if (element != null) {
            result.add(element);
        }
        return result;
    }

    /**
     * @param <T>
     *        set type
     * @return fresh threadsafe set
     */
    public static <T> Set<T> createSyncSet() {
        return Collections.newSetFromMap(createSyncMap());
    }

    /**
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return fresh threadsafe hashmap
     */
    public static <K, V> ConcurrentMap<K, V> createSyncMap() {
        return new ConcurrentHashMap<>(16, 0.75F, EXPECTEDTHREADS.get());
    }
}
