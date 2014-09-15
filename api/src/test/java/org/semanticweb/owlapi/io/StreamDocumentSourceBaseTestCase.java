package org.semanticweb.owlapi.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;

@SuppressWarnings({ "javadoc", "resource" })
public class StreamDocumentSourceBaseTestCase {

    @Test
    public void shouldCreateRewindableReaderWithKnownContent()
            throws IOException {
        String input = "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
                + "<owl:Ontology/>\n"
                + "    <owl:Class rdf:about=\"http://example.com/Person\">\n"
                + "        <owl:hasKey rdf:parseType=\"Collection\">\n"
                + "            <owl:ObjectProperty rdf:about=\"http://example.com/objectPoperty\"/>\n"
                + "            <owl:DatatypeProperty rdf:about=\"http://example.com/dataProperty\"/>\n"
                + "        </owl:hasKey>\n"
                + "    </owl:Class>\n"
                + "    <owl:ObjectProperty rdf:about=\"http://example.com/objectProperty\"/>\n"
                + "</rdf:RDF>";
        StreamDocumentSourceBase source = new StreamDocumentSourceBase(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes()),
                        StandardCharsets.UTF_8), IRI.create("urn:test"), null,
                null) {};
        Reader r = source.getReader().get();
        StringWriter w = new StringWriter();
        int i = r.read();
        while (i > -1) {
            w.write(i);
            i = r.read();
        }
        w.flush();
        String result = w.toString();
        assertEquals(input, result);
    }
}
