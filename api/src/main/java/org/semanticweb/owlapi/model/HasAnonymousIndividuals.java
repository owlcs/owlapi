package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasAnonymousIndividuals {

    /**
     * Gets the anonymous individuals occurring in this object. The set is a
     * copy, changes are not reflected back.
     * 
     * @return A set of anonymous individuals. Not {@code null}.
     */
    Set<OWLAnonymousIndividual> getAnonymousIndividuals();
}
