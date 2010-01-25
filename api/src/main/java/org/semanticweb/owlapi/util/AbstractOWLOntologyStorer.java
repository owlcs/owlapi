package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
/*
 * Copyright (C) 2007, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Dec-2007<br><br>
 */
public abstract class AbstractOWLOntologyStorer implements OWLOntologyStorer {


    public void storeOntology(OWLOntologyManager manager, OWLOntology ontology, IRI documentIRI, OWLOntologyFormat ontologyFormat) throws
            OWLOntologyStorageException {
        try {
            if (!documentIRI.isAbsolute()) {
                throw new OWLOntologyStorageException("Document IRI must be absolute: " + documentIRI);
            }


            File tempFile = File.createTempFile("owlapi", ".owl");
            try {
                BufferedWriter tempWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
                storeOntology(manager, ontology, tempWriter, ontologyFormat);
                tempWriter.flush();
                tempWriter.close();

                // Now copy across
                OutputStream os;
                if (documentIRI.getScheme().equals("file")) {
                    File file = new File(documentIRI.toURI());
                    // Ensure that the necessary directories exist.
                    file.getParentFile().mkdirs();
                    os = new FileOutputStream(file);
                } else {
                    URL url = documentIRI.toURI().toURL();
                    URLConnection conn = url.openConnection();
                    os = conn.getOutputStream();
                }



                BufferedReader br = new BufferedReader(new FileReader(tempFile));
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String line;
                while((line = br.readLine()) != null) {
                    w.write(line);
                    w.write("\n");
                }
                br.close();
                w.close();
            }
            finally {
                tempFile.delete();
            }

        }
        catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }


    public void storeOntology(OWLOntologyManager manager, OWLOntology ontology, OWLOntologyDocumentTarget target,
                              OWLOntologyFormat format) throws OWLOntologyStorageException {
        if (target.isWriterAvailable()) {
            try {
                Writer writer = target.getWriter();
                storeOntology(manager, ontology, writer, format);
                writer.close();
            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isOutputStreamAvailable()) {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream()));
                storeOntology(manager, ontology, writer, format);
                writer.close();
            }
            catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isDocumentIRIAvailable()) {
            storeOntology(manager, ontology, target.getDocumentIRI(), format);
        } else {
            throw new OWLOntologyStorageException("Neither a Writer, OutputStream or Document IRI could be obtained to store the ontology");
        }
    }

    protected abstract void storeOntology(OWLOntologyManager manager, OWLOntology ontology, Writer writer, OWLOntologyFormat format) throws OWLOntologyStorageException;
}
