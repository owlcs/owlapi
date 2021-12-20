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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
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
        OWLAnnotation a = Annotation(ANNPROPS.propQ, query);
        OWLOntology o =
            o(AnnotationPropertyDomain(ANNPROPS.propP, query), SubClassOf(a, CLASSES.C, OWLThing()),
                AnnotationAssertion(ANNPROPS.propQ, query, query));
        long count = o.referencingAxioms(query).count();
        assertEquals(6, o.getAxiomCount());
        assertEquals(3, count);
    }

    @Test
    void testSubClassOfAxiomAccessors() {
        OWLSubClassOfAxiom ax = SubClassOf(CLASSES.A, CLASSES.B);
        OWLSubClassOfAxiom ax2 = SubClassOf(CLASSES.A, ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.B));
        OWLOntology ont = o(ax, ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(contains(ont.subClassAxiomsForSubClass(CLASSES.A), ax));
        assertTrue(contains(ont.subClassAxiomsForSuperClass(CLASSES.B), ax));
        assertTrue(contains(ont.axioms(CLASSES.A), ax));
        assertTrue(contains(sup(ont.axioms(subClassWithSub, CLASSES.A, INCLUDED)), CLASSES.B));
        assertTrue(contains(sub(ont.axioms(subClassWithSuper, CLASSES.B, INCLUDED)), CLASSES.A));
    }

    @Test
    void testEquivalentClassesAxiomAccessors() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(CLASSES.A, CLASSES.B, CLASSES.C,
            ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.D));
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentClassesAxioms(CLASSES.A), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(CLASSES.B), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(CLASSES.C), ax));
        assertTrue(contains(ont.axioms(CLASSES.A), ax));
        assertTrue(contains(ont.axioms(CLASSES.B), ax));
        assertTrue(contains(ont.axioms(CLASSES.C), ax));
    }

    @Test
    void testDisjointClassesAxiomAccessors() {
        OWLDisjointClassesAxiom ax = DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C,
            ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.D));
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointClassesAxioms(CLASSES.A), ax));
        assertTrue(contains(ont.disjointClassesAxioms(CLASSES.B), ax));
        assertTrue(contains(ont.disjointClassesAxioms(CLASSES.C), ax));
        assertTrue(contains(ont.axioms(CLASSES.A), ax));
        assertTrue(contains(ont.axioms(CLASSES.B), ax));
        assertTrue(contains(ont.axioms(CLASSES.C), ax));
    }

    @Test
    void testSubObjectPropertyOfAxiomAccessors() {
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(OBJPROPS.P, OBJPROPS.Q);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectSubPropertyAxiomsForSubProperty(OBJPROPS.P), ax));
        assertTrue(contains(ont.objectSubPropertyAxiomsForSuperProperty(OBJPROPS.Q), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
    }

    @Test
    void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = create();
        OWLEquivalentObjectPropertiesAxiom ax =
            EquivalentObjectProperties(OBJPROPS.P, OBJPROPS.Q, OBJPROPS.R);
        ont.addAxiom(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(OBJPROPS.Q), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(OBJPROPS.R), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.Q), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.R), ax));
    }

    @Test
    void testDisjointObjectPropertiesAxiomAccessors() {
        OWLDisjointObjectPropertiesAxiom ax =
            DisjointObjectProperties(OBJPROPS.P, OBJPROPS.Q, OBJPROPS.R);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(OBJPROPS.Q), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(OBJPROPS.R), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.Q), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.R), ax));
    }

    @Test
    void testObjectPropertyDomainAxiomAccessors() {
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(OBJPROPS.P, CLASSES.A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyDomainAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(contains(domain(ont.objectPropertyDomainAxioms(OBJPROPS.P)), CLASSES.A));
    }

    @Test
    void testObjectPropertyRangeAxiomAccessors() {
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(OBJPROPS.P, CLASSES.A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyRangeAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(contains(range(ont.objectPropertyRangeAxioms(OBJPROPS.P)), CLASSES.A));
    }

    @Test
    void testFunctionalObjectPropertyAxiomAccessors() {
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isFunctional(OBJPROPS.P, ont));
    }

    @Test
    void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.inverseFunctionalObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isInverseFunctional(OBJPROPS.P, ont));
    }

    @Test
    void testTransitiveObjectPropertyAxiomAccessors() {
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.transitiveObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isTransitive(OBJPROPS.P, ont));
    }

    @Test
    void testSymmetricObjectPropertyAxiomAccessors() {
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.symmetricObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isSymmetric(OBJPROPS.P, ont));
    }

    @Test
    void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.asymmetricObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isAsymmetric(OBJPROPS.P, ont));
    }

    @Test
    void testReflexiveObjectPropertyAxiomAccessors() {
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.reflexiveObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isReflexive(OBJPROPS.P, ont));
    }

    @Test
    void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(OBJPROPS.P);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.irreflexiveObjectPropertyAxioms(OBJPROPS.P), ax));
        assertTrue(contains(ont.axioms(OBJPROPS.P), ax));
        assertTrue(isIrreflexive(OBJPROPS.P, ont));
    }

    @Test
    void testSubDataPropertyOfAxiomAccessors() {
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(DATAPROPS.DP, DATAPROPS.DQ);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataSubPropertyAxiomsForSubProperty(DATAPROPS.DP), ax));
        assertTrue(contains(ont.dataSubPropertyAxiomsForSuperProperty(DATAPROPS.DQ), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
    }

    @Test
    void testEquivalentDataPropertiesAxiomAccessors() {
        OWLEquivalentDataPropertiesAxiom ax =
            EquivalentDataProperties(DATAPROPS.DP, DATAPROPS.DQ, DATAPROPS.DR);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DATAPROPS.DQ), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(DATAPROPS.DR), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DQ), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DR), ax));
    }

    @Test
    void testDisjointDataPropertiesAxiomAccessors() {
        OWLDisjointDataPropertiesAxiom ax =
            DisjointDataProperties(DATAPROPS.DP, DATAPROPS.DQ, DATAPROPS.DR);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DATAPROPS.DQ), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(DATAPROPS.DR), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DQ), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DR), ax));
    }

    @Test
    void testDataPropertyDomainAxiomAccessors() {
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(DATAPROPS.DP, CLASSES.A);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyDomainAxioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
        assertTrue(contains(domain(ont.dataPropertyDomainAxioms(DATAPROPS.DP)), CLASSES.A));
    }

    @Test
    void testDataPropertyRangeAxiomAccessors() {
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(DATAPROPS.DP, DATATYPES.DT);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyRangeAxioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
        assertTrue(contains(range(ont.dataPropertyRangeAxioms(DATAPROPS.DP)), DATATYPES.DT));
    }

    @Test
    void testFunctionalDataPropertyAxiomAccessors() {
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(DATAPROPS.DP);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalDataPropertyAxioms(DATAPROPS.DP), ax));
        assertTrue(contains(ont.axioms(DATAPROPS.DP), ax));
        assertTrue(isFunctional(DATAPROPS.DP, ont));
    }

    @Test
    void testClassAssertionAxiomAccessors() {
        OWLClassAssertionAxiom ax = ClassAssertion(CLASSES.A, INDIVIDUALS.indA);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.classAssertionAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.classAssertionAxioms(CLASSES.A), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
        assertTrue(
            contains(instances(ont.classAssertionAxioms(INDIVIDUALS.indA)), INDIVIDUALS.indA));
        assertTrue(contains(types(ont.classAssertionAxioms(INDIVIDUALS.indA)), CLASSES.A));
    }

    @Test
    void testObjectPropertyAxiomAccessors() {
        OWLObjectPropertyAssertionAxiom ax =
            ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.indA, INDIVIDUALS.indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyAssertionAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
    }

    @Test
    void testNegativeObjectPropertyAxiomAccessors() {
        OWLNegativeObjectPropertyAssertionAxiom ax =
            NegativeObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.indA, INDIVIDUALS.indB);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeObjectPropertyAssertionAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
    }

    @Test
    void testDataPropertyAxiomAccessors() {
        OWLDataPropertyAssertionAxiom ax =
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.indA, LITERALS.LIT_THREE);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyAssertionAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
    }

    @Test
    void testNegativeDataPropertyAxiomAccessors() {
        OWLNegativeDataPropertyAssertionAxiom ax =
            NegativeDataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.indA, LITERALS.LIT_THREE);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeDataPropertyAssertionAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
    }

    @Test
    void testSameIndividualAxiomAccessors() {
        OWLSameIndividualAxiom ax =
            SameIndividual(INDIVIDUALS.indA, INDIVIDUALS.indB, INDIVIDUALS.indC);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.sameIndividualAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.sameIndividualAxioms(INDIVIDUALS.indB), ax));
        assertTrue(contains(ont.sameIndividualAxioms(INDIVIDUALS.indC), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
        Collection<OWLObject> equivalent =
            asUnorderedSet(equivalent(ont.sameIndividualAxioms(INDIVIDUALS.indA)));
        assertTrue(equivalent.contains(INDIVIDUALS.indB));
        assertTrue(equivalent.contains(INDIVIDUALS.indC));
    }

    @Test
    void testDifferentIndividualsAxiomAccessors() {
        OWLDifferentIndividualsAxiom ax =
            DifferentIndividuals(INDIVIDUALS.indA, INDIVIDUALS.indB, INDIVIDUALS.indC);
        OWLOntology ont = o(ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.differentIndividualAxioms(INDIVIDUALS.indA), ax));
        assertTrue(contains(ont.differentIndividualAxioms(INDIVIDUALS.indB), ax));
        assertTrue(contains(ont.differentIndividualAxioms(INDIVIDUALS.indC), ax));
        assertTrue(contains(ont.axioms(INDIVIDUALS.indA), ax));
        Collection<OWLObject> different =
            asUnorderedSet(different(ont.differentIndividualAxioms(INDIVIDUALS.indA)));
        assertTrue(different.contains(INDIVIDUALS.indB));
        assertTrue(different.contains(INDIVIDUALS.indC));
    }
}
