package org.semanticweb.owlapi6.iotest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.semanticweb.owlapi6.documents.StreamDocumentSourceBase;
import org.semanticweb.owlapi6.io.OWLParser;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

public class StreamDocumentSourceBaseTestCase {

    @Test
    public void shouldCreateRewindableReaderWithKnownContent() {
        String input =
            "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
                + "<owl:Ontology/>\n"
                + "    <owl:Class rdf:about=\"http://example.com/Person\">\n        <owl:hasKey rdf:parseType=\"Collection\">\n            <owl:ObjectProperty rdf:about=\"http://example.com/objectPoperty\"/>\n            <owl:DatatypeProperty rdf:about=\"http://example.com/dataProperty\"/>\n        </owl:hasKey>\n    </owl:Class>\n"
                + "    <owl:ObjectProperty rdf:about=\"http://example.com/objectProperty\"/>\n"
                + "</rdf:RDF>";
        StreamDocumentSourceBase source = new StreamDocumentSourceBase(
            new InputStreamReader(new ByteArrayInputStream(input.getBytes()),
                StandardCharsets.UTF_8),
            "urn:test:test", null, null) {};
        StringWriter w = new StringWriter();
        OWLParser mockParser = new ParserForTest(w);
        source.acceptParser(mockParser, mock(OWLOntology.class), mock(OntologyConfigurator.class));
        assertEquals(input, w.toString());
    }
}
