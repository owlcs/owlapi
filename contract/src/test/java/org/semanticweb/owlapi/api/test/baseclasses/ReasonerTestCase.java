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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import javax.annotation.Nonnull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 * This test case creates a small ontology and tests the getters in the reasoner
 * interface. The test ontology isn't designed to test the correctness of
 * reasoning results, rather it is designed to test the reasoner returns the
 * results in the form required by the OWL API reasoner interface.
 * 
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class ReasonerTestCase extends TestBase {

    @Nonnull
    private final OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
    private OWLReasoner reasoner;

    @Nonnull
    private OWLOntology createOntology() {
        OWLOntology o = getOWLOntology();
        OWLClass clsA = getClsA();
        OWLClass clsB = getClsB();
        OWLClass clsC = getClsC();
        OWLClass clsD = getClsD();
        OWLClass clsE = getClsE();
        OWLClass clsF = getClsF();
        OWLClass clsG = getClsG();
        OWLClass clsK = getClsK();
        OWLObjectPropertyExpression propP = getPropP();
        OWLObjectPropertyExpression propQ = getPropQ();
        OWLObjectPropertyExpression propR = getPropR();
        OWLObjectPropertyExpression propS = getPropS();
        o.addAxioms(SubClassOf(clsG, OWLThing()), SubClassOf(OWLThing(), clsG),
            EquivalentClasses(clsA, clsB), SubClassOf(clsC, clsB),
            SubClassOf(clsD, clsA), SubClassOf(clsD, clsF),
            SubClassOf(clsF, clsD), SubClassOf(clsE, clsC),
            SubClassOf(clsK, clsD), EquivalentClasses(clsK, OWLNothing()),
            EquivalentObjectProperties(propP, propQ),
            SubObjectPropertyOf(propP, propR),
            InverseObjectProperties(propR, propS));
        return o;
    }

    @Nonnull
    private OWLObjectProperty getPropS() {
        return ObjectProperty(iri("s"));
    }

    @Nonnull
    private OWLObjectProperty getPropR() {
        return ObjectProperty(iri("r"));
    }

    @Nonnull
    private OWLObjectProperty getPropQ() {
        return ObjectProperty(iri("q"));
    }

    @Nonnull
    private OWLObjectProperty getPropP() {
        return ObjectProperty(iri("p"));
    }

    @Nonnull
    private OWLClass getClsK() {
        return Class(iri("K"));
    }

    @Nonnull
    private OWLClass getClsG() {
        return Class(iri("G"));
    }

    @Nonnull
    private OWLClass getClsF() {
        return Class(iri("F"));
    }

    @Nonnull
    private OWLClass getClsE() {
        return Class(iri("E"));
    }

    @Nonnull
    private OWLClass getClsD() {
        return Class(iri("D"));
    }

    @Nonnull
    private OWLClass getClsC() {
        return Class(iri("C"));
    }

    @Nonnull
    private OWLClass getClsB() {
        return Class(iri("B"));
    }

    @Nonnull
    private OWLClass getClsA() {
        return Class(iri("A"));
    }

    @Before
    public void setUpOntoAndReasoner() {
        reasoner = reasonerFactory.createReasoner(createOntology());
    }

    @After
    public void tearDown() {
        reasoner.dispose();
    }

    @Test
    public void testGetName() {
        assertNotNull("name should not be null", reasoner.getReasonerName());
    }

    @Test
    public void testGetVersion() {
        assertNotNull("version should not be null",
            reasoner.getReasonerVersion());
    }

    @Test
    public void testGetTopClassNode() {
        Node<OWLClass> node = reasoner.getTopClassNode();
        assertTrue(node.isTopNode());
        assertFalse(node.isBottomNode());
        assertTrue(node.contains(OWLThing()));
        assertTrue(node.contains(getClsG()));
        assertEquals(2, node.getSize());
        assertEquals(2, node.entities().count());
        assertEquals(1, node.getEntitiesMinusTop().size());
        assertTrue(node.getEntitiesMinusTop().contains(getClsG()));
    }

    @Test
    public void testGetBottomClassNode() {
        Node<OWLClass> node = reasoner.getBottomClassNode();
        assertTrue(node.isBottomNode());
        assertFalse(node.isTopNode());
        assertTrue(node.contains(OWLNothing()));
        assertTrue(node.contains(getClsK()));
        assertEquals(2, node.getSize());
        assertEquals(2, node.entities().count());
        assertEquals(1, node.getEntitiesMinusBottom().size());
        assertTrue(node.getEntitiesMinusBottom().contains(getClsK()));
    }

    @Test
    public void testGetEquivalentClasses() {
        Node<OWLClass> nTop = reasoner.getEquivalentClasses(OWLThing());
        assertNotNull("object should not be null", nTop);
        assertEquals(2, nTop.getSize());
        assertTrue(nTop.contains(OWLThing()));
        assertTrue(nTop.contains(getClsG()));
        Node<OWLClass> nG = reasoner.getEquivalentClasses(getClsG());
        assertNotNull("object should not be null", nG);
        assertEquals(2, nG.getSize());
        assertTrue(nG.contains(OWLThing()));
        assertTrue(nG.contains(getClsG()));
        assertEquals(nTop, nG);
        Node<OWLClass> nA = reasoner.getEquivalentClasses(getClsA());
        assertNotNull("object should not be null", nA);
        assertEquals(2, nA.getSize());
        assertTrue(nA.contains(getClsA()));
        assertTrue(nA.contains(getClsB()));
        Node<OWLClass> nB = reasoner.getEquivalentClasses(getClsB());
        assertNotNull("object should not be null", nB);
        assertEquals(2, nB.getSize());
        assertTrue(nB.contains(getClsA()));
        assertTrue(nB.contains(getClsB()));
        assertEquals("object should not be null", nA, nB);
        Node<OWLClass> nC = reasoner.getEquivalentClasses(getClsC());
        assertNotNull("object should not be null", nC);
        assertEquals(1, nC.getSize());
        assertTrue(nC.contains(getClsC()));
        assertEquals(nC.getRepresentativeElement(), getClsC());
        Node<OWLClass> nE = reasoner.getEquivalentClasses(getClsE());
        assertNotNull("object should not be null", nE);
        assertEquals(1, nE.getSize());
        assertTrue(nE.contains(getClsE()));
        assertEquals(nE.getRepresentativeElement(), getClsE());
        Node<OWLClass> nD = reasoner.getEquivalentClasses(getClsD());
        assertNotNull("object should not be null", nD);
        assertEquals(2, nD.getSize());
        assertTrue(nD.contains(getClsD()));
        assertTrue(nD.contains(getClsF()));
        Node<OWLClass> nF = reasoner.getEquivalentClasses(getClsF());
        assertNotNull("object should not be null", nF);
        assertEquals(2, nF.getSize());
        assertTrue(nF.contains(getClsD()));
        assertTrue(nF.contains(getClsF()));
        assertEquals(nD, nF);
        Node<OWLClass> nBot = reasoner.getEquivalentClasses(OWLNothing());
        assertNotNull("object should not be null", nBot);
        assertEquals(2, nBot.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(getClsK()));
        Node<OWLClass> nK = reasoner.getEquivalentClasses(getClsK());
        assertNotNull("object should not be null", nK);
        assertEquals(2, nK.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(getClsK()));
        assertEquals(nBot, nK);
    }

    @Test
    public void testGetSuperClassesDirect() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(), true);
        assertNotNull("object should not be null", nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(getClsG(), true);
        assertNotNull("object should not be null", nsSupG);
        assertTrue(nsSupG.isEmpty());
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(getClsA(), true);
        assertNotNull("object should not be null", nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.nodes().count());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(getClsG()));
        assertTrue(nsSupA.isTopSingleton());
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(getClsB(), true);
        assertNotNull("object should not be null", nsSupB);
        assertEquals(1, nsSupB.nodes().count());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(getClsG()));
        assertTrue(nsSupB.isTopSingleton());
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(getClsC(), true);
        assertNotNull("object should not be null", nsSupC);
        assertEquals(1, nsSupC.nodes().count());
        assertTrue(nsSupC.containsEntity(getClsA()));
        assertTrue(nsSupC.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(getClsE(), true);
        assertNotNull("object should not be null", nsSupE);
        assertEquals(1, nsSupE.nodes().count());
        assertTrue(nsSupE.containsEntity(getClsC()));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(getClsD(), true);
        assertNotNull("object should not be null", nsSupD);
        assertEquals(1, nsSupD.nodes().count());
        assertTrue(nsSupD.containsEntity(getClsA()));
        assertTrue(nsSupD.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(getClsF(), true);
        assertNotNull("object should not be null", nsSupF);
        assertEquals(1, nsSupF.nodes().count());
        assertTrue(nsSupF.containsEntity(getClsA()));
        assertTrue(nsSupF.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(getClsK(), true);
        assertNotNull("object should not be null", nsSupK);
        assertEquals(2, nsSupK.nodes().count());
        assertTrue(nsSupK.containsEntity(getClsE()));
        assertTrue(nsSupK.containsEntity(getClsD()));
        assertTrue(nsSupK.containsEntity(getClsF()));
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(),
            true);
        assertNotNull("object should not be null", nsSupBot);
        assertEquals(2, nsSupBot.nodes().count());
        assertTrue(nsSupBot.containsEntity(getClsE()));
        assertTrue(nsSupBot.containsEntity(getClsD()));
        assertTrue(nsSupBot.containsEntity(getClsF()));
    }

    @Test
    public void testGetSuperClasses() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(),
            false);
        assertNotNull("object should not be null", nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(getClsG(), false);
        assertNotNull("object should not be null", nsSupG);
        assertTrue(nsSupG.isEmpty());
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(getClsA(), false);
        assertNotNull("object should not be null", nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.nodes().count());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(getClsG()));
        assertTrue(nsSupA.isTopSingleton());
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(getClsB(), false);
        assertNotNull("object should not be null", nsSupB);
        assertEquals(1, nsSupB.nodes().count());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(getClsG()));
        assertTrue(nsSupB.isTopSingleton());
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(getClsC(), false);
        assertNotNull("object should not be null", nsSupC);
        assertEquals(2, nsSupC.nodes().count());
        assertTrue(nsSupC.containsEntity(OWLThing()));
        assertTrue(nsSupC.containsEntity(getClsG()));
        assertTrue(nsSupC.containsEntity(getClsA()));
        assertTrue(nsSupC.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(getClsE(), false);
        assertNotNull("object should not be null", nsSupE);
        assertEquals(3, nsSupE.nodes().count());
        assertTrue(nsSupE.containsEntity(getClsC()));
        assertTrue(nsSupE.containsEntity(getClsA()));
        assertTrue(nsSupE.containsEntity(getClsB()));
        assertTrue(nsSupE.containsEntity(getClsG()));
        assertTrue(nsSupE.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(getClsD(), false);
        assertNotNull("object should not be null", nsSupD);
        assertEquals(2, nsSupD.nodes().count());
        assertTrue(nsSupD.containsEntity(getClsA()));
        assertTrue(nsSupD.containsEntity(getClsB()));
        assertTrue(nsSupD.containsEntity(getClsG()));
        assertTrue(nsSupD.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(getClsF(), false);
        assertNotNull("object should not be null", nsSupF);
        assertEquals(2, nsSupF.nodes().count());
        assertTrue(nsSupF.containsEntity(getClsA()));
        assertTrue(nsSupF.containsEntity(getClsB()));
        assertTrue(nsSupF.containsEntity(getClsG()));
        assertTrue(nsSupF.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(getClsK(), false);
        assertNotNull("object should not be null", nsSupK);
        assertEquals(5, nsSupK.nodes().count());
        assertTrue(nsSupK.containsEntity(getClsE()));
        assertTrue(nsSupK.containsEntity(getClsD()));
        assertTrue(nsSupK.containsEntity(getClsF()));
        assertTrue(nsSupK.containsEntity(getClsC()));
        assertTrue(nsSupK.containsEntity(getClsA()));
        assertTrue(nsSupK.containsEntity(getClsB()));
        assertTrue(nsSupK.containsEntity(getClsG()));
        assertTrue(nsSupK.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(),
            false);
        assertNotNull("object should not be null", nsSupBot);
        assertEquals(5, nsSupBot.nodes().count());
        assertTrue(nsSupBot.containsEntity(getClsE()));
        assertTrue(nsSupBot.containsEntity(getClsD()));
        assertTrue(nsSupBot.containsEntity(getClsF()));
        assertTrue(nsSupBot.containsEntity(getClsC()));
        assertTrue(nsSupBot.containsEntity(getClsA()));
        assertTrue(nsSupBot.containsEntity(getClsB()));
        assertTrue(nsSupBot.containsEntity(getClsG()));
        assertTrue(nsSupBot.containsEntity(OWLThing()));
    }

    @Test
    public void testGetSubClassesDirect() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), true);
        assertNotNull("object should not be null", nsSubTop);
        assertEquals(1, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(getClsA()));
        assertTrue(nsSubTop.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(getClsG(), true);
        assertNotNull("object should not be null", nsSubG);
        assertEquals(1, nsSubG.nodes().count());
        assertTrue(nsSubG.containsEntity(getClsA()));
        assertTrue(nsSubG.containsEntity(getClsB()));
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(getClsA(), true);
        assertNotNull("object should not be null", nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(2, nsSubA.nodes().count());
        assertTrue(nsSubA.containsEntity(getClsC()));
        assertTrue(nsSubA.containsEntity(getClsD()));
        assertTrue(nsSubA.containsEntity(getClsF()));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(getClsB(), true);
        assertNotNull("object should not be null", nsSubB);
        assertEquals(2, nsSubB.nodes().count());
        assertTrue(nsSubB.containsEntity(getClsC()));
        assertTrue(nsSubB.containsEntity(getClsD()));
        assertTrue(nsSubB.containsEntity(getClsF()));
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(getClsC(), true);
        assertNotNull("object should not be null", nsSubC);
        assertEquals(1, nsSubC.nodes().count());
        assertTrue(nsSubC.containsEntity(getClsE()));
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(getClsE(), true);
        assertNotNull("object should not be null", nsSubE);
        assertEquals(1, nsSubE.nodes().count());
        assertTrue(nsSubE.containsEntity(getClsK()));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(getClsD(), true);
        assertNotNull("object should not be null", nsSubD);
        assertEquals(1, nsSubD.nodes().count());
        assertTrue(nsSubD.containsEntity(getClsK()));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(getClsF(), true);
        assertNotNull("object should not be null", nsSubF);
        assertEquals(1, nsSubF.nodes().count());
        assertTrue(nsSubF.containsEntity(getClsK()));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(getClsK(), true);
        assertNotNull("object should not be null", nsSubK);
        assertTrue(nsSubK.isEmpty());
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(), true);
        assertNotNull("object should not be null", nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    @Test
    public void testGetSubClasses() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), false);
        assertNotNull("object should not be null", nsSubTop);
        assertEquals(5, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(getClsA()));
        assertTrue(nsSubTop.containsEntity(getClsB()));
        assertTrue(nsSubTop.containsEntity(getClsC()));
        assertTrue(nsSubTop.containsEntity(getClsD()));
        assertTrue(nsSubTop.containsEntity(getClsF()));
        assertTrue(nsSubTop.containsEntity(getClsE()));
        assertTrue(nsSubTop.containsEntity(getClsK()));
        assertTrue(nsSubTop.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(getClsG(), false);
        assertNotNull("object should not be null", nsSubG);
        assertEquals(5, nsSubG.nodes().count());
        assertTrue(nsSubG.containsEntity(getClsA()));
        assertTrue(nsSubG.containsEntity(getClsB()));
        assertTrue(nsSubG.containsEntity(getClsC()));
        assertTrue(nsSubG.containsEntity(getClsD()));
        assertTrue(nsSubG.containsEntity(getClsF()));
        assertTrue(nsSubG.containsEntity(getClsE()));
        assertTrue(nsSubG.containsEntity(getClsK()));
        assertTrue(nsSubG.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(getClsA(), false);
        assertNotNull("object should not be null", nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(4, nsSubA.nodes().count());
        assertTrue(nsSubA.containsEntity(getClsC()));
        assertTrue(nsSubA.containsEntity(getClsD()));
        assertTrue(nsSubA.containsEntity(getClsF()));
        assertTrue(nsSubA.containsEntity(getClsE()));
        assertTrue(nsSubA.containsEntity(getClsK()));
        assertTrue(nsSubA.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(getClsB(), false);
        assertNotNull("object should not be null", nsSubB);
        assertEquals(4, nsSubB.nodes().count());
        assertTrue(nsSubB.containsEntity(getClsC()));
        assertTrue(nsSubB.containsEntity(getClsD()));
        assertTrue(nsSubB.containsEntity(getClsF()));
        assertTrue(nsSubB.containsEntity(getClsE()));
        assertTrue(nsSubB.containsEntity(getClsK()));
        assertTrue(nsSubB.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(getClsC(), false);
        assertNotNull("object should not be null", nsSubC);
        assertEquals(2, nsSubC.nodes().count());
        assertTrue(nsSubC.containsEntity(getClsE()));
        assertTrue(nsSubC.containsEntity(getClsK()));
        assertTrue(nsSubC.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(getClsE(), false);
        assertNotNull("object should not be null", nsSubE);
        assertEquals(1, nsSubE.nodes().count());
        assertTrue(nsSubE.containsEntity(getClsK()));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(getClsD(), false);
        assertNotNull("object should not be null", nsSubD);
        assertEquals(1, nsSubD.nodes().count());
        assertTrue(nsSubD.containsEntity(getClsK()));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(getClsF(), false);
        assertNotNull("object should not be null", nsSubF);
        assertEquals(1, nsSubF.nodes().count());
        assertTrue(nsSubF.containsEntity(getClsK()));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(getClsK(), false);
        assertNotNull("object should not be null", nsSubK);
        assertTrue(nsSubK.isEmpty());
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(),
            false);
        assertNotNull("object should not be null", nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    @Test
    public void testIsSatisfiable() {
        assertTrue(reasoner.isSatisfiable(OWLThing()));
        assertTrue(reasoner.isSatisfiable(getClsG()));
        assertTrue(reasoner.isSatisfiable(getClsA()));
        assertTrue(reasoner.isSatisfiable(getClsB()));
        assertTrue(reasoner.isSatisfiable(getClsC()));
        assertTrue(reasoner.isSatisfiable(getClsD()));
        assertTrue(reasoner.isSatisfiable(getClsE()));
        assertFalse(reasoner.isSatisfiable(OWLNothing()));
        assertFalse(reasoner.isSatisfiable(getClsK()));
    }

    @Test
    public void testComputeClassHierarchy() {
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        assertTrue(reasoner.isPrecomputed(InferenceType.CLASS_HIERARCHY));
    }

    @Test
    public void testGetTopObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner
            .getTopObjectPropertyNode();
        assertNotNull("object should not be null", node);
        assertTrue(node.isTopNode());
    }

    @Test
    public void testGetBottomObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner
            .getBottomObjectPropertyNode();
        assertNotNull("object should not be null", node);
        assertTrue(node.isBottomNode());
    }

    @Test
    public void testGetSubObjectPropertiesDirect() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop = reasoner
            .getSubObjectProperties(df.getOWLTopObjectProperty(), true);
        assertNotNull("object should not be null", nsSubTop);
        assertEquals(2, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(getPropR()));
        assertTrue(nsSubTop.containsEntity(getPropS()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropS().getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner
            .getSubObjectProperties(getPropR(), true);
        assertNotNull("object should not be null", nsSubR);
        assertEquals(1, nsSubR.nodes().count());
        assertTrue(nsSubR.containsEntity(getPropP()));
        assertTrue(nsSubR.containsEntity(getPropQ()));
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus = reasoner
            .getSubObjectProperties(getPropR().getInverseProperty(), true);
        assertNotNull("object should not be null", nsSubRMinus);
        assertEquals(1, nsSubRMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus = reasoner
            .getSubObjectProperties(getPropS().getInverseProperty(), true);
        assertNotNull("object should not be null", nsSubSMinus);
        assertEquals(1, nsSubSMinus.nodes().count());
        assertTrue(nsSubSMinus.containsEntity(getPropP()));
        assertTrue(nsSubSMinus.containsEntity(getPropQ()));
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner
            .getSubObjectProperties(getPropS(), true);
        assertNotNull("object should not be null", nsSubS);
        assertEquals(1, nsSubS.nodes().count());
        assertTrue(nsSubS.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(getPropQ().getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner
            .getSubObjectProperties(getPropP(), true);
        assertNotNull("object should not be null", nsSubP);
        assertEquals(1, nsSubP.nodes().count());
        assertTrue(nsSubP.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner
            .getSubObjectProperties(getPropQ(), true);
        assertNotNull("object should not be null", nsSubQ);
        assertEquals(1, nsSubQ.nodes().count());
        assertTrue(nsSubQ.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus = reasoner
            .getSubObjectProperties(getPropP().getInverseProperty(), true);
        assertNotNull("object should not be null", nsSubPMinus);
        assertEquals(1, nsSubPMinus.nodes().count());
        assertTrue(nsSubPMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus = reasoner
            .getSubObjectProperties(getPropQ().getInverseProperty(), true);
        assertNotNull("object should not be null", nsSubQMinus);
        assertEquals(1, nsSubQMinus.nodes().count());
        assertTrue(nsSubQMinus.containsEntity(df.getOWLBottomObjectProperty()));
    }

    @Test
    public void testGetSubObjectProperties() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop = reasoner
            .getSubObjectProperties(df.getOWLTopObjectProperty(), false);
        assertNotNull("object should not be null", nsSubTop);
        assertEquals(5, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(getPropR()));
        assertTrue(nsSubTop.containsEntity(getPropS()));
        assertTrue(nsSubTop.containsEntity(getPropP()));
        assertTrue(nsSubTop.containsEntity(getPropQ()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner
            .getSubObjectProperties(getPropR(), false);
        assertNotNull("object should not be null", nsSubR);
        assertEquals(2, nsSubR.nodes().count());
        assertTrue(nsSubR.containsEntity(getPropP()));
        assertTrue(nsSubR.containsEntity(getPropQ()));
        assertTrue(nsSubR.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus = reasoner
            .getSubObjectProperties(getPropR().getInverseProperty(), false);
        assertNotNull("object should not be null", nsSubRMinus);
        assertEquals(2, nsSubRMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus = reasoner
            .getSubObjectProperties(getPropS().getInverseProperty(), false);
        assertNotNull("object should not be null", nsSubSMinus);
        assertEquals(2, nsSubSMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner
            .getSubObjectProperties(getPropS(), false);
        assertNotNull("object should not be null", nsSubS);
        assertEquals(2, nsSubS.nodes().count());
        assertTrue(nsSubS.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner
            .getSubObjectProperties(getPropP(), false);
        assertNotNull("object should not be null", nsSubP);
        assertEquals(1, nsSubP.nodes().count());
        assertTrue(nsSubP.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner
            .getSubObjectProperties(getPropQ(), false);
        assertNotNull("object should not be null", nsSubQ);
        assertEquals(1, nsSubQ.nodes().count());
        assertTrue(nsSubQ.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus = reasoner
            .getSubObjectProperties(getPropP().getInverseProperty(), false);
        assertNotNull("object should not be null", nsSubPMinus);
        assertEquals(1, nsSubPMinus.nodes().count());
        assertTrue(nsSubPMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus = reasoner
            .getSubObjectProperties(getPropQ().getInverseProperty(), false);
        assertNotNull("object should not be null", nsSubQMinus);
        assertEquals(1, nsSubQMinus.nodes().count());
        assertTrue(nsSubQMinus.containsEntity(df.getOWLBottomObjectProperty()));
    }
}
