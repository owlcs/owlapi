package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLIndividual;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jun-2009
 */
public class ClassAssertionWithAnonymousIndividualTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLIndividual ind = getFactory().getOWLAnonymousIndividual("a");
        OWLClass cls = getOWLClass("A");
        axioms.add(getFactory().getOWLClassAssertionAxiom(cls, ind));
        axioms.add(getFactory().getOWLDeclarationAxiom(cls));
        return axioms;
    }
}
