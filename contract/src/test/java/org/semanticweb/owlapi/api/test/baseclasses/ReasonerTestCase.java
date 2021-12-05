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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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

    @Nonnull
    private final OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
    @Nonnull
    private OWLOntology ont;
    private OWLReasoner reasoner;

    @Nonnull
    private OWLOntology createOntology() {
        OWLOntology o = create("ont");
        OWLOntologyManager man = o.getOWLOntologyManager();
        man.addAxiom(o, SubClassOf(G, OWLThing()));
        man.addAxiom(o, SubClassOf(OWLThing(), G));
        man.addAxiom(o, EquivalentClasses(A, B));
        man.addAxiom(o, SubClassOf(C, B));
        man.addAxiom(o, SubClassOf(D, A));
        man.addAxiom(o, SubClassOf(D, F));
        man.addAxiom(o, SubClassOf(F, D));
        man.addAxiom(o, SubClassOf(E, C));
        man.addAxiom(o, SubClassOf(K, D));
        man.addAxiom(o, EquivalentClasses(K, OWLNothing()));
        man.addAxiom(o, EquivalentObjectProperties(P, Q));
        man.addAxiom(o, SubObjectPropertyOf(P, R));
        man.addAxiom(o, InverseObjectProperties(R, S));
        return o;
    }

    @BeforeEach
    void setUpOntoAndReasoner() {
        ont = createOntology();
        reasoner = reasonerFactory.createReasoner(ont);
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
        assertTrue(node.contains(G));
        assertEquals(2, node.getSize());
        assertEquals(2, node.getEntities().size());
        assertEquals(1, node.getEntitiesMinusTop().size());
        assertTrue(node.getEntitiesMinusTop().contains(G));
    }

    @Test
    void testGetBottomClassNode() {
        Node<OWLClass> node = reasoner.getBottomClassNode();
        assertTrue(node.isBottomNode());
        assertFalse(node.isTopNode());
        assertTrue(node.contains(OWLNothing()));
        assertTrue(node.contains(K));
        assertEquals(2, node.getSize());
        assertEquals(2, node.getEntities().size());
        assertEquals(1, node.getEntitiesMinusBottom().size());
        assertTrue(node.getEntitiesMinusBottom().contains(K));
    }

    @Test
    void testGetEquivalentClasses() {
        Node<OWLClass> nTop = reasoner.getEquivalentClasses(OWLThing());
        assertNotNull(nTop);
        assertEquals(2, nTop.getSize());
        assertTrue(nTop.contains(OWLThing()));
        assertTrue(nTop.contains(G));
        Node<OWLClass> nG = reasoner.getEquivalentClasses(G);
        assertNotNull(nG);
        assertEquals(2, nG.getSize());
        assertTrue(nG.contains(OWLThing()));
        assertTrue(nG.contains(G));
        assertEquals(nTop, nG);
        Node<OWLClass> nA = reasoner.getEquivalentClasses(A);
        assertNotNull(nA);
        assertEquals(2, nA.getSize());
        assertTrue(nA.contains(A));
        assertTrue(nA.contains(B));
        Node<OWLClass> nB = reasoner.getEquivalentClasses(B);
        assertNotNull(nB);
        assertEquals(2, nB.getSize());
        assertTrue(nB.contains(A));
        assertTrue(nB.contains(B));
        assertEquals(nA, nB);
        Node<OWLClass> nC = reasoner.getEquivalentClasses(C);
        assertNotNull(nC);
        assertEquals(1, nC.getSize());
        assertTrue(nC.contains(C));
        assertEquals(C, nC.getRepresentativeElement());
        Node<OWLClass> nE = reasoner.getEquivalentClasses(E);
        assertNotNull(nE);
        assertEquals(1, nE.getSize());
        assertTrue(nE.contains(E));
        assertEquals(E, nE.getRepresentativeElement());
        Node<OWLClass> nD = reasoner.getEquivalentClasses(D);
        assertNotNull(nD);
        assertEquals(2, nD.getSize());
        assertTrue(nD.contains(D));
        assertTrue(nD.contains(F));
        Node<OWLClass> nF = reasoner.getEquivalentClasses(F);
        assertNotNull(nF);
        assertEquals(2, nF.getSize());
        assertTrue(nF.contains(D));
        assertTrue(nF.contains(F));
        assertEquals(nD, nF);
        Node<OWLClass> nBot = reasoner.getEquivalentClasses(OWLNothing());
        assertNotNull(nBot);
        assertEquals(2, nBot.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(K));
        Node<OWLClass> nK = reasoner.getEquivalentClasses(K);
        assertNotNull(nK);
        assertEquals(2, nK.getSize());
        assertTrue(nBot.contains(OWLNothing()));
        assertTrue(nBot.contains(K));
        assertEquals(nBot, nK);
    }

    @Test
    void testGetSuperClassesDirect() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(), true);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(G, true);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(A, true);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.getNodes().size());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(G));
        assertTrue(nsSupA.isTopSingleton());
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(B, true);
        assertNotNull(nsSupB);
        assertEquals(1, nsSupB.getNodes().size());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(G));
        assertTrue(nsSupB.isTopSingleton());
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(C, true);
        assertNotNull(nsSupC);
        assertEquals(1, nsSupC.getNodes().size());
        assertTrue(nsSupC.containsEntity(A));
        assertTrue(nsSupC.containsEntity(B));
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(E, true);
        assertNotNull(nsSupE);
        assertEquals(1, nsSupE.getNodes().size());
        assertTrue(nsSupE.containsEntity(C));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(D, true);
        assertNotNull(nsSupD);
        assertEquals(1, nsSupD.getNodes().size());
        assertTrue(nsSupD.containsEntity(A));
        assertTrue(nsSupD.containsEntity(B));
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(F, true);
        assertNotNull(nsSupF);
        assertEquals(1, nsSupF.getNodes().size());
        assertTrue(nsSupF.containsEntity(A));
        assertTrue(nsSupF.containsEntity(B));
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(K, true);
        assertNotNull(nsSupK);
        assertEquals(2, nsSupK.getNodes().size());
        assertTrue(nsSupK.containsEntity(E));
        assertTrue(nsSupK.containsEntity(D));
        assertTrue(nsSupK.containsEntity(F));
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(), true);
        assertNotNull(nsSupBot);
        assertEquals(2, nsSupBot.getNodes().size());
        assertTrue(nsSupBot.containsEntity(E));
        assertTrue(nsSupBot.containsEntity(D));
        assertTrue(nsSupBot.containsEntity(F));
    }

    @Test
    void testGetSuperClasses() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(OWLThing(), false);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());
        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(G, false);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());
        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(A, false);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertEquals(1, nsSupA.getNodes().size());
        assertTrue(nsSupA.containsEntity(OWLThing()));
        assertTrue(nsSupA.containsEntity(G));
        assertTrue(nsSupA.isTopSingleton());
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(B, false);
        assertNotNull(nsSupB);
        assertEquals(1, nsSupB.getNodes().size());
        assertTrue(nsSupB.containsEntity(OWLThing()));
        assertTrue(nsSupB.containsEntity(G));
        assertTrue(nsSupB.isTopSingleton());
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(C, false);
        assertNotNull(nsSupC);
        assertEquals(2, nsSupC.getNodes().size());
        assertTrue(nsSupC.containsEntity(OWLThing()));
        assertTrue(nsSupC.containsEntity(G));
        assertTrue(nsSupC.containsEntity(A));
        assertTrue(nsSupC.containsEntity(B));
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(E, false);
        assertNotNull(nsSupE);
        assertEquals(3, nsSupE.getNodes().size());
        assertTrue(nsSupE.containsEntity(C));
        assertTrue(nsSupE.containsEntity(A));
        assertTrue(nsSupE.containsEntity(B));
        assertTrue(nsSupE.containsEntity(G));
        assertTrue(nsSupE.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(D, false);
        assertNotNull(nsSupD);
        assertEquals(2, nsSupD.getNodes().size());
        assertTrue(nsSupD.containsEntity(A));
        assertTrue(nsSupD.containsEntity(B));
        assertTrue(nsSupD.containsEntity(G));
        assertTrue(nsSupD.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(F, false);
        assertNotNull(nsSupF);
        assertEquals(2, nsSupF.getNodes().size());
        assertTrue(nsSupF.containsEntity(A));
        assertTrue(nsSupF.containsEntity(B));
        assertTrue(nsSupF.containsEntity(G));
        assertTrue(nsSupF.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(K, false);
        assertNotNull(nsSupK);
        assertEquals(5, nsSupK.getNodes().size());
        assertTrue(nsSupK.containsEntity(E));
        assertTrue(nsSupK.containsEntity(D));
        assertTrue(nsSupK.containsEntity(F));
        assertTrue(nsSupK.containsEntity(C));
        assertTrue(nsSupK.containsEntity(A));
        assertTrue(nsSupK.containsEntity(B));
        assertTrue(nsSupK.containsEntity(G));
        assertTrue(nsSupK.containsEntity(OWLThing()));
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(OWLNothing(), false);
        assertNotNull(nsSupBot);
        assertEquals(5, nsSupBot.getNodes().size());
        assertTrue(nsSupBot.containsEntity(E));
        assertTrue(nsSupBot.containsEntity(D));
        assertTrue(nsSupBot.containsEntity(F));
        assertTrue(nsSupBot.containsEntity(C));
        assertTrue(nsSupBot.containsEntity(A));
        assertTrue(nsSupBot.containsEntity(B));
        assertTrue(nsSupBot.containsEntity(G));
        assertTrue(nsSupBot.containsEntity(OWLThing()));
    }

    @Test
    void testGetSubClassesDirect() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), true);
        assertNotNull(nsSubTop);
        assertEquals(1, nsSubTop.getNodes().size());
        assertTrue(nsSubTop.containsEntity(A));
        assertTrue(nsSubTop.containsEntity(B));
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(G, true);
        assertNotNull(nsSubG);
        assertEquals(1, nsSubG.getNodes().size());
        assertTrue(nsSubG.containsEntity(A));
        assertTrue(nsSubG.containsEntity(B));
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(A, true);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(2, nsSubA.getNodes().size());
        assertTrue(nsSubA.containsEntity(C));
        assertTrue(nsSubA.containsEntity(D));
        assertTrue(nsSubA.containsEntity(F));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(B, true);
        assertNotNull(nsSubB);
        assertEquals(2, nsSubB.getNodes().size());
        assertTrue(nsSubB.containsEntity(C));
        assertTrue(nsSubB.containsEntity(D));
        assertTrue(nsSubB.containsEntity(F));
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(C, true);
        assertNotNull(nsSubC);
        assertEquals(1, nsSubC.getNodes().size());
        assertTrue(nsSubC.containsEntity(E));
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(E, true);
        assertNotNull(nsSubE);
        assertEquals(1, nsSubE.getNodes().size());
        assertTrue(nsSubE.containsEntity(K));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(D, true);
        assertNotNull(nsSubD);
        assertEquals(1, nsSubD.getNodes().size());
        assertTrue(nsSubD.containsEntity(K));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(F, true);
        assertNotNull(nsSubF);
        assertEquals(1, nsSubF.getNodes().size());
        assertTrue(nsSubF.containsEntity(K));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(K, true);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(), true);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    @Test
    void testGetSubClasses() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(OWLThing(), false);
        assertNotNull(nsSubTop);
        assertEquals(5, nsSubTop.getNodes().size());
        assertTrue(nsSubTop.containsEntity(A));
        assertTrue(nsSubTop.containsEntity(B));
        assertTrue(nsSubTop.containsEntity(C));
        assertTrue(nsSubTop.containsEntity(D));
        assertTrue(nsSubTop.containsEntity(F));
        assertTrue(nsSubTop.containsEntity(E));
        assertTrue(nsSubTop.containsEntity(K));
        assertTrue(nsSubTop.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(G, false);
        assertNotNull(nsSubG);
        assertEquals(5, nsSubG.getNodes().size());
        assertTrue(nsSubG.containsEntity(A));
        assertTrue(nsSubG.containsEntity(B));
        assertTrue(nsSubG.containsEntity(C));
        assertTrue(nsSubG.containsEntity(D));
        assertTrue(nsSubG.containsEntity(F));
        assertTrue(nsSubG.containsEntity(E));
        assertTrue(nsSubG.containsEntity(K));
        assertTrue(nsSubG.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(A, false);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertEquals(4, nsSubA.getNodes().size());
        assertTrue(nsSubA.containsEntity(C));
        assertTrue(nsSubA.containsEntity(D));
        assertTrue(nsSubA.containsEntity(F));
        assertTrue(nsSubA.containsEntity(E));
        assertTrue(nsSubA.containsEntity(K));
        assertTrue(nsSubA.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(B, false);
        assertNotNull(nsSubB);
        assertEquals(4, nsSubB.getNodes().size());
        assertTrue(nsSubB.containsEntity(C));
        assertTrue(nsSubB.containsEntity(D));
        assertTrue(nsSubB.containsEntity(F));
        assertTrue(nsSubB.containsEntity(E));
        assertTrue(nsSubB.containsEntity(K));
        assertTrue(nsSubB.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(C, false);
        assertNotNull(nsSubC);
        assertEquals(2, nsSubC.getNodes().size());
        assertTrue(nsSubC.containsEntity(E));
        assertTrue(nsSubC.containsEntity(K));
        assertTrue(nsSubC.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(E, false);
        assertNotNull(nsSubE);
        assertEquals(1, nsSubE.getNodes().size());
        assertTrue(nsSubE.containsEntity(K));
        assertTrue(nsSubE.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(D, false);
        assertNotNull(nsSubD);
        assertEquals(1, nsSubD.getNodes().size());
        assertTrue(nsSubD.containsEntity(K));
        assertTrue(nsSubD.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(F, false);
        assertNotNull(nsSubF);
        assertEquals(1, nsSubF.getNodes().size());
        assertTrue(nsSubF.containsEntity(K));
        assertTrue(nsSubF.containsEntity(OWLNothing()));
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(K, false);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());
        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(OWLNothing(), false);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }

    @Test
    void testIsSatisfiable() {
        assertTrue(reasoner.isSatisfiable(OWLThing()));
        assertTrue(reasoner.isSatisfiable(G));
        assertTrue(reasoner.isSatisfiable(A));
        assertTrue(reasoner.isSatisfiable(B));
        assertTrue(reasoner.isSatisfiable(C));
        assertTrue(reasoner.isSatisfiable(D));
        assertTrue(reasoner.isSatisfiable(E));
        assertFalse(reasoner.isSatisfiable(OWLNothing()));
        assertFalse(reasoner.isSatisfiable(K));
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
        NodeSet<OWLObjectPropertyExpression> nsSubTop =
            reasoner.getSubObjectProperties(df.getOWLTopObjectProperty(), true);
        assertNotNull(nsSubTop);
        assertEquals(2, nsSubTop.getNodes().size());
        assertTrue(nsSubTop.containsEntity(R));
        assertTrue(nsSubTop.containsEntity(S));
        assertTrue(nsSubTop.containsEntity(R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(S.getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner.getSubObjectProperties(R, true);
        assertNotNull(nsSubR);
        assertEquals(1, nsSubR.getNodes().size());
        assertTrue(nsSubR.containsEntity(P));
        assertTrue(nsSubR.containsEntity(Q));
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus =
            reasoner.getSubObjectProperties(R.getInverseProperty(), true);
        assertNotNull(nsSubRMinus);
        assertEquals(1, nsSubRMinus.getNodes().size());
        assertTrue(nsSubRMinus.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(Q.getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus =
            reasoner.getSubObjectProperties(S.getInverseProperty(), true);
        assertNotNull(nsSubSMinus);
        assertEquals(1, nsSubSMinus.getNodes().size());
        assertTrue(nsSubSMinus.containsEntity(P));
        assertTrue(nsSubSMinus.containsEntity(Q));
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner.getSubObjectProperties(S, true);
        assertNotNull(nsSubS);
        assertEquals(1, nsSubS.getNodes().size());
        assertTrue(nsSubS.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(Q.getInverseProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner.getSubObjectProperties(P, true);
        assertNotNull(nsSubP);
        assertEquals(1, nsSubP.getNodes().size());
        assertTrue(nsSubP.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner.getSubObjectProperties(Q, true);
        assertNotNull(nsSubQ);
        assertEquals(1, nsSubQ.getNodes().size());
        assertTrue(nsSubQ.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus =
            reasoner.getSubObjectProperties(P.getInverseProperty(), true);
        assertNotNull(nsSubPMinus);
        assertEquals(1, nsSubPMinus.getNodes().size());
        assertTrue(nsSubPMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus =
            reasoner.getSubObjectProperties(Q.getInverseProperty(), true);
        assertNotNull(nsSubQMinus);
        assertEquals(1, nsSubQMinus.getNodes().size());
        assertTrue(nsSubQMinus.containsEntity(df.getOWLBottomObjectProperty()));
    }

    @Test
    void testGetSubObjectProperties() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop =
            reasoner.getSubObjectProperties(df.getOWLTopObjectProperty(), false);
        assertNotNull(nsSubTop);
        assertEquals(5, nsSubTop.getNodes().size());
        assertTrue(nsSubTop.containsEntity(R));
        assertTrue(nsSubTop.containsEntity(S));
        assertTrue(nsSubTop.containsEntity(P));
        assertTrue(nsSubTop.containsEntity(Q));
        assertTrue(nsSubTop.containsEntity(R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(R.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(Q.getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner.getSubObjectProperties(R, false);
        assertNotNull(nsSubR);
        assertEquals(2, nsSubR.getNodes().size());
        assertTrue(nsSubR.containsEntity(P));
        assertTrue(nsSubR.containsEntity(Q));
        assertTrue(nsSubR.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus =
            reasoner.getSubObjectProperties(R.getInverseProperty(), false);
        assertNotNull(nsSubRMinus);
        assertEquals(2, nsSubRMinus.getNodes().size());
        assertTrue(nsSubRMinus.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(Q.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus =
            reasoner.getSubObjectProperties(S.getInverseProperty(), false);
        assertNotNull(nsSubSMinus);
        assertEquals(2, nsSubSMinus.getNodes().size());
        assertTrue(nsSubRMinus.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(Q.getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner.getSubObjectProperties(S, false);
        assertNotNull(nsSubS);
        assertEquals(2, nsSubS.getNodes().size());
        assertTrue(nsSubS.containsEntity(P.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(Q.getInverseProperty()));
        assertTrue(nsSubS.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner.getSubObjectProperties(P, false);
        assertNotNull(nsSubP);
        assertEquals(1, nsSubP.getNodes().size());
        assertTrue(nsSubP.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner.getSubObjectProperties(Q, false);
        assertNotNull(nsSubQ);
        assertEquals(1, nsSubQ.getNodes().size());
        assertTrue(nsSubQ.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus =
            reasoner.getSubObjectProperties(P.getInverseProperty(), false);
        assertNotNull(nsSubPMinus);
        assertEquals(1, nsSubPMinus.getNodes().size());
        assertTrue(nsSubPMinus.containsEntity(df.getOWLBottomObjectProperty()));
        NodeSet<OWLObjectPropertyExpression> nsSubQMinus =
            reasoner.getSubObjectProperties(Q.getInverseProperty(), false);
        assertNotNull(nsSubQMinus);
        assertEquals(1, nsSubQMinus.getNodes().size());
        assertTrue(nsSubQMinus.containsEntity(df.getOWLBottomObjectProperty()));
    }

    @Test
    void testGetRootOntology() {
        assertEquals(reasoner.getRootOntology(), ont);
    }
}
