package org.semanticweb.owlapi.api.test.anonymous;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class AnonymousOntologyTestCase {

    @Test
    public void shouldNotFailOnAnonymousOntologySearch()
            throws OWLOntologyCreationException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        man.createOntology(new OWLOntologyID());
        assertNull(man.getOntology(new OWLOntologyID()));
    }
}
