package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 04-Feb-2009
 */
public class TypeAnnotationHandler extends BuiltInTypeHandler {

    public TypeAnnotationHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_ANNOTATION.getIRI());
    }

    @Override
    @SuppressWarnings("unused")
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        getConsumer().addAnnotationIRI(subject);
    }
}
