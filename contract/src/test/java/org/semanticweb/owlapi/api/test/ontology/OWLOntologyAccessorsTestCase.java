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
import static org.semanticweb.owlapi.model.Imports.*;
import static org.semanticweb.owlapi.search.Filters.*;
import static org.semanticweb.owlapi.search.Searcher.*;

import java.util.Collection;

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
import org.semanticweb.owlapi.model.OWLEntity;
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

    private void performAxiomTests(OWLOntology ont, OWLAxiom... axioms) {
        assertEquals(ont.getAxiomCount(), axioms.length);
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
                assertTrue(ont.getReferencingAxioms(entity, EXCLUDED).contains(
                        ax));
                assertTrue(ont.getSignature().contains(entity));
            }
        }
    }

    @Test
    public void testSubClassOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = Class(getIRI("A"));
        OWLClass clsB = Class(getIRI("B"));
        OWLObjectProperty prop = ObjectProperty(getIRI("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubClassOfAxiom ax = SubClassOf(clsA, clsB);
        man.addAxiom(ont, ax);
        OWLSubClassOfAxiom ax2 = SubClassOf(clsA,
                ObjectSomeValuesFrom(prop, clsB));
        man.addAxiom(ont, ax2);
        performAxiomTests(ont, ax, ax2);
        assertTrue(ont.getSubClassAxiomsForSubClass(clsA).contains(ax));
        assertTrue(ont.getSubClassAxiomsForSuperClass(clsB).contains(ax));
        assertTrue(ont.getAxioms(clsA, EXCLUDED).contains(ax));
        assertTrue(sup(ont.filterAxioms(subClassWithSub, clsA, INCLUDED))
                .contains(clsB));
        assertTrue(sub(ont.filterAxioms(subClassWithSuper, clsB, INCLUDED))
                .contains(clsA));
    }

    @Test
    public void testEquivalentClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = Class(getIRI("A"));
        OWLClass clsB = Class(getIRI("B"));
        OWLClass clsC = Class(getIRI("C"));
        OWLClass clsD = Class(getIRI("D"));
        OWLObjectProperty prop = ObjectProperty(getIRI("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, clsB, clsC,
                ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentClassesAxioms(clsA).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(clsB).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(clsC).contains(ax));
        assertTrue(ont.getAxioms(clsA, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(clsB, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(clsC, EXCLUDED).contains(ax));
    }

    @Test
    public void testDisjointClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = Class(getIRI("A"));
        OWLClass clsB = Class(getIRI("B"));
        OWLClass clsC = Class(getIRI("C"));
        OWLClass clsD = Class(getIRI("D"));
        OWLObjectProperty prop = ObjectProperty(getIRI("prop"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointClassesAxiom ax = DisjointClasses(clsA, clsB, clsC,
                ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointClassesAxioms(clsA).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(clsB).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(clsC).contains(ax));
        assertTrue(ont.getAxioms(clsA, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(clsB, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(clsC, EXCLUDED).contains(ax));
    }

    @Test
    public void testSubObjectPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLObjectProperty propQ = ObjectProperty(getIRI("q"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectSubPropertyAxiomsForSubProperty(propP)
                .contains(ax));
        assertTrue(ont.getObjectSubPropertyAxiomsForSuperProperty(propQ)
                .contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
    }

    @Test
    public void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLObjectProperty propQ = ObjectProperty(getIRI("q"));
        OWLObjectProperty propR = ObjectProperty(getIRI("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentObjectPropertiesAxiom ax = EquivalentObjectProperties(
                propP, propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propR, EXCLUDED).contains(ax));
    }

    @Test
    public void testDisjointObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLObjectProperty propQ = ObjectProperty(getIRI("q"));
        OWLObjectProperty propR = ObjectProperty(getIRI("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(propP,
                propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointObjectPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propR, EXCLUDED).contains(ax));
    }

    @Test
    public void testObjectPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyDomainAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(domain(ont.getObjectPropertyDomainAxioms(propP)).contains(
                clsA));
    }

    @Test
    public void testObjectPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyRangeAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(range(ont.getObjectPropertyRangeAxioms(propP))
                .contains(clsA));
    }

    @Test
    public void testFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getFunctionalObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isFunctional(ont, propP));
    }

    @Test
    public void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getInverseFunctionalObjectPropertyAxioms(propP)
                .contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isInverseFunctional(ont, propP));
    }

    @Test
    public void testTransitiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getTransitiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isTransitive(ont, propP));
    }

    @Test
    public void testSymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getSymmetricObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isSymmetric(ont, propP));
    }

    @Test
    public void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getAsymmetricObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isAsymmetric(ont, propP));
    }

    @Test
    public void testReflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getReflexiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isReflexive(ont, propP));
    }

    @Test
    public void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = ObjectProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getIrreflexiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isIrreflexive(ont, propP));
    }

    @Test
    public void testSubDataPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLDataProperty propQ = DataProperty(getIRI("q"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataSubPropertyAxiomsForSubProperty(propP).contains(
                ax));
        assertTrue(ont.getDataSubPropertyAxiomsForSuperProperty(propQ)
                .contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
    }

    @Test
    public void testEquivalentDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLDataProperty propQ = DataProperty(getIRI("q"));
        OWLDataProperty propR = DataProperty(getIRI("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(propP,
                propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getEquivalentDataPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propR, EXCLUDED).contains(ax));
    }

    @Test
    public void testDisjointDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLDataProperty propQ = DataProperty(getIRI("q"));
        OWLDataProperty propR = DataProperty(getIRI("r"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(propP,
                propQ, propR);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDisjointDataPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propQ, EXCLUDED).contains(ax));
        assertTrue(ont.getAxioms(propR, EXCLUDED).contains(ax));
    }

    @Test
    public void testDataPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLClass clsA = Class(getIRI("ClsA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyDomainAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(domain(ont.getDataPropertyDomainAxioms(propP))
                .contains(clsA));
    }

    @Test
    public void testDataPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLDatatype dt = Datatype(getIRI("dt"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(propP, dt);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyRangeAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(range(ont.getDataPropertyRangeAxioms(propP)).contains(dt));
    }

    @Test
    public void testFunctionalDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = DataProperty(getIRI("p"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(propP);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getFunctionalDataPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP, EXCLUDED).contains(ax));
        assertTrue(isFunctional(ont, propP));
    }

    @Test
    public void testClassAssertionAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = Class(getIRI("clsA"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLClassAssertionAxiom ax = ClassAssertion(clsA, indA);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getClassAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getClassAssertionAxioms(clsA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        assertTrue(instances(ont.getClassAssertionAxioms(indA)).contains(indA));
        assertTrue(types(ont.getClassAssertionAxioms(indA)).contains(clsA));
    }

    @Test
    public void testObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty prop = ObjectProperty(getIRI("prop"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indB"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(prop,
                indA, indB);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    public void testNegativeObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty prop = ObjectProperty(getIRI("prop"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indB"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(
                prop, indA, indB);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeObjectPropertyAssertionAxioms(indA).contains(
                ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    public void testDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty prop = DataProperty(getIRI("prop"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLLiteral lit = Literal(3);
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(prop, indA,
                lit);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    public void testNegativeDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty prop = DataProperty(getIRI("prop"));
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLLiteral lit = Literal(3);
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(
                prop, indA, lit);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeDataPropertyAssertionAxioms(indA)
                .contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
    }

    @Test
    public void testSameIndividualAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indB"));
        OWLNamedIndividual indC = NamedIndividual(getIRI("indC"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getSameIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        Collection<OWLObject> equivalent = equivalent(ont
                .getSameIndividualAxioms(indA));
        assertTrue(equivalent.contains(indB));
        assertTrue(equivalent.contains(indC));
    }

    @Test
    public void testDifferentIndividualsAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLNamedIndividual indA = NamedIndividual(getIRI("indA"));
        OWLNamedIndividual indB = NamedIndividual(getIRI("indB"));
        OWLNamedIndividual indC = NamedIndividual(getIRI("indC"));
        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB, indC);
        man.addAxiom(ont, ax);
        performAxiomTests(ont, ax);
        assertTrue(ont.getDifferentIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA, EXCLUDED).contains(ax));
        Collection<OWLObject> different = different(ont
                .getDifferentIndividualAxioms(indA));
        assertTrue(different.contains(indB));
        assertTrue(different.contains(indC));
    }
}
