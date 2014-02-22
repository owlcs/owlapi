package org.semanticweb.owlapi.model;

/** An interface to objects that have a domain.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasDomain<D extends OWLObject> {
    /** Gets the domain.
     * 
     * @return The domain. Not {@code null}. */
    D getDomain();
}
