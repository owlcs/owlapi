package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPObjectPropertyRangeHandler extends TriplePredicateHandler {

    public TPObjectPropertyRangeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_OBJECT_PROPERTY_RANGE.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return !isAnonymous(object);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        addAxiom(getDataFactory().getOWLObjectPropertyRangeAxiom(
                translateObjectProperty(subject),
                translateClassExpression(object), getPendingAnnotations()
        ));
        consumeTriple(subject, predicate, object);
    }
}
