package org.semanticweb.owlapi.model;

/**
 * An interface to an object that has a signature.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasContainsEntityInSignature {

    /**
     * Determines if the signature of this object contains the specified entity.
     * 
     * @param owlEntity
     *        The entity
     * @return {@code true} if the signature of this object contains
     *         {@code owlEntity}, otherwise {@code false}
     */
    boolean containsEntityInSignature(OWLEntity owlEntity);
}
