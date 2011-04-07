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
 * Copyright 2011, University of Manchester
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

package org.semanticweb.owlapi.api.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 01-Jul-2010
 */
public class OWLXMLNullPointerTestCase extends AbstractOWLAPITestCase {

    private static String NS = "http://www.co-ode.org/ontologies/pizza/pizza.owl";

    public static final String ANONYMOUS_INDIVIDUAL_ANNOTATION = "Anonymous individual for testing";

    public void testRoundTrip() {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.createOntology(IRI.create(NS));
            OWLDataFactory factory = manager.getOWLDataFactory();

            OWLAnonymousIndividual i = factory.getOWLAnonymousIndividual();
            manager.addAxiom(ontology, factory.getOWLAnnotationAssertionAxiom(factory.getRDFSLabel(), i, factory.getOWLLiteral(ANONYMOUS_INDIVIDUAL_ANNOTATION)));
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(factory.getOWLClass(IRI.create(NS + "#CheeseyPizza")), i));
            OWLIndividual j = factory.getOWLAnonymousIndividual();
            manager.addAxiom(ontology, factory.getOWLClassAssertionAxiom(factory.getOWLClass(IRI.create(NS + "#CheeseTopping")), j));
            manager.addAxiom(ontology, factory.getOWLObjectPropertyAssertionAxiom(factory.getOWLObjectProperty(IRI.create(NS + "#hasTopping")), i, j));

            File tmpFile = File.createTempFile("Test", ".owl");
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), new StreamDocumentTarget(new FileOutputStream(tmpFile)));

            OWLOntologyManager manager2 = OWLManager.createOWLOntologyManager();
            manager2.loadOntologyFromOntologyDocument(tmpFile);
        }
        catch (OWLOntologyCreationException e) {
            fail(e.getMessage());
        }
        catch (IOException e) {
            fail(e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            fail(e.getMessage());
        }
    }

}
