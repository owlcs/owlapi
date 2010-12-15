package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22-May-2007<br><br>
 */
public class ManchesterOWLSyntaxOntologyStorerException extends OWLOntologyStorageException {

    public ManchesterOWLSyntaxOntologyStorerException(String message) {
        super(message);
    }


    public ManchesterOWLSyntaxOntologyStorerException(String message, Throwable cause) {
        super(message, cause);
    }


    public ManchesterOWLSyntaxOntologyStorerException(Throwable cause) {
        super(cause);
    }
}
