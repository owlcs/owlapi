package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


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


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return !isAnonymous(subject) &&  !isAnonymous(object) && getConsumer().isAnnotationProperty(predicate);
    }


    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        boolean builtInAnnotationProperty = OWLRDFVocabulary.BUILT_IN_ANNOTATION_PROPERTY_IRIS.contains(predicate);
        return !getConsumer().isAxiom(subject) && !getConsumer().isAnnotation(subject) && (builtInAnnotationProperty || !predicate.isReservedVocabulary());
    }


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
        OWLAnnotationSubject annoSubject;
        if(isAnonymous(subject)) {
            annoSubject = getDataFactory().getOWLAnonymousIndividual(subject.toString());
        }
        else {
            annoSubject = subject;
        }

        if (getConsumer().isOntology(subject)) {
            // Assume we annotation our ontology?
            getConsumer().addOntologyAnnotation(anno);
        }
        else {
            OWLAxiom decAx = getDataFactory().getOWLAnnotationAssertionAxiom(annoSubject, anno, getPendingAnnotations());
            addAxiom(decAx);
        }
    }


}
