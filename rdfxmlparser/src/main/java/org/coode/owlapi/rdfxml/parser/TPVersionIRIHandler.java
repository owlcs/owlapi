package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Apr-2009
 */
public class TPVersionIRIHandler extends TriplePredicateHandler {

    public TPVersionIRIHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_VERSION_IRI.getIRI());
    }


    @Override
	public void handleTriple(IRI subject,
                             IRI predicate,
                             IRI object) throws UnloadableImportException {
        OWLOntology ontology = getConsumer().getOntology();
        OWLOntologyID ontologyID = new OWLOntologyID(ontology.getOntologyID().getOntologyIRI(), object);
        getConsumer().setOntologyID(ontologyID);
        consumeTriple(subject, predicate, object);
    }


    @Override
    @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject,
                                      IRI predicate,
                                      IRI object) {
        // Always apply at the end
        return false;
    }


    @Override
    @SuppressWarnings("unused")
	public boolean canHandle(IRI subject,
                             IRI predicate,
                             IRI object) {
        return subject.equals(getConsumer().getOntology().getOntologyID().getOntologyIRI());
    }
}
