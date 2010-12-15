package org.semanticweb.owlapi.io;

import java.net.URISyntaxException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 */
public class OWLParserURISyntaxException extends OWLParserException {

    public OWLParserURISyntaxException(URISyntaxException cause, int lineNumber, int columnNumber) {
        super(cause, lineNumber, columnNumber);
    }

    @Override
    public URISyntaxException getCause() {
        return (URISyntaxException) super.getCause();
    }
}
