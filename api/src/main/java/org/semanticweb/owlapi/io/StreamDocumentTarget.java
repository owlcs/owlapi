package org.semanticweb.owlapi.io;

import java.io.OutputStream;
import java.io.Writer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Jan-2008<br><br>
 */
public class StreamDocumentTarget implements OWLOntologyDocumentTarget {

    private OutputStream os;


    public StreamDocumentTarget(OutputStream os) {
        this.os = os;
    }


    public boolean isWriterAvailable() {
        return false;
    }


    public Writer getWriter() {
        throw new OWLRuntimeException("Writer not available.  getWriter() should not be called if isWriterAvailable() returns false.");
    }


    public boolean isOutputStreamAvailable() {
        return true;
    }


    public OutputStream getOutputStream() {
        return os;
    }


    public boolean isDocumentIRIAvailable() {
        return false;
    }


    public IRI getDocumentIRI() {
        throw new OWLRuntimeException("IRI not available.  getDocumentIRI() should not be called if isDocumentIRIAvailable() returns false.");
    }
}
