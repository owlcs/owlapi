package org.semanticweb.owlapi.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * A weakly linked cache - elements in the cache can be garbage collected.
 * 
 * @param <K>
 *        cached type
 */
public class WeakCache<K> implements Serializable {

    private static final long serialVersionUID = 30406L;
    private transient Map<K, WeakReference<K>> prefixCache = CollectionFactory
            .createSyncWeakMap();

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        in.defaultReadObject();
        prefixCache = CollectionFactory.createSyncWeakMap();
    }

    /**
     * @param s
     *        the value to cache
     * @return the cached value
     */
    public K cache(K s) {
        WeakReference<K> w = prefixCache.get(s);
        if (w != null) {
            K toReturn = w.get();
            if (toReturn != null) {
                return toReturn;
            }
        }
        // need to add the new key and return it
        prefixCache.put(s, new WeakReference<K>(s));
        return s;
    }

    /**
     * @param k
     *        the key to check
     * @return true if the cache contains k as a key; note that, due to the
     *         nature of this cache, by the time the method returns the key may
     *         no longer be in the map.
     */
    public boolean contains(K k) {
        WeakReference<K> w = prefixCache.get(k);
        if (w != null) {
            K toReturn = w.get();
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
