package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 *     An interface to objects which have a property.
 * </p>
 */
public interface HasProperty<P extends OWLObject> {

    /**
     * Gets the property.
     * @return The property.  Not {@code null}.
     */
    P getProperty();
}
