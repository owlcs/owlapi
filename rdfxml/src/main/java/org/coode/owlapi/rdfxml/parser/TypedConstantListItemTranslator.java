package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Dec-2006<br><br>
 */
public class TypedConstantListItemTranslator implements ListItemTranslator<OWLLiteral> {

    private static final Logger logger = Logger.getLogger(TypedConstantListItemTranslator.class.getName());

    @SuppressWarnings("unused")
    public TypedConstantListItemTranslator(OWLRDFConsumer consumer) {
    }

    @SuppressWarnings("unused")
    public OWLLiteral translate(IRI firstObject) {
        logger.info("Cannot translate list item to a constant because rdf:first triple is a resource triple");
        return null;
    }


    public OWLLiteral translate(OWLLiteral firstObject) {
        return firstObject;
    }
}
