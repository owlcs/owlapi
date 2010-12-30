package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class IndividualListItemTranslator implements ListItemTranslator<OWLIndividual> {

    private static final Logger logger = Logger.getLogger(IndividualListItemTranslator.class.getName());


    private OWLRDFConsumer consumer;


    public IndividualListItemTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }


    public OWLIndividual translate(IRI IRI) {
        return consumer.translateIndividual(IRI);
    }

    @SuppressWarnings("unused")
    public OWLIndividual translate(OWLLiteral firstObject) {
        logger.info("Cannot translate list item to individual, because rdf:first triple is a literal triple");
        return null;
    }
}
