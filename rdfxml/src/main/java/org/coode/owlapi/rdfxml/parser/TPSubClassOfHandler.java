package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class TPSubClassOfHandler extends TriplePredicateHandler {


    public TPSubClassOfHandler(OWLRDFConsumer consumer) {
        super(consumer, OWLRDFVocabulary.RDFS_SUBCLASS_OF.getIRI());
    }

    @Override
    public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && getConsumer().isClassExpression(subject) && getConsumer().isClassExpression(object);
    }

    @Override
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        getConsumer().addClassExpression(subject, false);
        getConsumer().addClassExpression(object, false);
        return !isSubjectOrObjectAnonymous(subject, object);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        OWLClassExpression subClass = translateClassExpression(subject);
        OWLClassExpression supClass = translateClassExpression(object);
        Set<OWLAnnotation> pendingAnnotations = getConsumer().getPendingAnnotations();
        OWLAxiom ax = getDataFactory().getOWLSubClassOfAxiom(subClass, supClass, pendingAnnotations);
        addAxiom(ax);
        consumeTriple(subject, predicate, object);
    }
}
