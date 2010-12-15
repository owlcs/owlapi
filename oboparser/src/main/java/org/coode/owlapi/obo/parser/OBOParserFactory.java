package org.coode.owlapi.obo.parser;

import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Jan-2007<br><br>
 */
public class OBOParserFactory implements OWLParserFactory {

    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        OWLOBOParser parser =  new OWLOBOParser();
        parser.setOWLOntologyManager(owlOntologyManager);
        return parser;
    }
}
