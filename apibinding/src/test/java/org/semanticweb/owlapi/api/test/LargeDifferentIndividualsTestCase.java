package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class LargeDifferentIndividualsTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        Set<OWLNamedIndividual> inds = new HashSet<OWLNamedIndividual>();
        for (int i = 0; i < 1000; i++) {
            inds.add(getOWLIndividual("i" + i));
        }
        axioms.add(getFactory().getOWLDifferentIndividualsAxiom(inds));
        return axioms;
    }
}
