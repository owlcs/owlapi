package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPEquivalentPropertyHandler extends TriplePredicateHandler {

    private static final Logger logger = Logger.getLogger(TPEquivalentPropertyHandler.class.getName());

    public TPEquivalentPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_EQUIVALENT_PROPERTY.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return (getConsumer().isObjectPropertyOnly(subject) && getConsumer().isObjectPropertyOnly(object)) || (getConsumer().isDataPropertyOnly(subject) && getConsumer().isDataPropertyOnly(object));
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        // If either is an object property then translate as object properties
        if (getConsumer().isObjectPropertyOnly(subject) || getConsumer().isObjectPropertyOnly(object)) {
            translateEquivalentObjectProperties(subject, predicate, object);
        }
        else if (getConsumer().isDataPropertyOnly(subject) || getConsumer().isDataPropertyOnly(object)) {
            Set<OWLDataPropertyExpression> props = new HashSet<OWLDataPropertyExpression>();
            props.add(translateDataProperty(subject));
            props.add(translateDataProperty(object));
            addAxiom(getDataFactory().getOWLEquivalentDataPropertiesAxiom(props, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else {
            // Assume object!?
            translateEquivalentObjectProperties(subject, predicate, object);
            logger.fine("Assuming equivalent object properties because property types " + "are ambiguous: " + subject + " <-> " + object);
        }
    }


    private void translateEquivalentObjectProperties(IRI subject, IRI predicate, IRI object) throws OWLOntologyChangeException {
        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        props.add(translateObjectProperty(subject));
        props.add(translateObjectProperty(object));
        addAxiom(getDataFactory().getOWLEquivalentObjectPropertiesAxiom(props, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
