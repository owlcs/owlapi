package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;

/**
 * An interface for checking if an anonymous individual is references multiple
 * times as object of a statement. This requires an id to be emitted.
 */
public interface IndividualAppearance {

    /**
     * @param i
     *        an anonymous individual
     * @return true if i appears more than once as object.
     */
    boolean appearsMultipleTimes(OWLAnonymousIndividual i);
}
