package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jul-2009
 */
public class TPPropertyDisjointWithHandler extends TriplePredicateHandler {

    public TPPropertyDisjointWithHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_PROPERTY_DISJOINT_WITH.getIRI());
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if(getConsumer().isDataPropertyOnly(subject) || getConsumer().isDataPropertyOnly(object)) {
            addAxiom(getDataFactory().getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(translateDataProperty(subject), translateDataProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        else if(getConsumer().isObjectPropertyOnly(subject) || getConsumer().isObjectPropertyOnly(object)) {
            addAxiom(getDataFactory().getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(translateObjectProperty(subject), translateObjectProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return (getConsumer().isObjectPropertyOnly(subject) && getConsumer().isObjectPropertyOnly(object)) ||
                (getConsumer().isDataPropertyOnly(subject) && getConsumer().isDataPropertyOnly(object));
    }
}
