package org.coode.owlapi.rdfxml.parser;

import java.util.List;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
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
public class TPSubPropertyOfHandler extends TriplePredicateHandler {

    public TPSubPropertyOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_SUB_PROPERTY_OF.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        if(getConsumer().isObjectProperty(object)) {
            getConsumer().addObjectProperty(subject, false);
        }
        else if(getConsumer().isDataProperty(object)) {
            getConsumer().addDataProperty(object, false);
        }
        else if(getConsumer().isAnnotationProperty(object)) {
            getConsumer().addAnnotationProperty(subject, false);
        }
        else if(getConsumer().isObjectProperty(subject)) {
            getConsumer().addObjectProperty(object, false);
        }
        else if(getConsumer().isDataProperty(subject)) {
            getConsumer().addDataProperty(object, false);
        }
        else if(getConsumer().isAnnotationProperty(subject)) {
            getConsumer().addAnnotationProperty(object, false);
        }
        return false;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

        // First check for object property chain
        if (!isStrict() && getConsumer().hasPredicate(subject, OWLRDFVocabulary.OWL_PROPERTY_CHAIN.getIRI())) {
            // Property chain
            IRI chainList = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_PROPERTY_CHAIN.getIRI(), true);
            List<OWLObjectPropertyExpression> properties = getConsumer().translateToObjectPropertyList(chainList);
            addAxiom(getDataFactory().getOWLSubPropertyChainOfAxiom(properties, translateObjectProperty(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (!isStrict() && getConsumer().hasPredicate(subject, OWLRDFVocabulary.RDF_FIRST.getIRI())) {
            // Legacy object property chain representation
            List<OWLObjectPropertyExpression> properties = getConsumer().translateToObjectPropertyList(subject);
            addAxiom(getDataFactory().getOWLSubPropertyChainOfAxiom(properties, translateObjectProperty(object), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if (getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) {
            translateSubObjectProperty(subject, predicate, object);
        }
        else if (getConsumer().isDataProperty(subject) && getConsumer().isDataProperty(object)) {
            translateSubDataProperty(subject, predicate, object);
        }
        else if(!isStrict()) {
            OWLAnnotationProperty subAnnoProp = getDataFactory().getOWLAnnotationProperty(subject);
            OWLAnnotationProperty superAnnoProp = getDataFactory().getOWLAnnotationProperty(object);
            addAxiom(getDataFactory().getOWLSubAnnotationPropertyOfAxiom(subAnnoProp, superAnnoProp, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }


    private void translateSubObjectProperty(IRI subject, IRI predicate, IRI object) throws OWLOntologyChangeException {
        // Object - object
        addAxiom(getDataFactory().getOWLSubObjectPropertyOfAxiom(translateObjectProperty(subject), translateObjectProperty(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }


    private void translateSubDataProperty(IRI subject, IRI predicate, IRI object) throws OWLOntologyChangeException {
        // Data - Data
        addAxiom(getDataFactory().getOWLSubDataPropertyOfAxiom(translateDataProperty(subject), translateDataProperty(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
