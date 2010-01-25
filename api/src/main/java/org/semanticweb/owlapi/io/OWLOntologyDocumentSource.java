package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;

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
 * License along with this library; if not, If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Apr-2007<br><br>
 *
 * A document source provides a point for loading an ontology.
 * A document source may provide three ways of obtaining an ontology document:
 * <ol>
 * <li>From a {@link java.io.Reader}
 * <li>From an {@link java.io.InputStream}
 * <li> From an ontology document {@link org.semanticweb.owlapi.model.IRI}
 * </ol>
 * Consumers that use a document source will attempt
 * to obtain a concrete representation of an ontology in the above
 * order.
 * </p>
 * Note that while an ontology document source may appear similar to a SAX input
 * source, an important difference is that the getReader and getInputStream
 * methods return new instances each time the method is called.  This allows
 * multiple attempts at loading an ontology.
 */
public interface OWLOntologyDocumentSource {

    /**
     * Determines if a reader is available which an ontology document can be
     * parsed from.
     * @return <code>true</code> if a reader can be obtained from this
     * document source, or <code>false</code> if a reader cannot be obtained
     * from this document source.
     */
    public boolean isReaderAvailable();


    /**
     * Gets a reader which an ontology document can be read from.  This
     * method may be called multiple times.  Each invocation will return
     * a new <code>Reader</code>. This method should not be called if the
     * <code>isReaderAvailable</code> method returns false.  A <code>Runtime</code> execption will be
     * thrown if this happens.
     * @return A new <code>Reader</code> which the ontology can be read from.
     */
    public Reader getReader();

    /**
     * Determines if an input stream is available which an ontology document can be parsed from.
     * @return <code>true</code> if an input stream can be obtained,
     * <code>false</code> if an input stream cannot be obtained from
     * this document source.
     */
    public boolean isInputStreamAvailable();


    /**
     * If an input stream can be obtained from this document source then this method creates it.
     * This method may be called multiple times.  Each invocation will return a new input stream.
     * This method should not be called if the <code>isInputStreamAvailable</code>
     * method returns <code>false</code>.
     * @return A new input stream which the ontology can be read from.
     */
    public InputStream getInputStream();


    /**
     * Gets the IRI of the ontology document.
     * @return An IRI which represents the ontology document IRI - this will never be <code>null</code>.
     */
    public IRI getDocumentIRI();


}
