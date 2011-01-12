package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPPropertyRangeHandler extends TriplePredicateHandler {


    public TPPropertyRangeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_RANGE.getIRI());
    }


    public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        return false;
    }


    public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject) && getConsumer().isClassExpression(object)) {
            OWLObjectPropertyExpression property = translateObjectProperty(subject);
            OWLClassExpression range = translateClassExpression(object);
            addAxiom(getDataFactory().getOWLObjectPropertyRangeAxiom(property, range, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (getConsumer().isDataProperty(subject) && getConsumer().isDataRange(object)) {
            OWLDataPropertyExpression property = translateDataProperty(subject);
            OWLDataRange dataRange = translateDataRange(object);
            addAxiom(getDataFactory().getOWLDataPropertyRangeAxiom(property, dataRange, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (getConsumer().isAnnotationProperty(subject) && !getConsumer().isAnonymousNode(object)) {
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(!isStrict()) {
            // TODO: Handle the case where the object is anonymous
            OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
            addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

}
