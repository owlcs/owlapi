package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jul-2009
 */
public class DisjointObjectPropertiesTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectPropertyExpression propA = getOWLObjectProperty("propA");
        OWLObjectPropertyExpression propB = getOWLObjectProperty("propB");
        axioms.add(getFactory().getOWLDisjointObjectPropertiesAxiom(propA, propB));
        return axioms;
    }
}
