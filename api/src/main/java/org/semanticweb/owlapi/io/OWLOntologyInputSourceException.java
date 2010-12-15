package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17-Nov-2007<br><br>
 */
public class OWLOntologyInputSourceException extends OWLRuntimeException {


    public OWLOntologyInputSourceException() {
    }


    public OWLOntologyInputSourceException(Throwable cause) {
        super(cause);
    }


    public OWLOntologyInputSourceException(String message) {
        super(message);
    }


    public OWLOntologyInputSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
