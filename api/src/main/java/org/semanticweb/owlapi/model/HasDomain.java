package org.semanticweb.owlapi.model;

/**
 * An interface to objects that have a domain.
 * 
 * @author Matthew Horridge<br>
 *         Stanford University<br>
 *         Bio-Medical Informatics Research Group<br>
 *         Date: 18/02/2014
 * @param <D>
 *        domain type
 */
public interface HasDomain<D extends OWLObject> {

    /**
     * Gets the domain.
     * 
     * @return The domain. Not {@code null}.
     */
    D getDomain();
}
