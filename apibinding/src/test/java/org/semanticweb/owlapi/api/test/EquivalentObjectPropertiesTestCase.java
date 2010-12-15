package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jun-2009
 */
public class EquivalentObjectPropertiesTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectProperty propA = getOWLObjectProperty("propA");
        OWLObjectProperty propB = getOWLObjectProperty("propB");
        axioms.add(getFactory().getOWLEquivalentObjectPropertiesAxiom(propA, propB));
        axioms.add(getFactory().getOWLDeclarationAxiom(propA));
        axioms.add(getFactory().getOWLDeclarationAxiom(propB));
        return axioms;
    }
}
