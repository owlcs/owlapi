package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 */
public class TPOnPropertyHandler extends TriplePredicateHandler {

    public TPOnPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI());
    }

    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        if (!getConsumer().getConfiguration().isStrict()) {
            getConsumer().addOWLRestriction(subject, false);
        }
        return false;
    }


    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
    }
}
