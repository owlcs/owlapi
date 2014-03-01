package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * An interface to an object which has a signature
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasSignature {

    /**
     * Gets the signature of this object.
     * 
     * @return A set of entities that represents the signature of this object.
     *         Not {@code null}. The returned set is a copy.
     */
    Set<OWLEntity> getSignature();
}
