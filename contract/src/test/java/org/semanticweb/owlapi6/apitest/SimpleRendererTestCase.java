package org.semanticweb.owlapi6.apitest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

public class SimpleRendererTestCase extends TestBase {

    private static final String RESULT = "test:t";
    private static final String TEST = "test";
    private static final String NS = "urn:test#";
    private static final OWLClass T = df.getOWLClass(NS, "t");
    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    public void shouldSetPrefixes() {
        testSubject.setPrefix(TEST, NS);
        assertEquals(RESULT, testSubject.render(T));
    }

    @Test
    public void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().setOntologyFormat(o, f);
        o.getPrefixManager().withPrefix(TEST, NS);
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals(RESULT, testSubject.render(T));
    }
}
