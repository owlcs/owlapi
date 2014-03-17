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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * An ontology document source which can read from a GZIP stream.
 * 
 * @author ignazio
 * @since 3.4.8
 */
public class GZipStreamDocumentSource extends OWLOntologyDocumentSourceBase {

    private static int counter = 0;
    private final IRI documentIRI;
    private byte[] buffer;

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified file.
     * 
     * @param is
     *        The stream that the ontology representation will be read from.
     */
    public GZipStreamDocumentSource(InputStream is) {
        this(is, getNextDocumentIRI(), null, null);
    }

    /** @return a fresh IRI */
    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create("gzipinputstream:ontology" + counter);
    }

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified stream.
     * 
     * @param stream
     *        The stream that the ontology representation will be read from.
     * @param documentIRI
     *        The document IRI
     * @param format
     *        ontology format
     * @param mime
     *        mime type
     */
    public GZipStreamDocumentSource(InputStream stream, IRI documentIRI,
            OWLOntologyFormat format, String mime) {
        super(format, mime);
        this.documentIRI = documentIRI;
        readIntoBuffer(stream);
    }

    private void readIntoBuffer(InputStream reader) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final int length = 100000;
            byte[] tempBuffer = new byte[length];
            int read = 0;
            do {
                read = reader.read(tempBuffer, 0, length);
                if (read > 0) {
                    bos.write(tempBuffer, 0, read);
                }
            } while (read > 0);
            buffer = bos.toByteArray();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public boolean isInputStreamAvailable() {
        return buffer != null;
    }

    @Override
    public InputStream getInputStream() {
        if (buffer == null) {
            throw new OWLRuntimeException(
                    "Stream not found - check that the file is available before calling this method.");
        }
        try {
            return new GZIPInputStream(new ByteArrayInputStream(buffer));
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
        return isInputStreamAvailable();
    }
}
