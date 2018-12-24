package uk.ac.manchester.cs.owlapi6.concurrent;

import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyBuilder;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;

import uk.ac.manchester.cs.owlapi6.OWLOntologyImpl;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class NonConcurrentOWLOntologyBuilder implements OWLOntologyBuilder {

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID) {
        return new OWLOntologyImpl(manager, ontologyID);
    }
}
