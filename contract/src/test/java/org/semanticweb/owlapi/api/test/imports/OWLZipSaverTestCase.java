package org.semanticweb.owlapi.api.test.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.OWLZipSaver;

class OWLZipSaverTestCase extends TestBase {
    static final IRI iri1 = iri("urn:test:", "o1.owl");
    static final IRI iri2 = iri("urn:test:", "o2.owl");

    @Test
    void shouldSaveClosureWithYaml() throws ZipException, IOException, OWLOntologyCreationException,
        OWLOntologyStorageException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OWLOntology o1 = m.createOntology(iri1);
        OWLOntology o2 = m.createOntology(iri2);
        o1.applyChange(new AddImport(o1, ImportsDeclaration(iri2)));
        new OWLZipSaver().saveOntologies(l(o1), l(o2), out);
        ZipInputStream in = new ZipInputStream(new ByteArrayInputStream(out.toByteArray()));
        ZipEntry nextEntry = in.getNextEntry();
        assertEquals("owlzip.yaml", nextEntry.getName());
        String read = read(in);
        assertEquals(
            "ontologies:\n" + "- iri: urn:test:o1.owl\n" + "  path: urn:test:o1.owl\n"
                + "  root: true\n" + "- iri: urn:test:o2.owl\n" + "  path: urn:test:o2.owl\n",
            read);
        nextEntry = in.getNextEntry();
        String read1 = read(in);
        nextEntry = in.getNextEntry();
        String read2 = read(in);
        OWLOntology o2read = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(read2));
        OWLOntology o1read = m1.loadOntologyFromOntologyDocument(new StringDocumentSource(read1));
        equal(o1, o1read);
        equal(o2, o2read);
    }

    protected String read(ZipInputStream in) throws IOException {
        StringBuilder b = new StringBuilder();
        while (in.available() > 0) {
            int read = in.read();
            if (read > 0) {
                b.append((char) read);
            }
        }
        return b.toString();
    }
}

