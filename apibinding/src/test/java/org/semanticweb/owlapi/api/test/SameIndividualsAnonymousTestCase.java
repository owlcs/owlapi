package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class SameIndividualsAnonymousTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        // Can't round trip more than two in RDF! Also, same individuals axiom with anon individuals is not allowed
        // in OWL 2, but it should at least round trip
        axioms.add(getFactory().getOWLSameIndividualAxiom(getFactory().getOWLAnonymousIndividual(), getFactory().getOWLAnonymousIndividual()));
        return axioms;
    }
}
