package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPTypeHandler extends TriplePredicateHandler {

    private static final Logger logger = Logger.getLogger(TPTypeHandler.class.getName());

    public TPTypeHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDF_TYPE.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // Can handle if object isn;t anonymous and either the object
        // IRI is owl:Thing, or it is not part of the build in vocabulary
        getConsumer().addClassExpression(object, false);
        if(isAnonymous(object)) {
            return false;
        }
        if(object.isReservedVocabulary()) {
            return object.equals(OWLRDFVocabulary.OWL_THING.getIRI());
        }
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (OWLRDFVocabulary.BUILT_IN_VOCABULARY_IRIS.contains(object)) {
            if (!object.equals(OWLRDFVocabulary.OWL_THING.getIRI())) {
                // Can't have instance of built in vocabulary!
                // Shall we throw an exception here?
                logger.fine("Individual of builtin type " + object);
            }
        }
        addAxiom(getDataFactory().getOWLClassAssertionAxiom(translateClassExpression(object), translateIndividual(subject), getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
