package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that have a domain.
 * </p>
 */
public interface HasDomain<D extends OWLObject> {

    /**
     * Gets the domain.
     * 
     * @return The domain. Not {@code null}.
     */
    D getDomain();
}
