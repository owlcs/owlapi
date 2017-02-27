package org.obolibrary.oboformat.parser;

import org.semanticweb.owlapi.io.OWLParserException;

/**
 * The Class OBOFormatException.
 */
public class OBOFormatException extends OWLParserException {

    /**
     * Instantiates a new oBO format exception.
     */
    public OBOFormatException() {
        super();
    }

    /**
     * Instantiates a new oBO format exception.
     *
     * @param message the message
     */
    public OBOFormatException(String message) {
        super(message);
    }

    /**
     * Instantiates a new oBO format exception.
     *
     * @param e the e
     */
    public OBOFormatException(Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new oBO format exception.
     *
     * @param message the message
     * @param e the e
     */
    public OBOFormatException(String message, Throwable e) {
        super(message, e);
    }
}
