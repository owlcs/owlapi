package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Jan-2007<br><br>
 */
public class TPFirstLiteralHandler extends AbstractLiteralTripleHandler {

    public TPFirstLiteralHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override@SuppressWarnings("unused")
	public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
        return predicate.equals(OWLRDFVocabulary.RDF_FIRST.getIRI());
    }


    @Override@SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, OWLLiteral object) {
        return predicate.equals(OWLRDFVocabulary.RDF_FIRST.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) throws OWLOntologyChangeException {
        getConsumer().addFirst(subject, object);
        consumeTriple(subject, predicate, object);
    }
}
