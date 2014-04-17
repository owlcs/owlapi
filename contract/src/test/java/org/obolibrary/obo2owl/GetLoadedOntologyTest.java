package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.google.common.base.Optional;

@SuppressWarnings("javadoc")
public class GetLoadedOntologyTest {

    @Test
    public void testConvert() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        String input = "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntology origOnt = manager.loadOntologyFromOntologyDocument(source);
        assertNotNull(origOnt);
        assertEquals(1, manager.getOntologies().size());
        assertFalse(origOnt.getOntologyID().getVersionIRI().isPresent());
        assertTrue(origOnt.getAxiomCount() > 0);
        Optional<IRI> ontologyIRI = origOnt.getOntologyID().getOntologyIRI();
        assertTrue(ontologyIRI.isPresent());
        OWLOntology newOnt = manager.getOntology(ontologyIRI.get());
        assertNotNull(newOnt);
    }
}
