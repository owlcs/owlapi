package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiModel_2Test {

    @Test
    public void shouldTestInterfaceOWLDataProperty() throws Exception {
        OWLDataProperty testSubject0 = mock(OWLDataProperty.class);
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result0 = testSubject0.asOWLDataProperty();
        }
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result2 = testSubject0.isAnonymous();
        Set<OWLDataPropertyExpression> result3 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result4 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result5 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result6 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result7 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result8 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLDataRange> result9 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLDataRange> result10 = testSubject0.getRanges(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLDataPropertyExpression> result11 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result12 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result13 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result14 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result15 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result16 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result17 = testSubject0.isDataPropertyExpression();
        boolean result18 = testSubject0.isObjectPropertyExpression();
        boolean result19 = testSubject0.isOWLTopObjectProperty();
        boolean result20 = testSubject0.isOWLBottomObjectProperty();
        boolean result21 = testSubject0.isOWLTopDataProperty();
        boolean result22 = testSubject0.isOWLBottomDataProperty();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
        Set<OWLAnnotation> result35 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result36 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result37 = testSubject0.accept(Utils.mockEntity());
        boolean result38 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result39 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result40 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result41 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result42 = testSubject0.isBuiltIn();
        EntityType<?> result43 = testSubject0.getEntityType();
        boolean result45 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result46 = testSubject0.asOWLClass();
            }
        }
        boolean result47 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result48 = testSubject0.asOWLObjectProperty();
        }
        boolean result49 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result29 = testSubject0.asOWLDataProperty();
        }
        boolean result30 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result52 = testSubject0.asOWLNamedIndividual();
        }
        boolean result23 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result24 = testSubject0.asOWLDatatype();
        }
        boolean result25 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result26 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result27 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result28 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyAssertionAxiom()
            throws Exception {
        OWLDataPropertyAssertionAxiom testSubject0 = mock(OWLDataPropertyAssertionAxiom.class);
        OWLDataPropertyAssertionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLDataPropertyExpression result1 = testSubject0.getProperty();
        OWLPropertyAssertionObject result2 = testSubject0.getObject();
        OWLIndividual result3 = testSubject0.getSubject();
        Set<OWLAnnotation> result4 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result5 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result6 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result7 = testSubject0.getAxiomWithoutAnnotations();
        boolean result9 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        boolean result12 = testSubject0.isAnnotated();
        AxiomType<?> result13 = testSubject0.getAxiomType();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result29 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyAxiom() throws Exception {
        OWLDataPropertyAxiom testSubject0 = mock(OWLDataPropertyAxiom.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyCharacteristicAxiom()
            throws Exception {
        OWLDataPropertyCharacteristicAxiom testSubject0 = mock(OWLDataPropertyCharacteristicAxiom.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
        OWLDataPropertyExpression result25 = testSubject0.getProperty();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyDomainAxiom()
            throws Exception {
        OWLDataPropertyDomainAxiom testSubject0 = mock(OWLDataPropertyDomainAxiom.class);
        OWLDataPropertyDomainAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLClassExpression result1 = testSubject0.getDomain();
        OWLDataPropertyExpression result2 = testSubject0.getProperty();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result28 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyExpression() throws Exception {
        OWLDataPropertyExpression testSubject0 = mock(OWLDataPropertyExpression.class);
        if (!testSubject0.isAnonymous()) {
            OWLDataProperty result0 = testSubject0.asOWLDataProperty();
        }
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result2 = testSubject0.isAnonymous();
        Set<OWLDataPropertyExpression> result3 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result4 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result5 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result6 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result7 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result8 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLDataRange> result9 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLDataRange> result10 = testSubject0.getRanges(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLDataPropertyExpression> result11 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result12 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result13 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result14 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result15 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result16 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result17 = testSubject0.isDataPropertyExpression();
        boolean result18 = testSubject0.isObjectPropertyExpression();
        boolean result19 = testSubject0.isOWLTopObjectProperty();
        boolean result20 = testSubject0.isOWLBottomObjectProperty();
        boolean result21 = testSubject0.isOWLTopDataProperty();
        boolean result22 = testSubject0.isOWLBottomDataProperty();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataPropertyRangeAxiom() throws Exception {
        OWLDataPropertyRangeAxiom testSubject0 = mock(OWLDataPropertyRangeAxiom.class);
        OWLDataPropertyRangeAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLPropertyRange result1 = testSubject0.getRange();
        OWLDataPropertyExpression result2 = testSubject0.getProperty();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result28 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLDataRange() throws Exception {
        OWLDataRange testSubject0 = mock(OWLDataRange.class);
        Object result0 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isDatatype()) {
            OWLDatatype result2 = testSubject0.asOWLDatatype();
        }
        boolean result3 = testSubject0.isDatatype();
        boolean result4 = testSubject0.isTopDatatype();
        DataRangeType result5 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataRangeVisitor() throws Exception {
        OWLDataRangeVisitor testSubject0 = mock(OWLDataRangeVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLDataRangeVisitorEx() throws Exception {
        OWLDataRangeVisitorEx<OWLObject> testSubject0 = Utils.mockDataRange();
    }

    @Test
    public void shouldTestInterfaceOWLDataSomeValuesFrom() throws Exception {
        OWLDataSomeValuesFrom testSubject0 = mock(OWLDataSomeValuesFrom.class);
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLDataPropertyExpression result1 = testSubject0.getProperty();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDatatype() throws Exception {
        OWLDatatype testSubject0 = mock(OWLDatatype.class);
        boolean result0 = testSubject0.isRDFPlainLiteral();
        boolean result1 = testSubject0.isInteger();
        boolean result2 = testSubject0.isBoolean();
        boolean result3 = testSubject0.isDouble();
        boolean result4 = testSubject0.isFloat();
        OWL2Datatype result5 = testSubject0.getBuiltInDatatype();
        boolean result6 = testSubject0.isString();
        Object result7 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result8 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result9 = testSubject0.asOWLDatatype();
        }
        boolean result10 = testSubject0.isDatatype();
        boolean result11 = testSubject0.isTopDatatype();
        DataRangeType result12 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
        Set<OWLAnnotation> result25 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result26 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockEntity());
        boolean result28 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result29 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result30 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result31 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result32 = testSubject0.isBuiltIn();
        EntityType<?> result33 = testSubject0.getEntityType();
        boolean result35 = !testSubject0.isDatatype();
        if (!testSubject0.isOWLClass()) {
            OWLClass result36 = testSubject0.asOWLClass();
        }
        boolean result37 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result38 = testSubject0.asOWLObjectProperty();
        }
        boolean result39 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result40 = testSubject0.asOWLDataProperty();
        }
        boolean result41 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result42 = testSubject0.asOWLNamedIndividual();
        }
        boolean result43 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result44 = testSubject0.asOWLDatatype();
        }
        boolean result45 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result46 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result47 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result48 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLDatatypeDefinitionAxiom()
            throws Exception {
        OWLDatatypeDefinitionAxiom testSubject0 = mock(OWLDatatypeDefinitionAxiom.class);
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        OWLDatatype result1 = testSubject0.getDatatype();
        OWLDataRange result2 = testSubject0.getDataRange();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result4 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        OWLAxiom result5 = testSubject0.getAxiomWithoutAnnotations();
        boolean result7 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
        boolean result10 = testSubject0.isAnnotated();
        AxiomType<?> result11 = testSubject0.getAxiomType();
        boolean result12 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result13 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDatatypeRestriction() throws Exception {
        OWLDatatypeRestriction testSubject0 = mock(OWLDatatypeRestriction.class);
        OWLDatatype result0 = testSubject0.getDatatype();
        Set<OWLFacetRestriction> result1 = testSubject0.getFacetRestrictions();
        Object result2 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        if (testSubject0.isDatatype()) {
            OWLDatatype result4 = testSubject0.asOWLDatatype();
        }
        boolean result5 = testSubject0.isDatatype();
        boolean result6 = testSubject0.isTopDatatype();
        DataRangeType result7 = testSubject0.getDataRangeType();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result17 = testSubject0.isTopEntity();
        boolean result18 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataUnionOf() throws Exception {
        OWLDataUnionOf testSubject0 = mock(OWLDataUnionOf.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDataVisitor() throws Exception {
        OWLDataVisitor testSubject0 = mock(OWLDataVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLDataVisitorEx() throws Exception {
        OWLDataVisitorEx<OWLObject> testSubject0 = Utils.mockData();
    }

    @Test
    public void shouldTestInterfaceOWLDeclarationAxiom() throws Exception {
        OWLDeclarationAxiom testSubject0 = mock(OWLDeclarationAxiom.class);
        OWLDeclarationAxiom result0 = testSubject0.getAxiomWithoutAnnotations();
        OWLEntity result1 = testSubject0.getEntity();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result3 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result5 = testSubject0.getAxiomWithoutAnnotations();
        boolean result7 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
        boolean result10 = testSubject0.isAnnotated();
        AxiomType<?> result11 = testSubject0.getAxiomType();
        boolean result12 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result13 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDifferentIndividualsAxiom()
            throws Exception {
        OWLDifferentIndividualsAxiom testSubject0 = mock(OWLDifferentIndividualsAxiom.class);
        OWLDifferentIndividualsAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result1 = testSubject0.containsAnonymousIndividuals();
        Set<OWLDifferentIndividualsAxiom> result2 = testSubject0
                .asPairwiseAxioms();
        Set<OWLIndividual> result3 = testSubject0.getIndividuals();
        List<OWLIndividual> result4 = testSubject0.getIndividualsAsList();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
        Set<OWLSubClassOfAxiom> result30 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLDisjointClassesAxiom() throws Exception {
        OWLDisjointClassesAxiom testSubject0 = mock(OWLDisjointClassesAxiom.class);
        OWLDisjointClassesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLDisjointClassesAxiom> result1 = testSubject0.asPairwiseAxioms();
        boolean result2 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result3 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result4 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result5 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
        Set<OWLSubClassOfAxiom> result31 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLDisjointDataPropertiesAxiom()
            throws Exception {
        OWLDisjointDataPropertiesAxiom testSubject0 = mock(OWLDisjointDataPropertiesAxiom.class);
        OWLDisjointDataPropertiesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLDataPropertyExpression> result1 = testSubject0.getProperties();
        Set<OWLDataPropertyExpression> result2 = testSubject0
                .getPropertiesMinus(mock(OWLDataPropertyExpression.class));
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDisjointObjectPropertiesAxiom()
            throws Exception {
        OWLDisjointObjectPropertiesAxiom testSubject0 = mock(OWLDisjointObjectPropertiesAxiom.class);
        OWLDisjointObjectPropertiesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLObjectPropertyExpression> result1 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result2 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLDisjointUnionAxiom() throws Exception {
        OWLDisjointUnionAxiom testSubject0 = mock(OWLDisjointUnionAxiom.class);
        OWLDisjointUnionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLClassExpression> result1 = testSubject0.getClassExpressions();
        OWLClass result2 = testSubject0.getOWLClass();
        OWLEquivalentClassesAxiom result3 = testSubject0
                .getOWLEquivalentClassesAxiom();
        OWLDisjointClassesAxiom result4 = testSubject0
                .getOWLDisjointClassesAxiom();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLEntity() throws Exception {
        OWLEntity testSubject0 = Utils.mockOWLEntity();
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockEntity());
        boolean result3 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result4 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result5 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result6 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result7 = testSubject0.isBuiltIn();
        EntityType<?> result8 = testSubject0.getEntityType();
        boolean result10 = !testSubject0.isOWLClass();
        if (testSubject0.isOWLClass()) {
            OWLClass result11 = testSubject0.asOWLClass();
        }
        boolean result12 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result13 = testSubject0.asOWLObjectProperty();
        }
        boolean result14 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result15 = testSubject0.asOWLDataProperty();
        }
        boolean result16 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result17 = testSubject0.asOWLNamedIndividual();
        }
        boolean result18 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result19 = testSubject0.asOWLDatatype();
        }
        boolean result20 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result21 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result22 = testSubject0.toStringID();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result35 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLEntityVisitor() throws Exception {
        OWLEntityVisitor testSubject0 = mock(OWLEntityVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLEntityVisitorEx() throws Exception {
        OWLEntityVisitorEx<OWLObject> testSubject0 = Utils.mockEntity();
    }

    @Test
    public void shouldTestInterfaceOWLEquivalentClassesAxiom() throws Exception {
        OWLEquivalentClassesAxiom testSubject0 = mock(OWLEquivalentClassesAxiom.class);
        OWLEquivalentClassesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLEquivalentClassesAxiom> result1 = testSubject0
                .asPairwiseAxioms();
        boolean result2 = testSubject0.containsNamedEquivalentClass();
        Set<OWLClass> result3 = testSubject0.getNamedClasses();
        boolean result4 = testSubject0.containsOWLNothing();
        boolean result5 = testSubject0.containsOWLThing();
        boolean result6 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result7 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result8 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result9 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
        Set<OWLAnnotation> result10 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result11 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result12 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result13 = testSubject0.getAxiomWithoutAnnotations();
        boolean result15 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result16 = testSubject0.isLogicalAxiom();
        boolean result17 = testSubject0.isAnnotationAxiom();
        boolean result18 = testSubject0.isAnnotated();
        AxiomType<?> result19 = testSubject0.getAxiomType();
        boolean result20 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result21 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
        Set<OWLSubClassOfAxiom> result35 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLEquivalentDataPropertiesAxiom()
            throws Exception {
        OWLEquivalentDataPropertiesAxiom testSubject0 = mock(OWLEquivalentDataPropertiesAxiom.class);
        OWLEquivalentDataPropertiesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLSubDataPropertyOfAxiom> result1 = testSubject0
                .asSubDataPropertyOfAxioms();
        Set<OWLDataPropertyExpression> result2 = testSubject0.getProperties();
        Set<OWLDataPropertyExpression> result3 = testSubject0
                .getPropertiesMinus(mock(OWLDataPropertyExpression.class));
        Set<OWLAnnotation> result4 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result5 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result6 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result7 = testSubject0.getAxiomWithoutAnnotations();
        boolean result9 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        boolean result12 = testSubject0.isAnnotated();
        AxiomType<?> result13 = testSubject0.getAxiomType();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLEquivalentObjectPropertiesAxiom()
            throws Exception {
        OWLEquivalentObjectPropertiesAxiom testSubject0 = mock(OWLEquivalentObjectPropertiesAxiom.class);
        OWLEquivalentObjectPropertiesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLSubObjectPropertyOfAxiom> result1 = testSubject0
                .asSubObjectPropertyOfAxioms();
        Set<OWLObjectPropertyExpression> result2 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
        Set<OWLAnnotation> result4 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result5 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result6 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result7 = testSubject0.getAxiomWithoutAnnotations();
        boolean result9 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        boolean result12 = testSubject0.isAnnotated();
        AxiomType<?> result13 = testSubject0.getAxiomType();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLException() throws Exception {
        OWLException testSubject0 = new OWLException("") {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLException testSubject1 = new OWLException("", new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        OWLException testSubject2 = new OWLException(new RuntimeException()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLFacetRestriction() throws Exception {
        OWLFacetRestriction testSubject0 = mock(OWLFacetRestriction.class);
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockData());
        OWLFacet result1 = testSubject0.getFacet();
        OWLLiteral result2 = testSubject0.getFacetValue();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result12 = testSubject0.isTopEntity();
        boolean result13 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalDataPropertyAxiom()
            throws Exception {
        OWLFunctionalDataPropertyAxiom testSubject0 = mock(OWLFunctionalDataPropertyAxiom.class);
        OWLFunctionalDataPropertyAxiom result0 = testSubject0
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLDataPropertyExpression result26 = testSubject0.getProperty();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalObjectPropertyAxiom()
            throws Exception {
        OWLFunctionalObjectPropertyAxiom testSubject0 = mock(OWLFunctionalObjectPropertyAxiom.class);
        OWLFunctionalObjectPropertyAxiom result0 = testSubject0
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLHasKeyAxiom() throws Exception {
        OWLHasKeyAxiom testSubject0 = mock(OWLHasKeyAxiom.class);
        OWLHasKeyAxiom result0 = testSubject0.getAxiomWithoutAnnotations();
        OWLClassExpression result1 = testSubject0.getClassExpression();
        Set<OWLPropertyExpression<?, ?>> result2 = testSubject0
                .getPropertyExpressions();
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getObjectPropertyExpressions();
        Set<OWLDataPropertyExpression> result4 = testSubject0
                .getDataPropertyExpressions();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLHasValueRestriction() throws Exception {
        OWLHasValueRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> testSubject0 = mock(OWLHasValueRestriction.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLImportsDeclaration() throws Exception {
        OWLImportsDeclaration testSubject0 = mock(OWLImportsDeclaration.class);
        IRI result0 = testSubject0.getIRI();
        URI result1 = testSubject0.getURI();
    }

    @Test
    public void shouldTestInterfaceOWLIndividual() throws Exception {
        OWLIndividual testSubject0 = mock(OWLIndividual.class);
        Object result0 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLNamedIndividual result2 = testSubject0.asOWLNamedIndividual();
        }
        String result3 = testSubject0.toStringID();
        boolean result4 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result5 = testSubject0
                    .asOWLAnonymousIndividual();
        }
        Set<OWLClassExpression> result6 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result7 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result8 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result9 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result10 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result11 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result12 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result13 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result14 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result15 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result16 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result17 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result18 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLIndividualAxiom() throws Exception {
        OWLIndividualAxiom testSubject0 = mock(OWLIndividualAxiom.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLIndividualVisitor() throws Exception {
        OWLIndividualVisitor testSubject0 = mock(OWLIndividualVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLIndividualVisitorEx() throws Exception {
        OWLIndividualVisitorEx<OWLObject> testSubject0 = Utils.mockIndividual();
    }

    @Test
    public void shouldTestInterfaceOWLInverseFunctionalObjectPropertyAxiom()
            throws Exception {
        OWLInverseFunctionalObjectPropertyAxiom testSubject0 = mock(OWLInverseFunctionalObjectPropertyAxiom.class);
        OWLInverseFunctionalObjectPropertyAxiom result0 = testSubject0
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLInverseObjectPropertiesAxiom()
            throws Exception {
        OWLInverseObjectPropertiesAxiom testSubject0 = mock(OWLInverseObjectPropertiesAxiom.class);
        OWLInverseObjectPropertiesAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLSubObjectPropertyOfAxiom> result1 = testSubject0
                .asSubObjectPropertyOfAxioms();
        OWLObjectPropertyExpression result2 = testSubject0.getFirstProperty();
        OWLObjectPropertyExpression result3 = testSubject0.getSecondProperty();
        Set<OWLObjectPropertyExpression> result4 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result5 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLIrreflexiveObjectPropertyAxiom()
            throws Exception {
        OWLIrreflexiveObjectPropertyAxiom testSubject0 = mock(OWLIrreflexiveObjectPropertyAxiom.class);
        OWLIrreflexiveObjectPropertyAxiom result0 = testSubject0
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLLiteral() throws Exception {
        OWLLiteral testSubject0 = mock(OWLLiteral.class);
        boolean result0 = testSubject0.parseBoolean();
        float result1 = testSubject0.parseFloat();
        double result2 = testSubject0.parseDouble();
        Object result3 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataVisitor.class));
        String result4 = testSubject0.getLiteral();
        boolean result5 = testSubject0.isRDFPlainLiteral();
        OWLDatatype result6 = testSubject0.getDatatype();
        boolean result7 = testSubject0.hasLang("");
        boolean result8 = testSubject0.hasLang();
        String result9 = testSubject0.getLang();
        boolean result10 = testSubject0.isInteger();
        int result11 = testSubject0.parseInteger();
        boolean result12 = testSubject0.isBoolean();
        boolean result13 = testSubject0.isDouble();
        boolean result14 = testSubject0.isFloat();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockAnnotationValue());
    }

    @Test
    public void shouldTestInterfaceOWLLogicalAxiom() throws Exception {
        OWLLogicalAxiom testSubject0 = mock(OWLLogicalAxiom.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLLogicalAxiomVisitor() throws Exception {
        OWLLogicalAxiomVisitor testSubject0 = mock(OWLLogicalAxiomVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLLogicalAxiomVisitorEx() throws Exception {
        OWLLogicalAxiomVisitorEx<OWLObject> testSubject0 = Utils
                .mockLogicalAxiom();
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
        Object result20 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result24 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result25 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result27 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLLogicalEntity() throws Exception {
        OWLLogicalEntity testSubject0 = mock(OWLLogicalEntity.class);
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockEntity());
        boolean result3 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result4 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result5 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result6 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result7 = testSubject0.isBuiltIn();
        EntityType<?> result8 = testSubject0.getEntityType();
        boolean result10 = !testSubject0.isOWLClass();
        if (testSubject0.isOWLClass()) {
            OWLClass result11 = testSubject0.asOWLClass();
        }
        boolean result12 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result13 = testSubject0.asOWLObjectProperty();
        }
        boolean result14 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result15 = testSubject0.asOWLDataProperty();
        }
        boolean result16 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result17 = testSubject0.asOWLNamedIndividual();
        }
        boolean result18 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result19 = testSubject0.asOWLDatatype();
        }
        boolean result20 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result21 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result22 = testSubject0.toStringID();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result35 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLMutableOntology() throws Exception {
        OWLMutableOntology testSubject0 = mock(OWLMutableOntology.class);
        List<OWLOntologyChange> result0 = testSubject0.applyChanges(Utils
                .mockList(mock(OWLOntologyChange.class)));
        List<OWLOntologyChange> result1 = testSubject0
                .applyChange(mock(OWLOntologyChange.class));
        boolean result2 = testSubject0.isEmpty();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        Set<OWLEntity> result4 = testSubject0.getSignature(false);
        Set<OWLEntity> result5 = testSubject0.getSignature();
        OWLOntologyManager result6 = testSubject0.getOWLOntologyManager();
        Set<OWLClass> result7 = testSubject0.getClassesInSignature(false);
        Set<OWLClass> result8 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result9 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLDataProperty> result10 = testSubject0
                .getDataPropertiesInSignature(false);
        Set<OWLObjectProperty> result11 = testSubject0
                .getObjectPropertiesInSignature(false);
        Set<OWLObjectProperty> result12 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result13 = testSubject0
                .getIndividualsInSignature();
        Set<OWLNamedIndividual> result14 = testSubject0
                .getIndividualsInSignature(false);
        Set<OWLDatatype> result15 = testSubject0.getDatatypesInSignature(false);
        Set<OWLDatatype> result16 = testSubject0.getDatatypesInSignature();
        OWLOntologyID result17 = testSubject0.getOntologyID();
        boolean result18 = testSubject0.isAnonymous();
        Set<IRI> result19 = testSubject0.getDirectImportsDocuments();
        Set<OWLOntology> result20 = testSubject0.getDirectImports();
        Set<OWLOntology> result21 = testSubject0.getImports();
        Set<OWLOntology> result22 = testSubject0.getImportsClosure();
        Set<OWLImportsDeclaration> result23 = testSubject0
                .getImportsDeclarations();
        Set<OWLDataPropertyAxiom> result24 = testSubject0
                .getAxioms(mock(OWLDataProperty.class));
        Set<OWLObjectPropertyAxiom> result25 = testSubject0.getAxioms(Utils
                .mockObjectProperty());
        Set<OWLClassAxiom> result26 = testSubject0
                .getAxioms(mock(OWLClass.class));
        Set<OWLAxiom> result27 = testSubject0.getAxioms();
        Set<OWLAnnotationAssertionAxiom> result28 = testSubject0
                .getAxioms(Utils.mockAxiomType());
        Set<OWLAnnotationAssertionAxiom> result29 = testSubject0.getAxioms(
                Utils.mockAxiomType(), false);
        Set<OWLDatatypeDefinitionAxiom> result30 = testSubject0
                .getAxioms(mock(OWLDatatype.class));
        Set<OWLAnnotationAxiom> result31 = testSubject0
                .getAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLIndividualAxiom> result32 = testSubject0
                .getAxioms(mock(OWLIndividual.class));
        int result33 = testSubject0.getAxiomCount(Utils.mockAxiomType(), false);
        int result34 = testSubject0.getAxiomCount();
        int result35 = testSubject0.getAxiomCount(Utils.mockAxiomType());
        Set<OWLLogicalAxiom> result36 = testSubject0.getLogicalAxioms();
        int result37 = testSubject0.getLogicalAxiomCount();
        Set<OWLAxiom> result38 = testSubject0.getTBoxAxioms(false);
        Set<OWLAxiom> result39 = testSubject0.getABoxAxioms(false);
        Set<OWLAxiom> result40 = testSubject0.getRBoxAxioms(false);
        boolean result41 = testSubject0.containsAxiom(mock(OWLAxiom.class));
        boolean result42 = testSubject0.containsAxiom(mock(OWLAxiom.class),
                false);
        boolean result43 = testSubject0.containsAxiomIgnoreAnnotations(
                mock(OWLAxiom.class), false);
        boolean result44 = testSubject0
                .containsAxiomIgnoreAnnotations(mock(OWLAxiom.class));
        Set<OWLAxiom> result45 = testSubject0.getAxiomsIgnoreAnnotations(
                mock(OWLAxiom.class), false);
        Set<OWLAxiom> result46 = testSubject0
                .getAxiomsIgnoreAnnotations(mock(OWLAxiom.class));
        Set<OWLClassAxiom> result47 = testSubject0.getGeneralClassAxioms();
        Set<OWLAnonymousIndividual> result48 = testSubject0
                .getReferencedAnonymousIndividuals();
        Set<OWLAnnotationProperty> result49 = testSubject0
                .getAnnotationPropertiesInSignature();
        Set<OWLAxiom> result50 = testSubject0.getReferencingAxioms(Utils
                .mockOWLEntity());
        Set<OWLAxiom> result51 = testSubject0.getReferencingAxioms(
                Utils.mockOWLEntity(), false);
        Set<OWLAxiom> result52 = testSubject0
                .getReferencingAxioms(mock(OWLAnonymousIndividual.class));
        boolean result53 = testSubject0.containsEntityInSignature(
                Utils.mockOWLEntity(), false);
        boolean result54 = testSubject0.containsEntityInSignature(Utils
                .mockOWLEntity());
        boolean result55 = testSubject0.containsEntityInSignature(
                IRI("urn:aFake"), false);
        boolean result56 = testSubject0
                .containsEntityInSignature(IRI("urn:aFake"));
        boolean result57 = testSubject0.isDeclared(Utils.mockOWLEntity());
        boolean result58 = testSubject0
                .isDeclared(Utils.mockOWLEntity(), false);
        boolean result59 = testSubject0.containsClassInSignature(
                IRI("urn:aFake"), false);
        boolean result60 = testSubject0
                .containsClassInSignature(IRI("urn:aFake"));
        boolean result61 = testSubject0.containsObjectPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result62 = testSubject0
                .containsObjectPropertyInSignature(IRI("urn:aFake"));
        boolean result63 = testSubject0
                .containsDataPropertyInSignature(IRI("urn:aFake"));
        boolean result64 = testSubject0.containsDataPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result65 = testSubject0
                .containsAnnotationPropertyInSignature(IRI("urn:aFake"));
        boolean result66 = testSubject0.containsAnnotationPropertyInSignature(
                IRI("urn:aFake"), false);
        boolean result67 = testSubject0
                .containsIndividualInSignature(IRI("urn:aFake"));
        boolean result68 = testSubject0.containsIndividualInSignature(
                IRI("urn:aFake"), false);
        boolean result69 = testSubject0
                .containsDatatypeInSignature(IRI("urn:aFake"));
        boolean result70 = testSubject0.containsDatatypeInSignature(
                IRI("urn:aFake"), false);
        Set<OWLEntity> result71 = testSubject0.getEntitiesInSignature(
                IRI("urn:aFake"), false);
        Set<OWLEntity> result72 = testSubject0
                .getEntitiesInSignature(IRI("urn:aFake"));
        Set<OWLSubAnnotationPropertyOfAxiom> result73 = testSubject0
                .getSubAnnotationPropertyOfAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLAnnotationPropertyDomainAxiom> result74 = testSubject0
                .getAnnotationPropertyDomainAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLAnnotationPropertyRangeAxiom> result75 = testSubject0
                .getAnnotationPropertyRangeAxioms(mock(OWLAnnotationProperty.class));
        Set<OWLDeclarationAxiom> result76 = testSubject0
                .getDeclarationAxioms(Utils.mockOWLEntity());
        Set<OWLAnnotationAssertionAxiom> result77 = testSubject0
                .getAnnotationAssertionAxioms(mock(OWLAnnotationSubject.class));
        Set<OWLSubClassOfAxiom> result78 = testSubject0
                .getSubClassAxiomsForSubClass(mock(OWLClass.class));
        Set<OWLSubClassOfAxiom> result79 = testSubject0
                .getSubClassAxiomsForSuperClass(mock(OWLClass.class));
        Set<OWLEquivalentClassesAxiom> result80 = testSubject0
                .getEquivalentClassesAxioms(mock(OWLClass.class));
        Set<OWLDisjointClassesAxiom> result81 = testSubject0
                .getDisjointClassesAxioms(mock(OWLClass.class));
        Set<OWLDisjointUnionAxiom> result82 = testSubject0
                .getDisjointUnionAxioms(mock(OWLClass.class));
        Set<OWLHasKeyAxiom> result83 = testSubject0
                .getHasKeyAxioms(mock(OWLClass.class));
        Set<OWLSubObjectPropertyOfAxiom> result84 = testSubject0
                .getObjectSubPropertyAxiomsForSubProperty(Utils
                        .mockObjectProperty());
        Set<OWLSubObjectPropertyOfAxiom> result85 = testSubject0
                .getObjectSubPropertyAxiomsForSuperProperty(Utils
                        .mockObjectProperty());
        Set<OWLObjectPropertyDomainAxiom> result86 = testSubject0
                .getObjectPropertyDomainAxioms(Utils.mockObjectProperty());
        Set<OWLObjectPropertyRangeAxiom> result87 = testSubject0
                .getObjectPropertyRangeAxioms(Utils.mockObjectProperty());
        Set<OWLInverseObjectPropertiesAxiom> result88 = testSubject0
                .getInverseObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLEquivalentObjectPropertiesAxiom> result89 = testSubject0
                .getEquivalentObjectPropertiesAxioms(Utils.mockObjectProperty());
        Set<OWLDisjointObjectPropertiesAxiom> result90 = testSubject0
                .getDisjointObjectPropertiesAxioms(Utils.mockObjectProperty());
        Set<OWLFunctionalObjectPropertyAxiom> result91 = testSubject0
                .getFunctionalObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLInverseFunctionalObjectPropertyAxiom> result92 = testSubject0
                .getInverseFunctionalObjectPropertyAxioms(Utils
                        .mockObjectProperty());
        Set<OWLSymmetricObjectPropertyAxiom> result93 = testSubject0
                .getSymmetricObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLAsymmetricObjectPropertyAxiom> result94 = testSubject0
                .getAsymmetricObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLReflexiveObjectPropertyAxiom> result95 = testSubject0
                .getReflexiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLIrreflexiveObjectPropertyAxiom> result96 = testSubject0
                .getIrreflexiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLTransitiveObjectPropertyAxiom> result97 = testSubject0
                .getTransitiveObjectPropertyAxioms(Utils.mockObjectProperty());
        Set<OWLSubDataPropertyOfAxiom> result98 = testSubject0
                .getDataSubPropertyAxiomsForSubProperty(mock(OWLDataProperty.class));
        Set<OWLSubDataPropertyOfAxiom> result99 = testSubject0
                .getDataSubPropertyAxiomsForSuperProperty(mock(OWLDataPropertyExpression.class));
        Set<OWLDataPropertyDomainAxiom> result100 = testSubject0
                .getDataPropertyDomainAxioms(mock(OWLDataProperty.class));
        Set<OWLDataPropertyRangeAxiom> result101 = testSubject0
                .getDataPropertyRangeAxioms(mock(OWLDataProperty.class));
        Set<OWLEquivalentDataPropertiesAxiom> result102 = testSubject0
                .getEquivalentDataPropertiesAxioms(mock(OWLDataProperty.class));
        Set<OWLDisjointDataPropertiesAxiom> result103 = testSubject0
                .getDisjointDataPropertiesAxioms(mock(OWLDataProperty.class));
        Set<OWLFunctionalDataPropertyAxiom> result104 = testSubject0
                .getFunctionalDataPropertyAxioms(mock(OWLDataPropertyExpression.class));
        Set<OWLClassAssertionAxiom> result105 = testSubject0
                .getClassAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLClassAssertionAxiom> result106 = testSubject0
                .getClassAssertionAxioms(Utils.mockAnonClass());
        Set<OWLDataPropertyAssertionAxiom> result107 = testSubject0
                .getDataPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLObjectPropertyAssertionAxiom> result108 = testSubject0
                .getObjectPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLNegativeObjectPropertyAssertionAxiom> result109 = testSubject0
                .getNegativeObjectPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLNegativeDataPropertyAssertionAxiom> result110 = testSubject0
                .getNegativeDataPropertyAssertionAxioms(mock(OWLIndividual.class));
        Set<OWLSameIndividualAxiom> result111 = testSubject0
                .getSameIndividualAxioms(mock(OWLIndividual.class));
        Set<OWLDifferentIndividualsAxiom> result112 = testSubject0
                .getDifferentIndividualAxioms(mock(OWLIndividual.class));
        Set<OWLDatatypeDefinitionAxiom> result113 = testSubject0
                .getDatatypeDefinitions(mock(OWLDatatype.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result114 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result115 = testSubject0
                .getAnonymousIndividuals();
        boolean result117 = testSubject0.isTopEntity();
        boolean result118 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNamedIndividual() throws Exception {
        OWLNamedIndividual testSubject0 = mock(OWLNamedIndividual.class);
        Object result0 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result2 = testSubject0.asOWLNamedIndividual();
        }
        String result3 = testSubject0.toStringID();
        boolean result4 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result5 = testSubject0
                    .asOWLAnonymousIndividual();
        }
        Set<OWLClassExpression> result6 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result7 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result8 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result9 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result10 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result11 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result12 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result13 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result14 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result15 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result16 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result17 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result18 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
        Set<OWLAnnotation> result31 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result32 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result33 = testSubject0.accept(Utils.mockEntity());
        boolean result34 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result35 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result36 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result37 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result38 = testSubject0.isBuiltIn();
        EntityType<?> result39 = testSubject0.getEntityType();
        boolean result41 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result42 = testSubject0.asOWLClass();
            }
        }
        boolean result43 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result44 = testSubject0.asOWLObjectProperty();
        }
        boolean result45 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result46 = testSubject0.asOWLDataProperty();
        }
        boolean result47 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result48 = testSubject0.asOWLNamedIndividual();
        }
        boolean result49 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result19 = testSubject0.asOWLDatatype();
        }
        boolean result20 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result52 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result21 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result22 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLNamedObject() throws Exception {
        OWLNamedObject testSubject0 = mock(OWLNamedObject.class);
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result0 = testSubject0.getIRI();
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result10 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNamedObjectVisitor() throws Exception {
        OWLNamedObjectVisitor testSubject0 = mock(OWLNamedObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLNamedObjectVisitorEx() throws Exception {
        OWLNamedObjectVisitorEx<OWLObject> testSubject0 = Utils
                .mockNamedObject();
    }

    @Test
    public void shouldTestInterfaceOWLNaryAxiom() throws Exception {
        OWLNaryAxiom testSubject0 = mock(OWLNaryAxiom.class);
        Set<? extends OWLNaryAxiom> result0 = testSubject0.asPairwiseAxioms();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNaryBooleanClassExpression()
            throws Exception {
        OWLNaryBooleanClassExpression testSubject0 = mock(OWLNaryBooleanClassExpression.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNaryClassAxiom() throws Exception {
        OWLNaryClassAxiom testSubject0 = mock(OWLNaryClassAxiom.class);
        boolean result0 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result1 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result2 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result3 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
        Set<OWLAnnotation> result4 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result5 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result6 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result7 = testSubject0.getAxiomWithoutAnnotations();
        boolean result9 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        boolean result12 = testSubject0.isAnnotated();
        AxiomType<?> result13 = testSubject0.getAxiomType();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
        Set<? extends OWLNaryAxiom> result29 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result30 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLNaryDataRange() throws Exception {
        OWLNaryDataRange testSubject0 = mock(OWLNaryDataRange.class);
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNaryIndividualAxiom() throws Exception {
        OWLNaryIndividualAxiom testSubject0 = mock(OWLNaryIndividualAxiom.class);
        Set<OWLIndividual> result0 = testSubject0.getIndividuals();
        List<OWLIndividual> result1 = testSubject0.getIndividualsAsList();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result3 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result5 = testSubject0.getAxiomWithoutAnnotations();
        boolean result7 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
        boolean result10 = testSubject0.isAnnotated();
        AxiomType<?> result11 = testSubject0.getAxiomType();
        boolean result12 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result13 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        Set<? extends OWLNaryAxiom> result27 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result28 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLNaryPropertyAxiom() throws Exception {
        OWLNaryPropertyAxiom<OWLObjectProperty> testSubject0 = mock(OWLNaryPropertyAxiom.class);
        Set<OWLObjectProperty> result0 = testSubject0.getProperties();
        Set<OWLObjectProperty> result1 = testSubject0
                .getPropertiesMinus(mock(OWLObjectProperty.class));
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result3 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result5 = testSubject0.getAxiomWithoutAnnotations();
        boolean result7 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
        boolean result10 = testSubject0.isAnnotated();
        AxiomType<?> result11 = testSubject0.getAxiomType();
        boolean result12 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result13 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result50 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result51 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result53 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result54 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result55 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result56 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result57 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result58 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLNegativeDataPropertyAssertionAxiom()
            throws Exception {
        OWLNegativeDataPropertyAssertionAxiom testSubject0 = mock(OWLNegativeDataPropertyAssertionAxiom.class);
        OWLNegativeDataPropertyAssertionAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result1 = testSubject0.containsAnonymousIndividuals();
        OWLDataPropertyExpression result2 = testSubject0.getProperty();
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
        Set<OWLEntity> result18 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result19 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result20 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result21 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result22 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result23 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result24 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result25 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result30 = testSubject0.asOWLSubClassOfAxiom();
    }
}
