package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser.ReificationManager;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses the nodeElement production. */
public class NodeElement implements State {
    /** The m_subject iri. */
    protected String m_subjectIRI;
    /** The m_reification manager. */
    protected ReificationManager m_reificationManager;
    /** The m_next li. */
    protected int m_nextLi = 1;
    /** The parser. */
    private RDFParser parser;

    /** Instantiates a new node element.
     * 
     * @param parser
     *            the parser */
    public NodeElement(RDFParser parser) {
        this.parser = parser;
    }

    /** Start dummy element.
     * 
     * @param atts
     *            the atts
     * @throws SAXException
     *             the sAX exception */
    public void startDummyElement(Attributes atts) throws SAXException {
        m_subjectIRI = NodeID.nextAnonymousIRI();
        m_reificationManager = parser.getReificationManager(atts);
    }

    /** Gets the subject iri.
     * 
     * @return subject iri */
    public String getSubjectIRI() {
        return m_subjectIRI;
    }

    /** Gets the reification id.
     * 
     * @param atts
     *            the atts
     * @return reification id
     * @throws SAXException
     *             the sAX exception */
    public String getReificationID(Attributes atts) throws SAXException {
        String rdfID = atts.getValue(RDFConstants.RDFNS, RDFConstants.ATTR_ID);
        if (rdfID != null) {
            rdfID = parser.getIRIFromID(rdfID);
        }
        return m_reificationManager.getReificationID(rdfID);
    }

    /** Gets the next li.
     * 
     * @return next list item */
    public String getNextLi() {
        return RDFConstants.RDFNS + "_" + m_nextLi++;
    }

    /** Gets the property iri.
     * 
     * @param uri
     *            the uri
     * @return property iri */
    public String getPropertyIRI(String uri) {
        if (RDFConstants.RDF_LI.equals(uri)) {
            return getNextLi();
        } else {
            return uri;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.semanticweb.owlapi.rdf.syntax.State#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
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

    /*
     * (non-Javadoc)
     * @see org.semanticweb.owlapi.rdf.syntax.State#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    /*
     * (non-Javadoc)
     * @see org.semanticweb.owlapi.rdf.syntax.State#characters(char[], int, int)
     */
    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        if (!parser.isWhitespaceOnly(data, start, length)) {
            throw new RDFParserException(
                    "Cannot answer characters when node is excepted.",
                    parser.m_documentLocator);
        }
    }
}
