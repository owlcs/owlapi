package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 14-Jul-2009
 */
public class DataQualifiedCardinalityTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLDataRange dr = getFactory().getIntegerOWLDatatype();
        OWLClass base = getOWLClass("A");
        axioms.add(getFactory().getOWLSubClassOfAxiom(base, getFactory().getOWLDataExactCardinality(3, prop, dr)));
        return axioms;
    }
}
