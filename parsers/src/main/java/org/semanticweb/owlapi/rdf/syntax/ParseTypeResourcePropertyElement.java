package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses parseTypeResourcePropertyElt production. */
public class ParseTypeResourcePropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    private RDFParser parser;

    public ParseTypeResourcePropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
        m_reificationID = m_nodeElement.getReificationID(atts);
        NodeElement anonymousNodeElement = new NodeElement(parser);
        anonymousNodeElement.startDummyElement(atts);
        parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(), m_propertyIRI,
                anonymousNodeElement.getSubjectIRI(), m_reificationID);
        parser.pushState(new PropertyElementList(anonymousNodeElement, parser));
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
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
