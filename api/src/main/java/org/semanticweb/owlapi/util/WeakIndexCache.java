package org.semanticweb.owlapi.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/** A weakly linked cache - elements in the cache can be garbage collected */
public class WeakIndexCache<K, V> {
	protected WeakHashMap<K, WeakReference<V>> prefixCache = new WeakHashMap<K, WeakReference<V>>();

	public V cache(K s, V v) {
		WeakReference<V> w = prefixCache.get(s);
		if (w != null) {
			V toReturn = w.get();
			if (toReturn == null) {
				//entry removed - move on
			} else {
				return toReturn;
			}
		}
		// need to add the new key and return it
		//miss++;
		prefixCache.put(s, new WeakReference<V>(v));
		return v;
	}

	public V get(K k) {
		WeakReference<V> w = prefixCache.get(k);
		if (w != null) {
			V toReturn = w.get();
				return toReturn;
		}
		return null;
	}

	/**
	 * @param k the key to check
	 * @return true if the cache contains k as a key; note that, due to the nature of this cache, by the time the method returns the key may no longer be in the map.*/
	public boolean contains(K k) {
		WeakReference<V> w = prefixCache.get(k);
		if (w != null) {
			V toReturn = w.get();
			if(toReturn!=null) {
				return true;
			}
		}
		return false;
	}

	public void clear() {
		prefixCache.clear();
	}
}
