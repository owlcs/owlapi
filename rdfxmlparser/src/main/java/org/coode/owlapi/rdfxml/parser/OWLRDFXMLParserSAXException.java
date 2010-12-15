package org.coode.owlapi.rdfxml.parser;

import org.xml.sax.SAXException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLRDFXMLParserSAXException extends OWLRDFXMLParserException {

    public OWLRDFXMLParserSAXException(SAXException cause) {
        super(cause);
    }
}
