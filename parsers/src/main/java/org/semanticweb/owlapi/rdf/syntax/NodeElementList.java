package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses the nodeElementList production. */
public class NodeElementList implements State {
    private RDFParser parser;

    /** @param parser */
    public NodeElementList(RDFParser parser) {
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        parser.pushState(new NodeElement(parser));
        parser.state.startElement(namespaceIRI, localName, qName, atts);
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
        parser.state.endElement(namespaceIRI, localName, qName);
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        if (!parser.isWhitespaceOnly(data, start, length)) {
            throw new RDFParserException(
                    "Expecting an object element instead of character content.",
                    parser.m_documentLocator);
        }
    }
}
