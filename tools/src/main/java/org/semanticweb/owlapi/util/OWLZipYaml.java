package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OWLZipYaml {

    List<OWLZipEntry> list = new ArrayList<>();

    public OWLZipYaml(List<Map<String, Object>> l) {
        list = asList(l.stream().map(this::map));
    }

    private OWLZipEntry map(Map<String, Object> o) {
        return new OWLZipEntry(o);
    }

    public Stream<OWLZipEntry> roots() {
        return list.stream().filter(OWLZipEntry::isRoot);
    }

    public Stream<OWLZipEntry> entries() {
        return list.stream();
    }
}
