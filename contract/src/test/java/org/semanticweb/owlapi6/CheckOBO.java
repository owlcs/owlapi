package org.semanticweb.owlapi6;

import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;

public class CheckOBO {
    public static void main(String[] args) throws OWLOntologyCreationException {
        OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(
            OWLManager.getOWLDataFactory().getIRI("http://purl.obolibrary.org/obo/cl.obo"));
    }
}
