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

package org.semanticweb.owlapi.reasoner.test;

import org.semanticweb.owlapi.api.test.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Jul-2010
 */
public class StructuralReasonerTestCase extends AbstractOWLAPITestCase {

    public void testClassHierarchy() {
        OWLClass clsX = getOWLClass("X");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsAp = getOWLClass("Ap");
        OWLClass clsB = getOWLClass("B");
        OWLOntology ont = getOWLOntology("ont");
        OWLOntologyManager man = ont.getOWLOntologyManager();
        man.addAxiom(ont, getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLThing(), clsX));
        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsB, clsA));
        man.addAxiom(ont, getFactory().getOWLEquivalentClassesAxiom(clsA, clsAp));

        StructuralReasoner reasoner = new StructuralReasoner(ont, new SimpleConfiguration(), BufferingMode.NON_BUFFERING);
        testClassHierarchy(reasoner);

        man.addAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLThing()));
        testClassHierarchy(reasoner);

        man.removeAxiom(ont, getFactory().getOWLSubClassOfAxiom(clsA, getFactory().getOWLThing()));
        testClassHierarchy(reasoner);

    }

    private void testClassHierarchy(StructuralReasoner reasoner) {
        OWLClass clsX = getOWLClass("X");
        OWLClass clsA = getOWLClass("A");
        OWLClass clsAp = getOWLClass("Ap");
        OWLClass clsB = getOWLClass("B");

        NodeSet<OWLClass> subsOfA = reasoner.getSubClasses(clsA, true);
        assertTrue(subsOfA.getNodes().size() == 1);
        assertTrue(subsOfA.containsEntity(clsB));

        NodeSet<OWLClass> subsOfAp = reasoner.getSubClasses(clsAp, true);
        assertTrue(subsOfAp.getNodes().size() == 1);
        assertTrue(subsOfAp.containsEntity(clsB));


        Node<OWLClass> topNode = reasoner.getTopClassNode();
        NodeSet<OWLClass> subsOfTop = reasoner.getSubClasses(topNode.getRepresentativeElement(), true);
        assertTrue(subsOfTop.getNodes().size() == 1);
        assertTrue(subsOfTop.containsEntity(clsA));

        NodeSet<OWLClass> descOfTop = reasoner.getSubClasses(topNode.getRepresentativeElement(), false);
        assertTrue(descOfTop.getNodes().size() == 3);
        assertTrue(descOfTop.containsEntity(clsA));
        assertTrue(descOfTop.containsEntity(clsB));
        assertTrue(descOfTop.containsEntity(getFactory().getOWLNothing()));

        NodeSet<OWLClass> supersOfTop = reasoner.getSuperClasses(getFactory().getOWLThing(), false);
        assertTrue(supersOfTop.isEmpty());


        NodeSet<OWLClass> supersOfA = reasoner.getSuperClasses(clsA, false);
        assertTrue(supersOfA.isTopSingleton());
        assertTrue(supersOfA.getNodes().size() == 1);
        assertTrue(supersOfA.containsEntity(getFactory().getOWLThing()));

//        NodeSet<OWLClass> supersOfBottom = reasoner.getSuperClasses(getFactory().getOWLNothing(), true);
//        assertTrue(supersOfBottom.getNodes().size() == 1);
//        assertTrue(supersOfBottom.containsEntity(clsB));

        Node<OWLClass> equivsOfTop = reasoner.getEquivalentClasses(getFactory().getOWLThing());
        assertTrue(equivsOfTop.getEntities().size() == 2);
        assertTrue(equivsOfTop.getEntities().contains(clsX));
    }

}
