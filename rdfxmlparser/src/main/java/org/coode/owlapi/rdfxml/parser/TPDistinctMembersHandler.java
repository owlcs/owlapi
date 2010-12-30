package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 09-Dec-2006<br><br>
 */
public class TPDistinctMembersHandler extends TriplePredicateHandler {

    public TPDistinctMembersHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DISTINCT_MEMBERS.getIRI());
    }


    @Override@SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // We need all of the list triples to be loaded :(
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLIndividual> inds = getConsumer().translateToIndividualSet(object);
        addAxiom(getDataFactory().getOWLDifferentIndividualsAxiom(inds, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
