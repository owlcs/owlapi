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
package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;

/**
 * Test cases for rendering of disjoint axioms. The OWL 1.1 specification makes
 * it possible to specify that a set of classes are mutually disjoint.
 * Unfortunately, this must be represented in RDF as a set of pairwise disjoint
 * statements. In otherwords, DisjointClasses(A, B, C) must be represented as
 * DisjointWith(A, B), DisjointWith(A, C) DisjointWith(B, C). ~This test case
 * ensure that these axioms are serialsed correctly.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
public class DisjointsTestCase extends TestBase {

    @Before
    public void setUpManager() {
        m.getOntologyFactories().set(new OWLOntologyFactoryImpl(builder));
    }

    @Test
    public void testAnonDisjoints() throws Exception {
        OWLOntology ontA = getOWLOntology();
        OWLClass clsA = createClass();
        OWLClass clsB = createClass();
        OWLObjectProperty prop = createObjectProperty();
        OWLClassExpression descA = df.getOWLObjectSomeValuesFrom(prop, clsA);
        OWLClassExpression descB = df.getOWLObjectSomeValuesFrom(prop, clsB);
        Set<OWLClassExpression> classExpressions = new HashSet<>();
        classExpressions.add(descA);
        classExpressions.add(descB);
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(classExpressions);
        ontA.addAxiom(ax);
        OWLOntology ontB = roundTrip(ontA);
        assertTrue(contains(ontB.axioms(), ax));
    }
}
