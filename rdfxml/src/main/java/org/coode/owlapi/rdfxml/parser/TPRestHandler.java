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
public class TPRestHandler extends TriplePredicateHandler {

    protected  static int count = 0;

    public TPRestHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDF_REST.getIRI());
    }


    @Override
    @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!object.equals(OWLRDFVocabulary.RDF_NIL.getIRI())) {
            getConsumer().addRest(subject, object);
        }
        else {
            count++;
        }
        consumeTriple(subject, predicate, object);
    }
}
