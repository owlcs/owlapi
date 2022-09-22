package org.semanticweb.owlapi.apitest.syntax.rdfxml;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

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
