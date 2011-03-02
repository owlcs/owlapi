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
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        inferTypes(subject, object);
        return super.canHandle(subject, predicate, object) && ((getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) || (getConsumer().isDataProperty(subject) && getConsumer().isDataProperty(object)));
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if(getConsumer().isDataProperty(subject) && getConsumer().isDataProperty(object)) {
            addAxiom(getDataFactory().getOWLDisjointDataPropertiesAxiom(CollectionFactory.createSet(translateDataProperty(subject), translateDataProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
        if(getConsumer().isObjectProperty(subject) && getConsumer().isObjectProperty(object)) {
            addAxiom(getDataFactory().getOWLDisjointObjectPropertiesAxiom(CollectionFactory.createSet(translateObjectProperty(subject), translateObjectProperty(object)), getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        inferTypes(subject, object);
        return false;
    }
}
