package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;

class EntitiesTestCase extends TestBase {

    @Test
    void shouldRoundtripEntities() {
        OWLOntology o = loadFrom(new StringDocumentSource(TestFiles.roundtripEntities, iriTest,
            new RDFXMLDocumentFormat(), null));
        o.getOWLOntologyManager().getOntologyConfigurator().withUseNamespaceEntities(true);
        StringDocumentTarget o2 = saveOntology(o);
        assertTrue(o2.toString().contains("<owl:priorVersion rdf:resource=\"&vin;test\"/>"));
    }

    @Test
    void shouldNotIncludeExternalEntities() {
        OWLOntology o =
            loadFrom(TestFiles.doNotIncludeExternalEntities, new RDFXMLDocumentFormat());
        OWLOntology o1 = o(AnnotationAssertion(RDFSComment(), iriTest, Literal("")));
        equal(o, o1);
    }
}
