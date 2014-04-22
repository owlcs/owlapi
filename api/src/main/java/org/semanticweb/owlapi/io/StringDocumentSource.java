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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * An ontology input source that wraps a string.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class StringDocumentSource extends OWLOntologyDocumentSourceBase {

    @Nonnull
    private static AtomicLong counter = new AtomicLong();
    @Nonnull
    private final IRI documentIRI;
    private final String string;

    /**
     * @param string
     *        the source string
     */
    public StringDocumentSource(@Nonnull String string) {
        this(string, getNextDocumentIRI(), null, null);
    }

    /**
     * @param target
     *        a document target
     */
    public StringDocumentSource(@Nonnull StringDocumentTarget target) {
        this(target.toString());
    }

    /** @return a fresh IRI */
    @Nonnull
    public static IRI getNextDocumentIRI() {
        return IRI.create("string:ontology" + counter.incrementAndGet());
    }

    /**
     * Specifies a string as an ontology document.
     * 
     * @param string
     *        The string
     * @param documentIRI
     *        The document IRI
     * @param f
     *        ontology format
     * @param mime
     *        mime type
     */
    public StringDocumentSource(@Nonnull String string,
            @Nonnull IRI documentIRI, OWLOntologyFormat f, String mime) {
        super(f, mime);
        this.string = checkNotNull(string, "string cannot be null");
        this.documentIRI = checkNotNull(documentIRI,
                "documentIRI cannot be null");
    }

    @Override
    public boolean isReaderAvailable() {
        return true;
    }

    @Override
    public Reader getReader() {
        return new StringReader(string);
    }

    @Override
    public boolean isInputStreamAvailable() {
        return false;
    }

    @Override
    public InputStream getInputStream() {
        throw new OWLRuntimeException(
                "InputStream not available.  Check with StringDocumentSource.isInputStreamAvailable() first!");
    }

    @Override
    public IRI getDocumentIRI() {
        return documentIRI;
    }
}
