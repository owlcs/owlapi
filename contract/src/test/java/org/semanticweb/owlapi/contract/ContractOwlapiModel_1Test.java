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

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.AddImportData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiModel_1Test {

    @Test
    public void shouldTestAddAxiom() throws OWLException {
        AddAxiom testSubject0 = new AddAxiom(Utils.getMockOntology(),
                mock(OWLAxiom.class));
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        OWLAxiom result2 = testSubject0.getAxiom();
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        Set<OWLEntity> result5 = testSubject0.getSignature();
        OWLOntology result6 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestAddImport() throws OWLException {
        AddImport testSubject0 = new AddImport(Utils.getMockOntology(),
                mock(OWLImportsDeclaration.class));
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        OWLImportsDeclaration result5 = testSubject0.getImportDeclaration();
        OWLOntology result6 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestAddOntologyAnnotation() throws OWLException {
        AddOntologyAnnotation testSubject0 = new AddOntologyAnnotation(
                Utils.getMockOntology(), mock(OWLAnnotation.class));
        OWLAnnotation result1 = testSubject0.getAnnotation();
        Object result2 = testSubject0.accept(Utils.mockOntologyChange());
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        boolean result4 = testSubject0.isAxiomChange();
        boolean result5 = testSubject0.isImportChange();
        OWLOntology result6 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestAxiomType() throws OWLException {
        AxiomType<?> testSubject0 = AxiomType.ANNOTATION_ASSERTION;
        String result1 = testSubject0.getName();
        int result2 = testSubject0.getIndex();
        AxiomType<?> result3 = AxiomType.getAxiomType("");
        boolean result4 = testSubject0.isLogical();
        boolean result5 = testSubject0.isOWL2Axiom();
        boolean result6 = testSubject0.isNonSyntacticOWL2Axiom();
        Set<OWLAxiom> result7 = AxiomType.getAxiomsWithoutTypes(
                Utils.mockSet(mock(OWLAxiom.class)), AxiomType.CLASS_ASSERTION);
        Set<OWLAxiom> result8 = AxiomType.getAxiomsOfTypes(
                Utils.mockSet(mock(OWLAxiom.class)), AxiomType.CLASS_ASSERTION);
        boolean result9 = testSubject0.isAxiomType("");
    }

    @Test
    public void shouldTestClassExpressionType() throws OWLException {
        ClassExpressionType testSubject0 = ClassExpressionType.DATA_ALL_VALUES_FROM;
        ClassExpressionType[] result1 = ClassExpressionType.values();
        String result3 = testSubject0.getName();
        String result4 = testSubject0.name();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDataRangeType() throws OWLException {
        DataRangeType testSubject0 = DataRangeType.DATA_COMPLEMENT_OF;
        DataRangeType[] result0 = DataRangeType.values();
        String result2 = testSubject0.getName();
        String result3 = testSubject0.name();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDefaultChangeBroadcastStrategy() throws OWLException {
        DefaultChangeBroadcastStrategy testSubject0 = new DefaultChangeBroadcastStrategy();
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestDefaultImpendingChangeBroadcastStrategy()
            throws OWLException {
        DefaultImpendingChangeBroadcastStrategy testSubject0 = new DefaultImpendingChangeBroadcastStrategy();
        testSubject0.broadcastChanges(
                mock(ImpendingOWLOntologyChangeListener.class),
                Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestEDTChangeBroadcastStrategy() throws OWLException {
        EDTChangeBroadcastStrategy testSubject0 = new EDTChangeBroadcastStrategy();
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestEntityType() throws OWLException {
        EntityType<?> testSubject0 = EntityType.ANNOTATION_PROPERTY;
        List<EntityType<?>> result1 = EntityType.values();
        String result2 = testSubject0.getName();
        OWLRDFVocabulary result3 = testSubject0.getVocabulary();
    }

    @Test
    public void shouldTestImmutableOWLOntologyChangeException()
            throws OWLException {
        ImmutableOWLOntologyChangeException testSubject0 = new ImmutableOWLOntologyChangeException(
                mock(OWLOntologyChange.class));
        OWLOntologyChange<?> result0 = testSubject0.getChange();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void
            shouldTestInterfaceImpendingOWLOntologyChangeBroadcastStrategy()
                    throws OWLException {
        ImpendingOWLOntologyChangeBroadcastStrategy testSubject0 = mock(ImpendingOWLOntologyChangeBroadcastStrategy.class);
        testSubject0.broadcastChanges(
                mock(ImpendingOWLOntologyChangeListener.class),
                Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestInterfaceImpendingOWLOntologyChangeListener()
            throws OWLException {
        ImpendingOWLOntologyChangeListener testSubject0 = mock(ImpendingOWLOntologyChangeListener.class);
        testSubject0.handleImpendingOntologyChanges(Utils
                .mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestImportChange() throws OWLException {
        ImportChange testSubject0 = new ImportChange(Utils.getMockOntology(),
                mock(OWLImportsDeclaration.class)) {

            @Override
            public void accept(OWLOntologyChangeVisitor visitor) {}

            @Override
            public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public AddImportData getChangeData() {
                return mock(AddImportData.class);
            }
        };
        boolean result1 = testSubject0.isAxiomChange();
        boolean result2 = testSubject0.isImportChange();
        OWLImportsDeclaration result3 = testSubject0.getImportDeclaration();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockOntologyChange());
        OWLOntology result5 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestIRI() throws OWLException {
        IRI testSubject0 = IRI("urn:fake#aFake");
        boolean result0 = testSubject0.isAbsolute();
        URI result1 = testSubject0.toURI();
        IRI result2 = testSubject0.resolve("");
        String result3 = testSubject0.getScheme();
        String result4 = testSubject0.getFragment();
        String result10 = testSubject0.getNamespace();
        boolean result11 = testSubject0.isReservedVocabulary();
        boolean result12 = testSubject0.isThing();
        boolean result13 = testSubject0.isNothing();
        boolean result14 = testSubject0.isPlainLiteral();
        String result15 = testSubject0.toQuotedString();
        IRI result16 = IRI.generateDocumentIRI();
        testSubject0.accept(mock(OWLAnnotationSubjectVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockAnnotationSubject());
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result31 = testSubject0.accept(Utils.mockAnnotationValue());
        int result32 = testSubject0.length();
        char result33 = testSubject0.charAt(0);
        CharSequence result34 = testSubject0.subSequence(0, 0);
    }

    @Test
    public void shouldTestMissingImportEvent() throws OWLException {
        MissingImportEvent testSubject0 = new MissingImportEvent(
                IRI("urn:aFake"), mock(OWLOntologyCreationException.class));
        IRI result0 = testSubject0.getImportedOntologyURI();
        OWLOntologyCreationException result1 = testSubject0
                .getCreationException();
    }

    @Test
    public void shouldTestMissingImportHandlingStrategy() throws OWLException {
        MissingImportHandlingStrategy testSubject0 = MissingImportHandlingStrategy.SILENT;
        MissingImportHandlingStrategy[] result0 = MissingImportHandlingStrategy
                .values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInterfaceMissingImportListener() throws OWLException {
        MissingImportListener testSubject0 = mock(MissingImportListener.class);
        testSubject0.importMissing(mock(MissingImportEvent.class));
    }

    @Test
    public void shouldTestNodeID() throws OWLException {
        NodeID testSubject0 = NodeID.getNodeID("_:test1");
        String result0 = testSubject0.getID();
        NodeID result1 = NodeID.getNodeID("");
        NodeID result2 = NodeID.getNodeID(null);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotation() throws OWLException {
        OWLAnnotation testSubject0 = mock(OWLAnnotation.class);
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        OWLAnnotationValue result1 = testSubject0.getValue();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        testSubject0.accept(mock(OWLAnnotationObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockAnnotationObject());
        boolean result4 = testSubject0.isDeprecatedIRIAnnotation();
        OWLAnnotation result5 = testSubject0.getAnnotatedAnnotation(Utils
                .mockSet(mock(OWLAnnotation.class)));
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationAssertionAxiom()
            throws OWLException {
        OWLAnnotationAssertionAxiom testSubject0 = mock(OWLAnnotationAssertionAxiom.class);
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        OWLAnnotationValue result1 = testSubject0.getValue();
        OWLAnnotation result2 = testSubject0.getAnnotation();
        OWLAnnotationSubject result3 = testSubject0.getSubject();
        OWLAnnotationAssertionAxiom result4 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result5 = testSubject0.isDeprecatedIRIAssertion();
        Set<OWLAnnotation> result6 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result7 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result8 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result9 = testSubject0.getAxiomWithoutAnnotations();
        boolean result11 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result12 = testSubject0.isLogicalAxiom();
        boolean result13 = testSubject0.isAnnotationAxiom();
        boolean result14 = testSubject0.isAnnotated();
        AxiomType<?> result15 = testSubject0.getAxiomType();
        boolean result16 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result17 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationAxiom() throws OWLException {
        OWLAnnotationAxiom testSubject0 = mock(OWLAnnotationAxiom.class);
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result1 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result5 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        boolean result8 = testSubject0.isAnnotated();
        AxiomType<?> result9 = testSubject0.getAxiomType();
        boolean result10 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result11 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationAxiomVisitor()
            throws OWLException {
        OWLAnnotationAxiomVisitor testSubject0 = mock(OWLAnnotationAxiomVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationAxiomVisitorEx()
            throws OWLException {
        OWLAnnotationAxiomVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationObject() throws OWLException {
        OWLAnnotationObject testSubject0 = mock(OWLAnnotationObject.class);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result9 = testSubject0.isTopEntity();
        boolean result10 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationObjectVisitor()
            throws OWLException {
        OWLAnnotationObjectVisitor testSubject0 = mock(OWLAnnotationObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationObjectVisitorEx()
            throws OWLException {
        OWLAnnotationObjectVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationObject();
        OWLObject result2 = testSubject0
                .visit(mock(OWLSubAnnotationPropertyOfAxiom.class));
        OWLObject result3 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        OWLObject result4 = testSubject0
                .visit(mock(OWLAnnotationPropertyRangeAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationProperty() throws OWLException {
        OWLAnnotationProperty testSubject0 = mock(OWLAnnotationProperty.class);
        boolean result0 = testSubject0.isBuiltIn();
        boolean result1 = testSubject0.isComment();
        boolean result2 = testSubject0.isLabel();
        boolean result3 = testSubject0.isDeprecated();
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result12 = testSubject0.accept(Utils.mockEntity());
        boolean result13 = testSubject0.isType(EntityType.CLASS);
        EntityType<?> result17 = testSubject0.getEntityType();
        boolean result19 = !testSubject0.isOWLClass();
        if (testSubject0.isOWLClass()) {
            OWLClass result20 = testSubject0.asOWLClass();
        }
        boolean result21 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result22 = testSubject0.asOWLObjectProperty();
        }
        boolean result23 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result24 = testSubject0.asOWLDataProperty();
        }
        boolean result25 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result26 = testSubject0.asOWLNamedIndividual();
        }
        boolean result27 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result28 = testSubject0.asOWLDatatype();
        }
        boolean result29 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result30 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result31 = testSubject0.toStringID();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result41 = testSubject0.isTopEntity();
        boolean result42 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result44 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationPropertyDomainAxiom()
            throws OWLException {
        OWLAnnotationPropertyDomainAxiom testSubject0 = mock(OWLAnnotationPropertyDomainAxiom.class);
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        IRI result1 = testSubject0.getDomain();
        OWLAnnotationPropertyDomainAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result4 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result6 = testSubject0.getAxiomWithoutAnnotations();
        boolean result8 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result9 = testSubject0.isLogicalAxiom();
        boolean result10 = testSubject0.isAnnotationAxiom();
        boolean result11 = testSubject0.isAnnotated();
        AxiomType<?> result12 = testSubject0.getAxiomType();
        boolean result13 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result14 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationPropertyRangeAxiom()
            throws OWLException {
        OWLAnnotationPropertyRangeAxiom testSubject0 = mock(OWLAnnotationPropertyRangeAxiom.class);
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        OWLAnnotationPropertyRangeAxiom result1 = testSubject0
                .getAxiomWithoutAnnotations();
        IRI result2 = testSubject0.getRange();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result4 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result6 = testSubject0.getAxiomWithoutAnnotations();
        boolean result8 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result9 = testSubject0.isLogicalAxiom();
        boolean result10 = testSubject0.isAnnotationAxiom();
        boolean result11 = testSubject0.isAnnotated();
        AxiomType<?> result12 = testSubject0.getAxiomType();
        boolean result13 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result14 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationSubject() throws OWLException {
        OWLAnnotationSubject testSubject0 = mock(OWLAnnotationSubject.class);
        testSubject0.accept(mock(OWLAnnotationSubjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAnnotationSubject());
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result10 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationSubjectVisitor()
            throws OWLException {
        OWLAnnotationSubjectVisitor testSubject0 = mock(OWLAnnotationSubjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationSubjectVisitorEx()
            throws OWLException {
        OWLAnnotationSubjectVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationSubject();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationValue() throws OWLException {
        OWLAnnotationValue testSubject0 = mock(OWLAnnotationValue.class);
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAnnotationValue());
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result10 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationValueVisitor()
            throws OWLException {
        OWLAnnotationValueVisitor testSubject0 = mock(OWLAnnotationValueVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationValueVisitorEx()
            throws OWLException {
        OWLAnnotationValueVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationValue();
    }

    @Test
    public void shouldTestInterfaceOWLAnonymousClassExpression()
            throws OWLException {
        OWLAnonymousClassExpression testSubject0 = mock(OWLAnonymousClassExpression.class);
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result2 = testSubject0.asOWLClass();
        }
        ClassExpressionType result4 = testSubject0.getClassExpressionType();
        boolean result5 = testSubject0.isClassExpressionLiteral();
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLAnonymousIndividual() throws OWLException {
        OWLAnonymousIndividual testSubject0 = mock(OWLAnonymousIndividual.class);
        NodeID result0 = testSubject0.getID();
        Object result1 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result2 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLNamedIndividual result3 = testSubject0.asOWLNamedIndividual();
        }
        String result4 = testSubject0.toStringID();
        boolean result5 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result6 = testSubject0
                    .asOWLAnonymousIndividual();
        }
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result32 = testSubject0.accept(Utils.mockAnnotationValue());
        testSubject0.accept(mock(OWLAnnotationSubjectVisitor.class));
        Object result33 = testSubject0.accept(Utils.mockAnnotationSubject());
    }

    @Test
    public void shouldTestInterfaceOWLAsymmetricObjectPropertyAxiom()
            throws OWLException {
        OWLAsymmetricObjectPropertyAxiom testSubject0 = mock(OWLAsymmetricObjectPropertyAxiom.class);
        OWLAsymmetricObjectPropertyAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result2 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result4 = testSubject0.getAxiomWithoutAnnotations();
        boolean result6 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result7 = testSubject0.isLogicalAxiom();
        boolean result8 = testSubject0.isAnnotationAxiom();
        boolean result9 = testSubject0.isAnnotated();
        AxiomType<?> result10 = testSubject0.getAxiomType();
        boolean result11 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result12 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
    }

    @Test
    public void shouldTestInterfaceOWLAxiom() throws OWLException {
        OWLAxiom testSubject0 = mock(OWLAxiom.class);
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result1 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result5 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        boolean result8 = testSubject0.isAnnotated();
        AxiomType<?> result9 = testSubject0.getAxiomType();
        boolean result10 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result11 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAxiomChange() throws OWLException {
        OWLAxiomChange testSubject0 = new OWLAxiomChange(
                Utils.getMockOntology(), mock(OWLAxiom.class)) {

            @Override
            public boolean isAddAxiom() {
                return false;
            }

            @Override
            public OWLOntologyChangeData<OWLAxiom> getChangeData() {
                return mock(AddAxiomData.class);
            }

            @Override
            public void accept(OWLOntologyChangeVisitor visitor) {}

            @Override
            public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
                return null;
            }
        };
        OWLAxiom result0 = testSubject0.getAxiom();
        boolean result1 = testSubject0.isAxiomChange();
        boolean result2 = testSubject0.isImportChange();
        Set<OWLEntity> result3 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockOntologyChange());
        OWLOntology result5 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestInterfaceOWLAxiomVisitor() throws OWLException {
        OWLAxiomVisitor testSubject0 = mock(OWLAxiomVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAxiomVisitorEx() throws OWLException {
        OWLAxiomVisitorEx<OWLObject> testSubject0 = Utils.mockAxiom();
        OWLObject result1 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        OWLObject result2 = testSubject0
                .visit(mock(OWLAsymmetricObjectPropertyAxiom.class));
        OWLObject result3 = testSubject0
                .visit(mock(OWLReflexiveObjectPropertyAxiom.class));
        OWLObject result7 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        OWLObject result8 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        OWLObject result10 = testSubject0
                .visit(mock(OWLDisjointDataPropertiesAxiom.class));
        OWLObject result11 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        OWLObject result13 = testSubject0
                .visit(mock(OWLObjectPropertyAssertionAxiom.class));
        OWLObject result14 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        OWLObject result19 = testSubject0
                .visit(mock(OWLSymmetricObjectPropertyAxiom.class));
        OWLObject result21 = testSubject0
                .visit(mock(OWLFunctionalDataPropertyAxiom.class));
        OWLObject result22 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        OWLObject result25 = testSubject0
                .visit(mock(OWLDataPropertyAssertionAxiom.class));
        OWLObject result26 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        OWLObject result27 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        OWLObject result29 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        OWLObject result32 = testSubject0
                .visit(mock(OWLInverseObjectPropertiesAxiom.class));
        OWLObject result36 = testSubject0
                .visit(mock(OWLSubAnnotationPropertyOfAxiom.class));
        OWLObject result37 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        OWLObject result38 = testSubject0
                .visit(mock(OWLAnnotationPropertyRangeAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLBooleanClassExpression()
            throws OWLException {
        OWLBooleanClassExpression testSubject0 = mock(OWLBooleanClassExpression.class);
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result2 = testSubject0.asOWLClass();
        }
        ClassExpressionType result4 = testSubject0.getClassExpressionType();
        boolean result5 = testSubject0.isClassExpressionLiteral();
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLCardinalityRestriction()
            throws OWLException {
        OWLCardinalityRestriction<OWLClassExpression> testSubject0 = mock(OWLCardinalityRestriction.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        Object result6 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result8 = testSubject0.asOWLClass();
        }
        ClassExpressionType result10 = testSubject0.getClassExpressionType();
        boolean result11 = testSubject0.isClassExpressionLiteral();
        boolean result12 = testSubject0.isOWLThing();
        boolean result13 = testSubject0.isOWLNothing();
        OWLClassExpression result15 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result16 = testSubject0.asConjunctSet();
        boolean result17 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result18 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLClass() throws OWLException {
        OWLClass testSubject0 = mock(OWLClass.class);
        Object result12 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result13 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result14 = testSubject0.asOWLClass();
        }
        ClassExpressionType result16 = testSubject0.getClassExpressionType();
        boolean result17 = testSubject0.isClassExpressionLiteral();
        boolean result18 = testSubject0.isOWLThing();
        boolean result19 = testSubject0.isOWLNothing();
        OWLClassExpression result21 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result22 = testSubject0.asConjunctSet();
        boolean result23 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result24 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result34 = testSubject0.isTopEntity();
        boolean result35 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result39 = testSubject0.accept(Utils.mockEntity());
        boolean result40 = testSubject0.isType(EntityType.CLASS);
        boolean result44 = testSubject0.isBuiltIn();
        EntityType<?> result45 = testSubject0.getEntityType();
        boolean result47 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result48 = testSubject0.asOWLClass();
        }
        boolean result49 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result70 = testSubject0.asOWLObjectProperty();
        }
        boolean result71 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result72 = testSubject0.asOWLDataProperty();
        }
        boolean result73 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result74 = testSubject0.asOWLNamedIndividual();
        }
        boolean result75 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result76 = testSubject0.asOWLDatatype();
        }
        boolean result77 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result78 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result59 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result60 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLClassAssertionAxiom() throws OWLException {
        OWLClassAssertionAxiom testSubject0 = mock(OWLClassAssertionAxiom.class);
        OWLClassAssertionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLIndividual result1 = testSubject0.getIndividual();
        OWLClassExpression result2 = testSubject0.getClassExpression();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result4 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result6 = testSubject0.getAxiomWithoutAnnotations();
        boolean result8 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result9 = testSubject0.isLogicalAxiom();
        boolean result10 = testSubject0.isAnnotationAxiom();
        boolean result11 = testSubject0.isAnnotated();
        AxiomType<?> result12 = testSubject0.getAxiomType();
        boolean result13 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result14 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result28 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLClassAxiom() throws OWLException {
        OWLClassAxiom testSubject0 = mock(OWLClassAxiom.class);
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result1 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result5 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        boolean result8 = testSubject0.isAnnotated();
        AxiomType<?> result9 = testSubject0.getAxiomType();
        boolean result10 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result11 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLClassExpression() throws OWLException {
        OWLClassExpression testSubject0 = Utils.mockAnonClass();
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result2 = testSubject0.asOWLClass();
        }
        ClassExpressionType result4 = testSubject0.getClassExpressionType();
        boolean result5 = testSubject0.isClassExpressionLiteral();
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLClassExpressionVisitor()
            throws OWLException {
        OWLClassExpressionVisitor testSubject0 = mock(OWLClassExpressionVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLClassExpressionVisitorEx()
            throws OWLException {
        OWLClassExpressionVisitorEx<OWLObject> testSubject0 = Utils
                .mockClassExpression();
    }

    @Test
    public void shouldTestInterfaceOWLDataAllValuesFrom() throws OWLException {
        OWLDataAllValuesFrom testSubject0 = mock(OWLDataAllValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLDataPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isObjectRestriction();
        boolean result3 = testSubject0.isDataRestriction();
        Object result4 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result5 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result6 = testSubject0.asOWLClass();
        }
        ClassExpressionType result8 = testSubject0.getClassExpressionType();
        boolean result9 = testSubject0.isClassExpressionLiteral();
        boolean result10 = testSubject0.isOWLThing();
        boolean result11 = testSubject0.isOWLNothing();
        OWLClassExpression result13 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result14 = testSubject0.asConjunctSet();
        boolean result15 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result16 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataCardinalityRestriction()
            throws OWLException {
        OWLDataCardinalityRestriction testSubject0 = mock(OWLDataCardinalityRestriction.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLDataPropertyExpression result3 = testSubject0.getProperty();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        Object result6 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result8 = testSubject0.asOWLClass();
        }
        ClassExpressionType result10 = testSubject0.getClassExpressionType();
        boolean result11 = testSubject0.isClassExpressionLiteral();
        boolean result12 = testSubject0.isOWLThing();
        boolean result13 = testSubject0.isOWLNothing();
        OWLClassExpression result15 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result16 = testSubject0.asConjunctSet();
        boolean result17 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result18 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataComplementOf() throws OWLException {
        OWLDataComplementOf testSubject0 = mock(OWLDataComplementOf.class);
        OWLDataRange result0 = testSubject0.getDataRange();
        Object result1 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isDatatype()) {
            OWLDatatype result3 = testSubject0.asOWLDatatype();
        }
        boolean result4 = testSubject0.isDatatype();
        boolean result5 = testSubject0.isTopDatatype();
        DataRangeType result6 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataExactCardinality()
            throws OWLException {
        OWLDataExactCardinality testSubject0 = mock(OWLDataExactCardinality.class);
        OWLClassExpression result0 = testSubject0.asIntersectionOfMinMax();
        int result1 = testSubject0.getCardinality();
        boolean result2 = testSubject0.isQualified();
        OWLPropertyRange result3 = testSubject0.getFiller();
        OWLDataPropertyExpression result4 = testSubject0.getProperty();
        boolean result5 = testSubject0.isObjectRestriction();
        boolean result6 = testSubject0.isDataRestriction();
        Object result7 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result8 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result9 = testSubject0.asOWLClass();
        }
        ClassExpressionType result11 = testSubject0.getClassExpressionType();
        boolean result12 = testSubject0.isClassExpressionLiteral();
        boolean result13 = testSubject0.isOWLThing();
        boolean result14 = testSubject0.isOWLNothing();
        OWLClassExpression result16 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result17 = testSubject0.asConjunctSet();
        boolean result18 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result19 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataHasValue() throws OWLException {
        OWLDataHasValue testSubject0 = mock(OWLDataHasValue.class);
        OWLObject result0 = testSubject0.getFiller();
        OWLClassExpression result1 = testSubject0.asSomeValuesFrom();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        Object result5 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result6 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result7 = testSubject0.asOWLClass();
        }
        ClassExpressionType result9 = testSubject0.getClassExpressionType();
        boolean result10 = testSubject0.isClassExpressionLiteral();
        boolean result11 = testSubject0.isOWLThing();
        boolean result12 = testSubject0.isOWLNothing();
        OWLClassExpression result14 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result15 = testSubject0.asConjunctSet();
        boolean result16 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result17 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataIntersectionOf() throws OWLException {
        OWLDataIntersectionOf testSubject0 = mock(OWLDataIntersectionOf.class);
        Set<OWLDataRange> result0 = testSubject0.getOperands();
        Object result1 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isDatatype()) {
            OWLDatatype result3 = testSubject0.asOWLDatatype();
        }
        boolean result4 = testSubject0.isDatatype();
        boolean result5 = testSubject0.isTopDatatype();
        DataRangeType result6 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataMaxCardinality() throws OWLException {
        OWLDataMaxCardinality testSubject0 = mock(OWLDataMaxCardinality.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLDataPropertyExpression result3 = testSubject0.getProperty();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        Object result6 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result8 = testSubject0.asOWLClass();
        }
        ClassExpressionType result10 = testSubject0.getClassExpressionType();
        boolean result11 = testSubject0.isClassExpressionLiteral();
        boolean result12 = testSubject0.isOWLThing();
        boolean result13 = testSubject0.isOWLNothing();
        OWLClassExpression result15 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result16 = testSubject0.asConjunctSet();
        boolean result17 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result18 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataMinCardinality() throws OWLException {
        OWLDataMinCardinality testSubject0 = mock(OWLDataMinCardinality.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLDataPropertyExpression result3 = testSubject0.getProperty();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        Object result6 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result8 = testSubject0.asOWLClass();
        }
        ClassExpressionType result10 = testSubject0.getClassExpressionType();
        boolean result11 = testSubject0.isClassExpressionLiteral();
        boolean result12 = testSubject0.isOWLThing();
        boolean result13 = testSubject0.isOWLNothing();
        OWLClassExpression result15 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result16 = testSubject0.asConjunctSet();
        boolean result17 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result18 = testSubject0.asDisjunctSet();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataOneOf() throws OWLException {
        OWLDataOneOf testSubject0 = mock(OWLDataOneOf.class);
        Set<OWLLiteral> result0 = testSubject0.getValues();
        Object result1 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isDatatype()) {
            OWLDatatype result3 = testSubject0.asOWLDatatype();
        }
        boolean result4 = testSubject0.isDatatype();
        boolean result5 = testSubject0.isTopDatatype();
        DataRangeType result6 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result51 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result52 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result53 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result54 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result55 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }
}
