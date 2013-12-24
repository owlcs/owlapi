package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that contains axioms and can provide subsets of these axioms by the type of axiom.
 * </p>
 */
public interface HasAxiomsByType {

    /**
     * Gets the axioms which are of the specified type.
     *
     * @param axiomType The type of axioms to be retrieved.
     * @return A set containing the axioms which are of the specified type. The set that is returned is a copy of the
     *         axioms in this object.  Modifications to the returned set will not be reflected in this object.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType);
}
