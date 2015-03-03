package org.coode.owlapi.obo.parser;


import org.semanticweb.owlapi.io.OWLParserException;

@Deprecated
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
