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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group, Date: 01-Jul-2010
 */
@SuppressWarnings("javadoc")
public class MultiImportsTestCase extends AbstractOWLAPITestCase {

    public static final File RESOURCES;
    static {
        File f = new File("contract/src/test/resources/");
        if (f.exists()) {
            RESOURCES = f;
        } else {
            f = new File("src/test/resources/");
            if (f.exists()) {
                RESOURCES = f;
            } else {
                RESOURCES = null;
                System.out
                        .println("MultiImportsTestCase: NO RESOURCE FOLDER ACCESSIBLE");
            }
        }
    }

    @Test
    public void testImports() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        manager.addIRIMapper(new AutoIRIMapper(new File(RESOURCES, "imports"),
                true));
        manager.loadOntologyFromOntologyDocument(new File(RESOURCES,
                "/imports/D.owl"));
    }

    @Test
    public void testCyclicImports() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        manager.addIRIMapper(new AutoIRIMapper(new File(RESOURCES,
                "importscyclic"), true));
        manager.loadOntologyFromOntologyDocument(new File(RESOURCES,
                "/importscyclic/D.owl"));
    }

    @Test
    public void testCyclicImports2() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        manager.addIRIMapper(new AutoIRIMapper(new File(RESOURCES,
                "importscyclic"), true));
        manager.loadOntologyFromOntologyDocument(IRI.create(new File(RESOURCES,
                "importscyclic/D.owl")));
    }

    @Test
    public void testTurtleGraphImport() throws OWLOntologyCreationException {
        // document without ontology declaration should be removed and
        // reimported
        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        File ONTOLOGY_DIRECTORY = new File(RESOURCES, "importNoOntology");
        IRI BOBS_ONTOLOGY_NAME = IRI
                .create("http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/subject-bob");
        OWLNamedIndividual BOBS_INDIVIDUAL = factory
                .getOWLNamedIndividual(IRI
                        .create("http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/subject-bob#subjectOnImmunosuppressantA2"));
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.addIRIMapper(new SimpleIRIMapper(
                IRI.create("http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/subject-amy"),
                IRI.create(new File(ONTOLOGY_DIRECTORY, "subject-amy.ttl"))));
        manager.addIRIMapper(new SimpleIRIMapper(BOBS_ONTOLOGY_NAME, IRI
                .create(new File(ONTOLOGY_DIRECTORY, "subject-bob.ttl"))));
        manager.addIRIMapper(new SimpleIRIMapper(
                IRI.create("http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/subject-sue"),
                IRI.create(new File(ONTOLOGY_DIRECTORY, "subject-sue.ttl"))));
        manager.addIRIMapper(new SimpleIRIMapper(IRI
                .create("http://www.w3.org/2013/12/FDA-TA/core"), IRI
                .create(new File(ONTOLOGY_DIRECTORY, "core.ttl"))));
        OWLOntology topLevelImport = manager
                .loadOntologyFromOntologyDocument(new File(ONTOLOGY_DIRECTORY,
                        "subjects.ttl"));
        assertTrue("Individuals about Bob are missing...",
                topLevelImport.containsEntityInSignature(BOBS_INDIVIDUAL, true));
    }
}
