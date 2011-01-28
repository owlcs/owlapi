package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPPropertyDomainHandler extends TriplePredicateHandler {

    public TPPropertyDomainHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_DOMAIN.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        return false;
    }

    @Override
	public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        if (getConsumer().isObjectProperty(subject) && getConsumer().isClassExpression(object)) {
            translateObjectPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isDataPropertyOnly(subject) && getConsumer().isClassExpression(object)) {
            translateDataPropertyDomain(subject, predicate, object);
        }
        else if (getConsumer().isAnnotationProperty(subject) && getConsumer().isClassExpression(object) && !getConsumer().isAnonymousNode(object)) {
            translateAnnotationPropertyDomain(subject, predicate, object);
        }
        else if(!isStrict()) {
            translateAnnotationPropertyDomain(subject, predicate, object);
        }
    }

    private void translateAnnotationPropertyDomain(IRI subject, IRI predicate, IRI object) {
        OWLAnnotationProperty prop = getDataFactory().getOWLAnnotationProperty(subject);
        addAxiom(getDataFactory().getOWLAnnotationPropertyDomainAxiom(prop, object, getPendingAnnotations()));
        // TODO: Handle anonymous domain - error?
        consumeTriple(subject, predicate, object);
    }


    private void translateDataPropertyDomain(IRI subject,
                                             IRI predicate,
                                             IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLDataPropertyDomainAxiom(translateDataProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }


    private void translateObjectPropertyDomain(IRI subject,
                                               IRI predicate,
                                               IRI object) throws OWLOntologyChangeException {
        addAxiom(getDataFactory().getOWLObjectPropertyDomainAxiom(translateObjectProperty(subject), translateClassExpression(object), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
