package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class GTPAnnotationLiteralHandler extends AbstractLiteralTripleHandler {

    public GTPAnnotationLiteralHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return !isAnonymous(subject) && !getConsumer().isAnnotation(subject) && getConsumer().isAnnotationProperty(predicate);
    }


    public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        boolean axiom = getConsumer().isAxiom(subject);
        boolean annotation = getConsumer().isAnnotation(subject);
        return !axiom && !annotation && getConsumer().isAnnotationProperty(predicate);
    }


    public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {
        consumeTriple(subject, predicate, object);
        if(getConsumer().isOntology(subject)) {
        	// PATCH:	getConsumer().addOntologyAnnotation(getDataFactory().getOWLAnnotation(getDataFactory().getOWLAnnotationProperty(predicate), object, getPendingAnnotations()));
        	// ORIG:	getConsumer().addOntologyAnnotation(getDataFactory().getOWLAnnotation(getDataFactory().getOWLAnnotationProperty(predicate), object));
            // This change makes sense given the else clause; however, I haven't been able to create or find a test that excercises this change.
        	getConsumer().addOntologyAnnotation(getDataFactory().getOWLAnnotation(getDataFactory().getOWLAnnotationProperty(predicate), object, getPendingAnnotations()));
        }
        else {
            OWLAnnotationAssertionAxiom ax = getDataFactory().getOWLAnnotationAssertionAxiom(getDataFactory().getOWLAnnotationProperty(predicate), subject, object, getPendingAnnotations());
            addAxiom(ax);
        }
    }
}
