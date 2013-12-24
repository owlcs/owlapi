package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object which has a signature
 * </p>
 */
public interface HasSignature {

    /**
     * Gets the signature of this object.
     * @return A set of entities that represents the signature of this object.  Not {@code null}.  The returned set is
     * a copy.
     */
    Set<OWLEntity> getSignature();
}
