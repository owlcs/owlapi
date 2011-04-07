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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Nov-2007<br><br>
 *
 * An ontology document source which can read from a stream.
 */
public class StreamDocumentSource implements OWLOntologyDocumentSource {

    public static final String DOCUMENT_IRI_SCHEME = "inputstream";

    private static int counter = 0;

    private IRI documentIRI;

    private byte [] buffer;


    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param is The stream that the ontology representation will be
     * read from.
     */
    public StreamDocumentSource(InputStream is) {
        this(is, getNextDocumentIRI());
    }

    public static synchronized IRI getNextDocumentIRI() {
        counter = counter + 1;
        return IRI.create(DOCUMENT_IRI_SCHEME + ":ontology" + counter);
    }


    /**
     * Constructs an input source which will read an ontology from
     * a representation from the specified stream.
     * @param stream The stream that the ontology representation will be
     * read from.
     * @param documentIRI The document IRI
     */
    public StreamDocumentSource(InputStream stream, IRI documentIRI) {
        this.documentIRI = documentIRI;
        readIntoBuffer(stream);
    }


    /**
     * Reads all the bytes from the specified stream into a temporary buffer,
     * which is necessary because we may need to access the input stream more
     * than once.  In other words, this method caches the input stream.
     * @param stream The stream to be "cached"
     */
    private void readIntoBuffer(InputStream stream) {
        try {
            byte [] tempBuffer = new byte [4096];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read;
            while((read = stream.read(tempBuffer)) != -1) {
                bos.write(tempBuffer, 0, read);
            }
            buffer = bos.toByteArray();
        }
        catch (IOException e) {
            throw new OWLOntologyInputSourceException(e);
        }
    }

    public boolean isInputStreamAvailable() {
        return true;
    }


    public InputStream getInputStream() {
        return new ByteArrayInputStream(buffer);
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }


    public Reader getReader() {
        throw new OWLRuntimeException("Reader not available.  Check with StreamDocumentSource.isReaderAvailable() first!");
    }


    public boolean isReaderAvailable() {
        return false;
    }
}
