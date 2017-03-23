package org.semanticweb.owlapi.io;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
    private Charset encoding = StandardCharsets.UTF_8;

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

    /**
     * @return the encoding
     */
    public Charset getEncoding() {
        return encoding;
    }

    /**
     * @param enc the encoding to set
     * @return this object
     */
    public OWLStorerParameters withEncoding(Charset enc) {
        encoding = enc;
        return this;
    }
}
