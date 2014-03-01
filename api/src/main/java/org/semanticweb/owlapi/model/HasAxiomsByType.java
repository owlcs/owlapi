package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * An interface to an object that contains axioms and can provide subsets of
 * these axioms by the type of axiom.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasAxiomsByType {

    /**
     * Gets the axioms which are of the specified type.
     * 
     * @param axiomType
     *        The type of axioms to be retrieved.
     * @param <T>
     *        axiom type
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in this object.
     *         Modifications to the returned set will not be reflected in this
     *         object.
     */
    <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType);
}
