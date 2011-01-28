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


    public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addClassExpression(subject, false);
        getConsumer().addClassExpression(object, false);
        // NB: In strict parsing the above type triples won't get added because they aren't explicit,
        // so we need an extra check to see if there are type triples for the classes
        return !isSubjectOrObjectAnonymous(subject, object) && isSubjectAndObjectClassExpression(subject, object);
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && isSubjectAndObjectClassExpression(subject, object);
    }

    public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
        operands.add(translateClassExpression(subject));
        operands.add(translateClassExpression(object));
        addAxiom(getDataFactory().getOWLDisjointClassesAxiom(operands, getPendingAnnotations()));
        consumeTriple(subject, predicate, object);
    }
}
