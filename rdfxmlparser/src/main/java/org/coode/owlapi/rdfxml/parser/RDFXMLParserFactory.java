package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class RDFXMLParserFactory implements OWLParserFactory {

    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        RDFXMLParser parser = new RDFXMLParser();
        parser.setOWLOntologyManager(owlOntologyManager);
        return parser;
    }
}
