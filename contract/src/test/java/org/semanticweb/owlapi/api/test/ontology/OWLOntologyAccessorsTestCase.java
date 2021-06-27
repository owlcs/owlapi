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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.*;
import static org.semanticweb.owlapi.search.Filters.subClassWithSub;
import static org.semanticweb.owlapi.search.Filters.subClassWithSuper;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
class OWLOntologyAccessorsTestCase extends TestBase {

    static final OWLNamedIndividual indA = NamedIndividual(iri("indA"));
    static final OWLNamedIndividual indB = NamedIndividual(iri("indB"));
    static final OWLNamedIndividual indC = NamedIndividual(iri("indC"));

    private static void performAxiomTests(OWLOntology ont, OWLAxiom... axioms) {
        assertEquals(ont.getAxiomCount(), axioms.length);
        for (OWLAxiom ax : axioms) {
            assertTrue(contains(ont.axioms(), ax));
            if (ax.isLogicalAxiom()) {
                assertTrue(contains(ont.logicalAxioms(), ax));
            }
            assertEquals(ont.getLogicalAxiomCount(), axioms.length);
            AxiomType<?> axiomType = ax.getAxiomType();
            assertTrue(contains(ont.axioms(axiomType), ax));
            assertTrue(contains(ont.axioms(axiomType, INCLUDED), ax));
            assertEquals(ont.getAxiomCount(axiomType), axioms.length);
            assertEquals(ont.getAxiomCount(axiomType, INCLUDED), axioms.length);
            ax.signature().forEach(e -> {
                assertTrue(contains(ont.referencingAxioms(e), ax));
                assertTrue(contains(ont.signature(), e));
            });
        }
    }

    @Test
    void shouldFindExpectedIRIOccurrences() {
        OWLOntology o = getOWLOntology();
        IRI query = iri("urn:test:", "someIRI");
        OWLAnnotationProperty ap1 = df.getOWLAnnotationProperty("urn:test:AP1");
        OWLAnnotationProperty ap2 = df.getOWLAnnotationProperty("urn:test:AP2");
        o.add(df.getOWLAnnotationPropertyDomainAxiom(ap1, query));
        OWLAnnotation a = df.getOWLAnnotation(ap2, query);
        OWLClass c = df.getOWLClass("urn:test:C");
        o.add(df.getOWLSubClassOfAxiom(c, df.getOWLThing(), Collections.singletonList(a)));
        o.add(df.getOWLAnnotationAssertionAxiom(iri("urn:test:", "otherIRI"), a));
        long count = o.referencingAxioms(query).count();
        assertEquals(3, o.getAxiomCount());
        assertEquals(3, count);
    }

    @Test
    void testSubClassOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSubClassOfAxiom ax = SubClassOf(A, B);
        ont.addAxiom(ax);
        OWLSubClassOfAxiom ax2 = SubClassOf(A, ObjectSomeValuesFrom(P, B));
        ont.addAxiom(ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(contains(ont.subClassAxiomsForSubClass(A), ax));
        assertTrue(contains(ont.subClassAxiomsForSuperClass(B), ax));
        assertTrue(contains(ont.axioms(A), ax));
        assertTrue(contains(sup(ont.axioms(subClassWithSub, A, INCLUDED)), B));
        assertTrue(contains(sub(ont.axioms(subClassWithSuper, B, INCLUDED)), A));
    }

    @Test
    void testEquivalentClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentClassesAxioms(A), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(B), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(C), ax));
        assertTrue(contains(ont.axioms(A), ax));
        assertTrue(contains(ont.axioms(B), ax));
        assertTrue(contains(ont.axioms(C), ax));
    }

    @Test
    void testDisjointClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDisjointClassesAxiom ax = DisjointClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointClassesAxioms(A), ax));
        assertTrue(contains(ont.disjointClassesAxioms(B), ax));
        assertTrue(contains(ont.disjointClassesAxioms(C), ax));
        assertTrue(contains(ont.axioms(A), ax));
        assertTrue(contains(ont.axioms(B), ax));
        assertTrue(contains(ont.axioms(C), ax));
    }

    @Test
    void testSubObjectPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(P, Q);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectSubPropertyAxiomsForSubProperty(P), ax));
        assertTrue(contains(ont.objectSubPropertyAxiomsForSuperProperty(Q), ax));
        assertTrue(contains(ont.axioms(P), ax));
    }

    @Test
    void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLEquivalentObjectPropertiesAxiom ax = EquivalentObjectProperties(P, Q, R);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(P), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(Q), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(R), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(ont.axioms(Q), ax));
        assertTrue(contains(ont.axioms(R), ax));
    }

    @Test
    void testDisjointObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(P, Q, R);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(P), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(Q), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(R), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(ont.axioms(Q), ax));
        assertTrue(contains(ont.axioms(R), ax));
    }

    @Test
    void testObjectPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(P, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyDomainAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(domain(ont.objectPropertyDomainAxioms(P)), A));
    }

    @Test
    void testObjectPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(P, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyRangeAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(range(ont.objectPropertyRangeAxioms(P)), A));
    }

    @Test
    void testFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isFunctional(P, ont));
    }

    @Test
    void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.inverseFunctionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isInverseFunctional(P, ont));
    }

    @Test
    void testTransitiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.transitiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isTransitive(P, ont));
    }

    @Test
    void testSymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.symmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isSymmetric(P, ont));
    }

    @Test
    void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.asymmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isAsymmetric(P, ont));
    }

    @Test
    void testReflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.reflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isReflexive(P, ont));
    }

    @Test
    void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.irreflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isIrreflexive(P, ont));
    }

    @Test
    void testSubDataPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(DP, DQ);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataSubPropertyAxiomsForSubProperty(DP), ax));
        assertTrue(contains(ont.dataSubPropertyAxiomsForSuperProperty(DQ), ax));
        assertTrue(contains(ont.axioms(DP), ax));
    }

    @Test
    void testEquivalentDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(DP, DQ, DR);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DP), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DQ), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DR), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(ont.axioms(DQ), ax));
        assertTrue(contains(ont.axioms(DR), ax));
    }

    @Test
    void testDisjointDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(DP, DQ, DR);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DP), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DQ), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DR), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(ont.axioms(DQ), ax));
        assertTrue(contains(ont.axioms(DR), ax));
    }

    @Test
    void testDataPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(DP, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyDomainAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(domain(ont.dataPropertyDomainAxioms(DP)), A));
    }

    @Test
    void testDataPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDatatype dt = Datatype(iri("dt"));
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, dt);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyRangeAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(range(ont.dataPropertyRangeAxioms(DP)), dt));
    }

    @Test
    void testFunctionalDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(DP);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalDataPropertyAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(isFunctional(DP, ont));
    }

    @Test
    void testClassAssertionAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLClassAssertionAxiom ax = ClassAssertion(A, indA);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.classAssertionAxioms(indA), ax));
        assertTrue(contains(ont.classAssertionAxioms(A), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        assertTrue(contains(instances(ont.classAssertionAxioms(indA)), indA));
        assertTrue(contains(types(ont.classAssertionAxioms(indA)), A));
    }

    @Test
    void testObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(P, indA, indB);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testNegativeObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(P, indA, indB);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeObjectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLLiteral lit = Literal(3);
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(DP, indA, lit);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testNegativeDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLLiteral lit = Literal(3);
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(DP, indA, lit);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeDataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testSameIndividualAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.sameIndividualAxioms(indA), ax));
        assertTrue(contains(ont.sameIndividualAxioms(indB), ax));
        assertTrue(contains(ont.sameIndividualAxioms(indC), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        Collection<OWLObject> equivalent =
            asUnorderedSet(equivalent(ont.sameIndividualAxioms(indA)));
        assertTrue(equivalent.contains(indB));
        assertTrue(equivalent.contains(indC));
    }

    @Test
    void testDifferentIndividualsAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB, indC);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.differentIndividualAxioms(indA), ax));
        assertTrue(contains(ont.differentIndividualAxioms(indB), ax));
        assertTrue(contains(ont.differentIndividualAxioms(indC), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        Collection<OWLObject> different =
            asUnorderedSet(different(ont.differentIndividualAxioms(indA)));
        assertTrue(different.contains(indB));
        assertTrue(different.contains(indC));
    }
}
