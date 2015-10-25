package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;

/**
 * Implementation of IndividualAppearance that always returns true - used to
 * force output of ids in all cases.
 */
public class AlwaysOutputId implements IndividualAppearance {

    @Override
    public boolean appearsMultipleTimes(OWLAnonymousIndividual i) {
        return true;
    }
}
