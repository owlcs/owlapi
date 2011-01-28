package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.OWLDataFactory;


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

    protected OWLDataFactory getDataFactory() {
        return consumer.getDataFactory();
    }

}
