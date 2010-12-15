package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 *
 * Indicates that a parse error happened when trying to parse an ontology.
 */
public class OWLParserException extends OWLException {

    private int lineNumber;

    private int columnNumber;

    public OWLParserException() {
        this.lineNumber = -1;
    }

    public OWLParserException(String message) {
        super(message);
        lineNumber = -1;
    }


    public OWLParserException(String message, Throwable cause) {
        super(message, cause);
        lineNumber = -1;
    }


    public OWLParserException(Throwable cause) {
        super(cause);
        lineNumber = -1;
    }

    public OWLParserException(String message, int lineNumber, int columnNumber) {
        super(message);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public OWLParserException(Throwable cause, int lineNumber, int columnNumber) {
        super(cause);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Gets the line number of the line that the parser
     * was parsing when the error occurred.
     * @return A positive integer which represents the line
     * number, or -1 if the line number could not be determined.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

//    public void setLineNumber(int lineNumber) {
//        this.lineNumber = lineNumber;
//    }


    @Override
	public String getMessage() {
        if (lineNumber != -1) {
            return super.getMessage() + " (Line " + lineNumber + ")";
        }
        else {
            return super.getMessage();
        }
    }
}
