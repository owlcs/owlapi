package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class GTPAnnotationResourceTripleHandler extends AbstractResourceTripleHandler {

    public GTPAnnotationResourceTripleHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return !isAnonymous(subject) && getConsumer().isAnnotationProperty(predicate);
    }


    @Override
	public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return !getConsumer().isAxiom(subject) && !getConsumer().isAnnotation(subject) && (getConsumer().isAnnotationProperty(predicate) || getConsumer().isOntology(subject));
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {


        OWLAnnotationValue value;
        if (isAnonymous(object)) {
            value = getDataFactory().getOWLAnonymousIndividual(object.toString());
        }
        else {
            value = object;
        }
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(predicate);
        OWLAnnotation anno = getDataFactory().getOWLAnnotation(prop, value);
        if (getConsumer().isOntology(subject)) {
            // Assume we annotation our ontology?
            getConsumer().addOntologyAnnotation(anno);
        }
        else {
            OWLAxiom decAx = getDataFactory().getOWLAnnotationAssertionAxiom(subject, anno, getPendingAnnotations());
            addAxiom(decAx);
        }

    }
}
