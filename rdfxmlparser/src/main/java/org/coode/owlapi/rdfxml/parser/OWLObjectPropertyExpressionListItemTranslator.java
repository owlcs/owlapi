package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class OWLObjectPropertyExpressionListItemTranslator implements ListItemTranslator<OWLObjectPropertyExpression> {

    private static final Logger logger = Logger.getLogger(OWLObjectPropertyExpressionListItemTranslator.class.getName());


    private OWLRDFConsumer consumer;


    public OWLObjectPropertyExpressionListItemTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLObjectPropertyExpression translate(IRI IRI) {
        return consumer.translateObjectPropertyExpression(IRI);
    }

    @SuppressWarnings("unused")
    public OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
        logger.info("Cannot translate list item as an object property, because rdf:first triple is a literal triple");
        return null;
    }
}
