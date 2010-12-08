package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 14-Jul-2009
 */
public class DisjointDataPropertiesTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLDataPropertyExpression propA = getOWLDataProperty("propA");
        OWLDataPropertyExpression propB = getOWLDataProperty("propB");
        axioms.add(getFactory().getOWLDisjointDataPropertiesAxiom(propA, propB));
        return axioms;
    }
}
