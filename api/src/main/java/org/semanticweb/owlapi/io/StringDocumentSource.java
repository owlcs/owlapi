package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
