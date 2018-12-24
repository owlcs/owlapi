package org.semanticweb.owlapi6.utility;

import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.yaml.snakeyaml.Yaml;

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

    /**
     * Constructor to read entries from a zip file containing a YAML index. The ontologies
     * themselves are not parsed; the path property in the YAML index can be used to create jar:
     * urls for loading the ontologies.
     * 
     * @param f file to parse
     * @throws ZipException if the zip file cannot be opened
     * @throws IOException if file read operations cannot be performed
     */
    public OWLZipYaml(File f) throws IOException {
        try (ZipFile z = new ZipFile(f)) {
            ZipEntry yaml = z.getEntry("owlzip.yaml");
            if (yaml == null) {
                throw new IllegalArgumentException(
                    "File does not have an owlzip.yaml entry: " + f.getName());
            }
            Yaml yamlParser = new Yaml();
            Map<String, List<Map<String, Object>>> load2 = yamlParser.load(z.getInputStream(yaml));
            list = asList(load2.get("ontologies").stream().map(this::map));
        }

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
