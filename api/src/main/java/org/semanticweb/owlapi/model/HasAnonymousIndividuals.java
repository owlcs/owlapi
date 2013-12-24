package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 */
public interface HasAnonymousIndividuals {

    /**
     * Gets the anonymous individuals occurring in this object. The set is a copy, changes are not reflected back.
     * @return A set of anonymous individuals.  Not {@code null}.
     */
    Set<OWLAnonymousIndividual> getAnonymousIndividuals();
}
