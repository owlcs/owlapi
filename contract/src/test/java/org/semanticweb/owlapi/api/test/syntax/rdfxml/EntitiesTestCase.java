package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

public class EntitiesTestCase extends TestBase {

    @Test
    public void shouldRoundtripEntities() throws Exception {
        OWLOntology o = loadOntologyFromString(
            new StringDocumentSource(TestFiles.roundtripEntities, "urn:test:test", new RDFXMLDocumentFormat(), null));
        o.getOWLOntologyManager().getOntologyConfigurator().withUseNamespaceEntities(true);
        StringDocumentTarget o2 = saveOntology(o);
        assertTrue(o2.toString().contains("<owl:priorVersion rdf:resource=\"&vin;test\"/>"));
    }

    @Test
    public void shouldNotIncludeExternalEntities() throws Exception {
        OWLOntology o = loadOntologyFromString(TestFiles.doNotIncludeExternalEntities, new RDFXMLDocumentFormat());
        OWLOntology o1 = m.createOntology();
        o1.add(df.getOWLAnnotationAssertionAxiom(df.getIRI("urn:test:i"), df.getRDFSComment("")));
        equal(o, o1);
    }
}
