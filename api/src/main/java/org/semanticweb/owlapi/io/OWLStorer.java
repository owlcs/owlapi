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
package org.semanticweb.owlapi.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;

import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * An ontology storer stores an ontology in a particular format at a location specified by a
 * particular URI.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public interface OWLStorer extends Serializable {

    /**
     * Determines if this storer can store an ontology in the specified ontology format.
     *
     * @param ontologyFormat The desired ontology format.
     * @return {@code true} if this storer can store an ontology in the desired format.
     */
    boolean canStoreOntology(OWLDocumentFormat ontologyFormat);

    /**
     * Stores an ontology to the specified writer in the specified format.
     *
     * @param ontology The ontology to be stored
     * @param writer The writer where the ontology will be saved to
     * @param format The format that the ontology should be stored in
     * @param storerParameters storer parameters
     * @throws OWLOntologyStorageException if there was a problem storing the ontology
     */
    void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format,
        OWLStorerParameters storerParameters) throws OWLOntologyStorageException;

    /**
     * Stores an ontology to the specified stream.
     *
     * @param ontology The ontology to be stored
     * @param outputStream Output stream where the ontology should be stored
     * @param format The format in which to store the ontology
     * @param storerParameters storer parameters
     * @throws OWLOntologyStorageException if there was a problem storing the ontology.
     */
    default void storeOntology(OWLOntology ontology, OutputStream outputStream,
        OWLDocumentFormat format, OWLStorerParameters storerParameters)
        throws OWLOntologyStorageException {
        if (!format.isTextual()) {
            throw new OWLOntologyStorageException(
                "This method must be overridden to support this binary format: " + format.getKey());
        }
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(outputStream, storerParameters.getEncoding())));
            storeOntology(ontology, writer, format, storerParameters);
            writer.flush();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
