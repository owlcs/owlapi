package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Jun-2009
 */
public class DisjointDataPropertiesRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDataProperty propA = getOWLDataProperty("pA");
        OWLDataProperty propB = getOWLDataProperty("pB");
        OWLDataProperty propC = getOWLDataProperty("pC");
        axioms.add(getFactory().getOWLDisjointDataPropertiesAxiom(propA, propB, propC));
        axioms.add(getFactory().getOWLDeclarationAxiom(propA));
        axioms.add(getFactory().getOWLDeclarationAxiom(propB));
        axioms.add(getFactory().getOWLDeclarationAxiom(propC));
        return axioms;
    }
}
