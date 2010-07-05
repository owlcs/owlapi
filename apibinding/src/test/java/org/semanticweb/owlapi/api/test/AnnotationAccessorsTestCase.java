package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class AnnotationAccessorsTestCase extends AbstractOWLAPITestCase {

    private static final IRI SUBJECT = IRI.create("http://owlapi.sourceforge.net/ontologies/test#X");

    private OWLAnnotationAssertionAxiom createAnnotationAssertionAxiom() {
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLAnnotationValue value = getFactory().getOWLLiteral("value");
        return getFactory().getOWLAnnotationAssertionAxiom(prop, SUBJECT, value);
    }


    public void testClassAccessor() {
        OWLOntology ont = getOWLOntology("ontology");
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        getManager().addAxiom(ont, ax);
        assertTrue(ont.getAnnotationAssertionAxioms(SUBJECT).contains(ax));
        OWLClass cls = getFactory().getOWLClass(SUBJECT);
        assertTrue(cls.getAnnotationAssertionAxioms(ont).contains(ax));
        assertTrue(cls.getAnnotations(ont).contains(ax.getAnnotation()));
    }
    
    public void testNamedIndividualAccessor() {
        OWLOntology ont = getOWLOntology("ontology");
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        getManager().addAxiom(ont, ax);
        assertTrue(ont.getAnnotationAssertionAxioms(SUBJECT).contains(ax));
        OWLNamedIndividual cls = getFactory().getOWLNamedIndividual(SUBJECT);
        assertTrue(cls.getAnnotationAssertionAxioms(ont).contains(ax));
        assertTrue(cls.getAnnotations(ont).contains(ax.getAnnotation()));
    }
    
    public void testObjectPropertyAccessor() {
        OWLOntology ont = getOWLOntology("ontology");
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        getManager().addAxiom(ont, ax);
        assertTrue(ont.getAnnotationAssertionAxioms(SUBJECT).contains(ax));
        OWLObjectProperty cls = getFactory().getOWLObjectProperty(SUBJECT);
        assertTrue(cls.getAnnotationAssertionAxioms(ont).contains(ax));
        assertTrue(cls.getAnnotations(ont).contains(ax.getAnnotation()));
    }
    
    public void testDataPropertyAccessor() {
        OWLOntology ont = getOWLOntology("ontology");
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        getManager().addAxiom(ont, ax);
        assertTrue(ont.getAnnotationAssertionAxioms(SUBJECT).contains(ax));
        OWLDataProperty cls = getFactory().getOWLDataProperty(SUBJECT);
        assertTrue(cls.getAnnotationAssertionAxioms(ont).contains(ax));
        assertTrue(cls.getAnnotations(ont).contains(ax.getAnnotation()));
    }
    
    public void testDatatypeAccessor() {
        OWLOntology ont = getOWLOntology("ontology");
        OWLAnnotationAssertionAxiom ax = createAnnotationAssertionAxiom();
        getManager().addAxiom(ont, ax);
        assertTrue(ont.getAnnotationAssertionAxioms(SUBJECT).contains(ax));
        OWLDatatype cls = getFactory().getOWLDatatype(SUBJECT);
        assertTrue(cls.getAnnotationAssertionAxioms(ont).contains(ax));
        assertTrue(cls.getAnnotations(ont).contains(ax.getAnnotation()));
    }
}
