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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.isAsymmetric;
import static org.semanticweb.owlapi.search.EntitySearcher.isFunctional;
import static org.semanticweb.owlapi.search.EntitySearcher.isInverseFunctional;
import static org.semanticweb.owlapi.search.EntitySearcher.isIrreflexive;
import static org.semanticweb.owlapi.search.EntitySearcher.isReflexive;
import static org.semanticweb.owlapi.search.EntitySearcher.isSymmetric;
import static org.semanticweb.owlapi.search.EntitySearcher.isTransitive;
import static org.semanticweb.owlapi.search.Filters.subClassWithSub;
import static org.semanticweb.owlapi.search.Filters.subClassWithSuper;
import static org.semanticweb.owlapi.search.Searcher.different;
import static org.semanticweb.owlapi.search.Searcher.domain;
import static org.semanticweb.owlapi.search.Searcher.equivalent;
import static org.semanticweb.owlapi.search.Searcher.instances;
import static org.semanticweb.owlapi.search.Searcher.range;
import static org.semanticweb.owlapi.search.Searcher.sub;
import static org.semanticweb.owlapi.search.Searcher.sup;
import static org.semanticweb.owlapi.search.Searcher.types;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
class OWLOntologyAccessorsTestCase extends TestBase {

    private static void performAxiomTests(@Nonnull OWLOntology ont, @Nonnull OWLAxiom... axioms) {
        assertEquals(ont.getLogicalAxiomCount(), axioms.length);
        for (OWLAxiom ax : axioms) {
            assertTrue(ont.getAxioms().contains(ax));
            if (ax.isLogicalAxiom()) {
                assertTrue(ont.getLogicalAxioms().contains(ax));
            }
            assertEquals(ont.getLogicalAxiomCount(), axioms.length);
            AxiomType<?> axiomType = ax.getAxiomType();
            assertTrue(ont.getAxioms(axiomType).contains(ax));
            assertTrue(ont.getAxioms(axiomType, INCLUDED).contains(ax));
            assertEquals(ont.getAxiomCount(axiomType), axioms.length);
            assertEquals(ont.getAxiomCount(axiomType, INCLUDED), axioms.length);
            for (OWLEntity entity : ax.getSignature()) {
                assert entity != null;
                assertTrue(ont.getReferencingAxioms(entity, EXCLUDED).contains(ax));
                assertTrue(ont.getSignature().contains(entity));
            }
        }
    }

    @Test
    void shouldFindExpectedIRIOccurrences() {
        IRI query = iri("urn:test:", "someIRI");
        OWLAnnotation a = df.getOWLAnnotation(propQ, query);
        OWLOntology o = o(df.getOWLAnnotationPropertyDomainAxiom(propP, query),
            df.getOWLSubClassOfAxiom(C, df.getOWLThing(), Collections.singleton(a)),
            df.getOWLAnnotationAssertionAxiom(query, a));
        int count = o.getReferencingAxioms(query).size();
        assertEquals(6, o.getAxiomCount());
        assertEquals(1, count);
    }

    @Test
    void testSubClassOfAxiomAccessors() {
        OWLSubClassOfAxiom ax = SubClassOf(A, B);
        OWLSubClassOfAxiom ax2 = SubClassOf(A, ObjectSomeValuesFrom(P, B));
        OWLOntology ont = o(ax, ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(ont.getSubClassAxiomsForSubClass(A).contains(ax));
        assertTrue(ont.getSubClassAxiomsForSuperClass(B).contains(ax));
        assertTrue(ont.getAxioms(A, EXCLUDED).contains(ax));
        assertTrue(sup(ont.filterAxioms(subClassWithSub, A, INCLUDED)).contains(B));
        assertTrue(sub(ont.filterAxioms(subClassWithSuper, B, INCLUDED)).contains(A));
    }

    @Test
    void testEquivalentClassesAxiomAccessors() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentClassesAxioms(A).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(B).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(C).contains(ax));
        assertTrue(ont.getAxioms(A, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(B, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(C, EXCLUDED).contains(ax));
    }

    @Test
    void testDisjointClassesAxiomAccessors() {
        OWLDisjointClassesAxiom ax = DisjointClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointClassesAxioms(A).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(B).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(C).contains(ax));
        assertTrue(ont.getAxioms(A, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(B, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(C, EXCLUDED).contains(ax));
    }

    @Test
    void testSubObjectPropertyOfAxiomAccessors() {
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(P, Q);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectSubPropertyAxiomsForSubProperty(P).contains(ax));
        assertTrue(ont.getObjectSubPropertyAxiomsForSuperProperty(Q).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
    }

    @Test
    void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLEquivalentObjectPropertiesAxiom ax = EquivalentObjectProperties(P, Q, R);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(P).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(Q).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(R).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(Q, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(R, EXCLUDED).contains(ax));
    }

    @Test
    void testDisjointObjectPropertiesAxiomAccessors() {
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(P, Q, R);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointObjectPropertiesAxioms(P).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(Q).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(R).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(Q, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(R, EXCLUDED).contains(ax));
    }

    @Test
    void testObjectPropertyDomainAxiomAccessors() {
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(P, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyDomainAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(domain(ont.getObjectPropertyDomainAxioms(P)).contains(A));
    }

    @Test
    void testObjectPropertyRangeAxiomAccessors() {
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(P, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyRangeAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(range(ont.getObjectPropertyRangeAxioms(P)).contains(A));
    }

    @Test
    void testFunctionalObjectPropertyAxiomAccessors() {
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getFunctionalObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isFunctional(P, ont));
    }

    @Test
    void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getInverseFunctionalObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isInverseFunctional(P, ont));
    }

    @Test
    void testTransitiveObjectPropertyAxiomAccessors() {
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getTransitiveObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isTransitive(P, ont));
    }

    @Test
    void testSymmetricObjectPropertyAxiomAccessors() {
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getSymmetricObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isSymmetric(P, ont));
    }

    @Test
    void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getAsymmetricObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isAsymmetric(P, ont));
    }

    @Test
    void testReflexiveObjectPropertyAxiomAccessors() {
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getReflexiveObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isReflexive(P, ont));
    }

    @Test
    void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getIrreflexiveObjectPropertyAxioms(P).contains(ax));
        assertTrue(ont.getAxioms(P, EXCLUDED).contains(ax));
        assertTrue(isIrreflexive(P, ont));
    }

    @Test
    void testSubDataPropertyOfAxiomAccessors() {
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(DP, DQ);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataSubPropertyAxiomsForSubProperty(DP).contains(ax));
        assertTrue(ont.getDataSubPropertyAxiomsForSuperProperty(DQ).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
    }

    @Test
    void testEquivalentDataPropertiesAxiomAccessors() {
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(DP, DQ, DR);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentDataPropertiesAxioms(DP).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(DQ).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(DR).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(DQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(DR, EXCLUDED).contains(ax));
    }

    @Test
    void testDisjointDataPropertiesAxiomAccessors() {
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(DP, DQ, DR);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointDataPropertiesAxioms(DP).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(DQ).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(DR).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(DQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(DR, EXCLUDED).contains(ax));
    }

    @Test
    void testDataPropertyDomainAxiomAccessors() {
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(DP, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyDomainAxioms(DP).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
        assertTrue(domain(ont.getDataPropertyDomainAxioms(DP)).contains(A));
    }

    @Test
    void testDataPropertyRangeAxiomAccessors() {
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, DT);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyRangeAxioms(DP).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
        assertTrue(range(ont.getDataPropertyRangeAxioms(DP)).contains(DT));
    }

    @Test
    void testFunctionalDataPropertyAxiomAccessors() {
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(DP);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getFunctionalDataPropertyAxioms(DP).contains(ax));
        assertTrue(ont.getAxioms(DP, EXCLUDED).contains(ax));
        assertTrue(isFunctional(DP, ont));
    }

    @Test
    void testClassAssertionAxiomAccessors() {
        OWLClassAssertionAxiom ax = ClassAssertion(A, indA);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getClassAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getClassAssertionAxioms(A).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        assertTrue(instances(ont.getClassAssertionAxioms(indA)).contains(indA));
        assertTrue(types(ont.getClassAssertionAxioms(indA)).contains(A));
    }

    @Test
    void testObjectPropertyAxiomAccessors() {
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(P, indA, indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    void testNegativeObjectPropertyAxiomAccessors() {
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(P, indA, indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeObjectPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    void testDataPropertyAxiomAccessors() {
        OWLLiteral lit = Literal(3);
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(DP, indA, lit);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    void testNegativeDataPropertyAxiomAccessors() {
        OWLLiteral lit = Literal(3);
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(DP, indA, lit);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeDataPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    void testSameIndividualAxiomAccessors() {
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getSameIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        Collection<OWLObject> equivalent = equivalent(ont.getSameIndividualAxioms(indA));
        assertTrue(equivalent.contains(indB));
        assertTrue(equivalent.contains(indC));
    }

    @Test
    void testDifferentIndividualsAxiomAccessors() {
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB, indC);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDifferentIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        Collection<OWLObject> different = different(ont.getDifferentIndividualAxioms(indA));
        assertTrue(different.contains(indB));
        assertTrue(different.contains(indC));
    }
}
