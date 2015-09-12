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

    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    public void shouldSetPrefixes() {
        testSubject.setPrefix("test", "urn:test#");
        assertEquals("test:t", testSubject.getShortForm(IRI.create(
        "urn:test#t")));
    }

    @Test
    public void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().setOntologyFormat(o, f);
        f.setPrefix("test", "urn:test#");
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals("test:t", testSubject.getShortForm(IRI.create(
        "urn:test#t")));
    }
}
