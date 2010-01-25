package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

import java.io.InputStream;
import java.io.Reader;
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
 */
public class IRIDocumentSource implements OWLOntologyDocumentSource {

    private IRI documentIRI;


    public IRIDocumentSource(IRI documentIRI) {
        this.documentIRI = documentIRI;
    }


    public IRI getDocumentIRI() {
        return documentIRI;
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        throw new OWLRuntimeException("InputStream not available.  Check with IRIDocumentSource.isInputStreamAvailable() first!");
    }


    public boolean isReaderAvailable() {
        return false;
    }


    public Reader getReader() {
        throw new OWLRuntimeException("Reader not available.  Check with IRIDocumentSource.isReaderAvailable() first!");
    }


    public String toString() {
        return documentIRI.toString();
    }
}
