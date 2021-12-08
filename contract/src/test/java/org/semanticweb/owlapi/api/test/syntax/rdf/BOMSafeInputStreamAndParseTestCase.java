package org.semanticweb.owlapi.api.test.syntax.rdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    private static final String RESEARCHER = "Researcher";
    private static final String ISA14 = "http://www.example.org/ISA14#";
    private static final String PREFIX_XML =
        "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"";
    private static final String PREFIX_TURTLE =
        "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n";
    static final String RESEARCHER_IRI = "<" + ISA14 + RESEARCHER + ">";
    static final String ISA14_O = ISA14 + "o";

    static Collection<Arguments> data() {
        List<Arguments> toReturn = new ArrayList<>();
        List<String> list = l(
        //@formatter:off
            "<Ontology xml:base=\"" + nextISA() + "\" ontologyIRI=\"" + ISA14 + "\"> <Declaration><Class IRI=\"" + RESEARCHER + "\"/></Declaration></Ontology>",
            "Ontology: <" + nextISA() + ">\nClass: " + RESEARCHER_IRI,
            "Ontology(<" + nextISA() + ">\nDeclaration(Class(" + RESEARCHER_IRI + ")))",
            PREFIX_TURTLE + "<" + nextISA() + "> rdf:type owl:Ontology .\n" + RESEARCHER_IRI + " rdf:type owl:Class .",
            "<rdf:RDF xml:base=\"" + nextISA() + "\" " + PREFIX_XML + " ><owl:Ontology rdf:about=\"#\" /><owl:Class rdf:about=\"" + ISA14 + RESEARCHER + "\"/></rdf:RDF>"
            //@formatter:on    
        );
        List<int[]> prefixes =
            l(new int[] {0x00, 0x00, 0xFE, 0xFF}, new int[] {0xFF, 0xFE, 0x00, 0x00},
                new int[] {0xFF, 0xFE}, new int[] {0xFE, 0xFF}, new int[] {0xEF, 0xBB, 0xBF});
        for (int[] p : prefixes) {
            for (String onto : list) {
                toReturn.add(Arguments.of(p, onto));
            }
        }
        return toReturn;
    }

    protected static IRI nextISA() {
        return IRI.getNextDocumentIRI(ISA14_O);
    }

    private static InputStream in(int[] b, String content) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int index : b) {
            out.write(index);
        }
        out.write(content.getBytes());
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
    void testBOMError32big(int[] b, String input) throws IOException {
        try (InputStream in = in(b, input)) {
            loadFrom(in);
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    void testBOMError32bigReader(int[] b, String input)
        throws OWLOntologyCreationException, IOException {
        try (InputStream in = in(b, input); InputStreamReader r = new InputStreamReader(in)) {
            m.loadOntologyFromOntologyDocument(new ReaderDocumentSource(r));
        }
    }
}
