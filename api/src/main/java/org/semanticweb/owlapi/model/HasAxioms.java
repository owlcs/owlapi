package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that contains axioms.
 * </p>
 */
public interface HasAxioms {

    /**
     * Gets the {@link OWLAxiom}s contained in this object.
     * @return The set of {@link OWLAxiom}s contained in this object.  Not {@code null}.  The returned set is a copy.
     * It may be modified but the modifications will not be reflected to the set of axioms contained in this object.
     */
    Set<OWLAxiom> getAxioms();
}
