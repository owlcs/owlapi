package org.coode.owlapi.rdfxml.parser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Dec-2006<br><br>
 * <p/>
 * A base handler for equivalent class axioms where the axiom is stated in
 * a direct way without an equivalent class triple.  For example
 * A intersectionOf (C or C)
 */
public abstract class AbstractNamedEquivalentClassAxiomHandler extends TriplePredicateHandler {

    public AbstractNamedEquivalentClassAxiomHandler(OWLRDFConsumer consumer, IRI predicateIRI) {
        super(consumer, predicateIRI);
    }


    @Override  @SuppressWarnings("unused")
	public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object) {
        return false;
    }


    @Override
	public boolean canHandle(IRI subject, IRI predicate, IRI object) {
        return super.canHandle(subject, predicate, object) && !isAnonymous(subject);
    }


    @Override
	public void handleTriple(IRI subject, IRI predicate, IRI object) throws UnloadableImportException {
        consumeTriple(subject, predicate, object);
        Set<OWLClassExpression> operands = new HashSet<OWLClassExpression>();
        operands.add(translateClassExpression(subject));
        operands.add(translateEquivalentClass(object));
        addAxiom(getDataFactory().getOWLEquivalentClassesAxiom(operands));
    }

    protected abstract OWLClassExpression translateEquivalentClass(IRI mainNode);

}
