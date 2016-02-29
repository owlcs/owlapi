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
package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.Profiles;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class MultiImportsTestCase extends TestBase {

    @Test
    public void testImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "imports"), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/imports/D.owl"));
    }

    @Test
    public void testCyclicImports() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(new File(RESOURCES, "/importscyclic/D.owl"));
    }

    @Test
    public void testCyclicImports2() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        m.loadOntologyFromOntologyDocument(IRI.create(new File(RESOURCES, "importscyclic/D.owl")));
    }

    @Test
    public void shouldNotCreateIllegalPunning() throws OWLOntologyCreationException {
        m.getIRIMappers().add(new AutoIRIMapper(new File(RESOURCES, "importscyclic"), true));
        OWLOntology o = m.loadOntologyFromOntologyDocument(IRI.create(new File(RESOURCES,
            "importscyclic/relaMath.owl")));
        OWLProfileReport checkOntology = Profiles.OWL2_DL.checkOntology(o);
        assertTrue(checkOntology.toString(), checkOntology.isInProfile());
        for (OWLOntology ont : o.getDirectImports()) {
            assertTrue(ont.getAnnotationPropertiesInSignature().toString(), ont.getAnnotationPropertiesInSignature()
                .isEmpty());
        }
    }

    @Test
    public void testTurtleGraphImport() throws OWLOntologyCreationException {
        // document without ontology declaration should be removed and
        // reimported
        File ontologyDirectory = new File(RESOURCES, "importNoOntology");
        String ns = "http://www.w3.org/2013/12/FDA-TA/tests/RenalTransplantation/";
        IRI bobsOntologyName = IRI.create(ns + "subject-bob");
        OWLNamedIndividual bobsIndividual = df.getOWLNamedIndividual(IRI.create(ns
            + "subject-bob#subjectOnImmunosuppressantA2"));
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create(ns + "subject-amy"), IRI.create(new File(ontologyDirectory,
            "subject-amy.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(bobsOntologyName, IRI.create(new File(ontologyDirectory,
            "subject-bob.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create(ns + "subject-sue"), IRI.create(new File(ontologyDirectory,
            "subject-sue.ttl"))));
        m.getIRIMappers().add(new SimpleIRIMapper(IRI.create("http://www.w3.org/2013/12/FDA-TA/core"), IRI.create(
            new File(ontologyDirectory, "core.ttl"))));
        OWLOntology topLevelImport = m.loadOntologyFromOntologyDocument(new File(ontologyDirectory, "subjects.ttl"));
        assertTrue("Individuals about Bob are missing...", topLevelImport.containsEntityInSignature(bobsIndividual,
            INCLUDED));
    }
}
