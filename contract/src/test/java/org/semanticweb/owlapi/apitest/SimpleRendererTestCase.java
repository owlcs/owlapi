package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.utility.SimpleRenderer;

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
