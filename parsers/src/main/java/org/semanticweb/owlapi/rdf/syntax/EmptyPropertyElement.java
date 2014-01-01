package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser.ReificationManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses emptyPropertyElt production. */
public class EmptyPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    private RDFParser parser;

    /** @param nodeElement
     *            node element
     * @param parser
     *            parser */
    public EmptyPropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (m_propertyIRI == null) {
            // this is the invocation on the outer element
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
            String reificationID = m_nodeElement.getReificationID(atts);
            String objectIRI = parser.getNodeIDResourceResourceIRI(atts);
            if (objectIRI == null) {
                objectIRI = NodeID.nextAnonymousIRI();
            }
            parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, objectIRI, reificationID);
            ReificationManager reificationManager = parser.getReificationManager(atts);
            parser.propertyAttributes(objectIRI, atts, reificationManager);
        } else {
            throw new RDFParserException("incorrect element start encountered.",
                    parser.m_documentLocator);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        throw new RDFParserException("Characters were not excepted.",
                parser.m_documentLocator);
    }
}
