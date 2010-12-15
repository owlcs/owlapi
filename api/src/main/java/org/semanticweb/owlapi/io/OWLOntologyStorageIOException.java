package org.semanticweb.owlapi.io;

import java.io.IOException;

import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-Dec-2009
 * </p>
 * An <code>OWLOntologyStorageException</code> that was caused by an IOException.
 */
public class OWLOntologyStorageIOException extends OWLOntologyStorageException {

    private IOException ioException;

    public OWLOntologyStorageIOException(IOException ioException) {
        super(ioException);
        this.ioException = ioException;
    }

    /**
     * Gets the IOException that this exception wraps.
     * @return The IOException 
     */
    public IOException getIOException() {
        return ioException;
    }
}
