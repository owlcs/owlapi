package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class TPHasValueHandler extends TriplePredicateHandler {

    public TPHasValueHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_HAS_VALUE.getIRI());
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addOWLRestriction(subject, false);
        return false;
    }
}
