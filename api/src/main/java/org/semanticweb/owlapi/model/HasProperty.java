package org.semanticweb.owlapi.model;

/** An interface to objects which have a property.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0
 * @param <P>
 *            property type */
public interface HasProperty<P extends OWLObject> {
    /** Gets the property.
     * 
     * @return The property. Not {@code null}. */
    P getProperty();
}
