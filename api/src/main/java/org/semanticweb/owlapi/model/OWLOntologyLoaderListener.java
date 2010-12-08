package org.semanticweb.owlapi.model;

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
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Apr-2008<br><br>
 * </p>
 * Receives notification of ontology loading starting and finishing from a manager.
 *
 */
public interface OWLOntologyLoaderListener {

    /**
     * Called when the process of attempting to load an ontology starts.
     * @param event The loading started event that describes the ontologt that
     * is being loaded.
     */
    void startedLoadingOntology(LoadingStartedEvent event);


    /**
     * Called when the process of loading an ontology has
     * finished.  This method will be called regardless of whether the
     * ontology could be loaded or not - it merely indicates that the process
     * of attempting to load an ontology has finished.
     * @param event The loading finished event that describes the ontology that was
     * loaded.
     */
    void finishedLoadingOntology(LoadingFinishedEvent event);

    
    public static class LoadingEvent {

        private OWLOntologyID ontologyID;

        private IRI documentIRI;

        private boolean imported;


        public LoadingEvent(OWLOntologyID ontologyID, IRI documentIRI, boolean imported) {
            this.ontologyID = ontologyID;
            this.documentIRI = documentIRI;
            this.imported = imported;
        }

        /**
         * Gets the ID of the ontology being loaded.
         * @return The ontology ID.
         */
        public OWLOntologyID getOntologyID() {
            return ontologyID;
        }


        /**
         * Gets the document IRI for the ontology being loaded
         * @return The document IRI that describes where the ontology
         * was loaded from.
         */
        public IRI getDocumentIRI() {
            return documentIRI;
        }


        /**
         * Determines if the ontology was loaded because of
         * an imports statement.
         * @return <code>true</code> if the ontology was loaded
         * because it was imported by another ontology, or <code>false</code>
         * if the ontology was loaded by a direct load request on OWLOntologyManager.
         */
        public boolean isImported() {
            return imported;
        }
    }

    public static class LoadingStartedEvent extends LoadingEvent {

        public LoadingStartedEvent(OWLOntologyID ontologyID, IRI documentIRI, boolean imported) {
            super(ontologyID, documentIRI, imported);
        }
    }


    /**
     * Describes the situation when the loading process for an ontology has
     * finished.
     */
    public static class LoadingFinishedEvent extends LoadingEvent {

        private OWLOntologyCreationException ex;

        public LoadingFinishedEvent(OWLOntologyID ontologyID, IRI documentIRI, boolean imported, OWLOntologyCreationException ex) {
            super(ontologyID, documentIRI, imported);
            this.ex = ex;
        }


        /**
         * Determines if the ontology was successfully loaded.
         * @return <code>true</code> if the ontology was successfully loaded,
         * <code>false</code> if the ontology was not successfully loaded. Note
         * that an ontology being successfully loaded does not imply that any ontologies
         * that the ontology imports were successfully loaded.
         */
        public boolean isSuccessful() {
            return ex == null;
        }


        /**
         * If the ontology was not loaded successfully then this method can
         * be used to access the exception that describes why the ontology was
         * not loaded successfully.
         * @return The exception that describes why the ontology was not
         * loaded successfully, or <code>null</code> if the ontology was loaded successfully.
         */
        public OWLOntologyCreationException getException() {
            return ex;
        }
    }

}
