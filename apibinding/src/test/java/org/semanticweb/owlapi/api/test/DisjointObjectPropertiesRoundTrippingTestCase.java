package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Jun-2009
 */
public class DisjointObjectPropertiesRoundTrippingTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectProperty propA = getOWLObjectProperty("pA");
        OWLObjectProperty propB = getOWLObjectProperty("pB");
        OWLObjectProperty propC = getOWLObjectProperty("pC");
        axioms.add(getFactory().getOWLDisjointObjectPropertiesAxiom(propA, propB, propC));
        axioms.add(getFactory().getOWLDeclarationAxiom(propA));
        axioms.add(getFactory().getOWLDeclarationAxiom(propB));
        axioms.add(getFactory().getOWLDeclarationAxiom(propC));
        return axioms;
    }
}
