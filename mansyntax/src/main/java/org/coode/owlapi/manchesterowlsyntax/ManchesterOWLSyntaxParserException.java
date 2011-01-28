package org.coode.owlapi.manchesterowlsyntax;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19-Nov-2007<br><br>
 */
public class ManchesterOWLSyntaxParserException extends OWLParserException {

    public ManchesterOWLSyntaxParserException(String message, int lineNumber, int columnNumber) {
        super(message, lineNumber, columnNumber);
    }
}
