package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Jan-2007<br><br>
 */
public class TPSomeValuesFromHandler extends TriplePredicateHandler {

    //protected static int count = 0;


    public TPSomeValuesFromHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI());
    //    count = 0;
    }


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addOWLRestriction(subject, false);
        return false;
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
