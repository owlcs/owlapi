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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for common utilities among stream, reader and file input sources.
 * 
 * @since 4.0.0 TODO both stream and reader sources copy the input in memory in
 *        case reloading is needed. This can be bad for memory. Remote loading
 *        will download the ontologies multiple times too, until parsing fails.
 *        Both issues could be addressed with a local file copy.
 */
public abstract class StreamDocumentSourceBase extends OWLOntologyDocumentSourceBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamDocumentSourceBase.class);
    protected byte[] byteBuffer;
    private Charset encoding = StandardCharsets.UTF_8;
    private boolean streamAvailable = false;

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
    public StreamDocumentSourceBase(InputStream stream, IRI documentIRI, @Nullable OWLDocumentFormat format,
            @Nullable String mime) {
        super(documentIRI, format, mime);
        readIntoBuffer(checkNotNull(stream, "stream cannot be null"));
        streamAvailable = true;
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
    public StreamDocumentSourceBase(Reader stream, IRI documentIRI, @Nullable OWLDocumentFormat format,
            @Nullable String mime) {
        super(documentIRI, format, mime);
        checkNotNull(stream, "stream cannot be null");
        // if the input stream carries encoding information, use it; else leave
        // the default as UTF-8
        if (stream instanceof InputStreamReader) {
            encoding = Charset.forName(((InputStreamReader) stream).getEncoding());
        }
        readIntoBuffer(stream);
        streamAvailable = false;
    }

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified stream.
     * 
     * @param stream
     *        The stream that the ontology representation will be read from.
     * @param prefix
     *        The document IRI prefix
     * @param format
     *        ontology format
     * @param mime
     *        mime type
     */
    protected StreamDocumentSourceBase(InputStream stream, String prefix, @Nullable OWLDocumentFormat format,
            @Nullable String mime) {
        super(prefix, format, mime);
        readIntoBuffer(checkNotNull(stream, "stream cannot be null"));
        streamAvailable = true;
    }

    /**
     * Constructs an input source which will read an ontology from a
     * representation from the specified stream.
     * 
     * @param stream
     *        The stream that the ontology representation will be read from.
     * @param prefix
     *        The document IRI prefix
     * @param format
     *        ontology format
     * @param mime
     *        mime type
     */
    protected StreamDocumentSourceBase(Reader stream, String prefix, @Nullable OWLDocumentFormat format,
            @Nullable String mime) {
        super(prefix, format, mime);
        checkNotNull(stream, "stream cannot be null");
        // if the input stream carries encoding information, use it; else leave
        // the default as UTF-8
        if (stream instanceof InputStreamReader) {
            encoding = Charset.forName(((InputStreamReader) stream).getEncoding());
        }
        readIntoBuffer(stream);
        streamAvailable = false;
    }

    /**
     * Reads all the bytes from the specified stream into a temporary buffer,
     * which is necessary because we may need to access the input stream more
     * than once. In other words, this method caches the input stream.
     * 
     * @param reader
     *        The stream to be "cached"
     */
    private void readIntoBuffer(InputStream reader) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream out = new GZIPOutputStream(bos);
            int length = 100000;
            byte[] tempBuffer = new byte[length];
            int read = 0;
            do {
                read = reader.read(tempBuffer, 0, length);
                if (read > 0) {
                    out.write(tempBuffer, 0, read);
                }
            } while (read > 0);
            out.finish();
            out.flush();
            byteBuffer = bos.toByteArray();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void readIntoBuffer(Reader reader) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream out = new GZIPOutputStream(bos);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            int length = 100000;
            char[] tempBuffer = new char[length];
            int read = 0;
            do {
                read = reader.read(tempBuffer, 0, length);
                if (read > 0) {
                    writer.write(tempBuffer, 0, read);
                }
            } while (read > 0);
            writer.flush();
            out.finish();
            out.flush();
            byteBuffer = bos.toByteArray();
        } catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public Optional<InputStream> getInputStream() {
        if (!streamAvailable) {
            return emptyOptional();
        }
        try {
            return optional(DocumentSources.wrap(new GZIPInputStream(new ByteArrayInputStream(byteBuffer))));
        } catch (IOException e) {
            LOGGER.error("Buffer cannot be opened", e);
            failedOnStreams.set(true);
            return emptyOptional();
        }
    }

    @Override
    public Optional<Reader> getReader() {
        if (streamAvailable) {
            return emptyOptional();
        }
        try {
            return optional(new InputStreamReader(
                    DocumentSources.wrap(new GZIPInputStream(new ByteArrayInputStream(byteBuffer))), encoding));
        } catch (IOException e) {
            LOGGER.error("Buffer cannot be opened", e);
            failedOnStreams.set(true);
            return emptyOptional();
        }
    }
}
