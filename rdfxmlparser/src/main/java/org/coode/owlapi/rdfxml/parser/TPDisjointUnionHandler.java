package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPDisjointUnionHandler extends TriplePredicateHandler {

    public TPDisjointUnionHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.OWL_DISJOINT_UNION_OF.getIRI());
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && !getConsumer().isAnonymousNode(subject) && getConsumer().isClassExpression(subject);
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addClassExpression(subject, false);
        return false;
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        if (!getConsumer().isAnonymousNode(subject)) {
            OWLClass cls = (OWLClass) translateClassExpression(subject);
            Set<OWLClassExpression> classExpressions = getConsumer().translateToClassExpressionSet(object);
            addAxiom(getDataFactory().getOWLDisjointUnionAxiom(cls, classExpressions, getPendingAnnotations()));
            consumeTriple(subject, predicate, object);
        }
    }
}
