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

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.AxiomSubjectProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.CachingBidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.CommonBaseIRIMapper;
import org.semanticweb.owlapi.util.DLExpressivityChecker;
import org.semanticweb.owlapi.util.DLExpressivityChecker.Construct;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.DelegatingObjectVisitorEx;
import org.semanticweb.owlapi.util.FilteringOWLOntologyChangeListener;
import org.semanticweb.owlapi.util.HashCode;
import org.semanticweb.owlapi.util.HornAxiomVisitorEx;
import org.semanticweb.owlapi.util.IRIShortFormProvider;
import org.semanticweb.owlapi.util.ImportsStructureEntitySorter;
import org.semanticweb.owlapi.util.ImportsStructureObjectSorter;
import org.semanticweb.owlapi.util.ImportsStructureObjectSorter.ObjectSelector;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAssertionAxiomGenerator;
import org.semanticweb.owlapi.util.InferredClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDataPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredDisjointClassesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentDataPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredEquivalentObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredIndividualAxiomGenerator;
import org.semanticweb.owlapi.util.InferredInverseObjectPropertiesAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredObjectPropertyCharacteristicAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredPropertyAssertionGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubDataPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.InferredSubObjectPropertyAxiomGenerator;
import org.semanticweb.owlapi.util.Monitorable;
import org.semanticweb.owlapi.util.MultiMap;
import org.semanticweb.owlapi.util.NNF;
import org.semanticweb.owlapi.util.NamedConjunctChecker;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;
import org.semanticweb.owlapi.util.NullProgressMonitor;
import org.semanticweb.owlapi.util.OWLAxiomFilter;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;
import org.semanticweb.owlapi.util.OWLClassExpressionCollector;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLClassLiteralCollector;
import org.semanticweb.owlapi.util.OWLEntityCollectingOntologyChangeListener;
import org.semanticweb.owlapi.util.OWLEntityCollector;
import org.semanticweb.owlapi.util.OWLEntityComparator;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLEntityRenamer;
import org.semanticweb.owlapi.util.OWLEntitySetProvider;
import org.semanticweb.owlapi.util.OWLEntityTinyURIConversionStrategy;
import org.semanticweb.owlapi.util.OWLEntityURIConverter;
import org.semanticweb.owlapi.util.OWLEntityURIConverterStrategy;
import org.semanticweb.owlapi.util.OWLEntityURIUnderscores2CamelBackConverterStrategy;
import org.semanticweb.owlapi.util.OWLEntityVisitorAdapter;
import org.semanticweb.owlapi.util.OWLEntityVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLObjectComponentCollector;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;
import org.semanticweb.owlapi.util.OWLObjectVisitorAdapter;
import org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLObjectWalker;
import org.semanticweb.owlapi.util.OWLOntologyChangeFilter;
import org.semanticweb.owlapi.util.OWLOntologyChangeVisitorAdapter;
import org.semanticweb.owlapi.util.OWLOntologyChangeVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLOntologyIRIChanger;
import org.semanticweb.owlapi.util.OWLOntologyImportsClosureSetProvider;
import org.semanticweb.owlapi.util.OWLOntologyMerger;
import org.semanticweb.owlapi.util.OWLOntologySingletonSetProvider;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;
import org.semanticweb.owlapi.util.ObjectPropertySimplifier;
import org.semanticweb.owlapi.util.OntologyIRIShortFormProvider;
import org.semanticweb.owlapi.util.ProgressMonitor;
import org.semanticweb.owlapi.util.PropertyAssertionValueShortFormProvider;
import org.semanticweb.owlapi.util.QNameShortFormProvider;
import org.semanticweb.owlapi.util.ReferencedEntitySetProvider;
import org.semanticweb.owlapi.util.RootClassChecker;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;
import org.semanticweb.owlapi.util.SWRLVariableShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.SimpleRenderer;
import org.semanticweb.owlapi.util.SimpleRootClassChecker;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.util.StructuralTransformation;
import org.semanticweb.owlapi.util.Version;
import org.semanticweb.owlapi.util.VersionInfo;
import org.semanticweb.owlapi.util.WeakCache;
import org.semanticweb.owlapi.util.WeakIndexCache;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiUtilTest {

    @Test
    public void shouldTestAbstractOWLOntologyStorer() throws OWLException {
        AbstractOWLOntologyStorer testSubject0 = new AbstractOWLOntologyStorer() {

            private static final long serialVersionUID = 40000L;

            @Override
            public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
                return false;
            }

            @Override
            protected void storeOntology(OWLOntology ontology, Writer writer,
                    OWLOntologyFormat format)
                    throws OWLOntologyStorageException {}
        };
        boolean result1 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestAnnotationValueShortFormProvider()
            throws OWLException {
        AnnotationValueShortFormProvider testSubject0 = new AnnotationValueShortFormProvider(
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class), mock(OWLOntologySetProvider.class));
        new AnnotationValueShortFormProvider(
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class), mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class));
        new AnnotationValueShortFormProvider(
                mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class),
                mock(IRIShortFormProvider.class),
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class));
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        List<OWLAnnotationProperty> result1 = testSubject0
                .getAnnotationProperties();
        Map<OWLAnnotationProperty, List<String>> result2 = testSubject0
                .getPreferredLanguageMap();
    }

    @Test
    public void shouldTestAxiomSubjectProvider() throws OWLException {
        AxiomSubjectProvider testSubject0 = new AxiomSubjectProvider();
        OWLObject result0 = testSubject0.getSubject(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestInterfaceBidirectionalShortFormProvider()
            throws OWLException {
        BidirectionalShortFormProvider testSubject0 = mock(BidirectionalShortFormProvider.class);
        Set<OWLEntity> result0 = testSubject0.getEntities("");
        OWLEntity result1 = testSubject0.getEntity("");
        Set<String> result2 = testSubject0.getShortForms();
        testSubject0.dispose();
        String result3 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestBidirectionalShortFormProviderAdapter()
            throws OWLException {
        BidirectionalShortFormProviderAdapter testSubject0 = new BidirectionalShortFormProviderAdapter(
                mock(ShortFormProvider.class));
        new BidirectionalShortFormProviderAdapter(Utils.mockSet(Utils
                .getMockOntology()), mock(ShortFormProvider.class));
        new BidirectionalShortFormProviderAdapter(Utils.getMockManager(),
                Utils.mockSet(Utils.getMockOntology()),
                mock(ShortFormProvider.class));
        testSubject0.dispose();
        testSubject0.add(Utils.mockOWLEntity());
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Set<OWLEntity> result1 = testSubject0.getEntities("");
        OWLEntity result2 = testSubject0.getEntity("");
        Set<String> result3 = testSubject0.getShortForms();
    }

    @Test
    public void shouldTestCachingBidirectionalShortFormProvider()
            throws OWLException {
        CachingBidirectionalShortFormProvider testSubject0 = new CachingBidirectionalShortFormProvider() {

            @Override
            protected String generateShortForm(OWLEntity entity) {
                return null;
            }
        };
        testSubject0.add(Utils.mockOWLEntity());
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Set<OWLEntity> result1 = testSubject0.getEntities("");
        OWLEntity result2 = testSubject0.getEntity("");
        Set<String> result3 = testSubject0.getShortForms();
    }

    @Test
    public void shouldTestCollectionFactory() throws OWLException {
        new CollectionFactory();
        Map<Object, Object> result0 = CollectionFactory.createMap();
        Set<Object> result1 = CollectionFactory
                .getCopyOnRequestSetFromMutableCollection(Utils
                        .mockCollection());
        Set<Object> result2 = CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Utils
                        .mockCollection());
        CollectionFactory.setExpectedThreads(8);
        Set<Object> result3 = CollectionFactory.createSet(Utils
                .mockCollection());
        Set<OWLClass> result4 = CollectionFactory.createSet(0);
        Set<Object> result5 = CollectionFactory.createSet(new Object());
        Set<OWLClass> result6 = CollectionFactory.createSet();
        List<Object> result7 = CollectionFactory.createList();
        Set<OWLClass> result8 = CollectionFactory.createSyncSet();
        Set<Object> result10 = CollectionFactory.getCopyOnRequestSet(Utils
                .mockCollection());
        Set<OWLOntology> result11 = CollectionFactory
                .getThreadSafeCopyOnRequestSet(Utils.mockSet(Utils
                        .getMockOntology()));
    }

    @Test
    public void shouldTestCommonBaseIRIMapper() throws OWLException {
        CommonBaseIRIMapper testSubject0 = new CommonBaseIRIMapper(
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
        testSubject0.addMapping(IRI("urn:aFake"), "");
    }

    @Test
    public void shouldTestDefaultPrefixManager() throws OWLException {
        DefaultPrefixManager testSubject0 = new DefaultPrefixManager(
                new DefaultPrefixManager());
        new DefaultPrefixManager();
        new DefaultPrefixManager("");
        testSubject0.clear();
        String result0 = testSubject0.getPrefix("");
        testSubject0.dispose();
        IRI result1 = testSubject0.getIRI("");
        testSubject0.setPrefix(":", "test");
        Map<String, String> result2 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result3 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result4 = testSubject0.containsPrefixMapping("");
        String result5 = testSubject0.getDefaultPrefix();
        String result6 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        String result7 = testSubject0.getShortForm(IRI("urn:aFake"));
        String result8 = testSubject0.getShortForm(Utils.mockOWLEntity());
        testSubject0.unregisterNamespace("");
    }

    @Test
    public void shouldTestDelegatingObjectVisitorEx() throws OWLException {
        DelegatingObjectVisitorEx<OWLObject> testSubject0 = new DelegatingObjectVisitorEx<OWLObject>(
                Utils.mockObject());
        OWLObject result0 = testSubject0
                .visit(mock(OWLAsymmetricObjectPropertyAxiom.class));
        OWLObject result9 = testSubject0
                .visit(mock(OWLDisjointDataPropertiesAxiom.class));
        OWLObject result10 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        OWLObject result14 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        OWLObject result15 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        OWLObject result16 = testSubject0
                .visit(mock(OWLFunctionalDataPropertyAxiom.class));
        OWLObject result17 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        OWLObject result18 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        OWLObject result19 = testSubject0
                .visit(mock(OWLInverseObjectPropertiesAxiom.class));
        OWLObject result20 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        OWLObject result21 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        OWLObject result22 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        OWLObject result23 = testSubject0
                .visit(mock(OWLObjectPropertyAssertionAxiom.class));
        OWLObject result28 = testSubject0
                .visit(mock(OWLReflexiveObjectPropertyAxiom.class));
        OWLObject result31 = testSubject0
                .visit(mock(OWLSymmetricObjectPropertyAxiom.class));
        OWLObject result32 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        OWLObject result73 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        OWLObject result74 = testSubject0
                .visit(mock(OWLAnnotationPropertyRangeAxiom.class));
        OWLObject result75 = testSubject0
                .visit(mock(OWLSubAnnotationPropertyOfAxiom.class));
    }

    @Test
    public void shouldTestDLExpressivityChecker() throws OWLException {
        DLExpressivityChecker testSubject0 = new DLExpressivityChecker(
                Utils.mockSet(Utils.getMockOntology()));
        List<Construct> result0 = testSubject0.getConstructs();
        String result1 = testSubject0.getDescriptionLogicName();
    }

    @Test
    public void shouldTestFilteringOWLOntologyChangeListener()
            throws OWLException {
        FilteringOWLOntologyChangeListener testSubject0 = new FilteringOWLOntologyChangeListener();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.processChanges(Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestHashCode() throws OWLException {
        new HashCode();
    }

    @Test
    public void shouldTestHornAxiomVisitorEx() throws OWLException {
        new HornAxiomVisitorEx();
    }

    @Test
    public void shouldTestImportsStructureEntitySorter() throws OWLException {
        new ImportsStructureEntitySorter(Utils.getMockOntology());
    }

    @Test
    public void shouldTestImportsStructureObjectSorter() throws OWLException {
        new ImportsStructureObjectSorter<Object>(Utils.getMockOntology(),
                mock(ObjectSelector.class));
    }

    @Test
    public void shouldTestInterfaceInferredAxiomGenerator() throws OWLException {
        InferredAxiomGenerator<OWLAxiom> testSubject0 = mock(InferredAxiomGenerator.class);
        Set<OWLAxiom> result0 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
        String result1 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredClassAssertionAxiomGenerator()
            throws OWLException {
        InferredClassAssertionAxiomGenerator testSubject0 = new InferredClassAssertionAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLClassAssertionAxiom> result2 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
    }

    @Test
    public void shouldTestInferredClassAxiomGenerator() throws OWLException {
        InferredClassAxiomGenerator<OWLClassAxiom> testSubject0 = new InferredClassAxiomGenerator<OWLClassAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLClass entity, OWLReasoner reasoner,
                    OWLDataFactory dataFactory, Set<OWLClassAxiom> result) {}
        };
        Set<OWLClassAxiom> result1 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredDataPropertyAxiomGenerator()
            throws OWLException {
        InferredDataPropertyAxiomGenerator<OWLDataPropertyAxiom> testSubject0 = new InferredDataPropertyAxiomGenerator<OWLDataPropertyAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLDataProperty entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLDataPropertyAxiom> result) {}
        };
        Set<OWLDataPropertyAxiom> result1 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredDataPropertyCharacteristicAxiomGenerator()
            throws OWLException {
        InferredDataPropertyCharacteristicAxiomGenerator testSubject0 = new InferredDataPropertyCharacteristicAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLDataPropertyCharacteristicAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredDisjointClassesAxiomGenerator()
            throws OWLException {
        InferredDisjointClassesAxiomGenerator testSubject0 = new InferredDisjointClassesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLDisjointClassesAxiom> result2 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentClassAxiomGenerator()
            throws OWLException {
        InferredEquivalentClassAxiomGenerator testSubject0 = new InferredEquivalentClassAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLEquivalentClassesAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManagerMockFactory().getOWLDataFactory(),
                Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentDataPropertiesAxiomGenerator()
            throws OWLException {
        InferredEquivalentDataPropertiesAxiomGenerator testSubject0 = new InferredEquivalentDataPropertiesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLEquivalentDataPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentObjectPropertyAxiomGenerator()
            throws OWLException {
        InferredEquivalentObjectPropertyAxiomGenerator testSubject0 = new InferredEquivalentObjectPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLEquivalentObjectPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredIndividualAxiomGenerator()
            throws OWLException {
        InferredIndividualAxiomGenerator<OWLIndividualAxiom> testSubject0 = new InferredIndividualAxiomGenerator<OWLIndividualAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLNamedIndividual entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLIndividualAxiom> result) {}
        };
        Set<OWLIndividualAxiom> result1 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredInverseObjectPropertiesAxiomGenerator()
            throws OWLException {
        InferredInverseObjectPropertiesAxiomGenerator testSubject0 = new InferredInverseObjectPropertiesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLInverseObjectPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredObjectPropertyAxiomGenerator()
            throws OWLException {
        InferredObjectPropertyAxiomGenerator<OWLObjectPropertyAxiom> testSubject0 = new InferredObjectPropertyAxiomGenerator<OWLObjectPropertyAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLObjectProperty entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLObjectPropertyAxiom> result) {}
        };
        Set<OWLObjectPropertyAxiom> result1 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredObjectPropertyCharacteristicAxiomGenerator()
            throws OWLException {
        InferredObjectPropertyCharacteristicAxiomGenerator testSubject0 = new InferredObjectPropertyCharacteristicAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLObjectPropertyCharacteristicAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredOntologyGenerator() throws OWLException {
        InferredOntologyGenerator testSubject0 = new InferredOntologyGenerator(
                Utils.structReasoner(),
                new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>());
        new InferredOntologyGenerator(Utils.structReasoner());
        List<InferredAxiomGenerator<?>> result0 = testSubject0
                .getAxiomGenerators();
        testSubject0.addGenerator(mock(InferredAxiomGenerator.class));
        testSubject0.removeGenerator(mock(InferredAxiomGenerator.class));
        testSubject0.fillOntology(Utils.getMockManagerMockFactory()
                .getOWLDataFactory(), Utils.getMockOntology());
    }

    @Test
    public void shouldTestInferredPropertyAssertionGenerator()
            throws OWLException {
        InferredPropertyAssertionGenerator testSubject0 = new InferredPropertyAssertionGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLPropertyAssertionAxiom<?, ?>> result2 = testSubject0
                .createAxioms(Utils.getMockManagerMockFactory()
                        .getOWLDataFactory(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredSubClassAxiomGenerator() throws OWLException {
        InferredSubClassAxiomGenerator testSubject0 = new InferredSubClassAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLSubClassOfAxiom> result2 = testSubject0.createAxioms(Utils
                .getMockManagerMockFactory().getOWLDataFactory(), Utils
                .structReasoner());
    }

    @Test
    public void shouldTestInferredSubDataPropertyAxiomGenerator()
            throws OWLException {
        InferredSubDataPropertyAxiomGenerator testSubject0 = new InferredSubDataPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLSubDataPropertyOfAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManagerMockFactory().getOWLDataFactory(),
                Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredSubObjectPropertyAxiomGenerator()
            throws OWLException {
        InferredSubObjectPropertyAxiomGenerator testSubject0 = new InferredSubObjectPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        Set<OWLSubObjectPropertyOfAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManagerMockFactory().getOWLDataFactory(),
                Utils.structReasoner());
    }

    @Test
    public void shouldTestInterfaceIRIShortFormProvider() throws OWLException {
        IRIShortFormProvider testSubject0 = mock(IRIShortFormProvider.class);
        String result0 = testSubject0.getShortForm(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInterfaceMonitorable() throws OWLException,
            InterruptedException {
        Monitorable testSubject0 = mock(Monitorable.class);
        testSubject0.interrupt();
        testSubject0.setProgressMonitor(mock(ProgressMonitor.class));
        boolean result0 = testSubject0.canInterrupt();
    }

    @Test
    public void shouldTestMultiMap() throws OWLException {
        MultiMap<Object, Object> testSubject0 = new MultiMap<Object, Object>(
                false);
        new MultiMap<Object, Object>(false, false);
        new MultiMap<Object, Object>();
        Collection<Object> result0 = testSubject0.get(mock(Object.class));
        boolean result1 = testSubject0.put(mock(Object.class),
                mock(Object.class));
        testSubject0.clear();
        boolean result3 = testSubject0.contains(mock(Object.class),
                mock(Object.class));
        int result4 = testSubject0.size();
        testSubject0.putAll(new MultiMap<Object, Object>());
        testSubject0.putAll(mock(Object.class), Utils.mockCollection());
        boolean result5 = testSubject0.remove(mock(Object.class),
                mock(Object.class));
        boolean result6 = testSubject0.remove(mock(Object.class));
        Set<Object> result7 = testSubject0.keySet();
        boolean result8 = testSubject0.containsValue(mock(Object.class));
        boolean result9 = testSubject0.containsKey(mock(Object.class));
        testSubject0.setEntry(mock(Object.class), Utils.mockCollection());
        Set<Object> result10 = testSubject0.getAllValues();
        boolean result11 = testSubject0.isValueSetsEqual();
    }

    @Test
    public void shouldTestNamedConjunctChecker() throws OWLException {
        NamedConjunctChecker testSubject0 = new NamedConjunctChecker();
        boolean result0 = testSubject0.isNamedConjunct(mock(OWLClass.class),
                Utils.mockAnonClass());
        boolean result1 = testSubject0.hasNamedConjunct(Utils.mockAnonClass());
        Set<OWLClass> result2 = testSubject0.getNamedConjuncts(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestNNF() throws OWLException {
        NNF testSubject0 = new NNF(mock(OWLDataFactory.class));
        testSubject0.reset();
    }

    @Test
    public void shouldTestNonMappingOntologyIRIMapper() throws OWLException {
        NonMappingOntologyIRIMapper testSubject0 = new NonMappingOntologyIRIMapper();
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestNullProgressMonitor() throws OWLException {
        NullProgressMonitor testSubject0 = new NullProgressMonitor();
        testSubject0.setSize(0L);
        testSubject0.setStarted();
        testSubject0.setProgress(0L);
        testSubject0.setMessage("");
        testSubject0.setIndeterminate(false);
        testSubject0.setFinished();
        boolean result0 = testSubject0.isCancelled();
    }

    @Test
    public void shouldTestObjectPropertySimplifier() throws OWLException {
        ObjectPropertySimplifier testSubject0 = new ObjectPropertySimplifier(
                mock(OWLDataFactory.class));
        OWLObjectPropertyExpression result0 = testSubject0.getSimplified(Utils
                .mockObjectProperty());
    }

    @Test
    public void shouldTestOntologyIRIShortFormProvider() throws OWLException {
        OntologyIRIShortFormProvider testSubject0 = new OntologyIRIShortFormProvider();
        String result0 = testSubject0.getShortForm(Utils.getMockOntology());
        String result1 = testSubject0.getShortForm(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInterfaceOWLAxiomFilter() throws OWLException {
        OWLAxiomFilter testSubject0 = mock(OWLAxiomFilter.class);
        boolean result0 = testSubject0.passes(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLAxiomSearchFilter() throws OWLException {
        OWLAxiomSearchFilter testSubject0 = mock(OWLAxiomSearchFilter.class);
        Iterable<AxiomType<?>> result0 = testSubject0.getAxiomTypes();
        boolean result1 = testSubject0.pass(mock(OWLAxiom.class),
                mock(Object.class));
    }

    @Test
    public void shouldTestOWLAxiomVisitorAdapter() throws OWLException {
        new OWLAxiomVisitorAdapter();
    }

    @Test
    public void shouldTestOWLClassExpressionCollector() throws OWLException {
        new OWLClassExpressionCollector();
    }

    @Test
    public void shouldTestOWLClassExpressionVisitorAdapter()
            throws OWLException {
        new OWLClassExpressionVisitorAdapter();
    }

    @Test
    public void shouldTestOWLClassExpressionVisitorExAdapter()
            throws OWLException {
        new OWLClassExpressionVisitorExAdapter<Object>();
    }

    @Test
    public void shouldTestOWLClassLiteralCollector() throws OWLException {
        OWLClassLiteralCollector testSubject0 = new OWLClassLiteralCollector(
                Utils.mockSet(mock(OWLObject.class)));
        new OWLClassLiteralCollector(Utils.mockSet(mock(OWLObject.class)),
                false);
        Set<OWLClass> result0 = testSubject0.getPositiveLiterals();
        Set<OWLClass> result1 = testSubject0.getNegativeLiterals();
        OWLAnnotation result2 = testSubject0.getAnnotation();
        OWLOntology result3 = testSubject0.getOntology();
        OWLAxiom result4 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result5 = testSubject0
                .getClassExpressionPath();
        boolean result6 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result7 = testSubject0.getDataRangePath();
    }

    @Test
    public void shouldTestOWLEntityCollectingOntologyChangeListener()
            throws OWLException {
        OWLEntityCollectingOntologyChangeListener testSubject0 = new OWLEntityCollectingOntologyChangeListener() {

            @Override
            public void ontologiesChanged() throws OWLException {}
        };
        Set<OWLEntity> result0 = testSubject0.getEntities();
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
        testSubject0.ontologiesChanged();
    }

    @Test
    public void shouldTestOWLEntityCollector() throws OWLException {
        new OWLEntityCollector(Utils.mockSet(Utils.mockOWLEntity()));
    }

    @Test
    public void shouldTestOWLEntityComparator() throws OWLException {
        new OWLEntityComparator(mock(ShortFormProvider.class));
    }

    @Test
    public void shouldTestOWLEntityRemover() throws OWLException {
        OWLEntityRemover testSubject0 = new OWLEntityRemover(
                Utils.mockSet(Utils.getMockOntology()));
        testSubject0.reset();
        List<RemoveAxiom> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestOWLEntityRenamer() throws OWLException {
        OWLEntityRenamer testSubject0 = new OWLEntityRenamer(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange<?>> result0 = testSubject0.changeIRI(
                IRI("urn:aFake"), IRI("urn:aFake"));
        List<OWLOntologyChange<?>> result1 = testSubject0.changeIRI(
                Utils.mockOWLEntity(), IRI("urn:aFake"));
        List<OWLOntologyChange<?>> result2 = testSubject0
                .changeIRI(mock(Map.class));
    }

    @Test
    public void shouldTestInterfaceOWLEntitySetProvider() throws OWLException {
        OWLEntitySetProvider<OWLClass> testSubject0 = mock(OWLEntitySetProvider.class);
        Set<OWLClass> result0 = testSubject0.getEntities();
    }

    @Test
    public void shouldTestOWLEntityTinyURIConversionStrategy()
            throws OWLException {
        OWLEntityTinyURIConversionStrategy testSubject0 = new OWLEntityTinyURIConversionStrategy();
        new OWLEntityTinyURIConversionStrategy("");
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestOWLEntityURIConverter() throws OWLException {
        OWLEntityURIConverter testSubject0 = new OWLEntityURIConverter(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()),
                mock(OWLEntityURIConverterStrategy.class));
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges();
    }

    @Test
    public void shouldTestInterfaceOWLEntityURIConverterStrategy()
            throws OWLException {
        OWLEntityURIConverterStrategy testSubject0 = mock(OWLEntityURIConverterStrategy.class);
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestOWLEntityURIUnderscores2CamelBackConverterStrategy()
            throws OWLException {
        OWLEntityURIUnderscores2CamelBackConverterStrategy testSubject0 = new OWLEntityURIUnderscores2CamelBackConverterStrategy();
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestOWLEntityVisitorAdapter() throws OWLException {
        new OWLEntityVisitorAdapter();
    }

    @Test
    public void shouldTestOWLEntityVisitorExAdapter() throws OWLException {
        new OWLEntityVisitorExAdapter<Object>(mock(Object.class));
        new OWLEntityVisitorExAdapter<Object>();
    }

    @Test
    public void shouldTestOWLObjectComponentCollector() throws OWLException {
        OWLObjectComponentCollector testSubject0 = new OWLObjectComponentCollector();
        Set<OWLObject> result0 = testSubject0
                .getComponents(mock(OWLObject.class));
        Set<OWLObject> result1 = testSubject0.getResult();
    }

    @Test
    public void shouldTestOWLObjectDuplicator() throws OWLException {
        OWLObjectDuplicator testSubject0 = new OWLObjectDuplicator(
                mock(OWLDataFactory.class));
        new OWLObjectDuplicator(mock(OWLDataFactory.class), mock(Map.class));
        new OWLObjectDuplicator(mock(Map.class), mock(OWLDataFactory.class));
        OWLObject result0 = testSubject0.duplicateObject(mock(OWLObject.class));
    }

    public void shouldTestOWLObjectPropertyManager() throws OWLException {
        OWLObjectPropertyManager testSubject0 = new OWLObjectPropertyManager(
                Utils.getMockManager(), Utils.getMockOntology());
        testSubject0.dispose();
        Collection<Set<OWLObjectPropertyExpression>> result0 = OWLObjectPropertyManager
                .getEquivalentObjectProperties(Utils.mockSet(Utils
                        .getMockOntology()));
        Collection<Set<OWLObjectPropertyExpression>> result1 = testSubject0
                .getEquivalentObjectProperties();
        boolean result2 = testSubject0.isComposite(Utils.mockObjectProperty());
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getCompositeProperties();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result4 = testSubject0
                .getPropertyHierarchy();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result5 = testSubject0
                .getHierarchyReflexiveTransitiveClosure();
        boolean result6 = testSubject0.isSubPropertyOf(
                Utils.mockObjectProperty(), Utils.mockObjectProperty());
        boolean result7 = testSubject0.isNonSimple(Utils.mockObjectProperty());
        Set<OWLObjectPropertyExpression> result8 = testSubject0
                .getNonSimpleProperties();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result9 = testSubject0
                .getPropertyPartialOrdering();
        boolean result10 = testSubject0.isLessThan(Utils.mockObjectProperty(),
                Utils.mockObjectProperty());
        OWLObjectPropertyManager.tarjan(Utils.mockSet(Utils.getMockOntology()),
                Utils.mockObjectProperty(), 0, mock(Stack.class),
                mock(Map.class), mock(Map.class),
                Utils.mockSet(Utils.mockSet(Utils.mockObjectProperty())),
                Utils.mockSet(Utils.mockObjectProperty()),
                Utils.mockSet(Utils.mockObjectProperty()));
    }

    @Test
    public void shouldTestOWLObjectTypeIndexProvider() throws OWLException {
        OWLObjectTypeIndexProvider testSubject0 = new OWLObjectTypeIndexProvider();
        int result0 = testSubject0.getTypeIndex(mock(OWLObject.class));
    }

    @Test
    public void shouldTestOWLObjectVisitorAdapter() throws OWLException {
        new OWLObjectVisitorAdapter();
    }

    @Test
    public void shouldTestOWLObjectVisitorExAdapter() throws OWLException {
        OWLObjectVisitorExAdapter<Object> testSubject0 = new OWLObjectVisitorExAdapter<Object>(
                mock(Object.class));
        new OWLObjectVisitorExAdapter<Object>();
        Object result12 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result13 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        Object result15 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        Object result17 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        Object result19 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result20 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        Object result21 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        Object result32 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result65 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
    }

    @Test
    public void shouldTestOWLObjectWalker() throws OWLException {
        OWLObjectWalker<OWLOntology> testSubject0 = new OWLObjectWalker<OWLOntology>(
                Utils.mockSet(Utils.getMockOntology()));
        new OWLObjectWalker<OWLOntology>(
                Utils.mockSet(Utils.getMockOntology()), false);
        OWLAnnotation result0 = testSubject0.getAnnotation();
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result3 = testSubject0
                .getClassExpressionPath();
        boolean result4 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result5 = testSubject0.getDataRangePath();
    }

    @Test
    public void shouldTestOWLOntologyChangeFilter() throws OWLException {
        OWLOntologyChangeFilter testSubject0 = new OWLOntologyChangeFilter();
        testSubject0.processChanges(Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestOWLOntologyChangeVisitorAdapter() throws OWLException {
        new OWLOntologyChangeVisitorAdapter();
    }

    @Test
    public void shouldTestOWLOntologyChangeVisitorAdapterEx()
            throws OWLException {
        new OWLOntologyChangeVisitorExAdapter<Object>();
    }

    public void shouldTestOWLOntologyImportsClosureSetProvider()
            throws OWLException {
        OWLOntologyImportsClosureSetProvider testSubject0 = new OWLOntologyImportsClosureSetProvider(
                Utils.getMockManager(), Utils.getMockOntology());
        Set<OWLOntology> result0 = testSubject0.getOntologies();
    }

    @Test
    public void shouldTestOWLOntologyMerger() throws OWLException {
        OWLOntologyMerger testSubject0 = new OWLOntologyMerger(
                mock(OWLOntologySetProvider.class));
        new OWLOntologyMerger(mock(OWLOntologySetProvider.class), false);
        new OWLOntologyMerger(mock(OWLOntologySetProvider.class),
                mock(OWLAxiomFilter.class));
        boolean result0 = testSubject0.passes(mock(OWLAxiom.class));
        OWLOntology result1 = testSubject0.createMergedOntology(
                Utils.getMockManager(), IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLOntologySingletonSetProvider() throws OWLException {
        OWLOntologySingletonSetProvider testSubject0 = new OWLOntologySingletonSetProvider(
                Utils.getMockOntology());
        Set<OWLOntology> result0 = testSubject0.getOntologies();
    }

    @Test
    public void shouldTestOWLOntologyURIChanger() throws OWLException {
        OWLOntologyIRIChanger testSubject0 = new OWLOntologyIRIChanger(
                Utils.getMockManager());
        List<OWLOntologyChange<?>> result0 = testSubject0.getChanges(
                Utils.getMockOntology(), IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLOntologyWalker() throws OWLException {
        OWLOntologyWalker testSubject0 = new OWLOntologyWalker(
                Utils.mockSet(Utils.getMockOntology()));
        OWLAnnotation result0 = testSubject0.getAnnotation();
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result3 = testSubject0
                .getClassExpressionPath();
        boolean result4 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result5 = testSubject0.getDataRangePath();
    }

    @Test
    public void shouldTestOWLOntologyWalkerVisitor() throws OWLException {
        OWLOntologyWalkerVisitorEx<Object> testSubject0 = new OWLOntologyWalkerVisitorEx<Object>(
                mock(OWLOntologyWalker.class));
        OWLAxiom result0 = testSubject0.getCurrentAxiom();
        OWLOntology result1 = testSubject0.getCurrentOntology();
        OWLAnnotation result2 = testSubject0.getCurrentAnnotation();
        Object result12 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        Object result15 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result16 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        Object result18 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        Object result20 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        Object result22 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result23 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        Object result24 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        Object result35 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result68 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
    }

    @Test
    public void shouldTestInterfaceProgressMonitor() throws OWLException {
        ProgressMonitor testSubject0 = mock(ProgressMonitor.class);
        testSubject0.setSize(0L);
        testSubject0.setStarted();
        testSubject0.setProgress(0L);
        testSubject0.setMessage("");
        testSubject0.setIndeterminate(false);
        testSubject0.setFinished();
        boolean result0 = testSubject0.isCancelled();
    }

    @Test
    public void shouldTestPropertyAssertionValueShortFormProvider()
            throws OWLException {
        PropertyAssertionValueShortFormProvider testSubject0 = new PropertyAssertionValueShortFormProvider(
                new ArrayList<OWLPropertyExpression>(), mock(Map.class),
                mock(OWLOntologySetProvider.class));
        new PropertyAssertionValueShortFormProvider(
                new ArrayList<OWLPropertyExpression>(), mock(Map.class),
                mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class));
        List<OWLPropertyExpression> result0 = testSubject0.getProperties();
        testSubject0.dispose();
        String result1 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Map<OWLDataPropertyExpression, List<String>> result2 = testSubject0
                .getPreferredLanguageMap();
    }

    public void shouldTestQNameShortFormProvider() throws OWLException {
        QNameShortFormProvider testSubject0 = new QNameShortFormProvider();
        new QNameShortFormProvider(mock(Map.class));
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestReferencedEntitySetProvider() throws OWLException {
        ReferencedEntitySetProvider testSubject0 = new ReferencedEntitySetProvider(
                Utils.mockSet(Utils.getMockOntology()));
        Set<OWLEntity> result0 = testSubject0.getEntities();
    }

    @Test
    public void shouldTestInterfaceRootClassChecker() throws OWLException {
        RootClassChecker testSubject0 = mock(RootClassChecker.class);
        boolean result0 = testSubject0.isRootClass(mock(OWLClass.class));
    }

    @Test
    public void shouldTestInterfaceShortFormProvider() throws OWLException {
        ShortFormProvider testSubject0 = mock(ShortFormProvider.class);
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestSimpleIRIMapper() throws OWLException {
        SimpleIRIMapper testSubject0 = new SimpleIRIMapper(IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestSimpleIRIShortFormProvider() throws OWLException {
        SimpleIRIShortFormProvider testSubject0 = new SimpleIRIShortFormProvider();
        String result0 = testSubject0.getShortForm(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestSimpleRenderer() throws OWLException {
        SimpleRenderer testSubject0 = new SimpleRenderer();
        testSubject0.reset();
        String result1 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setPrefix("test:", "urn:test");
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        String result2 = testSubject0.getShortForm(IRI("urn:aFake"));
        testSubject0.resetShortFormProvider();
        boolean result3 = testSubject0.isUsingDefaultShortFormProvider();
        testSubject0.setPrefixesFromOntologyFormat(Utils.getMockOntology(),
                Utils.getMockManager(), false);
        testSubject0.writeAnnotations(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestSimpleRootClassChecker() throws OWLException {
        SimpleRootClassChecker testSubject0 = new SimpleRootClassChecker(
                Utils.mockSet(Utils.getMockOntology()));
        boolean result0 = testSubject0.isRootClass(mock(OWLClass.class));
    }

    @Test
    public void shouldTestSimpleShortFormProvider() throws OWLException {
        SimpleShortFormProvider testSubject0 = new SimpleShortFormProvider();
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestStructuralTransformation() throws OWLException {
        StructuralTransformation testSubject0 = new StructuralTransformation(
                mock(OWLDataFactory.class));
        Set<OWLAxiom> result0 = testSubject0.getTransformedAxioms(Utils
                .mockSet(mock(OWLAxiom.class)));
    }

    @Test
    public void shouldTestSWRLVariableExtractor() throws OWLException {
        SWRLVariableExtractor testSubject0 = new SWRLVariableExtractor();
        Set<SWRLVariable> result0 = testSubject0.getVariables();
    }

    @Test
    public void shouldTestInterfaceSWRLVariableShortFormProvider()
            throws OWLException {
        SWRLVariableShortFormProvider testSubject0 = mock(SWRLVariableShortFormProvider.class);
        String result0 = testSubject0.getShortForm(mock(SWRLVariable.class));
    }

    @Test
    public void shouldTestVersion() throws OWLException {
        Version testSubject0 = new Version(0, 0, 0, 0);
        int result0 = testSubject0.getMajor();
        int result1 = testSubject0.getMinor();
        int result2 = testSubject0.getPatch();
        int result3 = testSubject0.getBuild();
    }

    @Test
    public void shouldTestVersionInfo() throws OWLException {
        VersionInfo testSubject0 = VersionInfo.getVersionInfo();
        String result1 = testSubject0.getVersion();
        VersionInfo result2 = VersionInfo.getVersionInfo();
        String result3 = testSubject0.getGeneratedByMessage();
    }

    @Test
    public void shouldTestWeakCache() throws OWLException {
        WeakCache<Object> testSubject0 = new WeakCache<Object>();
        Object result0 = testSubject0.cache(mock(Object.class));
        testSubject0.clear();
        boolean result1 = testSubject0.contains(mock(Object.class));
    }

    @Test
    public void shouldTestWeakIndexCache() throws OWLException {
        WeakIndexCache<Object, Object> testSubject0 = new WeakIndexCache<Object, Object>();
        Object result0 = testSubject0.get(mock(Object.class));
        Object result1 = testSubject0.cache(mock(Object.class),
                mock(Object.class));
        testSubject0.clear();
        boolean result2 = testSubject0.contains(mock(Object.class));
    }
}
