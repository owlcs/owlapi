package org.semanticweb.owlapi.util;

import java.util.Map;
import java.util.Optional;

import org.semanticweb.owlapi.model.OWLOntologyID;

public class OWLZipEntry {
    public static final String PATH = "path";
    public static final String IRI = "iri";
    public static final String VERSION = "version";
    public static final String ROOT = "root";
    private final Map<String, Object> values;

    public OWLZipEntry(Map<String, Object> map) {
        values = map;
    }

    public <T> T set(String key, T value) {
        return (T) values.put(key, value);
    }

    public <T> T get(String key) {
        return (T) values.get(key);
    }

    public boolean isRoot() {
        return values.getOrDefault(ROOT, Boolean.FALSE).equals(Boolean.TRUE);
    }

    public String path() {
        return get(PATH);
    }

    public OWLOntologyID id() {
        return new OWLOntologyID(iri(get(IRI)), iri(get(VERSION)));
    }

    private Optional<org.semanticweb.owlapi.model.IRI> iri(String s) {
        if (s == null) {
            return Optional.empty();
        }
        return Optional.of(org.semanticweb.owlapi.model.IRI.create(s));
    }
}
