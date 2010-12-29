package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class FunctionalObjectPropertyInverseTestCase extends AbstractAxiomsRoundTrippingTestCase {

     @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLFunctionalObjectPropertyAxiom(getOWLObjectProperty("p").getInverseProperty()));
        return axioms;
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        // Can't represent inverse object property frames in Manchester OWL Syntax
//        super.testManchesterOWLSyntax();
    }
}
