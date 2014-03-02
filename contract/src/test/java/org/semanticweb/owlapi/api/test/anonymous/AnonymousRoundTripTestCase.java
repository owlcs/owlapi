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
package org.semanticweb.owlapi.api.test.anonymous;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class AnonymousRoundTripTestCase extends TestBase {

    @Test
    public void testRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        String NS = "http://smi-protege.stanford.edu/ontologies/AnonymousIndividuals.owl";
        OWLClass a = Class(IRI(NS + "#A"));
        OWLAnonymousIndividual h = AnonymousIndividual();
        OWLAnonymousIndividual i = AnonymousIndividual();
        OWLAnnotationProperty p = AnnotationProperty(IRI(NS + "#p"));
        OWLObjectProperty q = ObjectProperty(IRI(NS + "#q"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        OWLAnnotation annotation1 = df.getOWLAnnotation(p, h);
        m.addAxiom(ontology,
                df.getOWLAnnotationAssertionAxiom(a.getIRI(), annotation1));
        m.addAxiom(ontology, ClassAssertion(a, h));
        m.addAxiom(ontology, ObjectPropertyAssertion(q, h, i));
        OWLAnnotation annotation2 = df.getOWLAnnotation(df.getRDFSLabel(),
                Literal("Second", "en"));
        m.addAxiom(ontology, df.getOWLAnnotationAssertionAxiom(h, annotation2));
        roundTrip(ontology, new ManchesterOWLSyntaxOntologyFormat());
    }
}
