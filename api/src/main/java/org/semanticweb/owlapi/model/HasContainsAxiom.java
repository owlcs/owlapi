package org.semanticweb.owlapi.model;

/**
 * An interface to an object that contains axioms.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasContainsAxiom {

    /**
     * Determines whether or not this object contains a particular
     * {@link OWLAxiom}.
     * 
     * @param axiom
     *        The {@link OWLAxiom} to test for.
     * @return {@code true} if this object contains {@code axiom}, otherwise
     *         {@code false}.
     */
    boolean containsAxiom(OWLAxiom axiom);
}
