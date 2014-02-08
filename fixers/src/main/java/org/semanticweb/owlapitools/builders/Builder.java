package org.semanticweb.owlapitools.builders;

import java.util.List;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;

/** A builder interface for building owl objects
 * 
 * @author ignazio
 * @param <T>
 *            builder type */
public interface Builder<T> {
    /** @return freshly built object */
    T buildObject();

    /** If the builder is constructing an axiom, this method will add the axiom
     * and all needed changes to make the ontology fit in the expected profile;
     * the changes will be returned but there is no need to apply them, as they
     * have already been applied.
     * 
     * @param o
     *            ontology
     * @return changes the ontology to which the changes should be applied */
    List<OWLOntologyChange<?>> buildChanges(OWLOntology o);
}
