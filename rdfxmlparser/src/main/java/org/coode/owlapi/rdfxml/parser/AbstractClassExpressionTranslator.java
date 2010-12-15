package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public abstract class AbstractClassExpressionTranslator implements ClassExpressionTranslator {

    private OWLRDFConsumer consumer;


    protected AbstractClassExpressionTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLRDFConsumer getConsumer() {
        return consumer;
    }

//    protected <T extends Triple> T getFirstTripleWithPredicate(IRI mainNode, IRI predicate) {
//        return (T) consumer.getFirstTripleWithPredicate(mainNode, predicate);
//    }

    protected IRI getResourceObject(IRI subject, IRI predicate, boolean consume) {
        return consumer.getResourceObject(subject, predicate, consume);
    }

    protected OWLLiteral getLiteralObject(IRI subject, IRI predicate, boolean consume) {
        return consumer.getLiteralObject(subject, predicate, consume);
    }

    protected boolean isTriplePresent(IRI mainNode, IRI predicate, IRI value, boolean consume) {
        return consumer.isTriplePresent(mainNode, predicate, value, true);
    }

    protected Set<OWLClassExpression> translateToClassExpressionSet(IRI mainNode) {
        return consumer.translateToClassExpressionSet(mainNode);
    }

    protected Set<OWLIndividual> translateToIndividualSet(IRI mainNode) {
        return consumer.translateToIndividualSet(mainNode);
    }

    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }

    protected OWLClassExpression translateToClassExpression(IRI mainNode) {
        return consumer.translateClassExpression(mainNode);
    }

}
