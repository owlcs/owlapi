package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.SAXException;
/*
 * Copyright (C) 2009, University of Manchester
 * Original code by Boris Motik.  The original code formed part of KAON
 * which is licensed under the Lesser General Public License.  The original package
 * name was edu.unika.aifb.rdf.api
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
 * Receives notifications about triples generated during the parsing process.
 */
public interface RDFConsumer {
    /**
     * Called when model parsing is started.
     *
     * @param physicalURI           physical URI of the model
     */
    void startModel(String physicalURI) throws SAXException;
    /**
     * Called when model parsing is finished.
     */
    void endModel() throws SAXException;
    /**
     * Called when a statement with resource value is added to the model.
     *
     * @param subject               URI of the subject resource
     * @param predicate             URI of the predicate resource
     * @param object                URI of the object resource
     */
    void statementWithResourceValue(String subject,String predicate,String object) throws SAXException;
    /**
     * Called when a statement with literal value is added to the model.
     *
     * @param subject               URI of the subject resource
     * @param predicate             URI of the predicate resource
     * @param object                literal object value
     * @param language              the language
     * @param datatype              the URI of the literal's datatype (may be <code>null</code>)
     */
    void statementWithLiteralValue(String subject,String predicate,String object,String language,String datatype) throws SAXException;
    /**
     * Receives the logical URI of the model.
     *
     * @param logicalURI            logical URI of the model
     */
    void logicalURI(String logicalURI) throws SAXException;
    /**
     * Receives the notification that the model being parsed includes another model with supplied URIs.
     *
     * @param logicalURI            logical URI of the model
     * @param physicalURI           physical URI of the model
     */
    void includeModel(String logicalURI,String physicalURI) throws SAXException;
    /**
     * Receives the notification that the attribute and its value has been parsed.
     *
     * @param key                   the key of the attribute
     * @param value                 the value of the attribute
     */
    void addModelAttribte(String key,String value) throws SAXException;
}

