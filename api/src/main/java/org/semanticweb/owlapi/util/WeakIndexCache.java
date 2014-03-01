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
 *        key type
 * @param <V>
 *        value type
 */
public class WeakIndexCache<K, V> implements Serializable {

    private static final long serialVersionUID = 30406L;
    protected transient Map<K, WeakReference<V>> prefixCache = CollectionFactory
            .createSyncWeakMap();

    /**
     * @param s
     *        the cache key
     * @param v
     *        the cache value
     * @return the cached value
     */
    public V cache(K s, V v) {
        WeakReference<V> w = prefixCache.get(s);
        if (w != null) {
            V toReturn = w.get();
            if (toReturn != null) {
                return toReturn;
            }
        }
        // need to add the new key and return it
        prefixCache.put(s, new WeakReference<V>(v));
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
            V toReturn = w.get();
            return toReturn;
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
