package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author ignazio palmisano
 *
 * @param <Key>
 * @param <Value>
 */
public class MultiMap<Key, Value> {
	private final Map<Key, Collection<Value>> map;
	private int size = 0;
	private boolean useSets = true;
	private final boolean threadSafe;

	public MultiMap(boolean threadsafe) {
		threadSafe = threadsafe;
		if (threadSafe) {
			map = CollectionFactory.createSyncMap();
		} else {
			map = CollectionFactory.createMap();
		}
	}

	public MultiMap(boolean threadsafe, boolean usesets) {
		this(threadsafe);
		this.useSets = usesets;
	}

	/**
	 * @param key
	 * @param value
	 */
	public boolean put(Key key, Value value) {
		Collection<Value> set = this.map.get(key);
		if (set == null) {
			set = createCollection();
			this.map.put(key, set);
		}
		boolean toReturn = set.add(value);
		if (toReturn) {
			size = -1;
		}
		return toReturn;
	}

	private Collection<Value> createCollection() {
		return useSets ? (threadSafe ? CollectionFactory.<Value> createSyncSet()
				: CollectionFactory.<Value> createSet()) : (threadSafe ? Collections
				.synchronizedList(new ArrayList<Value>()) : new ArrayList<Value>());
	}

	/**
	 * @param key
	 * @param values
	 */
	public void setEntry(Key key, Set<Value> values) {
		//		if (this.map.containsKey(key)) {
		//			this.size = this.size - this.map.get(key).size();
		//		}
		this.map.put(key, values);
		this.size = -1;
		// this.size + values.size();
	}

	/**
	 * returns a mutable set of values connected to the key; if no value is
	 * connected, returns an immutable empty set
	 *
	 * @param key
	 * @return the set of values connected with the key
	 */
	public Collection<Value> get(Key key) {
		final Collection<Value> collection = this.map.get(key);
		if (collection != null) {
			return collection;
		}
		return Collections.emptyList();
	}

	/**
	 * @return the set of keys
	 */
	public Set<Key> keySet() {
		return this.map.keySet();
	}

	/**
	 * @return all values in the map
	 */
	public Set<Value> getAllValues() {
		Set<Value> toReturn = CollectionFactory.createSet();
		for (Collection<Value> s : this.map.values()) {
			toReturn.addAll(s);
		}
		return toReturn;
	}

	/**
	 * removes the set of values connected to the key
	 *
	 * @param key
	 */
	public boolean remove(Key key) {
		if (this.map.remove(key) != null) {
			size = -1;
			return true;
		}
		return false;
	}

	/**
	 * removes the value connected to the key; if there is more than one value
	 * connected to the key, only one is removed
	 *
	 * @param key
	 * @param value
	 */
	public boolean remove(Key key, Value value) {
		Collection<Value> c = this.map.get(key);
		if (c != null) {
			boolean toReturn = c.remove(value);
			// if false, no change was actually made - skip the rest
			if (!toReturn) {
				return false;
			}
			size = -1;
			if (c.isEmpty()) {
				this.map.remove(key);
			}
			return true;
		}
		return false;
	}

	/**
	 * @return the size of the multimap (sum of all the sizes of the sets)
	 */
	public int size() {
		if (size < 0) {
			size = getAllValues().size();
		}
		return this.size;
	}

	/**
	 * @param k
	 * @param v
	 * @return true if the pairing (k, v) is in the map (set equality for v)
	 */
	public boolean contains(Key k, Value v) {
		final Collection<Value> collection = this.map.get(k);
		if (collection == null) {
			return false;
		}
		return collection.contains(v);
	}

	/**
	 * @param k
	 *
	 * @return true if k is a key for the map
	 */
	public boolean containsKey(Key k) {
		return this.map.containsKey(k);
	}

	public void clear() {
		this.map.clear();
		this.size = 0;
	}
}
