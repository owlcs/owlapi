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

import java.io.Reader;
import java.io.StringReader;
import java.util.Optional;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;

/**
 * An ontology input source that wraps a string.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class StringDocumentSource extends OWLOntologyDocumentSourceBase {

    private final String string;

    /**
     * @param string
     *        the source string
     */
    public StringDocumentSource(String string) {
        super("string:ontology", null, null);
        this.string = checkNotNull(string, "string cannot be null");
        // avoid attempting IRI resolution if it is known to be failed
        // a String document source needs not have an IRI and should not resolve
        // it anyway
        failedOnIRI.set(true);
    }

    /**
     * @param string
     *        the source string
     * @param iri
     *        The document IRI
     */
    public StringDocumentSource(String string, IRI iri) {
        this(string, iri, null, null);
    }

    /**
     * @param target
     *        a document target
     */
    public StringDocumentSource(StringDocumentTarget target) {
        this(target.toString());
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
    public StringDocumentSource(String string, IRI documentIRI, @Nullable OWLDocumentFormat f, @Nullable String mime) {
        super(documentIRI, f, mime);
        this.string = checkNotNull(string, "string cannot be null");
    }

    /**
     * Specifies a string as an ontology document.
     * 
     * @param string
     *        The string
     * @param prefix
     *        The document IRI prefix
     * @param f
     *        ontology format
     * @param mime
     *        mime type
     */
    public StringDocumentSource(String string, String prefix, @Nullable OWLDocumentFormat f, @Nullable String mime) {
        super(prefix, f, mime);
        this.string = checkNotNull(string, "string cannot be null");
    }

    @Override
    public Optional<Reader> getReader() {
        return optional(new StringReader(string));
    }
}
