package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Feb-2008<br><br>
 */
public class TurtleOntologyParserFactory implements OWLParserFactory {


    public OWLParser createParser(OWLOntologyManager owlOntologyManager) {
        TurtleOntologyParser parser = new TurtleOntologyParser();
        parser.setOWLOntologyManager(owlOntologyManager);
        return parser;
    }
}
