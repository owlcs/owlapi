package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class AnonymousIndividualRoundtripTestCase extends AbstractAxiomsRoundTrippingTestCase {

    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        String NS = "http://smi-protege.stanford.edu/ontologies/AnonymousIndividuals.owl";
        OWLDataFactory factory = getFactory();
        OWLClass a = factory.getOWLClass(IRI.create(NS + "#A"));
        OWLAnnotationProperty annoPropP = factory.getOWLAnnotationProperty(IRI.create(NS + "#annoPropP"));
        OWLObjectProperty objProp = factory.getOWLObjectProperty(IRI.create(NS + "#objProp"));
        OWLAnonymousIndividual anonIndH = factory.getOWLAnonymousIndividual();
        OWLAnonymousIndividual anonIndI = factory.getOWLAnonymousIndividual();
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLAnnotation annotation1 = factory.getOWLAnnotation(annoPropP, anonIndH);
        axioms.add(factory.getOWLAnnotationAssertionAxiom(a.getIRI(), annotation1));
        axioms.add(factory.getOWLClassAssertionAxiom(a, anonIndH));
        axioms.add(factory.getOWLObjectPropertyAssertionAxiom(objProp, anonIndH, anonIndI));
        OWLAnnotation annotation2 = factory.getOWLAnnotation(factory.getRDFSLabel(), factory.getOWLStringLiteral("Second", "en"));
        axioms.add(factory.getOWLAnnotationAssertionAxiom(anonIndH, annotation2));
        return axioms;
    }
}
