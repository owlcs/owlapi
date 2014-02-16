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
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.reasoner.impl.DefaultNode;
import org.semanticweb.owlapi.reasoner.impl.DefaultNodeSet;
import org.semanticweb.owlapi.reasoner.impl.NodeFactory;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNode;
import org.semanticweb.owlapi.reasoner.impl.OWLClassNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLDatatypeNode;
import org.semanticweb.owlapi.reasoner.impl.OWLDatatypeNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNode;
import org.semanticweb.owlapi.reasoner.impl.OWLNamedIndividualNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNode;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLReasonerBase;
import org.semanticweb.owlapi.util.Version;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractReasonerImplTest {
    @Test
    public void shouldTestDefaultNode() throws OWLException {
        DefaultNode<OWLObject> testSubject0 = new DefaultNode<OWLObject>(
                mock(OWLObject.class)) {
            @Override
            protected OWLObject getTopEntity() {
                return null;
            }

            @Override
            protected OWLObject getBottomEntity() {
                return null;
            }
        };
        new DefaultNode<OWLObject>(Utils.mockSet(mock(OWLObject.class))) {
            @Override
            protected OWLObject getTopEntity() {
                return null;
            }

            @Override
            protected OWLObject getBottomEntity() {
                return null;
            }
        };
        testSubject0.add(mock(OWLObject.class));
        boolean result1 = testSubject0.contains(mock(OWLObject.class));
        Iterator<OWLObject> result2 = testSubject0.iterator();
        int result3 = testSubject0.getSize();
        Set<OWLObject> result4 = testSubject0.getEntities();
        boolean result5 = testSubject0.isTopNode();
        boolean result6 = testSubject0.isBottomNode();
        Set<OWLObject> result7 = testSubject0
                .getEntitiesMinus(mock(OWLObject.class));
        Set<OWLObject> result8 = testSubject0.getEntitiesMinusTop();
        Set<OWLObject> result9 = testSubject0.getEntitiesMinusBottom();
        boolean result10 = testSubject0.isSingleton();
        OWLObject result11 = testSubject0.getRepresentativeElement();
    }

    public void shouldTestDefaultNodeSet() throws OWLException {
        DefaultNodeSet<OWLObject> testSubject0 = new DefaultNodeSet<OWLObject>(
                mock(OWLObject.class)) {
            @Override
            protected DefaultNode<OWLObject> getNode(OWLObject entity) {
                return null;
            }

            @Override
            protected DefaultNode<OWLObject> getNode(Set<OWLObject> entities) {
                return null;
            }
        };
        new DefaultNodeSet<OWLObject>(mock(Node.class)) {
            @Override
            protected DefaultNode<OWLObject> getNode(OWLObject entity) {
                return null;
            }

            @Override
            protected DefaultNode<OWLObject> getNode(Set<OWLObject> entities) {
                return null;
            }
        };
        new DefaultNodeSet<OWLObject>(Utils.mockSet(Utils
                .mockNode(OWLObject.class))) {
            @Override
            protected DefaultNode<OWLObject> getNode(OWLObject entity) {
                return null;
            }

            @Override
            protected DefaultNode<OWLObject> getNode(Set<OWLObject> entities) {
                return null;
            }
        };
        new DefaultNodeSet<OWLObject>() {
            @Override
            protected DefaultNode<OWLObject> getNode(OWLObject entity) {
                return null;
            }

            @Override
            protected DefaultNode<OWLObject> getNode(Set<OWLObject> entities) {
                return null;
            }
        };
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLObject>> result2 = testSubject0.iterator();
        boolean result3 = testSubject0.isSingleton();
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLObject>> result8 = testSubject0.getNodes();
        testSubject0.addEntity(mock(OWLObject.class));
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLObject.class)));
        testSubject0.addSameEntities(Utils.mockSet(mock(OWLObject.class)));
        testSubject0.addDifferentEntities(Utils.mockSet(mock(OWLObject.class)));
    }

    @Test
    public void shouldTestNodeFactory() throws OWLException {
        new NodeFactory();
        DefaultNode<OWLClass> result0 = NodeFactory.getOWLClassNode();
        DefaultNode<OWLClass> result1 = NodeFactory
                .getOWLClassNode(mock(OWLClass.class));
        DefaultNode<OWLClass> result2 = NodeFactory.getOWLClassNode(Utils
                .mockSet(mock(OWLClass.class)));
        DefaultNode<OWLClass> result3 = NodeFactory.getOWLClassTopNode();
        DefaultNode<OWLClass> result4 = NodeFactory.getOWLClassBottomNode();
        DefaultNode<OWLObjectPropertyExpression> result5 = NodeFactory
                .getOWLObjectPropertyNode();
        DefaultNode<OWLObjectPropertyExpression> result6 = NodeFactory
                .getOWLObjectPropertyNode(Utils.mockObjectProperty());
        DefaultNode<OWLObjectPropertyExpression> result7 = NodeFactory
                .getOWLObjectPropertyNode(Utils.mockSet(Utils
                        .mockObjectProperty()));
        DefaultNode<OWLObjectPropertyExpression> result8 = NodeFactory
                .getOWLObjectPropertyTopNode();
        DefaultNode<OWLObjectPropertyExpression> result9 = NodeFactory
                .getOWLObjectPropertyBottomNode();
        DefaultNode<OWLDataProperty> result10 = NodeFactory
                .getOWLDataPropertyNode();
        DefaultNode<OWLDataProperty> result11 = NodeFactory
                .getOWLDataPropertyNode(mock(OWLDataProperty.class));
        DefaultNode<OWLDataProperty> result12 = NodeFactory
                .getOWLDataPropertyNode(Utils
                        .mockSet(mock(OWLDataProperty.class)));
        DefaultNode<OWLDataProperty> result13 = NodeFactory
                .getOWLDataPropertyTopNode();
        DefaultNode<OWLDataProperty> result14 = NodeFactory
                .getOWLDataPropertyBottomNode();
        DefaultNode<OWLNamedIndividual> result15 = NodeFactory
                .getOWLNamedIndividualNode();
        DefaultNode<OWLNamedIndividual> result16 = NodeFactory
                .getOWLNamedIndividualNode(mock(OWLNamedIndividual.class));
        DefaultNode<OWLNamedIndividual> result17 = NodeFactory
                .getOWLNamedIndividualNode(Utils
                        .mockSet(mock(OWLNamedIndividual.class)));
    }

    @Test
    public void shouldTestOWLClassNode() throws OWLException {
        OWLClassNode testSubject0 = new OWLClassNode(mock(OWLClass.class));
        new OWLClassNode();
        new OWLClassNode(Utils.mockSet(mock(OWLClass.class)));
        OWLClassNode result0 = OWLClassNode.getTopNode();
        OWLClassNode result1 = OWLClassNode.getBottomNode();
        testSubject0.add(mock(OWLClass.class));
        boolean result3 = testSubject0.contains(mock(OWLClass.class));
        Iterator<OWLClass> result4 = testSubject0.iterator();
        int result5 = testSubject0.getSize();
        Set<OWLClass> result6 = testSubject0.getEntities();
        boolean result7 = testSubject0.isTopNode();
        boolean result8 = testSubject0.isBottomNode();
        Set<OWLClass> result9 = testSubject0
                .getEntitiesMinus(mock(OWLClass.class));
        Set<OWLClass> result10 = testSubject0.getEntitiesMinusTop();
        Set<OWLClass> result11 = testSubject0.getEntitiesMinusBottom();
        boolean result12 = testSubject0.isSingleton();
        OWLObject result13 = testSubject0.getRepresentativeElement();
    }

    @Test
    public void shouldTestOWLClassNodeSet() throws OWLException {
        OWLClassNodeSet testSubject0 = new OWLClassNodeSet();
        new OWLClassNodeSet(mock(OWLClass.class));
        new OWLClassNodeSet(mock(Node.class));
        new OWLClassNodeSet(Utils.mockSet(Utils.mockNode(OWLClass.class)));
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLClass>> result2 = testSubject0.iterator();
        boolean result3 = testSubject0.isSingleton();
        Set<OWLClass> result4 = testSubject0.getFlattened();
        boolean result5 = testSubject0.containsEntity(mock(OWLClass.class));
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLClass>> result8 = testSubject0.getNodes();
        testSubject0.addEntity(mock(OWLClass.class));
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLClass.class)));
        testSubject0.addSameEntities(Utils.mockSet(mock(OWLClass.class)));
        testSubject0.addDifferentEntities(Utils.mockSet(mock(OWLClass.class)));
    }

    @Test
    public void shouldTestOWLDataPropertyNode() throws OWLException {
        OWLDataPropertyNode testSubject0 = new OWLDataPropertyNode();
        new OWLDataPropertyNode(Utils.mockSet(mock(OWLDataProperty.class)));
        new OWLDataPropertyNode(mock(OWLDataProperty.class));
        OWLDataPropertyNode result0 = OWLDataPropertyNode.getTopNode();
        OWLDataPropertyNode result1 = OWLDataPropertyNode.getBottomNode();
        testSubject0.add(mock(OWLDataProperty.class));
        boolean result3 = testSubject0.contains(mock(OWLDataProperty.class));
        Iterator<OWLDataProperty> result4 = testSubject0.iterator();
        int result5 = testSubject0.getSize();
        Set<OWLDataProperty> result6 = testSubject0.getEntities();
        boolean result7 = testSubject0.isTopNode();
        boolean result8 = testSubject0.isBottomNode();
        Set<OWLDataProperty> result9 = testSubject0
                .getEntitiesMinus(mock(OWLDataProperty.class));
        Set<OWLDataProperty> result10 = testSubject0.getEntitiesMinusTop();
        Set<OWLDataProperty> result11 = testSubject0.getEntitiesMinusBottom();
        boolean result12 = testSubject0.isSingleton();
        OWLObject result13 = testSubject0.getRepresentativeElement();
    }

    @Test
    public void shouldTestOWLDataPropertyNodeSet() throws OWLException {
        OWLDataPropertyNodeSet testSubject0 = new OWLDataPropertyNodeSet();
        new OWLDataPropertyNodeSet(mock(OWLDataProperty.class));
        new OWLDataPropertyNodeSet(mock(Node.class));
        new OWLDataPropertyNodeSet(Utils.mockSet(Utils
                .mockNode(OWLDataProperty.class)));
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLDataProperty>> result2 = testSubject0.iterator();
        boolean result3 = testSubject0.isSingleton();
        Set<OWLDataProperty> result4 = testSubject0.getFlattened();
        boolean result5 = testSubject0
                .containsEntity(mock(OWLDataProperty.class));
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLDataProperty>> result8 = testSubject0.getNodes();
        testSubject0.addEntity(mock(OWLDataProperty.class));
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLDataProperty.class)));
        testSubject0
                .addSameEntities(Utils.mockSet(mock(OWLDataProperty.class)));
        testSubject0.addDifferentEntities(Utils
                .mockSet(mock(OWLDataProperty.class)));
    }

    @Test
    public void shouldTestOWLDatatypeNode() throws OWLException {
        OWLDatatypeNode testSubject0 = new OWLDatatypeNode();
        new OWLDatatypeNode(mock(OWLDatatype.class));
        new OWLDatatypeNode(Utils.mockSet(mock(OWLDatatype.class)));
        testSubject0.add(mock(OWLDatatype.class));
        boolean result1 = testSubject0.contains(mock(OWLDatatype.class));
        Iterator<OWLDatatype> result2 = testSubject0.iterator();
        int result3 = testSubject0.getSize();
        Set<OWLDatatype> result4 = testSubject0.getEntities();
        boolean result5 = testSubject0.isTopNode();
        boolean result6 = testSubject0.isBottomNode();
        Set<OWLDatatype> result7 = testSubject0
                .getEntitiesMinus(mock(OWLDatatype.class));
        Set<OWLDatatype> result8 = testSubject0.getEntitiesMinusTop();
        Set<OWLDatatype> result9 = testSubject0.getEntitiesMinusBottom();
        boolean result10 = testSubject0.isSingleton();
        OWLObject result11 = testSubject0.getRepresentativeElement();
    }

    @Test
    public void shouldTestOWLDatatypeNodeSet() throws OWLException {
        OWLDatatypeNodeSet testSubject0 = new OWLDatatypeNodeSet();
        new OWLDatatypeNodeSet(mock(OWLDatatype.class));
        new OWLDatatypeNodeSet(mock(Node.class));
        new OWLDatatypeNodeSet(Utils.mockSet(Utils.mockNode(OWLDatatype.class)));
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLDatatype>> result2 = testSubject0.iterator();
        boolean result3 = testSubject0.isSingleton();
        Set<OWLDatatype> result4 = testSubject0.getFlattened();
        boolean result5 = testSubject0.containsEntity(mock(OWLDatatype.class));
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLDatatype>> result8 = testSubject0.getNodes();
        testSubject0.addEntity(mock(OWLDatatype.class));
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLDatatype.class)));
        testSubject0.addSameEntities(Utils.mockSet(mock(OWLDatatype.class)));
        testSubject0.addDifferentEntities(Utils
                .mockSet(mock(OWLDatatype.class)));
    }

    @Test
    public void shouldTestOWLNamedIndividualNode() throws OWLException {
        OWLNamedIndividualNode testSubject0 = new OWLNamedIndividualNode();
        new OWLNamedIndividualNode(mock(OWLNamedIndividual.class));
        new OWLNamedIndividualNode(
                Utils.mockSet(mock(OWLNamedIndividual.class)));
        testSubject0.add(mock(OWLNamedIndividual.class));
        boolean result1 = testSubject0.contains(mock(OWLNamedIndividual.class));
        Iterator<OWLNamedIndividual> result2 = testSubject0.iterator();
        int result3 = testSubject0.getSize();
        Set<OWLNamedIndividual> result4 = testSubject0.getEntities();
        boolean result5 = testSubject0.isTopNode();
        boolean result6 = testSubject0.isBottomNode();
        Set<OWLNamedIndividual> result7 = testSubject0
                .getEntitiesMinus(mock(OWLNamedIndividual.class));
        Set<OWLNamedIndividual> result8 = testSubject0.getEntitiesMinusTop();
        Set<OWLNamedIndividual> result9 = testSubject0.getEntitiesMinusBottom();
        boolean result10 = testSubject0.isSingleton();
        OWLObject result11 = testSubject0.getRepresentativeElement();
    }

    @Test
    public void shouldTestOWLNamedIndividualNodeSet() throws OWLException {
        OWLNamedIndividualNodeSet testSubject0 = new OWLNamedIndividualNodeSet();
        new OWLNamedIndividualNodeSet(mock(OWLNamedIndividual.class));
        new OWLNamedIndividualNodeSet(mock(Node.class));
        new OWLNamedIndividualNodeSet(Utils.mockSet(Utils
                .mockNode(OWLNamedIndividual.class)));
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLNamedIndividual>> result2 = testSubject0.iterator();
        boolean result3 = testSubject0.isSingleton();
        Set<OWLNamedIndividual> result4 = testSubject0.getFlattened();
        boolean result5 = testSubject0
                .containsEntity(mock(OWLNamedIndividual.class));
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLNamedIndividual>> result8 = testSubject0.getNodes();
        testSubject0.addEntity(mock(OWLNamedIndividual.class));
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLNamedIndividual.class)));
        testSubject0.addSameEntities(Utils
                .mockSet(mock(OWLNamedIndividual.class)));
        testSubject0.addDifferentEntities(Utils
                .mockSet(mock(OWLNamedIndividual.class)));
    }

    @Test
    public void shouldTestOWLObjectPropertyNode() throws OWLException {
        OWLObjectPropertyNode testSubject0 = new OWLObjectPropertyNode();
        new OWLObjectPropertyNode(Utils.mockSet(Utils.mockObjectProperty()));
        new OWLObjectPropertyNode(Utils.mockObjectProperty());
        OWLObjectPropertyNode result0 = OWLObjectPropertyNode.getTopNode();
        OWLObjectPropertyNode result1 = OWLObjectPropertyNode.getBottomNode();
        testSubject0.add(Utils.mockObjectProperty());
        boolean result3 = testSubject0.contains(Utils.mockObjectProperty());
        Iterator<OWLObjectPropertyExpression> result4 = testSubject0.iterator();
        int result5 = testSubject0.getSize();
        Set<OWLObjectPropertyExpression> result6 = testSubject0.getEntities();
        boolean result7 = testSubject0.isTopNode();
        boolean result8 = testSubject0.isBottomNode();
        Set<OWLObjectPropertyExpression> result9 = testSubject0
                .getEntitiesMinus(Utils.mockObjectProperty());
        Set<OWLObjectPropertyExpression> result10 = testSubject0
                .getEntitiesMinusTop();
        Set<OWLObjectPropertyExpression> result11 = testSubject0
                .getEntitiesMinusBottom();
        boolean result12 = testSubject0.isSingleton();
        OWLObject result13 = testSubject0.getRepresentativeElement();
    }

    @Test
    public void shouldTestOWLObjectPropertyNodeSet() throws OWLException {
        OWLObjectPropertyNodeSet testSubject0 = new OWLObjectPropertyNodeSet();
        new OWLObjectPropertyNodeSet(Utils.mockObjectProperty());
        new OWLObjectPropertyNodeSet(mock(Node.class));
        new OWLObjectPropertyNodeSet(Utils.mockSet(Utils
                .mockNode(OWLObjectPropertyExpression.class)));
        boolean result1 = testSubject0.isEmpty();
        Iterator<Node<OWLObjectPropertyExpression>> result2 = testSubject0
                .iterator();
        boolean result3 = testSubject0.isSingleton();
        Set<OWLObjectPropertyExpression> result4 = testSubject0.getFlattened();
        boolean result5 = testSubject0.containsEntity(Utils
                .mockObjectProperty());
        boolean result6 = testSubject0.isTopSingleton();
        boolean result7 = testSubject0.isBottomSingleton();
        Set<Node<OWLObjectPropertyExpression>> result8 = testSubject0
                .getNodes();
        testSubject0.addEntity(Utils.mockObjectProperty());
        testSubject0.addNode(mock(Node.class));
        testSubject0.addAllNodes(Utils.mockCollection(Utils
                .mockNode(OWLObjectPropertyExpression.class)));
        testSubject0.addSameEntities(Utils.mockSet(Utils.mockObjectProperty()));
        testSubject0.addDifferentEntities(Utils.mockSet(Utils
                .mockObjectProperty()));
    }

    @Test
    public void shouldTestOWLReasonerBase() throws OWLException {
        OWLReasonerBase testSubject0 = new OWLReasonerBase(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class),
                BufferingMode.NON_BUFFERING) {
            @Override
            public String getReasonerName() {
                return null;
            }

            @Override
            public Version getReasonerVersion() {
                return null;
            }

            @Override
            public void interrupt() {}

            @Override
            public void precomputeInferences(InferenceType... inferenceTypes)
                    throws ReasonerInterruptedException, TimeOutException,
                    InconsistentOntologyException {}

            @Override
            public boolean isPrecomputed(InferenceType inferenceType) {
                return false;
            }

            @Override
            public Set<InferenceType> getPrecomputableInferenceTypes() {
                return null;
            }

            @Override
            public boolean isConsistent() throws ReasonerInterruptedException,
                    TimeOutException {
                return false;
            }

            @Override
            public boolean isSatisfiable(OWLClassExpression classExpression)
                    throws ReasonerInterruptedException, TimeOutException,
                    ClassExpressionNotInProfileException,
                    FreshEntitiesException, InconsistentOntologyException {
                return false;
            }

            @Override
            public Node<OWLClass> getUnsatisfiableClasses()
                    throws ReasonerInterruptedException, TimeOutException,
                    InconsistentOntologyException {
                return null;
            }

            @Override
            public boolean isEntailed(OWLAxiom axiom)
                    throws ReasonerInterruptedException,
                    UnsupportedEntailmentTypeException, TimeOutException,
                    AxiomNotInProfileException, FreshEntitiesException,
                    InconsistentOntologyException {
                return false;
            }

            @Override
            public boolean isEntailed(Set<? extends OWLAxiom> axioms)
                    throws ReasonerInterruptedException,
                    UnsupportedEntailmentTypeException, TimeOutException,
                    AxiomNotInProfileException, FreshEntitiesException,
                    InconsistentOntologyException {
                return false;
            }

            @Override
            public boolean
                    isEntailmentCheckingSupported(AxiomType<?> axiomType) {
                return false;
            }

            @Override
            public Node<OWLClass> getTopClassNode() {
                return null;
            }

            @Override
            public Node<OWLClass> getBottomClassNode() {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getSubClasses(OWLClassExpression ce,
                    boolean direct) throws ReasonerInterruptedException,
                    TimeOutException, FreshEntitiesException,
                    InconsistentOntologyException,
                    ClassExpressionNotInProfileException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getSuperClasses(OWLClassExpression ce,
                    boolean direct) throws InconsistentOntologyException,
                    ClassExpressionNotInProfileException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public Node<OWLClass> getEquivalentClasses(OWLClassExpression ce)
                    throws InconsistentOntologyException,
                    ClassExpressionNotInProfileException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getDisjointClasses(OWLClassExpression ce)
                    throws ReasonerInterruptedException, TimeOutException,
                    FreshEntitiesException, InconsistentOntologyException {
                return null;
            }

            @Override
            public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
                return null;
            }

            @Override
            public Node<OWLObjectPropertyExpression>
                    getBottomObjectPropertyNode() {
                return null;
            }

            @Override
            public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
                    OWLObjectPropertyExpression pe, boolean direct)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLObjectPropertyExpression>
                    getSuperObjectProperties(OWLObjectPropertyExpression pe,
                            boolean direct)
                            throws InconsistentOntologyException,
                            FreshEntitiesException,
                            ReasonerInterruptedException, TimeOutException {
                return null;
            }

            @Override
            public
                    Node<OWLObjectPropertyExpression>
                    getEquivalentObjectProperties(OWLObjectPropertyExpression pe)
                            throws InconsistentOntologyException,
                            FreshEntitiesException,
                            ReasonerInterruptedException, TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLObjectPropertyExpression>
                    getDisjointObjectProperties(OWLObjectPropertyExpression pe)
                            throws InconsistentOntologyException,
                            FreshEntitiesException,
                            ReasonerInterruptedException, TimeOutException {
                return null;
            }

            @Override
            public Node<OWLObjectPropertyExpression>
                    getInverseObjectProperties(OWLObjectPropertyExpression pe)
                            throws InconsistentOntologyException,
                            FreshEntitiesException,
                            ReasonerInterruptedException, TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getObjectPropertyDomains(
                    OWLObjectPropertyExpression pe, boolean direct)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getObjectPropertyRanges(
                    OWLObjectPropertyExpression pe, boolean direct)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public Node<OWLDataProperty> getTopDataPropertyNode() {
                return null;
            }

            @Override
            public Node<OWLDataProperty> getBottomDataPropertyNode() {
                return null;
            }

            @Override
            public NodeSet<OWLDataProperty> getSubDataProperties(
                    OWLDataProperty pe, boolean direct)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLDataProperty> getSuperDataProperties(
                    OWLDataProperty pe, boolean direct)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public Node<OWLDataProperty> getEquivalentDataProperties(
                    OWLDataProperty pe) throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLDataProperty> getDisjointDataProperties(
                    OWLDataPropertyExpression pe)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getDataPropertyDomains(OWLDataProperty pe,
                    boolean direct) throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLClass> getTypes(OWLNamedIndividual ind,
                    boolean direct) throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLNamedIndividual> getInstances(
                    OWLClassExpression ce, boolean direct)
                    throws InconsistentOntologyException,
                    ClassExpressionNotInProfileException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
                    OWLNamedIndividual ind, OWLObjectPropertyExpression pe)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public Set<OWLLiteral> getDataPropertyValues(
                    OWLNamedIndividual ind, OWLDataProperty pe)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public Node<OWLNamedIndividual> getSameIndividuals(
                    OWLNamedIndividual ind)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            public NodeSet<OWLNamedIndividual> getDifferentIndividuals(
                    OWLNamedIndividual ind)
                    throws InconsistentOntologyException,
                    FreshEntitiesException, ReasonerInterruptedException,
                    TimeOutException {
                return null;
            }

            @Override
            protected void handleChanges(Set<OWLAxiom> addAxioms,
                    Set<OWLAxiom> removeAxioms) {}
        };
        testSubject0.flush();
        testSubject0.dispose();
        OWLDataFactory result0 = testSubject0.getOWLDataFactory();
        long result1 = testSubject0.getTimeOut();
        FreshEntityPolicy result2 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result3 = testSubject0
                .getIndividualNodeSetPolicy();
        BufferingMode result4 = testSubject0.getBufferingMode();
        Set<OWLAxiom> result6 = testSubject0.getPendingAxiomAdditions();
        Set<OWLAxiom> result7 = testSubject0.getPendingAxiomRemovals();
        OWLOntology result8 = testSubject0.getRootOntology();
        OWLReasonerConfiguration result9 = testSubject0
                .getReasonerConfiguration();
        Collection<OWLAxiom> result10 = testSubject0.getReasonerAxioms();
        testSubject0.interrupt();
        NodeSet<OWLClass> result12 = testSubject0.getTypes(
                mock(OWLNamedIndividual.class), false);
        NodeSet<OWLNamedIndividual> result13 = testSubject0
                .getObjectPropertyValues(mock(OWLNamedIndividual.class),
                        Utils.mockObjectProperty());
        Set<OWLLiteral> result14 = testSubject0.getDataPropertyValues(
                mock(OWLNamedIndividual.class), mock(OWLDataProperty.class));
        Node<OWLNamedIndividual> result15 = testSubject0
                .getSameIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLNamedIndividual> result16 = testSubject0
                .getDifferentIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLClass> result17 = testSubject0.getSuperClasses(
                Utils.mockAnonClass(), false);
        NodeSet<OWLClass> result18 = testSubject0.getSubClasses(
                Utils.mockAnonClass(), false);
        Node<OWLClass> result19 = testSubject0.getEquivalentClasses(Utils
                .mockAnonClass());
        NodeSet<OWLClass> result20 = testSubject0.getDisjointClasses(Utils
                .mockAnonClass());
        String result21 = testSubject0.getReasonerName();
        Version result22 = testSubject0.getReasonerVersion();
        testSubject0.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        boolean result23 = testSubject0
                .isPrecomputed(InferenceType.CLASS_HIERARCHY);
        Set<InferenceType> result24 = testSubject0
                .getPrecomputableInferenceTypes();
        boolean result25 = testSubject0.isConsistent();
        boolean result26 = testSubject0.isSatisfiable(Utils.mockAnonClass());
        Node<OWLClass> result27 = testSubject0.getUnsatisfiableClasses();
        boolean result28 = testSubject0.isEntailed(mock(OWLAxiom.class));
        boolean result29 = testSubject0.isEntailed(Utils
                .mockSet(mock(OWLAxiom.class)));
        boolean result30 = testSubject0
                .isEntailmentCheckingSupported(mock(AxiomType.class));
        Node<OWLClass> result31 = testSubject0.getTopClassNode();
        Node<OWLClass> result32 = testSubject0.getBottomClassNode();
        Node<OWLObjectPropertyExpression> result33 = testSubject0
                .getTopObjectPropertyNode();
        Node<OWLObjectPropertyExpression> result34 = testSubject0
                .getBottomObjectPropertyNode();
        NodeSet<OWLObjectPropertyExpression> result35 = testSubject0
                .getSubObjectProperties(Utils.mockObjectProperty(), false);
        NodeSet<OWLObjectPropertyExpression> result36 = testSubject0
                .getSuperObjectProperties(Utils.mockObjectProperty(), false);
        Node<OWLObjectPropertyExpression> result37 = testSubject0
                .getEquivalentObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLObjectPropertyExpression> result38 = testSubject0
                .getDisjointObjectProperties(Utils.mockObjectProperty());
        Node<OWLObjectPropertyExpression> result39 = testSubject0
                .getInverseObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLClass> result40 = testSubject0.getObjectPropertyDomains(
                Utils.mockObjectProperty(), false);
        NodeSet<OWLClass> result41 = testSubject0.getObjectPropertyRanges(
                Utils.mockObjectProperty(), false);
        Node<OWLDataProperty> result42 = testSubject0.getTopDataPropertyNode();
        Node<OWLDataProperty> result43 = testSubject0
                .getBottomDataPropertyNode();
        NodeSet<OWLDataProperty> result44 = testSubject0.getSubDataProperties(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLDataProperty> result45 = testSubject0
                .getSuperDataProperties(mock(OWLDataProperty.class), false);
        Node<OWLDataProperty> result46 = testSubject0
                .getEquivalentDataProperties(mock(OWLDataProperty.class));
        NodeSet<OWLDataProperty> result47 = testSubject0
                .getDisjointDataProperties(mock(OWLDataPropertyExpression.class));
        NodeSet<OWLClass> result48 = testSubject0.getDataPropertyDomains(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLNamedIndividual> result49 = testSubject0.getInstances(
                Utils.mockAnonClass(), false);
    }
}
