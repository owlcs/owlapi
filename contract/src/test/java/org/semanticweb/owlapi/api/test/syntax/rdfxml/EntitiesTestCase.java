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
    void shouldRoundtripEntities() throws Exception {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(TestFiles.roundtripEntities,
            iri("urn:test#", "test"), new RDFXMLDocumentFormat(), null));
        o.getOWLOntologyManager().getOntologyConfigurator().withUseNamespaceEntities(true);
        StringDocumentTarget o2 = saveOntology(o);
        assertTrue(o2.toString().contains("<owl:priorVersion rdf:resource=\"&vin;test\"/>"));
    }

    @Test
    void shouldNotIncludeExternalEntities() throws Exception {
        OWLOntology o = loadOntologyFromString(TestFiles.doNotIncludeExternalEntities,
            new RDFXMLDocumentFormat());
        OWLOntology o1 = m.createOntology();
        o1.add(df.getOWLAnnotationAssertionAxiom(df.getRDFSComment(), iri("urn:test:", "i"),
            df.getOWLLiteral("")));
        equal(o, o1);
    }
}
