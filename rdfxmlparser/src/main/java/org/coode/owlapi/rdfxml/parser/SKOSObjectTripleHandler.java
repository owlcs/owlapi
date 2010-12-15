package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 05-Nov-2008<br><br>
 */
public class SKOSObjectTripleHandler extends TriplePredicateHandler {


    public SKOSObjectTripleHandler(OWLRDFConsumer consumer, SKOSVocabulary predicate) {
        super(consumer, predicate.getIRI());
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLIndividual subj = getDataFactory().getOWLNamedIndividual(subject);
        OWLIndividual obj = getDataFactory().getOWLNamedIndividual(object);
        OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(predicate);
        addAxiom(getDataFactory().getOWLObjectPropertyAssertionAxiom(prop, subj, obj));
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return getPredicateIRI().equals(predicate);
    }
}
