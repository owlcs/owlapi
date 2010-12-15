package org.semanticweb.owlapi.io;

import org.xml.sax.SAXException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Dec-2009
 * </p>
 * Many of the various OWL document formats are written in XML.  This exception wraps a {@link org.xml.sax.SAXException}
 * as an <code>OWLParserException</code>.  Parser implementers should use this class or subclasses of this class to
 * wrap <code>SAXException</code>s.
 */
public class OWLParserSAXException extends OWLParserException {

    public OWLParserSAXException(SAXException cause) {
        super(cause);
    }

    @Override
    public SAXException getCause() {
        return (SAXException) super.getCause();
    }
}
