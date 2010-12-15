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
 * Date: 09-Jun-2009
 */
public class ObjectPropertyAssertionWithAnonymousIndividualsTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
	protected Set<? extends OWLAxiom> createAxioms() {
        OWLIndividual subject = getFactory().getOWLAnonymousIndividual();
        OWLIndividual object = getFactory().getOWLAnonymousIndividual();
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(getFactory().getOWLObjectPropertyAssertionAxiom(prop, subject, object));
        axioms.add(getFactory().getOWLDeclarationAxiom(prop));
        return axioms;
    }
}
