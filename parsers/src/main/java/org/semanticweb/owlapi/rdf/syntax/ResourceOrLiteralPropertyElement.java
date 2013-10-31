package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses resourcePropertyElt or literalPropertyElt productions. m_text is
 * {@code null} when startElement is expected on the actual property element. */
public class ResourceOrLiteralPropertyElement implements State {
    protected NodeElement nodeElement;
    protected String propertyIRI;
    protected String reificationID;
    protected String datatype;
    protected StringBuilder text;
    protected NodeElement innerNode;
    private RDFParser parser;

    /** @param nodeElement
     * @param parser */
    public ResourceOrLiteralPropertyElement(NodeElement nodeElement, RDFParser parser) {
        this.nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (text == null) {
            // this is the invocation on the outer element
            propertyIRI = nodeElement.getPropertyIRI(namespaceIRI + localName);
            reificationID = nodeElement.getReificationID(atts);
            datatype = atts.getValue(RDFConstants.RDFNS, RDFConstants.ATTR_DATATYPE);
            text = new StringBuilder();
        } else {
            if (!parser.isWhitespaceOnly(text)) {
                throw new RDFParserException("Text was seen and new node is started.",
                        parser.m_documentLocator);
            }
            if (datatype != null) {
                throw new RDFParserException(
                        "rdf:datatype specified on a node with resource value.",
                        parser.m_documentLocator);
            }
            innerNode = new NodeElement(parser);
            parser.pushState(innerNode);
            parser.state.startElement(namespaceIRI, localName, qName, atts);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (innerNode != null) {
            parser.statementWithResourceValue(nodeElement.getSubjectIRI(), propertyIRI,
                    innerNode.getSubjectIRI(), reificationID);
        } else {
            parser.statementWithLiteralValue(nodeElement.getSubjectIRI(), propertyIRI,
                    text.toString(), datatype, reificationID);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        if (innerNode != null) {
            if (!parser.isWhitespaceOnly(data, start, length)) {
                throw new RDFParserException(
                        "Cannot answer characters when object properties are excepted.",
                        parser.m_documentLocator);
            }
        } else {
            text.append(data, start, length);
        }
    }
}
