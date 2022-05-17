package org.semanticweb.owlapi.impl.concurrent;

import org.semanticweb.owlapi.impl.OWLOntologyImpl;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class NonConcurrentOWLOntologyBuilder implements OWLOntologyBuilder {

    @Override
    public OWLOntology createOWLOntology(OWLOntologyManager manager, OWLOntologyID ontologyID,
        OntologyConfigurator config) {
        return new OWLOntologyImpl(manager, ontologyID, config);
    }
}
