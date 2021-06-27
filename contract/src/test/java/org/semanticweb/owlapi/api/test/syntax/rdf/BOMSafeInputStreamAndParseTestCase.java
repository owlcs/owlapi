package org.semanticweb.owlapi.api.test.syntax.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class BOMSafeInputStreamAndParseTestCase extends TestBase {

    static final String RESEARCHER = "<http://www.example.org/ISA14#Researcher>";
    static final String ISA14_O = "http://www.example.org/ISA14#o";

    static Collection<Arguments> data() {
        List<Arguments> toReturn = new ArrayList<>();
        List<String> list = Arrays.asList("<Ontology xml:base=\"" + IRI.getNextDocumentIRI(ISA14_O)
            + "\" ontologyIRI=\"http://www.example.org/ISA14#\"> <Declaration><Class IRI=\"Researcher\"/></Declaration></Ontology>",
            "Ontology: <" + IRI.getNextDocumentIRI(ISA14_O) + ">\nClass: " + RESEARCHER,
            "Ontology(<" + IRI.getNextDocumentIRI(ISA14_O) + ">\nDeclaration(Class(" + RESEARCHER
                + ")))",
            "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n<"
                + IRI.getNextDocumentIRI(ISA14_O) + "> rdf:type owl:Ontology .\n" + RESEARCHER
                + " rdf:type owl:Class .",
            "<rdf:RDF xml:base=\"" + IRI.getNextDocumentIRI(ISA14_O)
                + "\" xmlns:owl =\"http://www.w3.org/2002/07/owl#\" xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" ><owl:Ontology rdf:about=\"#\" /><owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/></rdf:RDF>");
        List<int[]> prefixes =
            Arrays.asList(new int[] {0x00, 0x00, 0xFE, 0xFF}, new int[] {0xFF, 0xFE, 0x00, 0x00},
                new int[] {0xFF, 0xFE}, new int[] {0xFE, 0xFF}, new int[] {0xEF, 0xBB, 0xBF});
        for (int[] p : prefixes) {
            for (String s : list) {
                toReturn.add(Arguments.of(p, s));
            }
        }
        return toReturn;
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
    @ParameterizedTest
    @MethodSource("data")
    void testBOMError32big(int[] b, String input) throws OWLOntologyCreationException, IOException {
        m.loadOntologyFromOntologyDocument(in(b, input));
    }

    @ParameterizedTest
    @MethodSource("data")
    void testBOMError32bigReader(int[] b, String input)
        throws OWLOntologyCreationException, IOException {
        m.loadOntologyFromOntologyDocument(
            new ReaderDocumentSource(new InputStreamReader(in(b, input))));
    }
}
