package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLRDFXMLParserException extends OWLParserException {

    public OWLRDFXMLParserException(String message) {
        super(message);
    }


    public OWLRDFXMLParserException(String message, Throwable cause) {
        super(message, cause);
    }


    public OWLRDFXMLParserException(Throwable cause) {
        super(cause);
    }
}
