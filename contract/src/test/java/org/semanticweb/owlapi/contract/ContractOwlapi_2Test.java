package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.DataRangeType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitor;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLIndividualVisitorEx;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternals;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyCharacteristicAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeDefinitionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDeclarationAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDifferentIndividualsAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointClassesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointDataPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDisjointUnionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEntityCollectionContainerCollector;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentClassesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentDataPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLEquivalentObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFacetRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFunctionalDataPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLFunctionalObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLHasKeyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLImportsDeclarationImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLIndividualAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLIndividualImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLIndividualRelationshipAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLInverseFunctionalObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLInverseObjectPropertiesAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLIrreflexiveObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplBoolean;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplDouble;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplFloat;
import uk.ac.manchester.cs.owl.owlapi.OWLLiteralImplInteger;
import uk.ac.manchester.cs.owl.owlapi.OWLLogicalAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapi_2Test {

    @Test
    public void shouldTestInterfaceOWLDataFactoryInternals() throws Exception {
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
    public void shouldTestOWLDataFactoryInternalsImpl() throws Exception {
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
        OWLDatatype mock = mock(OWLDatatype.class);
        when(mock.getIRI()).thenReturn(OWL2Datatype.XSD_STRING.getIRI());
        OWLLiteral result10 = testSubject0.getOWLLiteral("", mock);
        testSubject0.purge();
        OWLDatatype result11 = testSubject0.getTopDatatype();
        OWLDatatype result12 = testSubject0.getRDFPlainLiteral();
        OWLDatatype result13 = testSubject0.getIntegerOWLDatatype();
        OWLDatatype result14 = testSubject0.getFloatOWLDatatype();
        OWLDatatype result15 = testSubject0.getDoubleOWLDatatype();
        OWLDatatype result16 = testSubject0.getBooleanOWLDatatype();
        OWLLiteral result17 = testSubject0.getOWLLiteral("", "");
        OWLLiteral result18 = testSubject0.getOWLLiteral(false);
        String result19 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLDataHasValueImpl() throws Exception {
        OWLDataHasValueImpl testSubject0 = new OWLDataHasValueImpl(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLClassExpression result5 = testSubject0.asSomeValuesFrom();
        OWLObject result6 = testSubject0.getValue();
        OWLDataPropertyExpression result7 = testSubject0.getProperty();
        boolean result8 = testSubject0.isClassExpressionLiteral();
        boolean result9 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result10 = testSubject0.asOWLClass();
            }
        }
        boolean result12 = testSubject0.isOWLThing();
        boolean result13 = testSubject0.isOWLNothing();
        OWLClassExpression result15 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result16 = testSubject0.asConjunctSet();
        boolean result17 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result18 = testSubject0.asDisjunctSet();
        String result19 = testSubject0.toString();
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
    public void shouldTestOWLDataIntersectionOfImpl() throws Exception {
        OWLDataIntersectionOfImpl testSubject0 = new OWLDataIntersectionOfImpl(
                Utils.mockSet(mock(OWLDatatype.class)));
        Object result0 = testSubject0.accept(Utils.mockDataRange());
        Object result1 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLDataVisitor.class));
        DataRangeType result3 = testSubject0.getDataRangeType();
        if (testSubject0.isDatatype()) {
            OWLDatatype result4 = testSubject0.asOWLDatatype();
        }
        boolean result5 = testSubject0.isDatatype();
        boolean result6 = testSubject0.isTopDatatype();
        Set<OWLDataRange> result7 = testSubject0.getOperands();
        String result8 = testSubject0.toString();
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
    public void shouldTestOWLDataMaxCardinalityImpl() throws Exception {
        OWLDataMaxCardinalityImpl testSubject0 = new OWLDataMaxCardinalityImpl(
                mock(OWLDataPropertyExpression.class), 0,
                mock(OWLDataRange.class));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isQualified();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        int result6 = testSubject0.getCardinality();
        OWLPropertyRange result7 = testSubject0.getFiller();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
        boolean result9 = testSubject0.isClassExpressionLiteral();
        boolean result10 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result11 = testSubject0.asOWLClass();
            }
        }
        boolean result13 = testSubject0.isOWLThing();
        boolean result14 = testSubject0.isOWLNothing();
        OWLClassExpression result16 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result17 = testSubject0.asConjunctSet();
        boolean result18 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result19 = testSubject0.asDisjunctSet();
        String result20 = testSubject0.toString();
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
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDataMinCardinalityImpl() throws Exception {
        OWLDataMinCardinalityImpl testSubject0 = new OWLDataMinCardinalityImpl(
                mock(OWLDataPropertyExpression.class), 0,
                mock(OWLDataRange.class));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isQualified();
        boolean result4 = testSubject0.isObjectRestriction();
        boolean result5 = testSubject0.isDataRestriction();
        int result6 = testSubject0.getCardinality();
        OWLPropertyRange result7 = testSubject0.getFiller();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
        boolean result9 = testSubject0.isClassExpressionLiteral();
        boolean result10 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result11 = testSubject0.asOWLClass();
            }
        }
        boolean result13 = testSubject0.isOWLThing();
        boolean result14 = testSubject0.isOWLNothing();
        OWLClassExpression result16 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result17 = testSubject0.asConjunctSet();
        boolean result18 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result19 = testSubject0.asDisjunctSet();
        String result20 = testSubject0.toString();
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
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDataOneOfImpl() throws Exception {
        OWLDataOneOfImpl testSubject0 = new OWLDataOneOfImpl(
                Utils.mockSet(mock(OWLLiteral.class)));
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
        Set<OWLLiteral> result7 = testSubject0.getValues();
        String result8 = testSubject0.toString();
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
    public void shouldTestOWLDataPropertyAssertionAxiomImpl() throws Exception {
        OWLDataPropertyAssertionAxiomImpl testSubject0 = new OWLDataPropertyAssertionAxiomImpl(
                mock(OWLIndividual.class),
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDataPropertyAssertionAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
        OWLPropertyAssertionObject result9 = testSubject0.getObject();
        OWLIndividual result10 = testSubject0.getSubject();
        boolean result11 = testSubject0.isLogicalAxiom();
        boolean result12 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result13 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result14 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result15 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result16 = testSubject0.isAnnotated();
        boolean result17 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result18 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result20 = testSubject0.toString();
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
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDataPropertyCharacteristicAxiomImpl()
            throws Exception {
        OWLDataPropertyCharacteristicAxiomImpl testSubject0 = new OWLDataPropertyCharacteristicAxiomImpl(
                mock(OWLDataPropertyExpression.class),
                Utils.mockCollection(mock(OWLAnnotation.class))) {

            private static final long serialVersionUID = 30406L;

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
        OWLDataPropertyExpression result0 = testSubject0.getProperty();
        OWLDataPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isLogicalAxiom();
        boolean result3 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result4 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result5 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result6 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result7 = testSubject0.isAnnotated();
        boolean result8 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result9 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result11 = testSubject0.toString();
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
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLDataPropertyDomainAxiomImpl() throws Exception {
        OWLDataPropertyDomainAxiomImpl testSubject0 = new OWLDataPropertyDomainAxiomImpl(
                mock(OWLDataPropertyExpression.class), Utils.mockAnonClass(),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDataPropertyDomainAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result5 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result6 = testSubject0.asOWLSubClassOfAxiom();
        OWLClassExpression result7 = testSubject0.getDomain();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
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
        String result18 = testSubject0.toString();
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
    public void shouldTestOWLDataPropertyImpl() throws Exception {
        OWLDataPropertyImpl testSubject0 = new OWLDataPropertyImpl(
                IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Object result2 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLEntityVisitor.class));
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockEntity());
        Object result4 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result5 = testSubject0.isType(EntityType.CLASS);
        boolean result6 = testSubject0.isTopEntity();
        boolean result7 = testSubject0.isBottomEntity();
        boolean result8 = testSubject0.isAnonymous();
        Set<OWLAxiom> result9 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result10 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result11 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        IRI result12 = testSubject0.getIRI();
        boolean result13 = testSubject0.isBuiltIn();
        EntityType<?> result14 = testSubject0.getEntityType();
        boolean result16 = !testSubject0.isAnonymous();
        if (testSubject0.isOWLClass()) {
            OWLClass result17 = testSubject0.asOWLClass();
        }
        boolean result18 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result19 = testSubject0.asOWLObjectProperty();
        }
        boolean result20 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result21 = testSubject0.asOWLDataProperty();
        }
        boolean result22 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result23 = testSubject0.asOWLNamedIndividual();
        }
        boolean result24 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result25 = testSubject0.asOWLDatatype();
        }
        boolean result26 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result27 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result28 = testSubject0.toStringID();
        boolean result29 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result30 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result31 = testSubject0.isDataPropertyExpression();
        boolean result32 = testSubject0.isObjectPropertyExpression();
        boolean result33 = testSubject0.isOWLTopObjectProperty();
        boolean result34 = testSubject0.isOWLBottomObjectProperty();
        boolean result35 = testSubject0.isOWLTopDataProperty();
        boolean result36 = testSubject0.isOWLBottomDataProperty();
        Set<OWLDataPropertyExpression> result37 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result38 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result39 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result40 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result41 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result42 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLDataRange> result43 = testSubject0.getRanges(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLDataRange> result44 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLDataPropertyExpression> result45 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result46 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLDataPropertyExpression> result47 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLDataPropertyExpression> result48 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        String result49 = testSubject0.toString();
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
    }

    @Test
    public void shouldTestOWLDataPropertyRangeAxiomImpl() throws Exception {
        OWLDataPropertyRangeAxiomImpl testSubject0 = new OWLDataPropertyRangeAxiomImpl(
                mock(OWLDataPropertyExpression.class),
                mock(OWLDataRange.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDataPropertyRangeAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result5 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result6 = testSubject0.asOWLSubClassOfAxiom();
        OWLPropertyRange result7 = testSubject0.getRange();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
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
        String result18 = testSubject0.toString();
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
    public void shouldTestOWLDataSomeValuesFromImpl() throws Exception {
        OWLDataSomeValuesFromImpl testSubject0 = new OWLDataSomeValuesFromImpl(
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result9 = testSubject0.asOWLClass();
            }
        }
        boolean result11 = testSubject0.isOWLThing();
        boolean result12 = testSubject0.isOWLNothing();
        OWLClassExpression result14 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result15 = testSubject0.asConjunctSet();
        boolean result16 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result17 = testSubject0.asDisjunctSet();
        String result18 = testSubject0.toString();
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
    public void shouldTestOWLDatatypeDefinitionAxiomImpl() throws Exception {
        OWLDatatypeDefinitionAxiomImpl testSubject0 = new OWLDatatypeDefinitionAxiomImpl(
                mock(OWLDatatype.class), mock(OWLDataRange.class),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLDatatype result2 = testSubject0.getDatatype();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result8 = testSubject0.getAxiomType();
        OWLDataRange result9 = testSubject0.getDataRange();
        Set<OWLAnnotation> result10 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result11 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result12 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result13 = testSubject0.isAnnotated();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result17 = testSubject0.toString();
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
    }

    @Test
    public void shouldTestOWLDatatypeImpl() throws Exception {
        OWLDatatypeImpl testSubject0 = new OWLDatatypeImpl(IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockDataRange());
        Object result3 = testSubject0.accept(Utils.mockObject());
        Object result4 = testSubject0.accept(Utils.mockData());
        Object result5 = testSubject0.accept(Utils.mockEntity());
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLDataVisitor.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        boolean result6 = testSubject0.isType(EntityType.CLASS);
        boolean result7 = testSubject0.isTopEntity();
        boolean result8 = testSubject0.isBottomEntity();
        Set<OWLAxiom> result9 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAxiom> result10 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationAssertionAxiom> result11 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        IRI result12 = testSubject0.getIRI();
        boolean result13 = testSubject0.isBuiltIn();
        EntityType<?> result14 = testSubject0.getEntityType();
        boolean result16 = !testSubject0.isOWLClass();
        if (testSubject0.isOWLClass()) {
            OWLClass result17 = testSubject0.asOWLClass();
        }
        boolean result18 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result19 = testSubject0.asOWLObjectProperty();
        }
        boolean result20 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result21 = testSubject0.asOWLDataProperty();
        }
        boolean result22 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result23 = testSubject0.asOWLNamedIndividual();
        }
        boolean result24 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result25 = testSubject0.asOWLDatatype();
        }
        boolean result26 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result27 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result28 = testSubject0.toStringID();
        boolean result29 = testSubject0.isRDFPlainLiteral();
        boolean result30 = testSubject0.isInteger();
        boolean result31 = testSubject0.isBoolean();
        boolean result32 = testSubject0.isDouble();
        boolean result33 = testSubject0.isFloat();
        boolean result35 = testSubject0.isString();
        boolean result36 = testSubject0.isDatatype();
        boolean result37 = testSubject0.isTopDatatype();
        DataRangeType result38 = testSubject0.getDataRangeType();
        String result39 = testSubject0.toString();
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
    }

    @Test
    public void shouldTestOWLDatatypeRestrictionImpl() throws Exception {
        OWLDatatypeRestrictionImpl testSubject0 = new OWLDatatypeRestrictionImpl(
                mock(OWLDatatype.class),
                Utils.mockSet(mock(OWLFacetRestriction.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockObject());
        if (testSubject0.isDatatype()) {
            OWLDatatype result3 = testSubject0.asOWLDatatype();
        }
        OWLDatatype result4 = testSubject0.getDatatype();
        boolean result5 = testSubject0.isDatatype();
        boolean result6 = testSubject0.isTopDatatype();
        DataRangeType result7 = testSubject0.getDataRangeType();
        Set<OWLFacetRestriction> result8 = testSubject0.getFacetRestrictions();
        String result9 = testSubject0.toString();
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
    public void shouldTestOWLDataUnionOfImpl() throws Exception {
        OWLDataUnionOfImpl testSubject0 = new OWLDataUnionOfImpl(
                Utils.mockSet(mock(OWLDatatype.class)));
        Object result0 = testSubject0.accept(Utils.mockDataRange());
        Object result1 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLDataVisitor.class));
        DataRangeType result3 = testSubject0.getDataRangeType();
        if (testSubject0.isDatatype()) {
            OWLDatatype result4 = testSubject0.asOWLDatatype();
        }
        boolean result5 = testSubject0.isDatatype();
        boolean result6 = testSubject0.isTopDatatype();
        Set<OWLDataRange> result7 = testSubject0.getOperands();
        String result8 = testSubject0.toString();
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
    public void shouldTestOWLDeclarationAxiomImpl() throws Exception {
        OWLDeclarationAxiomImpl testSubject0 = new OWLDeclarationAxiomImpl(
                Utils.mockOWLEntity(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLDeclarationAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result8 = testSubject0.getAxiomType();
        OWLEntity result9 = testSubject0.getEntity();
        Set<OWLAnnotation> result10 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result11 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result12 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result13 = testSubject0.isAnnotated();
        boolean result14 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result15 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result17 = testSubject0.toString();
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
    }

    @Test
    public void shouldTestOWLDifferentIndividualsAxiomImpl() throws Exception {
        OWLDifferentIndividualsAxiomImpl testSubject0 = new OWLDifferentIndividualsAxiomImpl(
                Utils.mockSet(mock(OWLIndividual.class),
                        mock(OWLIndividual.class)),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDifferentIndividualsAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        boolean result7 = testSubject0.containsAnonymousIndividuals();
        Set<OWLDifferentIndividualsAxiom> result8 = testSubject0
                .asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result9 = testSubject0.asOWLSubClassOfAxioms();
        Set<OWLIndividual> result10 = testSubject0.getIndividuals();
        List<OWLIndividual> result11 = testSubject0.getIndividualsAsList();
        boolean result12 = testSubject0.isLogicalAxiom();
        boolean result13 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result14 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result15 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result16 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result17 = testSubject0.isAnnotated();
        boolean result18 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result19 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result21 = testSubject0.toString();
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
    public void shouldTestOWLDisjointClassesAxiomImpl() throws Exception {
        OWLDisjointClassesAxiomImpl testSubject0 = new OWLDisjointClassesAxiomImpl(
                Utils.mockSet(mock(OWLClass.class)),
                Utils.mockSet(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDisjointClassesAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLDisjointClassesAxiom> result7 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result8 = testSubject0.asOWLSubClassOfAxioms();
        boolean result9 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result10 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result11 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result12 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
        boolean result13 = testSubject0.isLogicalAxiom();
        boolean result14 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result15 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result16 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result17 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result18 = testSubject0.isAnnotated();
        boolean result19 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result20 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result22 = testSubject0.toString();
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
        boolean result33 = testSubject0.isTopEntity();
        boolean result34 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLDisjointDataPropertiesAxiomImpl() throws Exception {
        OWLDisjointDataPropertiesAxiomImpl testSubject0 = new OWLDisjointDataPropertiesAxiomImpl(
                Utils.mockSet(mock(OWLDataProperty.class)),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDisjointDataPropertiesAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLDataPropertyExpression> result7 = testSubject0.getProperties();
        Set<OWLDataPropertyExpression> result8 = testSubject0
                .getPropertiesMinus(mock(OWLDataPropertyExpression.class));
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
        String result18 = testSubject0.toString();
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
    public void shouldTestOWLDisjointObjectPropertiesAxiomImpl()
            throws Exception {
        OWLDisjointObjectPropertiesAxiomImpl testSubject0 = new OWLDisjointObjectPropertiesAxiomImpl(
                Utils.mockSet(Utils.mockObjectProperty()),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDisjointObjectPropertiesAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLObjectPropertyExpression> result7 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result8 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
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
        String result18 = testSubject0.toString();
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

    public void shouldTestOWLDisjointUnionAxiomImpl() throws Exception {
        OWLDisjointUnionAxiomImpl testSubject0 = new OWLDisjointUnionAxiomImpl(
                mock(OWLClass.class), Utils.mockSet(Utils.mockAnonClass()),
                Utils.mockSet(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLDisjointUnionAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLClassExpression> result7 = testSubject0.getClassExpressions();
        OWLClass result8 = testSubject0.getOWLClass();
        OWLEquivalentClassesAxiom result9 = testSubject0
                .getOWLEquivalentClassesAxiom();
        OWLDisjointClassesAxiom result10 = testSubject0
                .getOWLDisjointClassesAxiom();
        boolean result11 = testSubject0.isLogicalAxiom();
        boolean result12 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result13 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result14 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result15 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result16 = testSubject0.isAnnotated();
        boolean result17 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result18 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result20 = testSubject0.toString();
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
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLEntityCollectionContainerCollector()
            throws Exception {
        OWLEntityCollectionContainerCollector testSubject0 = new OWLEntityCollectionContainerCollector(
                Utils.mockSet(Utils.mockOWLEntity()),
                Utils.mockCollection(mock(OWLAnonymousIndividual.class)));
        OWLEntityCollectionContainerCollector testSubject1 = new OWLEntityCollectionContainerCollector(
                Utils.mockSet(Utils.mockOWLEntity()));
        testSubject0.reset(Utils.mockSet(Utils.mockOWLEntity()));
        testSubject0.setCollectClasses(false);
        testSubject0.setCollectObjectProperties(false);
        testSubject0.setCollectDataProperties(false);
        testSubject0.setCollectIndividuals(false);
        testSubject0.setCollectDatatypes(false);
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEquivalentClassesAxiomImpl() throws Exception {
        OWLEquivalentClassesAxiomImpl testSubject0 = new OWLEquivalentClassesAxiomImpl(
                Utils.mockSet(Utils.mockAnonClass()),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLEquivalentClassesAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLEquivalentClassesAxiom> result7 = testSubject0
                .asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result8 = testSubject0.asOWLSubClassOfAxioms();
        boolean result9 = testSubject0.containsNamedEquivalentClass();
        Set<OWLClass> result10 = testSubject0.getNamedClasses();
        boolean result11 = testSubject0.containsOWLNothing();
        boolean result12 = testSubject0.containsOWLThing();
        boolean result13 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result14 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result15 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result16 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
        boolean result17 = testSubject0.isLogicalAxiom();
        boolean result18 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result19 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result20 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result21 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result22 = testSubject0.isAnnotated();
        boolean result23 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result24 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result26 = testSubject0.toString();
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
        boolean result37 = testSubject0.isTopEntity();
        boolean result38 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLEquivalentDataPropertiesAxiomImpl()
            throws Exception {
        OWLEquivalentDataPropertiesAxiomImpl testSubject0 = new OWLEquivalentDataPropertiesAxiomImpl(
                Utils.mockSet(mock(OWLDataProperty.class)),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLEquivalentDataPropertiesAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLSubDataPropertyOfAxiom> result7 = testSubject0
                .asSubDataPropertyOfAxioms();
        Set<OWLDataPropertyExpression> result8 = testSubject0.getProperties();
        Set<OWLDataPropertyExpression> result9 = testSubject0
                .getPropertiesMinus(mock(OWLDataPropertyExpression.class));
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
        String result19 = testSubject0.toString();
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
    public void shouldTestOWLEquivalentObjectPropertiesAxiomImpl()
            throws Exception {
        OWLEquivalentObjectPropertiesAxiomImpl testSubject0 = new OWLEquivalentObjectPropertiesAxiomImpl(
                Utils.mockSet(Utils.mockObjectProperty()),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLEquivalentObjectPropertiesAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLSubObjectPropertyOfAxiom> result7 = testSubject0
                .asSubObjectPropertyOfAxioms();
        Set<OWLObjectPropertyExpression> result8 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result9 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
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
        String result19 = testSubject0.toString();
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
    public void shouldTestOWLFacetRestrictionImpl() throws Exception {
        OWLFacetRestrictionImpl testSubject0 = new OWLFacetRestrictionImpl(
                OWLFacet.MAX_INCLUSIVE, mock(OWLLiteral.class));
        Object result0 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLFacet result2 = testSubject0.getFacet();
        OWLLiteral result3 = testSubject0.getFacetValue();
        String result4 = testSubject0.toString();
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
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLFunctionalDataPropertyAxiomImpl() throws Exception {
        OWLFunctionalDataPropertyAxiomImpl testSubject0 = new OWLFunctionalDataPropertyAxiomImpl(
                mock(OWLDataPropertyExpression.class),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLFunctionalDataPropertyAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLDataPropertyExpression result8 = testSubject0.getProperty();
        OWLDataPropertyExpression result9 = testSubject0.getProperty();
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
        String result19 = testSubject0.toString();
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
    public void shouldTestOWLFunctionalObjectPropertyAxiomImpl()
            throws Exception {
        OWLFunctionalObjectPropertyAxiomImpl testSubject0 = new OWLFunctionalObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLFunctionalObjectPropertyAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLObjectPropertyExpression result8 = testSubject0.getProperty();
        OWLObjectPropertyExpression result9 = testSubject0.getProperty();
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
        String result19 = testSubject0.toString();
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
    public void shouldTestOWLHasKeyAxiomImpl() throws Exception {
        OWLHasKeyAxiomImpl testSubject0 = new OWLHasKeyAxiomImpl(
                Utils.mockAnonClass(),
                new HashSet<OWLPropertyExpression<?, ?>>(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLHasKeyAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        boolean result6 = testSubject0.isLogicalAxiom();
        AxiomType<?> result7 = testSubject0.getAxiomType();
        OWLClassExpression result8 = testSubject0.getClassExpression();
        Set<OWLPropertyExpression<?, ?>> result9 = testSubject0
                .getPropertyExpressions();
        Set<OWLObjectPropertyExpression> result10 = testSubject0
                .getObjectPropertyExpressions();
        Set<OWLDataPropertyExpression> result11 = testSubject0
                .getDataPropertyExpressions();
        boolean result12 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result13 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result14 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockAxiom());
        boolean result15 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result16 = testSubject0.isAnnotated();
        boolean result17 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result18 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result20 = testSubject0.toString();
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
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLImportsDeclarationImpl() throws Exception {
        OWLImportsDeclarationImpl testSubject0 = new OWLImportsDeclarationImpl(
                IRI("urn:aFake"));
        String result0 = testSubject0.toString();
        IRI result3 = testSubject0.getIRI();
        URI result5 = testSubject0.getURI();
    }

    @Test
    public void shouldTestOWLIndividualAxiomImpl() throws Exception {
        OWLIndividualAxiomImpl testSubject0 = new OWLIndividualAxiomImpl(
                Utils.mockCollection(mock(OWLAnnotation.class))) {

            private static final long serialVersionUID = 30406L;

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
        String result9 = testSubject0.toString();
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
    public void shouldTestOWLIndividualImpl() throws Exception {
        OWLIndividualImpl testSubject0 = new OWLIndividualImpl() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public boolean isNamed() {
                return false;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public OWLNamedIndividual asOWLNamedIndividual() {
                return null;
            }

            @Override
            public OWLAnonymousIndividual asOWLAnonymousIndividual() {
                return null;
            }

            @Override
            public String toStringID() {
                return null;
            }

            @Override
            public void accept(OWLIndividualVisitor visitor) {}

            @Override
            public <O> O accept(OWLIndividualVisitorEx<O> visitor) {
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
        };
        boolean result1 = !testSubject0.isAnonymous();
        Set<OWLClassExpression> result9 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result10 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result11 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result12 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result13 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result14 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result15 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result16 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result17 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result18 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result19 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result20 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result21 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
        String result22 = testSubject0.toString();
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
        boolean result33 = testSubject0.isTopEntity();
        boolean result34 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result36 = testSubject0.accept(Utils.mockObject());
        Object result37 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result38 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLNamedIndividual result39 = testSubject0.asOWLNamedIndividual();
        }
        String result40 = testSubject0.toStringID();
        boolean result41 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result42 = testSubject0
                    .asOWLAnonymousIndividual();
        }
    }

    @Test
    public void shouldTestOWLIndividualRelationshipAxiomImpl() throws Exception {
        OWLIndividualRelationshipAxiomImpl<OWLObjectPropertyExpression, OWLPropertyAssertionObject> testSubject0 = new OWLIndividualRelationshipAxiomImpl<OWLObjectPropertyExpression, OWLPropertyAssertionObject>(
                mock(OWLIndividual.class), Utils.mockObjectProperty(),
                mock(OWLPropertyAssertionObject.class),
                Utils.mockCollection(mock(OWLAnnotation.class))) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
            public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
                return null;
            }
        };
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLPropertyAssertionObject result1 = testSubject0.getObject();
        OWLIndividual result2 = testSubject0.getSubject();
        boolean result3 = testSubject0.isLogicalAxiom();
        boolean result4 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result5 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result6 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result7 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result8 = testSubject0.isAnnotated();
        boolean result9 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result10 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result12 = testSubject0.toString();
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
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result28 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result30 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result31 = testSubject0.asOWLSubClassOfAxiom();
    }

    public void shouldTestOWLInverseFunctionalObjectPropertyAxiomImpl()
            throws Exception {
        OWLInverseFunctionalObjectPropertyAxiomImpl testSubject0 = new OWLInverseFunctionalObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLInverseFunctionalObjectPropertyAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
    }

    public void shouldTestOWLInverseObjectPropertiesAxiomImpl()
            throws Exception {
        OWLInverseObjectPropertiesAxiomImpl testSubject0 = new OWLInverseObjectPropertiesAxiomImpl(
                Utils.mockObjectProperty(), Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLInverseObjectPropertiesAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        Set<OWLSubObjectPropertyOfAxiom> result7 = testSubject0
                .asSubObjectPropertyOfAxioms();
        OWLObjectPropertyExpression result8 = testSubject0.getFirstProperty();
        OWLObjectPropertyExpression result9 = testSubject0.getSecondProperty();
        Set<OWLObjectPropertyExpression> result10 = testSubject0
                .getProperties();
        Set<OWLObjectPropertyExpression> result11 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
        boolean result12 = testSubject0.isLogicalAxiom();
        boolean result13 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result14 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result15 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockObject());
        boolean result16 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result17 = testSubject0.isAnnotated();
        boolean result18 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result19 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result21 = testSubject0.toString();
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
    public void shouldTestOWLIrreflexiveObjectPropertyAxiomImpl()
            throws Exception {
        OWLIrreflexiveObjectPropertyAxiomImpl testSubject0 = new OWLIrreflexiveObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLIrreflexiveObjectPropertyAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLObjectPropertyExpression result8 = testSubject0.getProperty();
        OWLObjectPropertyExpression result9 = testSubject0.getProperty();
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
        String result19 = testSubject0.toString();
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

    public void shouldTestOWLLiteralImpl() throws Exception {
        OWLLiteralImpl testSubject0 = new OWLLiteralImpl("", "",
                mock(OWLDatatype.class));
        boolean result0 = testSubject0.parseBoolean();
        float result1 = testSubject0.parseFloat();
        double result2 = testSubject0.parseDouble();
        Object result3 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockAnnotationValue());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        String result6 = testSubject0.getLiteral();
        boolean result7 = testSubject0.isRDFPlainLiteral();
        OWLDatatype result8 = testSubject0.getDatatype();
        boolean result9 = testSubject0.hasLang("");
        boolean result10 = testSubject0.hasLang();
        String result11 = testSubject0.getLang();
        boolean result12 = testSubject0.isInteger();
        int result13 = testSubject0.parseInteger();
        boolean result14 = testSubject0.isBoolean();
        boolean result15 = testSubject0.isDouble();
        boolean result16 = testSubject0.isFloat();
        String result17 = testSubject0.toString();
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
    }

    public void shouldTestOWLLiteralImplBoolean() throws Exception {
        OWLLiteralImplBoolean testSubject0 = new OWLLiteralImplBoolean(false);
        boolean result1 = testSubject0.parseBoolean();
        float result2 = testSubject0.parseFloat();
        double result3 = testSubject0.parseDouble();
        Object result4 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLAnnotationValueVisitor.class));
        Object result6 = testSubject0.accept(Utils.mockAnnotationValue());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        String result7 = testSubject0.getLiteral();
        boolean result8 = testSubject0.isRDFPlainLiteral();
        OWLDatatype result9 = testSubject0.getDatatype();
        boolean result10 = testSubject0.hasLang();
        boolean result11 = testSubject0.hasLang("");
        String result12 = testSubject0.getLang();
        boolean result13 = testSubject0.isInteger();
        int result14 = testSubject0.parseInteger();
        boolean result15 = testSubject0.isBoolean();
        boolean result16 = testSubject0.isDouble();
        boolean result17 = testSubject0.isFloat();
        String result18 = testSubject0.toString();
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
    public void shouldTestOWLLiteralImplDouble() throws Exception {
        OWLLiteralImplDouble testSubject0 = new OWLLiteralImplDouble(0D,
                mock(OWLDatatype.class));
    }

    @Test
    public void shouldTestOWLLiteralImplFloat() throws Exception {
        OWLLiteralImplFloat testSubject0 = new OWLLiteralImplFloat(0F,
                mock(OWLDatatype.class));
        float result2 = testSubject0.parseFloat();
    }

    @Test
    public void shouldTestOWLLiteralImplInteger() throws Exception {
        OWLLiteralImplInteger testSubject0 = new OWLLiteralImplInteger(0,
                mock(OWLDatatype.class));
        boolean result13 = testSubject0.isInteger();
    }

    @Test
    public void shouldTestOWLLogicalAxiomImpl() throws Exception {
        OWLLogicalAxiomImpl testSubject0 = new OWLLogicalAxiomImpl(
                new ArrayList<OWLAnnotation>()) {

            private static final long serialVersionUID = 30406L;

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
        String result9 = testSubject0.toString();
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
    public void shouldTestOWLNamedIndividualImpl() throws Exception {
        OWLNamedIndividualImpl testSubject0 = new OWLNamedIndividualImpl(
                IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        Object result2 = testSubject0.accept(Utils.mockIndividual());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockEntity());
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        testSubject0.accept(mock(OWLIndividualVisitor.class));
        boolean result5 = testSubject0.isType(EntityType.CLASS);
        boolean result6 = testSubject0.isAnonymous();
        Set<OWLAxiom> result7 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result8 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result9 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        IRI result10 = testSubject0.getIRI();
        EntityType<?> result11 = testSubject0.getEntityType();
        boolean result13 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result14 = testSubject0.asOWLNamedIndividual();
        }
        boolean result15 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result16 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result17 = testSubject0.toStringID();
        boolean result18 = testSubject0.isNamed();
        if (testSubject0.isAnonymous()) {
            OWLAnonymousIndividual result19 = testSubject0
                    .asOWLAnonymousIndividual();
        }
        boolean result21 = !testSubject0.isAnonymous();
        Set<OWLClassExpression> result29 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result30 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result31 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result32 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result33 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result34 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result35 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result36 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result37 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result38 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result39 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result40 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result41 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
        String result42 = testSubject0.toString();
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
        boolean result63 = testSubject0.isTopEntity();
        boolean result60 = testSubject0.isBottomEntity();
    }
}
