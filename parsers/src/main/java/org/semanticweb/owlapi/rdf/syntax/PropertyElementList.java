package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses the propertyEltList production. The contents of the startElement
 * method implements also the propertyElt production. */
public class PropertyElementList implements State {
    protected NodeElement m_nodeElement;
    private RDFParser parser;

    /** @param nodeElement
     * @param parser */
    public PropertyElementList(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        String parseType = atts
                .getValue(RDFConstants.RDFNS, RDFConstants.ATTR_PARSE_TYPE);
        if (RDFConstants.PARSE_TYPE_LITERAL.equals(parseType)) {
            parser.pushState(new ParseTypeLiteralPropertyElement(m_nodeElement, parser));
        } else if (RDFConstants.PARSE_TYPE_RESOURCE.equals(parseType)) {
            parser.pushState(new ParseTypeResourcePropertyElement(m_nodeElement, parser));
        } else if (RDFConstants.PARSE_TYPE_COLLECTION.equals(parseType)) {
            parser.pushState(new ParseTypeCollectionPropertyElement(m_nodeElement, parser));
        } else if (parseType != null) {
            parser.pushState(new ParseTypeLiteralPropertyElement(m_nodeElement, parser));
        } else {
            String objectIRI = parser.getNodeIDResourceResourceIRI(atts);
            if (objectIRI != null) {
                parser.pushState(new EmptyPropertyElement(m_nodeElement, parser));
            } else {
                parser.pushState(new ResourceOrLiteralPropertyElement(m_nodeElement,
                        parser));
            }
        }
        parser.m_state.startElement(namespaceIRI, localName, qName, atts);
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
        parser.m_state.endElement(namespaceIRI, localName, qName);
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        if (!parser.isWhitespaceOnly(data, start, length)) {
            throw new RDFParserException(
                    "Cannot answer characters when object properties are excepted.",
                    parser.m_documentLocator);
        }
    }
}
