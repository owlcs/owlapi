package org.semanticweb.owlapi.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Dec-2009
 * </p>
 * An output target that will output an ontology to <code>System.out</code>
 */
public class SystemOutDocumentTarget implements OWLOntologyDocumentTarget {

    public IRI getDocumentIRI() {
        throw new OWLRuntimeException("IRI not available.  getDocumentIRI() should not be called if isDocumentIRIAvailable() returns false.");
    }

    public boolean isDocumentIRIAvailable() {
        return false;
    }

    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                System.out.write(b);
            }

            @Override
            public void close() throws IOException {
                // Do nothing
            }
        };
    }

    public boolean isOutputStreamAvailable() {
        return true;
    }

    public Writer getWriter() throws IOException {
        throw new OWLRuntimeException("Writer not available.  getWriter() should not be called if isWriterAvailable() returns false.");
    }

    public boolean isWriterAvailable() {
        return false;
    }
}
