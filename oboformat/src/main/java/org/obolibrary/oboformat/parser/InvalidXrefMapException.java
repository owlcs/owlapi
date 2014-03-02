package org.obolibrary.oboformat.parser;

/** The Class InvalidXrefMapException. */
public class InvalidXrefMapException extends OBOFormatException {

    // generated
    private static final long serialVersionUID = 40000L;

    /** Instantiates a new invalid xref map exception. */
    public InvalidXrefMapException() {
        super();
    }

    /**
     * Instantiates a new invalid xref map exception.
     * 
     * @param message
     *        the message
     * @param e
     *        the e
     */
    public InvalidXrefMapException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Instantiates a new invalid xref map exception.
     * 
     * @param message
     *        the message
     */
    public InvalidXrefMapException(String message) {
        super(message);
    }

    /**
     * Instantiates a new invalid xref map exception.
     * 
     * @param e
     *        the e
     */
    public InvalidXrefMapException(Throwable e) {
        super(e);
    }
}
