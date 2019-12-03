package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.SimpleRenderer;

@SuppressWarnings("javadoc")
public class SimpleRendererTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";
    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    public void shouldSetPrefixes() {
        testSubject.setPrefix("test", URN_TEST);
        assertEquals("test:t", testSubject.getShortForm(IRI.create(URN_TEST, "t")));
    }

    @Test
    public void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().setOntologyFormat(o, f);
        f.setPrefix("test", URN_TEST);
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals("test:t", testSubject.getShortForm(IRI.create(URN_TEST, "t")));
    }
}
