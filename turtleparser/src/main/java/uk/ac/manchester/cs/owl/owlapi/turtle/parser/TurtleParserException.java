package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import org.semanticweb.owlapi.io.OWLParserException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Feb-2008<br><br>
 */
public class TurtleParserException extends OWLParserException {


    public TurtleParserException(String message) {
        super(message);
    }


    public TurtleParserException(String message, Throwable cause) {
        super(message, cause);
    }


    public TurtleParserException(Throwable cause) {
        super(cause);
    }
}
