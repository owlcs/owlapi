package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.io.OWLParserException;

/**
 * Parser exception.
 */
@Deprecated
public class OBOParserException extends OWLParserException {

    private static final long serialVersionUID = 40000L;

    /**
     * @param message
     *        message
     */
    public OBOParserException(String message) {
        super(message);
    }

    /**
     * @param message
     *        message
     * @param cause
     *        cause
     */
    public OBOParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     *        cause
     */
    public OBOParserException(Throwable cause) {
        super(cause);
    }
}
