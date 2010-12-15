package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;


/**
 * Throws if an RDF error is encountered while parsing RDF.
 */
public class RDFParserException extends SAXException {

    protected String m_publicId;

    protected String m_systemId;

    protected int m_lineNumber;

    protected int m_columnNumber;


    public RDFParserException(String message) {
        this(message, null, null, -1, -1);
    }


    public RDFParserException(String message, Locator locator) {
        this(message, locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
    }


    public RDFParserException(String message, String publicId, String systemId, int lineNumber, int columnNumber) {
        super((lineNumber != -1 || columnNumber != -1 ? "[line=" + lineNumber + ":" + "column=" + columnNumber + "] " : "") + message);
        m_publicId = publicId;
        m_systemId = systemId;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
    }


    public String getPublicId() {
        return m_publicId;
    }


    public String getSystemId() {
        return m_systemId;
    }


    public int getLineNumber() {
        return m_lineNumber;
    }


    public int getColumnNumber() {
        return m_columnNumber;
    }
}
