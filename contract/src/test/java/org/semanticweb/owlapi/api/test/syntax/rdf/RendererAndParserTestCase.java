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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLStorerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
class RendererAndParserTestCase extends TestBase {

    static List<OWLAxiom> getData() {
        return l(
            // AnonymousIndividual
            ClassAssertion(ObjectComplementOf(createClass()), createIndividual()),
            // ClassAssertionAxioms
            ClassAssertion(createClass(), createIndividual()),
            // DifferentIndividualsAxiom
            DifferentIndividuals(createIndividual(), createIndividual(), createIndividual(),
                createIndividual(), createIndividual()),
            // EquivalentClasses
            EquivalentClasses(createClass(),
                ObjectSomeValuesFrom(createObjectProperty(), OWLThing())),
            // NegativeDataPropertyAssertionAxiom
            NegativeDataPropertyAssertion(createDataProperty(), createIndividual(),
                Literal("TestConstant")),
            // NegativeObjectPropertyAssertionAxiom
            NegativeObjectPropertyAssertion(createObjectProperty(), createIndividual(),
                createIndividual()),
            // QCR
            SubClassOf(createClass(), ObjectMinCardinality(3, createObjectProperty(),
                ObjectIntersectionOf(createClass(), createClass()))));
    }

    @BeforeEach
    void setUpManager() {
        m.getOntologyStorers().set(new RDFXMLStorerFactory());
        m.getOntologyParsers().set(new RDFXMLParserFactory());
    }

    @ParameterizedTest
    @MethodSource("getData")
    void testSaveAndReload(OWLAxiom axioms) {
        OWLOntology ontA = create();
        ontA.add(axioms);
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
        assertTrue(aMinusB.isEmpty() && bMinusA.isEmpty(), msg.toString());
    }
}
