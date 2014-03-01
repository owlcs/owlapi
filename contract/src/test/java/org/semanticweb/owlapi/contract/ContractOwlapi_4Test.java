package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFactory.OWLOntologyCreationHandler;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLArgument;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLPredicate;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;

import uk.ac.manchester.cs.owl.owlapi.OWLValueRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.SWRLAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLBinaryAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLBuiltInAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLClassAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDataPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDataRangeAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLDifferentIndividualsAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLIndividualArgumentImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLLiteralArgumentImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLObjectPropertyAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLSameIndividualAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLUnaryAtomImpl;
import uk.ac.manchester.cs.owl.owlapi.SWRLVariableImpl;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapi_4Test {

    public void shouldTestOWLValueRestrictionImpl() throws Exception {
        OWLValueRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLObject> testSubject0 = new OWLValueRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLObject>(
                Utils.mockObjectProperty(), mock(OWLObject.class)) {

            private static final long serialVersionUID = 30406L;

            @Override
            public OWLClassExpression asSomeValuesFrom() {
                return null;
            }

            @Override
            public boolean isObjectRestriction() {
                return false;
            }

            @Override
            public boolean isDataRestriction() {
                return false;
            }

            @Override
            public ClassExpressionType getClassExpressionType() {
                return null;
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
        };
        OWLObject result0 = testSubject0.getValue();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isClassExpressionLiteral();
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            OWLClass result4 = testSubject0.asOWLClass();
        }
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        String result13 = testSubject0.toString();
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
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        Object result28 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result29 = testSubject0.getClassExpressionType();
        boolean result30 = testSubject0.isObjectRestriction();
        boolean result31 = testSubject0.isDataRestriction();
        OWLClassExpression result32 = testSubject0.asSomeValuesFrom();
    }

    public void shouldTestParsableOWLOntologyFactory() throws Exception {
        ParsableOWLOntologyFactory testSubject0 = new ParsableOWLOntologyFactory();
        OWLOntology result1 = testSubject0.loadOWLOntology(
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class),
                new OWLOntologyLoaderConfiguration());
        OWLOntology result2 = testSubject0.loadOWLOntology(
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class));
        boolean result3 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
        boolean result4 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
        List<OWLParser> result5 = testSubject0.getParsers();
        testSubject0.setOWLOntologyManager(Utils.getMockManager());
        OWLOntologyManager result6 = testSubject0.getOWLOntologyManager();
        OWLOntology result7 = testSubject0.createOWLOntology(
                new OWLOntologyID(), IRI("urn:aFake"),
                mock(OWLOntologyCreationHandler.class));
        String result8 = testSubject0.toString();
    }

    public void shouldTestSWRLAtomImpl() throws Exception {
        SWRLAtomImpl testSubject0 = new SWRLAtomImpl(mock(SWRLPredicate.class)) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public Collection<SWRLArgument> getAllArguments() {
                return null;
            }

            @Override
            public void accept(SWRLObjectVisitor visitor) {}

            @Override
            public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
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
        SWRLPredicate result0 = testSubject0.getPredicate();
        String result1 = testSubject0.toString();
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
        boolean result12 = testSubject0.isTopEntity();
        boolean result13 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result15 = testSubject0.accept(Utils.mockObject());
        Collection<SWRLArgument> result16 = testSubject0.getAllArguments();
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result17 = testSubject0.accept(Utils.mockSWRLObject());
    }

    public void shouldTestSWRLBinaryAtomImpl() throws Exception {
        SWRLBinaryAtomImpl<SWRLArgument, SWRLArgument> testSubject0 = new SWRLBinaryAtomImpl<SWRLArgument, SWRLArgument>(
                mock(SWRLPredicate.class), mock(SWRLArgument.class),
                mock(SWRLArgument.class)) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public void accept(SWRLObjectVisitor visitor) {}

            @Override
            public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }
        };
        Collection<SWRLArgument> result0 = testSubject0.getAllArguments();
        SWRLArgument result1 = testSubject0.getFirstArgument();
        SWRLArgument result2 = testSubject0.getSecondArgument();
        SWRLPredicate result3 = testSubject0.getPredicate();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result19 = testSubject0.accept(Utils.mockSWRLObject());
    }

    public void shouldTestSWRLBuiltInAtomImpl() throws Exception {
        SWRLBuiltInAtomImpl testSubject0 = new SWRLBuiltInAtomImpl(
                IRI("urn:aFake"), Utils.mockList(mock(SWRLDArgument.class)));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Object result1 = testSubject0.accept(Utils.mockObject());
        SWRLPredicate result2 = testSubject0.getPredicate();
        IRI result3 = testSubject0.getPredicate();
        Collection<SWRLArgument> result4 = testSubject0.getAllArguments();
        List<SWRLDArgument> result5 = testSubject0.getArguments();
        boolean result6 = testSubject0.isCoreBuiltIn();
        String result7 = testSubject0.toString();
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
    }

    public void shouldTestSWRLClassAtomImpl() throws Exception {
        SWRLClassAtomImpl testSubject0 = new SWRLClassAtomImpl(
                Utils.mockAnonClass(), mock(SWRLIArgument.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        OWLClassExpression result2 = testSubject0.getPredicate();
        SWRLPredicate result3 = testSubject0.getPredicate();
        SWRLArgument result4 = testSubject0.getArgument();
        Collection<SWRLArgument> result5 = testSubject0.getAllArguments();
        String result6 = testSubject0.toString();
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
        boolean result17 = testSubject0.isTopEntity();
        boolean result18 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLDataPropertyAtomImpl() throws Exception {
        SWRLDataPropertyAtomImpl testSubject0 = new SWRLDataPropertyAtomImpl(
                mock(OWLDataPropertyExpression.class),
                mock(SWRLIArgument.class), mock(SWRLDArgument.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLDataPropertyExpression result2 = testSubject0.getPredicate();
        SWRLPredicate result3 = testSubject0.getPredicate();
        Collection<SWRLArgument> result4 = testSubject0.getAllArguments();
        SWRLArgument result5 = testSubject0.getFirstArgument();
        SWRLArgument result6 = testSubject0.getSecondArgument();
        String result7 = testSubject0.toString();
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
    }

    public void shouldTestSWRLDataRangeAtomImpl() throws Exception {
        SWRLDataRangeAtomImpl testSubject0 = new SWRLDataRangeAtomImpl(
                mock(OWLDataRange.class), mock(SWRLDArgument.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        SWRLPredicate result2 = testSubject0.getPredicate();
        OWLDataRange result3 = testSubject0.getPredicate();
        SWRLArgument result4 = testSubject0.getArgument();
        Collection<SWRLArgument> result5 = testSubject0.getAllArguments();
        String result6 = testSubject0.toString();
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
        boolean result17 = testSubject0.isTopEntity();
        boolean result18 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLDifferentIndividualsAtomImpl() throws Exception {
        SWRLDifferentIndividualsAtomImpl testSubject0 = new SWRLDifferentIndividualsAtomImpl(
                mock(OWLDataFactory.class), mock(SWRLIArgument.class),
                mock(SWRLIArgument.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Object result1 = testSubject0.accept(Utils.mockObject());
        Collection<SWRLArgument> result2 = testSubject0.getAllArguments();
        SWRLArgument result3 = testSubject0.getFirstArgument();
        SWRLArgument result4 = testSubject0.getSecondArgument();
        SWRLPredicate result5 = testSubject0.getPredicate();
        String result6 = testSubject0.toString();
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
        boolean result17 = testSubject0.isTopEntity();
        boolean result18 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLIndividualArgumentImpl() throws Exception {
        SWRLIndividualArgumentImpl testSubject0 = new SWRLIndividualArgumentImpl(
                mock(OWLIndividual.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLIndividual result2 = testSubject0.getIndividual();
        String result3 = testSubject0.toString();
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
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLLiteralArgumentImpl() throws Exception {
        SWRLLiteralArgumentImpl testSubject0 = new SWRLLiteralArgumentImpl(
                mock(OWLLiteral.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLLiteral result2 = testSubject0.getLiteral();
        String result3 = testSubject0.toString();
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
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLObjectPropertyAtomImpl() throws Exception {
        SWRLObjectPropertyAtomImpl testSubject0 = new SWRLObjectPropertyAtomImpl(
                Utils.mockObjectProperty(), mock(SWRLIArgument.class),
                mock(SWRLIArgument.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Object result1 = testSubject0.accept(Utils.mockObject());
        SWRLPredicate result2 = testSubject0.getPredicate();
        OWLObjectPropertyExpression result3 = testSubject0.getPredicate();
        SWRLObjectPropertyAtom result4 = testSubject0.getSimplified();
        Collection<SWRLArgument> result5 = testSubject0.getAllArguments();
        SWRLArgument result6 = testSubject0.getFirstArgument();
        SWRLArgument result7 = testSubject0.getSecondArgument();
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

    public void shouldTestSWRLRuleImpl() throws Exception {
        SWRLRuleImpl testSubject0 = new SWRLRuleImpl(
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        SWRLRule result4 = testSubject0.getAxiomWithoutAnnotations();
        boolean result6 = testSubject0.isLogicalAxiom();
        AxiomType<?> result7 = testSubject0.getAxiomType();
        SWRLRule result8 = testSubject0.getSimplified();
        Set<SWRLAtom> result9 = testSubject0.getBody();
        Set<SWRLAtom> result10 = testSubject0.getHead();
        Set<SWRLVariable> result11 = testSubject0.getVariables();
        boolean result12 = testSubject0.containsAnonymousClassExpressions();
        Set<OWLClassExpression> result13 = testSubject0
                .getClassAtomPredicates();
        boolean result14 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result15 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result16 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockObject());
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

    public void shouldTestSWRLSameIndividualAtomImpl() throws Exception {
        SWRLSameIndividualAtomImpl testSubject0 = new SWRLSameIndividualAtomImpl(
                mock(OWLDataFactory.class), mock(SWRLIArgument.class),
                mock(SWRLIArgument.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockSWRLObject());
        Object result1 = testSubject0.accept(Utils.mockObject());
        Collection<SWRLArgument> result2 = testSubject0.getAllArguments();
        SWRLArgument result3 = testSubject0.getFirstArgument();
        SWRLArgument result4 = testSubject0.getSecondArgument();
        SWRLPredicate result5 = testSubject0.getPredicate();
        String result6 = testSubject0.toString();
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
        boolean result17 = testSubject0.isTopEntity();
        boolean result18 = testSubject0.isBottomEntity();
    }

    public void shouldTestSWRLUnaryAtomImpl() throws Exception {
        SWRLUnaryAtomImpl<SWRLArgument> testSubject0 = new SWRLUnaryAtomImpl<SWRLArgument>(
                mock(SWRLPredicate.class), mock(SWRLArgument.class)) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public void accept(SWRLObjectVisitor visitor) {}

            @Override
            public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
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
        SWRLArgument result0 = testSubject0.getArgument();
        Collection<SWRLArgument> result1 = testSubject0.getAllArguments();
        SWRLPredicate result2 = testSubject0.getPredicate();
        String result3 = testSubject0.toString();
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
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result17 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockSWRLObject());
    }

    public void shouldTestSWRLVariableImpl() throws Exception {
        SWRLVariableImpl testSubject0 = new SWRLVariableImpl(IRI("urn:aFake")) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;
        };
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(SWRLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockSWRLObject());
        IRI result2 = testSubject0.getIRI();
        String result3 = testSubject0.toString();
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
        boolean result14 = testSubject0.isTopEntity();
        boolean result15 = testSubject0.isBottomEntity();
    }
}
