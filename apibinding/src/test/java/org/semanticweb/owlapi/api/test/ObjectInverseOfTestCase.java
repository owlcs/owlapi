package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class ObjectInverseOfTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        OWLClass clsA = getOWLClass("A");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        OWLClassExpression ce = getFactory().getOWLObjectSomeValuesFrom(prop.getInverseProperty(), clsA);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLSubClassOfAxiom(getOWLClass("B"), ce));
        return axioms;
    }
}
