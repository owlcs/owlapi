package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses parseTypeCollectionPropertyElt production. */
public class ParseTypeCollectionPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected String m_lastCellIRI;
    private RDFParser parser;

    /** @param nodeElement
     *            nodeElement
     * @param parser
     *            parser */
    public ParseTypeCollectionPropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (m_propertyIRI == null) {
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
        } else {
            NodeElement collectionNode = new NodeElement(parser);
            parser.pushState(collectionNode);
            parser.state.startElement(namespaceIRI, localName, qName, atts);
            String newListCellIRI = listCell(collectionNode.getSubjectIRI());
            if (m_lastCellIRI == null) {
                parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                        m_propertyIRI, newListCellIRI, m_reificationID);
            } else {
                parser.statementWithResourceValue(m_lastCellIRI, RDFConstants.RDF_REST,
                        newListCellIRI, null);
            }
            m_lastCellIRI = newListCellIRI;
        }
    }

    protected String listCell(String valueIRI) throws SAXException {
        String listCellIRI = NodeID.nextAnonymousIRI();
        parser.statementWithResourceValue(listCellIRI, RDFConstants.RDF_FIRST, valueIRI,
                null);
        parser.statementWithResourceValue(listCellIRI, RDFConstants.RDF_TYPE,
                RDFConstants.RDF_LIST, null);
        return listCellIRI;
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_lastCellIRI == null) {
            parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, RDFConstants.RDF_NIL, m_reificationID);
        } else {
            parser.statementWithResourceValue(m_lastCellIRI, RDFConstants.RDF_REST,
                    RDFConstants.RDF_NIL, null);
        }
        parser.popState();
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
