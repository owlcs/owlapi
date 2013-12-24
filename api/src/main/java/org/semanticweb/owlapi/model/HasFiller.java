package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to objects that have a filler.
 * </p>
 */
public interface HasFiller<T extends OWLObject> {

    T getFiller();
}
