package org.coode.owlapi.owlxml.renderer;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 */
public class OWLXMLOntologyStorageException extends OWLOntologyStorageException {

    public OWLXMLOntologyStorageException(String message) {
        super(message);
    }


    public OWLXMLOntologyStorageException(String message, Throwable cause) {
        super(message, cause);
    }


    public OWLXMLOntologyStorageException(Throwable cause) {
        super(cause);
    }
}
