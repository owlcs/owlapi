package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.MultiMap;

import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitCollectionVisitor;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.InitVisitor;

/**
 * MAp implementation for a pointer
 * 
 * @author ignazio
 * @param <K>
 *        key
 * @param <V>
 *        value
 */
public class MapPointer<K, V extends OWLAxiom> implements
        Internals.Pointer<K, V>, Serializable {

    private static final long serialVersionUID = 30406L;
    private final MultiMap<K, V> map;
    private final AxiomType<?> type;
    private final OWLAxiomVisitorEx<?> visitor;
    private boolean initialized;
    protected final Internals i;

    /**
     * @param t
     *        type of axioms contained
     * @param v
     *        visitor
     * @param initialized
     *        true if initialized
     * @param i
     *        internals containing this pointer
     */
    public MapPointer(AxiomType<?> t, OWLAxiomVisitorEx<?> v,
            boolean initialized, Internals i) {
        type = t;
        visitor = v;
        map = new MultiMap<K, V>();
        this.initialized = initialized;
        this.i = i;
    }

    /** @return true if initialized */
    public boolean isInitialized() {
        return initialized;
    }

    /** init the map pointer */
    @SuppressWarnings("unchecked")
    public void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        if (visitor == null) {
            return;
        }
        if (visitor instanceof InitVisitor) {
            for (V ax : (Set<V>) i.getValues(i.getAxiomsByType(), type)) {
                K key = ax.accept((InitVisitor<K>) visitor);
                if (key != null) {
                    map.put(key, ax);
                }
            }
        } else {
            for (V ax : (Set<V>) i.getValues(i.getAxiomsByType(), type)) {
                Collection<K> keys = ax
                        .accept((InitCollectionVisitor<K>) visitor);
                for (K key : keys) {
                    map.put(key, ax);
                }
            }
        }
    }

    @Override
    public String toString() {
        return initialized + map.toString();
    }

    /** @return keyset */
    public Set<K> keySet() {
        return CollectionFactory.getCopyOnRequestSetFromMutableCollection(map
                .keySet());
    }

    /**
     * @param key
     *        key to look up
     * @return value
     */
    public Set<V> getValues(K key) {
        return CollectionFactory.getCopyOnRequestSetFromMutableCollection(map
                .get(key));
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    public boolean hasValues(K key) {
        return map.containsKey(key);
    }

    /**
     * @param key
     *        key to add
     * @param value
     *        value to add
     * @return true if addition happens
     */
    public boolean put(K key, V value) {
        return map.put(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @param value
     *        value to remove
     * @return true if removal happens
     */
    public boolean remove(K key, V value) {
        return map.remove(key, value);
    }

    /**
     * @param key
     *        key to look up
     * @return true if there are values for key
     */
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * @param key
     *        key to look up
     * @param value
     *        value to look up
     * @return true if key and value are contained
     */
    public boolean contains(K key, V value) {
        return map.contains(key, value);
    }

    /** @return all values contained */
    public Set<V> getAllValues() {
        return map.getAllValues();
    }

    /** @return number of mapping contained */
    public int size() {
        return map.size();
    }
}
