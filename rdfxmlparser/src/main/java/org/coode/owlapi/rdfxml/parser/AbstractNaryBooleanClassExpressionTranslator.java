package org.coode.owlapi.rdfxml.parser;

import java.util.Set;
import java.util.logging.Logger;

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
public abstract class AbstractNaryBooleanClassExpressionTranslator extends AbstractClassExpressionTranslator {

    private Logger logger = Logger.getLogger(OWLRDFConsumer.class.getName());

    public AbstractNaryBooleanClassExpressionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }


    public OWLClassExpression translate(IRI mainNode) {
        IRI object = getResourceObject(mainNode, getPredicateIRI(), true);
        Set<OWLClassExpression> operands = translateToClassExpressionSet(object);
        if(operands.size() < 2) {
            logger.fine("Number of operands is less than 2");
            if(operands.size() == 1) {
                return operands.iterator().next();
            }
            else {
                // Zero - just return thing
                logger.fine("Number of operands is zero! Translating as owl:Thing");
                return getDataFactory().getOWLThing();
            }
        }
        return createClassExpression(operands);
    }

    protected abstract OWLClassExpression createClassExpression(Set<OWLClassExpression> operands);

    protected abstract IRI getPredicateIRI();
}
