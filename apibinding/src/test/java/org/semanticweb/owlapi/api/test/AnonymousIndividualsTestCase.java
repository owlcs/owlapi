package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 30-Jun-2009
 */
public class AnonymousIndividualsTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLAnonymousIndividual ind = getFactory().getOWLAnonymousIndividual();
        axioms.add(getFactory().getOWLObjectPropertyAssertionAxiom(
                getOWLObjectProperty("p"),
                getOWLIndividual("i1"),
                ind
        ));
        axioms.add(getFactory().getOWLObjectPropertyAssertionAxiom(
                getOWLObjectProperty("p"),
                ind,
                getOWLIndividual("i2")
        ));
        return axioms;
    }
}
