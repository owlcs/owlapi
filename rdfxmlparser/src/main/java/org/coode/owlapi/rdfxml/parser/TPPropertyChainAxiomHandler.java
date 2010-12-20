package org.coode.owlapi.rdfxml.parser;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 01-Jun-2009
 */
public class TPPropertyChainAxiomHandler extends TriplePredicateHandler {

    public TPPropertyChainAxiomHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_PROPERTY_CHAIN_AXIOM.getIRI());
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addObjectProperty(object, false);
        return false;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLObjectPropertyExpression superProp = getConsumer().translateObjectPropertyExpression(subject);
        List<OWLObjectPropertyExpression> chain = getConsumer().translateToObjectPropertyList(object);
        consumeTriple(subject, predicate, object);
        Set<OWLAnnotation> annos = getPendingAnnotations();
        addAxiom(getDataFactory().getOWLSubPropertyChainOfAxiom(chain, superProp, annos));
    }

}
