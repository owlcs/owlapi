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
package org.semanticweb.owlapi.model;

import javax.annotation.Nullable;

/**
 * Indicates that an ontology with the given ontology IRI (and possible version
 * IRI) exists.
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
public class OWLOntologyAlreadyExistsException extends OWLOntologyCreationException {

    private static final String ONTOLOGY_ALREADY_EXISTS = "Ontology already exists. ";
    private final OWLOntologyID ontologyID;
    private final @Nullable IRI documentIRI;

    /**
     * Constructs an {@code OWLOntologyAlreadyExistsException} to describe the
     * situation where an attempt to create an ontology failed because the
     * manager already contained an ontology with specified ontology ID.
     * 
     * @param id
     *        The ID of the ontology (not {@code null}) that was already
     *        contained in the manager.
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID id) {
        super(ONTOLOGY_ALREADY_EXISTS + id);
        ontologyID = id;
        documentIRI = null;
    }

    /**
     * Constructs an {@code OWLOntologyAlreadyExistsException} to describe the
     * situation where an attempt to load an ontology failed because the manager
     * already contained an ontology with the ID that was the same as the
     * ontology being loaded.
     * 
     * @param ontologyID
     *        The ontology ID
     * @param documentIRI
     *        The IRI of the document where the load attempt occurred from
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID ontologyID, IRI documentIRI) {
        super(ONTOLOGY_ALREADY_EXISTS + ontologyID + " (New ontology loaded from " + documentIRI.toQuotedString()
                + ')');
        this.ontologyID = ontologyID;
        this.documentIRI = documentIRI;
    }

    /**
     * Constructs an {@code OWLOntologyAlreadyExistsException} to describe the
     * situation where an attempt to create an ontology failed because the
     * manager already contained an ontology with specified ontology ID.
     * 
     * @param id
     *        The ID of the ontology (not {@code null}) that was already
     *        contained in the manager.
     * @param t
     *        the cause
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID id, Throwable t) {
        super(ONTOLOGY_ALREADY_EXISTS + id, t);
        ontologyID = id;
        documentIRI = null;
    }

    /**
     * Constructs an {@code OWLOntologyAlreadyExistsException} to describe the
     * situation where an attempt to load an ontology failed because the manager
     * already contained an ontology with the ID that was the same as the
     * ontology being loaded.
     * 
     * @param ontologyID
     *        The ontology ID
     * @param documentIRI
     *        The IRI of the document where the load attempt occurred from
     * @param t
     *        the cause
     */
    public OWLOntologyAlreadyExistsException(OWLOntologyID ontologyID, IRI documentIRI, Throwable t) {
        super(ONTOLOGY_ALREADY_EXISTS + ontologyID + " (New ontology loaded from " + documentIRI.toQuotedString() + ')',
                t);
        this.ontologyID = ontologyID;
        this.documentIRI = documentIRI;
    }

    /**
     * Gets the ID of the ontology that already exists.
     * 
     * @return The ontology ID.
     */
    public OWLOntologyID getOntologyID() {
        return ontologyID;
    }

    /**
     * Gets the document IRI where the ontology was loaded from.
     * 
     * @return The IRI of the document where the ontology was loaded from. If
     *         the ontology was created without loading it from an ontology
     *         document then the return value will be {@code null}.
     */
    public @Nullable IRI getDocumentIRI() {
        return documentIRI;
    }
}
