package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses parseTypeLiteralPropertyElt production. */
public class ParseTypeLiteralPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected int m_depth;
    protected StringBuilder m_content;
    private RDFParser parser;

    public ParseTypeLiteralPropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (m_depth == 0) {
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
            m_content = new StringBuilder();
        } else {
            m_content.append('<');
            m_content.append(qName);
            int length = atts.getLength();
            for (int i = 0; i < length; i++) {
                m_content.append(' ');
                m_content.append(atts.getQName(i));
                m_content.append("=\"");
                m_content.append(atts.getValue(i));
                m_content.append("\"");
            }
            m_content.append(">");
        }
        m_depth++;
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_depth == 1) {
            parser.statementWithLiteralValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, m_content.toString(), RDFConstants.RDF_XMLLITERAL,
                    m_reificationID);
            parser.popState();
        } else {
            m_content.append("</");
            m_content.append(qName);
            m_content.append(">");
        }
        m_depth--;
    }

    @Override
    public void characters(char[] data, int start, int length) {
        m_content.append(data, start, length);
    }
}
