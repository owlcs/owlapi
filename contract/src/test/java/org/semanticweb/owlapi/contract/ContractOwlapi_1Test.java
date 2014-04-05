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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationSubjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory.OWLOntologyCreationHandler;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import uk.ac.manchester.cs.owl.owlapi.AbstractInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.CollectionContainer;
import uk.ac.manchester.cs.owl.owlapi.CollectionContainerVisitor;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory;
import uk.ac.manchester.cs.owl.owlapi.InternalsNoCache;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnnotationPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnonymousClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAnonymousIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAsymmetricObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLCardinalityRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataCardinalityRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapi_1Test {

    @Test
    public void shouldTestAbstractInMemOWLOntologyFactory() throws OWLException {
        AbstractInMemOWLOntologyFactory testSubject0 = new AbstractInMemOWLOntologyFactory(
                mock(OWLOntologyBuilder.class)) {

            private static final long serialVersionUID = 40000L;

            @Override
            public OWLOntology loadOWLOntology(OWLOntologyManager m,
                    OWLOntologyDocumentSource documentSource,
                    OWLOntologyCreationHandler handler,
                    OWLOntologyLoaderConfiguration configuration)
                    throws OWLOntologyCreationException {
                return mock(OWLOntology.class);
            }

            @Override
            public boolean canLoad(OWLOntologyDocumentSource documentSource) {
                return false;
            }
        };
        boolean result2 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
        OWLOntology result5 = testSubject0.loadOWLOntology(
                mock(OWLOntologyManager.class),
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class),
                new OWLOntologyLoaderConfiguration());
        boolean result6 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
    }

    @Test
    public void shouldTestInterfaceCollectionContainer() throws OWLException {
        CollectionContainer<Object> testSubject0 = mock(CollectionContainer.class);
        testSubject0.accept(Utils.mockCollContainer());
    }

    @Test
    public void shouldTestInterfaceCollectionContainerVisitor()
            throws OWLException {
        CollectionContainerVisitor<Object> testSubject0 = Utils
                .mockCollContainer();
    }

    @Test
    public void shouldTestEmptyInMemOWLOntologyFactory() throws OWLException {
        EmptyInMemOWLOntologyFactory testSubject0 = new EmptyInMemOWLOntologyFactory(
                mock(OWLOntologyBuilder.class));
        boolean result3 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
        boolean result5 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInitVisitorFactory() throws OWLException {
        new InitVisitorFactory();
    }

    @Test
    public void shouldTestInternalsNoCache() throws OWLException {
        InternalsNoCache testSubject0 = new InternalsNoCache(false);
        OWLClass result0 = testSubject0.getOWLClass(IRI("urn:aFake"));
        OWLDatatype result1 = testSubject0.getTopDatatype();
        OWLObjectProperty result2 = testSubject0
                .getOWLObjectProperty(IRI("urn:aFake"));
        OWLDataProperty result3 = testSubject0
                .getOWLDataProperty(IRI("urn:aFake"));
        OWLNamedIndividual result4 = testSubject0
                .getOWLNamedIndividual(IRI("urn:aFake"));
        OWLAnnotationProperty result5 = testSubject0
                .getOWLAnnotationProperty(IRI("urn:aFake"));
        OWLDatatype result6 = testSubject0.getRDFPlainLiteral();
        OWLDatatype result7 = testSubject0.getOWLDatatype(IRI("urn:aFake"));
        OWLDatatype result8 = testSubject0.getIntegerOWLDatatype();
        OWLDatatype result9 = testSubject0.getFloatOWLDatatype();
        OWLDatatype result10 = testSubject0.getDoubleOWLDatatype();
        OWLDatatype result11 = testSubject0.getBooleanOWLDatatype();
        OWLLiteral result12 = testSubject0.getOWLLiteral(0D);
        OWLLiteral result13 = testSubject0.getOWLLiteral("true",
                testSubject0.getBooleanOWLDatatype());
        OWLLiteral result14 = testSubject0.getOWLLiteral(0F);
        OWLLiteral result15 = testSubject0.getOWLLiteral("");
        OWLLiteral result16 = testSubject0.getOWLLiteral("", "");
        OWLLiteral result17 = testSubject0.getOWLLiteral(0);
        OWLLiteral result18 = testSubject0.getOWLLiteral(false);
        testSubject0.purge();
    }

    @Test
    public void shouldTestOWLAnnotationAssertionAxiomImpl() throws OWLException {
        OWLAnnotationAssertionAxiomImpl testSubject0 = new OWLAnnotationAssertionAxiomImpl(
                mock(OWLAnnotationSubject.class),
                mock(OWLAnnotationProperty.class),
                mock(OWLAnnotationValue.class),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        OWLAnnotationValue result1 = testSubject0.getValue();
        OWLAnnotation result2 = testSubject0.getAnnotation();
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockAxiom());
        Object result4 = testSubject0.accept(Utils.mockObject());
        OWLAnnotationSubject result5 = testSubject0.getSubject();
        OWLAxiom result6 = testSubject0.getAxiomWithoutAnnotations();
        OWLAnnotationAssertionAxiom result7 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result12 = testSubject0.getAxiomType();
        boolean result13 = testSubject0.isDeprecatedIRIAssertion();
        Set<OWLAnnotation> result14 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result15 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result16 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result17 = testSubject0.isAnnotated();
        boolean result18 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result19 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
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
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAnnotationImpl() throws OWLException {
        OWLAnnotationImpl testSubject0 = new OWLAnnotationImpl(
                mock(OWLAnnotationProperty.class),
                mock(OWLAnnotationValue.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        OWLAnnotationValue result1 = testSubject0.getValue();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Object result3 = testSubject0.accept(Utils.mockAnnotationObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAnnotationObjectVisitor.class));
        boolean result5 = testSubject0.isDeprecatedIRIAnnotation();
        OWLAnnotation result6 = testSubject0.getAnnotatedAnnotation(Utils
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
        boolean result20 = testSubject0.isTopEntity();
        boolean result21 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyDomainAxiomImpl()
            throws OWLException {
        OWLAnnotationPropertyDomainAxiomImpl testSubject0 = new OWLAnnotationPropertyDomainAxiomImpl(
                mock(OWLAnnotationProperty.class), IRI("urn:aFake"),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        IRI result3 = testSubject0.getDomain();
        OWLAxiom result4 = testSubject0.getAxiomWithoutAnnotations();
        OWLAnnotationPropertyDomainAxiom result5 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result10 = testSubject0.getAxiomType();
        Set<OWLAnnotation> result11 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result12 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result13 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result14 = testSubject0.isAnnotated();
        boolean result15 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result16 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
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
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyImpl() throws OWLException {
        OWLAnnotationPropertyImpl testSubject0 = new OWLAnnotationPropertyImpl(
                IRI("urn:aFake"));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockObject());
        Object result3 = testSubject0.accept(Utils.mockEntity());
        boolean result4 = testSubject0.isType(EntityType.CLASS);
        IRI result8 = testSubject0.getIRI();
        boolean result9 = testSubject0.isBuiltIn();
        EntityType<?> result10 = testSubject0.getEntityType();
        if (testSubject0.isOWLClass()) {
            OWLClass result13 = testSubject0.asOWLClass();
        }
        boolean result14 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result15 = testSubject0.asOWLObjectProperty();
        }
        boolean result16 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result17 = testSubject0.asOWLDataProperty();
        }
        boolean result18 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result19 = testSubject0.asOWLNamedIndividual();
        }
        boolean result20 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result21 = testSubject0.asOWLDatatype();
        }
        boolean result22 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result23 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result24 = testSubject0.toStringID();
        boolean result25 = testSubject0.isComment();
        boolean result26 = testSubject0.isLabel();
        boolean result27 = testSubject0.isDeprecated();
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
        boolean result45 = testSubject0.isTopEntity();
        boolean result46 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAnnotationPropertyRangeAxiomImpl()
            throws OWLException {
        OWLAnnotationPropertyRangeAxiomImpl testSubject0 = new OWLAnnotationPropertyRangeAxiomImpl(
                mock(OWLAnnotationProperty.class), IRI("urn:aFake"),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        OWLAnnotationProperty result0 = testSubject0.getProperty();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        OWLAnnotationPropertyRangeAxiom result4 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result7 = testSubject0.isLogicalAxiom();
        boolean result8 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result9 = testSubject0.getAxiomType();
        IRI result10 = testSubject0.getRange();
        Set<OWLAnnotation> result11 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result12 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result13 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result14 = testSubject0.isAnnotated();
        boolean result15 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result16 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
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
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAnonymousClassExpressionImpl() throws OWLException {
        OWLAnonymousClassExpressionImpl testSubject0 = new OWLAnonymousClassExpressionImpl() {

            private static final long serialVersionUID = 40000L;

            @Override
            public ClassExpressionType getClassExpressionType() {
                return ClassExpressionType.DATA_ALL_VALUES_FROM;
            }

            @Override
            public boolean isClassExpressionLiteral() {
                return false;
            }

            @Override
            public void accept(OWLClassExpressionVisitor visitor) {}

            @Override
            public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }

            @Override
            protected int index() {
                return 0;
            }
        };
        boolean result0 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result1 = testSubject0.asOWLClass();
        }
        boolean result3 = testSubject0.isOWLThing();
        boolean result4 = testSubject0.isOWLNothing();
        OWLClassExpression result6 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result7 = testSubject0.asConjunctSet();
        boolean result8 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result9 = testSubject0.asDisjunctSet();
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
        boolean result21 = testSubject0.isTopEntity();
        boolean result22 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result24 = testSubject0.accept(Utils.mockObject());
        Object result25 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result26 = testSubject0.getClassExpressionType();
        boolean result27 = testSubject0.isClassExpressionLiteral();
    }

    @Test
    public void shouldTestOWLAnonymousIndividualImpl() throws OWLException {
        OWLAnonymousIndividualImpl testSubject0 = new OWLAnonymousIndividualImpl(
                mock(NodeID.class));
        Object result0 = testSubject0.accept(Utils.mockAnnotationValue());
        Object result1 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        testSubject0.accept(mock(OWLAnnotationSubjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockAnnotationSubject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result4 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLNamedIndividual result5 = testSubject0.asOWLNamedIndividual();
        }
        String result6 = testSubject0.toStringID();
        NodeID result7 = testSubject0.getID();
        boolean result8 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result9 = testSubject0
                    .asOWLAnonymousIndividual();
        }
        boolean result11 = !testSubject0.isAnonymous();
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
        boolean result43 = testSubject0.isTopEntity();
        boolean result44 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAsymmetricObjectPropertyAxiomImpl()
            throws OWLException {
        OWLAsymmetricObjectPropertyAxiomImpl testSubject0 = new OWLAsymmetricObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLAsymmetricObjectPropertyAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLObjectPropertyExpression result7 = testSubject0.getProperty();
        OWLObjectPropertyExpression result8 = testSubject0.getProperty();
        boolean result9 = testSubject0.isLogicalAxiom();
        boolean result10 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result11 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result12 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result13 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result14 = testSubject0.isAnnotated();
        boolean result15 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result16 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
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
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLAxiomImpl() throws OWLException {
        OWLAxiomImpl testSubject0 = new OWLAxiomImpl(
                Utils.mockCollection(mock(OWLAnnotation.class))) {

            private static final long serialVersionUID = 40000L;

            @Override
            public void accept(OWLAxiomVisitor visitor) {}

            @Override
            public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public OWLAxiom getAxiomWithoutAnnotations() {
                return this;
            }

            @Override
            public OWLAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
                return this;
            }

            @Override
            public boolean isLogicalAxiom() {
                return false;
            }

            @Override
            public boolean isAnnotationAxiom() {
                return false;
            }

            @Override
            public AxiomType<?> getAxiomType() {
                return AxiomType.ANNOTATION_ASSERTION;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result1 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result2 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result3 = testSubject0.isAnnotated();
        boolean result4 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result5 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        OWLAxiom result6 = testSubject0.getNNF();
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
        boolean result18 = testSubject0.isTopEntity();
        boolean result19 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result21 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result22 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result23 = testSubject0.getAxiomWithoutAnnotations();
        boolean result25 = testSubject0.isLogicalAxiom();
        boolean result26 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result27 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLCardinalityRestrictionImpl() throws OWLException {
        OWLCardinalityRestrictionImpl<OWLClassExpression> testSubject0 = mock(OWLCardinalityRestrictionImpl.class);
        int result0 = testSubject0.getCardinality();
        OWLPropertyRange result1 = testSubject0.getFiller();
        boolean result3 = testSubject0.isClassExpressionLiteral();
        boolean result4 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result5 = testSubject0.asOWLClass();
        }
        boolean result7 = testSubject0.isOWLThing();
        boolean result8 = testSubject0.isOWLNothing();
        OWLClassExpression result10 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result11 = testSubject0.asConjunctSet();
        boolean result12 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result13 = testSubject0.asDisjunctSet();
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
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result28 = testSubject0.accept(Utils.mockObject());
        Object result29 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result30 = testSubject0.getClassExpressionType();
        boolean result31 = testSubject0.isObjectRestriction();
        boolean result32 = testSubject0.isDataRestriction();
        boolean result33 = testSubject0.isQualified();
    }

    @Test
    public void shouldTestOWLClassAssertionImpl() throws OWLException {
        OWLClassAssertionAxiomImpl testSubject0 = new OWLClassAssertionAxiomImpl(
                mock(OWLIndividual.class), Utils.mockAnonClass(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLClassAssertionAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLIndividual result8 = testSubject0.getIndividual();
        OWLClassExpression result9 = testSubject0.getClassExpression();
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result12 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result13 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result14 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result15 = testSubject0.isAnnotated();
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
        boolean result30 = testSubject0.isTopEntity();
        boolean result31 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLClassAxiomImpl() throws OWLException {
        OWLClassAxiomImpl testSubject0 = new OWLClassAxiomImpl(
                new ArrayList<OWLAnnotation>()) {

            private static final long serialVersionUID = 40000L;

            @Override
            public void accept(OWLAxiomVisitor visitor) {}

            @Override
            public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public OWLAxiom getAxiomWithoutAnnotations() {
                return this;
            }

            @Override
            public OWLAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
                return this;
            }

            @Override
            public AxiomType<?> getAxiomType() {
                return AxiomType.ANNOTATION_ASSERTION;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        boolean result0 = testSubject0.isLogicalAxiom();
        boolean result1 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result3 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result4 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result5 = testSubject0.isAnnotated();
        boolean result6 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result7 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
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
        boolean result20 = testSubject0.isTopEntity();
        boolean result21 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result23 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result24 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result25 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result27 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLClassExpressionImpl() throws OWLException {
        OWLClassExpressionImpl testSubject0 = new OWLClassExpressionImpl() {

            private static final long serialVersionUID = 40000L;

            @Override
            public ClassExpressionType getClassExpressionType() {
                return ClassExpressionType.DATA_ALL_VALUES_FROM;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public boolean isClassExpressionLiteral() {
                return false;
            }

            @Override
            public OWLClass asOWLClass() {
                return mock(OWLClass.class);
            }

            @Override
            public boolean isOWLThing() {
                return false;
            }

            @Override
            public boolean isOWLNothing() {
                return false;
            }

            @Override
            public OWLClassExpression getNNF() {
                return mock(OWLClassExpression.class);
            }

            @Override
            public OWLClassExpression getComplementNNF() {
                return mock(OWLClassExpression.class);
            }

            @Override
            public OWLClassExpression getObjectComplementOf() {
                return mock(OWLClassExpression.class);
            }

            @Override
            public Set<OWLClassExpression> asConjunctSet() {
                return Collections.emptySet();
            }

            @Override
            public boolean containsConjunct(OWLClassExpression ce) {
                return false;
            }

            @Override
            public Set<OWLClassExpression> asDisjunctSet() {
                return Collections.emptySet();
            }

            @Override
            public void accept(OWLClassExpressionVisitor visitor) {}

            @Override
            public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }

            @Override
            protected int index() {
                return 0;
            }
        };
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
        boolean result11 = testSubject0.isTopEntity();
        boolean result12 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result14 = testSubject0.accept(Utils.mockObject());
        Object result15 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result16 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result17 = testSubject0.asOWLClass();
        }
        OWLClassExpression result18 = testSubject0.getNNF();
        ClassExpressionType result19 = testSubject0.getClassExpressionType();
        boolean result20 = testSubject0.isClassExpressionLiteral();
        boolean result21 = testSubject0.isOWLThing();
        boolean result22 = testSubject0.isOWLNothing();
        OWLClassExpression result24 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result25 = testSubject0.asConjunctSet();
        boolean result26 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result27 = testSubject0.asDisjunctSet();
    }

    @Test
    public void shouldTestOWLClassImpl() throws OWLException {
        OWLClassImpl testSubject0 = new OWLClassImpl(IRI("urn:aFake"));
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockClassExpression());
        Object result5 = testSubject0.accept(Utils.mockEntity());
        Object result6 = testSubject0.accept(Utils.mockObject());
        boolean result7 = testSubject0.isType(EntityType.CLASS);
        boolean result8 = testSubject0.isTopEntity();
        boolean result9 = testSubject0.isBottomEntity();
        boolean result10 = testSubject0.isAnonymous();
        Set<OWLEquivalentClassesAxiom> result14 = Utils.getMockOntology()
                .getEquivalentClassesAxioms(testSubject0);
        Set<OWLDisjointClassesAxiom> result15 = Utils.getMockOntology()
                .getDisjointClassesAxioms(testSubject0);
        Set<OWLDisjointUnionAxiom> result16 = Utils.getMockOntology()
                .getDisjointUnionAxioms(testSubject0);
        IRI result17 = testSubject0.getIRI();
        boolean result18 = testSubject0.isBuiltIn();
        EntityType<?> result19 = testSubject0.getEntityType();
        boolean result21 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result22 = testSubject0.asOWLClass();
        }
        boolean result23 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result24 = testSubject0.asOWLObjectProperty();
        }
        boolean result25 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result26 = testSubject0.asOWLDataProperty();
        }
        boolean result27 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result28 = testSubject0.asOWLNamedIndividual();
        }
        boolean result29 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result30 = testSubject0.asOWLDatatype();
        }
        boolean result31 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result32 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result33 = testSubject0.toStringID();
        ClassExpressionType result45 = testSubject0.getClassExpressionType();
        boolean result46 = testSubject0.isClassExpressionLiteral();
        boolean result47 = testSubject0.isOWLThing();
        boolean result48 = testSubject0.isOWLNothing();
        OWLClassExpression result50 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result51 = testSubject0.asConjunctSet();
        boolean result52 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result53 = testSubject0.asDisjunctSet();
        Set<OWLSubClassOfAxiom> result54 = Utils.getMockOntology()
                .getSubClassAxiomsForSubClass(testSubject0);
        Set<OWLEntity> result60 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result61 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result62 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result63 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result64 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result65 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result56 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLDataAllValuesFromImpl() throws OWLException {
        OWLDataAllValuesFromImpl testSubject0 = new OWLDataAllValuesFromImpl(
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLPropertyRange result5 = testSubject0.getFiller();
        OWLDataPropertyExpression result6 = testSubject0.getProperty();
        boolean result7 = testSubject0.isClassExpressionLiteral();
        boolean result8 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result9 = testSubject0.asOWLClass();
        }
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
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDataCardinalityRestrictionImpl()
            throws OWLException {
        OWLDataCardinalityRestrictionImpl testSubject0 = new OWLDataCardinalityRestrictionImpl(
                mock(OWLDataPropertyExpression.class), 0,
                mock(OWLDataRange.class)) {

            private static final long serialVersionUID = 40000L;

            @Override
            public ClassExpressionType getClassExpressionType() {
                return ClassExpressionType.DATA_ALL_VALUES_FROM;
            }

            @Override
            public void accept(OWLClassExpressionVisitor visitor) {}

            @Override
            public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected int index() {
                return 0;
            }
        };
        boolean result0 = testSubject0.isQualified();
        boolean result1 = testSubject0.isObjectRestriction();
        boolean result2 = testSubject0.isDataRestriction();
        int result3 = testSubject0.getCardinality();
        OWLPropertyRange result4 = testSubject0.getFiller();
        OWLDataPropertyExpression result5 = testSubject0.getProperty();
        boolean result6 = testSubject0.isClassExpressionLiteral();
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result8 = testSubject0.asOWLClass();
        }
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
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result31 = testSubject0.accept(Utils.mockObject());
        Object result32 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result33 = testSubject0.getClassExpressionType();
    }

    @Test
    public void shouldTestOWLDataComplementOfImpl() throws OWLException {
        OWLDataComplementOfImpl testSubject0 = new OWLDataComplementOfImpl(
                mock(OWLDataRange.class));
        testSubject0.accept(mock(OWLDataVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockData());
        Object result1 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockDataRange());
        if (testSubject0.isDatatype()) {
            OWLDatatype result3 = testSubject0.asOWLDatatype();
        }
        boolean result4 = testSubject0.isDatatype();
        boolean result5 = testSubject0.isTopDatatype();
        DataRangeType result6 = testSubject0.getDataRangeType();
        OWLDataRange result7 = testSubject0.getDataRange();
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
        boolean result19 = testSubject0.isTopEntity();
        boolean result20 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDataExactCardinalityImpl() throws OWLException {
        OWLDataExactCardinalityImpl testSubject0 = new OWLDataExactCardinalityImpl(
                mock(OWLDataPropertyExpression.class), 0,
                mock(OWLDataRange.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        OWLClassExpression result3 = testSubject0.asIntersectionOfMinMax();
        boolean result4 = testSubject0.isQualified();
        boolean result5 = testSubject0.isObjectRestriction();
        boolean result6 = testSubject0.isDataRestriction();
        int result7 = testSubject0.getCardinality();
        OWLPropertyRange result8 = testSubject0.getFiller();
        OWLDataPropertyExpression result9 = testSubject0.getProperty();
        boolean result10 = testSubject0.isClassExpressionLiteral();
        boolean result11 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result12 = testSubject0.asOWLClass();
        }
        boolean result14 = testSubject0.isOWLThing();
        boolean result15 = testSubject0.isOWLNothing();
        OWLClassExpression result17 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result18 = testSubject0.asConjunctSet();
        boolean result19 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result20 = testSubject0.asDisjunctSet();
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
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataFactoryInternals()
            throws OWLException {
        OWLDataFactoryInternals testSubject0 = mock(OWLDataFactoryInternals.class);
        OWLClass result0 = testSubject0.getOWLClass(IRI("urn:aFake"));
        OWLDatatype result1 = testSubject0.getTopDatatype();
        OWLObjectProperty result2 = testSubject0
                .getOWLObjectProperty(IRI("urn:aFake"));
        OWLDataProperty result3 = testSubject0
                .getOWLDataProperty(IRI("urn:aFake"));
        OWLNamedIndividual result4 = testSubject0
                .getOWLNamedIndividual(IRI("urn:aFake"));
        OWLAnnotationProperty result5 = testSubject0
                .getOWLAnnotationProperty(IRI("urn:aFake"));
        OWLDatatype result6 = testSubject0.getRDFPlainLiteral();
        OWLDatatype result7 = testSubject0.getOWLDatatype(IRI("urn:aFake"));
        OWLDatatype result8 = testSubject0.getIntegerOWLDatatype();
        OWLDatatype result9 = testSubject0.getFloatOWLDatatype();
        OWLDatatype result10 = testSubject0.getDoubleOWLDatatype();
        OWLDatatype result11 = testSubject0.getBooleanOWLDatatype();
        OWLLiteral result12 = testSubject0.getOWLLiteral("");
        OWLLiteral result13 = testSubject0.getOWLLiteral("", "");
        OWLLiteral result14 = testSubject0.getOWLLiteral("",
                mock(OWLDatatype.class));
        OWLLiteral result15 = testSubject0.getOWLLiteral(0);
        OWLLiteral result16 = testSubject0.getOWLLiteral(false);
        OWLLiteral result17 = testSubject0.getOWLLiteral(0D);
        OWLLiteral result18 = testSubject0.getOWLLiteral(0F);
        testSubject0.purge();
    }

    @Test
    public void shouldTestOWLDataFactoryInternalsImpl() throws OWLException {
        OWLDataFactoryInternalsImpl testSubject0 = new OWLDataFactoryInternalsImpl(
                false);
        OWLClass result0 = testSubject0.getOWLClass(IRI("urn:aFake"));
        OWLObjectProperty result1 = testSubject0
                .getOWLObjectProperty(IRI("urn:aFake"));
        OWLDataProperty result2 = testSubject0
                .getOWLDataProperty(IRI("urn:aFake"));
        OWLNamedIndividual result3 = testSubject0
                .getOWLNamedIndividual(IRI("urn:aFake"));
        OWLAnnotationProperty result4 = testSubject0
                .getOWLAnnotationProperty(IRI("urn:aFake"));
        OWLDatatype result5 = testSubject0.getOWLDatatype(IRI("urn:aFake"));
        OWLLiteral result6 = testSubject0.getOWLLiteral(0D);
        OWLLiteral result7 = testSubject0.getOWLLiteral(0);
        OWLLiteral result8 = testSubject0.getOWLLiteral("");
        OWLLiteral result9 = testSubject0.getOWLLiteral(0F);
        OWLLiteral result10 = testSubject0.getOWLLiteral("true",
                testSubject0.getBooleanOWLDatatype());
        testSubject0.purge();
        OWLDatatype result11 = testSubject0.getTopDatatype();
        OWLDatatype result12 = testSubject0.getRDFPlainLiteral();
        OWLDatatype result13 = testSubject0.getIntegerOWLDatatype();
        OWLDatatype result14 = testSubject0.getFloatOWLDatatype();
        OWLDatatype result15 = testSubject0.getDoubleOWLDatatype();
        OWLDatatype result16 = testSubject0.getBooleanOWLDatatype();
        OWLLiteral result17 = testSubject0.getOWLLiteral("", "");
        OWLLiteral result18 = testSubject0.getOWLLiteral(false);
    }
}
