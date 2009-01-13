package org.semanticweb.owl.io;

import java.io.OutputStream;
import java.io.Writer;
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
 * Date: 18-Nov-2007<br><br>
 *
 * Specifies an interface to access a stream which can be used to store an
 * ontology.  Any OWLOntologyStorer that uses this interface will first try
 * to obtain a writer (if isWriterAvailable returns true), followed by an
 * OutputStream (if isOutputStreamAvailable returns true), followed by trying
 * to open a stream from a physical URI (if isPhysicalURIAvailable returns true)
 */
public interface OWLOntologyOutputTarget {

    boolean isWriterAvailable();

    Writer getWriter();

    boolean isOutputStreamAvailable();

    OutputStream getOutputStream();

    boolean isPhysicalURIAvailable();

    URI getPhysicalURI();
}
