package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class TPInverseOfHandler extends TriplePredicateHandler {

    public TPInverseOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_INVERSE_OF.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addOWLObjectProperty(subject);
        getConsumer().addOWLObjectProperty(object);
        return !isSubjectOrObjectAnonymous(subject, object);
    }


    @Override
	public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return !isSubjectOrObjectAnonymous(subject, object);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        addAxiom(getDataFactory().getOWLInverseObjectPropertiesAxiom(
                translateObjectProperty(subject),
                translateObjectProperty(object), getPendingAnnotations()
        ));
        consumeTriple(subject, predicate, object);
    }
}
