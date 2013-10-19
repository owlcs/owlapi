package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Base class for all parser states. */
public interface State {
    /** @param namespaceIRI
     * @param localName
     * @param qName
     * @param atts
     * @throws SAXException */
    void
            startElement(String namespaceIRI, String localName, String qName,
                    Attributes atts) throws SAXException;

    /** @param namespaceIRI
     * @param localName
     * @param qName
     * @throws SAXException */
    void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException;

    /** @param data
     * @param start
     * @param length
     * @throws SAXException */
    void characters(char[] data, int start, int length) throws SAXException;
}
