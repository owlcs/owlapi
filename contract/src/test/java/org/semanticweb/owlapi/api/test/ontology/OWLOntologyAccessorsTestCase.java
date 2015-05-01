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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.search.EntitySearcher.*;
import static org.semanticweb.owlapi.search.Filters.*;
import static org.semanticweb.owlapi.search.Searcher.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
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
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public class OWLOntologyAccessorsTestCase extends TestBase {

    private static void performAxiomTests(@Nonnull OWLOntology ont,
        @Nonnull OWLAxiom... axioms) {
        assertEquals(ont.getAxiomCount(), axioms.length);
        for (OWLAxiom ax : axioms) {
            assertTrue(contains(ont.axioms(), ax));
            if (ax.isLogicalAxiom()) {
                assertTrue(contains(ont.logicalAxioms(), ax));
            }
            assertEquals(ont.getLogicalAxiomCount(), axioms.length);
            AxiomType<?> axiomType = ax.getAxiomType();
            assertTrue(ont.getAxioms(axiomType).contains(ax));
            assertTrue(ont.getAxioms(axiomType, INCLUDED).contains(ax));
            assertEquals(ont.getAxiomCount(axiomType), axioms.length);
            assertEquals(ont.getAxiomCount(axiomType, INCLUDED), axioms.length);
            ax.signature().forEach(e -> {
                assertTrue(contains(ont.referencingAxioms(e), ax));
                assertTrue(contains(ont.signature(), e));
            } );
        }
    }

    @Test
    public void testSubClassOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubClassOfAxiom ax = SubClassOf(clsA, clsB);
        man.addAxiom(ont, ax);
        OWLSubClassOfAxiom ax2 = SubClassOf(clsA,
            ObjectSomeValuesFrom(prop, clsB));
        man.addAxiom(ont, ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(contains(ont.subClassAxiomsForSubClass(clsA), ax));
        assertTrue(contains(ont.subClassAxiomsForSuperClass(clsB), ax));
        assertTrue(contains(ont.axioms(clsA), ax));
        assertTrue(
            contains(sup(ont.axioms(subClassWithSub, clsA, INCLUDED)), clsB));
        assertTrue(
            contains(sub(ont.axioms(subClassWithSuper, clsB, INCLUDED)), clsA));
    }

    @Test
    public void testEquivalentClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        OWLClass clsD = Class(iri("D"));
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, clsB, clsC,
            ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentClassesAxioms(clsA), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(clsB), ax));
        assertTrue(contains(ont.equivalentClassesAxioms(clsC), ax));
        assertTrue(contains(ont.axioms(clsA), ax));
        assertTrue(contains(ont.axioms(clsB), ax));
        assertTrue(contains(ont.axioms(clsC), ax));
    }

    @Test
    public void testDisjointClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        OWLClass clsD = Class(iri("D"));
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointClassesAxiom ax = DisjointClasses(clsA, clsB, clsC,
            ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointClassesAxioms(clsA), ax));
        assertTrue(contains(ont.disjointClassesAxioms(clsB), ax));
        assertTrue(contains(ont.disjointClassesAxioms(clsC), ax));
        assertTrue(contains(ont.axioms(clsA), ax));
        assertTrue(contains(ont.axioms(clsB), ax));
        assertTrue(contains(ont.axioms(clsC), ax));
    }

    @Test
    public void testSubObjectPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLObjectProperty propQ = ObjectProperty(iri("q"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(
            contains(ont.objectSubPropertyAxiomsForSubProperty(propP), ax));
        assertTrue(
            contains(ont.objectSubPropertyAxiomsForSuperProperty(propQ), ax));
        assertTrue(contains(ont.axioms(propP), ax));
    }

    @Test
    public void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLObjectProperty propQ = ObjectProperty(iri("q"));
        OWLObjectProperty propR = ObjectProperty(iri("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentObjectPropertiesAxiom ax = EquivalentObjectProperties(
            propP, propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(propP), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(propQ), ax));
        assertTrue(contains(ont.equivalentObjectPropertiesAxioms(propR), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(ont.axioms(propQ), ax));
        assertTrue(contains(ont.axioms(propR), ax));
    }

    @Test
    public void testDisjointObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLObjectProperty propQ = ObjectProperty(iri("q"));
        OWLObjectProperty propR = ObjectProperty(iri("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(propP,
            propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(propP), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(propQ), ax));
        assertTrue(contains(ont.disjointObjectPropertiesAxioms(propR), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(ont.axioms(propQ), ax));
        assertTrue(contains(ont.axioms(propR), ax));
    }

    @Test
    public void testObjectPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClass clsA = Class(iri("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyDomainAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(
            contains(domain(ont.objectPropertyDomainAxioms(propP)), clsA));
    }

    @Test
    public void testObjectPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClass clsA = Class(iri("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyRangeAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(range(ont.objectPropertyRangeAxioms(propP)), clsA));
    }

    @Test
    public void testFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isFunctional(propP, ont));
    }

    @Test
    public void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(
            propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(
            contains(ont.inverseFunctionalObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isInverseFunctional(propP, ont));
    }

    @Test
    public void testTransitiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.transitiveObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isTransitive(propP, ont));
    }

    @Test
    public void testSymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.symmetricObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isSymmetric(propP, ont));
    }

    @Test
    public void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.asymmetricObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isAsymmetric(propP, ont));
    }

    @Test
    public void testReflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.reflexiveObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isReflexive(propP, ont));
    }

    @Test
    public void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.irreflexiveObjectPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isIrreflexive(propP, ont));
    }

    @Test
    public void testSubDataPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLDataProperty propQ = DataProperty(iri("q"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(
            contains(ont.dataSubPropertyAxiomsForSubProperty(propP), ax));
        assertTrue(
            contains(ont.dataSubPropertyAxiomsForSuperProperty(propQ), ax));
        assertTrue(contains(ont.axioms(propP), ax));
    }

    @Test
    public void testEquivalentDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLDataProperty propQ = DataProperty(iri("q"));
        OWLDataProperty propR = DataProperty(iri("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(propP,
            propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(propP), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(propQ), ax));
        assertTrue(contains(ont.equivalentDataPropertiesAxioms(propR), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(ont.axioms(propQ), ax));
        assertTrue(contains(ont.axioms(propR), ax));
    }

    @Test
    public void testDisjointDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLDataProperty propQ = DataProperty(iri("q"));
        OWLDataProperty propR = DataProperty(iri("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(propP, propQ,
            propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.disjointDataPropertiesAxioms(propP), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(propQ), ax));
        assertTrue(contains(ont.disjointDataPropertiesAxioms(propR), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(ont.axioms(propQ), ax));
        assertTrue(contains(ont.axioms(propR), ax));
    }

    @Test
    public void testDataPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLClass clsA = Class(iri("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyDomainAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(domain(ont.dataPropertyDomainAxioms(propP)), clsA));
    }

    @Test
    public void testDataPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLDatatype dt = Datatype(iri("dt"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(propP, dt);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyRangeAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(contains(range(ont.dataPropertyRangeAxioms(propP)), dt));
    }

    @Test
    public void testFunctionalDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty propP = DataProperty(iri("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.functionalDataPropertyAxioms(propP), ax));
        assertTrue(contains(ont.axioms(propP), ax));
        assertTrue(isFunctional(propP, ont));
    }

    @Test
    public void testClassAssertionAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLClass clsA = Class(iri("clsA"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLClassAssertionAxiom ax = ClassAssertion(clsA, indA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.classAssertionAxioms(indA), ax));
        assertTrue(contains(ont.classAssertionAxioms(clsA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        assertTrue(contains(instances(ont.classAssertionAxioms(indA)), indA));
        assertTrue(contains(types(ont.classAssertionAxioms(indA)), clsA));
    }

    @Test
    public void testObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indB"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(prop, indA,
            indB);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.objectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    public void testNegativeObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indB"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(
            prop, indA, indB);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(
            contains(ont.negativeObjectPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    public void testDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty prop = DataProperty(iri("prop"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLLiteral lit = Literal(3);
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(prop, indA,
            lit);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.dataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    public void testNegativeDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLDataProperty prop = DataProperty(iri("prop"));
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLLiteral lit = Literal(3);
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(
            prop, indA, lit);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.negativeDataPropertyAssertionAxioms(indA), ax));
        assertTrue(contains(ont.axioms(indA), ax));
    }

    @Test
    public void testSameIndividualAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indB"));
        OWLNamedIndividual indC = NamedIndividual(iri("indC"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.sameIndividualAxioms(indA), ax));
        assertTrue(contains(ont.sameIndividualAxioms(indB), ax));
        assertTrue(contains(ont.sameIndividualAxioms(indC), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        Collection<OWLObject> equivalent = asSet(
            equivalent(ont.sameIndividualAxioms(indA)));
        assertTrue(equivalent.contains(indB));
        assertTrue(equivalent.contains(indC));
    }

    @Test
    public void testDifferentIndividualsAxiomAccessors() {
        OWLOntology ont = getOWLOntology();
        OWLNamedIndividual indA = NamedIndividual(iri("indA"));
        OWLNamedIndividual indB = NamedIndividual(iri("indB"));
        OWLNamedIndividual indC = NamedIndividual(iri("indC"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB,
            indC);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(contains(ont.differentIndividualAxioms(indA), ax));
        assertTrue(contains(ont.differentIndividualAxioms(indB), ax));
        assertTrue(contains(ont.differentIndividualAxioms(indC), ax));
        assertTrue(contains(ont.axioms(indA), ax));
        Collection<OWLObject> different = asSet(
            different(ont.differentIndividualAxioms(indA)));
        assertTrue(different.contains(indB));
        assertTrue(different.contains(indC));
    }
}
