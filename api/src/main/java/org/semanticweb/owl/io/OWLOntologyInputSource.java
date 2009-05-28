package org.semanticweb.owl.io;

import org.semanticweb.owl.model.IRI;

import java.io.InputStream;
import java.io.Reader;
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
 *
 * An input source provides a point for loading an ontology.
 * An input source may provide three forms
 * of obtaining a concrete representation of an ontology 1) From a
 * <code>Reader</code>, 2) From and <code>InputStream</code> 3) From
 * a physical URI.  Consumers which use an input source will attempt
 * to obtain a concrete representation of an ontology in the above
 * order.
 *
 * While an ontology input source may appear similar to a SAX input
 * source, an important difference is that the getReader and getInputStream
 * methods return new instances each time the method is called.  This allows
 * multiple attempts at loading an ontology.
 */
public interface OWLOntologyInputSource {

    /**
     * Determines if a reader is available which an ontology can be
     * parsed from.
     * @return <code>true</code> if a reader can be ontained from this
     * input source, or <code>false</code> if a reader cannot be obtained
     * from this input source.
     */
    public boolean isReaderAvailable();


    /**
     * Gets a reader which can be used to read an ontology from.  This
     * method may be called multiple times.  Each invocation will return
     * a new <code>Reader</code>. This method should not be called if the
     * <code>isReaderAvailable</code> method returns false
     * @return A new <code>Reader</code> which the ontology can be read from.
     */
    public Reader getReader();

    /**
     * Determines if an input stream is available which an ontology
     * can be parsed from.
     * @return <code>true</code> if an input stream can be obtained,
     * <code>false</code> if an input stream cannot be obtained from
     * this input source.
     */
    public boolean isInputStreamAvailable();


    /**
     * If an input stream can be obtained from this input source
     * then this method creates it.  This method may be called
     * multiple times.  Each invocation will return a new input stream.
     * This method should not be called if the <code>isInputStreamAvailable</code>
     * method returns <code>false</code>.
     * @return A new input stream which the ontology can be read from.
     */
    public InputStream getInputStream();


    /**
     * Gets the physical IRI of the ontology.
     * @return An IRI which represents the physical IRI of
     * an ontology - this will never be <code>null</code>.
     */
    public URI getPhysicalURI();


    
}
