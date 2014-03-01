package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.OWLOntologyFactory.OWLOntologyCreationHandler;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration.MissingOntologyHeaderStrategy;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiModel_3Test {

    @Test
    public void shouldTestInterfaceOWLNegativeObjectPropertyAssertionAxiom()
            throws Exception {
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
    public void shouldTestInterfaceOWLObject() throws Exception {
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
    public void shouldTestInterfaceOWLObjectAllValuesFrom() throws Exception {
        OWLObjectAllValuesFrom testSubject0 = mock(OWLObjectAllValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isObjectRestriction();
        boolean result3 = testSubject0.isDataRestriction();
        Object result4 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result5 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result6 = testSubject0.asOWLClass();
            }
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
            throws Exception {
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectComplementOf() throws Exception {
        OWLObjectComplementOf testSubject0 = mock(OWLObjectComplementOf.class);
        OWLClassExpression result0 = testSubject0.getOperand();
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result2 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result3 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectExactCardinality() throws Exception {
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result9 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectHasSelf() throws Exception {
        OWLObjectHasSelf testSubject0 = mock(OWLObjectHasSelf.class);
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        boolean result1 = testSubject0.isObjectRestriction();
        boolean result2 = testSubject0.isDataRestriction();
        Object result3 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result4 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result5 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectHasValue() throws Exception {
        OWLObjectHasValue testSubject0 = mock(OWLObjectHasValue.class);
        OWLObject result0 = testSubject0.getValue();
        OWLClassExpression result1 = testSubject0.asSomeValuesFrom();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        Object result5 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result6 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result7 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectIntersectionOf() throws Exception {
        OWLObjectIntersectionOf testSubject0 = mock(OWLObjectIntersectionOf.class);
        Set<OWLClassExpression> result0 = testSubject0.getOperands();
        List<OWLClassExpression> result1 = testSubject0.getOperandsAsList();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectInverseOf() throws Exception {
        OWLObjectInverseOf testSubject0 = mock(OWLObjectInverseOf.class);
        OWLObjectPropertyExpression result0 = testSubject0.getInverse();
        if (!testSubject0.isAnonymous()) {
            OWLObjectProperty result1 = testSubject0.asOWLObjectProperty();
        }
        boolean result2 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result3 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result4 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result5 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result6 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result7 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result8 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result9 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result10 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result11 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result12 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result13 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result14 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result15 = testSubject0
                .getInverses(Utils.getMockOntology());
        OWLObjectPropertyExpression result16 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result17 = testSubject0.getSimplified();
        OWLObjectProperty result18 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result19 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result20 = testSubject0.isAnonymous();
        Set<OWLObjectPropertyExpression> result21 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result22 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result23 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result24 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result25 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result26 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result27 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLClassExpression> result28 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result29 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result30 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result31 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result32 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result33 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result34 = testSubject0.isFunctional(Utils.getMockOntology());
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
    public void shouldTestInterfaceOWLObjectMaxCardinality() throws Exception {
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectMinCardinality() throws Exception {
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectOneOf() throws Exception {
        OWLObjectOneOf testSubject0 = mock(OWLObjectOneOf.class);
        Set<OWLIndividual> result0 = testSubject0.getIndividuals();
        OWLClassExpression result1 = testSubject0.asObjectUnionOf();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectProperty() throws Exception {
        OWLObjectProperty testSubject0 = mock(OWLObjectProperty.class);
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result0 = testSubject0.asOWLObjectProperty();
        }
        boolean result1 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result2 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result3 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result4 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result5 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result6 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result7 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result8 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result9 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result10 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result11 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result12 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result13 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result14 = testSubject0
                .getInverses(Utils.getMockOntology());
        OWLObjectPropertyExpression result15 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result16 = testSubject0.getSimplified();
        OWLObjectProperty result17 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result19 = testSubject0.isAnonymous();
        Set<OWLObjectPropertyExpression> result20 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result21 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result22 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result23 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result24 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result25 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result26 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLClassExpression> result27 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result28 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result29 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result30 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result31 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result32 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result33 = testSubject0.isFunctional(Utils.getMockOntology());
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
        Set<OWLAnnotation> result82 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result83 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result84 = testSubject0.accept(Utils.mockEntity());
        boolean result85 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result86 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result87 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result88 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result59 = testSubject0.isBuiltIn();
        EntityType<?> result60 = testSubject0.getEntityType();
        boolean result62 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result63 = testSubject0.asOWLClass();
            }
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
            throws Exception {
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
    public void shouldTestInterfaceOWLObjectPropertyAxiom() throws Exception {
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
            throws Exception {
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
            throws Exception {
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
            throws Exception {
        OWLObjectPropertyExpression testSubject0 = Utils.mockObjectProperty();
        if (!testSubject0.isAnonymous()) {
            OWLObjectProperty result0 = testSubject0.asOWLObjectProperty();
        }
        boolean result1 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result2 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result3 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result4 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result5 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result6 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result7 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result8 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result9 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result10 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result11 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result12 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result13 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result14 = testSubject0
                .getInverses(Utils.getMockOntology());
        OWLObjectPropertyExpression result15 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result16 = testSubject0.getSimplified();
        OWLObjectProperty result17 = testSubject0.getNamedProperty();
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result19 = testSubject0.isAnonymous();
        Set<OWLObjectPropertyExpression> result20 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result21 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result22 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result23 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result24 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result25 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result26 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLClassExpression> result27 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result28 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result29 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result30 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result31 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result32 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result33 = testSubject0.isFunctional(Utils.getMockOntology());
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
            throws Exception {
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
    public void shouldTestInterfaceOWLObjectSomeValuesFrom() throws Exception {
        OWLObjectSomeValuesFrom testSubject0 = mock(OWLObjectSomeValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isObjectRestriction();
        boolean result3 = testSubject0.isDataRestriction();
        Object result4 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result5 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result6 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectUnionOf() throws Exception {
        OWLObjectUnionOf testSubject0 = mock(OWLObjectUnionOf.class);
        Set<OWLClassExpression> result0 = testSubject0.getOperands();
        List<OWLClassExpression> result1 = testSubject0.getOperandsAsList();
        Object result2 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLObjectVisitor() throws Exception {
        OWLObjectVisitor testSubject0 = mock(OWLObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLObjectVisitorEx() throws Exception {
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

    public void shouldTestInterfaceOWLOntology() throws Exception {
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
        Set<OWLDataPropertyAxiom> result22 = testSubject0
                .getAxioms(mock(OWLDataProperty.class));
        Set<OWLObjectPropertyAxiom> result23 = testSubject0.getAxioms(Utils
                .mockObjectProperty());
        Set<OWLClassAxiom> result24 = testSubject0
                .getAxioms(mock(OWLClass.class));
        Set<OWLAxiom> result25 = testSubject0.getAxioms();
        Set<OWLAnnotationAssertionAxiom> result26 = testSubject0
                .getAxioms(Utils.mockAxiomType());
        Set<OWLAnnotationAssertionAxiom> result27 = testSubject0.getAxioms(
                Utils.mockAxiomType(), false);
        Set<OWLDatatypeDefinitionAxiom> result28 = testSubject0
                .getAxioms(mock(OWLDatatype.class));
        Set<OWLAnnotationAxiom> result29 = testSubject0
                .getAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLIndividualAxiom> result30 = testSubject0
                .getAxioms(mock(OWLIndividual.class));
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
                false);
        boolean result41 = testSubject0.containsAxiomIgnoreAnnotations(
                mock(OWLAxiom.class), false);
        boolean result42 = testSubject0
                .containsAxiomIgnoreAnnotations(mock(OWLAxiom.class));
        Set<OWLAxiom> result43 = testSubject0.getAxiomsIgnoreAnnotations(
                mock(OWLAxiom.class), false);
        Set<OWLAxiom> result44 = testSubject0
                .getAxiomsIgnoreAnnotations(mock(OWLAxiom.class));
        Set<OWLClassAxiom> result45 = testSubject0.getGeneralClassAxioms();
        Set<OWLAnonymousIndividual> result46 = testSubject0
                .getReferencedAnonymousIndividuals();
        Set<OWLAnnotationProperty> result47 = testSubject0
                .getAnnotationPropertiesInSignature();
        Set<OWLAxiom> result48 = testSubject0.getReferencingAxioms(Utils
                .mockOWLEntity());
        Set<OWLAxiom> result49 = testSubject0.getReferencingAxioms(
                Utils.mockOWLEntity(), false);
        Set<OWLAxiom> result60 = testSubject0
                .getReferencingAxioms(mock(OWLAnonymousIndividual.class));
        boolean result61 = testSubject0.containsEntityInSignature(
                Utils.mockOWLEntity(), false);
        boolean result62 = testSubject0.containsEntityInSignature(Utils
                .mockOWLEntity());
        boolean result63 = testSubject0.containsEntityInSignature(
                IRI("urn:aFake"), false);
        boolean result64 = testSubject0
                .containsEntityInSignature(IRI("urn:aFake"));
        boolean result65 = testSubject0.isDeclared(Utils.mockOWLEntity());
        boolean result66 = testSubject0
                .isDeclared(Utils.mockOWLEntity(), false);
        boolean result67 = testSubject0.containsClassInSignature(
                IRI("urn:aFake"), false);
        boolean result68 = testSubject0
                .containsClassInSignature(IRI("urn:aFake"));
        boolean result59 = testSubject0.containsObjectPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result120 = testSubject0
                .containsObjectPropertyInSignature(IRI("urn:aFake"));
        boolean result121 = testSubject0
                .containsDataPropertyInSignature(IRI("urn:aFake"));
        boolean result122 = testSubject0.containsDataPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result123 = testSubject0
                .containsAnnotationPropertyInSignature(IRI("urn:aFake"));
        boolean result124 = testSubject0.containsAnnotationPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result125 = testSubject0
                .containsIndividualInSignature(IRI("urn:aFake"));
        boolean result126 = testSubject0.containsIndividualInSignature(
                IRI("urn:aFake"), false);
        boolean result127 = testSubject0
                .containsDatatypeInSignature(IRI("urn:aFake"));
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
    public void shouldTestOWLOntologyAlreadyExistsException() throws Exception {
        OWLOntologyAlreadyExistsException testSubject0 = new OWLOntologyAlreadyExistsException(
                new OWLOntologyID());
        OWLOntologyAlreadyExistsException testSubject1 = new OWLOntologyAlreadyExistsException(
                new OWLOntologyID(), IRI("urn:aFake"));
        OWLOntologyAlreadyExistsException testSubject2 = new OWLOntologyAlreadyExistsException(
                new OWLOntologyID(), new RuntimeException());
        OWLOntologyAlreadyExistsException testSubject3 = new OWLOntologyAlreadyExistsException(
                new OWLOntologyID(), IRI("urn:aFake"), new RuntimeException());
        IRI result0 = testSubject0.getDocumentIRI();
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLOntologyChange() throws Exception {
        OWLOntologyChange testSubject0 = new OWLOntologyChange(
                Utils.getMockOntology()) {

            @Override
            public boolean isAxiomChange() {
                return false;
            }

            @Override
            public Set<OWLEntity> getSignature() {
                return null;
            }

            @Override
            public OWLOntologyChangeData getChangeData() {
                return null;
            }

            @Override
            public OWLAxiom getAxiom() {
                return null;
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
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeBroadcastStrategy()
            throws Exception {
        OWLOntologyChangeBroadcastStrategy testSubject0 = mock(OWLOntologyChangeBroadcastStrategy.class);
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
    }

    @Test
    public void shouldTestOWLOntologyChangeException() throws Exception {
        OWLOntologyChangeException testSubject0 = new OWLOntologyChangeException(
                mock(OWLOntologyChange.class), "") {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLOntologyChangeException testSubject1 = new OWLOntologyChangeException(
                mock(OWLOntologyChange.class), "", new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLOntologyChangeException testSubject2 = new OWLOntologyChangeException(
                mock(OWLOntologyChange.class), new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLOntologyChange result0 = testSubject0.getChange();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeListener() throws Exception {
        OWLOntologyChangeListener testSubject0 = mock(OWLOntologyChangeListener.class);
        testSubject0.ontologiesChanged(Utils
                .mockList(mock(OWLOntologyChange.class)));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeProgressListener()
            throws Exception {
        OWLOntologyChangeProgressListener testSubject0 = mock(OWLOntologyChangeProgressListener.class);
        testSubject0.end();
        testSubject0.begin(0);
        testSubject0.appliedChange(mock(OWLOntologyChange.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangesVetoedListener()
            throws Exception {
        OWLOntologyChangesVetoedListener testSubject0 = mock(OWLOntologyChangesVetoedListener.class);
        testSubject0.ontologyChangesVetoed(
                Utils.mockList(mock(OWLOntologyChange.class)),
                mock(OWLOntologyChangeVetoException.class));
    }

    @Test
    public void shouldTestOWLOntologyChangeVetoException() throws Exception {
        OWLOntologyChangeVetoException testSubject0 = new OWLOntologyChangeVetoException(
                mock(OWLOntologyChange.class), "");
        OWLOntologyChangeVetoException testSubject1 = new OWLOntologyChangeVetoException(
                mock(OWLOntologyChange.class), "", new RuntimeException());
        OWLOntologyChangeVetoException testSubject2 = new OWLOntologyChangeVetoException(
                mock(OWLOntologyChange.class), new RuntimeException());
        OWLOntologyChange result0 = testSubject0.getChange();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeVisitor() throws Exception {
        OWLOntologyChangeVisitor testSubject0 = mock(OWLOntologyChangeVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChangeVisitorEx()
            throws Exception {
        OWLOntologyChangeVisitorEx<OWLObject> testSubject0 = Utils
                .mockOntologyChange();
    }

    @Test
    public void shouldTestOWLOntologyCreationException() throws Exception {
        OWLOntologyCreationException testSubject0 = new OWLOntologyCreationException();
        OWLOntologyCreationException testSubject1 = new OWLOntologyCreationException(
                "");
        OWLOntologyCreationException testSubject2 = new OWLOntologyCreationException(
                "", new RuntimeException());
        OWLOntologyCreationException testSubject3 = new OWLOntologyCreationException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLOntologyDocumentAlreadyExistsException()
            throws Exception {
        OWLOntologyDocumentAlreadyExistsException testSubject0 = new OWLOntologyDocumentAlreadyExistsException(
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getOntologyDocumentIRI();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyFactory() throws Exception {
        OWLOntologyFactory testSubject0 = mock(OWLOntologyFactory.class);
        testSubject0.setOWLOntologyManager(Utils.getMockManager());
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.createOWLOntology(
                new OWLOntologyID(), IRI("urn:aFake"),
                mock(OWLOntologyCreationHandler.class));
        OWLOntology result2 = testSubject0.loadOWLOntology(
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class));
        OWLOntology result3 = testSubject0.loadOWLOntology(
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
            throws Exception {
        OWLOntologyFactoryNotFoundException testSubject0 = new OWLOntologyFactoryNotFoundException(
                IRI("urn:aFake"));
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestOWLOntologyFormat() throws Exception {
        @SuppressWarnings("serial")
        OWLOntologyFormat testSubject0 = new OWLOntologyFormat() {};
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result0 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result1 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result2 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result3 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyID() throws Exception {
        OWLOntologyID testSubject0 = new OWLOntologyID();
        OWLOntologyID testSubject1 = new OWLOntologyID(IRI("urn:aFake"),
                IRI("urn:aFake"));
        OWLOntologyID testSubject2 = new OWLOntologyID(IRI("urn:aFake"));
        String result0 = testSubject0.toString();
        boolean result3 = testSubject0.isAnonymous();
        IRI result4 = testSubject0.getOntologyIRI();
        boolean result5 = testSubject0.isOWL2DLOntologyID();
        IRI result6 = testSubject0.getVersionIRI();
        IRI result7 = testSubject0.getDefaultDocumentIRI();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyIRIMapper() throws Exception {
        OWLOntologyIRIMapper testSubject0 = mock(OWLOntologyIRIMapper.class);
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLOntologyIRIMappingNotFoundException()
            throws Exception {
        OWLOntologyIRIMappingNotFoundException testSubject0 = new OWLOntologyIRIMappingNotFoundException(
                IRI("urn:aFake"));
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestOWLOntologyLoaderConfiguration() throws Exception {
        OWLOntologyLoaderConfiguration testSubject0 = new OWLOntologyLoaderConfiguration();
        boolean result0 = testSubject0.isStrict();
        MissingOntologyHeaderStrategy result2 = testSubject0
                .getMissingOntologyHeaderStrategy();
        OWLOntologyLoaderConfiguration result3 = testSubject0
                .setMissingOntologyHeaderStrategy(mock(MissingOntologyHeaderStrategy.class));
        OWLOntologyLoaderConfiguration result4 = testSubject0
                .setLoadAnnotationAxioms(false);
        boolean result5 = testSubject0.isLoadAnnotationAxioms();
        OWLOntologyLoaderConfiguration result6 = testSubject0
                .setMissingImportHandlingStrategy(mock(MissingImportHandlingStrategy.class));
        MissingImportHandlingStrategy result7 = testSubject0
                .getMissingImportHandlingStrategy();
        OWLOntologyLoaderConfiguration result8 = testSubject0.setStrict(false);
        boolean result9 = testSubject0.isIgnoredImport(IRI("urn:aFake"));
        Set<IRI> result10 = testSubject0.getIgnoredImports();
        OWLOntologyLoaderConfiguration result11 = testSubject0
                .addIgnoredImport(IRI("urn:aFake"));
        OWLOntologyLoaderConfiguration result12 = testSubject0
                .removeIgnoredImport(IRI("urn:aFake"));
        OWLOntologyLoaderConfiguration result13 = testSubject0
                .clearIgnoredImports();
        String result14 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyLoaderListener() throws Exception {
        OWLOntologyLoaderListener testSubject0 = mock(OWLOntologyLoaderListener.class);
    }

    public void shouldTestInterfaceOWLOntologyManager() throws Exception {
        OWLOntologyManager testSubject0 = Utils.getMockManager();
        boolean result0 = testSubject0.contains(IRI("urn:aFake"));
        boolean result1 = testSubject0.contains(new OWLOntologyID());
        Set<OWLOntology> result2 = testSubject0.getDirectImports(Utils
                .getMockOntology());
        Set<OWLOntology> result3 = testSubject0.getImports(Utils
                .getMockOntology());
        Set<OWLOntology> result4 = testSubject0.getImportsClosure(Utils
                .getMockOntology());
        OWLDataFactory result5 = testSubject0.getOWLDataFactory();
        Set<OWLOntology> result6 = testSubject0.getOntologies();
        Set<OWLOntology> result7 = testSubject0
                .getOntologies(mock(OWLAxiom.class));
        Set<OWLOntology> result8 = testSubject0.getVersions(IRI("urn:aFake"));
        OWLOntology result9 = testSubject0.getOntology(IRI("urn:aFake"));
        OWLOntology result10 = testSubject0.getOntology(new OWLOntologyID());
        OWLOntology result11 = testSubject0
                .getImportedOntology(mock(OWLImportsDeclaration.class));
        List<OWLOntology> result12 = testSubject0.getSortedImportsClosure(Utils
                .getMockOntology());
        List<OWLOntologyChange> result13 = testSubject0.applyChanges(Utils
                .mockList(mock(OWLOntologyChange.class)));
        List<OWLOntologyChange> result14 = testSubject0.addAxioms(
                Utils.getMockOntology(), Utils.mockSet(mock(OWLAxiom.class)));
        List<OWLOntologyChange> result15 = testSubject0.addAxiom(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        List<OWLOntologyChange> result16 = testSubject0.removeAxiom(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        List<OWLOntologyChange> result17 = testSubject0.removeAxioms(
                Utils.getMockOntology(), Utils.mockSet(mock(OWLAxiom.class)));
        List<OWLOntologyChange> result18 = testSubject0
                .applyChange(mock(OWLOntologyChange.class));
        OWLOntology result19 = testSubject0.createOntology();
        OWLOntology result20 = testSubject0.createOntology(Utils
                .mockSet(mock(OWLAxiom.class)));
        OWLOntology result21 = testSubject0.createOntology(
                Utils.mockSet(mock(OWLAxiom.class)), IRI("urn:aFake"));
        OWLOntology result22 = testSubject0.createOntology(IRI("urn:aFake"));
        OWLOntology result23 = testSubject0.createOntology(new OWLOntologyID());
        OWLOntology result24 = testSubject0.createOntology(IRI("urn:aFake"),
                Utils.mockSet(Utils.getMockOntology()), false);
        OWLOntology result25 = testSubject0.createOntology(IRI("urn:aFake"),
                Utils.mockSet(Utils.getMockOntology()));
        OWLOntology result26 = testSubject0.loadOntology(IRI("urn:aFake"));
        OWLOntology result27 = testSubject0
                .loadOntologyFromOntologyDocument(IRI("urn:aFake"));
        OWLOntology result28 = testSubject0
                .loadOntologyFromOntologyDocument(mock(File.class));
        OWLOntology result29 = testSubject0
                .loadOntologyFromOntologyDocument(mock(InputStream.class));
        OWLOntology result30 = testSubject0
                .loadOntologyFromOntologyDocument(mock(OWLOntologyDocumentSource.class));
        OWLOntology result31 = testSubject0.loadOntologyFromOntologyDocument(
                mock(OWLOntologyDocumentSource.class),
                new OWLOntologyLoaderConfiguration());
        testSubject0.removeOntology(Utils.getMockOntology());
        IRI result32 = testSubject0.getOntologyDocumentIRI(Utils
                .getMockOntology());
        testSubject0.setOntologyDocumentIRI(Utils.getMockOntology(),
                IRI("urn:aFake"));
        OWLOntologyFormat result33 = testSubject0.getOntologyFormat(Utils
                .getMockOntology());
        testSubject0.setOntologyFormat(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class));
        testSubject0.saveOntology(Utils.getMockOntology());
        testSubject0.saveOntology(Utils.getMockOntology(), IRI("urn:aFake"));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OutputStream.class));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class), IRI("urn:aFake"));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class), mock(OutputStream.class));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class));
        testSubject0.saveOntology(Utils.getMockOntology(),
                mock(OWLOntologyFormat.class),
                mock(OWLOntologyDocumentTarget.class));
        testSubject0.addIRIMapper(mock(OWLOntologyIRIMapper.class));
        testSubject0.removeIRIMapper(mock(OWLOntologyIRIMapper.class));
        testSubject0.clearIRIMappers();
        testSubject0.addOntologyFactory(mock(OWLOntologyFactory.class));
        testSubject0.removeOntologyFactory(mock(OWLOntologyFactory.class));
        Collection<OWLOntologyFactory> result34 = testSubject0
                .getOntologyFactories();
        testSubject0.addOntologyStorer(mock(OWLOntologyStorer.class));
        testSubject0.removeOntologyStorer(mock(OWLOntologyStorer.class));
        testSubject0
                .addOntologyChangeListener(mock(OWLOntologyChangeListener.class));
        testSubject0.addOntologyChangeListener(
                mock(OWLOntologyChangeListener.class),
                mock(OWLOntologyChangeBroadcastStrategy.class));
        testSubject0
                .addImpendingOntologyChangeListener(mock(ImpendingOWLOntologyChangeListener.class));
        testSubject0
                .removeImpendingOntologyChangeListener(mock(ImpendingOWLOntologyChangeListener.class));
        testSubject0
                .addOntologyChangesVetoedListener(mock(OWLOntologyChangesVetoedListener.class));
        testSubject0
                .removeOntologyChangesVetoedListener(mock(OWLOntologyChangesVetoedListener.class));
        testSubject0
                .setDefaultChangeBroadcastStrategy(mock(OWLOntologyChangeBroadcastStrategy.class));
        testSubject0
                .removeOntologyChangeListener(mock(OWLOntologyChangeListener.class));
        testSubject0.makeLoadImportRequest(mock(OWLImportsDeclaration.class),
                new OWLOntologyLoaderConfiguration());
        testSubject0
                .addMissingImportListener(mock(MissingImportListener.class));
        testSubject0
                .removeMissingImportListener(mock(MissingImportListener.class));
        testSubject0
                .addOntologyLoaderListener(mock(OWLOntologyLoaderListener.class));
        testSubject0
                .removeOntologyLoaderListener(mock(OWLOntologyLoaderListener.class));
        testSubject0
                .addOntologyChangeProgessListener(mock(OWLOntologyChangeProgressListener.class));
        testSubject0
                .removeOntologyChangeProgessListener(mock(OWLOntologyChangeProgressListener.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyManagerFactory() throws Exception {
        OWLOntologyManagerFactory testSubject0 = mock(OWLOntologyManagerFactory.class);
        OWLDataFactory result0 = testSubject0.getFactory();
        OWLOntologyManager result1 = testSubject0.buildOWLOntologyManager();
        OWLOntologyManager result2 = testSubject0
                .buildOWLOntologyManager(mock(OWLDataFactory.class));
    }

    @Test
    public void shouldTestOWLOntologyManagerProperties() throws Exception {
        OWLOntologyManagerProperties testSubject0 = new OWLOntologyManagerProperties();
        testSubject0.setLoadAnnotationAxioms(false);
        boolean result0 = testSubject0.isLoadAnnotationAxioms();
        testSubject0.restoreDefaults();
        boolean result1 = testSubject0
                .isTreatDublinCoreVocabularyAsBuiltInVocabulary();
        testSubject0.setTreatDublinCoreVocabularyAsBuiltInVocabulary(false);
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyRenameException() throws Exception {
        OWLOntologyRenameException testSubject0 = new OWLOntologyRenameException(
                mock(OWLOntologyChange.class), new OWLOntologyID());
        OWLOntologyID result0 = testSubject0.getOntologyID();
        OWLOntologyChange result1 = testSubject0.getChange();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLOntologyResourceAccessException() throws Exception {
        OWLOntologyResourceAccessException testSubject0 = new OWLOntologyResourceAccessException(
                "");
        OWLOntologyResourceAccessException testSubject1 = new OWLOntologyResourceAccessException(
                "", new RuntimeException());
        OWLOntologyResourceAccessException testSubject2 = new OWLOntologyResourceAccessException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologySetProvider() throws Exception {
        OWLOntologySetProvider testSubject0 = mock(OWLOntologySetProvider.class);
        Set<OWLOntology> result0 = testSubject0.getOntologies();
    }
}
