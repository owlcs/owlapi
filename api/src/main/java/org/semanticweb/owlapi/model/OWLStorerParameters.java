package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * A wrapper for storer specific parameters.
 * 
 * @author ignazio
 * @since 6.0.0
 */
public class OWLStorerParameters implements Serializable {
    private final Map<Serializable, Serializable> parameterMap = new HashMap<>();

    /**
     * @param key key for the new entry
     * @param value value for the new entry
     */
    public void setParameter(Serializable key, Serializable value) {
        parameterMap.put(key, value);
    }

    /**
     * @param key key for the new entry
     * @param defaultValue value for the new entry
     * @param <T> type
     * @return the value
     */
    public <T> T getParameter(Serializable key, T defaultValue) {
        Serializable val = parameterMap.get(key);
        if (val == null) {
            return defaultValue;
        }
        return (T) val;
    }

    /**
     * @return all keys in the parameter map
     */
    public Set<Serializable> keys() {
        return parameterMap.keySet();
    }

    /**
     * @param c consumer to apply to all entries
     */
    public void stream(BiConsumer<Serializable, Serializable> c) {
        parameterMap.forEach(c);
    }
}
