package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;

public class AlwaysOutputId implements IndividualAppearance {

    @Override
    public boolean appearsMultipleTimes(OWLAnonymousIndividual i) {
        return true;
    }
}
