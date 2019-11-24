package org.semanticweb.owlapi6.apitest.syntax.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.ReaderDocumentSource;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;

@RunWith(Parameterized.class)
public class BOMSafeInputStreamAndParseTestCase extends TestBase {

    private static final String ISA14_O = "http://www.example.org/ISA14#o";

    @Parameters
    public static Collection<String> data() {
        OWLDataFactory f = OWLManager.getOWLDataFactory();
        return Arrays.asList("<Ontology xml:base=\""
            + f.getNextDocumentIRI(ISA14_O)
            + "\" ontologyIRI=\"http://www.example.org/ISA14#\"> <Declaration><Class IRI=\"Researcher\"/></Declaration></Ontology>",
            "Ontology: <" + f.getNextDocumentIRI(ISA14_O)
                + ">\nClass: <http://www.example.org/ISA14#Researcher>",
            "Ontology(<" + f.getNextDocumentIRI(ISA14_O)
                + ">\nDeclaration(Class(<http://www.example.org/ISA14#Researcher>)))",
            "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n<"
                + f.getNextDocumentIRI(ISA14_O)
                + "> rdf:type owl:Ontology .\n<http://www.example.org/ISA14#Researcher> rdf:type owl:Class .",
            "<rdf:RDF xml:base=\"" + f.getNextDocumentIRI(ISA14_O)
                + "\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\" xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ><owl:Ontology rdf:about=\"#\" /><owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/></rdf:RDF>");
    }

    private final String input;

    public BOMSafeInputStreamAndParseTestCase(String in) {
        input = in;
    }

    private static InputStream in(int[] b, String s) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i : b) {
            out.write(i);
        }
        out.write(s.getBytes());
        byte[] byteArray = out.toByteArray();
        return new ByteArrayInputStream(byteArray);
    }

    // Bytes Encoding Form
    // 00 00 FE FF | UTF-32, big-endian
    // FF FE 00 00 | UTF-32, little-endian
    // FE FF |UTF-16, big-endian
    // FF FE |UTF-16, little-endian
    // EF BB BF |UTF-8
    @Test
    public void testBOMError32big() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0x00, 0x00, 0xFE, 0xFF};
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @Test
    public void testBOMError32small() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE, 0x00, 0x00};
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @Test
    public void testBOMError16big() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE};
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @Test
    public void testBOMError16small() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE};
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @Test
    public void testBOMError8() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xEF, 0xBB, 0xBF};
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @Test
    public void testBOMError32bigReader() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0x00, 0x00, 0xFE, 0xFF};
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError32Reader() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE, 0x00, 0x00};
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16Reader() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE};
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16smallReader() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xFF, 0xFE};
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError8Reader() throws OWLOntologyCreationException, IOException {
        int[] b = new int[] {0xEF, 0xBB, 0xBF};
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }
}
