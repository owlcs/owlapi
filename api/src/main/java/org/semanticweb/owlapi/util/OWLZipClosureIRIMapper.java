package org.semanticweb.owlapi.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * An IRI mapper that uses a zip file and its owlzip.properties content to map logical IRIs to
 * {@code jar:} IRIs. This enables access to zipped imports closures.
 */
public class OWLZipClosureIRIMapper implements OWLOntologyIRIMapper {
    private static final Pattern CATALOG_PATTERN = Pattern.compile("catalog[\\-v0-9]*\\.xml");
    private List<IRI> physicalRoots = new ArrayList<>();
    private Map<IRI, IRI> logicalToPhysicalIRI = new ConcurrentHashMap<>();

    /**
     * @param f zip file
     * @throws IOException thrown if access to the file is impossible
     */
    public OWLZipClosureIRIMapper(File f) throws IOException {
        String basePhysicalIRI = "jar:" + f.toURI() + "!/";
        try (ZipFile z = new ZipFile(f)) {
            // owlzip.properties index
            if (loadFromOwlzipProperties(basePhysicalIRI, z)) {
                return;
            }
            // catalog.xml index
            if (loadFromCatalog(basePhysicalIRI, z)) {
                return;
            }
            // no index: look up root.owl for root ontologies, others imported as usual
            ZipEntry root = z.getEntry("root.owl");
            if (root != null) {
                physicalRoots.add(IRI.create(basePhysicalIRI + "root.owl"));
            }
            ZipIRIMapper mapper = new ZipIRIMapper(z, basePhysicalIRI);
            mapper.iriMappings().forEach(e -> logicalToPhysicalIRI.put(e.getKey(), e.getValue()));
            // TODO OBO compressed files are not mapped according to the ontology IRI
            // but according to the file name in AutoIRIMapper and ZipIRIMapper. This needs
            // sorting.
        }
    }

    protected boolean loadFromCatalog(String basePhysicalIRI, ZipFile z) throws IOException {
        ZipEntry yaml = z.stream().filter(e -> CATALOG_PATTERN.matcher(e.getName()).matches())
            .findFirst().orElse(null);
        if (yaml == null) {
            return false;
        }
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(z.getInputStream(yaml));
            NodeList uris = doc.getElementsByTagName("uri");
            for (int i = 0; i < uris.getLength(); i++) {
                // Catalogs do not have a way to indicate root ontologies; all ontologies will be
                // considered root.
                // Duplicate entries are unsupported; entries whose name starts with duplicate: will
                // cause mismatches
                Element e = (Element) uris.item(i);
                IRI physicalIRI = IRI.create(basePhysicalIRI + e.getAttribute("uri"));
                physicalRoots.add(physicalIRI);
                String name = e.getAttribute("name");
                if (name.startsWith("duplicate:")) {
                    name = name.replace("duplicate:", "");
                }
                logicalToPhysicalIRI.put(IRI.create(name), physicalIRI);
            }
            return true;
        } catch (SAXException | ParserConfigurationException e1) {
            throw new IOException(e1);
        }
    }

    protected boolean loadFromOwlzipProperties(String basePhysicalIRI, ZipFile z)
        throws IOException {
        ZipEntry yaml = z.getEntry("owlzip.properties");
        if (yaml == null) {
            return false;
        }
        Properties p = new Properties();
        p.load(z.getInputStream(yaml));
        String[] roots = p.getProperty("roots", "").split(", ");
        for (String s : roots) {
            String name = s.trim();
            if (!name.isEmpty()) {
                physicalRoots.add(IRI.create(basePhysicalIRI + name.trim()));
            }
            p.entrySet().stream().filter(e -> !e.getKey().equals("roots"))
                .forEach(e -> logicalToPhysicalIRI.put(IRI.create(e.getValue().toString()),
                    IRI.create(basePhysicalIRI + e.getKey())));
        }
        return true;
    }

    @Nullable
    @Override
    public IRI getDocumentIRI(IRI ontologyIRI) {
        return logicalToPhysicalIRI.get(ontologyIRI);
    }

    /**
     * @return stream of known roots; can be empty
     */
    public Stream<IRI> roots() {
        return physicalRoots.stream();
    }

    /**
     * @return all known mappings
     */
    public Stream<Map.Entry<IRI, IRI>> mappedEntries() {
        return logicalToPhysicalIRI.entrySet().stream();
    }
}
