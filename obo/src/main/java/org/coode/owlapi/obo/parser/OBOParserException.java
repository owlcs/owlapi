package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OBOParserException extends OWLParserException {

    public OBOParserException(String message) {
        super(message);
    }


    public OBOParserException(String message, Throwable cause) {
        super(message, cause);
    }


    public OBOParserException(Throwable cause) {
        super(cause);
    }
}
