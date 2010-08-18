package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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
                assertTrue(ont.getLogicalAxioms().contains((OWLLogicalAxiom) ax));
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
