package org.semanticweb.owlapitools.profiles.violations;

import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

/** Entity not declared
 * 
 * @author ignazio */
public interface UndeclaredEntityViolation {
    /** @return entity not declared */
    OWLEntity getEntity();

    /** @return ontology */
    OWLOntology getOntology();
}
