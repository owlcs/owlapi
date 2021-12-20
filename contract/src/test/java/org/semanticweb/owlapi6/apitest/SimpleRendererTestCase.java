package org.semanticweb.owlapi6.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

class SimpleRendererTestCase extends TestBase {

    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    void shouldSetPrefixes() {
        testSubject.setPrefix("test", OWLAPI_TEST);
        assertEquals("test:A", testSubject.render(CLASSES.A));
    }

    @Test
    void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
        OWLOntology o = createAnon();
        o.getOWLOntologyManager().setOntologyFormat(o, format);
        o.getPrefixManager().withPrefix("test", OWLAPI_TEST);
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals("test:A", testSubject.render(CLASSES.A));
    }
}
