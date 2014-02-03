/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** Test for 3186250 */
@SuppressWarnings("javadoc")
public class MissingDeclarationRoundTripTestCase {
    private static final String NS = "http://test.org/MissingDeclaration.owl";
    OWLClass a;
    OWLAnnotationProperty p;

    @Before
    public void setUp() {
        a = Class(IRI(NS + "#A"));
        p = AnnotationProperty(IRI(NS + "#p"));
    }

    @Test
    public void shouldFindOneAxiom() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        OWLOntology ontology = createOntology();
        assertTrue(ontology.containsAnnotationPropertyInSignature(p.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
        String saved = saveOntology(ontology);
        ontology = loadOntology(saved);
        assertTrue(ontology.containsAnnotationPropertyInSignature(p.getIRI()));
        assertEquals(1, ontology.getAxiomCount());
    }

    private OWLOntology createOntology() throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology(IRI(NS));
        OWLAnnotationAssertionAxiom axiom = AnnotationAssertion(p, a.getIRI(),
                Literal("Hello"));
        manager.addAxiom(ontology, axiom);
        return ontology;
    }

    public String saveOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
        format.setAddMissingTypes(false);
        manager.saveOntology(ontology, format, target);
        return target.toString();
    }

    public OWLOntology loadOntology(String o)
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setStrict(true);
        return manager.loadOntologyFromOntologyDocument(
                new StringDocumentSource(o), config);
    }
}
