package org.semanticweb.owl.model;

import org.semanticweb.owl.io.OWLOntologyInputSource;

import java.net.URI;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 *
 * An ontology factory is responsible from creating new ontologies and creating ontologies
 * from physical URIs.
 */
public interface OWLOntologyFactory {

    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager);

    /**
     * Creates an (empty) ontology.
     * @param ontologyURI The URI of the ontology to create. This MUST NOT BE NULL.
     * @param physicalURI The physical URI of the ontology.  This MAY be
     * <code>null</code>.
     * @param handler The ontology creation handler that will be notified when the
     * ontology has been created.
     * @return The newly created ontology
     * @throws OWLOntologyCreationException if the ontology could not be created.
     */
    public OWLOntology createOWLOntology(URI ontologyURI, URI physicalURI, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException;


    /**
     * Creates and loads an <code>OWLOntology</code>.
     * be loaded into the ontology.
     * @param handler A pointer to an <code>OWLOntologyCreationHandler</code> which will be notified immediately
     * after an emtpty ontology has been created, but before the source data is read and the ontology is loaded
     * with axioms.
     * @return The newly created and loaded ontology
     * @throws OWLOntologyCreationException if the ontology could not be created.
     */
    public OWLOntology loadOWLOntology(OWLOntologyInputSource inputSource, OWLOntologyCreationHandler handler) throws OWLOntologyCreationException;

    /**
     * Determines if the factory can create an ontology for the specified physical
     * URI.
     * @param physicalURI The physical URI of the ontology to be created.  This may
     * be <code>null</code>.
     * @return <code>true</code> if the factory can create an ontology given a physical URI,
     * or <code>false</code> if the factory cannot create an ontology.
     */
    public boolean canCreateFromPhysicalURI(URI physicalURI);


    /**
     * Determines if the factory can load an ontology for the specified physical
     * URI.
     * @return <code>true</code> if the factory can load from the specified input source.
     */
    public boolean canLoad(OWLOntologyInputSource inputSource);


    /**
     * An <code>OWLOntologyCreationHandler</code> gets notified when the factory has created an empty
     * ontology (during the loading process).  This may be needed to handle features such as cyclic imports.
     * For example if OntA and OntB are ontologies and OntA imports OntB and vice versa, OntA will probably be
     * partially loaded, but then will require the loading of OntB to ensure that all entities are declared.  OntB
     * will also require the partial loading of OntA for the same reason.  The handler allows a reference to
     * an ontology which is being loaded to be obtained before loading is finished.
     */
    public interface OWLOntologyCreationHandler {

        /**
         * The factory calls this method as soon as it has created an ontology.  If the
         * factory is loading an ontology then the ontology will not have been populated with
         * axioms at this stage.
         * @param ontology  The newly created ontology.
         */
        void ontologyCreated(OWLOntology ontology);

        void setOntologyFormat(OWLOntology ontology, OWLOntologyFormat format);
    }
}
