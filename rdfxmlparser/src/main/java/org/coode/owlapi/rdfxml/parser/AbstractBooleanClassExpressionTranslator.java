package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLBooleanClassExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 */
public abstract class AbstractBooleanClassExpressionTranslator extends AbstractClassExpressionTranslator {

    protected AbstractBooleanClassExpressionTranslator(OWLRDFConsumer consumer) {
        super(consumer);
    }

    public abstract OWLBooleanClassExpression translate(IRI mainNode);
}
