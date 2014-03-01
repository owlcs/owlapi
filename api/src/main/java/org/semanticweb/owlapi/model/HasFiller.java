package org.semanticweb.owlapi.model;

/**
 * An interface to objects that have a filler.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 * @param <T>
 *        filler type
 */
public interface HasFiller<T extends OWLObject> {

    /** @return filler */
    T getFiller();
}
