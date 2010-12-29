package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class SubObjectPropertyOfInverseTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectPropertyExpression propA = getOWLObjectProperty("p").getInverseProperty();
        OWLObjectPropertyExpression propB = getOWLObjectProperty("q").getInverseProperty();
        axioms.add(getFactory().getOWLSubObjectPropertyOfAxiom(propA, propB));
        return axioms;
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        // Can't represent inverse object property frames in Manchester OWL Syntax
//        super.testManchesterOWLSyntax();
    }
}
