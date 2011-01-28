package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLRDFParserException extends OWLParserException {

    public OWLRDFParserException() {
    }

    public OWLRDFParserException(String message) {
        super(message);
    }


    public OWLRDFParserException(String message, Throwable cause) {
        super(message, cause);
    }


    public OWLRDFParserException(Throwable cause) {
        super(cause);
    }
}
