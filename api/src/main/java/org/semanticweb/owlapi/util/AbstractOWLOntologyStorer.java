/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for ontology storers. Note that all current implementations are
 * stateless.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public abstract class AbstractOWLOntologyStorer implements OWLOntologyStorer {

    private static final long serialVersionUID = 40000L;
    private static final String UTF_8 = "UTF-8";
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(OWLOntologyStorer.class);

    @Override
    public final void storeOntology(OWLOntology ontology, IRI documentIRI,
            OWLOntologyFormat ontologyFormat)
            throws OWLOntologyStorageException {
        if (!documentIRI.isAbsolute()) {
            throw new OWLOntologyStorageException(
                    "Document IRI must be absolute: " + documentIRI);
        }
        try {
            OutputStream os = null;
            try {
                // prepare actual output
                os = prepareActualOutput(documentIRI);
                store(ontology, ontologyFormat, os);
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    private OutputStream prepareActualOutput(IRI documentIRI)
            throws FileNotFoundException, MalformedURLException, IOException {
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
        return os;
    }

    private void store(OWLOntology ontology, OWLOntologyFormat ontologyFormat,
            OutputStream tempOutputStream) throws OWLOntologyStorageException,
            IOException {
        Writer tempWriter = new BufferedWriter(new OutputStreamWriter(
                tempOutputStream, UTF_8));
        storeOntology(ontology, tempWriter, ontologyFormat);
        tempWriter.flush();
        tempWriter.close();
    }

    @Override
    public final void storeOntology(OWLOntology ontology,
            OWLOntologyDocumentTarget target, OWLOntologyFormat format)
            throws OWLOntologyStorageException {
        if (target.isWriterAvailable()) {
            try {
                Writer writer = target.getWriter();
                storeOntology(ontology, writer, format);
                writer.flush();
            } catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isOutputStreamAvailable()) {
            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        target.getOutputStream(), UTF_8));
                storeOntology(ontology, writer, format);
                writer.flush();
            } catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        } else if (target.isDocumentIRIAvailable()) {
            storeOntology(ontology, target.getDocumentIRI(), format);
        } else {
            throw new OWLOntologyStorageException(
                    "Neither a Writer, OutputStream or Document IRI could be obtained to store the ontology");
        }
    }

    // XXX check if Writer is the best interface here
    protected abstract void storeOntology(OWLOntology ontology, Writer writer,
            OWLOntologyFormat format) throws OWLOntologyStorageException;
}
