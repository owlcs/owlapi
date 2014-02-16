/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.rdfxml.parser;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.xml.sax.SAXException;

/** Receives notifications about triples generated during the parsing process. */
public interface RDFConsumer {
    /** Called when model parsing is started.
     * 
     * @param physicalURI
     *            physical URI of the model
     * @throws SAXException
     *             SAXException */
    void startModel(@Nonnull String physicalURI) throws SAXException;

    /** Called when model parsing is finished.
     * 
     * @throws SAXException
     *             sax exception */
    void endModel() throws SAXException;

    /** Called when a statement with resource value is added to the model.
     * 
     * @param subject
     *            URI of the subject resource
     * @param predicate
     *            URI of the predicate resource
     * @param object
     *            URI of the object resource
     * @throws SAXException
     *             SAXException */
    void statementWithResourceValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object)
            throws SAXException;

    /** Called when a statement with literal value is added to the model.
     * 
     * @param subject
     *            URI of the subject resource
     * @param predicate
     *            URI of the predicate resource
     * @param object
     *            literal object value
     * @param language
     *            the language
     * @param datatype
     *            the URI of the literal's datatype (may be {@code null})
     * @throws SAXException
     *             SAXException */
    void statementWithLiteralValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object,
            @Nullable String language, @Nullable String datatype)
            throws SAXException;

    /** Receives the logical URI of the model.
     * 
     * @param logicalURI
     *            logical URI of the model
     * @throws SAXException
     *             SAXException */
    void logicalURI(@Nonnull String logicalURI) throws SAXException;

    /** Receives the notification that the model being parsed includes another
     * model with supplied URIs.
     * 
     * @param logicalURI
     *            logical URI of the model
     * @param physicalURI
     *            physical URI of the model
     * @throws SAXException
     *             SAXException */
    void
            includeModel(@Nullable String logicalURI,
                    @Nullable String physicalURI) throws SAXException;

    /** Receives the notification that the attribute and its value has been
     * parsed.
     * 
     * @param key
     *            the key of the attribute
     * @param value
     *            the value of the attribute
     * @throws SAXException
     *             SAXException */
    void addModelAttribte(@Nonnull String key, @Nonnull String value)
            throws SAXException;
}
