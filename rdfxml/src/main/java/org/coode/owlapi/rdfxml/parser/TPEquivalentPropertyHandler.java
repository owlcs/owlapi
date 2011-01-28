package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPEquivalentPropertyHandler extends TriplePredicateHandler {

    public TPEquivalentPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLAnnotation> pendingAnnotations = getPendingAnnotations();
        if (getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) {
            Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
            props.add(translateObjectProperty(subject));
            props.add(translateObjectProperty(object));
            addAxiom(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(props, pendingAnnotations));
            consumeTriple(subject, predicate, object);
        }
        if (getConsumer().isDataProperty(subject) && getConsumer().isDataProperty(object)) {
            Set<OWLDataPropertyExpression> props = new HashSet<OWLDataPropertyExpression>();
            props.add(translateDataProperty(subject));
            props.add(translateDataProperty(object));
            addAxiom(getDataFactory().getOWLEquivalentDataPropertiesAxiom(props, pendingAnnotations));
            consumeTriple(subject, predicate, object);
        }
        // TODO: LOG ERROR
    }

}
