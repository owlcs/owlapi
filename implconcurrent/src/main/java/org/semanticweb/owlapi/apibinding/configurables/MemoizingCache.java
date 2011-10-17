/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.apibinding.configurables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @author ignazio
 *
 *Cache where values computation is carried out with Computables and multithread safe - no stale data, no multiple computations for the same value
 *
 * @param <A> type of key
 * @param <V> type of value
 */
public class MemoizingCache<A, V> implements Map<A, V> {
	private final ConcurrentHashMap<A, FutureTask<V>> cache = CollectionFactory
			.createSyncMap();

	/**
	 * @param computant the object that carries out the copmutation
	 * @param key the key
	 * @return the computed value
	 */
	public V get(final Computable<V> computant, final A key) {
		while (true) {
			FutureTask<V> f = cache.get(key);
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					public V call() {
						V compute = computant.compute();
						return compute;
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(key, ft);
				if (f == null) {
					f = ft;
					ft.run();
				}
			}
			try {
				return f.get();
			} catch (CancellationException e) {
				cache.remove(key, f);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				cache.remove(key);
				throw new RuntimeException("Unexpected interrupted exception", e);
			}
		}
	}

	/**
	 * @param computed the value
	 * @param key the key
	 * @return computed
	 */
	public V get(final V computed, final A key) {
		while (true) {
			FutureTask<V> f = cache.get(key);
			if (f == null) {
				Callable<V> eval = new Callable<V>() {
					public V call() {
						return computed;
					}
				};
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(key, ft);
				if (f == null) {
					f = ft;
					ft.run();
				}
			}
			try {
				return f.get();
			} catch (CancellationException e) {
				cache.remove(key, f);
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			} catch (InterruptedException e) {
				cache.remove(key);
				throw new RuntimeException("Unexpected interrupted exception", e);
			}
		}
	}

	public void clear() {
		this.cache.clear();
	}

	public boolean containsKey(Object key) {
		return this.cache.containsKey(key);
	}

	public boolean containsValue(Object value) {
		for (Future<V> f : cache.values()) {
			try {
				if (f.get().equals(value)) {
					return true;
				}
			} catch (InterruptedException e) {
				// nothing to do here
			} catch (ExecutionException e) {
				// nothing to do here
			}
		}
		return false;
	}

	public Set<java.util.Map.Entry<A, V>> entrySet() {
		throw new UnsupportedOperationException("EntrySet not available");
	}

	public V get(Object key) {
		if (!cache.containsKey(key)) {
			return null;
		}
		// the run for the future task is supposed to have already been performed, so no exceptions are expected or managed here
		try {
			FutureTask<V> futureTask = cache.get(key);
			if (futureTask != null) {
				return futureTask.get();
			} else {
				throw new NullPointerException("Unexpected null value in the map: key was expected to be contained and checked in this method, but is no longer contained in the map");
			}
		} catch (CancellationException e) {
			throw new RuntimeException(e);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public Set<A> keySet() {
		return cache.keySet();
	}

	/**
	 * This method should not be used for a MemoizingCache; the preferred way is
	 * to provide a Computable and use the get(Computable, A) method Notice that
	 * the returned value might not be the last previous stored value but one of
	 * the values stored before the new value
	 */
	public V put(A key, final V value) {
		V toReturn = this.get(key);
		this.get(value, key);
		return toReturn;
	}

	@SuppressWarnings("unused")
	public void putAll(Map<? extends A, ? extends V> t) {
		throw new UnsupportedOperationException(
				"Adding values must be done through the get(Computable<A,V>, A) method");
	}

	public V remove(Object key) {
		V f = get(key);
		cache.remove(key);
		return f;
	}

	public int size() {
		return cache.size();
	}

	public Collection<V> values() {
		List<V> toReturn = new ArrayList<V>();
		for (A key : cache.keySet()) {
			toReturn.add(get(key));
		}
		return toReturn;
	}
}
