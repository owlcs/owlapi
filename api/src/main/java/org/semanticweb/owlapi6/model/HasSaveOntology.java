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
package org.semanticweb.owlapi6.model;

import java.io.OutputStream;

import org.semanticweb.owlapi6.io.OWLOntologyDocumentTarget;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 6.0.0
 */
public interface HasSaveOntology {

    /**
     * Saves the ontology.
     * <p>
     * The location will be the location that the ontology was loaded from, or, if the ontology was
     * created programmatically, it will be the location specified by an ontology IRI mapper at
     * creation time.
     * <p>
     * The format will be the same format used for loading, or the default ontology format if the
     * ontology was created programmatically.
     *
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology() throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The location will be the location specified in the document iri.
     * <p>
     * The format will be the same format used for loading, or the default ontology format if the
     * ontology was created programmatically.
     *
     * @param documentIRI location to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(IRI documentIRI) throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The output will be on the stream parameter.
     * <p>
     * The format will be the same format used for loading, or the default ontology format if the
     * ontology was created programmatically.
     *
     * @param outputStream stream to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OutputStream outputStream) throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The location will be the location that the ontology was loaded from, or, if the ontology was
     * created programmatically, it will be the location specified by an ontology IRI mapper at
     * creation time.
     * <p>
     * The format will be the one specified in the ontology format parameter.
     * 
     * @param ontologyFormat format to use to save the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OWLDocumentFormat ontologyFormat) throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The location will be the location specified in the document iri.
     * <p>
     * The format will be the one specified in the ontology format parameter.
     * 
     * @param ontologyFormat format to use to save the ontology
     * @param documentIRI location to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OWLDocumentFormat ontologyFormat, IRI documentIRI)
        throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The output will be on the stream parameter.
     * <p>
     * The format will be the one specified in the ontology format parameter.
     * 
     * @param ontologyFormat format to use to save the ontology
     * @param outputStream stream to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OWLDocumentFormat ontologyFormat, OutputStream outputStream)
        throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The location is determined by the document target parameter.
     * <p>
     * The format will be the same format used for loading, or the default ontology format if the
     * ontology was created programmatically.
     * 
     * @param documentTarget the document target to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException;

    /**
     * Saves the ontology.
     * <p>
     * The location is determined by the document target parameter.
     * <p>
     * The format will be the one specified in the ontology format parameter.
     * 
     * @param ontologyFormat format to use to save the ontology
     * @param documentTarget the document target to store the ontology
     * @throws OWLOntologyStorageException An exception will be thrown if there is a problem with
     *         the format or with the location.
     */
    void saveOntology(OWLDocumentFormat ontologyFormat, OWLOntologyDocumentTarget documentTarget)
        throws OWLOntologyStorageException;
}
