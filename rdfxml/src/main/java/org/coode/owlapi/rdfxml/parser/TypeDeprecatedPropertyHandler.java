package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 10-Dec-2009
 */
public class TypeDeprecatedPropertyHandler extends BuiltInTypeHandler {

    public TypeDeprecatedPropertyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DEPRECATED_PROPERTY.getIRI());
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        addAxiom(getDataFactory().getDeprecatedOWLAnnotationAssertionAxiom(subject));
    }
}
