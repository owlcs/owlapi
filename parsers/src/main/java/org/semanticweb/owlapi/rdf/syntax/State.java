package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Base class for all parser states. */
public interface State {
    /** @param namespaceIRI
     *            namespaceIRI
     * @param localName
     *            localName
     * @param qName
     *            qName
     * @param atts
     *            atts
     * @throws SAXException
     *             sax exception */
    void
            startElement(String namespaceIRI, String localName, String qName,
                    Attributes atts) throws SAXException;

    /** @param namespaceIRI
     *            namespaceIRI
     * @param localName
     *            localName
     * @param qName
     *            qName
     * @throws SAXException
     *             SAXException */
    void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException;

    /** @param data
     *            data
     * @param start
     *            start
     * @param length
     *            length
     * @throws SAXException
     *             SAXException */
    void characters(char[] data, int start, int length) throws SAXException;
}
