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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import org.semanticweb.owlapi.formats.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.OWLOntologyFormatFactoryRegistry;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/** An ontology document source which reads an ontology from a reader.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 17-Nov-2007 */
public class ReaderDocumentSource implements OWLOntologyDocumentSource {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private final IRI documentIRI;
    private String buffer;
    private OWLOntologyFormatFactory format;

    /** Constructs and ontology input source which will read an ontology from a
     * reader.
     * 
     * @param reader
     *            The reader that will be used to read an ontology. */
    public ReaderDocumentSource(Reader reader) {
        this(reader, getNextDocumentIRI());
    }

    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param format An {@link OWLOntologyFormatFactory} that matches this file, or null if it is not known. 
     */
    public ReaderDocumentSource(Reader reader, OWLOntologyFormatFactory format) {
        this(reader, getNextDocumentIRI(), format);
    }

    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param mimeType The MIME type to use when finding a format
     */
    public ReaderDocumentSource(Reader reader, String mimeType) {
        this(reader, getNextDocumentIRI(), mimeType);
    }

    /**
     * @return a fresh IRI
     */
    public static IRI getNextDocumentIRI() {
        return IRI.create("reader:ontology" + counter.incrementAndGet());
    }

    /** Constructs and ontology input source which will read an ontology from a
     * reader.
     * 
     * @param reader
     *            The reader that will be used to read an ontology.
     * @param documentIRI
     *            The ontology document IRI which will be used as the base of
     *            the document if needed. */
    public ReaderDocumentSource(Reader reader, IRI documentIRI) {
        this.documentIRI = documentIRI;
        fillBuffer(reader);
    }


    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param documentIRI The ontology document IRI which will be used as the base
     * of the document if needed.
     * @param format An {@link OWLOntologyFormatFactory} that matches this file, or null if it is not known. 
     */
    public ReaderDocumentSource(Reader reader, IRI documentIRI, OWLOntologyFormatFactory format) {
        this(reader, documentIRI);
        this.format = format;
    }

    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param documentIRI The ontology document IRI which will be used as the base
     * of the document if needed.
     * @param mimeType The MIME type to use when finding a format
     */
    public ReaderDocumentSource(Reader reader, IRI documentIRI, String mimeType) {
        this(reader, documentIRI);
        OWLOntologyFormatFactory formatFactory = OWLOntologyFormatFactoryRegistry.getInstance().getByMIMEType(mimeType);
        
        if(formatFactory != null) {
            this.format = formatFactory;
        }
    }

    private void fillBuffer(Reader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            final int length = 100000;
            char[] tempBuffer = new char[length];
            int read = 0;
            do {
                read = reader.read(tempBuffer, 0, length);
                if (read > 0) {
                    builder.append(tempBuffer, 0, read);
                }
            } while (read > 0);
            buffer = builder.toString();
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
        return new StringReader(buffer);
    }

    @Override
    public boolean isReaderAvailable() {
        return true;
    }

    @Override
    public boolean isInputStreamAvailable() {
        return false;
    }

    @Override
    public InputStream getInputStream() {
        throw new OWLRuntimeException(
                "InputStream not available.  Check with ReaderDocumentSource.isReaderAvailable() first!");
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
