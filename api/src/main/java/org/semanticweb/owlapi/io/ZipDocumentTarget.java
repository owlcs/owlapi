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
        return false;
    }


    public Writer getWriter() {
        throw new OWLRuntimeException("Writer not available.  getWriter() should not be called if isWriterAvailable() returns false.");
    }



    public boolean isOutputStreamAvailable() {
        return true;
    }


    public OutputStream getOutputStream() throws IOException {
        File parentFile = file.getAbsoluteFile().getParentFile();
        
		if(parentFile.exists() || parentFile.mkdirs()) {
            ZipOutputStream os = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            os.putNextEntry(new ZipEntry("ontology.txt"));
            return os;
        }
        else {
            throw new IOException("Could not create directories: " + parentFile);
        }
    }


    public boolean isDocumentIRIAvailable() {
        return false;
    }


    public IRI getDocumentIRI() {
        throw new OWLRuntimeException("IRI not available.  getDocumentIRI() should not be called if isDocumentIRIAvailable() returns false.");
    }
}
