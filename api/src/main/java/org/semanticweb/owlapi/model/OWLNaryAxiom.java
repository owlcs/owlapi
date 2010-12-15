package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Jan-2010
 * </p>
 * Represents an axiom that contains two or more operands that could also be represented with multiple pairwise axioms
 */
public interface OWLNaryAxiom extends OWLAxiom {

    /**
     * Gets this axiom as a set of pairwise axioms.  Note that annotations on this axiom will not be copied to each axiom
     * returned in the set of pairwise axioms.
     * @return This axiom as a set of pairwise axioms.
     */
    Set<? extends OWLNaryAxiom> asPairwiseAxioms();
}
