package org.semanticweb.owlapitools.decomposition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * @param <K>
 *        key
 * @param <V>
 *        value
 * @author ignazio palmisano
 */
public class IdentityMultiMap<K, V> implements Serializable {

    private static final long serialVersionUID = 30402L;
    private final IdentityHashMap<K, Collection<V>> map = new IdentityHashMap<>();
    private int size = 0;

    /**
     * @param key
     *        key
     * @param value
     *        value
     * @return true if changes happen
     */
    public boolean put(K key, V value) {
        Collection<V> set = this.map.get(key);
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

    private Collection<V> createCollection() {
        Collection<V> toReturn = Collections
                .newSetFromMap(new IdentityHashMap<V, Boolean>());
        return toReturn;
    }

    /**
     * @param key
     *        key
     * @param values
     *        values
     */
    public void setEntry(K key, Collection<V> values) {
        this.map.put(key, values);
        this.size = -1;
    }

    /**
     * returns a mutable set of values connected to the key; if no value is
     * connected, returns an immutable empty set
     * 
     * @param key
     *        key
     * @return the set of values connected with the key
     */
    public Collection<V> get(K key) {
        final Collection<V> collection = this.map.get(key);
        if (collection != null) {
            return collection;
        }
        return Collections.emptyList();
    }

    /** @return the set of keys */
    public Set<K> keySet() {
        return this.map.keySet();
    }

    /** @return all values in the map */
    public Set<V> getAllValues() {
        Set<V> toReturn = CollectionFactory.createSet();
        for (Collection<V> s : this.map.values()) {
            toReturn.addAll(s);
        }
        return toReturn;
    }

    /**
     * removes the set of values connected to the key
     * 
     * @param key
     *        key
     * @return true if changes made
     */
    public boolean remove(K key) {
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
     *        key
     * @param value
     *        value
     * @return true if changes made
     */
    public boolean remove(K key, V value) {
        Collection<V> c = this.map.get(key);
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

    /** @return the size of the multimap (sum of all the sizes of the sets) */
    public int size() {
        if (size < 0) {
            size = getAllValues().size();
        }
        return this.size;
    }

    /**
     * @param k
     *        key
     * @param v
     *        value
     * @return true if the pairing (k, v) is in the map (set equality for v)
     */
    public boolean contains(K k, V v) {
        final Collection<V> collection = this.map.get(k);
        if (collection == null) {
            return false;
        }
        return collection.contains(v);
    }

    /**
     * @param k
     *        key
     * @return true if k is a key for the map
     */
    public boolean containsKey(K k) {
        return this.map.containsKey(k);
    }

    /**
     * @param v
     *        value
     * @return true if v is a value for a key in the map
     */
    public boolean containsValue(V v) {
        for (Collection<V> c : map.values()) {
            if (c.contains(v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     */
    public void clear() {
        this.map.clear();
        this.size = 0;
    }

    @Override
    public String toString() {
        return "MultiMap " + size() + "\n" + map.toString();
    }

    /**
     * @param otherMap
     *        otherMap
     */
    public void putAll(IdentityMultiMap<K, V> otherMap) {
        for (K k : otherMap.keySet()) {
            putAll(k, otherMap.get(k));
        }
    }

    /**
     * @param k
     *        k
     * @param v
     *        v
     */
    public void putAll(K k, Collection<V> v) {
        Collection<V> set = map.get(k);
        if (set == null) {
            set = createCollection();
            setEntry(k, set);
        }
        set.addAll(v);
        size = -1;
    }

    /** @return true if duplicate value sets */
    public boolean isValueSetsEqual() {
        if (map.size() < 2) {
            return true;
        }
        List<Collection<V>> list = new ArrayList<>(map.values());
        for (int i = 1; i < list.size(); i++) {
            if (!list.get(0).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }
}
