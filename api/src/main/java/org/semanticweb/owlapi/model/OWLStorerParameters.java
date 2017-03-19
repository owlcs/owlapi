package org.semanticweb.owlapi.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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


}
