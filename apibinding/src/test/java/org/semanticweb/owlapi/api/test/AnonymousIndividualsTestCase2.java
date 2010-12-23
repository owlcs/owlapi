package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22/12/2010
 */
public class AnonymousIndividualsTestCase2 extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        // Originally submitted by Timothy Redmond
        OWLDataFactory factory = getFactory();
        String NS = "http://another.com/ont";
        OWLClass a = factory.getOWLClass(IRI.create(NS + "#A"));
        OWLAnnotationProperty p = factory.getOWLAnnotationProperty(IRI.create(NS + "#p"));
        OWLObjectProperty q = factory.getOWLObjectProperty(IRI.create(NS + "#q"));
        OWLAnonymousIndividual h = factory.getOWLAnonymousIndividual();
        OWLAnonymousIndividual i = factory.getOWLAnonymousIndividual();
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(factory.getOWLAnnotationAssertionAxiom(p, a.getIRI(), h));
        axioms.add(factory.getOWLClassAssertionAxiom(a, h));
        axioms.add(factory.getOWLObjectPropertyAssertionAxiom(q, h, i));
        axioms.add(factory.getOWLAnnotationAssertionAxiom(factory.getRDFSLabel(), h, factory.getOWLLiteral("Second", "en")));
        return axioms;
    }

    @Override
    protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
    }
}
