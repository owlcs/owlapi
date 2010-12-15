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
public class WriterDocumentTarget implements OWLOntologyDocumentTarget {

    private Writer writer;


    public WriterDocumentTarget(Writer writer) {
        this.writer = writer;
    }


    public boolean isWriterAvailable() {
        return true;
    }


    public Writer getWriter() {
        return writer;
    }


    public boolean isOutputStreamAvailable() {
        return false;
    }


    public OutputStream getOutputStream() {
        throw new OWLRuntimeException("OutputStream not available.  getOutputStream() should not be called if isOutputStreamAvailable() returns false.");
    }


    public boolean isDocumentIRIAvailable() {
        return false;
    }


    public IRI getDocumentIRI() {
        throw new OWLRuntimeException("IRI not available.  getDocumentIRI() should not be called if isDocumentIRIAvailable() returns false.");
    }
}
