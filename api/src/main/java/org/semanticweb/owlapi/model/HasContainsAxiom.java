package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that contains axioms.
 * </p>
 */
public interface HasContainsAxiom {

    /**
     * Determines whether or not this object contains a particular {@link OWLAxiom}.
     * @param axiom The {@link OWLAxiom} to test for.
     * @return {@code true} if this object contains {@code axiom}, otherwise {@code false}.
     */
    boolean containsAxiom(OWLAxiom axiom);
}
