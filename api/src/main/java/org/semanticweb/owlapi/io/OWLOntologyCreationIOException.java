package org.semanticweb.owlapi.io;

import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Apr-2008<br><br>
 *
 * Indicates an {@link java.io.IOException} happened during ontology creation.  The cause of this exception will
 * be an {@link java.io.IOException}.
 */
public class OWLOntologyCreationIOException extends OWLOntologyCreationException {

    public OWLOntologyCreationIOException(IOException ioException) {
        super(ioException.getClass().getSimpleName() + ": " + ioException.getMessage(), ioException);
    }


    @Override
	public IOException getCause() {
        return (IOException) super.getCause();
    }

    /**
     * Delegates to the getMessage() method of the contained <code>IOException</code>.
     * @return The message of the IOException 
     */
    @Override
	public String getMessage() {
        return getCause().getMessage();
    }
}
