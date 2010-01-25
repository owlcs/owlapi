package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
        return new BufferedWriter(new OutputStreamWriter(getOutputStream()));
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
