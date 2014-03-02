package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class GetLoadedOntologyTest {

    @Test
    public void testConvert() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology origOnt = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        "Prefix(:=<http://www.example.org/#>)\n"
                                + "Ontology(<http://example.org/>\n"
                                + "SubClassOf(:a :b)\n" + ")"));
        assertNotNull(origOnt);
        assertEquals(1, manager.getOntologies().size());
        assertNull(origOnt.getOntologyID().getVersionIRI());
        assertTrue(origOnt.getAxiomCount() > 0);
        OWLOntology newOnt = manager.getOntology(origOnt.getOntologyID()
                .getOntologyIRI());
        assertNotNull(newOnt); // SUCCEEDS
    }
}
