package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Jul-2009
 */
public class TPAnnotatedSourceHandler extends TriplePredicateHandler {

    public TPAnnotatedSourceHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ANNOTATED_SOURCE.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addAnnotatedSource(object, subject);
        getConsumer().checkForAndProcessAnnotatedDeclaration(subject);
        return false;
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
