package org.semanticweb.owlapi.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Apr-2009
 */
public class ZipDocumentTarget implements OWLOntologyDocumentTarget {

    private File file;


    public ZipDocumentTarget(File file) {
        this.file = file;
    }


    public boolean isWriterAvailable() {
        return true;
    }


    public Writer getWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(getOutputStream(), "UTF-8"));
    }


    public boolean isOutputStreamAvailable() {
        return true;
    }


    public OutputStream getOutputStream() throws IOException {
        if(file.getParentFile().mkdirs()) {
            ZipOutputStream os = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            os.putNextEntry(new ZipEntry("ontology.txt"));
            return os;
        }
        else {
            throw new IOException("Could not create directories: " + file.getParentFile());
        }
    }


    public boolean isDocumentIRIAvailable() {
        return false;
    }


    public IRI getDocumentIRI() {
        throw new OWLRuntimeException("IRI not available.  getDocumentIRI() should not be called if isDocumentIRIAvailable() returns false.");
    }
}
