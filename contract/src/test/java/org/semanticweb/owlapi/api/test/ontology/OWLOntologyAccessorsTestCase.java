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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.util.Collection;

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
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
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

    private static void performAxiomTests(OWLOntology ont, OWLAxiom... axioms) {
        assertEquals(ont.getLogicalAxiomCount(), axioms.length);
        for (OWLAxiom ax : axioms) {
            assertTrue(contains(ont.axioms(), ax));
            if (ax.isLogicalAxiom()) {
                assertTrue(contains(ont.logicalAxioms(), ax));
            }
            AxiomType<?> axiomType = ax.getAxiomType();
            assertTrue(contains(ont.axioms(axiomType), ax));
            assertTrue(contains(ont.axioms(axiomType, INCLUDED), ax));
            assertEquals(ont.getAxiomCount(axiomType), axioms.length);
            assertEquals(ont.getAxiomCount(axiomType, INCLUDED), axioms.length);
            ax.signature().forEach(entity -> {
                assertTrue(contains(ont.referencingAxioms(entity), ax));
                assertTrue(contains(ont.signature(), entity));
            });
        }
    }

    @Test
    void shouldFindExpectedIRIOccurrences() {
        IRI query = iri("urn:test:", "someIRI");
        OWLAnnotation a = Annotation(propQ, query);
        OWLOntology o = o(AnnotationPropertyDomain(propP, query), SubClassOf(a, C, OWLThing()),
            AnnotationAssertion(propQ, query, query));
        long count = o.referencingAxioms(query).count();
        assertEquals(6, o.getAxiomCount());
        assertEquals(3, count);
    }

    @Test
    void testSubClassOfAxiomAccessors() {
        OWLSubClassOfAxiom ax = SubClassOf(A, B);
        OWLSubClassOfAxiom ax2 = SubClassOf(A, ObjectSomeValuesFrom(P, B));
        OWLOntology ont = o(ax, ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(contains(ont.subClassAxiomsForSubClass(A), ax));
        assertTrue(contains(ont.subClassAxiomsForSuperClass(B), ax));
        assertTrue(contains(ont.axioms(A), ax));
        assertTrue(contains(sup(ont.axioms(subClassWithSub, A, INCLUDED)), B));
        assertTrue(contains(sub(ont.axioms(subClassWithSuper, B, INCLUDED)), A));
    }

    @Test
    void testEquivalentClassesAxiomAccessors() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        OWLOntology ont = o(ax);
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
        OWLDisjointClassesAxiom ax = DisjointClasses(A, B, C, ObjectSomeValuesFrom(P, D));
        OWLOntology ont = o(ax);
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
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(P, Q);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectSubPropertyAxiomsForSubProperty(P), ax));
        assertTrue(contains(ont.objectSubPropertyAxiomsForSuperProperty(Q), ax));
        assertTrue(contains(ont.axioms(P), ax));
    }

    @Test
    void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = create();
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
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(P, Q, R);
        OWLOntology ont = o(ax);
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
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(P, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyDomainAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(domain(ont.objectPropertyDomainAxioms(P)), A));
    }

    @Test
    void testObjectPropertyRangeAxiomAccessors() {
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(P, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyRangeAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(contains(range(ont.objectPropertyRangeAxioms(P)), A));
    }

    @Test
    void testFunctionalObjectPropertyAxiomAccessors() {
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isFunctional(P, ont));
    }

    @Test
    void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.inverseFunctionalObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isInverseFunctional(P, ont));
    }

    @Test
    void testTransitiveObjectPropertyAxiomAccessors() {
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.transitiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isTransitive(P, ont));
    }

    @Test
    void testSymmetricObjectPropertyAxiomAccessors() {
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.symmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isSymmetric(P, ont));
    }

    @Test
    void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.asymmetricObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isAsymmetric(P, ont));
    }

    @Test
    void testReflexiveObjectPropertyAxiomAccessors() {
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.reflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isReflexive(P, ont));
    }

    @Test
    void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.irreflexiveObjectPropertyAxioms(P), ax));
        assertTrue(contains(ont.axioms(P), ax));
        assertTrue(isIrreflexive(P, ont));
    }

    @Test
    void testSubDataPropertyOfAxiomAccessors() {
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(DP, DQ);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataSubPropertyAxiomsForSubProperty(DP), ax));
        assertTrue(contains(ont.dataSubPropertyAxiomsForSuperProperty(DQ), ax));
        assertTrue(contains(ont.axioms(DP), ax));
    }

    @Test
    void testEquivalentDataPropertiesAxiomAccessors() {
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(DP, DQ, DR);
        OWLOntology ont = o(ax);
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
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(DP, DQ, DR);
        OWLOntology ont = o(ax);
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
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(DP, A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyDomainAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(domain(ont.dataPropertyDomainAxioms(DP)), A));
    }

    @Test
    void testDataPropertyRangeAxiomAccessors() {
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DP, DT);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyRangeAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(contains(range(ont.dataPropertyRangeAxioms(DP)), DT));
    }

    @Test
    void testFunctionalDataPropertyAxiomAccessors() {
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(DP);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalDataPropertyAxioms(DP), ax));
        assertTrue(contains(ont.axioms(DP), ax));
        assertTrue(isFunctional(DP, ont));
    }

    @Test
    void testClassAssertionAxiomAccessors() {
        OWLClassAssertionAxiom ax = ClassAssertion(A, indA);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.classAssertionAxioms(indA), ax));
        assertTrue(contains(ont.classAssertionAxioms(A), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        assertTrue(contains(instances(ont.classAssertionAxioms(indA)), indA));
        assertTrue(contains(types(ont.classAssertionAxioms(indA)), A));
    }

    @Test
    void testObjectPropertyAxiomAccessors() {
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(P, indA, indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testNegativeObjectPropertyAxiomAccessors() {
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(P, indA, indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeObjectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testDataPropertyAxiomAccessors() {
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(DP, indA, LIT_THREE);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testNegativeDataPropertyAxiomAccessors() {
        OWLNegativeDataPropertyAssertionAxiom ax =
            NegativeDataPropertyAssertion(DP, indA, LIT_THREE);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeDataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    void testSameIndividualAxiomAccessors() {
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        OWLOntology ont = o(ax);
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
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB, indC);
        OWLOntology ont = o(ax);
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
