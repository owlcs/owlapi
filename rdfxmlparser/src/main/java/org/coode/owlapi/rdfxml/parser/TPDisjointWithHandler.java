package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPDisjointWithHandler extends TriplePredicateHandler {

    public TPDisjointWithHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DISJOINT_WITH.getIRI());
    }


    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        // We can only handle disjoint axioms if we don't have to do
        // any translation of the subject or object - i.e. only if the
        // subject or object are named classes
        return !isSubjectOrObjectAnonymous(subject, object);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
        operands.add(translateClassExpression(subject));
        operands.add(translateClassExpression(object));
        addAxiom(getDataFactory().getOWLDisjointClassesAxiom(operands, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
