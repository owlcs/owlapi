package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasAddAxioms {

    /**
     * A convenience method that adds a set of axioms to an ontology. The
     * appropriate AddAxiom change objects are automatically generated.
     * 
     * @param ont
     *        The ontology to which the axioms should be added. Not {@code null}
     *        .
     * @param axioms
     *        The axioms to be added. Not {@code null}.
     * @return A list of ontology changes that represent the changes which took
     *         place in order to add the axioms.
     */
    List<OWLOntologyChange<?>> addAxioms(OWLOntology ont,
            Set<? extends OWLAxiom> axioms);
}
