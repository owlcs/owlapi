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

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Receives notifications about triples generated during the parsing process.
 */
public interface RDFConsumer {

    /**
     * Called when model parsing is started.
     * 
     * @param physicalURI
     *        physical URI of the model
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void startModel(IRI physicalURI);

    /**
     * Called when model parsing is finished.
     * 
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void endModel();

    /**
     * Called when a statement with resource value is added to the model.
     * 
     * @param subject
     *        URI of the subject resource
     * @param predicate
     *        URI of the predicate resource
     * @param object
     *        URI of the object resource
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void statementWithResourceValue(String subject, String predicate, String object);

    /**
     * Called when a statement with resource value is added to the model.
     * 
     * @param subject
     *        URI of the subject resource
     * @param predicate
     *        URI of the predicate resource
     * @param object
     *        URI of the object resource
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void statementWithResourceValue(IRI subject, IRI predicate, IRI object);

    /**
     * Called when a statement with literal value is added to the model.
     * 
     * @param subject
     *        URI of the subject resource
     * @param predicate
     *        URI of the predicate resource
     * @param object
     *        literal object value
     * @param language
     *        the language
     * @param datatype
     *        the URI of the literal's datatype (may be {@code null})
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void statementWithLiteralValue(String subject, String predicate, String object, @Nullable String language,
            @Nullable String datatype);

    /**
     * Called when a statement with literal value is added to the model.
     * 
     * @param subject
     *        URI of the subject resource
     * @param predicate
     *        URI of the predicate resource
     * @param object
     *        literal object value
     * @param language
     *        the language
     * @param datatype
     *        the URI of the literal's datatype (may be {@code null})
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void statementWithLiteralValue(IRI subject, IRI predicate, String object, @Nullable String language,
            @Nullable IRI datatype);

    /**
     * Receives the logical URI of the model.
     * 
     * @param logicalURI
     *        logical URI of the model
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void logicalURI(IRI logicalURI);

    /**
     * Receives the notification that the model being parsed includes another
     * model with supplied URIs.
     * 
     * @param logicalURI
     *        logical URI of the model
     * @param physicalURI
     *        physical URI of the model
     * @throws OWLRuntimeException
     *         OWLRuntimeException
     */
    void includeModel(@Nullable String logicalURI, @Nullable String physicalURI);

    /**
     * for iris that need to be mapped to blank nodes, e.g., SWRL rules with an
     * IRI - the IRI should be dropped for such constructs.
     * 
     * @param i
     *        iri to remap if not blank
     * @return blank iri remapping i
     */
    IRI remapIRI(IRI i);

    /**
     * for iris that have been remapped to blank nodes, e.g., SWRL rules: the
     * triple subject swrl:body object, for example, needs the subject to be
     * remapped consistently.
     * 
     * @param i
     *        iri to remap if not blank
     * @return blank iri remapping i, or i if i has not been remapped earlier.
     */
    String remapOnlyIfRemapped(String i);

    /**
     * Add a prefix to the underlying ontology format, if prefixes are
     * supported.
     * 
     * @param abbreviation
     *        short name for prefix
     * @param value
     *        replacement for short version
     */
    void addPrefix(String abbreviation, String value);
}
