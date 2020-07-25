package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Class for saving ontologies to zip files with an optional index.
 * 
 * @author ignazio
 */
public class OWLZipSaver {
    /** YAML entry name */
    public static final String YAML_INDEX_NAME = "owlzip.yaml";
    /** Properties entry name */
    public static final String PROPERTIES_INDEX_NAME = "owlzip.properties";
    /** Catalog entry name */
    public static final String CATALOG_INDEX_NAME = "catalog.xml";
    private Function<OWLOntologyID, String> entryPath = this::entryPath;
    private BiFunction<Collection<OWLOntology>, Collection<OWLOntology>, String> index =
        this::yamlIndex;
    private Supplier<String> indexName = () -> YAML_INDEX_NAME;

    /**
     * @param entryPath function to use to generate the path in the zip file for an ontology. The
     *        default strategy is to use the last part of the ontology IRI.
     */
    public void setEntryPath(Function<OWLOntologyID, String> entryPath) {
        this.entryPath = entryPath;
    }

    /**
     * @param index function to use to generate the index content. The default strategy is to create
     *        a YAML file. The methods on this class can be passed here to switch to different
     *        strategies, or a user defined strategy can be used.
     */
    public void setIndex(
        BiFunction<Collection<OWLOntology>, Collection<OWLOntology>, String> index) {
        this.index = index;
    }

    /**
     * @param indexName the name for the index entry. This defaults to owlzip.yaml; other known
     *        values are owlzip.poperties and catalog-v001.xml. A supplier that returns a null or
     *        empty string will cause the class to skip creating an index; in that case, the
     *        ontologies will just be compressed.
     */
    public void setIndexName(Supplier<String> indexName) {
        this.indexName = indexName;
    }

    /**
     * Save the root ontology as root in the index and its imports closure as companion ontologies.
     * 
     * @param root root ontology to save
     * @param out output stream; to be created by the caller, closed in this method
     * @throws IOException if writing to the file fails
     * @throws OWLOntologyStorageException if serializing the ontologies fails
     */
    public void saveImportsClosure(OWLOntology root, OutputStream out)
        throws IOException, OWLOntologyStorageException {
        saveOntologies(Collections.singletonList(root),
            asList(root.importsClosure().filter(x -> x != root)), out);
    }

    /**
     * Save the root ontologies as root in the index and their imports closures as companion
     * ontologies.
     * 
     * @param roots root ontologies to save
     * @param out output stream; to be created by the caller, closed in this method
     * @throws IOException if writing to the file fails
     * @throws OWLOntologyStorageException if serializing the ontologies fails
     */
    public void saveImportsClosures(Collection<OWLOntology> roots, OutputStream out)
        throws IOException, OWLOntologyStorageException {
        saveOntologies(roots, asList(
            roots.stream().flatMap(OWLOntology::importsClosure).filter(x -> !roots.contains(x))),
            out);
    }

    /**
     * Save the ontologies in the index and their content as companion ontologies; no ontology will
     * be marked as root.
     * 
     * @param ontologies ontologies to save
     * @param out output stream; to be created by the caller, closed in this method
     * @throws IOException if writing to the file fails
     * @throws OWLOntologyStorageException if serializing the ontologies fails
     */
    public void saveOntologies(Collection<OWLOntology> ontologies, OutputStream out)
        throws IOException, OWLOntologyStorageException {
        saveOntologies(Collections.emptyList(), ontologies, out);
    }

    /**
     * Save the ontologies in the index, marking as root the ontologies in the roots collection; the
     * other ontologies will be saved as companion ontologies. This method assumes the caller takes
     * care of flattening ontology imports closures, and will not navigate imports statements.
     * 
     * @param roots root ontologies
     * @param ontologies ontologies to save
     * @param out output stream; to be created by the caller, closed in this method
     * @throws IOException if writing to the file fails
     * @throws OWLOntologyStorageException if serializing the ontologies fails
     */
    public void saveOntologies(Collection<OWLOntology> roots, Collection<OWLOntology> ontologies,
        OutputStream out) throws IOException, OWLOntologyStorageException {
        try (ZipOutputStream f = new ZipOutputStream(out)) {
            addIndex(roots, ontologies, f);
            for (OWLOntology o : roots) {
                ZipEntry next = new ZipEntry(entryPath.apply(o.getOntologyID()));
                f.putNextEntry(next);
                ByteArrayOutputStream temp = new ByteArrayOutputStream();
                o.saveOntology(temp);
                f.write(temp.toByteArray());
            }
            for (OWLOntology o : ontologies) {
                ZipEntry next = new ZipEntry(entryPath.apply(o.getOntologyID()));
                f.putNextEntry(next);
                ByteArrayOutputStream temp = new ByteArrayOutputStream();
                o.saveOntology(temp);
                f.write(temp.toByteArray());
            }
            f.close();
        }
    }

    protected void addIndex(Collection<OWLOntology> roots, Collection<OWLOntology> ontologies,
        ZipOutputStream f) throws IOException {
        String name = indexName.get();
        if (name != null && !name.isEmpty()) {
            ZipEntry e = new ZipEntry(name);
            f.putNextEntry(e);
            String entrydata = index.apply(roots, ontologies);
            f.write(entrydata.getBytes());
            f.closeEntry();
        }
    }

    /**
     * Prepare a catalog index; this method can be used as a {@link java.util.function.BiFunction}
     * for {@link #setIndex(BiFunction)}. Imports statements are not navigated; all ontologies
     * required have to appear explicitly in the collections.
     * 
     * @param roots collection of roots, if any
     * @param ontologies collection of ontologies, if any
     * @return content of a catalog index for the input ontologies
     */
    public String catalogIndex(Collection<OWLOntology> roots, Collection<OWLOntology> ontologies) {
        StringBuilder b = new StringBuilder(1000).append(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<catalog prefer=\"public\" xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\">\n    <group id=\"Folder Repository, directory=, recursive=true, Auto-Update=true, version=2\" prefer=\"public\" xml:base=\"\">\n");
        int id = 1;
        if (roots.size() + ontologies.size() > 0) {
            for (OWLOntology o : roots) {
                b.append("        <uri id=\"test").append(id).append("\" name=\"")
                    .append(o.getOntologyID().getOntologyIRI().get()).append("\" uri=\"")
                    .append(entryPath.apply(o.getOntologyID())).append("\"/>\n");
                id++;
            }
            for (OWLOntology o : ontologies) {
                b.append("        <uri id=\"test").append(id).append("\" name=\"")
                    .append(o.getOntologyID().getOntologyIRI().get()).append("\" uri=\"")
                    .append(entryPath.apply(o.getOntologyID())).append("\"/>\n");
                id++;
            }
        }
        b.append("    </group>\n</catalog>\n");
        return b.toString();
    }

    /**
     * Prepare a yaml index; this method can be used as a {@link java.util.function.BiFunction} for
     * {@link #setIndex(BiFunction)}. Imports statements are not navigated; all ontologies required
     * have to appear explicitly in the collections.
     * 
     * @param roots collection of roots, if any
     * @param ontologies collection of ontologies, if any
     * @return content of a yaml index for the input ontologies
     */
    public String yamlIndex(Collection<OWLOntology> roots, Collection<OWLOntology> ontologies) {
        StringBuilder b = new StringBuilder();
        if (roots.size() + ontologies.size() > 0) {
            b.append("ontologies:\n");
            roots.stream().map(OWLOntology::getOntologyID)
                .forEach(o -> b.append("- iri: ").append(o.getOntologyIRI().get())
                    .append("\n  path: ").append(entryPath.apply(o)).append("\n  root: true\n"));
            ontologies.stream().map(OWLOntology::getOntologyID)
                .forEach(o -> b.append("- iri: ").append(o.getOntologyIRI().get())
                    .append("\n  path: ").append(entryPath.apply(o)).append("\n"));
        }
        return b.toString();
    }

    /**
     * Prepare a properties index; this method can be used as a
     * {@link java.util.function.BiFunction} for {@link #setIndex(BiFunction)}. Imports statements
     * are not navigated; all ontologies required have to appear explicitly in the collections.
     * 
     * @param roots collection of roots, if any
     * @param ontologies collection of ontologies, if any
     * @return content of a properties index for the input ontologies
     */
    public String propertiesIndex(Collection<OWLOntology> roots,
        Collection<OWLOntology> ontologies) {
        StringBuilder b = new StringBuilder();
        if (!roots.isEmpty()) {
            String rootsEntry = roots.stream().map(OWLOntology::getOntologyID).map(entryPath)
                .collect(Collectors.joining(", "));
            b.append("roots=").append(rootsEntry).append("\n");
        }
        ontologies.stream().map(OWLOntology::getOntologyID).forEach(o -> b
            .append(entryPath.apply(o)).append("=").append(o.getOntologyIRI().get()).append("\n"));
        return b.toString();
    }

    /**
     * Prepare a zip name for an ontology id; this method can be used as a Function for
     * {@code setEntryPath}.
     * 
     * @param id ontology id
     * @return zip entry name for the ontology
     */
    public String entryPath(OWLOntologyID id) {
        String string = id.getOntologyIRI().get().toString();
        if (string.endsWith("/") || string.endsWith("#")) {
            string = XMLUtils.getNCNameSuffix(string.subSequence(0, string.length() - 1));
        }
        return verifyNotNull(string);
    }
}
