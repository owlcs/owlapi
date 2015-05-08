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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLStorerFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class RendererAndParserTestCase extends TestBase {

    private AxiomBuilder axioms;

    public RendererAndParserTestCase(AxiomBuilder b) {
        axioms = b;
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
        // AnonymousIndividual
        () -> {
            Set<OWLAxiom> axioms = new HashSet<>();
            OWLClassExpression desc = df.getOWLObjectComplementOf(
            createClass());
            OWLIndividual ind = createIndividual();
            axioms.add(df.getOWLClassAssertionAxiom(desc, ind));
            return axioms;
        } ,
        // ClassAssertionAxioms
        () -> {
            OWLIndividual ind = createIndividual();
            OWLClass cls = createClass();
            OWLAxiom ax = df.getOWLClassAssertionAxiom(cls, ind);
            return singleton(ax);
        } ,
        // DifferentIndividualsAxiom
        () -> {
            Set<OWLIndividual> individuals = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                individuals.add(createIndividual());
            }
            OWLAxiom ax = df.getOWLDifferentIndividualsAxiom(individuals);
            return singleton(ax);
        } ,
        // EquivalentClasses
        () -> {
            OWLClass clsA = createClass();
            OWLObjectProperty prop = createObjectProperty();
            OWLClassExpression descA = df.getOWLObjectSomeValuesFrom(prop, df
            .getOWLThing());
            Set<OWLClassExpression> classExpressions = new HashSet<>();
            classExpressions.add(clsA);
            classExpressions.add(descA);
            OWLAxiom ax = df.getOWLEquivalentClassesAxiom(classExpressions);
            return singleton(ax);
        } ,
        // NegativeDataPropertyAssertionAxiom
        () -> {
            OWLIndividual subj = createIndividual();
            OWLDataProperty prop = createDataProperty();
            OWLLiteral obj = df.getOWLLiteral("TestConstant");
            OWLAxiom ax = df.getOWLNegativeDataPropertyAssertionAxiom(prop,
            subj, obj);
            return singleton(ax);
        } ,
        // NegativeObjectPropertyAssertionAxiom
        () -> {
            return singleton((OWLAxiom) df
            .getOWLNegativeObjectPropertyAssertionAxiom(createObjectProperty(),
            createIndividual(), createIndividual()));
        } ,
        // QCR
        () -> {
            OWLClass clsA = createClass();
            OWLClass clsB = createClass();
            OWLClass clsC = createClass();
            OWLObjectProperty prop = createObjectProperty();
            OWLClassExpression filler = df.getOWLObjectIntersectionOf(clsB,
            clsC);
            OWLCardinalityRestriction<?> restriction = df
            .getOWLObjectMinCardinality(3, prop, filler);
            assertTrue(restriction.isQualified());
            OWLAxiom ax = df.getOWLSubClassOfAxiom(clsA, restriction);
            return singleton(ax);
        } );
    }

    @Before
    public void setUpManager() {
        m.getOntologyFactories().set(new OWLOntologyFactoryImpl(builder));
        m.getOntologyStorers().set(new RDFXMLStorerFactory());
        m.getOntologyParsers().set(new RDFXMLParserFactory());
    }

    @Test
    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = getOWLOntology();
        for (OWLAxiom ax : axioms.build()) {
            ontA.addAxiom(ax);
        }
        OWLOntology ontB = roundTrip(ontA);
        Set<OWLLogicalAxiom> aMinusB = asSet(ontA.logicalAxioms());
        aMinusB.removeAll(asList(ontB.axioms()));
        Set<OWLLogicalAxiom> bMinusA = asSet(ontB.logicalAxioms());
        bMinusA.removeAll(asList(ontA.axioms()));
        StringBuilder msg = new StringBuilder();
        if (aMinusB.isEmpty() && bMinusA.isEmpty()) {
            msg.append("Ontology save/load roundtrip OK.\n");
        } else {
            msg.append("Ontology save/load roundtripping error.\n");
            msg.append("=> ").append(aMinusB.size()).append(
            " axioms lost in roundtripping.\n");
            for (OWLAxiom axiom : aMinusB) {
                msg.append(axiom + "\n");
            }
            msg.append("=> ").append(bMinusA.size()).append(
            " axioms added after roundtripping.\n");
            for (OWLAxiom axiom : bMinusA) {
                msg.append(axiom + "\n");
            }
        }
        assertTrue(msg.toString(), aMinusB.isEmpty() && bMinusA.isEmpty());
    }
}
