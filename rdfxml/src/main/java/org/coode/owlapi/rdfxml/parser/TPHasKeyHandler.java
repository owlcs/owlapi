package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class TPHasKeyHandler extends TriplePredicateHandler {

    private OptimisedListTranslator<OWLPropertyExpression> listTranslator;

    public TPHasKeyHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_HAS_KEY.getIRI());
        this.listTranslator = new OptimisedListTranslator<OWLPropertyExpression>(getConsumer(), new HasKeyListItemTranslator(getConsumer()));
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addClassExpression(subject, false);
        return false;
    }

    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (getConsumer().isClassExpression(subject)) {
            consumeTriple(subject, predicate, object);
            OWLClassExpression ce = translateClassExpression(subject);
            Set<OWLPropertyExpression> props = listTranslator.translateToSet(object);
            addAxiom(getDataFactory().getOWLHasKeyAxiom(ce, props, getPendingAnnotations()));
        }
    }
}
