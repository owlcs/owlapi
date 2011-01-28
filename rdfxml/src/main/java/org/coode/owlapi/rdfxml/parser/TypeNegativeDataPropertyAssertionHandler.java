package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
@SuppressWarnings("deprecation")
public class TypeNegativeDataPropertyAssertionHandler extends BuiltInTypeHandler {

    public TypeNegativeDataPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_NEGATIVE_DATA_PROPERTY_ASSERTION.getIRI());
    }

    @Override
    @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        IRI source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SOURCE_INDIVIDUAL.getIRI(), true);
        if (source == null) {
            source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_SUBJECT.getIRI(), true);
        }
        if (source == null) {
            source = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_SUBJECT.getIRI(), true);
        }

        IRI property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ASSERTION_PROPERTY.getIRI(), true);
        if (property == null) {
            property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_PREDICATE.getIRI(), true);
        }
        if (property == null) {
            property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.RDF_PREDICATE.getIRI(), true);
        }
        OWLLiteral target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_TARGET_VALUE.getIRI(), true);
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.OWL_OBJECT.getIRI(), true);
        }
        if (target == null) {
            target = getConsumer().getLiteralObject(subject, OWLRDFVocabulary.RDF_OBJECT.getIRI(), true);
        }
        OWLIndividual sourceInd = getConsumer().getOWLIndividual(source);
        OWLDataPropertyExpression prop = getConsumer().translateDataPropertyExpression(property);
        consumeTriple(subject, predicate, object);
        getConsumer().translateAnnotations(subject);
        Set<OWLAnnotation> annos = getConsumer().getPendingAnnotations();
        addAxiom(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(prop, sourceInd, target, annos));

    }
}
