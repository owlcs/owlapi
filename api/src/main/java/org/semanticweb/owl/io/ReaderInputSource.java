package org.semanticweb.owl.io;

import org.semanticweb.owl.model.OWLRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
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
 * Date: 17-Nov-2007<br><br>
 *
 * Ontology input source which reads an ontology from a reader.
 */
public class ReaderInputSource implements OWLOntologyInputSource {

    private URI physicalURI;

    private String buffer;



    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     */
    public ReaderInputSource(Reader reader) {
        this(reader, URI.create("http://org.semanticweb.ontologies/Ontology" + System.nanoTime()));
    }


    /**
     * Constructs and ontology input source which will read an ontology
     * from a reader.
     * @param reader The reader that will be used to read an ontology.
     * @param physicalURI The physical URI which will be used as the base
     * of the document if needed.
     */
    public ReaderInputSource(Reader reader, URI physicalURI) {
        this.physicalURI = physicalURI;
        fillBuffer(reader);
    }

    private void fillBuffer(Reader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            char [] tempBuffer = new char [4096];
            int read;
            while((read = reader.read(tempBuffer)) != -1) {
                builder.append(tempBuffer, 0, read);
            }
            buffer = builder.toString();
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public URI getPhysicalURI() {
        return physicalURI;
    }


    public Reader getReader() {

        return new StringReader(buffer);
    }


    public boolean isReaderAvailable() {
        return true;
    }


    public boolean isInputStreamAvailable() {
        return false;
    }


    public InputStream getInputStream() {
        return null;
    }
}

