package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that has a signature.
 * </p>
 */
public interface HasContainsEntityInSignature {

    /**
     * Determines if the signature of this object contains the specified entity.
     *
     * @param owlEntity The entity
     * @return {@code true} if the signature of this object contains {@code owlEntity}, otherwise
     *         {@code false}
     */
    boolean containsEntityInSignature(OWLEntity owlEntity);

}
