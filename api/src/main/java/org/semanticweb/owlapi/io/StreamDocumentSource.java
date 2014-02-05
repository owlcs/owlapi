/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicInteger;

import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactoryRegistry;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** An ontology document source which can read from a stream.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 15-Nov-2007 */
public class StreamDocumentSource implements OWLOntologyDocumentSource {
    private static final Logger log = LoggerFactory.getLogger(StreamDocumentSource.class);
    
    private static final AtomicInteger counter = new AtomicInteger(0);

    private final IRI documentIRI;
    private byte[] buffer;
    private OWLOntologyFormatFactory format;

    /** Constructs an input source which will read an ontology from a
     * representation from the specified stream.
     * 
     * @param is
     *            The stream that the ontology representation will be read from. */
    public StreamDocumentSource(InputStream is) {
        this(is, getNextDocumentIRI());
    }
    
    
    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param is The stream that the ontology representation will be
     * read from.
     * @param format An {@link OWLOntologyFormatFactory} that matches this file, or null if it is not known. 
     */
    public StreamDocumentSource(InputStream is, OWLOntologyFormatFactory format) {
        this(is, getNextDocumentIRI(), format);
    }
    
    
    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param is The stream that the ontology representation will be
     * read from.
     * @param mimeType The MIME type to use when finding a format
     */
    public StreamDocumentSource(InputStream is, String mimeType) {
        this(is, getNextDocumentIRI(), mimeType);
    }
    
    
    /** @return a fresh IRI */
    public static IRI getNextDocumentIRI() {
        return IRI.create("inputstream:ontology" + counter.incrementAndGet());
    }

    /** Constructs an input source which will read an ontology from a
     * representation from the specified stream.
     * 
     * @param stream
     *            The stream that the ontology representation will be read from.
     * @param documentIRI
     *            The document IRI */
    public StreamDocumentSource(InputStream stream, IRI documentIRI) {
        this.documentIRI = documentIRI;
        readIntoBuffer(stream);
    }

    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param stream The stream that the ontology representation will be
     * read from.
     * @param documentIRI The document IRI
     * @param mimeType The MIME type to use when finding a format
     */
    public StreamDocumentSource(InputStream stream, IRI documentIRI, String mimeType) {
        this(stream, documentIRI);
        OWLOntologyFormatFactory formatFactory = OWLOntologyFormatFactoryRegistry.getInstance().getByMIMEType(mimeType);
        
        if(formatFactory != null) {
            this.format = formatFactory;
        }
    }


    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param stream The stream that the ontology representation will be
     * read from.
     * @param documentIRI The document IRI
     * @param format An {@link OWLOntologyFormatFactory} that matches this file, or null if it is not known. 
     */
    public StreamDocumentSource(InputStream stream, IRI documentIRI, OWLOntologyFormatFactory format) {
        this(stream, documentIRI);
        this.format = format;
    }


    /** Reads all the bytes from the specified stream into a temporary buffer,
     * which is necessary because we may need to access the input stream more
     * than once. In other words, this method caches the input stream.
     * 
     * @param reader
     *            The reader to be "cached" */
    private void readIntoBuffer(InputStream reader) {
        long start = System.currentTimeMillis();
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(8096);
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
        finally {
            if(log.isDebugEnabled()) {
                log.debug("readIntoBuffer: timing={}", System.currentTimeMillis()-start);
            }
        }
    }

    @Override
    public boolean isInputStreamAvailable() {
        return true;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(buffer);
    }

    @Override
    public IRI getDocumentIRI() {
        return documentIRI;
    }

    @Override
    public Reader getReader() {
        throw new OWLRuntimeException(
                "Reader not available.  Check with StreamDocumentSource.isReaderAvailable() first!");
    }

    @Override
    public boolean isReaderAvailable() {
        return false;
    }


    @Override
    public OWLOntologyFormatFactory getFormatFactory() {
        return this.format;
    }


    @Override
    public boolean isFormatKnown() {
        return this.format != null;
    }
}
