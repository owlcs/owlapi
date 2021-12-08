package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.SimpleRenderer;

class SimpleRendererTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";
    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    void shouldSetPrefixes() {
        testSubject.setPrefix("test", URN_TEST);
        assertEquals("test:t", testSubject.getShortForm(iri(URN_TEST, "t")));
    }

    @Test
    void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
        OWLOntology o = createAnon();
        o.getOWLOntologyManager().setOntologyFormat(o, format);
        format.setPrefix("test", URN_TEST);
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals("test:t", testSubject.getShortForm(iri(URN_TEST, "t")));
    }
}
