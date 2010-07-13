package org.semanticweb.owlapi.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br>
 * <br>
 */
public class CollectionFactory {
	public static <T> Set<T> createSet() {
		return new HashSet<T>();
	}

	public static <T> Set<T> createSet(Collection<T> c) {
		return new HashSet<T>(c);
	}

	public static <T> Set<T> createSet(int initialCapacity) {
		return new HashSet<T>(initialCapacity);
	}

	public static <K, V> Map<K, V> createMap() {
		return new HashMap<K, V>();
	}

	public static <T> Set<T> createSet(T... elements) {
		Set<T> result = createSet();
		for (T t : elements) {
			result.add(t);
		}
		return result;
	}
	
	public static <T> Set<T> createSyncSet(){
		return new SyncSet<T>();
	}
	
	public static <K,V> Map<K,V> createSyncMap(){
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * this class implements a Set using a ConcurrentHashMap as backing
	 * structure; compared to the default HashSet implementation, this structure
	 * is threadsafe without being completely synchronized, so it offers better
	 * performances
	 */
	private static final class SyncSet<T> implements Set<T> {
		private final ConcurrentHashMap<T, Set<T>> backingMap;

		public SyncSet(ConcurrentHashMap<T, Set<T>> map) {
			backingMap = map;
		}

		public SyncSet() {
			this(new ConcurrentHashMap<T, Set<T>>());
		}

		public boolean add(T e) {
			return backingMap.put(e, this) == null;
		}

		public boolean addAll(Collection<? extends T> c) {
			boolean toReturn = false;
			for (T o : c) {
				toReturn = toReturn || add(o);
			}
			return toReturn;
		}

		public void clear() {
			backingMap.clear();
		}

		public boolean contains(Object o) {
			return backingMap.containsKey(o);
		}

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

		public boolean isEmpty() {
			return backingMap.isEmpty();
		}

		public Iterator<T> iterator() {
			return backingMap.keySet().iterator();
		}

		public int size() {
			return backingMap.size();
		}

		public boolean remove(Object o) {
			return backingMap.remove(o) != null;
		}

		public boolean removeAll(Collection<?> c) {
			boolean toReturn = false;
			for (Object o : c) {
				toReturn = toReturn || remove(o);
			}
			return toReturn;
		}

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

		public Object[] toArray() {
			return backingMap.keySet().toArray();
		}

		public <T> T[] toArray(T[] a) {
			return backingMap.keySet().toArray(a);
		}
	}
}
