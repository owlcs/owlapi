package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParserException;
import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 * </p>
 * Translates an {@link org.semanticweb.owlapi.io.OWLParserException} to a {@link org.xml.sax.SAXException}.
 */
public class TranslatedOWLParserException extends SAXException {

    private OWLParserException parserException;

    public TranslatedOWLParserException(OWLParserException cause) {
        super(cause);
        this.parserException = cause;
    }

    public OWLParserException getParserException() {
        return parserException;
    }
}

