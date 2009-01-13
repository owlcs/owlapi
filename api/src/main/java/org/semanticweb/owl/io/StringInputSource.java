package org.semanticweb.owl.io;

import java.io.Reader;
import java.io.InputStream;
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
 * Date: 24-Apr-2007<br><br>
 */
public class StringInputSource implements OWLOntologyInputSource {

    private URI physicalURI;

    private String string;

    public StringInputSource(String string) {
        this.string = string;
        physicalURI = URI.create("http://org.semanticweb.ontologies/Ontology" + System.nanoTime());
    }


    /**
     * Specified an input source with an ontology URI
     * @param string
     * @param ontologyURI
     */
    public StringInputSource(String string, URI ontologyURI) {
        this.string = string;
        this.physicalURI = ontologyURI;
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
        return null;
    }


    public URI getPhysicalURI() {
        return physicalURI;
    }
}
