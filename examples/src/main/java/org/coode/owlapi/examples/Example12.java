/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Apr-2008<br><br>
 * This example shows how to merge to ontologies (by simply combining axioms from
 * one ontology into another ontology).
 */
public class Example12 {

    public static void main(String[] args) {
        try {
            // Just load two arbitrary ontologies for the purposes of this example
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
            man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/amino-acid/2006/05/18/amino-acid.owl"));
            // Create our ontology merger
            OWLOntologyMerger merger = new OWLOntologyMerger(man);
            // We merge all of the loaded ontologies.  Since an OWLOntologyManager is an OWLOntologySetProvider we
            // just pass this in.  We also need to specify the URI of the new ontology that will be created.
            IRI mergedOntologyIRI = IRI.create("http://www.semanticweb.com/mymergedont");
            OWLOntology merged = merger.createMergedOntology(man, mergedOntologyIRI);
            // Print out the axioms in the merged ontology.
            for (OWLAxiom ax : merged.getAxioms()) {
                System.out.println(ax);
            }
            // Save to RDF/XML
            man.saveOntology(merged, new RDFXMLOntologyFormat(), IRI.create("file:/tmp/mergedont.owlapi"));
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Problem saving ontology: " + e.getMessage());
        }
    }
}
