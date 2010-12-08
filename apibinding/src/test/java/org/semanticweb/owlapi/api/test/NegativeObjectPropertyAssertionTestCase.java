package org.semanticweb.owlapi.api.test;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Jun-2009
 */
public class NegativeObjectPropertyAssertionTestCase extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        OWLIndividual subject = getOWLIndividual("iA");
        OWLIndividual object = getOWLIndividual("iB");
        axioms.add(getFactory().getOWLNegativeObjectPropertyAssertionAxiom(prop, subject, object));
        return axioms;
    }
}
