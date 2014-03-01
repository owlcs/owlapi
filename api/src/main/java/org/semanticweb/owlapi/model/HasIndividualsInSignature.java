package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasIndividualsInSignature {

    /**
     * A convenience method that obtains the individuals that are in the
     * signature of this object
     * 
     * @return A set containing the individuals that are in the signature of
     *         this object.The set is a subset of the signature, and is not
     *         backed by the signature; it is a modifiable collection and
     *         changes are not reflected by the signature.
     */
    Set<OWLNamedIndividual> getIndividualsInSignature();
}
