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
package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.Imports.EXCLUDED;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** test for 3178902 adapted from the report Thimoty provided. */
@SuppressWarnings("javadoc")
public class ThreeEquivalentsRoundTripTestCase extends TestBase {

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        String NS = "http://protege.org/ontologies";
        OWLClass b = Class(IRI(NS + "#B"));
        OWLClass c = Class(IRI(NS + "#C"));
        OWLObjectProperty p = ObjectProperty(IRI(NS + "#p"));
        OWLObjectProperty q = ObjectProperty(IRI(NS + "#q"));
        OWLAxiom axiomToAdd = EquivalentClasses(Class(IRI(NS + "#A")),
                ObjectSomeValuesFrom(p, b), ObjectSomeValuesFrom(q, c));
        OWLOntology ontology = m.createOntology();
        ontology.getOWLOntologyManager().addAxiom(ontology, axiomToAdd);
        // when
        ontology = roundTrip(ontology);
        // then
        assertTrue(ontology.containsObjectPropertyInSignature(p.getIRI(),
                EXCLUDED));
        assertTrue(ontology.containsObjectPropertyInSignature(q.getIRI(),
                EXCLUDED));
        assertTrue(ontology.containsClassInSignature(b.getIRI(), EXCLUDED));
        assertTrue(ontology.containsClassInSignature(c.getIRI(), EXCLUDED));
    }
}
