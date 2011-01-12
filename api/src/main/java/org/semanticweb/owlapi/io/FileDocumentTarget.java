package org.semanticweb.owlapi.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16/12/2010
 * <br>
 * An <code>OWLOntologyDocumentTarget</code> that supports writing out to a <code>File</code>.
 * @since 3.2
 */
public class FileDocumentTarget implements OWLOntologyDocumentTarget {

    private File file;

    /**
     * Constructs the document target, with the target being the specified file.
     * @param file The file that is the target.
     */
    public FileDocumentTarget(File file) {
        this.file = file;
    }

    public boolean isWriterAvailable() {
        return true;
    }

    public Writer getWriter() throws IOException {
        return new BufferedWriter(new FileWriter(file));
    }

    public boolean isOutputStreamAvailable() {
        return true;
    }

    public OutputStream getOutputStream() throws IOException {
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    public boolean isDocumentIRIAvailable() {
        return true;
    }

    public IRI getDocumentIRI() {
        return IRI.create(file);
    }
}
