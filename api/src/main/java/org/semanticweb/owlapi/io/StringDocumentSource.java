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

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Apr-2007<br><br>
 * </p>
 * An ontology input source that wraps a string.
 */
public class StringDocumentSource implements OWLOntologyDocumentSource {

    public static final String DOCUMENT_IRI_SCHEME = "string";

    private static int counter = 0;

    private IRI documentIRI;

    private String string;

    public StringDocumentSource(String string) {
        this.string = string;
        documentIRI = getNextDocumentIRI();
    }

    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create(DOCUMENT_IRI_SCHEME + ":ontology" + counter);
    }



    /**
     * Specifies a string as an ontology document.
     * @param string The string
     * @param documentIRI The document IRI
     */
    public StringDocumentSource(String string, IRI documentIRI) {
        this.string = string;
        this.documentIRI = documentIRI;
    }


    public boolean isReaderAvailable() {
        return true;
    }


    public Reader getReader() {
        return new StringReader(string);
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        throw new OWLRuntimeException("InputStream not available.  Check with StringDocumentSource.isInputStreamAvailable() first!");
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }
}
