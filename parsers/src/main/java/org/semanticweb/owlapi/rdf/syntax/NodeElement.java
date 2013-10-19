package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser.ReificationManager;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses the nodeElement production. */
public class NodeElement implements State {
    protected String m_subjectIRI;
    protected ReificationManager m_reificationManager;
    protected int m_nextLi = 1;
    private RDFParser parser;

    public NodeElement(RDFParser parser) {
        this.parser = parser;
    }

    public void startDummyElement(Attributes atts) throws SAXException {
        m_subjectIRI = NodeID.nextAnonymousIRI();
        m_reificationManager = parser.getReificationManager(atts);
    }

    public String getSubjectIRI() {
        return m_subjectIRI;
    }

    public String getReificationID(Attributes atts) throws SAXException {
        String rdfID = atts.getValue(RDFConstants.RDFNS, RDFConstants.ATTR_ID);
        if (rdfID != null) {
            rdfID = parser.getIRIFromID(rdfID);
        }
        return m_reificationManager.getReificationID(rdfID);
    }

    public String getNextLi() {
        return RDFConstants.RDFNS + "_" + m_nextLi++;
    }

    public String getPropertyIRI(String uri) {
        if (RDFConstants.RDF_LI.equals(uri)) {
            return getNextLi();
        } else {
            return uri;
        }
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        m_subjectIRI = parser.getIDNodeIDAboutResourceIRI(atts);
        boolean isRDFNS = RDFConstants.RDFNS.equals(namespaceIRI);
        m_reificationManager = parser.getReificationManager(atts);
        if (!isRDFNS || !RDFConstants.ELT_DESCRIPTION.equals(localName)) {
            parser.statementWithResourceValue(m_subjectIRI, RDFConstants.RDF_TYPE,
                    namespaceIRI + localName, m_reificationManager.getReificationID(null));
        }
        parser.checkUnsupportedAttributes(atts);
        parser.propertyAttributes(m_subjectIRI, atts, m_reificationManager);
        parser.pushState(new PropertyElementList(this, parser));
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
                    "Cannot answer characters when node is excepted.",
                    parser.m_documentLocator);
        }
    }
}
