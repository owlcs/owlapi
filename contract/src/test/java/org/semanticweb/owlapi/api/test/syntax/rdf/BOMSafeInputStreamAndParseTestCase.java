package org.semanticweb.owlapi.api.test.syntax.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class BOMSafeInputStreamAndParseTestCase extends TestBase {

    @Parameters
    public static Collection<String> data() {
        return Arrays.asList("<Ontology xml:base=\"" + IRI.getNextDocumentIRI(
        "http://www.example.org/ISA14#o")
        + "\" ontologyIRI=\"http://www.example.org/ISA14#\"> <Declaration><Class IRI=\"Researcher\"/></Declaration></Ontology>",
        "Ontology: <" + IRI.getNextDocumentIRI("http://www.example.org/ISA14#o")
        + ">\nClass: <http://www.example.org/ISA14#Researcher>", "Ontology(<"
        + IRI.getNextDocumentIRI("http://www.example.org/ISA14#o")
        + ">\nDeclaration(Class(<http://www.example.org/ISA14#Researcher>)))",
        "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n<"
        + IRI.getNextDocumentIRI("http://www.example.org/ISA14#o")
        + "> rdf:type owl:Ontology .\n<http://www.example.org/ISA14#Researcher> rdf:type owl:Class .",
        "<rdf:RDF xml:base=\"" + IRI.getNextDocumentIRI(
        "http://www.example.org/ISA14#o")
        + "\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\" xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ><owl:Ontology rdf:about=\"#\" /><owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/></rdf:RDF>");
    }

    private String input;

    public BOMSafeInputStreamAndParseTestCase(String in) {
        input = in;
    }

    @Nonnull
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
    public void testBOMError32big() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0x00, 0x00, 0xFE, 0xFF };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        in(b, input));
    }

    @Test
    public void testBOMError32small() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE, 0x00, 0x00 };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        in(b, input));
    }

    @Test
    public void testBOMError16big() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        in(b, input));
    }

    @Test
    public void testBOMError16small() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        in(b, input));
    }

    @Test
    public void testBOMError8() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xEF, 0xBB, 0xBF };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        in(b, input));
    }

    @Test
    public void testBOMError32bigReader() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0x00, 0x00, 0xFE, 0xFF };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError32Reader() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE, 0x00, 0x00 };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16Reader() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError16smallReader() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xFF, 0xFE };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }

    @Test
    public void testBOMError8Reader() throws OWLOntologyCreationException,
    IOException {
        int[] b = new int[] { 0xEF, 0xBB, 0xBF };
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
        new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }
}
