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
package org.semanticweb.owlapi.apitest.baseclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;

/**
 * This test case creates a small ontology and tests the getters in the reasoner interface. The test
 * ontology isn't designed to test the correctness of reasoning results, rather it is designed to
 * test the reasoner returns the results in the form required by the OWL API reasoner interface.
 *
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.1.0
 */
class ReasonerTestCase extends TestBase {

    private final OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
    private OWLReasoner reasoner;

    private OWLOntology createOntology() {
        OWLOntology o = create("ont");
        o.add(SubClassOf(CLASSES.G, OWLThing()), SubClassOf(OWLThing(), CLASSES.G),
            EquivalentClasses(CLASSES.A, CLASSES.B), SubClassOf(CLASSES.C, CLASSES.B),
            SubClassOf(CLASSES.D, CLASSES.A), SubClassOf(CLASSES.D, CLASSES.F),
            SubClassOf(CLASSES.F, CLASSES.D), SubClassOf(CLASSES.E, CLASSES.C),
            SubClassOf(CLASSES.K, CLASSES.D), EquivalentClasses(CLASSES.K, OWLNothing()),
            EquivalentObjectProperties(OBJPROPS.P, OBJPROPS.Q),
            SubObjectPropertyOf(OBJPROPS.P, OBJPROPS.R),
            InverseObjectProperties(OBJPROPS.R, OBJPROPS.S));
        return o;
    }

    @BeforeEach
    void setUpOntoAndReasoner() {
        reasoner = reasonerFactory.createReasoner(createOntology());
    }

    @AfterEach
    void tearDown() {
        reasoner.dispose();
    }

    @Test
    void testGetName() {
        assertNotNull(reasoner.getReasonerName());
    }

    @Test
    void testGetVersion() {
        assertNotNull(reasoner.getReasonerVersion());
    }

    @Test
    void testGetTopClassNode() {
        Node<OWLClass> node = reasoner.getTopClassNode();
        assertTrue(node.isTopNode());
        assertFalse(node.isBottomNode());
        assertTrue(node.contains(OWLThing()));
        assertTrue(node.contains(CLASSES.G));
        assertEquals(2, node.getSize());
        assertEquals(2, node.entities().count());
        assertEquals(1, node.getEntitiesMinusTop().size());
        assertTrue(node.getEntitiesMinusTop().contains(CLASSES.G));
    }

    @Test
    void testGetBottomClassNode() {
        Node<OWLClass> node = reasoner.getBottomClassNode();
        assertTrue(node.isBottomNode());
        assertFalse(node.isTopNode());
        assertTrue(node.contains(OWLNothing()));
        assertTrue(node.contains(CLASSES.K));
        assertEquals(2, node.getSize());
        assertEquals(2, node.entities().count());
        assertEquals(1, node.getEntitiesMinusBottom().size());
        assertTrue(node.getEntitiesMinusBottom().contains(CLASSES.K));
    }

    @Test
    void testGetEquivalentClasses() {
        Node<OWLClass> nTop = reasoner.getEquivalentClasses(OWLThing());
        assertNotNull(nTop);
        assertEquals(2, nTop.getSize());
        assertTrue(nTop.contains(OWLThing()));
        assertTrue(nTop.contains(CLASSES.G));
        Node<OWLClass> nG = reasoner.getEquivalentClasses(CLASSES.G);
        assertNotNull(nG);
        assertEquals(2, nG.getSize());
        assertTrue(nG.contains(OWLThing()));
        assertTrue(nG.contains(CLASSES.G));
        assertEquals(nTop, nG);
        equivABC();
        equivDEF();
        Node<OWLClass> nBot = reasoner.getEquivalentClasses(OWLNothing());
        assertNotNull(nBot);
        assertEquals(2, nBot.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(CLASSES.K));
        Node<OWLClass> nK = reasoner.getEquivalentClasses(CLASSES.K);
        assertNotNull(nK);
        assertEquals(2, nK.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(CLASSES.K));
        assertEquals(nBot, nK);
    }

    protected void equivDEF() {
        Node<OWLClass> nE = reasoner.getEquivalentClasses(CLASSES.E);
        assertNotNull(nE);
        assertEquals(1, nE.getSize());
        assertTrue(nE.contains(CLASSES.E));
        assertEquals(CLASSES.E, nE.getRepresentativeElement());
        Node<OWLClass> nD = reasoner.getEquivalentClasses(CLASSES.D);
        assertNotNull(nD);
        assertEquals(2, nD.getSize());
        assertTrue(nD.contains(CLASSES.D));
        assertTrue(nD.contains(CLASSES.F));
        Node<OWLClass> nF = reasoner.getEquivalentClasses(CLASSES.F);
        assertNotNull(nF);
        assertEquals(2, nF.getSize());
        assertTrue(nF.contains(CLASSES.D));
        assertTrue(nF.contains(CLASSES.F));
        assertEquals(nD, nF);
    }

    protected void equivABC() {
        Node<OWLClass> nA = reasoner.getEquivalentClasses(CLASSES.A);
        assertNotNull(nA);
        assertEquals(2, nA.getSize());
        assertTrue(nA.contains(CLASSES.A));
        assertTrue(nA.contains(CLASSES.B));
        Node<OWLClass> nB = reasoner.getEquivalentClasses(CLASSES.B);
        assertNotNull(nB);
        assertEquals(2, nB.getSize());
        assertTrue(nB.contains(CLASSES.A));
        assertTrue(nB.contains(CLASSES.B));
        assertEquals(nA, nB);
        Node<OWLClass> nC = reasoner.getEquivalentClasses(CLASSES.C);
        assertNotNull(nC);
        assertEquals(1, nC.getSize());
        assertTrue(nC.contains(CLASSES.C));
        assertEquals(CLASSES.C, nC.getRepresentativeElement());
    }

    @Test
    void testGetSuperClassesDirect() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(), true);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        supGED();
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(CLASSES.F, true);
        assertNotNull(nsSupF);
        assertEquals(1, nsSupF.nodes().count());
        assertTrue(nsSupF.containsEntity(CLASSES.A));
        assertTrue(nsSupF.containsEntity(CLASSES.B));
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(CLASSES.K, true);
        assertNotNull(nsSupK);
        assertEquals(2, nsSupK.nodes().count());
        assertTrue(nsSupK.containsEntity(CLASSES.E));
        assertTrue(nsSupK.containsEntity(CLASSES.D));
        assertTrue(nsSupK.containsEntity(CLASSES.F));
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(), true);
        assertNotNull(nsSupBot);
        assertEquals(2, nsSupBot.nodes().count());
        assertTrue(nsSupBot.containsEntity(CLASSES.E));
        assertTrue(nsSupBot.containsEntity(CLASSES.D));
        assertTrue(nsSupBot.containsEntity(CLASSES.F));
    }

    protected void supGED() {
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(CLASSES.G, true);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());
        supA();
        supB();
        supC();
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(CLASSES.E, true);
        assertNotNull(nsSupE);
        assertEquals(1, nsSupE.nodes().count());
        assertTrue(nsSupE.containsEntity(CLASSES.C));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(CLASSES.D, true);
        assertNotNull(nsSupD);
        assertEquals(1, nsSupD.nodes().count());
        assertTrue(nsSupD.containsEntity(CLASSES.A));
        assertTrue(nsSupD.containsEntity(CLASSES.B));
    }

    protected void supC() {
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(CLASSES.C, true);
        assertNotNull(nsSupC);
        assertEquals(1, nsSupC.nodes().count());
        assertTrue(nsSupC.containsEntity(CLASSES.A));
        assertTrue(nsSupC.containsEntity(CLASSES.B));
    }

    protected void supB() {
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(CLASSES.B, true);
        assertNotNull(nsSupB);
        assertEquals(1, nsSupB.nodes().count());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(CLASSES.G));
        assertTrue(nsSupB.isTopSingleton());
    }

    protected void supA() {
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(CLASSES.A, true);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.nodes().count());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(CLASSES.G));
        assertTrue(nsSupA.isTopSingleton());
    }

    @Test
    void testGetSuperClasses() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(), false);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(CLASSES.G, false);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());
        supABC();
        supDEF();
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(CLASSES.K, false);
        assertNotNull(nsSupK);
        assertEquals(5, nsSupK.nodes().count());
        assertTrue(nsSupK.containsEntity(CLASSES.E));
        assertTrue(nsSupK.containsEntity(CLASSES.D));
        assertTrue(nsSupK.containsEntity(CLASSES.F));
        assertTrue(nsSupK.containsEntity(CLASSES.C));
        assertTrue(nsSupK.containsEntity(CLASSES.A));
        assertTrue(nsSupK.containsEntity(CLASSES.B));
        assertTrue(nsSupK.containsEntity(CLASSES.G));
        assertTrue(nsSupK.containsEntity(OWLThing()));
        supBottom();
    }

    protected void supBottom() {
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(), false);
        assertNotNull(nsSupBot);
        assertEquals(5, nsSupBot.nodes().count());
        assertTrue(nsSupBot.containsEntity(CLASSES.E));
        assertTrue(nsSupBot.containsEntity(CLASSES.D));
        assertTrue(nsSupBot.containsEntity(CLASSES.F));
        assertTrue(nsSupBot.containsEntity(CLASSES.C));
        assertTrue(nsSupBot.containsEntity(CLASSES.A));
        assertTrue(nsSupBot.containsEntity(CLASSES.B));
        assertTrue(nsSupBot.containsEntity(CLASSES.G));
        assertTrue(nsSupBot.containsEntity(OWLThing()));
    }

    protected void supDEF() {
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(CLASSES.E, false);
        assertNotNull(nsSupE);
        assertEquals(3, nsSupE.nodes().count());
        assertTrue(nsSupE.containsEntity(CLASSES.C));
        assertTrue(nsSupE.containsEntity(CLASSES.A));
        assertTrue(nsSupE.containsEntity(CLASSES.B));
        assertTrue(nsSupE.containsEntity(CLASSES.G));
        assertTrue(nsSupE.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(CLASSES.D, false);
        assertNotNull(nsSupD);
        assertEquals(2, nsSupD.nodes().count());
        assertTrue(nsSupD.containsEntity(CLASSES.A));
        assertTrue(nsSupD.containsEntity(CLASSES.B));
        assertTrue(nsSupD.containsEntity(CLASSES.G));
        assertTrue(nsSupD.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(CLASSES.F, false);
        assertNotNull(nsSupF);
        assertEquals(2, nsSupF.nodes().count());
        assertTrue(nsSupF.containsEntity(CLASSES.A));
        assertTrue(nsSupF.containsEntity(CLASSES.B));
        assertTrue(nsSupF.containsEntity(CLASSES.G));
        assertTrue(nsSupF.containsEntity(OWLThing()));
    }

    protected void supABC() {
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(CLASSES.A, false);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.nodes().count());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(CLASSES.G));
        assertTrue(nsSupA.isTopSingleton());
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(CLASSES.B, false);
        assertNotNull(nsSupB);
        assertEquals(1, nsSupB.nodes().count());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(CLASSES.G));
        assertTrue(nsSupB.isTopSingleton());
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(CLASSES.C, false);
        assertNotNull(nsSupC);
        assertEquals(2, nsSupC.nodes().count());
        assertTrue(nsSupC.containsEntity(OWLThing()));
        assertTrue(nsSupC.containsEntity(CLASSES.G));
        assertTrue(nsSupC.containsEntity(CLASSES.A));
        assertTrue(nsSupC.containsEntity(CLASSES.B));
    }

    @Test
    void testGetSubClassesDirect() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), true);
        assertNotNull(nsSubTop);
        assertEquals(1, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(CLASSES.A));
        assertTrue(nsSubTop.containsEntity(CLASSES.B));
        subGBA();
        subGED();
        subKF();
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(), true);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    protected void subKF() {
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(CLASSES.F, true);
        assertNotNull(nsSubF);
        assertEquals(1, nsSubF.nodes().count());
        assertTrue(nsSubF.containsEntity(CLASSES.K));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(CLASSES.K, true);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());
    }

    protected void subGED() {
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(CLASSES.C, true);
        assertNotNull(nsSubC);
        assertEquals(1, nsSubC.nodes().count());
        assertTrue(nsSubC.containsEntity(CLASSES.E));
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(CLASSES.E, true);
        assertNotNull(nsSubE);
        assertEquals(1, nsSubE.nodes().count());
        assertTrue(nsSubE.containsEntity(CLASSES.K));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(CLASSES.D, true);
        assertNotNull(nsSubD);
        assertEquals(1, nsSubD.nodes().count());
        assertTrue(nsSubD.containsEntity(CLASSES.K));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
    }

    protected void subGBA() {
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(CLASSES.G, true);
        assertNotNull(nsSubG);
        assertEquals(1, nsSubG.nodes().count());
        assertTrue(nsSubG.containsEntity(CLASSES.A));
        assertTrue(nsSubG.containsEntity(CLASSES.B));
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(CLASSES.A, true);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(2, nsSubA.nodes().count());
        assertTrue(nsSubA.containsEntity(CLASSES.C));
        assertTrue(nsSubA.containsEntity(CLASSES.D));
        assertTrue(nsSubA.containsEntity(CLASSES.F));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(CLASSES.B, true);
        assertNotNull(nsSubB);
        assertEquals(2, nsSubB.nodes().count());
        assertTrue(nsSubB.containsEntity(CLASSES.C));
        assertTrue(nsSubB.containsEntity(CLASSES.D));
        assertTrue(nsSubB.containsEntity(CLASSES.F));
    }

    @Test
    void testGetSubClasses() {
        subCTop();
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(CLASSES.G, false);
        assertNotNull(nsSubG);
        assertEquals(5, nsSubG.nodes().count());
        assertTrue(nsSubG.containsEntity(CLASSES.A));
        assertTrue(nsSubG.containsEntity(CLASSES.B));
        assertTrue(nsSubG.containsEntity(CLASSES.C));
        assertTrue(nsSubG.containsEntity(CLASSES.D));
        assertTrue(nsSubG.containsEntity(CLASSES.F));
        assertTrue(nsSubG.containsEntity(CLASSES.E));
        assertTrue(nsSubG.containsEntity(CLASSES.K));
        assertTrue(nsSubG.containsEntity(OWLNothing()));
        subABC(nsSubG);
        subDEF();
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(CLASSES.K, false);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(), false);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    protected void subDEF() {
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(CLASSES.E, false);
        assertNotNull(nsSubE);
        assertEquals(1, nsSubE.nodes().count());
        assertTrue(nsSubE.containsEntity(CLASSES.K));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(CLASSES.D, false);
        assertNotNull(nsSubD);
        assertEquals(1, nsSubD.nodes().count());
        assertTrue(nsSubD.containsEntity(CLASSES.K));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(CLASSES.F, false);
        assertNotNull(nsSubF);
        assertEquals(1, nsSubF.nodes().count());
        assertTrue(nsSubF.containsEntity(CLASSES.K));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
    }

    protected void subABC(NodeSet<OWLClass> nsSubG) {
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(CLASSES.A, false);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(4, nsSubA.nodes().count());
        assertTrue(nsSubA.containsEntity(CLASSES.C));
        assertTrue(nsSubA.containsEntity(CLASSES.D));
        assertTrue(nsSubA.containsEntity(CLASSES.F));
        assertTrue(nsSubA.containsEntity(CLASSES.E));
        assertTrue(nsSubA.containsEntity(CLASSES.K));
        assertTrue(nsSubA.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(CLASSES.B, false);
        assertNotNull(nsSubB);
        assertEquals(4, nsSubB.nodes().count());
        assertTrue(nsSubB.containsEntity(CLASSES.C));
        assertTrue(nsSubB.containsEntity(CLASSES.D));
        assertTrue(nsSubB.containsEntity(CLASSES.F));
        assertTrue(nsSubB.containsEntity(CLASSES.E));
        assertTrue(nsSubB.containsEntity(CLASSES.K));
        assertTrue(nsSubB.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(CLASSES.C, false);
        assertNotNull(nsSubC);
        assertEquals(2, nsSubC.nodes().count());
        assertTrue(nsSubC.containsEntity(CLASSES.E));
        assertTrue(nsSubC.containsEntity(CLASSES.K));
        assertTrue(nsSubC.containsEntity(OWLNothing()));
    }

    protected void subCTop() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), false);
        assertNotNull(nsSubTop);
        assertEquals(5, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(CLASSES.A));
        assertTrue(nsSubTop.containsEntity(CLASSES.B));
        assertTrue(nsSubTop.containsEntity(CLASSES.C));
        assertTrue(nsSubTop.containsEntity(CLASSES.D));
        assertTrue(nsSubTop.containsEntity(CLASSES.F));
        assertTrue(nsSubTop.containsEntity(CLASSES.E));
        assertTrue(nsSubTop.containsEntity(CLASSES.K));
        assertTrue(nsSubTop.containsEntity(OWLNothing()));
    }

    @Test
    void testIsSatisfiable() {
        assertTrue(reasoner.isSatisfiable(OWLThing()));
        assertTrue(reasoner.isSatisfiable(CLASSES.G));
        assertTrue(reasoner.isSatisfiable(CLASSES.A));
        assertTrue(reasoner.isSatisfiable(CLASSES.B));
        assertTrue(reasoner.isSatisfiable(CLASSES.C));
        assertTrue(reasoner.isSatisfiable(CLASSES.D));
        assertTrue(reasoner.isSatisfiable(CLASSES.E));
        assertFalse(reasoner.isSatisfiable(OWLNothing()));
        assertFalse(reasoner.isSatisfiable(CLASSES.K));
    }

    @Test
    void testComputeClassHierarchy() {
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        assertTrue(reasoner.isPrecomputed(InferenceType.CLASS_HIERARCHY));
    }

    @Test
    void testGetTopObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner.getTopObjectPropertyNode();
        assertNotNull(node);
        assertTrue(node.isTopNode());
    }

    @Test
    void testGetBottomObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner.getBottomObjectPropertyNode();
        assertNotNull(node);
        assertTrue(node.isBottomNode());
    }

    @Test
    void testGetSubObjectPropertiesDirect() {
        subPropTop();
        subProp();
        subR();
        subInverse();
        subS();
        subP();
        subQ();
        subPInverse();
        subQInverse();
    }

    protected void subQInverse() {
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus =
            reasoner.getSubObjectProperties(OBJPROPS.Q.getInverseProperty(), true);
        assertNotNull(nsSubQMinus);
        assertEquals(1, nsSubQMinus.nodes().count());
        assertTrue(nsSubQMinus.containsEntity(BottomObjectProperty()));
    }

    protected void subPInverse() {
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus =
            reasoner.getSubObjectProperties(OBJPROPS.P.getInverseProperty(), true);
        assertNotNull(nsSubPMinus);
        assertEquals(1, nsSubPMinus.nodes().count());
        assertTrue(nsSubPMinus.containsEntity(BottomObjectProperty()));
    }

    protected void subQ() {
        NodeSet<OWLObjectPropertyExpression> nsSubQ =
            reasoner.getSubObjectProperties(OBJPROPS.Q, true);
        assertNotNull(nsSubQ);
        assertEquals(1, nsSubQ.nodes().count());
        assertTrue(nsSubQ.containsEntity(BottomObjectProperty()));
    }

    protected void subP() {
        NodeSet<OWLObjectPropertyExpression> nsSubP =
            reasoner.getSubObjectProperties(OBJPROPS.P, true);
        assertNotNull(nsSubP);
        assertEquals(1, nsSubP.nodes().count());
        assertTrue(nsSubP.containsEntity(BottomObjectProperty()));
    }

    protected void subS() {
        NodeSet<OWLObjectPropertyExpression> nsSubS =
            reasoner.getSubObjectProperties(OBJPROPS.S, true);
        assertNotNull(nsSubS);
        assertEquals(1, nsSubS.nodes().count());
        assertTrue(nsSubS.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(OBJPROPS.Q.getInverseProperty()));
    }

    protected void subInverse() {
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus =
            reasoner.getSubObjectProperties(OBJPROPS.S.getInverseProperty(), true);
        assertNotNull(nsSubSMinus);
        assertEquals(1, nsSubSMinus.nodes().count());
        assertTrue(nsSubSMinus.containsEntity(OBJPROPS.P));
        assertTrue(nsSubSMinus.containsEntity(OBJPROPS.Q));
    }

    protected void subR() {
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus =
            reasoner.getSubObjectProperties(OBJPROPS.R.getInverseProperty(), true);
        assertNotNull(nsSubRMinus);
        assertEquals(1, nsSubRMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.Q.getInverseProperty()));
    }

    protected void subProp() {
        NodeSet<OWLObjectPropertyExpression> nsSubR =
            reasoner.getSubObjectProperties(OBJPROPS.R, true);
        assertNotNull(nsSubR);
        assertEquals(1, nsSubR.nodes().count());
        assertTrue(nsSubR.containsEntity(OBJPROPS.P));
        assertTrue(nsSubR.containsEntity(OBJPROPS.Q));
    }

    protected void subPropTop() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop =
            reasoner.getSubObjectProperties(TopObjectProperty(), true);
        assertNotNull(nsSubTop);
        assertEquals(2, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(OBJPROPS.R));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.S));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.S.getInverseProperty()));
    }

    @Test
    void testGetSubObjectProperties() {
        subTop();
        NodeSet<OWLObjectPropertyExpression> nsSubR =
            reasoner.getSubObjectProperties(OBJPROPS.R, false);
        assertNotNull(nsSubR);
        assertEquals(2, nsSubR.nodes().count());
        assertTrue(nsSubR.containsEntity(OBJPROPS.P));
        assertTrue(nsSubR.containsEntity(OBJPROPS.Q));
        assertTrue(nsSubR.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus =
            reasoner.getSubObjectProperties(OBJPROPS.R.getInverseProperty(), false);
        assertNotNull(nsSubRMinus);
        assertEquals(2, nsSubRMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.Q.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus =
            reasoner.getSubObjectProperties(OBJPROPS.S.getInverseProperty(), false);
        assertNotNull(nsSubSMinus);
        assertEquals(2, nsSubSMinus.nodes().count());
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(OBJPROPS.Q.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubS =
            reasoner.getSubObjectProperties(OBJPROPS.S, false);
        assertNotNull(nsSubS);
        assertEquals(2, nsSubS.nodes().count());
        assertTrue(nsSubS.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(OBJPROPS.Q.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(BottomObjectProperty()));
        subPQ();
    }

    protected void subPQ() {
        NodeSet<OWLObjectPropertyExpression> nsSubP =
            reasoner.getSubObjectProperties(OBJPROPS.P, false);
        assertNotNull(nsSubP);
        assertEquals(1, nsSubP.nodes().count());
        assertTrue(nsSubP.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQ =
            reasoner.getSubObjectProperties(OBJPROPS.Q, false);
        assertNotNull(nsSubQ);
        assertEquals(1, nsSubQ.nodes().count());
        assertTrue(nsSubQ.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus =
            reasoner.getSubObjectProperties(OBJPROPS.P.getInverseProperty(), false);
        assertNotNull(nsSubPMinus);
        assertEquals(1, nsSubPMinus.nodes().count());
        assertTrue(nsSubPMinus.containsEntity(BottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus =
            reasoner.getSubObjectProperties(OBJPROPS.Q.getInverseProperty(), false);
        assertNotNull(nsSubQMinus);
        assertEquals(1, nsSubQMinus.nodes().count());
        assertTrue(nsSubQMinus.containsEntity(BottomObjectProperty()));
    }

    protected void subTop() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop =
            reasoner.getSubObjectProperties(TopObjectProperty(), false);
        assertNotNull(nsSubTop);
        assertEquals(5, nsSubTop.nodes().count());
        assertTrue(nsSubTop.containsEntity(OBJPROPS.R));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.S));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.P));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.Q));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.P.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(OBJPROPS.Q.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(BottomObjectProperty()));
    }
}
