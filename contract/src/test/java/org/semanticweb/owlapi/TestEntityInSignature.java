package org.semanticweb.owlapi;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

public class TestEntityInSignature {

    @Test
    public void testContainsEntityInSignature() throws OWLOntologyCreationException {
        OWLOntology ont1 = OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(this.getClass().getResourceAsStream("/entity_in_signature_test.ttl"));
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        OWLClass class1 = factory.getOWLClass(IRI.create("http://purl.obolibrary.org/obo/UO_0000196"));

        // Should print true, does print true
        System.out.println(ont1.containsEntityInSignature(class1));

        // Should print false, instead prints true
        OWLOntology ont2 = OWLManager.createOWLOntologyManager().createOntology(IRI.create("http://purl.obolibrary.org/obo/uo.owl"));
        System.out.println(ont2.containsEntityInSignature(class1));

        // Should print false, instead prints true
        OWLOntology ont3 = OWLManager.createOWLOntologyManager().createOntology(ont1.getOntologyID());
        System.out.println(ont3.containsEntityInSignature(class1));
    }

}
