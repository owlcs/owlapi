/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 18-Jul-2008<br><br>
 */
public class OWLOntologyAccessorsTestCase extends AbstractOWLAPITestCase {


    private void performAxiomTests(OWLOntology ont, OWLAxiom ... axioms) {



        assertEquals(ont.getAxiomCount(), axioms.length);

        for (OWLAxiom ax : axioms) {
            assertTrue(ont.getAxioms().contains(ax));

            if (ax.isLogicalAxiom()) {
                assertTrue(ont.getLogicalAxioms().contains(ax));
            }

            assertEquals(ont.getLogicalAxiomCount(), axioms.length);

            AxiomType<?> axiomType = ax.getAxiomType();

            assertTrue(ont.getAxioms(axiomType).contains(ax));
            assertTrue(ont.getAxioms(axiomType, true).contains(ax));
            assertEquals(ont.getAxiomCount(axiomType), axioms.length);
            assertEquals(ont.getAxiomCount(axiomType, true), axioms.length);

            for (OWLEntity entity : ax.getSignature()) {
                assertTrue(ont.getReferencingAxioms(entity).contains(ax));
                assertTrue(ont.getSignature().contains(entity));
            }
        }
    }


    public void testSubClassOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty prop = getOWLObjectProperty("prop");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubClassOfAxiom ax = SubClassOf(clsA, clsB);
        man.addAxiom(ont, ax);
        OWLSubClassOfAxiom ax2 = SubClassOf(clsA, ObjectSomeValuesFrom(prop, clsB));
        man.addAxiom(ont, ax2);
        
        performAxiomTests(ont, ax, ax2);

        assertTrue(ont.getSubClassAxiomsForSubClass(clsA).contains(ax));
        assertTrue(ont.getSubClassAxiomsForSuperClass(clsB).contains(ax));
        assertTrue(ont.getAxioms(clsA).contains(ax));
        assertTrue(clsA.getSuperClasses(ont).contains(clsB));
        assertTrue(clsB.getSubClasses(ont).contains(clsA));
    }

    public void testEquivalentClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        OWLClass clsD = getOWLClass("D");
        OWLObjectProperty prop = getOWLObjectProperty("prop");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, clsB, clsC, ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getEquivalentClassesAxioms(clsA).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(clsB).contains(ax));
        assertTrue(ont.getEquivalentClassesAxioms(clsC).contains(ax));
        assertTrue(ont.getAxioms(clsA).contains(ax));
        assertTrue(ont.getAxioms(clsB).contains(ax));
        assertTrue(ont.getAxioms(clsC).contains(ax));
    }

    public void testDisjointClassesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        OWLClass clsD = getOWLClass("D");
        OWLObjectProperty prop = getOWLObjectProperty("prop");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointClassesAxiom ax = DisjointClasses(clsA, clsB, clsC, ObjectSomeValuesFrom(prop, clsD));
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDisjointClassesAxioms(clsA).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(clsB).contains(ax));
        assertTrue(ont.getDisjointClassesAxioms(clsC).contains(ax));
        assertTrue(ont.getAxioms(clsA).contains(ax));
        assertTrue(ont.getAxioms(clsB).contains(ax));
        assertTrue(ont.getAxioms(clsC).contains(ax));
    }

    public void testSubObjectPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubObjectPropertyOfAxiom ax = SubObjectPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getObjectSubPropertyAxiomsForSubProperty(propP).contains(ax));
        assertTrue(ont.getObjectSubPropertyAxiomsForSuperProperty(propQ).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
    }

    public void testEquivalentObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");
        OWLObjectProperty propR = getOWLObjectProperty("r");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentObjectPropertiesAxiom ax = EquivalentObjectProperties(propP, propQ, propR);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getEquivalentObjectPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propQ).contains(ax));
        assertTrue(ont.getAxioms(propR).contains(ax));
    }

    public void testDisjointObjectPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLObjectProperty propQ = getOWLObjectProperty("q");
        OWLObjectProperty propR = getOWLObjectProperty("r");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointObjectPropertiesAxiom ax = DisjointObjectProperties(propP, propQ, propR);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDisjointObjectPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getDisjointObjectPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propQ).contains(ax));
        assertTrue(ont.getAxioms(propR).contains(ax));
    }

    public void testObjectPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClass clsA = getOWLClass("ClsA");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyDomainAxiom ax = ObjectPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getObjectPropertyDomainAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.getDomains(ont).contains(clsA));

    }

    public void testObjectPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClass clsA = getOWLClass("ClsA");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyRangeAxiom ax = ObjectPropertyRange(propP, clsA);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getObjectPropertyRangeAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.getRanges(ont).contains(clsA));
    }

    public void testFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalObjectPropertyAxiom ax = FunctionalObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getFunctionalObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isFunctional(ont));
    }

    public void testInverseFunctionalObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLInverseFunctionalObjectPropertyAxiom ax = InverseFunctionalObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getInverseFunctionalObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isInverseFunctional(ont));
    }

    public void testTransitiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLTransitiveObjectPropertyAxiom ax = TransitiveObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getTransitiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isTransitive(ont));
    }

    public void testSymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSymmetricObjectPropertyAxiom ax = SymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getSymmetricObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isSymmetric(ont));
    }

    public void testAsymmetricObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLAsymmetricObjectPropertyAxiom ax = AsymmetricObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getAsymmetricObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isAsymmetric(ont));
    }

    public void testReflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLReflexiveObjectPropertyAxiom ax = ReflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getReflexiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isReflexive(ont));
    }

    public void testIrreflexiveObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty propP = getOWLObjectProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLIrreflexiveObjectPropertyAxiom ax = IrreflexiveObjectProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getIrreflexiveObjectPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isIrreflexive(ont));
    }

    public void testSubDataPropertyOfAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLDataProperty propQ = getOWLDataProperty("q");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSubDataPropertyOfAxiom ax = SubDataPropertyOf(propP, propQ);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDataSubPropertyAxiomsForSubProperty(propP).contains(ax));
        assertTrue(ont.getDataSubPropertyAxiomsForSuperProperty(propQ).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
    }

    public void testEquivalentDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLDataProperty propQ = getOWLDataProperty("q");
        OWLDataProperty propR = getOWLDataProperty("r");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLEquivalentDataPropertiesAxiom ax = EquivalentDataProperties(propP, propQ, propR);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getEquivalentDataPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getEquivalentDataPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propQ).contains(ax));
        assertTrue(ont.getAxioms(propR).contains(ax));
    }

    public void testDisjointDataPropertiesAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLDataProperty propQ = getOWLDataProperty("q");
        OWLDataProperty propR = getOWLDataProperty("r");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDisjointDataPropertiesAxiom ax = DisjointDataProperties(propP, propQ, propR);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDisjointDataPropertiesAxioms(propP).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(propQ).contains(ax));
        assertTrue(ont.getDisjointDataPropertiesAxioms(propR).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propQ).contains(ax));
        assertTrue(ont.getAxioms(propR).contains(ax));
    }

    public void testDataPropertyDomainAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLClass clsA = getOWLClass("ClsA");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyDomainAxiom ax = DataPropertyDomain(propP, clsA);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDataPropertyDomainAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.getDomains(ont).contains(clsA));

    }

    public void testDataPropertyRangeAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");
        OWLDatatype dt = getOWLDatatype("dt");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyRangeAxiom ax = DataPropertyRange(propP, dt);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDataPropertyRangeAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.getRanges(ont).contains(dt));
    }

    public void testFunctionalDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty propP = getOWLDataProperty("p");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLFunctionalDataPropertyAxiom ax = FunctionalDataProperty(propP);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getFunctionalDataPropertyAxioms(propP).contains(ax));
        assertTrue(ont.getAxioms(propP).contains(ax));
        assertTrue(propP.isFunctional(ont));
    }

    public void testClassAssertionAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLClass clsA = getOWLClass("clsA");
        OWLNamedIndividual indA = getOWLIndividual("indA");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLClassAssertionAxiom ax = ClassAssertion(clsA, indA);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getClassAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getClassAssertionAxioms(clsA).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
        assertTrue(clsA.getIndividuals(ont).contains(indA));
        assertTrue(indA.getTypes(ont).contains(clsA));
    }

    public void testObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indB");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLObjectPropertyAssertionAxiom ax = ObjectPropertyAssertion(prop, indA, indB);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);
        assertTrue(ont.getObjectPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
    }
    
    public void testNegativeObjectPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLObjectProperty prop = getOWLObjectProperty("prop");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indB");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeObjectPropertyAssertionAxiom ax = NegativeObjectPropertyAssertion(prop, indA, indB);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeObjectPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
    }
    
    public void testDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLLiteral lit = getFactory().getOWLLiteral(3);

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDataPropertyAssertionAxiom ax = DataPropertyAssertion(prop, indA, lit);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);
        assertTrue(ont.getDataPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
    }
    
    public void testNegativeDataPropertyAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLLiteral lit = getFactory().getOWLLiteral(3);

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLNegativeDataPropertyAssertionAxiom ax = NegativeDataPropertyAssertion(prop, indA, lit);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);
        assertTrue(ont.getNegativeDataPropertyAssertionAxioms(indA).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
    }

    public void testSameIndividualAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indB");
        OWLNamedIndividual indC = getOWLIndividual("indC");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLSameIndividualAxiom ax = SameIndividual(indA, indB, indC);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getSameIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getSameIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
        assertTrue(indA.getSameIndividuals(ont).contains(indB));
        assertTrue(indA.getSameIndividuals(ont).contains(indC));
    }

    public void testDifferentIndividualsAxiomAccessors() {
        OWLOntology ont = getOWLOntology("ont");
        OWLNamedIndividual indA = getOWLIndividual("indA");
        OWLNamedIndividual indB = getOWLIndividual("indB");
        OWLNamedIndividual indC = getOWLIndividual("indC");

        OWLOntologyManager man = ont.getOWLOntologyManager();
        OWLDifferentIndividualsAxiom ax = DifferentIndividuals(indA, indB, indC);
        man.addAxiom(ont, ax);

        performAxiomTests(ont, ax);

        assertTrue(ont.getDifferentIndividualAxioms(indA).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indB).contains(ax));
        assertTrue(ont.getDifferentIndividualAxioms(indC).contains(ax));
        assertTrue(ont.getAxioms(indA).contains(ax));
        assertTrue(indA.getDifferentIndividuals(ont).contains(indB));
        assertTrue(indA.getDifferentIndividuals(ont).contains(indC));
    }
}
