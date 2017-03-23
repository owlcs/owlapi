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
package org.semanticweb.owlapi.io;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for ontology storers.
 *
 * @author Ignazio
 * @since 6.0.0
 */
public class OWLOntologyDocumentTargetBase implements OWLOntologyDocumentTarget {

    protected static final Logger LOGGER =
        LoggerFactory.getLogger(OWLOntologyDocumentTargetBase.class);
    @Nullable
    protected final IRI iri;
    protected Streamer<OutputStream> baseStream;
    protected Streamer<OutputStream> stream;
    protected Streamer<PrintWriter> writer;
    private final OWLStorerParameters storerParameters = new OWLStorerParameters();

    protected OWLOntologyDocumentTargetBase(Streamer<OutputStream> baseStream, @Nullable IRI iri) {
        this.baseStream = baseStream;
        stream = () -> stream(baseStream);
        writer = () -> writer(baseStream);
        this.iri = iri;
    }

    @Override
    public OWLStorerParameters getStorerParameters() {
        return storerParameters;
    }

    protected PrintWriter writer(Streamer<OutputStream> in) throws IOException {
        return new PrintWriter(
            new BufferedWriter(new OutputStreamWriter(in.get(), storerParameters.getEncoding())));
    }

    protected OutputStream stream(Streamer<OutputStream> in) throws IOException {
        return new BufferedOutputStream(in.get());
    }

    protected void storeOnWriter(OWLStorer storer, OWLOntology ontology, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        try (PrintWriter w = writer.get()) {
            storer.storeOntology(ontology, w, format, storerParameters);
            w.flush();
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    protected void storeOnStream(OWLStorer storer, OWLOntology ontology, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        try (OutputStream w = stream.get()) {
            storer.storeOntology(ontology, w, format, storerParameters);
            w.flush();
        } catch (IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    @Override
    public void store(OWLStorer storer, OWLOntology ontology, OWLDocumentFormat format)
        throws OWLOntologyStorageException {
        if (iri != null) {
            IRI documentIRI = verifyNotNull(iri);
            if (!documentIRI.isAbsolute()) {
                throw new OWLOntologyStorageException(
                    "Document IRI must be absolute: " + documentIRI);
            }
            if (documentIRI.isFileIRI()) {
                File file = new File(documentIRI.toURI());
                // Ensure that the necessary directories exist.
                file.getParentFile().mkdirs();
                baseStream = () -> new FileOutputStream(file);
            } else {
                baseStream = () -> {
                    URLConnection openConnection = documentIRI.toURI().toURL().openConnection();
                    openConnection.setDoOutput(true);
                    return openConnection.getOutputStream();
                };
            }
            stream = () -> stream(baseStream);
            writer = () -> writer(baseStream);
        }
        if (format.isTextual()) {
            storeOnWriter(storer, ontology, format);
        } else {
            storeOnStream(storer, ontology, format);
        }
    }

    protected interface Streamer<W> {

        W get() throws IOException;
    }
}
