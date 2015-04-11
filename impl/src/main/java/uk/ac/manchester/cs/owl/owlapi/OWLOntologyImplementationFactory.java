package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

import java.io.Serializable;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
public interface OWLOntologyImplementationFactory extends Serializable {

    OWLOntology createOWLOntology(OWLOntologyID ontologyID);
}
