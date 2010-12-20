package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 20/12/2010
 */
public class TPOnDataRangeHandler extends TriplePredicateHandler {

    public TPOnDataRangeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ON_DATA_RANGE.getIRI());
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {

    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return false;
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addDataRange(object, true);
        return false;
    }
}
