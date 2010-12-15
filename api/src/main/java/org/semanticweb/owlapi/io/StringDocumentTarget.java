package org.semanticweb.owlapi.io;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Mar-2008<br><br>
 */
public class StringDocumentTarget implements OWLOntologyDocumentTarget {

    private StringWriter writer;


    public StringDocumentTarget() {
        this.writer = new StringWriter();
    }


    @Override
	public String toString() {
        return writer.getBuffer().toString();
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
