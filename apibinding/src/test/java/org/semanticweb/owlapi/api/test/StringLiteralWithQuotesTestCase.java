package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 21-Sep-2009
 */
public class StringLiteralWithQuotesTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLNamedIndividual ind = getOWLIndividual("i");
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLLiteral literal = getFactory().getOWLLiteral("Test \"literal\"");
        axioms.add(getFactory().getOWLDataPropertyAssertionAxiom(prop, ind, literal));
        OWLLiteral literal2 = getFactory().getOWLLiteral("Test 'literal'");
        axioms.add(getFactory().getOWLDataPropertyAssertionAxiom(prop, ind, literal2));
        OWLLiteral literal3 = getFactory().getOWLLiteral("Test \"\"\"literal\"\"\"");
        axioms.add(getFactory().getOWLDataPropertyAssertionAxiom(prop, ind, literal3));
        return axioms;
    }
}
