package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Implementation of IndividualAppearance and AxiomAppearance that always
 * returns true - used to force output of ids in all cases.
 */
public class AlwaysOutputId implements IndividualAppearance, AxiomAppearance {

    @Override
    public boolean appearsMultipleTimes(OWLAnonymousIndividual i) {
        return true;
    }

    @Override
    public boolean appearsMultipleTimes(OWLAxiom ax) {
        return true;
    }
}
