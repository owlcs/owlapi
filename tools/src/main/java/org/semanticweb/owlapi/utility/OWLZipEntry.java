package org.semanticweb.owlapi.utility;

import java.util.Map;
import java.util.Optional;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * OWL/ZIP YAML entry, foruse by the YAML parser.
 * 
 * @author ignazio
 */
public class OWLZipEntry {
    /**
     * Constant for path attribute in YAML file.
     */
    public static final String PATH = "path";
    /**
     * Constant for iri attribute in YAML file.
     */
    public static final String IRI = "iri";
    /**
     * Constant for version attribute in YAML file.
     */
    public static final String VERSION = "version";
    /**
     * Constant for root attribute in YAML file.
     */
    public static final String ROOT = "root";
    private final Map<String, Object> values;

    /**
     * @param map values for entry
     */
    public OWLZipEntry(Map<String, Object> map) {
        values = map;
    }

    /**
     * @param key key to set
     * @param value value to set
     * @return previous value
     */
    public <T> T set(String key, T value) {
        return (T) values.put(key, value);
    }

    /**
     * @param key key to retrieve
     * @return value associated
     */
    public <T> T get(String key) {
        return (T) values.get(key);
    }

    /**
     * @return true if this entry is root
     */
    public boolean isRoot() {
        return values.getOrDefault(ROOT, Boolean.FALSE).equals(Boolean.TRUE);
    }

    /**
     * @return path in the zip file for this entry
     */
    public String path() {
        return get(PATH);
    }

    /**
     * @param df data factory
     * @return ontology id for this entry
     */
    public OWLOntologyID id(OWLDataFactory df) {
        String s = get(IRI);
        String v = get(VERSION);
        org.semanticweb.owlapi.model.IRI o = s == null ? null : df.getIRI(s);
        org.semanticweb.owlapi.model.IRI version = v == null ? null : df.getIRI(v);
        return df.getOWLOntologyID(Optional.ofNullable(o), Optional.ofNullable(version));
    }
}
