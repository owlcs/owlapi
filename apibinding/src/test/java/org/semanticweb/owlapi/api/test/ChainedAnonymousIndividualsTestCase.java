package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 */
public class ChainedAnonymousIndividualsTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        IRI annoPropIRI = IRI.create("http://owlapi.sourceforge.net/ontology#annoProp");
        OWLAnnotationProperty property = getFactory().getOWLAnnotationProperty(annoPropIRI);
        IRI subject = IRI.create("http://owlapi.sourceforge.net/ontology#subject");
        axioms.add(getFactory().getOWLDeclarationAxiom(getFactory().getOWLNamedIndividual(subject)));
        OWLAnonymousIndividual individual1 = getFactory().getOWLAnonymousIndividual();
        OWLAnonymousIndividual individual2 = getFactory().getOWLAnonymousIndividual();
        OWLAnonymousIndividual individual3 = getFactory().getOWLAnonymousIndividual();
        OWLAnnotationAssertionAxiom annoAssertion1 = getFactory().getOWLAnnotationAssertionAxiom(property, subject, individual1);
        OWLAnnotationAssertionAxiom annoAssertion2 = getFactory().getOWLAnnotationAssertionAxiom(property, individual1, individual2);
        OWLAnnotationAssertionAxiom annoAssertion3 = getFactory().getOWLAnnotationAssertionAxiom(property, individual2, individual3);
        axioms.add(annoAssertion1);
        axioms.add(annoAssertion2);
        axioms.add(annoAssertion3);
        return axioms;
    }
}
