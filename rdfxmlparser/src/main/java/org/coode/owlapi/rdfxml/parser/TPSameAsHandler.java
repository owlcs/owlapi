package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPSameAsHandler extends TriplePredicateHandler {

    public TPSameAsHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_SAME_AS.getIRI());
    }


    @Override
    @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return true;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLIndividual> inds = new HashSet<OWLIndividual>();
        inds.add(translateIndividual(subject));
        inds.add(translateIndividual(object));
        addAxiom(getDataFactory().getOWLSameIndividualAxiom(inds, getConsumer().getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
        
        
    }
}
