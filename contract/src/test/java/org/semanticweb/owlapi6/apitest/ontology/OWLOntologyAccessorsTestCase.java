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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.D;
import static org.semanticweb.owlapi6.apitest.TestEntities.DP;
import static org.semanticweb.owlapi6.apitest.TestEntities.DQ;
import static org.semanticweb.owlapi6.apitest.TestEntities.DR;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.apitest.TestEntities.Q;
import static org.semanticweb.owlapi6.apitest.TestEntities.R;
import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi6.search.Filters.subClassWithSub;
import static org.semanticweb.owlapi6.search.Filters.subClassWithSuper;
import static org.semanticweb.owlapi6.search.Searcher.different;
import static org.semanticweb.owlapi6.search.Searcher.domain;
import static org.semanticweb.owlapi6.search.Searcher.equivalent;
import static org.semanticweb.owlapi6.search.Searcher.instances;
import static org.semanticweb.owlapi6.search.Searcher.isAsymmetric;
import static org.semanticweb.owlapi6.search.Searcher.isFunctional;
import static org.semanticweb.owlapi6.search.Searcher.isInverseFunctional;
import static org.semanticweb.owlapi6.search.Searcher.isIrreflexive;
import static org.semanticweb.owlapi6.search.Searcher.isReflexive;
import static org.semanticweb.owlapi6.search.Searcher.isSymmetric;
import static org.semanticweb.owlapi6.search.Searcher.isTransitive;
import static org.semanticweb.owlapi6.search.Searcher.range;
import static org.semanticweb.owlapi6.search.Searcher.sub;
import static org.semanticweb.owlapi6.search.Searcher.sup;
import static org.semanticweb.owlapi6.search.Searcher.types;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public class OWLOntologyAccessorsTestCase extends TestBase {

    private static final OWLNamedIndividual idnC = NamedIndividual(iri("indC"));
    private static final OWLNamedIndividual idnB = NamedIndividual(iri("indB"));
    private static final OWLNamedIndividual idnA = NamedIndividual(iri("indA"));

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
    public void shouldFindExpectedIRIOccurrences() {
        OWLOntology o = getOWLOntology();
        IRI query = df.getIRI("urn:test:someIRI");
        OWLAnnotationProperty ap1 = df.getOWLAnnotationProperty("urn:test:AP1");
        OWLAnnotationProperty ap2 = df.getOWLAnnotationProperty("urn:test:AP2");
        o.add(df.getOWLAnnotationPropertyDomainAxiom(ap1, query));
        OWLAnnotation a = df.getOWLAnnotation(ap2, query);
        OWLClass c = df.getOWLClass("urn:test:C");
        o.add(df.getOWLSubClassOfAxiom(c, df.getOWLThing(), Collections.singletonList(a)));
        o.add(df.getOWLAnnotationAssertionAxiom(df.getIRI("urn:test:otherIRI"), a));
        long count = o.referencingAxioms(query).count();
        assertEquals(3, o.getAxiomCount());
        assertEquals(3, count);
    }

    @Test
    public void testSubClassOfAxiomAccessors() {
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
    public void testEquivalentClassesAxiomAccessors() {
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
    public void testDisjointClassesAxiomAccessors() {
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
    public void testSubObjectPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(P, Q);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectSubPropertyAxiomsForSubProperty(P), ax));
        assertTrue(contains(ont.objectSubPropertyAxiomsForSuperProperty(Q), ax));
        assertTrue(contains(ont.axioms(P), ax));
    }

    @Test
    public void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
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
    public void testDisjointObjectPropertiesAxiomAccessors() {
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
    public void testObjectPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(P, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyDomainAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(domain(ont.objectPropertyDomainAxioms(P)), A));
    }

    @Test
    public void testObjectPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(P, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyRangeAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(range(ont.objectPropertyRangeAxioms(P)), A));
    }

    @Test
    public void testFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isFunctional(P, ont));
    }

    @Test
    public void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.inverseFunctionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isInverseFunctional(P, ont));
    }

    @Test
    public void testTransitiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.transitiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isTransitive(P, ont));
    }

    @Test
    public void testSymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.symmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isSymmetric(P, ont));
    }

    @Test
    public void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.asymmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isAsymmetric(P, ont));
    }

    @Test
    public void testReflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.reflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isReflexive(P, ont));
    }

    @Test
    public void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(P);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.irreflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isIrreflexive(P, ont));
    }

    @Test
    public void testSubDataPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(DP, DQ);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataSubPropertyAxiomsForSubProperty(DP), ax));
        assertTrue(contains(ont.dataSubPropertyAxiomsForSuperProperty(DQ), ax));
        assertTrue(contains(ont.axioms(DP), ax));
    }

    @Test
    public void testEquivalentDataPropertiesAxiomAccessors() {
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
    public void testDisjointDataPropertiesAxiomAccessors() {
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
    public void testDataPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(DP, A);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyDomainAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(domain(ont.dataPropertyDomainAxioms(DP)), A));
    }

    @Test
    public void testDataPropertyRangeAxiomAccessors() {
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
    public void testFunctionalDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(DP);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalDataPropertyAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(isFunctional(DP, ont));
    }

    @Test
    public void testClassAssertionAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLClassAssertionAxiom ax = ClassAssertion(A, idnA);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.classAssertionAxioms(idnA), ax));
        assertTrue(contains(ont.classAssertionAxioms(A), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
        assertTrue(contains(instances(ont.classAssertionAxioms(idnA)), idnA));
        assertTrue(contains(types(ont.classAssertionAxioms(idnA)), A));
    }

    @Test
    public void testObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(P, idnA, idnB);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyAssertionAxioms(idnA), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
    }

    @Test
    public void testNegativeObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(P, idnA, idnB);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeObjectPropertyAssertionAxioms(idnA), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
    }

    @Test
    public void testDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLLiteral lit = Literal(3);
        ont.getOWLOntologyManager();
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(DP, idnA, lit);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyAssertionAxioms(idnA), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
    }

    @Test
    public void testNegativeDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLLiteral lit = Literal(3);
        ont.getOWLOntologyManager();
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(DP, idnA, lit);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeDataPropertyAssertionAxioms(idnA), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
    }

    @Test
    public void testSameIndividualAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLSameIndividualAxiom ax = SameIndividual(idnA, idnB, idnC);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.sameIndividualAxioms(idnA), ax));
        assertTrue(contains(ont.sameIndividualAxioms(idnB), ax));
        assertTrue(contains(ont.sameIndividualAxioms(idnC), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
        Collection<OWLObject> equivalent =
            asUnorderedSet(equivalent(ont.sameIndividualAxioms(idnA)));
        assertTrue(equivalent.contains(idnB));
        assertTrue(equivalent.contains(idnC));
    }

    @Test
    public void testDifferentIndividualsAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        ont.getOWLOntologyManager();
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(idnA, idnB, idnC);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.differentIndividualAxioms(idnA), ax));
        assertTrue(contains(ont.differentIndividualAxioms(idnB), ax));
        assertTrue(contains(ont.differentIndividualAxioms(idnC), ax));
        assertTrue(contains(ont.axioms(idnA), ax));
        Collection<OWLObject> different =
            asUnorderedSet(different(ont.differentIndividualAxioms(idnA)));
        assertTrue(different.contains(idnB));
        assertTrue(different.contains(idnC));
    }
}
