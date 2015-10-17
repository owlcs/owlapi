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

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;
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
public abstract class AbstractOWLStorer implements OWLStorer {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractOWLStorer.class);

    @Override
    public void storeOntology(OWLOntology ontology, IRI documentIRI, OWLDocumentFormat ontologyFormat)
        throws OWLOntologyStorageException {
        if (!documentIRI.isAbsolute()) {
            throw new OWLOntologyStorageException("Document IRI must be absolute: " + documentIRI);
        }
        try (
            // prepare actual output
            OutputStream os = prepareActualOutput(documentIRI)) {
            store(ontology, ontologyFormat, os);
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    private static OutputStream prepareActualOutput(IRI documentIRI) throws IOException {
        // files opened with FileOutputStream
        if ("file".equals(documentIRI.getScheme())) {
            File file = new File(documentIRI.toURI());
            // Ensure that the necessary directories exist.
            file.getParentFile().mkdirs();
            return new FileOutputStream(file);
        }
        // URLs
        URL url = documentIRI.toURI().toURL();
        URLConnection conn = url.openConnection();
        return conn.getOutputStream();
    }

    private void store(OWLOntology ontology, OWLDocumentFormat ontologyFormat, OutputStream tempOutputStream)
        throws OWLOntologyStorageException, IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(tempOutputStream, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter tempWriter = new PrintWriter(bw);) {
            storeOntology(ontology, tempWriter, ontologyFormat);
            tempWriter.flush();
        }
    }

    @Override
    public void storeOntology(OWLOntology ontology, OWLOntologyDocumentTarget target, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        Optional<Writer> writer = target.getWriter();
        if (format.isTextual() && writer.isPresent()) {
            try (Writer w = writer.get(); PrintWriter pw = new PrintWriter(w);) {
                storeOntology(ontology, pw, format);
                pw.flush();
                return;
            } catch (IOException e) {
                throw new OWLOntologyStorageException(e);
            }
        }
        Optional<OutputStream> outputStream = target.getOutputStream();
        if (outputStream.isPresent()) {
            storeOntology(ontology, outputStream.get(), format);
            return;
        }
        Optional<IRI> documentIRI = target.getDocumentIRI();
        if (documentIRI.isPresent()) {
            storeOntology(ontology, documentIRI.get(), format);
            return;
        }
        throw new OWLOntologyStorageException(
            "Neither a Writer, OutputStream or Document IRI could be obtained to store the ontology in this format: "
                + format.getKey());
    }

    /*
     * Override this to support textual serialisation.
     */
    protected abstract void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format)
        throws OWLOntologyStorageException;

    /*
     * Override this to support direct binary serialisation without the UTF-8
     * encoding being applied.
     */
    protected void storeOntology(OWLOntology ontology, OutputStream outputStream, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        if (!format.isTextual()) {
            throw new OWLOntologyStorageException("This method must be overridden to support this binary format: "
                + format.getKey());
        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream,
                StandardCharsets.UTF_8)));
            storeOntology(ontology, writer, format);
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
