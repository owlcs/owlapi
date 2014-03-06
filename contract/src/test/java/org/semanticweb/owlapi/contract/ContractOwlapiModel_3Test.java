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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.OWLOntologyFactory.OWLOntologyCreationHandler;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiModel_3Test {

    @Test
    public void shouldTestInterfaceOWLNegativeObjectPropertyAssertionAxiom()
            throws OWLException {
        OWLNegativeObjectPropertyAssertionAxiom testSubject0 = mock(OWLNegativeObjectPropertyAssertionAxiom.class);
        OWLNegativeObjectPropertyAssertionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result1 = testSubject0.containsAnonymousIndividuals();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
        OWLPropertyAssertionObject result3 = testSubject0.getObject();
        OWLIndividual result4 = testSubject0.getSubject();
        Set<OWLAnnotation> result5 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result6 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result7 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result8 = testSubject0.getAxiomWithoutAnnotations();
        boolean result10 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result11 = testSubject0.isLogicalAxiom();
        boolean result12 = testSubject0.isAnnotationAxiom();
        boolean result13 = testSubject0.isAnnotated();
        AxiomType<?> result14 = testSubject0.getAxiomType();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result30 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLObject() throws OWLException {
        OWLObject testSubject0 = mock(OWLObject.class);
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
    public void shouldTestInterfaceOWLObjectAllValuesFrom() throws OWLException {
        OWLObjectAllValuesFrom testSubject0 = mock(OWLObjectAllValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectCardinalityRestriction()
            throws OWLException {
        OWLObjectCardinalityRestriction testSubject0 = mock(OWLObjectCardinalityRestriction.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLObjectPropertyExpression result3 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectComplementOf() throws OWLException {
        OWLObjectComplementOf testSubject0 = mock(OWLObjectComplementOf.class);
        OWLClassExpression result0 = testSubject0.getOperand();
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result2 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result3 = testSubject0.asOWLClass();
        }
        ClassExpressionType result5 = testSubject0.getClassExpressionType();
        boolean result6 = testSubject0.isClassExpressionLiteral();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result58 = testSubject0.accept(Utils.mockObject());
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectExactCardinality()
            throws OWLException {
        OWLObjectExactCardinality testSubject0 = mock(OWLObjectExactCardinality.class);
        OWLClassExpression result0 = testSubject0.asIntersectionOfMinMax();
        int result1 = testSubject0.getCardinality();
        boolean result2 = testSubject0.isQualified();
        OWLPropertyRange result3 = testSubject0.getFiller();
        OWLObjectPropertyExpression result4 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectHasSelf() throws OWLException {
        OWLObjectHasSelf testSubject0 = mock(OWLObjectHasSelf.class);
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        boolean result1 = testSubject0.isObjectRestriction();
        boolean result2 = testSubject0.isDataRestriction();
        Object result3 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result4 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result5 = testSubject0.asOWLClass();
        }
        ClassExpressionType result7 = testSubject0.getClassExpressionType();
        boolean result8 = testSubject0.isClassExpressionLiteral();
        boolean result9 = testSubject0.isOWLThing();
        boolean result10 = testSubject0.isOWLNothing();
        OWLClassExpression result12 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result13 = testSubject0.asConjunctSet();
        boolean result14 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result15 = testSubject0.asDisjunctSet();
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
    public void shouldTestInterfaceOWLObjectHasValue() throws OWLException {
        OWLObjectHasValue testSubject0 = mock(OWLObjectHasValue.class);
        OWLObject result0 = testSubject0.getFiller();
        OWLClassExpression result1 = testSubject0.asSomeValuesFrom();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectIntersectionOf()
            throws OWLException {
        OWLObjectIntersectionOf testSubject0 = mock(OWLObjectIntersectionOf.class);
        Set<OWLClassExpression> result0 = testSubject0.getOperands();
        List<OWLClassExpression> result1 = testSubject0.getOperandsAsList();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result4 = testSubject0.asOWLClass();
        }
        ClassExpressionType result6 = testSubject0.getClassExpressionType();
        boolean result7 = testSubject0.isClassExpressionLiteral();
        boolean result8 = testSubject0.isOWLThing();
        boolean result9 = testSubject0.isOWLNothing();
        OWLClassExpression result11 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result12 = testSubject0.asConjunctSet();
        boolean result13 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result14 = testSubject0.asDisjunctSet();
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
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectInverseOf() throws OWLException {
        OWLObjectInverseOf testSubject0 = mock(OWLObjectInverseOf.class);
        OWLObjectPropertyExpression result0 = testSubject0.getInverse();
        if (!testSubject0.isAnonymous()) {
            OWLObjectProperty result1 = testSubject0.asOWLObjectProperty();
        }
        OWLObjectPropertyExpression result16 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result17 = testSubject0.getSimplified();
        OWLObjectProperty result18 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result19 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result20 = testSubject0.isAnonymous();
        boolean result35 = testSubject0.isDataPropertyExpression();
        boolean result36 = testSubject0.isObjectPropertyExpression();
        boolean result37 = testSubject0.isOWLTopObjectProperty();
        boolean result38 = testSubject0.isOWLBottomObjectProperty();
        boolean result39 = testSubject0.isOWLTopDataProperty();
        boolean result40 = testSubject0.isOWLBottomDataProperty();
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
        boolean result59 = testSubject0.isTopEntity();
        boolean result60 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectMaxCardinality()
            throws OWLException {
        OWLObjectMaxCardinality testSubject0 = mock(OWLObjectMaxCardinality.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLObjectPropertyExpression result3 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectMinCardinality()
            throws OWLException {
        OWLObjectMinCardinality testSubject0 = mock(OWLObjectMinCardinality.class);
        int result0 = testSubject0.getCardinality();
        boolean result1 = testSubject0.isQualified();
        OWLPropertyRange result2 = testSubject0.getFiller();
        OWLObjectPropertyExpression result3 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectOneOf() throws OWLException {
        OWLObjectOneOf testSubject0 = mock(OWLObjectOneOf.class);
        Set<OWLIndividual> result0 = testSubject0.getIndividuals();
        OWLClassExpression result1 = testSubject0.asObjectUnionOf();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result4 = testSubject0.asOWLClass();
        }
        ClassExpressionType result6 = testSubject0.getClassExpressionType();
        boolean result7 = testSubject0.isClassExpressionLiteral();
        boolean result8 = testSubject0.isOWLThing();
        boolean result9 = testSubject0.isOWLNothing();
        OWLClassExpression result11 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result12 = testSubject0.asConjunctSet();
        boolean result13 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result14 = testSubject0.asDisjunctSet();
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
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectProperty() throws OWLException {
        OWLObjectProperty testSubject0 = mock(OWLObjectProperty.class);
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result0 = testSubject0.asOWLObjectProperty();
        }
        OWLObjectPropertyExpression result15 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result16 = testSubject0.getSimplified();
        OWLObjectProperty result17 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result19 = testSubject0.isAnonymous();
        boolean result34 = testSubject0.isDataPropertyExpression();
        boolean result35 = testSubject0.isObjectPropertyExpression();
        boolean result36 = testSubject0.isOWLTopObjectProperty();
        boolean result37 = testSubject0.isOWLBottomObjectProperty();
        boolean result38 = testSubject0.isOWLTopDataProperty();
        boolean result39 = testSubject0.isOWLBottomDataProperty();
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
        boolean result49 = testSubject0.isTopEntity();
        boolean result80 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result84 = testSubject0.accept(Utils.mockEntity());
        boolean result85 = testSubject0.isType(EntityType.CLASS);
        boolean result59 = testSubject0.isBuiltIn();
        EntityType<?> result60 = testSubject0.getEntityType();
        boolean result62 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result63 = testSubject0.asOWLClass();
        }
        boolean result64 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result65 = testSubject0.asOWLObjectProperty();
        }
        boolean result66 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result67 = testSubject0.asOWLDataProperty();
        }
        boolean result68 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result69 = testSubject0.asOWLNamedIndividual();
        }
        boolean result70 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result71 = testSubject0.asOWLDatatype();
        }
        boolean result72 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result73 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result74 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result75 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLObjectPropertyAssertionAxiom()
            throws OWLException {
        OWLObjectPropertyAssertionAxiom testSubject0 = mock(OWLObjectPropertyAssertionAxiom.class);
        OWLObjectPropertyAssertionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLObjectPropertyAssertionAxiom result1 = testSubject0.getSimplified();
        boolean result2 = testSubject0.isInSimplifiedForm();
        OWLObjectPropertyExpression result3 = testSubject0.getProperty();
        OWLPropertyAssertionObject result4 = testSubject0.getObject();
        OWLIndividual result5 = testSubject0.getSubject();
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
        OWLSubClassOfAxiom result31 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLObjectPropertyAxiom() throws OWLException {
        OWLObjectPropertyAxiom testSubject0 = mock(OWLObjectPropertyAxiom.class);
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
    public void shouldTestInterfaceOWLObjectPropertyCharacteristicAxiom()
            throws OWLException {
        OWLObjectPropertyCharacteristicAxiom testSubject0 = mock(OWLObjectPropertyCharacteristicAxiom.class);
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
        OWLObjectPropertyExpression result25 = testSubject0.getProperty();
    }

    @Test
    public void shouldTestInterfaceOWLObjectPropertyDomainAxiom()
            throws OWLException {
        OWLObjectPropertyDomainAxiom testSubject0 = mock(OWLObjectPropertyDomainAxiom.class);
        OWLObjectPropertyDomainAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLClassExpression result1 = testSubject0.getDomain();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectPropertyExpression()
            throws OWLException {
        OWLObjectPropertyExpression testSubject0 = Utils.mockObjectProperty();
        if (!testSubject0.isAnonymous()) {
            OWLObjectProperty result0 = testSubject0.asOWLObjectProperty();
        }
        OWLObjectPropertyExpression result15 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result16 = testSubject0.getSimplified();
        OWLObjectProperty result17 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result19 = testSubject0.isAnonymous();
        boolean result34 = testSubject0.isDataPropertyExpression();
        boolean result35 = testSubject0.isObjectPropertyExpression();
        boolean result36 = testSubject0.isOWLTopObjectProperty();
        boolean result37 = testSubject0.isOWLBottomObjectProperty();
        boolean result38 = testSubject0.isOWLTopDataProperty();
        boolean result39 = testSubject0.isOWLBottomDataProperty();
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
        boolean result49 = testSubject0.isTopEntity();
        boolean result60 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectPropertyRangeAxiom()
            throws OWLException {
        OWLObjectPropertyRangeAxiom testSubject0 = mock(OWLObjectPropertyRangeAxiom.class);
        OWLObjectPropertyRangeAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLPropertyRange result1 = testSubject0.getRange();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectSomeValuesFrom()
            throws OWLException {
        OWLObjectSomeValuesFrom testSubject0 = mock(OWLObjectSomeValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLObjectUnionOf() throws OWLException {
        OWLObjectUnionOf testSubject0 = mock(OWLObjectUnionOf.class);
        Set<OWLClassExpression> result0 = testSubject0.getOperands();
        List<OWLClassExpression> result1 = testSubject0.getOperandsAsList();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result4 = testSubject0.asOWLClass();
        }
        ClassExpressionType result6 = testSubject0.getClassExpressionType();
        boolean result7 = testSubject0.isClassExpressionLiteral();
        boolean result8 = testSubject0.isOWLThing();
        boolean result9 = testSubject0.isOWLNothing();
        OWLClassExpression result11 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result12 = testSubject0.asConjunctSet();
        boolean result13 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result14 = testSubject0.asDisjunctSet();
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
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLObjectVisitor() throws OWLException {
        OWLObjectVisitor testSubject0 = mock(OWLObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLObjectVisitorEx() throws OWLException {
        OWLObjectVisitorEx<OWLObject> testSubject0 = Utils.mockObject();
        Object result1 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        Object result7 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        Object result8 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        Object result11 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        Object result14 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        Object result22 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result26 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result27 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result29 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        Object result37 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntology() throws OWLException {
        OWLOntology testSubject0 = Utils.getMockOntology();
        boolean result0 = testSubject0.isEmpty();
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations();
        Set<OWLEntity> result2 = testSubject0.getSignature(false);
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
        Set<OWLDatatype> result13 = testSubject0.getDatatypesInSignature(false);
        Set<OWLDatatype> result14 = testSubject0.getDatatypesInSignature();
        OWLOntologyID result15 = testSubject0.getOntologyID();
        boolean result16 = testSubject0.isAnonymous();
        Set<IRI> result17 = testSubject0.getDirectImportsDocuments();
        Set<OWLOntology> result18 = testSubject0.getDirectImports();
        Set<OWLOntology> result19 = testSubject0.getImports();
        Set<OWLOntology> result20 = testSubject0.getImportsClosure();
        Set<OWLImportsDeclaration> result21 = testSubject0
                .getImportsDeclarations();
        Set<OWLDataPropertyAxiom> result22 = testSubject0.getAxioms(
                mock(OWLDataProperty.class), false);
        Set<OWLObjectPropertyAxiom> result23 = testSubject0.getAxioms(
                Utils.mockObjectProperty(), false);
        Set<OWLClassAxiom> result24 = testSubject0.getAxioms(
                mock(OWLClass.class), false);
        Set<OWLAxiom> result25 = testSubject0.getAxioms();
        Set<OWLAnnotationAssertionAxiom> result26 = testSubject0
                .getAxioms(Utils.mockAxiomType());
        Set<OWLAnnotationAssertionAxiom> result27 = testSubject0.getAxioms(
                Utils.mockAxiomType(), false);
        Set<OWLDatatypeDefinitionAxiom> result28 = testSubject0.getAxioms(
                mock(OWLDatatype.class), false);
        Set<OWLAnnotationAxiom> result29 = testSubject0.getAxioms(
                mock(OWLAnnotationProperty.class), false);
        Set<OWLIndividualAxiom> result30 = testSubject0.getAxioms(
                mock(OWLIndividual.class), false);
        int result31 = testSubject0.getAxiomCount(Utils.mockAxiomType(), false);
        int result32 = testSubject0.getAxiomCount();
        int result33 = testSubject0.getAxiomCount(Utils.mockAxiomType());
        Set<OWLLogicalAxiom> result34 = testSubject0.getLogicalAxioms();
        int result35 = testSubject0.getLogicalAxiomCount();
        Set<OWLAxiom> result36 = testSubject0.getTBoxAxioms(false);
        Set<OWLAxiom> result37 = testSubject0.getABoxAxioms(false);
        Set<OWLAxiom> result38 = testSubject0.getRBoxAxioms(false);
        boolean result39 = testSubject0.containsAxiom(mock(OWLAxiom.class));
        boolean result40 = testSubject0.containsAxiom(mock(OWLAxiom.class),
                false, false);
        Set<OWLAxiom> result43 = testSubject0.getAxiomsIgnoreAnnotations(
                mock(OWLAxiom.class), false);
        Set<OWLClassAxiom> result45 = testSubject0.getGeneralClassAxioms();
        Set<OWLAnonymousIndividual> result46 = testSubject0
                .getReferencedAnonymousIndividuals(false);
        Set<OWLAnnotationProperty> result47 = testSubject0
                .getAnnotationPropertiesInSignature(false);
        Set<OWLAxiom> result49 = testSubject0.getReferencingAxioms(
                Utils.mockOWLEntity(), false);
        boolean result61 = testSubject0.containsEntityInSignature(
                Utils.mockOWLEntity(), false);
        boolean result62 = testSubject0.containsEntityInSignature(Utils
                .mockOWLEntity());
        boolean result63 = testSubject0.containsEntityInSignature(
                IRI("urn:aFake"), false);
        boolean result64 = testSubject0.containsEntityInSignature(
                IRI("urn:aFake"), false);
        boolean result65 = testSubject0.isDeclared(Utils.mockOWLEntity());
        boolean result66 = testSubject0
                .isDeclared(Utils.mockOWLEntity(), false);
        boolean result67 = testSubject0.containsClassInSignature(
                IRI("urn:aFake"), false);
        boolean result68 = testSubject0.containsClassInSignature(
                IRI("urn:aFake"), false);
        boolean result59 = testSubject0.containsObjectPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result120 = testSubject0.containsObjectPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result121 = testSubject0.containsDataPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result122 = testSubject0.containsDataPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result123 = testSubject0.containsAnnotationPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result124 = testSubject0.containsAnnotationPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result125 = testSubject0.containsIndividualInSignature(
                IRI("urn:aFake"), false);
        boolean result126 = testSubject0.containsIndividualInSignature(
                IRI("urn:aFake"), false);
        boolean result127 = testSubject0.containsDatatypeInSignature(
                IRI("urn:aFake"), false);
        boolean result128 = testSubject0.containsDatatypeInSignature(
                IRI("urn:aFake"), false);
        Set<OWLEntity> result69 = testSubject0.getEntitiesInSignature(
                IRI("urn:aFake"), false);
        Set<OWLEntity> result70 = testSubject0
                .getEntitiesInSignature(IRI("urn:aFake"));
        Set<OWLSubAnnotationPropertyOfAxiom> result71 = testSubject0
                .getSubAnnotationPropertyOfAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLAnnotationPropertyDomainAxiom> result72 = testSubject0
                .getAnnotationPropertyDomainAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLAnnotationPropertyRangeAxiom> result73 = testSubject0
                .getAnnotationPropertyRangeAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLDeclarationAxiom> result74 = testSubject0
                .getDeclarationAxioms(Utils.mockOWLEntity());
        Set<OWLAnnotationAssertionAxiom> result75 = testSubject0
                .getAnnotationAssertionAxioms(mock(OWLAnnotationSubject.class));
        Set<OWLSubClassOfAxiom> result76 = testSubject0
                .getSubClassAxiomsForSubClass(mock(OWLClass.class));
        Set<OWLSubClassOfAxiom> result77 = testSubject0
                .getSubClassAxiomsForSuperClass(mock(OWLClass.class));
        Set<OWLEquivalentClassesAxiom> result78 = testSubject0
                .getEquivalentClassesAxioms(mock(OWLClass.class));
        Set<OWLDisjointClassesAxiom> result79 = testSubject0
                .getDisjointClassesAxioms(mock(OWLClass.class));
        Set<OWLDisjointUnionAxiom> result80 = testSubject0
                .getDisjointUnionAxioms(mock(OWLClass.class));
        Set<OWLHasKeyAxiom> result81 = testSubject0
                .getHasKeyAxioms(mock(OWLClass.class));
        Set<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSubProperty = testSubject0
                .getObjectSubPropertyAxiomsForSubProperty(Utils
                        .mockObjectProperty());
        Set<OWLSubObjectPropertyOfAxiom> result82 = objectSubPropertyAxiomsForSubProperty;
        Set<OWLSubObjectPropertyOfAxiom> result83 = testSubject0
                .getObjectSubPropertyAxiomsForSuperProperty(Utils
                        .mockObjectProperty());
        Set<OWLObjectPropertyDomainAxiom> result84 = testSubject0
                .getObjectPropertyDomainAxioms(Utils.mockObjectProperty());
        Set<OWLObjectPropertyRangeAxiom> result85 = testSubject0
                .getObjectPropertyRangeAxioms(Utils.mockObjectProperty());
        Set<OWLInverseObjectPropertiesAxiom> result86 = testSubject0
                .getInverseObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLEquivalentObjectPropertiesAxiom> result87 = testSubject0
                .getEquivalentObjectPropertiesAxioms(Utils.mockObjectProperty());
        Set<OWLDisjointObjectPropertiesAxiom> result88 = testSubject0
                .getDisjointObjectPropertiesAxioms(Utils.mockObjectProperty());
        Set<OWLFunctionalObjectPropertyAxiom> result89 = testSubject0
                .getFunctionalObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLInverseFunctionalObjectPropertyAxiom> result90 = testSubject0
                .getInverseFunctionalObjectPropertyAxioms(Utils
                        .mockObjectProperty());
        Set<OWLSymmetricObjectPropertyAxiom> result91 = testSubject0
                .getSymmetricObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLAsymmetricObjectPropertyAxiom> result92 = testSubject0
                .getAsymmetricObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLReflexiveObjectPropertyAxiom> result93 = testSubject0
                .getReflexiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLIrreflexiveObjectPropertyAxiom> result94 = testSubject0
                .getIrreflexiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLTransitiveObjectPropertyAxiom> result95 = testSubject0
                .getTransitiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLSubDataPropertyOfAxiom> result96 = testSubject0
                .getDataSubPropertyAxiomsForSubProperty(mock(OWLDataProperty.class));
        Set<OWLSubDataPropertyOfAxiom> result97 = testSubject0
                .getDataSubPropertyAxiomsForSuperProperty(mock(OWLDataPropertyExpression.class));
        Set<OWLDataPropertyDomainAxiom> result98 = testSubject0
                .getDataPropertyDomainAxioms(mock(OWLDataProperty.class));
        Set<OWLDataPropertyRangeAxiom> result99 = testSubject0
                .getDataPropertyRangeAxioms(mock(OWLDataProperty.class));
        Set<OWLEquivalentDataPropertiesAxiom> result100 = testSubject0
                .getEquivalentDataPropertiesAxioms(mock(OWLDataProperty.class));
        Set<OWLDisjointDataPropertiesAxiom> result101 = testSubject0
                .getDisjointDataPropertiesAxioms(mock(OWLDataProperty.class));
        Set<OWLFunctionalDataPropertyAxiom> result102 = testSubject0
                .getFunctionalDataPropertyAxioms(mock(OWLDataPropertyExpression.class));
        Set<OWLClassAssertionAxiom> result103 = testSubject0
                .getClassAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLClassAssertionAxiom> result104 = testSubject0
                .getClassAssertionAxioms(Utils.mockAnonClass());
        Set<OWLDataPropertyAssertionAxiom> result105 = testSubject0
                .getDataPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLObjectPropertyAssertionAxiom> result106 = testSubject0
                .getObjectPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLNegativeObjectPropertyAssertionAxiom> result107 = testSubject0
                .getNegativeObjectPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLNegativeDataPropertyAssertionAxiom> result108 = testSubject0
                .getNegativeDataPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLSameIndividualAxiom> result109 = testSubject0
                .getSameIndividualAxioms(mock(OWLIndividual.class));
        Set<OWLDifferentIndividualsAxiom> result110 = testSubject0
                .getDifferentIndividualAxioms(mock(OWLIndividual.class));
        Set<OWLDatatypeDefinitionAxiom> result111 = testSubject0
                .getDatatypeDefinitions(mock(OWLDatatype.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result112 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result113 = testSubject0
                .getAnonymousIndividuals();
        boolean result115 = testSubject0.isTopEntity();
        boolean result116 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLOntologyChange() throws OWLException {
        OWLOntologyChange<OWLAxiom> testSubject0 = new OWLOntologyChange<OWLAxiom>(
                Utils.getMockOntology()) {

            @Override
            public boolean isAxiomChange() {
                return false;
            }

            @Override
            public Set<OWLEntity> getSignature() {
                return Collections.emptySet();
            }

            @SuppressWarnings("unchecked")
            @Override
            public OWLOntologyChangeData<OWLAxiom> getChangeData() {
                return mock(OWLOntologyChangeData.class);
            }

            @Override
            public OWLAxiom getAxiom() {
                return mock(OWLAxiom.class);
            }

            @Override
            public boolean isImportChange() {
                return false;
            }

            @Override
            public boolean isAddAxiom() {
                return false;
            }

            @Override
            public void accept(OWLOntologyChangeVisitor visitor) {}

            @Override
            public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
                return null;
            }
        };
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockOntologyChange());
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeBroadcastStrategy()
            throws OWLException {
        OWLOntologyChangeBroadcastStrategy testSubject0 = mock(OWLOntologyChangeBroadcastStrategy.class);
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeListener()
            throws OWLException {
        OWLOntologyChangeListener testSubject0 = mock(OWLOntologyChangeListener.class);
        testSubject0.ontologiesChanged(Utils.mockList(mock(AddAxiom.class)));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeProgressListener()
            throws OWLException {
        OWLOntologyChangeProgressListener testSubject0 = mock(OWLOntologyChangeProgressListener.class);
        testSubject0.end();
        testSubject0.begin(0);
        testSubject0.appliedChange(mock(OWLOntologyChange.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangesVetoedListener()
            throws OWLException {
        OWLOntologyChangesVetoedListener testSubject0 = mock(OWLOntologyChangesVetoedListener.class);
        testSubject0.ontologyChangesVetoed(
                Utils.mockList(mock(AddAxiom.class)),
                mock(OWLOntologyChangeVetoException.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeVisitor()
            throws OWLException {
        OWLOntologyChangeVisitor testSubject0 = mock(OWLOntologyChangeVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeVisitorEx()
            throws OWLException {
        OWLOntologyChangeVisitorEx<OWLObject> testSubject0 = Utils
                .mockOntologyChange();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyFactory() throws OWLException {
        OWLOntologyFactory testSubject0 = mock(OWLOntologyFactory.class);
        OWLOntology result1 = testSubject0.createOWLOntology(
                mock(OWLOntologyManager.class), new OWLOntologyID(),
                IRI("urn:aFake"), mock(OWLOntologyCreationHandler.class));
        OWLOntology result3 = testSubject0.loadOWLOntology(
                mock(OWLOntologyManager.class),
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class),
                new OWLOntologyLoaderConfiguration());
        boolean result4 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
        boolean result5 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
    }

    @Test
    public void shouldTestOWLOntologyFactoryNotFoundException()
            throws OWLException {
        OWLOntologyFactoryNotFoundException testSubject0 = new OWLOntologyFactoryNotFoundException(
                IRI("urn:aFake"));
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLOntologyID() throws OWLException {
        OWLOntologyID testSubject0 = new OWLOntologyID();
        new OWLOntologyID(IRI("urn:aFake"), IRI("urn:aFake"));
        new OWLOntologyID(IRI("urn:aFake"));
        boolean result3 = testSubject0.isAnonymous();
        IRI result4 = testSubject0.getOntologyIRI();
        boolean result5 = testSubject0.isOWL2DLOntologyID();
        IRI result6 = testSubject0.getVersionIRI();
        IRI result7 = testSubject0.getDefaultDocumentIRI();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyIRIMapper() throws OWLException {
        OWLOntologyIRIMapper testSubject0 = mock(OWLOntologyIRIMapper.class);
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLOntologyIRIMappingNotFoundException()
            throws OWLException {
        OWLOntologyIRIMappingNotFoundException testSubject0 = new OWLOntologyIRIMappingNotFoundException(
                IRI("urn:aFake"));
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyLoaderListener()
            throws OWLException {
        OWLOntologyLoaderListener testSubject0 = mock(OWLOntologyLoaderListener.class);
    }

    @Test
    public void shouldTestOWLOntologyManagerProperties() throws OWLException {
        OWLOntologyManagerProperties testSubject0 = new OWLOntologyManagerProperties();
        testSubject0.setLoadAnnotationAxioms(false);
        boolean result0 = testSubject0.isLoadAnnotationAxioms();
        testSubject0.restoreDefaults();
        boolean result1 = testSubject0
                .isTreatDublinCoreVocabularyAsBuiltInVocabulary();
        testSubject0.setTreatDublinCoreVocabularyAsBuiltInVocabulary(false);
    }

    @Test
    public void shouldTestInterfaceOWLOntologySetProvider() throws OWLException {
        OWLOntologySetProvider testSubject0 = mock(OWLOntologySetProvider.class);
        Set<OWLOntology> result0 = testSubject0.getOntologies();
    }
}
