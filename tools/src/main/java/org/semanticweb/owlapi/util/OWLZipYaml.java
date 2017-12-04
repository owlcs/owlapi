package org.semanticweb.owlapi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyID;

class OWLZipYaml {
    static class Entry {
        @Nullable
        String physical;
        @Nullable
        String logical;
        @Nullable
        String version;
    }

    Map<String, Object> roots;

    public OWLZipYaml(Map<String, Object> roots) {
        this.roots = roots;
    }

    public Map<OWLOntologyID, String> roots() {
        Map<OWLOntologyID, String> map = new HashMap<>();
        ((List<Map<String, String>>) roots.get("roots")).forEach(m -> map(map, m));
        return map;
    }

    public Stream<Map.Entry<OWLOntologyID, String>> entries() {
        Map<OWLOntologyID, String> list = new HashMap<>();
        roots.forEach((a, b) -> addEntry(a, b, list));
        return list.entrySet().stream();
    }

    private static void addEntry(String a, Object b, Map<OWLOntologyID, String> l) {
        if (!"roots".equals(a)) {
            map(l, (Map<String, String>) b);
        }
    }

    protected static void map(Map<OWLOntologyID, String> map, Map<String, String> m) {
        Optional<IRI> iri = Optional.of(IRI.create(m.get("iri")));
        String version = m.get("versionIri");
        Optional<IRI> versionIRI =
            Optional.ofNullable(version == null ? null : IRI.create(version));
        map.put(new OWLOntologyID(iri, versionIRI), m.get("path"));
    }
}
