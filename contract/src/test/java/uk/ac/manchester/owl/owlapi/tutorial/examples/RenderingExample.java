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
package uk.ac.manchester.owl.owlapi.tutorial.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import uk.ac.manchester.owl.owlapi.tutorialowled2011.OWLTutorialSyntaxOntologyFormat;
import uk.ac.manchester.owl.owlapi.tutorialowled2011.OWLTutorialSyntaxOntologyStorer;

/**
 * Simple Rendering Example. Reads an ontology and then renders it.
 * 
 * @author Sean Bechhofer, The University Of Manchester, Information Management
 *         Group
 * @since 2.0.0
 */
public class RenderingExample {

    /**
     * @param inputOntology
     *        input ontology IRI
     * @param outputOntology
     *        output ontology IRI
     * @throws OWLOntologyCreationException
     *         OWLOntologyCreationException
     * @throws OWLOntologyStorageException
     *         OWLOntologyStorageException
     */
    public void render(String inputOntology, String outputOntology)
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // A simple example of how to load and save an ontology
        /* Get an Ontology Manager */
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI inputDocumentIRI = IRI.create(inputOntology);
        IRI outputDocumentIRI = IRI.create(outputOntology);
        /* Load an ontology from a document IRI */
        OWLOntology ontology = manager
                .loadOntologyFromOntologyDocument(inputDocumentIRI);
        /* Report information about the ontology */
        System.out.println("Ontology Loaded...");
        System.out.println("Document IRI: " + inputDocumentIRI);
        System.out.println("Logical IRI : " + ontology.getOntologyID());
        System.out.println("Format      : "
                + manager.getOntologyFormat(ontology));
        /* Register the ontology storer with the manager */
        manager.getOntologyStorers().add(new OWLTutorialSyntaxOntologyStorer());
        /* Save using a different format */
        System.out.println("Storing     : " + outputDocumentIRI);
        manager.saveOntology(ontology, new OWLTutorialSyntaxOntologyFormat(),
                outputDocumentIRI);
        /* Remove the ontology from the manager */
        manager.removeOntology(ontology);
        System.out.println("Done");
    }
}
