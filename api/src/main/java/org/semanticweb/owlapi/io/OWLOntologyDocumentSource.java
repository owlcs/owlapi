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

import org.semanticweb.owlapi.model.IRI;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-Apr-2007<br><br>
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
     *         document source, or <code>false</code> if a reader cannot be obtained
     *         from this document source.
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
     *         <code>false</code> if an input stream cannot be obtained from
     *         this document source.
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
