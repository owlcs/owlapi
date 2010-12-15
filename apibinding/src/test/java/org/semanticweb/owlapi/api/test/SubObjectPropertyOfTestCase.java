package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class SubObjectPropertyOfTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectProperty propA = getOWLObjectProperty("p");
        OWLObjectProperty propB = getOWLObjectProperty("q");
        axioms.add(getFactory().getOWLSubObjectPropertyOfAxiom(propA, propB));
        return axioms;
    }
}
