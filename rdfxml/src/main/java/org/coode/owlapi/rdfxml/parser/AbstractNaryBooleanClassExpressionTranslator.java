package org.coode.owlapi.rdfxml.parser;

import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 *
 * A base class for translators that translate a set of triples to an Nary boolean class expressions -
 * i.e. an OWLIntersectionOf or OWLUnionOf class expression.
 */
public abstract class AbstractNaryBooleanClassExpressionTranslator extends AbstractBooleanClassExpressionTranslator {


    private IRI predicate;

    public AbstractNaryBooleanClassExpressionTranslator(OWLRDFConsumer consumer, IRI predicate) {
        super(consumer);
        this.predicate = predicate;
    }

    public Set<OWLClassExpression> translateClassExpressions(IRI mainNode) {
        IRI object = getConsumer().getResourceObject(mainNode, predicate, true);
        return getConsumer().translateToClassExpressionSet(object);
    }

    public boolean matches(IRI mainNode) {
        if(!getConsumer().isClassExpression(mainNode)) {
            return false;
        }
        IRI operandsList = getConsumer().getResourceObject(mainNode, predicate, false);
        if(operandsList == null) {
            return false;
        }
        return true;
    }
}
