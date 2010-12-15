package org.coode.owlapi.obo.renderer;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 19, 2008<br><br>
 */
public class OBOStorageException extends OWLOntologyStorageException{

    public OBOStorageException(OWLObject source, OWLObject problem, String message){
        super(source + ": " + message + " (" + problem + ")");
    }
}
