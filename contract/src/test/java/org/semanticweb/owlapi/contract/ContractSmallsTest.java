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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.debugging.AbstractOWLDebugger;
import org.semanticweb.owlapi.debugging.DebuggerClassExpressionGenerator;
import org.semanticweb.owlapi.debugging.JustificationMap;
import org.semanticweb.owlapi.debugging.OWLDebugger;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.modularity.OntologySegmenter;
import org.semanticweb.owlapi.normalform.NegationalNormalFormConverter;
import org.semanticweb.owlapi.normalform.NormalFormRewriter;
import org.semanticweb.owlapi.normalform.OWLObjectComplementOfExtractor;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLObjectRenderer;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLRenderer;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLWriter;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFConsumer;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFParserException;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLNamespaceManager;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import uk.ac.manchester.cs.owl.explanation.ordering.NodeRenderer;
import uk.ac.manchester.cs.owl.explanation.ordering.Tree;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

import com.clarkparsia.owlapi.explanation.io.ExplanationRenderer;
import com.clarkparsia.owlapi.explanation.io.SilentExplanationRenderer;
import com.clarkparsia.owlapi.explanation.util.DefinitionTracker;
import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;
import com.clarkparsia.owlapi.explanation.util.OntologyUtils;
import com.clarkparsia.owlapi.explanation.util.SilentExplanationProgressMonitor;
import com.clarkparsia.owlapi.modularity.locality.LocalityClass;
import com.clarkparsia.owlapi.modularity.locality.LocalityEvaluator;
import com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator;
import com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractSmallsTest {

    @Test
    public void shouldTestEscapeUtils() throws OWLException {
        new EscapeUtils();
        String result1 = EscapeUtils.escapeString("");
        String result2 = EscapeUtils.unescapeString("");
    }

    @Test
    public void shouldTestInterfaceOntologySegmenter() throws OWLException {
        OntologySegmenter testSubject0 = mock(OntologySegmenter.class);
        Set<OWLAxiom> result0 = testSubject0.extract(Utils.mockSet(Utils
                .mockOWLEntity()));
        Set<OWLAxiom> result1 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner());
        OWLOntology result2 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"));
        OWLOntology result3 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"), 0, 0,
                Utils.structReasoner());
    }

    @Test
    public void shouldTestInterfaceExplanationRenderer() throws OWLException,
            IOException {
        // this test enforces interfaces
        ExplanationRenderer testSubject0 = mock(ExplanationRenderer.class);
        testSubject0.render(mock(OWLAxiom.class), Utils.mockSetSetAxiom());
        testSubject0.startRendering(mock(Writer.class));
        testSubject0.endRendering();
    }

    @Test
    public void shouldTestSilentExplanationRenderer() throws OWLException,
            IOException {
        SilentExplanationRenderer testSubject0 = new SilentExplanationRenderer();
        testSubject0.render(mock(OWLAxiom.class), Utils.mockSetSetAxiom());
        testSubject0.startRendering(mock(Writer.class));
        testSubject0.endRendering();
    }

    @Test
    public void shouldTestLocalityClass() throws OWLException {
        LocalityClass testSubject0 = LocalityClass.BOTTOM_BOTTOM;
        LocalityClass testSubject1 = LocalityClass.TOP_BOTTOM;
        LocalityClass testSubject2 = LocalityClass.TOP_TOP;
    }

    @Test
    public void shouldTestInterfaceLocalityEvaluator() throws OWLException {
        LocalityEvaluator testSubject0 = mock(LocalityEvaluator.class);
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
    }

    @Test
    public void shouldTestSemanticLocalityEvaluator() throws OWLException {
        SemanticLocalityEvaluator testSubject0 = new SemanticLocalityEvaluator(
                Utils.getMockManager(), mock(OWLReasonerFactory.class));
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
    }

    @Test
    public void shouldTestSyntacticLocalityEvaluator() throws OWLException {
        SyntacticLocalityEvaluator testSubject0 = new SyntacticLocalityEvaluator(
                LocalityClass.BOTTOM_BOTTOM);
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
        Set<LocalityClass> result1 = testSubject0.supportedLocalityClasses();
    }

    @Test
    public void shouldTestModuleType() throws OWLException {
        ModuleType testSubject0 = ModuleType.BOT;
        ModuleType[] result1 = ModuleType.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSyntacticLocalityModuleExtractor()
            throws OWLException {
        SyntacticLocalityModuleExtractor testSubject0 = new SyntacticLocalityModuleExtractor(
                Utils.getMockManager(), Utils.getMockOntology(),
                Utils.mockSet(mock(OWLAxiom.class)), ModuleType.BOT);
        new SyntacticLocalityModuleExtractor(Utils.getMockManager(),
                Utils.getMockOntology(), ModuleType.BOT);
        Set<OWLAxiom> result0 = testSubject0.extract(Utils.mockSet(Utils
                .mockOWLEntity()));
        Set<OWLAxiom> result1 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner(), false);
        Set<OWLAxiom> result2 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner());
        OWLOntology result3 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"), 0, 0,
                Utils.structReasoner());
        OWLOntology result4 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()),
                IRI.create("urn:anotherFake"));
        testSubject0.setModuleType(ModuleType.BOT);
        ModuleType result5 = testSubject0.getModuleType();
    }

    @Test
    public void shouldTestDefinitionTracker() throws OWLException {
        DefinitionTracker testSubject0 = new DefinitionTracker(
                Utils.getMockOntology());
        boolean result0 = testSubject0.isDefined(Utils.mockOWLEntity());
        boolean result1 = testSubject0.isDefined(Utils.mockAnonClass());
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestInterfaceExplanationProgressMonitor()
            throws OWLException {
        ExplanationProgressMonitor testSubject0 = mock(ExplanationProgressMonitor.class);
        boolean result0 = testSubject0.isCancelled();
        testSubject0.foundExplanation(Utils.mockSet(mock(OWLAxiom.class)));
        testSubject0.foundAllExplanations();
    }

    @Test
    public void shouldTestOntologyUtils() throws OWLException {
        new OntologyUtils();
        OntologyUtils.addAxiom(mock(OWLAxiom.class),
                Utils.mockSet(Utils.getMockOntology()), Utils.getMockManager());
        Set<OWLOntology> result0 = OntologyUtils.removeAxiom(
                mock(OWLAxiom.class), Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockManager());
        boolean result1 = OntologyUtils.containsUnreferencedEntity(
                Utils.getMockOntology(), Utils.mockAnonClass());
    }

    @Test
    public void shouldTestSilentExplanationProgressMonitor()
            throws OWLException {
        SilentExplanationProgressMonitor testSubject0 = new SilentExplanationProgressMonitor();
        boolean result0 = testSubject0.isCancelled();
        testSubject0.foundExplanation(Utils.mockSet(mock(OWLAxiom.class)));
        testSubject0.foundAllExplanations();
    }

    @Test
    public void shouldTestRDFXMLNamespaceManager() throws OWLException {
        RDFXMLNamespaceManager testSubject0 = new RDFXMLNamespaceManager(
                Utils.getMockOntology(),
                new OWLFunctionalSyntaxOntologyFormat());
        new RDFXMLNamespaceManager(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class));
        Set<OWLEntity> result0 = testSubject0.getEntitiesWithInvalidQNames();
        String result1 = testSubject0.getQName("");
        testSubject0.setPrefix("", "");
        String result2 = testSubject0.getDefaultPrefix();
        String result3 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result4 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result5 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
    }

    @Test
    public void shouldTestInterfaceNodeRenderer() throws OWLException {
        NodeRenderer<Object> testSubject0 = mock(NodeRenderer.class);
        String result0 = testSubject0.render(mock(Tree.class));
    }

    @Test
    public void shouldTestInterfaceTree() throws OWLException {
        Tree<Object> testSubject0 = mock(Tree.class);
        Tree<Object> result0 = testSubject0.getParent();
        Tree<Object> result1 = testSubject0.getRoot();
        boolean result2 = testSubject0.isRoot();
        List<Tree<Object>> result3 = testSubject0.getChildren();
        Object result4 = testSubject0.getUserObject();
        Object result5 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        int result6 = testSubject0.getChildCount();
        boolean result7 = testSubject0.isLeaf();
        List<Tree<Object>> result8 = testSubject0.getPathToRoot();
        List<Object> result9 = testSubject0.getUserObjectPathToRoot();
        Set<Object> result10 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<Object> result11 = testSubject0.fillDepthFirst();
    }

    @Test
    public void shouldTestNegationalNormalFormConverter() throws OWLException {
        NegationalNormalFormConverter testSubject0 = new NegationalNormalFormConverter(
                mock(OWLDataFactory.class));
        boolean result0 = testSubject0.isInNormalForm(Utils.mockAnonClass());
        OWLClassExpression result1 = testSubject0.convertToNormalForm(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestInterfaceNormalFormRewriter() throws OWLException {
        NormalFormRewriter testSubject0 = mock(NormalFormRewriter.class);
        boolean result0 = testSubject0.isInNormalForm(Utils.mockAnonClass());
        OWLClassExpression result1 = testSubject0.convertToNormalForm(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestOWLObjectComplementOfExtractor() throws OWLException {
        OWLObjectComplementOfExtractor testSubject0 = new OWLObjectComplementOfExtractor();
        testSubject0.reset();
        Set<OWLClassExpression> result0 = testSubject0
                .getComplementedClassExpressions(Utils.mockAnonClass());
    }

    @Test
    public void shouldTestInterfaceRDFConsumer() throws OWLException,
            SAXException {
        RDFConsumer testSubject0 = mock(RDFConsumer.class);
        testSubject0.startModel(IRI.create(""));
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI(IRI.create(""));
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
    }

    @Test
    public void shouldTestRDFParserException() throws OWLException {
        RDFParserException testSubject0 = new RDFParserException("",
                mock(Locator.class));
        int result0 = testSubject0.getLineNumber();
        int result1 = testSubject0.getColumnNumber();
        String result2 = testSubject0.getPublicId();
        String result3 = testSubject0.getSystemId();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.getMessage();
        String result10 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLObjectRenderer() throws OWLException {
        new OWLXMLObjectRenderer(mock(OWLXMLWriter.class));
    }

    @Test
    public void shouldTestOWLXMLRenderer() throws OWLException {
        OWLXMLRenderer testSubject0 = new OWLXMLRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
    }

    @Test
    public void shouldTestAbstractOWLDebugger() throws OWLException {
        AbstractOWLDebugger testSubject0 = new AbstractOWLDebugger(
                Utils.getRealMockManager(), Utils.getMockOntology()) {

            @Override
            public Set<OWLAxiom> getSOSForIncosistentClass(
                    OWLClassExpression cls) throws OWLException {
                return Collections.emptySet();
            }

            @Override
            public void dispose() {}

            @Override
            protected OWLClassExpression getCurrentClass() throws OWLException {
                return mock(OWLClassExpression.class);
            }
        };
        OWLOntology result1 = testSubject0.getOWLOntology();
        Set<Set<OWLAxiom>> result2 = testSubject0
                .getAllSOSForIncosistentClass(Utils.mockAnonClass());
        testSubject0.constructHittingSetTree(
                Utils.mockSet(mock(OWLAxiom.class)), Utils.mockSetSetAxiom(),
                Utils.mockSetSetAxiom(), Utils.mockSet(mock(OWLAxiom.class)));
        testSubject0.dispose();
        Set<OWLAxiom> result4 = testSubject0.getSOSForIncosistentClass(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestDebuggerClassExpressionGenerator()
            throws OWLException {
        DebuggerClassExpressionGenerator testSubject0 = new DebuggerClassExpressionGenerator(
                mock(OWLDataFactory.class));
        OWLClassExpression result0 = testSubject0.getDebuggerClassExpression();
    }

    @Test
    public void shouldTestJustificationMap() throws OWLException {
        JustificationMap testSubject0 = new JustificationMap(
                Utils.mockAnonClass(), Utils.mockSet(mock(OWLAxiom.class)));
        Set<OWLAxiom> result0 = testSubject0.getRootAxioms();
        Set<OWLAxiom> result1 = testSubject0
                .getChildAxioms(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLDebugger() throws OWLException {
        OWLDebugger testSubject0 = mock(OWLDebugger.class);
        testSubject0.dispose();
        OWLOntology result0 = testSubject0.getOWLOntology();
        Set<Set<OWLAxiom>> result1 = testSubject0
                .getAllSOSForIncosistentClass(Utils.mockAnonClass());
        Set<OWLAxiom> result2 = testSubject0.getSOSForIncosistentClass(Utils
                .mockAnonClass());
    }
}
