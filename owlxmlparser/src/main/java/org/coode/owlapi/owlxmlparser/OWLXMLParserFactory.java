package org.coode.owlapi.owlxmlparser;

import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2006<br><br>
 */
public class OWLXMLParserFactory implements OWLParserFactory {

    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        OWLXMLParser parser = new OWLXMLParser();
        parser.setOWLOntologyManager(owlOntologyManager);
        return parser;
    }
}
