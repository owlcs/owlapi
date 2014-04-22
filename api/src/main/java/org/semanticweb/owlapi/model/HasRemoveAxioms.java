package org.semanticweb.owlapi.model;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasRemoveAxioms {

    /**
     * A convenience method that removes a set of axioms from an ontology. The
     * appropriate RemoveAxiom change objects are automatically generated.
     * 
     * @param ont
     *        The ontology from which the axioms should be removed.
     * @param axioms
     *        The axioms to be removed. Not {@code null}
     * @return A list of ontology changes that represent the changes which took
     *         place in order to remove the axioms. Not {@code null}.
     * @throws OWLOntologyChangeException
     *         if there was a problem removing the axioms
     */
    @Nonnull
    List<OWLOntologyChange<?>> removeAxioms(OWLOntology ont,
            Set<? extends OWLAxiom> axioms);
}
