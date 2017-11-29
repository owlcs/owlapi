package org.semanticweb.owlapi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.Nullable;

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

    public List<String> roots() {
        return (List<String>) roots.get("roots");
    }

    public Stream<Entry> entries() {
        List<Entry> list = new ArrayList<>();
        roots.forEach((a, b) -> addEntry(a, b, list));
        return list.stream();
    }

    private static void addEntry(String a, Object b, List<Entry> l) {
        if (!"roots".equals(a)) {
            Map<String, String> m = (Map<String, String>) b;
            Entry e = new Entry();
            e.physical = m.get("physical");
            e.logical = m.get("logical");
            e.version = m.get("version");
            l.add(e);
        }
    }
}
