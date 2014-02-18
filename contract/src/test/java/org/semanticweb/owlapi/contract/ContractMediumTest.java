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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxParser;
import org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxParserConstants;
import org.semanticweb.owlapi.dlsyntax.parser.DLSyntaxParserTokenManager;
import org.semanticweb.owlapi.dlsyntax.parser.ParseException;
import org.semanticweb.owlapi.expression.OWLClassExpressionParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.IllegalElementNameException;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.OWLOntologyXMLNamespaceManager;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterFactory;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterImpl;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterNamespaceManager;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner.RootNode;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.Version;

import uk.ac.manchester.cs.owl.explanation.ordering.AlphaExplanationOrderer;
import uk.ac.manchester.cs.owl.explanation.ordering.EntailedAxiomTree;
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationOrderer;
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationTree;
import uk.ac.manchester.cs.owl.explanation.ordering.MutableTree;
import uk.ac.manchester.cs.owl.explanation.ordering.NodeRenderer;
import uk.ac.manchester.cs.owl.explanation.ordering.NullExplanationOrderer;
import uk.ac.manchester.cs.owl.explanation.ordering.Tree;

@SuppressWarnings({ "unused", "javadoc", "unchecked", "rawtypes" })
public class ContractMediumTest {
    @Test
    public void shouldTestInterfaceOWLClassExpressionParser()
            throws OWLException, ParserException {
        OWLClassExpressionParser testSubject0 = mock(OWLClassExpressionParser.class);
        Object result0 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestInterfaceOWLEntityChecker() throws OWLException {
        OWLEntityChecker testSubject0 = mock(OWLEntityChecker.class);
        OWLClass result0 = testSubject0.getOWLClass("");
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty("");
        OWLDataProperty result2 = testSubject0.getOWLDataProperty("");
        OWLAnnotationProperty result3 = testSubject0
                .getOWLAnnotationProperty("");
        OWLDatatype result4 = testSubject0.getOWLDatatype("");
        OWLNamedIndividual result5 = testSubject0.getOWLIndividual("");
    }

    @Test
    public void shouldTestInterfaceOWLExpressionParser() throws OWLException,
            ParserException {
        OWLExpressionParser<OWLObject> testSubject0 = mock(OWLExpressionParser.class);
        Object result0 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChecker() throws OWLException {
        OWLOntologyChecker testSubject0 = mock(OWLOntologyChecker.class);
        OWLOntology result0 = testSubject0.getOntology("");
    }

    @Test
    public void shouldTestShortFormEntityChecker() throws OWLException {
        ShortFormEntityChecker testSubject0 = new ShortFormEntityChecker(
                mock(BidirectionalShortFormProvider.class));
        OWLClass result0 = testSubject0.getOWLClass("");
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty("");
        OWLDataProperty result2 = testSubject0.getOWLDataProperty("");
        OWLAnnotationProperty result3 = testSubject0
                .getOWLAnnotationProperty("");
        OWLDatatype result4 = testSubject0.getOWLDatatype("");
        OWLNamedIndividual result5 = testSubject0.getOWLIndividual("");
    }

    @Test
    public void shouldTestAlphaExplanationOrderer() throws OWLException {
        AlphaExplanationOrderer testSubject0 = new AlphaExplanationOrderer(
                mock(OWLObjectRenderer.class));
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
    }

    @Test
    public void shouldTestEntailedAxiomTree() throws OWLException {
        EntailedAxiomTree testSubject0 = new EntailedAxiomTree(
                mock(OWLAxiom.class));
        boolean result0 = testSubject0.isEntailed();
        Tree<OWLAxiom> result2 = testSubject0.getParent();
        Tree<OWLAxiom> result4 = testSubject0.getRoot();
        boolean result5 = testSubject0.isRoot();
        List<Tree<OWLAxiom>> result6 = testSubject0.getChildren();
        Object result7 = testSubject0.getUserObject();
        testSubject0.addChild(mock(MutableTree.class));
        testSubject0.removeChild(mock(MutableTree.class));
        Object result8 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        int result9 = testSubject0.getChildCount();
        boolean result10 = testSubject0.isLeaf();
        List<Tree<OWLAxiom>> result11 = testSubject0.getPathToRoot();
        List<OWLAxiom> result12 = testSubject0.getUserObjectPathToRoot();
        Set<OWLAxiom> result13 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<OWLAxiom> result14 = testSubject0.fillDepthFirst();
    }

    @Test
    public void shouldTestInterfaceExplanationOrderer() throws OWLException {
        ExplanationOrderer testSubject0 = mock(ExplanationOrderer.class);
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
    }

    @Test
    public void shouldTestExplanationTree() throws OWLException {
        ExplanationTree testSubject0 = new ExplanationTree(mock(OWLAxiom.class));
        boolean result0 = testSubject0.isEntailed();
        Tree<OWLAxiom> result2 = testSubject0.getParent();
        Tree<OWLAxiom> result4 = testSubject0.getRoot();
        boolean result5 = testSubject0.isRoot();
        List<Tree<OWLAxiom>> result6 = testSubject0.getChildren();
        Object result7 = testSubject0.getUserObject();
        testSubject0.addChild(mock(MutableTree.class));
        testSubject0.removeChild(mock(MutableTree.class));
        Object result8 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        int result9 = testSubject0.getChildCount();
        boolean result10 = testSubject0.isLeaf();
        List<Tree<OWLAxiom>> result11 = testSubject0.getPathToRoot();
        List<OWLAxiom> result12 = testSubject0.getUserObjectPathToRoot();
        Set<OWLAxiom> result13 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<OWLAxiom> result14 = testSubject0.fillDepthFirst();
    }

    @Test
    public void shouldTestNullExplanationOrderer() throws OWLException {
        NullExplanationOrderer testSubject0 = new NullExplanationOrderer();
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
    }

    @Test
    public void shouldTestInterfaceOWLKnowledgeExplorerReasoner()
            throws OWLException {
        OWLKnowledgeExplorerReasoner testSubject0 = mock(OWLKnowledgeExplorerReasoner.class);
        RootNode result0 = testSubject0.getRoot(Utils.mockAnonClass());
        Node<? extends OWLObjectPropertyExpression> result1 = testSubject0
                .getObjectNeighbours(mock(RootNode.class), false);
        Collection<RootNode> result2 = testSubject0.getObjectNeighbours(
                mock(RootNode.class), mock(OWLObjectProperty.class));
        Node<OWLDataProperty> result3 = testSubject0.getDataNeighbours(
                mock(RootNode.class), false);
        Collection<RootNode> result4 = testSubject0.getDataNeighbours(
                mock(RootNode.class), mock(OWLDataProperty.class));
        Node<? extends OWLClassExpression> result5 = testSubject0
                .getObjectLabel(mock(RootNode.class), false);
        Node<? extends OWLDataRange> result6 = testSubject0.getDataLabel(
                mock(RootNode.class), false);
        testSubject0.interrupt();
        testSubject0.flush();
        testSubject0.dispose();
        NodeSet<OWLClass> result7 = testSubject0.getTypes(
                mock(OWLNamedIndividual.class), false);
        NodeSet<OWLNamedIndividual> result8 = testSubject0
                .getObjectPropertyValues(mock(OWLNamedIndividual.class),
                        Utils.mockObjectProperty());
        Set<OWLLiteral> result9 = testSubject0.getDataPropertyValues(
                mock(OWLNamedIndividual.class), mock(OWLDataProperty.class));
        Node<OWLNamedIndividual> result10 = testSubject0
                .getSameIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLNamedIndividual> result11 = testSubject0
                .getDifferentIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLClass> result12 = testSubject0.getSuperClasses(
                Utils.mockAnonClass(), false);
        NodeSet<OWLClass> result13 = testSubject0.getSubClasses(
                Utils.mockAnonClass(), false);
        Node<OWLClass> result14 = testSubject0.getEquivalentClasses(Utils
                .mockAnonClass());
        NodeSet<OWLClass> result15 = testSubject0.getDisjointClasses(Utils
                .mockAnonClass());
        long result16 = testSubject0.getTimeOut();
        FreshEntityPolicy result17 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result18 = testSubject0
                .getIndividualNodeSetPolicy();
        String result19 = testSubject0.getReasonerName();
        Version result20 = testSubject0.getReasonerVersion();
        BufferingMode result21 = testSubject0.getBufferingMode();
        List<OWLOntologyChange> result22 = testSubject0.getPendingChanges();
        Set<OWLAxiom> result23 = testSubject0.getPendingAxiomAdditions();
        Set<OWLAxiom> result24 = testSubject0.getPendingAxiomRemovals();
        OWLOntology result25 = testSubject0.getRootOntology();
        testSubject0.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        boolean result26 = testSubject0
                .isPrecomputed(InferenceType.CLASS_HIERARCHY);
        Set<InferenceType> result27 = testSubject0
                .getPrecomputableInferenceTypes();
        boolean result28 = testSubject0.isConsistent();
        boolean result29 = testSubject0.isSatisfiable(Utils.mockAnonClass());
        Node<OWLClass> result30 = testSubject0.getUnsatisfiableClasses();
        boolean result31 = testSubject0.isEntailed(mock(OWLAxiom.class));
        boolean result32 = testSubject0.isEntailed(Utils
                .mockSet(mock(OWLAxiom.class)));
        boolean result33 = testSubject0
                .isEntailmentCheckingSupported(mock(AxiomType.class));
        Node<OWLClass> result34 = testSubject0.getTopClassNode();
        Node<OWLClass> result35 = testSubject0.getBottomClassNode();
        Node<OWLObjectPropertyExpression> result36 = testSubject0
                .getTopObjectPropertyNode();
        Node<OWLObjectPropertyExpression> result37 = testSubject0
                .getBottomObjectPropertyNode();
        NodeSet<OWLObjectPropertyExpression> result38 = testSubject0
                .getSubObjectProperties(Utils.mockObjectProperty(), false);
        NodeSet<OWLObjectPropertyExpression> result39 = testSubject0
                .getSuperObjectProperties(Utils.mockObjectProperty(), false);
        Node<OWLObjectPropertyExpression> result40 = testSubject0
                .getEquivalentObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLObjectPropertyExpression> result41 = testSubject0
                .getDisjointObjectProperties(Utils.mockObjectProperty());
        Node<OWLObjectPropertyExpression> result42 = testSubject0
                .getInverseObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLClass> result43 = testSubject0.getObjectPropertyDomains(
                Utils.mockObjectProperty(), false);
        NodeSet<OWLClass> result44 = testSubject0.getObjectPropertyRanges(
                Utils.mockObjectProperty(), false);
        Node<OWLDataProperty> result45 = testSubject0.getTopDataPropertyNode();
        Node<OWLDataProperty> result46 = testSubject0
                .getBottomDataPropertyNode();
        NodeSet<OWLDataProperty> result47 = testSubject0.getSubDataProperties(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLDataProperty> result48 = testSubject0
                .getSuperDataProperties(mock(OWLDataProperty.class), false);
        Node<OWLDataProperty> result49 = testSubject0
                .getEquivalentDataProperties(mock(OWLDataProperty.class));
        NodeSet<OWLDataProperty> result50 = testSubject0
                .getDisjointDataProperties(mock(OWLDataPropertyExpression.class));
        NodeSet<OWLClass> result51 = testSubject0.getDataPropertyDomains(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLNamedIndividual> result52 = testSubject0.getInstances(
                Utils.mockAnonClass(), false);
    }

    @Test
    public void shouldTestIllegalElementNameException() throws OWLException {
        IllegalElementNameException testSubject0 = new IllegalElementNameException(
                "");
        String result0 = testSubject0.getElementName();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLOntologyXMLNamespaceManager() throws OWLException {
        OWLOntologyXMLNamespaceManager testSubject0 = new OWLOntologyXMLNamespaceManager(
                Utils.getMockOntology(),
                new OWLFunctionalSyntaxOntologyFormat());
        new OWLOntologyXMLNamespaceManager(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class));
        String result0 = testSubject0.getQName("");
        testSubject0.setPrefix("", "");
        String result1 = testSubject0.getDefaultPrefix();
        String result2 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result3 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result4 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
    }

    @Test
    public void shouldTestInterfaceXMLWriter() throws OWLException, IOException {
        XMLWriter testSubject0 = mock(XMLWriter.class);
        testSubject0.setEncoding("");
        testSubject0.startDocument(IRI.create("iri"));
        testSubject0.endDocument();
        XMLWriterNamespaceManager result0 = testSubject0.getNamespacePrefixes();
        String result1 = testSubject0.getXMLBase();
        testSubject0.setWrapAttributes(false);
        testSubject0.writeStartElement(IRI.create("iri"));
        testSubject0.writeEndElement();
        testSubject0.writeAttribute("", "");
        testSubject0.writeTextContent("");
        testSubject0.writeComment("");
    }

    @Ignore
    @Test
    public void shouldTestXMLWriterFactory() throws OWLException {
        XMLWriterFactory testSubject0 = XMLWriterFactory.getInstance();
        XMLWriterFactory result0 = XMLWriterFactory.getInstance();
        XMLWriter result1 = testSubject0.createXMLWriter(mock(Writer.class),
                mock(XMLWriterNamespaceManager.class), "");
    }

    @Ignore
    @Test
    public void shouldTestXMLWriterImpl() throws OWLException, IOException {
        XMLWriterImpl testSubject0 = new XMLWriterImpl(mock(Writer.class),
                mock(XMLWriterNamespaceManager.class), "");
        IRI i = IRI.create("iri");
        testSubject0.setEncoding("");
        testSubject0.startDocument(i);
        testSubject0.endDocument();
        String result0 = testSubject0.getDefaultNamespace();
        XMLWriterNamespaceManager result1 = testSubject0.getNamespacePrefixes();
        String result2 = testSubject0.getXMLBase();
        testSubject0.setWrapAttributes(false);
        testSubject0.writeStartElement(i);
        testSubject0.writeEndElement();
        testSubject0.writeAttribute("", "");
        testSubject0.writeTextContent("");
        testSubject0.writeComment("");
    }

    @Test
    public void shouldTestXMLWriterNamespaceManager() throws OWLException {
        XMLWriterNamespaceManager testSubject0 = new XMLWriterNamespaceManager(
                "");
        testSubject0.setPrefix("", "");
        String result0 = testSubject0.getDefaultPrefix();
        String result1 = testSubject0.getQName("");
        String result2 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result3 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result4 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
    }

    @Test
    public void shouldTestXMLWriterPreferences() throws OWLException {
        XMLWriterPreferences testSubject0 = XMLWriterPreferences.getInstance();
        XMLWriterPreferences result0 = XMLWriterPreferences.getInstance();
        boolean result1 = testSubject0.isUseNamespaceEntities();
        testSubject0.setUseNamespaceEntities(false);
        boolean result2 = testSubject0.isIndenting();
        testSubject0.setIndenting(false);
        int result3 = testSubject0.getIndentSize();
        testSubject0.setIndentSize(0);
    }

    @Test
    public void shouldTestStructuralReasoner() throws OWLException {
        StructuralReasoner testSubject0 = new StructuralReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class),
                BufferingMode.NON_BUFFERING);
        testSubject0.interrupt();
        NodeSet<OWLClass> result0 = testSubject0.getTypes(
                mock(OWLNamedIndividual.class), false);
        NodeSet<OWLNamedIndividual> result1 = testSubject0
                .getObjectPropertyValues(mock(OWLNamedIndividual.class),
                        Utils.mockObjectProperty());
        Set<OWLLiteral> result2 = testSubject0.getDataPropertyValues(
                mock(OWLNamedIndividual.class), DataProperty(IRI("urn:p")));
        Node<OWLNamedIndividual> result3 = testSubject0
                .getSameIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLNamedIndividual> result4 = testSubject0
                .getDifferentIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLClass> result5 = testSubject0.getSuperClasses(
                Utils.mockAnonClass(), false);
        NodeSet<OWLClass> result6 = testSubject0.getSubClasses(
                Utils.mockAnonClass(), false);
        Node<OWLClass> result7 = testSubject0.getEquivalentClasses(Utils
                .mockAnonClass());
        NodeSet<OWLClass> result8 = testSubject0.getDisjointClasses(Utils
                .mockAnonClass());
        FreshEntityPolicy result9 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result10 = testSubject0
                .getIndividualNodeSetPolicy();
        String result11 = testSubject0.getReasonerName();
        Version result12 = testSubject0.getReasonerVersion();
        testSubject0.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        boolean result13 = testSubject0
                .isPrecomputed(InferenceType.CLASS_HIERARCHY);
        Set<InferenceType> result14 = testSubject0
                .getPrecomputableInferenceTypes();
        boolean result15 = testSubject0.isConsistent();
        boolean result16 = testSubject0.isSatisfiable(Utils.mockAnonClass());
        Node<OWLClass> result17 = testSubject0.getUnsatisfiableClasses();
        boolean result18 = testSubject0.isEntailed(Utils
                .mockSet(mock(OWLAxiom.class)));
        boolean result19 = testSubject0.isEntailed(mock(OWLAxiom.class));
        boolean result20 = testSubject0
                .isEntailmentCheckingSupported(mock(AxiomType.class));
        Node<OWLClass> result21 = testSubject0.getTopClassNode();
        Node<OWLClass> result22 = testSubject0.getBottomClassNode();
        Node<OWLObjectPropertyExpression> result23 = testSubject0
                .getTopObjectPropertyNode();
        Node<OWLObjectPropertyExpression> result24 = testSubject0
                .getBottomObjectPropertyNode();
        NodeSet<OWLObjectPropertyExpression> result25 = testSubject0
                .getSubObjectProperties(Utils.mockObjectProperty(), false);
        NodeSet<OWLObjectPropertyExpression> result26 = testSubject0
                .getSuperObjectProperties(Utils.mockObjectProperty(), false);
        Node<OWLObjectPropertyExpression> result27 = testSubject0
                .getEquivalentObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLObjectPropertyExpression> result28 = testSubject0
                .getDisjointObjectProperties(Utils.mockObjectProperty());
        Node<OWLObjectPropertyExpression> result29 = testSubject0
                .getInverseObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLClass> result30 = testSubject0.getObjectPropertyDomains(
                Utils.mockObjectProperty(), false);
        NodeSet<OWLClass> result31 = testSubject0.getObjectPropertyRanges(
                Utils.mockObjectProperty(), false);
        Node<OWLDataProperty> result32 = testSubject0.getTopDataPropertyNode();
        Node<OWLDataProperty> result33 = testSubject0
                .getBottomDataPropertyNode();
        NodeSet<OWLDataProperty> result34 = testSubject0.getSubDataProperties(
                DataProperty(IRI("urn:p")), false);
        NodeSet<OWLDataProperty> result35 = testSubject0
                .getSuperDataProperties(DataProperty(IRI("urn:p")), false);
        Node<OWLDataProperty> result36 = testSubject0
                .getEquivalentDataProperties(DataProperty(IRI("urn:p")));
        NodeSet<OWLDataProperty> result37 = testSubject0
                .getDisjointDataProperties(DataProperty(IRI("urn:p")));
        NodeSet<OWLClass> result38 = testSubject0.getDataPropertyDomains(
                DataProperty(IRI("urn:p")), false);
        NodeSet<OWLNamedIndividual> result39 = testSubject0.getInstances(
                Utils.mockAnonClass(), false);
        testSubject0.prepareReasoner();
        testSubject0.flush();
        testSubject0.dispose();
        OWLDataFactory result40 = testSubject0.getOWLDataFactory();
        long result41 = testSubject0.getTimeOut();
        BufferingMode result42 = testSubject0.getBufferingMode();
        List<OWLOntologyChange> result43 = testSubject0.getPendingChanges();
        Set<OWLAxiom> result44 = testSubject0.getPendingAxiomAdditions();
        Set<OWLAxiom> result45 = testSubject0.getPendingAxiomRemovals();
        OWLOntology result46 = testSubject0.getRootOntology();
        OWLReasonerConfiguration result47 = testSubject0
                .getReasonerConfiguration();
        Collection<OWLAxiom> result48 = testSubject0.getReasonerAxioms();
    }

    @Test
    public void shouldTestStructuralReasonerFactory() throws OWLException {
        StructuralReasonerFactory testSubject0 = new StructuralReasonerFactory();
        String result0 = testSubject0.getReasonerName();
        OWLReasoner result1 = testSubject0.createNonBufferingReasoner(Utils
                .getMockOntology());
        OWLReasoner result2 = testSubject0.createNonBufferingReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
        OWLReasoner result3 = testSubject0.createReasoner(Utils
                .getMockOntology());
        OWLReasoner result4 = testSubject0.createReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
    }

    @Ignore
    @Test
    public void shouldTestDLSyntaxParser() throws OWLException, ParseException {
        DLSyntaxParser testSubject0 = new DLSyntaxParser(
                mock(InputStream.class));
        new DLSyntaxParser(mock(DLSyntaxParserTokenManager.class));
        new DLSyntaxParser(mock(Reader.class));
        new DLSyntaxParser(mock(InputStream.class), "UTF-8");
        IRI result0 = testSubject0.getIRI("");
        testSubject0.setDefaultNamespace("");
        testSubject0.ReInit(mock(InputStream.class), "UTF-8");
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(DLSyntaxParserTokenManager.class));
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
        testSubject0.setOWLDataFactory(mock(OWLDataFactory.class));
        testSubject0.setPrefixMapping("", "");
        IRI result13 = testSubject0.getIRIFromId("");
    }

    @Test
    public void shouldTestInterfaceDLSyntaxParserConstants()
            throws OWLException {
        DLSyntaxParserConstants testSubject0 = mock(DLSyntaxParserConstants.class);
    }
}
