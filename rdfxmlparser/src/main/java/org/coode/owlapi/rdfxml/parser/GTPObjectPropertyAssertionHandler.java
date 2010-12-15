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
public class GTPObjectPropertyAssertionHandler extends AbstractResourceTripleHandler {

    public GTPObjectPropertyAssertionHandler(OWLRDFConsumer consumer) {
        super(consumer);
    }


    @Override
	public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return !getConsumer().isAnnotationProperty(predicate) && !OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(predicate) && !getConsumer().isOntology(subject);
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        addAxiom(getDataFactory().getOWLObjectPropertyAssertionAxiom(translateObjectProperty(predicate), translateIndividual(subject), translateIndividual(object), getPendingAnnotations()));
    }
}
