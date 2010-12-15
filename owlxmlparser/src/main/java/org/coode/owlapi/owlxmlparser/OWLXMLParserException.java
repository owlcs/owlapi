package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLXMLParserException extends OWLParserException {

    public OWLXMLParserException(String message, int lineNumber, int columnNumber) {
        super(message, lineNumber, columnNumber);
    }
}
