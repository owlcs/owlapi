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

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        inferTypes(subject, object);
        return false;
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

        if (isStrict()) {
            if (isObjectPropertyStrict(subject) && isClassExpressionStrict(object)) {
                translateAsObjectPropertyRange(subject, predicate, object);
            }
            else if (isDataPropertyStrict(subject) && isDataRangeStrict(object)) {
                translateAsDataPropertyRange(subject, predicate, object);
            }
            else if (getConsumer().isAnnotationProperty(subject) && !getConsumer().isAnonymousNode(object)) {
                translateAsAnnotationPropertyRange(subject, predicate, object);
            }
        }
        else {
            if (isAnnotationPropertyOnly(subject) && !isAnonymous(object)) {
                translateAsAnnotationPropertyRange(subject, predicate, object);
            }
            else if (isClassExpressionLax(object)) {
                translateAsObjectPropertyRange(subject, predicate, object);
            }
            else if (isDataRangeLax(object)) {
                translateAsDataPropertyRange(subject, predicate, object);
            }
            else if (isObjectPropertyLax(subject)) {
                translateAsObjectPropertyRange(subject, predicate, object);
            }
            else if (isDataPropertyLax(subject)) {
                translateAsDataPropertyRange(subject, predicate, object);
            }
            else {
                translateAsAnnotationPropertyRange(subject, predicate, object);
            }

        }
    }

    private void translateAsAnnotationPropertyRange(IRI subject, IRI predicate, IRI object) {
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
        addAxiom(getDataFactory().getOWLAnnotationPropertyRangeAxiom(prop, object, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }

    private void translateAsDataPropertyRange(IRI subject, IRI predicate, IRI object) {
        OWLDataPropertyExpression property = translateDataProperty(subject);
        OWLDataRange dataRange = translateDataRange(object);
        addAxiom(getDataFactory().getOWLDataPropertyRangeAxiom(property, dataRange, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }

    private void translateAsObjectPropertyRange(IRI subject, IRI predicate, IRI object) {
        OWLObjectPropertyExpression property = translateObjectProperty(subject);
        OWLClassExpression range = translateClassExpression(object);
        addAxiom(getDataFactory().getOWLObjectPropertyRangeAxiom(property, range, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }

}
