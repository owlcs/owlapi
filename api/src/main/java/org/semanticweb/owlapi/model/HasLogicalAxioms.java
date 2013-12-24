package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that contains a set of logical axioms.
 * </p>
 */
public interface HasLogicalAxioms {

    /**
     * Gets the set of {@link OWLLogicalAxiom}s contained within this object.
     * @return A set of {@link OWLLogicalAxiom}s that are contained within this object. Not {@code null}.
     * The returned set is a copy.  Modifications to the returned set will not affect the set of logical axioms
     * contained within this object.
     */
    Set<OWLLogicalAxiom> getLogicalAxioms();
}
