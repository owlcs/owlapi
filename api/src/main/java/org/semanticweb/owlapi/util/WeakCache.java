package org.semanticweb.owlapi.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/** A weakly linked cache - elements in the cache can be garbage collected */
public class WeakCache<K> {
	private WeakHashMap<K, WeakReference<K>> prefixCache = new WeakHashMap<K, WeakReference<K>>();

	public K cache(K s) {
		WeakReference<K> w = prefixCache.get(s);
		if (w != null) {
			K toReturn = w.get();
			if (toReturn == null) {
				//entry removed - move on
			} else {
				return toReturn;
			}
		}
		// need to add the new key and return it
		//miss++;
		prefixCache.put(s, new WeakReference<K>(s));
		return s;
	}

	public void clear() {
		prefixCache.clear();

	}
}
