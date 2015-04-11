package uk.ac.manchester.cs.owl.owlapi;

import java.io.Serializable;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
public interface OWLOntologyImplementationFactory extends Serializable {

    /**
     * @param ontologyID
     *        ontology id
     * @return new ontology instance
     */
    OWLOntology createOWLOntology(OWLOntologyID ontologyID);
}
