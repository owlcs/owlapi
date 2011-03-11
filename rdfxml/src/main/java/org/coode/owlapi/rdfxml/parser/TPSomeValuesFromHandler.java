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

    public TPSomeValuesFromHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SOME_VALUES_FROM.getIRI());
    }

    @Override
    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        handleTriple(subject, predicate, object);
        return false;
    }

    @Override
    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addOWLRestriction(subject, false);
        if(getConsumer().isDataRange(object)) {
            IRI property = getConsumer().getResourceObject(subject, OWLRDFVocabulary.OWL_ON_PROPERTY.getIRI(), false);
            if(property != null) {
                getConsumer().addDataProperty(property, false);
            }
        }
    }
}
