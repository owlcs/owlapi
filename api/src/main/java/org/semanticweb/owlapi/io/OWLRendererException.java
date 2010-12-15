package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLRendererException extends OWLOntologyStorageException {

    public OWLRendererException(String message) {
        super(message);
    }


    public OWLRendererException(String message, Throwable cause) {
        super(message, cause);
    }


    public OWLRendererException(Throwable cause) {
        super(cause);
    }
}
