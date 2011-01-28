package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19/12/2010
 */
public class TPAnnotatedTargetHandler extends TriplePredicateHandler {

    public TPAnnotatedTargetHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ANNOTATED_TARGET.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addAnnotatedSource(object, subject);
        getConsumer().checkForAndProcessAnnotatedDeclaration(subject);
        return false;
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
