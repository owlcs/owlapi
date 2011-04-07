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

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Jul-2010
 * <p>
 * This test case creates a small ontology and tests the getters in the reasoner interface.  The test ontology
 * isn't designed to test the correctness of reasoning results, rather it is designed to test the reasoner returns
 * the results in the form required by the OWL API reasoner interface.
 */
public abstract class AbstractReasonerTestCase extends AbstractOWLAPITestCase {

    private OWLReasonerFactory reasonerFactory;

    private OWLOntology ont;

    private OWLReasoner reasoner;

    public AbstractReasonerTestCase(OWLReasonerFactory reasonerFactory) {
        this.reasonerFactory = reasonerFactory;
    }

    private void createOntology() {
        ont = getOWLOntology("ont");
        OWLClass clsA = getClsA();
        OWLClass clsB = getClsB();
        OWLClass clsC = getClsC();
        OWLClass clsD = getClsD();
        OWLClass clsE = getClsE();
        OWLClass clsF = getClsF();
        OWLClass clsG = getClsG();
        OWLClass clsK = getClsK();

        OWLOntologyManager man = ont.getOWLOntologyManager();
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsG, getFactory().getOWLThing()));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(getFactory().getOWLThing(), clsG));
        man.addAxiom(ont, getFactory().getOWLEquivalentClassesAxiom(clsA, clsB));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsC, clsB));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsD, clsA));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsD, clsF));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsF, clsD));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsE, clsC));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsK, clsD));
        man.addAxiom(ont, getFactory().getOWLEquivalentClassesAxiom(clsK, getFactory().getOWLNothing()));

        OWLObjectPropertyExpression propP = getPropP();
        OWLObjectPropertyExpression propQ = getPropQ();
        OWLObjectPropertyExpression propR = getPropR();
        OWLObjectPropertyExpression propS = getPropS();

        man.addAxiom(ont, getFactory().getOWLEquivalentObjectPropertiesAxiom(propP, propQ));
        man.addAxiom(ont, getFactory().getOWLSubObjectPropertyOfAxiom(propP, propR));
        man.addAxiom(ont, getFactory().getOWLInverseObjectPropertiesAxiom(propR, propS));
    }

    private OWLObjectProperty getPropS() {
        return getOWLObjectProperty("s");
    }

    private OWLObjectProperty getPropR() {
        return getOWLObjectProperty("r");
    }

    private OWLObjectProperty getPropQ() {
        return getOWLObjectProperty("q");
    }

    private OWLObjectProperty getPropP() {
        return getOWLObjectProperty("p");
    }

    private OWLClass getClsK() {
        return getOWLClass("K");
    }

    private OWLClass getClsG() {
        return getOWLClass("G");
    }

    private OWLClass getClsF() {
        return getOWLClass("F");
    }

    private OWLClass getClsE() {
        return getOWLClass("E");
    }

    private OWLClass getClsD() {
        return getOWLClass("D");
    }

    private OWLClass getClsC() {
        return getOWLClass("C");
    }

    private OWLClass getClsB() {
        return getOWLClass("B");
    }

    private OWLClass getClsA() {
        return getOWLClass("A");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createOntology();
        reasoner = reasonerFactory.createReasoner(ont);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        reasoner.dispose();
    }

    public void testGetName() {
        assertNotNull(reasoner.getReasonerName());
    }

    public void testGetVersion() {
        assertNotNull(reasoner.getReasonerVersion());
    }

    public void testGetRootOntology() {
        assertEquals(reasoner.getRootOntology(), ont);
    }

    public void testGetTopClassNode() {
        Node<OWLClass> node = reasoner.getTopClassNode();
        assertTrue(node.isTopNode());
        assertFalse(node.isBottomNode());
        assertTrue(node.contains(getFactory().getOWLThing()));
        assertTrue(node.contains(getClsG()));
        assertTrue(node.getSize() == 2);
        assertTrue(node.getEntities().size() == 2);
        assertTrue(node.getEntitiesMinusTop().size() == 1);
        assertTrue(node.getEntitiesMinusTop().contains(getClsG()));
    }

    public void testGetBottomClassNode() {
        Node<OWLClass> node = reasoner.getBottomClassNode();
        assertTrue(node.isBottomNode());
        assertFalse(node.isTopNode());
        assertTrue(node.contains(getFactory().getOWLNothing()));
        assertTrue(node.contains(getClsK()));
        assertTrue(node.getSize() == 2);
        assertTrue(node.getEntities().size() == 2);
        assertTrue(node.getEntitiesMinusBottom().size() == 1);
        assertTrue(node.getEntitiesMinusBottom().contains(getClsK()));
    }

    public void testGetEquivalentClasses() {

        Node<OWLClass> nTop = reasoner.getEquivalentClasses(getFactory().getOWLThing());
        assertNotNull(nTop);
        assertTrue(nTop.getSize() == 2);
        assertTrue(nTop.contains(getFactory().getOWLThing()));
        assertTrue(nTop.contains(getClsG()));
        
        Node<OWLClass> nG = reasoner.getEquivalentClasses(getClsG());
        assertNotNull(nG);
        assertTrue(nG.getSize() == 2);
        assertTrue(nG.contains(getFactory().getOWLThing()));
        assertTrue(nG.contains(getClsG()));

        assertEquals(nTop, nG);

        
        Node<OWLClass> nA = reasoner.getEquivalentClasses(getClsA());
        assertNotNull(nA);
        assertTrue(nA.getSize() == 2);
        assertTrue(nA.contains(getClsA()));
        assertTrue(nA.contains(getClsB()));
        
        Node<OWLClass> nB = reasoner.getEquivalentClasses(getClsB());
        assertNotNull(nB);
        assertTrue(nB.getSize() == 2);
        assertTrue(nB.contains(getClsA()));
        assertTrue(nB.contains(getClsB()));
        
        assertEquals(nA, nB);
        
        Node<OWLClass> nC = reasoner.getEquivalentClasses(getClsC());
        assertNotNull(nC);
        assertTrue(nC.getSize() == 1);
        assertTrue(nC.contains(getClsC()));
        assertTrue(nC.getRepresentativeElement().equals(getClsC()));

        Node<OWLClass> nE = reasoner.getEquivalentClasses(getClsE());
        assertNotNull(nE);
        assertTrue(nE.getSize() == 1);
        assertTrue(nE.contains(getClsE()));
        assertTrue(nE.getRepresentativeElement().equals(getClsE()));

        
        Node<OWLClass> nD = reasoner.getEquivalentClasses(getClsD());
        assertNotNull(nD);
        assertTrue(nD.getSize() == 2);
        assertTrue(nD.contains(getClsD()));
        assertTrue(nD.contains(getClsF()));
        
        Node<OWLClass> nF = reasoner.getEquivalentClasses(getClsF());
        assertNotNull(nF);
        assertTrue(nF.getSize() == 2);
        assertTrue(nF.contains(getClsD()));
        assertTrue(nF.contains(getClsF()));
        
        assertEquals(nD, nF);
                
        
        Node<OWLClass> nBot = reasoner.getEquivalentClasses(getFactory().getOWLNothing());
        assertNotNull(nBot);
        assertTrue(nBot.getSize() == 2);
        assertTrue(nBot.contains(getFactory().getOWLNothing()));
        assertTrue(nBot.contains(getClsK()));
        
        Node<OWLClass> nK = reasoner.getEquivalentClasses(getClsK());
        assertNotNull(nK);
        assertTrue(nK.getSize() == 2);
        assertTrue(nBot.contains(getFactory().getOWLNothing()));
        assertTrue(nBot.contains(getClsK()));
        
        assertEquals(nBot, nK);
                
        

    }

    public void testGetSuperClassesDirect() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(getFactory().getOWLThing(), true);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());

        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(getClsG(), true);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());

        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(getClsA(), true);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertTrue(nsSupA.getNodes().size() == 1);
        assertTrue(nsSupA.containsEntity(getFactory().getOWLThing()));
        assertTrue(nsSupA.containsEntity(getClsG()));
        assertTrue(nsSupA.isTopSingleton());
    
        
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(getClsB(), true);
        assertNotNull(nsSupB);
        assertTrue(nsSupB.getNodes().size() == 1);
        assertTrue(nsSupB.containsEntity(getFactory().getOWLThing()));
        assertTrue(nsSupB.containsEntity(getClsG()));
        assertTrue(nsSupB.isTopSingleton());
        
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(getClsC(), true);
        assertNotNull(nsSupC);
        assertTrue(nsSupC.getNodes().size() == 1);
        assertTrue(nsSupC.containsEntity(getClsA()));
        assertTrue(nsSupC.containsEntity(getClsB()));
        
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(getClsE(), true);
        assertNotNull(nsSupE);
        assertTrue(nsSupE.getNodes().size() == 1);
        assertTrue(nsSupE.containsEntity(getClsC()));
                
        
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(getClsD(), true);
        assertNotNull(nsSupD);
        assertTrue(nsSupD.getNodes().size() == 1);
        assertTrue(nsSupD.containsEntity(getClsA()));
        assertTrue(nsSupD.containsEntity(getClsB()));
        
        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(getClsF(), true);
        assertNotNull(nsSupF);
        assertTrue(nsSupF.getNodes().size() == 1);
        assertTrue(nsSupF.containsEntity(getClsA()));
        assertTrue(nsSupF.containsEntity(getClsB()));
        
        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(getClsK(), true);
        assertNotNull(nsSupK);
        assertTrue(nsSupK.getNodes().size() == 2);
        assertTrue(nsSupK.containsEntity(getClsE()));
        assertTrue(nsSupK.containsEntity(getClsD()));
        assertTrue(nsSupK.containsEntity(getClsF()));
        
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(getFactory().getOWLNothing(), true);
        assertNotNull(nsSupBot);
        assertTrue(nsSupBot.getNodes().size() == 2);
        assertTrue(nsSupBot.containsEntity(getClsE()));
        assertTrue(nsSupBot.containsEntity(getClsD()));
        assertTrue(nsSupBot.containsEntity(getClsF()));
                
        
    }


    public void testGetSuperClasses() {
        NodeSet<OWLClass> nsSupTop = reasoner.getSuperClasses(getFactory().getOWLThing(), false);
        assertNotNull(nsSupTop);
        assertTrue(nsSupTop.isEmpty());

        NodeSet<OWLClass> nsSupG = reasoner.getSuperClasses(getClsG(), false);
        assertNotNull(nsSupG);
        assertTrue(nsSupG.isEmpty());

        NodeSet<OWLClass> nsSupA = reasoner.getSuperClasses(getClsA(), false);
        assertNotNull(nsSupA);
        assertFalse(nsSupA.isEmpty());
        assertTrue(nsSupA.getNodes().size() == 1);
        assertTrue(nsSupA.containsEntity(getFactory().getOWLThing()));
        assertTrue(nsSupA.containsEntity(getClsG()));
        assertTrue(nsSupA.isTopSingleton());
    
        
        NodeSet<OWLClass> nsSupB = reasoner.getSuperClasses(getClsB(), false);
        assertNotNull(nsSupB);
        assertTrue(nsSupB.getNodes().size() == 1);
        assertTrue(nsSupB.containsEntity(getFactory().getOWLThing()));
        assertTrue(nsSupB.containsEntity(getClsG()));
        assertTrue(nsSupB.isTopSingleton());
        
        NodeSet<OWLClass> nsSupC = reasoner.getSuperClasses(getClsC(), false);
        assertNotNull(nsSupC);
        assertTrue(nsSupC.getNodes().size() == 2);
        assertTrue(nsSupC.containsEntity(getFactory().getOWLThing()));
        assertTrue(nsSupC.containsEntity(getClsG()));
        assertTrue(nsSupC.containsEntity(getClsA()));
        assertTrue(nsSupC.containsEntity(getClsB()));
        
        NodeSet<OWLClass> nsSupE = reasoner.getSuperClasses(getClsE(), false);
        assertNotNull(nsSupE);
        assertTrue(nsSupE.getNodes().size() == 3);
        assertTrue(nsSupE.containsEntity(getClsC()));
        assertTrue(nsSupE.containsEntity(getClsA()));
        assertTrue(nsSupE.containsEntity(getClsB()));
        assertTrue(nsSupE.containsEntity(getClsG()));
        assertTrue(nsSupE.containsEntity(getFactory().getOWLThing()));

        
        NodeSet<OWLClass> nsSupD = reasoner.getSuperClasses(getClsD(), false);
        assertNotNull(nsSupD);
        assertTrue(nsSupD.getNodes().size() == 2);
        assertTrue(nsSupD.containsEntity(getClsA()));
        assertTrue(nsSupD.containsEntity(getClsB()));
        assertTrue(nsSupD.containsEntity(getClsG()));
        assertTrue(nsSupD.containsEntity(getFactory().getOWLThing()));

        NodeSet<OWLClass> nsSupF = reasoner.getSuperClasses(getClsF(), false);
        assertNotNull(nsSupF);
        assertTrue(nsSupF.getNodes().size() == 2);
        assertTrue(nsSupF.containsEntity(getClsA()));
        assertTrue(nsSupF.containsEntity(getClsB()));
        assertTrue(nsSupF.containsEntity(getClsG()));
        assertTrue(nsSupF.containsEntity(getFactory().getOWLThing()));

        NodeSet<OWLClass> nsSupK = reasoner.getSuperClasses(getClsK(), false);
        assertNotNull(nsSupK);
        assertTrue(nsSupK.getNodes().size() == 5);
        assertTrue(nsSupK.containsEntity(getClsE()));
        assertTrue(nsSupK.containsEntity(getClsD()));
        assertTrue(nsSupK.containsEntity(getClsF()));
        assertTrue(nsSupK.containsEntity(getClsC()));
        assertTrue(nsSupK.containsEntity(getClsA()));
        assertTrue(nsSupK.containsEntity(getClsB()));
        assertTrue(nsSupK.containsEntity(getClsG()));
        assertTrue(nsSupK.containsEntity(getFactory().getOWLThing()));
        
        NodeSet<OWLClass> nsSupBot = reasoner.getSuperClasses(getFactory().getOWLNothing(), false);
        assertNotNull(nsSupBot);
        assertTrue(nsSupBot.getNodes().size() == 5);
        assertTrue(nsSupBot.containsEntity(getClsE()));
        assertTrue(nsSupBot.containsEntity(getClsD()));
        assertTrue(nsSupBot.containsEntity(getClsF()));
        assertTrue(nsSupBot.containsEntity(getClsC()));
        assertTrue(nsSupBot.containsEntity(getClsA()));
        assertTrue(nsSupBot.containsEntity(getClsB()));
        assertTrue(nsSupBot.containsEntity(getClsG()));
        assertTrue(nsSupBot.containsEntity(getFactory().getOWLThing()));
    }

    public void testGetSubClassesDirect() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(getFactory().getOWLThing(), true);
        assertNotNull(nsSubTop);
        assertTrue(nsSubTop.getNodes().size() == 1);
        assertTrue(nsSubTop.containsEntity(getClsA()));
        assertTrue(nsSubTop.containsEntity(getClsB()));

        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(getClsG(), true);
        assertNotNull(nsSubG);
        assertTrue(nsSubG.getNodes().size() == 1);
        assertTrue(nsSubG.containsEntity(getClsA()));
        assertTrue(nsSubG.containsEntity(getClsB()));

        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(getClsA(), true);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertTrue(nsSubA.getNodes().size() == 2);
        assertTrue(nsSubA.containsEntity(getClsC()));
        assertTrue(nsSubA.containsEntity(getClsD()));
        assertTrue(nsSubA.containsEntity(getClsF()));
    
        
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(getClsB(), true);
        assertNotNull(nsSubB);
        assertTrue(nsSubB.getNodes().size() == 2);
        assertTrue(nsSubB.containsEntity(getClsC()));
        assertTrue(nsSubB.containsEntity(getClsD()));
        assertTrue(nsSubB.containsEntity(getClsF()));
        
        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(getClsC(), true);
        assertNotNull(nsSubC);
        assertTrue(nsSubC.getNodes().size() == 1);
        assertTrue(nsSubC.containsEntity(getClsE()));
        
        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(getClsE(), true);
        assertNotNull(nsSubE);
        assertTrue(nsSubE.getNodes().size() == 1);
        assertTrue(nsSubE.containsEntity(getClsK()));
        assertTrue(nsSubE.containsEntity(getFactory().getOWLNothing()));

        
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(getClsD(), true);
        assertNotNull(nsSubD);
        assertTrue(nsSubD.getNodes().size() == 1);
        assertTrue(nsSubD.containsEntity(getClsK()));
        assertTrue(nsSubD.containsEntity(getFactory().getOWLNothing()));
        
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(getClsF(), true);
        assertNotNull(nsSubF);
        assertTrue(nsSubF.getNodes().size() == 1);
        assertTrue(nsSubF.containsEntity(getClsK()));
        assertTrue(nsSubF.containsEntity(getFactory().getOWLNothing()));
        
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(getClsK(), true);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());

        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(getFactory().getOWLNothing(), true);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());
    }


    public void testGetSubClasses() {
        NodeSet<OWLClass> nsSubTop = reasoner.getSubClasses(getFactory().getOWLThing(), false);
        assertNotNull(nsSubTop);
        assertTrue(nsSubTop.getNodes().size() == 5);
        assertTrue(nsSubTop.containsEntity(getClsA()));
        assertTrue(nsSubTop.containsEntity(getClsB()));
        assertTrue(nsSubTop.containsEntity(getClsC()));
        assertTrue(nsSubTop.containsEntity(getClsD()));
        assertTrue(nsSubTop.containsEntity(getClsF()));
        assertTrue(nsSubTop.containsEntity(getClsE()));
        assertTrue(nsSubTop.containsEntity(getClsK()));
        assertTrue(nsSubTop.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> nsSubG = reasoner.getSubClasses(getClsG(), false);
        assertNotNull(nsSubG);
        assertTrue(nsSubG.getNodes().size() == 5);
        assertTrue(nsSubG.containsEntity(getClsA()));
        assertTrue(nsSubG.containsEntity(getClsB()));
        assertTrue(nsSubG.containsEntity(getClsC()));
        assertTrue(nsSubG.containsEntity(getClsD()));
        assertTrue(nsSubG.containsEntity(getClsF()));
        assertTrue(nsSubG.containsEntity(getClsE()));
        assertTrue(nsSubG.containsEntity(getClsK()));
        assertTrue(nsSubG.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> nsSubA = reasoner.getSubClasses(getClsA(), false);
        assertNotNull(nsSubA);
        assertFalse(nsSubG.isEmpty());
        assertTrue(nsSubA.getNodes().size() == 4);
        assertTrue(nsSubA.containsEntity(getClsC()));
        assertTrue(nsSubA.containsEntity(getClsD()));
        assertTrue(nsSubA.containsEntity(getClsF()));
        assertTrue(nsSubA.containsEntity(getClsE()));
        assertTrue(nsSubA.containsEntity(getClsK()));
        assertTrue(nsSubA.containsEntity(getFactory().getOWLNothing()));

        
        NodeSet<OWLClass> nsSubB = reasoner.getSubClasses(getClsB(), false);
        assertNotNull(nsSubB);
        assertTrue(nsSubB.getNodes().size() == 4);
        assertTrue(nsSubB.containsEntity(getClsC()));
        assertTrue(nsSubB.containsEntity(getClsD()));
        assertTrue(nsSubB.containsEntity(getClsF()));
        assertTrue(nsSubB.containsEntity(getClsE()));
        assertTrue(nsSubB.containsEntity(getClsK()));
        assertTrue(nsSubB.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> nsSubC = reasoner.getSubClasses(getClsC(), false);
        assertNotNull(nsSubC);
        assertTrue(nsSubC.getNodes().size() == 2);
        assertTrue(nsSubC.containsEntity(getClsE()));
        assertTrue(nsSubC.containsEntity(getClsK()));
        assertTrue(nsSubC.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> nsSubE = reasoner.getSubClasses(getClsE(), false);
        assertNotNull(nsSubE);
        assertTrue(nsSubE.getNodes().size() == 1);
        assertTrue(nsSubE.containsEntity(getClsK()));
        assertTrue(nsSubE.containsEntity(getFactory().getOWLNothing()));

        
        NodeSet<OWLClass> nsSubD = reasoner.getSubClasses(getClsD(), false);
        assertNotNull(nsSubD);
        assertTrue(nsSubD.getNodes().size() == 1);
        assertTrue(nsSubD.containsEntity(getClsK()));
        assertTrue(nsSubD.containsEntity(getFactory().getOWLNothing()));
        
        NodeSet<OWLClass> nsSubF = reasoner.getSubClasses(getClsF(), false);
        assertNotNull(nsSubF);
        assertTrue(nsSubF.getNodes().size() == 1);
        assertTrue(nsSubF.containsEntity(getClsK()));
        assertTrue(nsSubF.containsEntity(getFactory().getOWLNothing()));
        
        NodeSet<OWLClass> nsSubK = reasoner.getSubClasses(getClsK(), false);
        assertNotNull(nsSubK);
        assertTrue(nsSubK.isEmpty());

        NodeSet<OWLClass> nsSubBot = reasoner.getSubClasses(getFactory().getOWLNothing(), false);
        assertNotNull(nsSubBot);
        assertTrue(nsSubBot.isEmpty());

    }

    public void testIsSatisfiable() {
        assertTrue(reasoner.isSatisfiable(getFactory().getOWLThing()));
        assertTrue(reasoner.isSatisfiable(getClsG()));
        assertTrue(reasoner.isSatisfiable(getClsA()));
        assertTrue(reasoner.isSatisfiable(getClsB()));
        assertTrue(reasoner.isSatisfiable(getClsC()));
        assertTrue(reasoner.isSatisfiable(getClsD()));
        assertTrue(reasoner.isSatisfiable(getClsE()));
        assertFalse(reasoner.isSatisfiable(getFactory().getOWLNothing()));
        assertFalse(reasoner.isSatisfiable(getClsK()));
    }



    public void testComputeClassHierarchy() {
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        assertTrue(reasoner.isPrecomputed(InferenceType.CLASS_HIERARCHY));
    }


    public void testGetTopObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner.getTopObjectPropertyNode();
        assertNotNull(node);
        assertTrue(node.isTopNode());
    }

    public void testGetBottomObjectPropertyNode() {
        Node<OWLObjectPropertyExpression> node = reasoner.getBottomObjectPropertyNode();
        assertNotNull(node);
        assertTrue(node.isBottomNode());
    }

    public void testGetSubObjectPropertiesDirect() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop = reasoner.getSubObjectProperties(getFactory().getOWLTopObjectProperty(), true);
        assertNotNull(nsSubTop);
        assertTrue(nsSubTop.getNodes().size() == 2);
        assertTrue(nsSubTop.containsEntity(getPropR()));
        assertTrue(nsSubTop.containsEntity(getPropS()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropS().getInverseProperty()));
    
    
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner.getSubObjectProperties(getPropR(), true);
        assertNotNull(nsSubR);
        assertTrue(nsSubR.getNodes().size() == 1);
        assertTrue(nsSubR.containsEntity(getPropP()));
        assertTrue(nsSubR.containsEntity(getPropQ()));
        
        NodeSet<OWLObjectPropertyExpression> nsSubRMinus = reasoner.getSubObjectProperties(getPropR().getInverseProperty(), true);
        assertNotNull(nsSubRMinus);
        assertTrue(nsSubRMinus.getNodes().size() == 1);
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));

        
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus = reasoner.getSubObjectProperties(getPropS().getInverseProperty(), true);
        assertNotNull(nsSubSMinus);
        assertTrue(nsSubSMinus.getNodes().size() == 1);
        assertTrue(nsSubSMinus.containsEntity(getPropP()));
        assertTrue(nsSubSMinus.containsEntity(getPropQ()));
        
        
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner.getSubObjectProperties(getPropS(), true);
        assertNotNull(nsSubS);
        assertTrue(nsSubS.getNodes().size() == 1);
        assertTrue(nsSubS.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(getPropQ().getInverseProperty()));

        
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner.getSubObjectProperties(getPropP(), true);
        assertNotNull(nsSubP);
        assertTrue(nsSubP.getNodes().size() == 1);
        assertTrue(nsSubP.containsEntity(getFactory().getOWLBottomObjectProperty()));
                
        
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner.getSubObjectProperties(getPropQ(), true);
        assertNotNull(nsSubQ);
        assertTrue(nsSubQ.getNodes().size() == 1);
        assertTrue(nsSubQ.containsEntity(getFactory().getOWLBottomObjectProperty()));
        
        
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus = reasoner.getSubObjectProperties(getPropP().getInverseProperty(), true);
        assertNotNull(nsSubPMinus);
        assertTrue(nsSubPMinus.getNodes().size() == 1);
        assertTrue(nsSubPMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));

        NodeSet<OWLObjectPropertyExpression> nsSubQMinus = reasoner.getSubObjectProperties(getPropQ().getInverseProperty(), true);
        assertNotNull(nsSubQMinus);
        assertTrue(nsSubQMinus.getNodes().size() == 1);
        assertTrue(nsSubQMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));
        
    }
    
    
    public void testGetSubObjectProperties() {
        NodeSet<OWLObjectPropertyExpression> nsSubTop = reasoner.getSubObjectProperties(getFactory().getOWLTopObjectProperty(), false);
        assertNotNull(nsSubTop);
        assertTrue(nsSubTop.getNodes().size() == 5);
        assertTrue(nsSubTop.containsEntity(getPropR()));
        assertTrue(nsSubTop.containsEntity(getPropS()));
        assertTrue(nsSubTop.containsEntity(getPropP()));
        assertTrue(nsSubTop.containsEntity(getPropQ()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropR().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubTop.containsEntity(getFactory().getOWLBottomObjectProperty()));

    
        NodeSet<OWLObjectPropertyExpression> nsSubR = reasoner.getSubObjectProperties(getPropR(), false);
        assertNotNull(nsSubR);
        assertTrue(nsSubR.getNodes().size() == 2);
        assertTrue(nsSubR.containsEntity(getPropP()));
        assertTrue(nsSubR.containsEntity(getPropQ()));
        assertTrue(nsSubR.containsEntity(getFactory().getOWLBottomObjectProperty()));


        NodeSet<OWLObjectPropertyExpression> nsSubRMinus = reasoner.getSubObjectProperties(getPropR().getInverseProperty(), false);
        assertNotNull(nsSubRMinus);
        assertTrue(nsSubRMinus.getNodes().size() == 2);
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));

        
        NodeSet<OWLObjectPropertyExpression> nsSubSMinus = reasoner.getSubObjectProperties(getPropS().getInverseProperty(), false);
        assertNotNull(nsSubSMinus);
        assertTrue(nsSubSMinus.getNodes().size() == 2);
        assertTrue(nsSubRMinus.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubRMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));
        
        
        NodeSet<OWLObjectPropertyExpression> nsSubS = reasoner.getSubObjectProperties(getPropS(), false);
        assertNotNull(nsSubS);
        assertTrue(nsSubS.getNodes().size() == 2);
        assertTrue(nsSubS.containsEntity(getPropP().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(getPropQ().getInverseProperty()));
        assertTrue(nsSubS.containsEntity(getFactory().getOWLBottomObjectProperty()));

        
        NodeSet<OWLObjectPropertyExpression> nsSubP = reasoner.getSubObjectProperties(getPropP(), false);
        assertNotNull(nsSubP);
        assertTrue(nsSubP.getNodes().size() == 1);
        assertTrue(nsSubP.containsEntity(getFactory().getOWLBottomObjectProperty()));
                
        
        NodeSet<OWLObjectPropertyExpression> nsSubQ = reasoner.getSubObjectProperties(getPropQ(), false);
        assertNotNull(nsSubQ);
        assertTrue(nsSubQ.getNodes().size() == 1);
        assertTrue(nsSubQ.containsEntity(getFactory().getOWLBottomObjectProperty()));
        
        
        NodeSet<OWLObjectPropertyExpression> nsSubPMinus = reasoner.getSubObjectProperties(getPropP().getInverseProperty(), false);
        assertNotNull(nsSubPMinus);
        assertTrue(nsSubPMinus.getNodes().size() == 1);
        assertTrue(nsSubPMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));

        NodeSet<OWLObjectPropertyExpression> nsSubQMinus = reasoner.getSubObjectProperties(getPropQ().getInverseProperty(), false);
        assertNotNull(nsSubQMinus);
        assertTrue(nsSubQMinus.getNodes().size() == 1);
        assertTrue(nsSubQMinus.containsEntity(getFactory().getOWLBottomObjectProperty()));
        
    }
}
