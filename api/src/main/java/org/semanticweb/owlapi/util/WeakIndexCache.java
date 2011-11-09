package org.semanticweb.owlapi.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/** A weakly linked cache - elements in the cache can be garbage collected */
public class WeakIndexCache<K, V> {
	private WeakHashMap<K, WeakReference<V>> prefixCache = new WeakHashMap<K, WeakReference<V>>();

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
}
