package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class LargeDisjointClassesAxiomTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        Set<OWLClass> clses = new HashSet<OWLClass>();
        for(int i = 0; i < 1000; i++) {
            clses.add(getOWLClass("Cls" + i));
        }
        axioms.add(getFactory().getOWLDisjointClassesAxiom(clses));
        return axioms;
    }
}
