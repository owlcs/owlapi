package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.Serializable;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
public interface OWLOntologyImplementationFactory extends Serializable {

    /**
     * @param manager
     *        manager that will host the ontology
     * @param ontologyID
     *        ontology id
     * @return new ontology instance
     */
    OWLOntology createOWLOntology(OWLOntologyManager manager,
        OWLOntologyID ontologyID);
}
