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
package org.semanticweb.owlapi6.apitest.syntax.rdf;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.createClass;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.createDataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.createIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.createObjectProperty;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLLogicalAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi6.rdf.rdfxml.renderer.RDFXMLStorerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
@RunWith(Parameterized.class)
public class RendererAndParserTestCase extends TestBase {

    private final AxiomBuilder axioms;

    public RendererAndParserTestCase(AxiomBuilder b) {
        axioms = b;
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
            // AnonymousIndividual
            () -> singletonList(df.getOWLClassAssertionAxiom(
                df.getOWLObjectComplementOf(createClass()), createIndividual())),
            // ClassAssertionAxioms
            () -> singletonList(df.getOWLClassAssertionAxiom(createClass(), createIndividual())),
            // DifferentIndividualsAxiom
            () -> singletonList(df.getOWLDifferentIndividualsAxiom(createIndividual(),
                createIndividual(), createIndividual(), createIndividual(), createIndividual())),
            // EquivalentClasses
            () -> singletonList(df.getOWLEquivalentClassesAxiom(createClass(),
                df.getOWLObjectSomeValuesFrom(createObjectProperty(), df.getOWLThing()))),
            // NegativeDataPropertyAssertionAxiom
            () -> singletonList(df.getOWLNegativeDataPropertyAssertionAxiom(createDataProperty(),
                createIndividual(), df.getOWLLiteral("TestConstant"))),
            // NegativeObjectPropertyAssertionAxiom
            () -> singletonList(df.getOWLNegativeObjectPropertyAssertionAxiom(
                createObjectProperty(), createIndividual(), createIndividual())),
            // QCR
            () -> singletonList(df.getOWLSubClassOfAxiom(createClass(),
                df.getOWLObjectMinCardinality(3, createObjectProperty(),
                    df.getOWLObjectIntersectionOf(createClass(), createClass())))));
    }

    @Before
    public void setUpManager() {
        m.getOntologyStorers().set(new RDFXMLStorerFactory());
        m.getOntologyParsers().set(new RDFXMLParserFactory());
    }

    @Test
    public void testSaveAndReload() throws Exception {
        OWLOntology ontA = getOWLOntology();
        ontA.add(axioms.build());
        OWLOntology ontB = roundTrip(ontA);
        Set<OWLLogicalAxiom> aMinusB = asUnorderedSet(ontA.logicalAxioms());
        ontB.axioms().forEach(aMinusB::remove);
        Set<OWLLogicalAxiom> bMinusA = asUnorderedSet(ontB.logicalAxioms());
        ontA.axioms().forEach(bMinusA::remove);
        StringBuilder msg = new StringBuilder();
        if (aMinusB.isEmpty() && bMinusA.isEmpty()) {
            msg.append("Ontology save/load roundtrip OK.\n");
        } else {
            msg.append("Ontology save/load roundtripping error.\n");
            msg.append("=> ").append(aMinusB.size()).append(" axioms lost in roundtripping.\n");
            for (OWLAxiom axiom : aMinusB) {
                msg.append(axiom).append("\n");
            }
            msg.append("=> ").append(bMinusA.size()).append(" axioms added after roundtripping.\n");
            for (OWLAxiom axiom : bMinusA) {
                msg.append(axiom).append("\n");
            }
        }
        assertTrue(msg.toString(), aMinusB.isEmpty() && bMinusA.isEmpty());
    }
}
