package org.semanticweb.owlapi6.api.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

@SuppressWarnings("javadoc")
public class SimpleRendererTestCase extends TestBase {

    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    public void shouldSetPrefixes() {
        testSubject.setPrefix("test", "urn:test#");
        assertEquals("test:t", testSubject.getShortForm(df.getIRI("urn:test#", "t")));
    }

    @Test
    public void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().setOntologyFormat(o, f);
        o.getPrefixManager().withPrefix("test", "urn:test#");
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals("test:t", testSubject.getShortForm(df.getIRI("urn:test#", "t")));
    }
}
