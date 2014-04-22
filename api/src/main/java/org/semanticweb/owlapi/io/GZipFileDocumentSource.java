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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * An ontology document source which can read from a GZIP File.
 * 
 * @author ignazio
 * @since 3.4.8
 */
public class GZipFileDocumentSource extends OWLOntologyDocumentSourceBase {

    private static int counter = 0;
    @Nonnull
    private final IRI documentIRI;
    @Nonnull
    private final File file;

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified file.
     * 
     * @param is
     *        The file that the ontology representation will be read from.
     */
    public GZipFileDocumentSource(@Nonnull File is) {
        this(is, getNextDocumentIRI(), null, null);
    }

    /** @return a fresh IRI */
    @Nonnull
    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create("file:ontology" + counter);
    }

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified file.
     * 
     * @param stream
     *        The file that the ontology representation will be read from.
     * @param documentIRI
     *        The document IRI
     * @param format
     *        ontology format
     * @param mime
     *        mime type
     */
    public GZipFileDocumentSource(@Nonnull File stream,
            @Nonnull IRI documentIRI, @Nullable OWLOntologyFormat format,
            @Nullable String mime) {
        super(format, mime);
        this.documentIRI = documentIRI;
        file = stream;
    }

    @Override
    public boolean isInputStreamAvailable() {
        return file.exists();
    }

    @Nonnull
    @Override
    public InputStream getInputStream() {
        try {
            return new GZIPInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new OWLRuntimeException(
                    "File not found - check that the file is available before calling this method.");
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public IRI getDocumentIRI() {
        return documentIRI;
    }

    @Override
    public Reader getReader() {
        try {
            return new InputStreamReader(getInputStream(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // will never happen though - UTF-8 is always supported
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public boolean isReaderAvailable() {
        return file.exists();
    }
}
