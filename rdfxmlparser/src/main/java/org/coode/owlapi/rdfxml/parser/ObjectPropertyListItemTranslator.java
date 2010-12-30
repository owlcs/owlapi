package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Jan-2007<br><br>
 */
public class ObjectPropertyListItemTranslator implements ListItemTranslator<OWLObjectPropertyExpression> {

    private static final Logger logger = Logger.getLogger(ObjectPropertyListItemTranslator.class.getName());


    private OWLRDFConsumer consumer;


    public ObjectPropertyListItemTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    /**
     * The rdf:first triple that represents the item to be translated.  This triple
     * will point to something like a class expression, individual.
     * @param firstObject The rdf:first triple that points to the item to be translated.
     * @return The translated item.
     */
    public OWLObjectPropertyExpression translate(IRI firstObject) {
        consumer.addObjectProperty(firstObject, false);
        return consumer.translateObjectPropertyExpression(firstObject);
    }

    @SuppressWarnings("unused")
    public OWLObjectPropertyExpression translate(OWLLiteral firstObject) {
        logger.info("Cannot translate list item as an object property, because rdf:first triple is a literal triple");
        return null;
    }
}
