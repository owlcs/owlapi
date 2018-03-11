package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * OWL/ZIP YAML entry list, foruse by the YAML parser.
 * 
 * @author ignazio
 */
public class OWLZipYaml {

    List<OWLZipEntry> list = new ArrayList<>();

    /**
     * @param l list of YAML parsed objects
     */
    public OWLZipYaml(List<Map<String, Object>> l) {
        list = asList(l.stream().map(this::map));
    }

    private OWLZipEntry map(Map<String, Object> o) {
        return new OWLZipEntry(o);
    }

    /**
     * @return list of ontologies marked as roots
     */
    public Stream<OWLZipEntry> roots() {
        return list.stream().filter(OWLZipEntry::isRoot);
    }

    /**
     * @return list of all ontologies
     */
    public Stream<OWLZipEntry> entries() {
        return list.stream();
    }
}
