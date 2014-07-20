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

import java.io.OutputStream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLMutableOntology extends OWLOntology, HasApplyChange,
        HasApplyChanges, HasDirectAddAxiom, HasDirectAddAxioms {

    /**
     * Saves the ontology. The ontology will be saved to the location that it
     * was loaded from, or if it was created programmatically, it will be saved
     * to the location specified by an ontology IRI mapper at creation time. The
     * ontology will be saved in the same format which it was loaded from, or
     * the default ontology format if the ontology was created programmatically.
     * 
     * @throws OWLOntologyStorageException
     *         An exception will be thrown if there is a problem with saving the
     *         ontology, or the ontology can't be saved in the format it was
     *         loaded from.
     */
    void saveOntology() throws OWLOntologyStorageException;

    /**
     * Saves the ontology, using the specified document IRI to determine
     * where/how the ontology should be saved.
     * 
     * @param documentIRI
     *        The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology cannot be saved
     */
    void saveOntology(@Nonnull IRI documentIRI)
            throws OWLOntologyStorageException;

    /**
     * Saves the ontology, to the specified output stream
     * 
     * @param outputStream
     *        The output stream where the ontology will be saved to
     * @throws OWLOntologyStorageException
     *         If there was a problem saving this ontology to the specified
     *         output stream
     */
    void saveOntology(@Nonnull OutputStream outputStream)
            throws OWLOntologyStorageException;

    /**
     * Saves the ontology in the specified ontology format to its document URI.
     * 
     * @param ontologyFormat
     *        The format in which the ontology should be saved.
     * @throws OWLOntologyStorageException
     *         If the ontology cannot be saved.
     */
    void saveOntology(@Nonnull OWLDocumentFormat ontologyFormat)
            throws OWLOntologyStorageException;

    /**
     * Saves the ontology to the specified document IRI in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The format in which to save the ontology
     * @param documentIRI
     *        The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    void saveOntology(@Nonnull OWLDocumentFormat ontologyFormat,
            @Nonnull IRI documentIRI) throws OWLOntologyStorageException;

    /**
     * Saves the ontology to the specified output stream in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The format in which to save the ontology
     * @param outputStream
     *        The output stream where the ontology will be saved to.
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    void saveOntology(@Nonnull OWLDocumentFormat ontologyFormat,
            @Nonnull OutputStream outputStream)
            throws OWLOntologyStorageException;

    /**
     * Saves the ontology to the specified
     * {@link org.semanticweb.owlapi.io.OWLOntologyDocumentTarget}.
     * 
     * @param documentTarget
     *        The output target where the ontology will be saved to.
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    void saveOntology(@Nonnull OWLOntologyDocumentTarget documentTarget)
            throws OWLOntologyStorageException;

    /**
     * Saves the ontology to the specified output target in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The output format in which to save the ontology
     * @param documentTarget
     *        The output target where the ontology will be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    void saveOntology(@Nonnull OWLDocumentFormat ontologyFormat,
            @Nonnull OWLOntologyDocumentTarget documentTarget)
            throws OWLOntologyStorageException;
}
