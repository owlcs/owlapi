package org.semanticweb.owlapi6.apitest.syntax.rdfxml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

class EntitiesTestCase extends TestBase {

    @Test
    void shouldRoundtripEntities() {
        OWLOntology o = loadFrom(new StringDocumentSource(TestFiles.roundtripEntities,
            "urn:test#test", new RDFXMLDocumentFormat(), null));
        o.getOWLOntologyManager().getOntologyConfigurator().withUseNamespaceEntities(true);
        StringDocumentTarget o2 = saveOntology(o);
        assertTrue(o2.toString().contains("<owl:priorVersion rdf:resource=\"&vin;test\"/>"));
    }

    @Test
    void shouldNotIncludeExternalEntities() {
        OWLOntology o =
            loadFrom(TestFiles.doNotIncludeExternalEntities, new RDFXMLDocumentFormat());
        OWLOntology o1 = o(AnnotationAssertion(RDFSComment(), IRIS.iriTest, Literal("")));
        equal(o, o1);
    }
}
