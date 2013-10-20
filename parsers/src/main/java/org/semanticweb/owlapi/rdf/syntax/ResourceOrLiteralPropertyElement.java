package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses resourcePropertyElt or literalPropertyElt productions. m_text is
 * {@code null} when startElement is expected on the actual property element. */
public class ResourceOrLiteralPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected String m_datatype;
    protected StringBuilder m_text;
    protected NodeElement m_innerNode;
    private RDFParser parser;

    /** @param nodeElement
     * @param parser */
    public ResourceOrLiteralPropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (m_text == null) {
            // this is the invocation on the outer element
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
            m_datatype = atts.getValue(RDFConstants.RDFNS, RDFConstants.ATTR_DATATYPE);
            m_text = new StringBuilder();
        } else {
            if (!parser.isWhitespaceOnly(m_text)) {
                throw new RDFParserException("Text was seen and new node is started.",
                        parser.m_documentLocator);
            }
            if (m_datatype != null) {
                throw new RDFParserException(
                        "rdf:datatype specified on a node with resource value.",
                        parser.m_documentLocator);
            }
            m_innerNode = new NodeElement(parser);
            parser.pushState(m_innerNode);
            parser.m_state.startElement(namespaceIRI, localName, qName, atts);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_innerNode != null) {
            parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, m_innerNode.getSubjectIRI(), m_reificationID);
        } else {
            parser.statementWithLiteralValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, m_text.toString(), m_datatype, m_reificationID);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        if (m_innerNode != null) {
            if (!parser.isWhitespaceOnly(data, start, length)) {
                throw new RDFParserException(
                        "Cannot answer characters when object properties are excepted.",
                        parser.m_documentLocator);
            }
        } else {
            m_text.append(data, start, length);
        }
    }
}
