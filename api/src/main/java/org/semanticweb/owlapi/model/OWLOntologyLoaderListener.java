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

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Receives notification of ontology loading starting and finishing from a
 * manager.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public interface OWLOntologyLoaderListener extends Serializable {

    /**
     * Called when the process of attempting to load an ontology starts.
     * 
     * @param event
     *        The loading started event that describes the ontologt that is
     *        being loaded.
     */
    void startedLoadingOntology(@Nonnull LoadingStartedEvent event);

    /**
     * Called when the process of loading an ontology has finished. This method
     * will be called regardless of whether the ontology could be loaded or not
     * - it merely indicates that the process of attempting to load an ontology
     * has finished.
     * 
     * @param event
     *        The loading finished event that describes the ontology that was
     *        loaded.
     */
    void finishedLoadingOntology(@Nonnull LoadingFinishedEvent event);

    /** Loading event. */
    class LoadingEvent {

        private final OWLOntologyID ontologyID;
        private final IRI documentIRI;
        private final boolean imported;

        public LoadingEvent(OWLOntologyID ontologyID, IRI documentIRI,
                boolean imported) {
            this.ontologyID = ontologyID;
            this.documentIRI = documentIRI;
            this.imported = imported;
        }

        /**
         * Gets the ID of the ontology being loaded.
         * 
         * @return The ontology ID.
         */
        public OWLOntologyID getOntologyID() {
            return ontologyID;
        }

        /**
         * Gets the document IRI for the ontology being loaded.
         * 
         * @return The document IRI that describes where the ontology was loaded
         *         from.
         */
        public IRI getDocumentIRI() {
            return documentIRI;
        }

        /**
         * Determines if the ontology was loaded because of an imports
         * statement.
         * 
         * @return {@code true} if the ontology was loaded because it was
         *         imported by another ontology, or {@code false} if the
         *         ontology was loaded by a direct load request on
         *         OWLOntologyManager.
         */
        public boolean isImported() {
            return imported;
        }
    }

    /** Loading start event. */
    class LoadingStartedEvent extends LoadingEvent {

        public LoadingStartedEvent(OWLOntologyID ontologyID, IRI documentIRI,
                boolean imported) {
            super(ontologyID, documentIRI, imported);
        }
    }

    /**
     * Describes the situation when the loading process for an ontology has
     * finished.
     */
    class LoadingFinishedEvent extends LoadingEvent {

        private final Exception ex;

        public LoadingFinishedEvent(OWLOntologyID ontologyID, IRI documentIRI,
                boolean imported, Exception ex) {
            super(ontologyID, documentIRI, imported);
            this.ex = ex;
        }

        /**
         * Determines if the ontology was successfully loaded.
         * 
         * @return {@code true} if the ontology was successfully loaded,
         *         {@code false} if the ontology was not successfully loaded.
         *         Note that an ontology being successfully loaded does not
         *         imply that any ontologies that the ontology imports were
         *         successfully loaded.
         */
        public boolean isSuccessful() {
            return ex == null;
        }

        /**
         * If the ontology was not loaded successfully then this method can be
         * used to access the exception that describes why the ontology was not
         * loaded successfully.
         * 
         * @return The exception that describes why the ontology was not loaded
         *         successfully, or {@code null} if the ontology was loaded
         *         successfully.
         */
        public Exception getException() {
            return ex;
        }
    }
}
