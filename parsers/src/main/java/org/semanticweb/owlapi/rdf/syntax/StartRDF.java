package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** State expecting start of RDF text. */
public class StartRDF implements State {
    private final RDFParser parser;

    /** @param parser
     *            parser */
    public StartRDF(RDFParser parser) {
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        if (!RDFConstants.RDFNS.equals(namespaceIRI)
                || !RDFConstants.ELT_RDF.equals(localName)) {
            throw new RDFParserException("Expecting rdf:RDF element.",
                    parser.m_documentLocator);
        }
        // the logical IRI is the current IRI that we have as the base IRI
        // at this point
        parser.m_consumer.logicalURI(parser.m_baseIRI.toString());
        parser.pushState(new NodeElementList(parser));
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
                    "Expecting rdf:rdf element instead of character content.",
                    parser.m_documentLocator);
        }
    }
}
