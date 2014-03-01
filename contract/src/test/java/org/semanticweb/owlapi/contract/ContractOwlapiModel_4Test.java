package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorerNotFoundException;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyAxiom;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLQuantifiedDataRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiomSetShortCut;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiomShortCut;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLUnaryPropertyAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBinaryAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataFactory;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObject;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLUnaryAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.SetOntologyID;
import org.semanticweb.owlapi.model.SpecificOntologyChangeBroadcastStrategy;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiModel_4Test {

    @Test
    public void shouldTestOWLOntologyStorageException() throws Exception {
        OWLOntologyStorageException testSubject0 = new OWLOntologyStorageException(
                "");
        OWLOntologyStorageException testSubject1 = new OWLOntologyStorageException(
                "", new RuntimeException());
        OWLOntologyStorageException testSubject2 = new OWLOntologyStorageException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyStorer() throws Exception {
        OWLOntologyStorer testSubject0 = mock(OWLOntologyStorer.class);
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestOWLOntologyStorerNotFoundException() throws Exception {
        OWLOntologyStorerNotFoundException testSubject0 = new OWLOntologyStorerNotFoundException(
                mock(OWLOntologyFormat.class));
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLProperty() throws Exception {
        OWLProperty<OWLClassExpression, OWLObjectPropertyExpression> testSubject0 = mock(OWLProperty.class);
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result1 = testSubject0.isAnonymous();
        Set<OWLObjectPropertyExpression> result2 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result4 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result5 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result6 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result7 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result8 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLClassExpression> result9 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result10 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result11 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result12 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result13 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result14 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result15 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result16 = testSubject0.isDataPropertyExpression();
        boolean result17 = testSubject0.isObjectPropertyExpression();
        boolean result18 = testSubject0.isOWLTopObjectProperty();
        boolean result19 = testSubject0.isOWLBottomObjectProperty();
        boolean result20 = testSubject0.isOWLTopDataProperty();
        boolean result21 = testSubject0.isOWLBottomDataProperty();
        Set<OWLEntity> result22 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result23 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result24 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result25 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result26 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result27 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result28 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result29 = testSubject0.getDatatypesInSignature();
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
        Set<OWLAnnotation> result34 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result35 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result36 = testSubject0.accept(Utils.mockEntity());
        boolean result37 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result38 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result39 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result40 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result41 = testSubject0.isBuiltIn();
        EntityType<?> result42 = testSubject0.getEntityType();
        boolean result44 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result45 = testSubject0.asOWLClass();
            }
        }
        boolean result46 = testSubject0.isOWLObjectProperty();
        if (testSubject0.isOWLObjectProperty()) {
            OWLObjectProperty result47 = testSubject0.asOWLObjectProperty();
        }
        boolean result48 = testSubject0.isOWLDataProperty();
        if (testSubject0.isOWLDataProperty()) {
            OWLDataProperty result49 = testSubject0.asOWLDataProperty();
        }
        boolean result50 = testSubject0.isOWLNamedIndividual();
        if (testSubject0.isOWLNamedIndividual()) {
            OWLNamedIndividual result51 = testSubject0.asOWLNamedIndividual();
        }
        boolean result52 = testSubject0.isOWLDatatype();
        if (testSubject0.isOWLDatatype()) {
            OWLDatatype result53 = testSubject0.asOWLDatatype();
        }
        boolean result54 = testSubject0.isOWLAnnotationProperty();
        if (testSubject0.isOWLAnnotationProperty()) {
            OWLAnnotationProperty result55 = testSubject0
                    .asOWLAnnotationProperty();
        }
        String result56 = testSubject0.toStringID();
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        IRI result57 = testSubject0.getIRI();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyAssertionAxiom() throws Exception {
        OWLPropertyAssertionAxiom<OWLObjectPropertyExpression, OWLIndividual> testSubject0 = mock(OWLPropertyAssertionAxiom.class);
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLPropertyAssertionObject result1 = testSubject0.getObject();
        OWLIndividual result2 = testSubject0.getSubject();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result28 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyAssertionObject()
            throws Exception {
        OWLPropertyAssertionObject testSubject0 = mock(OWLPropertyAssertionObject.class);
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result9 = testSubject0.isTopEntity();
        boolean result10 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyAxiom() throws Exception {
        OWLPropertyAxiom testSubject0 = mock(OWLPropertyAxiom.class);
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyDomainAxiom() throws Exception {
        OWLPropertyDomainAxiom<OWLObjectPropertyExpression> testSubject0 = mock(OWLPropertyDomainAxiom.class);
        OWLClassExpression result0 = testSubject0.getDomain();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyExpression() throws Exception {
        OWLPropertyExpression<OWLClassExpression, OWLObjectPropertyExpression> testSubject0 = mock(OWLPropertyExpression.class);
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result1 = testSubject0.isAnonymous();
        Set<OWLObjectPropertyExpression> result2 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result4 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result5 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLClassExpression> result6 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result7 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result8 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLClassExpression> result9 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result10 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result11 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result12 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result13 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        boolean result14 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result15 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result16 = testSubject0.isDataPropertyExpression();
        boolean result17 = testSubject0.isObjectPropertyExpression();
        boolean result18 = testSubject0.isOWLTopObjectProperty();
        boolean result19 = testSubject0.isOWLBottomObjectProperty();
        boolean result20 = testSubject0.isOWLTopDataProperty();
        boolean result21 = testSubject0.isOWLBottomDataProperty();
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result31 = testSubject0.isTopEntity();
        boolean result32 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyExpressionVisitor()
            throws Exception {
        OWLPropertyExpressionVisitor testSubject0 = mock(OWLPropertyExpressionVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLPropertyExpressionVisitorEx()
            throws Exception {
        OWLPropertyExpressionVisitorEx<OWLObject> testSubject0 = Utils
                .mockPropertyExpression();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyRange() throws Exception {
        OWLPropertyRange testSubject0 = mock(OWLPropertyRange.class);
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result9 = testSubject0.isTopEntity();
        boolean result10 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLPropertyRangeAxiom() throws Exception {
        OWLPropertyRangeAxiom<OWLObjectPropertyExpression, OWLPropertyRange> testSubject0 = mock(OWLPropertyRangeAxiom.class);
        OWLPropertyRange result0 = testSubject0.getRange();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLQuantifiedDataRestriction()
            throws Exception {
        OWLQuantifiedDataRestriction testSubject0 = mock(OWLQuantifiedDataRestriction.class);
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLQuantifiedObjectRestriction()
            throws Exception {
        OWLQuantifiedObjectRestriction testSubject0 = mock(OWLQuantifiedObjectRestriction.class);
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLQuantifiedRestriction() throws Exception {
        OWLQuantifiedRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLPropertyRange> testSubject0 = mock(OWLQuantifiedRestriction.class);
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLReflexiveObjectPropertyAxiom()
            throws Exception {
        OWLReflexiveObjectPropertyAxiom testSubject0 = mock(OWLReflexiveObjectPropertyAxiom.class);
        OWLReflexiveObjectPropertyAxiom result0 = testSubject0
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
        OWLSubClassOfAxiom result27 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLRestriction() throws Exception {
        OWLRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLPropertyRange> testSubject0 = mock(OWLRestriction.class);
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLRuntimeException() throws Exception {
        OWLRuntimeException testSubject0 = new OWLRuntimeException();
        OWLRuntimeException testSubject1 = new OWLRuntimeException("");
        OWLRuntimeException testSubject2 = new OWLRuntimeException("",
                new RuntimeException());
        OWLRuntimeException testSubject3 = new OWLRuntimeException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLSameIndividualAxiom() throws Exception {
        OWLSameIndividualAxiom testSubject0 = mock(OWLSameIndividualAxiom.class);
        OWLSameIndividualAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result1 = testSubject0.containsAnonymousIndividuals();
        Set<OWLSameIndividualAxiom> result2 = testSubject0.asPairwiseAxioms();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result27 = testSubject0.isTopEntity();
        boolean result28 = testSubject0.isBottomEntity();
        Set<OWLSubClassOfAxiom> result30 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLSubAnnotationPropertyOfAxiom()
            throws Exception {
        OWLSubAnnotationPropertyOfAxiom testSubject0 = mock(OWLSubAnnotationPropertyOfAxiom.class);
        OWLSubAnnotationPropertyOfAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAnnotationProperty result1 = testSubject0.getSubProperty();
        OWLAnnotationProperty result2 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSubClassOfAxiom() throws Exception {
        OWLSubClassOfAxiom testSubject0 = mock(OWLSubClassOfAxiom.class);
        OWLSubClassOfAxiom result0 = testSubject0.getAxiomWithoutAnnotations();
        OWLClassExpression result1 = testSubject0.getSubClass();
        OWLClassExpression result2 = testSubject0.getSuperClass();
        boolean result3 = testSubject0.isGCI();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSubClassOfAxiomSetShortCut()
            throws Exception {
        OWLSubClassOfAxiomSetShortCut testSubject0 = mock(OWLSubClassOfAxiomSetShortCut.class);
        Set<OWLSubClassOfAxiom> result0 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestInterfaceOWLSubClassOfAxiomShortCut()
            throws Exception {
        OWLSubClassOfAxiomShortCut testSubject0 = mock(OWLSubClassOfAxiomShortCut.class);
        OWLSubClassOfAxiom result0 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLSubDataPropertyOfAxiom() throws Exception {
        OWLSubDataPropertyOfAxiom testSubject0 = mock(OWLSubDataPropertyOfAxiom.class);
        OWLSubDataPropertyOfAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLDataPropertyExpression result1 = testSubject0.getSubProperty();
        OWLDataPropertyExpression result2 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSubObjectPropertyOfAxiom()
            throws Exception {
        OWLSubObjectPropertyOfAxiom testSubject0 = mock(OWLSubObjectPropertyOfAxiom.class);
        OWLSubObjectPropertyOfAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLObjectPropertyExpression result1 = testSubject0.getSubProperty();
        OWLObjectPropertyExpression result2 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result25 = testSubject0.isTopEntity();
        boolean result26 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSubPropertyAxiom() throws Exception {
        OWLSubPropertyAxiom<OWLObjectPropertyExpression> testSubject0 = mock(OWLSubPropertyAxiom.class);
        OWLObjectPropertyExpression result0 = testSubject0.getSubProperty();
        OWLObjectPropertyExpression result1 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSubPropertyChainOfAxiom()
            throws Exception {
        OWLSubPropertyChainOfAxiom testSubject0 = mock(OWLSubPropertyChainOfAxiom.class);
        OWLSubPropertyChainOfAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLObjectPropertyExpression result1 = testSubject0.getSuperProperty();
        List<?> result2 = testSubject0.getPropertyChain();
        boolean result3 = testSubject0.isEncodingOfTransitiveProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result26 = testSubject0.isTopEntity();
        boolean result27 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceOWLSymmetricObjectPropertyAxiom()
            throws Exception {
        OWLSymmetricObjectPropertyAxiom testSubject0 = mock(OWLSymmetricObjectPropertyAxiom.class);
        OWLSymmetricObjectPropertyAxiom result0 = testSubject0
                .getAxiomWithoutAnnotations();
        Set<OWLSubObjectPropertyOfAxiom> result1 = testSubject0
                .asSubPropertyAxioms();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result27 = testSubject0.getProperty();
    }

    @Test
    public void shouldTestInterfaceOWLTransitiveObjectPropertyAxiom()
            throws Exception {
        OWLTransitiveObjectPropertyAxiom testSubject0 = mock(OWLTransitiveObjectPropertyAxiom.class);
        OWLTransitiveObjectPropertyAxiom result0 = testSubject0
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
        OWLObjectPropertyExpression result26 = testSubject0.getProperty();
    }

    @Test
    public void shouldTestInterfaceOWLUnaryPropertyAxiom() throws Exception {
        OWLUnaryPropertyAxiom<OWLObjectPropertyExpression> testSubject0 = mock(OWLUnaryPropertyAxiom.class);
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
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
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result23 = testSubject0.isTopEntity();
        boolean result24 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfacePrefixManager() throws Exception {
        PrefixManager testSubject0 = new DefaultPrefixManager();
        String result0 = testSubject0.getPrefix("");
        IRI result1 = testSubject0.getIRI("");
        Map<String, String> result2 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result3 = testSubject0.getPrefixNames();
        boolean result4 = testSubject0.containsPrefixMapping("");
        String result5 = testSubject0.getDefaultPrefix();
        String result6 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestRemoveAxiom() throws Exception {
        RemoveAxiom testSubject0 = new RemoveAxiom(Utils.getMockOntology(),
                mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        OWLAxiom result2 = testSubject0.getAxiom();
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        Set<OWLEntity> result5 = testSubject0.getSignature();
        OWLOntology result6 = testSubject0.getOntology();
    }

    public void shouldTestRemoveImport() throws Exception {
        RemoveImport testSubject0 = new RemoveImport(Utils.getMockOntology(),
                mock(OWLImportsDeclaration.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        OWLAxiom result2 = testSubject0.getAxiom();
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        OWLImportsDeclaration result5 = testSubject0.getImportDeclaration();
        OWLOntology result6 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestRemoveOntologyAnnotation() throws Exception {
        RemoveOntologyAnnotation testSubject0 = new RemoveOntologyAnnotation(
                Utils.getMockOntology(), mock(OWLAnnotation.class));
        String result0 = testSubject0.toString();
        OWLAnnotation result1 = testSubject0.getAnnotation();
        Object result2 = testSubject0.accept(Utils.mockOntologyChange());
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        OWLAxiom result3 = testSubject0.getAxiom();
        boolean result4 = testSubject0.isAxiomChange();
        boolean result5 = testSubject0.isImportChange();
        OWLOntology result6 = testSubject0.getOntology();
    }

    public void shouldTestSetOntologyID() throws Exception {
        SetOntologyID testSubject0 = new SetOntologyID(Utils.getMockOntology(),
                new OWLOntologyID());
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        OWLAxiom result2 = testSubject0.getAxiom();
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        OWLOntologyID result5 = testSubject0.getOriginalOntologyID();
        OWLOntologyID result6 = testSubject0.getNewOntologyID();
        OWLOntology result7 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestSpecificOntologyChangeBroadcastStrategy()
            throws Exception {
        SpecificOntologyChangeBroadcastStrategy testSubject0 = new SpecificOntologyChangeBroadcastStrategy(
                Utils.getMockOntology());
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceSWRLArgument() throws Exception {
        SWRLArgument testSubject0 = mock(SWRLArgument.class);
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result1 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLAtom() throws Exception {
        SWRLAtom testSubject0 = mock(SWRLAtom.class);
        SWRLPredicate result0 = testSubject0.getPredicate();
        Collection<SWRLArgument> result1 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result12 = testSubject0.isTopEntity();
        boolean result13 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLBinaryAtom() throws Exception {
        SWRLBinaryAtom<SWRLArgument, SWRLArgument> testSubject0 = mock(SWRLBinaryAtom.class);
        SWRLArgument result0 = testSubject0.getFirstArgument();
        SWRLArgument result1 = testSubject0.getSecondArgument();
        SWRLPredicate result2 = testSubject0.getPredicate();
        Collection<SWRLArgument> result3 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLBuiltInAtom() throws Exception {
        SWRLBuiltInAtom testSubject0 = mock(SWRLBuiltInAtom.class);
        IRI result0 = testSubject0.getPredicate();
        List<SWRLDArgument> result1 = testSubject0.getArguments();
        boolean result2 = testSubject0.isCoreBuiltIn();
        SWRLPredicate result3 = testSubject0.getPredicate();
        Collection<SWRLArgument> result4 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLClassAtom() throws Exception {
        SWRLClassAtom testSubject0 = mock(SWRLClassAtom.class);
        OWLClassExpression result0 = testSubject0.getPredicate();
        SWRLArgument result1 = testSubject0.getArgument();
        SWRLPredicate result2 = testSubject0.getPredicate();
        Collection<SWRLArgument> result3 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLDArgument() throws Exception {
        SWRLDArgument testSubject0 = mock(SWRLDArgument.class);
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result52 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result53 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result54 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result55 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result56 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result57 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result58 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result59 = testSubject0.getDatatypesInSignature();
        boolean result10 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLDataFactory() throws Exception {
        SWRLDataFactory testSubject0 = mock(SWRLDataFactory.class);
        SWRLRule result2 = testSubject0.getSWRLRule(
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(SWRLAtom.class)));
        SWRLRule result3 = testSubject0.getSWRLRule(
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(OWLAnnotation.class)));
        SWRLClassAtom result4 = testSubject0.getSWRLClassAtom(
                Utils.mockAnonClass(), mock(SWRLIArgument.class));
        SWRLDataRangeAtom result5 = testSubject0.getSWRLDataRangeAtom(
                mock(OWLDataRange.class), mock(SWRLDArgument.class));
        SWRLObjectPropertyAtom result6 = testSubject0
                .getSWRLObjectPropertyAtom(Utils.mockObjectProperty(),
                        mock(SWRLIArgument.class), mock(SWRLIArgument.class));
        SWRLDataPropertyAtom result7 = testSubject0.getSWRLDataPropertyAtom(
                mock(OWLDataPropertyExpression.class),
                mock(SWRLIArgument.class), mock(SWRLDArgument.class));
        SWRLBuiltInAtom result8 = testSubject0.getSWRLBuiltInAtom(
                IRI("urn:aFake"), Utils.mockList(mock(SWRLDArgument.class)));
        SWRLVariable result9 = testSubject0.getSWRLVariable(IRI("urn:aFake"));
        SWRLIndividualArgument result10 = testSubject0
                .getSWRLIndividualArgument(mock(OWLIndividual.class));
        SWRLLiteralArgument result11 = testSubject0
                .getSWRLLiteralArgument(mock(OWLLiteral.class));
        SWRLSameIndividualAtom result12 = testSubject0
                .getSWRLSameIndividualAtom(mock(SWRLIArgument.class),
                        mock(SWRLIArgument.class));
        SWRLDifferentIndividualsAtom result13 = testSubject0
                .getSWRLDifferentIndividualsAtom(mock(SWRLIArgument.class),
                        mock(SWRLIArgument.class));
    }

    @Test
    public void shouldTestInterfaceSWRLDataPropertyAtom() throws Exception {
        SWRLDataPropertyAtom testSubject0 = mock(SWRLDataPropertyAtom.class);
        OWLDataPropertyExpression result0 = testSubject0.getPredicate();
        SWRLArgument result1 = testSubject0.getFirstArgument();
        SWRLArgument result2 = testSubject0.getSecondArgument();
        SWRLPredicate result3 = testSubject0.getPredicate();
        Collection<SWRLArgument> result4 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result12 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result13 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result14 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result11 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLDataRangeAtom() throws Exception {
        SWRLDataRangeAtom testSubject0 = mock(SWRLDataRangeAtom.class);
        OWLDataRange result0 = testSubject0.getPredicate();
        SWRLArgument result1 = testSubject0.getArgument();
        SWRLPredicate result12 = testSubject0.getPredicate();
        Collection<SWRLArgument> result13 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result14 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result16 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLDifferentIndividualsAtom()
            throws Exception {
        SWRLDifferentIndividualsAtom testSubject0 = mock(SWRLDifferentIndividualsAtom.class);
        SWRLArgument result0 = testSubject0.getFirstArgument();
        SWRLArgument result1 = testSubject0.getSecondArgument();
        SWRLPredicate result12 = testSubject0.getPredicate();
        Collection<SWRLArgument> result13 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result11 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLIArgument() throws Exception {
        SWRLIArgument testSubject0 = mock(SWRLIArgument.class);
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result1 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLIndividualArgument() throws Exception {
        SWRLIndividualArgument testSubject0 = mock(SWRLIndividualArgument.class);
        OWLIndividual result0 = testSubject0.getIndividual();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result11 = testSubject0.isTopEntity();
        boolean result12 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLLiteralArgument() throws Exception {
        SWRLLiteralArgument testSubject0 = mock(SWRLLiteralArgument.class);
        OWLLiteral result0 = testSubject0.getLiteral();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result11 = testSubject0.isTopEntity();
        boolean result12 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLObject() throws Exception {
        SWRLObject testSubject0 = mock(SWRLObject.class);
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result1 = testSubject0.isTopEntity();
        boolean result11 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLObjectPropertyAtom() throws Exception {
        SWRLObjectPropertyAtom testSubject0 = mock(SWRLObjectPropertyAtom.class);
        OWLObjectPropertyExpression result0 = testSubject0.getPredicate();
        SWRLObjectPropertyAtom result1 = testSubject0.getSimplified();
        SWRLArgument result12 = testSubject0.getFirstArgument();
        SWRLArgument result13 = testSubject0.getSecondArgument();
        SWRLPredicate result14 = testSubject0.getPredicate();
        Collection<SWRLArgument> result15 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result11 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result16 = testSubject0.isTopEntity();
        boolean result17 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLObjectVisitor() throws Exception {
        SWRLObjectVisitor testSubject0 = mock(SWRLObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceSWRLObjectVisitorEx() throws Exception {
        SWRLObjectVisitorEx<OWLObject> testSubject0 = Utils.mockSWRLObject();
    }

    @Test
    public void shouldTestInterfaceSWRLPredicate() throws Exception {
        SWRLPredicate testSubject0 = mock(SWRLPredicate.class);
    }

    @Test
    public void shouldTestInterfaceSWRLRule() throws Exception {
        SWRLRule testSubject0 = mock(SWRLRule.class);
        SWRLRule result0 = testSubject0.getAxiomWithoutAnnotations();
        SWRLRule result1 = testSubject0.getSimplified();
        Set<SWRLAtom> result2 = testSubject0.getBody();
        Set<SWRLAtom> result3 = testSubject0.getHead();
        Set<SWRLVariable> result4 = testSubject0.getVariables();
        boolean result5 = testSubject0.containsAnonymousClassExpressions();
        Set<OWLClassExpression> result6 = testSubject0.getClassAtomPredicates();
        Set<OWLAnnotation> result7 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result8 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result9 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result10 = testSubject0.getAxiomWithoutAnnotations();
        boolean result12 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result13 = testSubject0.isLogicalAxiom();
        boolean result14 = testSubject0.isAnnotationAxiom();
        boolean result15 = testSubject0.isAnnotated();
        AxiomType<?> result16 = testSubject0.getAxiomType();
        boolean result17 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result18 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        Set<OWLEntity> result20 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result21 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result22 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result23 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result24 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result25 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result26 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result27 = testSubject0.getDatatypesInSignature();
        boolean result29 = testSubject0.isTopEntity();
        boolean result30 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result32 = testSubject0.accept(Utils.mockSWRLObject());
    }

    @Test
    public void shouldTestInterfaceSWRLSameIndividualAtom() throws Exception {
        SWRLSameIndividualAtom testSubject0 = mock(SWRLSameIndividualAtom.class);
        SWRLArgument result0 = testSubject0.getFirstArgument();
        SWRLArgument result1 = testSubject0.getSecondArgument();
        SWRLPredicate result12 = testSubject0.getPredicate();
        Collection<SWRLArgument> result13 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result11 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLUnaryAtom() throws Exception {
        SWRLUnaryAtom<SWRLArgument> testSubject0 = mock(SWRLUnaryAtom.class);
        SWRLArgument result0 = testSubject0.getArgument();
        SWRLPredicate result1 = testSubject0.getPredicate();
        Collection<SWRLArgument> result2 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result4 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result6 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result7 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result8 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result9 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result10 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result11 = testSubject0.getDatatypesInSignature();
        boolean result13 = testSubject0.isTopEntity();
        boolean result14 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestInterfaceSWRLVariable() throws Exception {
        SWRLVariable testSubject0 = mock(SWRLVariable.class);
        IRI result0 = testSubject0.getIRI();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        Set<OWLEntity> result2 = testSubject0.getSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result3 = testSubject0.accept(Utils.mockObject());
        Set<OWLAnonymousIndividual> result4 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result5 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result6 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result7 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result8 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result9 = testSubject0.getDatatypesInSignature();
        boolean result11 = testSubject0.isTopEntity();
        boolean result12 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestUnknownOWLOntologyException() throws Exception {
        UnknownOWLOntologyException testSubject0 = new UnknownOWLOntologyException(
                new OWLOntologyID());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestUnloadableImportException() throws Exception {
        UnloadableImportException testSubject0 = new UnloadableImportException(
                mock(OWLOntologyCreationException.class),
                mock(OWLImportsDeclaration.class));
        OWLImportsDeclaration result0 = testSubject0.getImportsDeclaration();
        OWLOntologyCreationException result1 = testSubject0
                .getOntologyCreationException();
        Throwable result3 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }
}
