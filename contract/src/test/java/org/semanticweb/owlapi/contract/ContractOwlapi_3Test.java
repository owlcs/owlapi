package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataRangeVisitor;
import org.semanticweb.owlapi.model.OWLDataRangeVisitorEx;
import org.semanticweb.owlapi.model.OWLDataVisitor;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitor;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNaryAxiom;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyAssertionObject;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

import uk.ac.manchester.cs.owl.owlapi.OWLNaryBooleanClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNaryClassAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNaryDataRangeImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNaryIndividualAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNaryPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNegativeDataPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLNegativeObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectCardinalityRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectExactCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectHasSelfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectHasValueImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectInverseOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMaxCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectMinCardinalityImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectOneOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyAssertionAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyCharacteristicAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyIRIMapperImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLPropertyDomainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLPropertyExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLPropertyRangeAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLQuantifiedDataRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLQuantifiedObjectRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLQuantifiedRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLReflexiveObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLRestrictionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSameIndividualAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubAnnotationPropertyOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubDataPropertyOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubObjectPropertyOfAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubPropertyChainAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSymmetricObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLTransitiveObjectPropertyAxiomImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLUnaryPropertyAxiomImpl;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapi_3Test {

    @Test
    public void shouldTestOWLNaryBooleanClassExpressionImpl() throws Exception {
        OWLNaryBooleanClassExpressionImpl testSubject0 = new OWLNaryBooleanClassExpressionImpl(
                Utils.mockSet(Utils.mockAnonClass())) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
        boolean result0 = testSubject0.isClassExpressionLiteral();
        Set<OWLClassExpression> result1 = testSubject0.getOperands();
        List<OWLClassExpression> result2 = testSubject0.getOperandsAsList();
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
        }
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        String result13 = testSubject0.toString();
        Set<OWLEntity> result16 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result17 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result18 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result19 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result20 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result21 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result22 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        Object result28 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result29 = testSubject0.getClassExpressionType();
    }

    @Test
    public void shouldTestOWLNaryClassAxiomImpl() throws Exception {
        OWLNaryClassAxiomImpl testSubject0 = new OWLNaryClassAxiomImpl(
                Utils.mockSet(Utils.mockAnonClass()),
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
            public Set<? extends OWLNaryAxiom> asPairwiseAxioms() {
                return null;
            }

            @Override
            public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
                return null;
            }
        };
        boolean result0 = testSubject0.contains(Utils.mockAnonClass());
        Set<OWLClassExpression> result1 = testSubject0.getClassExpressions();
        List<OWLClassExpression> result2 = testSubject0
                .getClassExpressionsAsList();
        Set<OWLClassExpression> result3 = testSubject0
                .getClassExpressionsMinus(Utils.mockAnonClass());
        boolean result4 = testSubject0.isLogicalAxiom();
        boolean result5 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result6 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result7 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result8 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result9 = testSubject0.isAnnotated();
        boolean result10 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result11 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result13 = testSubject0.toString();
        Set<OWLEntity> result16 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result17 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result18 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result19 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result20 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result21 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result22 = testSubject0.getDatatypesInSignature();
        boolean result24 = testSubject0.isTopEntity();
        boolean result25 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result28 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result29 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result31 = testSubject0.getAxiomType();
        Set<? extends OWLNaryAxiom> result32 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result33 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestOWLNaryDataRangeImpl() throws Exception {
        OWLNaryDataRangeImpl testSubject0 = new OWLNaryDataRangeImpl(
                new HashSet<OWLDataRange>()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public DataRangeType getDataRangeType() {
                return null;
            }

            @Override
            public void accept(OWLDataVisitor visitor) {}

            @Override
            public <O> O accept(OWLDataVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public void accept(OWLDataRangeVisitor visitor) {}

            @Override
            public <O> O accept(OWLDataRangeVisitorEx<O> visitor) {
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
        if (testSubject0.isDatatype()) {
            OWLDatatype result0 = testSubject0.asOWLDatatype();
        }
        boolean result1 = testSubject0.isDatatype();
        boolean result2 = testSubject0.isTopDatatype();
        Set<OWLDataRange> result3 = testSubject0.getOperands();
        String result4 = testSubject0.toString();
        Set<OWLEntity> result7 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result8 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result9 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result10 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result11 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result12 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result13 = testSubject0.getDatatypesInSignature();
        boolean result15 = testSubject0.isTopEntity();
        boolean result16 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result18 = testSubject0.accept(Utils.mockObject());
        Object result19 = testSubject0.accept(Utils.mockDataRange());
        testSubject0.accept(mock(OWLDataVisitor.class));
        Object result20 = testSubject0.accept(Utils.mockData());
        testSubject0.accept(mock(OWLDataRangeVisitor.class));
        DataRangeType result21 = testSubject0.getDataRangeType();
    }

    @Test
    public void shouldTestOWLNaryIndividualAxiomImpl() throws Exception {
        OWLNaryIndividualAxiomImpl testSubject0 = new OWLNaryIndividualAxiomImpl(
                Utils.mockSet(mock(OWLIndividual.class)),
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
            public Set<? extends OWLNaryAxiom> asPairwiseAxioms() {
                return null;
            }

            @Override
            public Set<OWLSubClassOfAxiom> asOWLSubClassOfAxioms() {
                return null;
            }
        };
        Set<OWLIndividual> result0 = testSubject0.getIndividuals();
        List<OWLIndividual> result1 = testSubject0.getIndividualsAsList();
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
        boolean result22 = testSubject0.isTopEntity();
        boolean result23 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
        Set<? extends OWLNaryAxiom> result30 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result31 = testSubject0.asOWLSubClassOfAxioms();
    }

    @Test
    public void shouldTestOWLNaryPropertyAxiomImpl() throws Exception {
        OWLNaryPropertyAxiomImpl<OWLObjectPropertyExpression> testSubject0 = new OWLNaryPropertyAxiomImpl<OWLObjectPropertyExpression>(
                Utils.mockSet(Utils.mockObjectProperty()),
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
        };
        Set<OWLObjectPropertyExpression> result0 = testSubject0.getProperties();
        Set<OWLObjectPropertyExpression> result1 = testSubject0
                .getPropertiesMinus(Utils.mockObjectProperty());
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
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
    public void shouldTestOWLNegativeDataPropertyAssertionImplAxiom()
            throws Exception {
        OWLNegativeDataPropertyAssertionAxiomImpl testSubject0 = new OWLNegativeDataPropertyAssertionAxiomImpl(
                mock(OWLIndividual.class),
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLNegativeDataPropertyAssertionAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        boolean result7 = testSubject0.containsAnonymousIndividuals();
        OWLSubClassOfAxiom result8 = testSubject0.asOWLSubClassOfAxiom();
        OWLDataPropertyExpression result9 = testSubject0.getProperty();
        OWLPropertyAssertionObject result10 = testSubject0.getObject();
        OWLIndividual result11 = testSubject0.getSubject();
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
        Set<OWLEntity> result24 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result25 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result26 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result27 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result28 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result29 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result30 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLNegativeObjectPropertyAssertionAxiomImpl()
            throws Exception {
        OWLNegativeObjectPropertyAssertionAxiomImpl testSubject0 = new OWLNegativeObjectPropertyAssertionAxiomImpl(
                mock(OWLIndividual.class), Utils.mockObjectProperty(),
                mock(OWLIndividual.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLNegativeObjectPropertyAssertionAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        boolean result7 = testSubject0.containsAnonymousIndividuals();
        OWLSubClassOfAxiom result8 = testSubject0.asOWLSubClassOfAxiom();
        OWLObjectPropertyExpression result9 = testSubject0.getProperty();
        OWLPropertyAssertionObject result10 = testSubject0.getObject();
        OWLIndividual result11 = testSubject0.getSubject();
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
        Set<OWLEntity> result24 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result25 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result26 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result27 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result28 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result29 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result30 = testSubject0.getDatatypesInSignature();
        boolean result32 = testSubject0.isTopEntity();
        boolean result33 = testSubject0.isBottomEntity();
    }

    @Test
    public void shouldTestOWLObjectAllValuesFromImpl() throws Exception {
        OWLObjectAllValuesFromImpl testSubject0 = new OWLObjectAllValuesFromImpl(
                Utils.mockObjectProperty(), Utils.mockAnonClass());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLPropertyRange result5 = testSubject0.getFiller();
        OWLObjectPropertyExpression result6 = testSubject0.getProperty();
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
        Set<OWLEntity> result21 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectCardinalityRestrictionImpl()
            throws Exception {
        OWLObjectCardinalityRestrictionImpl testSubject0 = new OWLObjectCardinalityRestrictionImpl(
                Utils.mockObjectProperty(), 0, Utils.mockAnonClass()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
        boolean result0 = testSubject0.isQualified();
        boolean result1 = testSubject0.isObjectRestriction();
        boolean result2 = testSubject0.isDataRestriction();
        int result3 = testSubject0.getCardinality();
        OWLPropertyRange result4 = testSubject0.getFiller();
        OWLObjectPropertyExpression result5 = testSubject0.getProperty();
        boolean result6 = testSubject0.isClassExpressionLiteral();
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
        }
        boolean result10 = testSubject0.isOWLThing();
        boolean result11 = testSubject0.isOWLNothing();
        OWLClassExpression result13 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result14 = testSubject0.asConjunctSet();
        boolean result15 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result16 = testSubject0.asDisjunctSet();
        String result17 = testSubject0.toString();
        Set<OWLEntity> result20 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result21 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result22 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result23 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result24 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result25 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result26 = testSubject0.getDatatypesInSignature();
        boolean result28 = testSubject0.isTopEntity();
        boolean result29 = testSubject0.isBottomEntity();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result31 = testSubject0.accept(Utils.mockObject());
        Object result32 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result33 = testSubject0.getClassExpressionType();
    }

    @Test
    public void shouldTestOWLObjectComplementOfImpl() throws Exception {
        OWLObjectComplementOfImpl testSubject0 = new OWLObjectComplementOfImpl(
                Utils.mockAnonClass());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isClassExpressionLiteral();
        OWLClassExpression result4 = testSubject0.getOperand();
        boolean result5 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result6 = testSubject0.asOWLClass();
            }
        }
        boolean result8 = testSubject0.isOWLThing();
        boolean result9 = testSubject0.isOWLNothing();
        OWLClassExpression result11 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result12 = testSubject0.asConjunctSet();
        boolean result13 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result14 = testSubject0.asDisjunctSet();
        String result15 = testSubject0.toString();
        Set<OWLEntity> result18 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result19 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result20 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result21 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result22 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result23 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result24 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectExactCardinalityImpl() throws Exception {
        OWLObjectExactCardinalityImpl testSubject0 = new OWLObjectExactCardinalityImpl(
                Utils.mockObjectProperty(), 0, Utils.mockAnonClass());
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        OWLClassExpression result3 = testSubject0.asIntersectionOfMinMax();
        boolean result4 = testSubject0.isQualified();
        boolean result5 = testSubject0.isObjectRestriction();
        boolean result6 = testSubject0.isDataRestriction();
        int result7 = testSubject0.getCardinality();
        OWLPropertyRange result8 = testSubject0.getFiller();
        OWLObjectPropertyExpression result9 = testSubject0.getProperty();
        boolean result10 = testSubject0.isClassExpressionLiteral();
        boolean result11 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result12 = testSubject0.asOWLClass();
            }
        }
        boolean result14 = testSubject0.isOWLThing();
        boolean result15 = testSubject0.isOWLNothing();
        OWLClassExpression result17 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result18 = testSubject0.asConjunctSet();
        boolean result19 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result20 = testSubject0.asDisjunctSet();
        String result21 = testSubject0.toString();
        Set<OWLEntity> result24 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result25 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result26 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result27 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result28 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result29 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result30 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectHasSelfImpl() throws Exception {
        OWLObjectHasSelfImpl testSubject0 = new OWLObjectHasSelfImpl(
                Utils.mockObjectProperty());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        Object result1 = testSubject0.accept(Utils.mockObject());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLObjectPropertyExpression result5 = testSubject0.getProperty();
        boolean result6 = testSubject0.isClassExpressionLiteral();
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
        }
        boolean result10 = testSubject0.isOWLThing();
        boolean result11 = testSubject0.isOWLNothing();
        OWLClassExpression result13 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result14 = testSubject0.asConjunctSet();
        boolean result15 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result16 = testSubject0.asDisjunctSet();
        String result17 = testSubject0.toString();
        Set<OWLEntity> result20 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result21 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result22 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result23 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result24 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result25 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result26 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectHasValueImpl() throws Exception {
        OWLObjectHasValueImpl testSubject0 = new OWLObjectHasValueImpl(
                Utils.mockObjectProperty(), mock(OWLIndividual.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLClassExpression result5 = testSubject0.asSomeValuesFrom();
        OWLObject result6 = testSubject0.getValue();
        OWLObjectPropertyExpression result7 = testSubject0.getProperty();
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
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectImpl() throws Exception {
        OWLObjectImpl testSubject0 = new OWLObjectImpl() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
        String result0 = testSubject0.toString();
        Set<OWLEntity> result3 = testSubject0.getSignature();
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
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result14 = testSubject0.accept(Utils.mockObject());
    }

    @Test
    public void shouldTestOWLObjectIntersectionOfImpl() throws Exception {
        OWLObjectIntersectionOfImpl testSubject0 = new OWLObjectIntersectionOfImpl(
                Utils.mockSet(Utils.mockAnonClass()));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        Set<OWLClassExpression> result3 = testSubject0.asConjunctSet();
        boolean result4 = testSubject0.containsConjunct(Utils.mockAnonClass());
        boolean result5 = testSubject0.isClassExpressionLiteral();
        Set<OWLClassExpression> result6 = testSubject0.getOperands();
        List<OWLClassExpression> result7 = testSubject0.getOperandsAsList();
        boolean result8 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result9 = testSubject0.asOWLClass();
            }
        }
        boolean result11 = testSubject0.isOWLThing();
        boolean result12 = testSubject0.isOWLNothing();
        OWLClassExpression result14 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result15 = testSubject0.asDisjunctSet();
        String result16 = testSubject0.toString();
        Set<OWLEntity> result19 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectInverseOfImpl() throws Exception {
        OWLObjectInverseOfImpl testSubject0 = new OWLObjectInverseOfImpl(
                Utils.mockObjectProperty());
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result2 = testSubject0.isAnonymous();
        if (testSubject0.isObjectPropertyExpression()
                && !testSubject0.isAnonymous()) {
            OWLObjectProperty result3 = testSubject0.asOWLObjectProperty();
        }
        boolean result4 = testSubject0.isOWLTopObjectProperty();
        boolean result5 = testSubject0.isOWLBottomObjectProperty();
        boolean result6 = testSubject0.isOWLTopDataProperty();
        boolean result7 = testSubject0.isOWLBottomDataProperty();
        OWLObjectPropertyExpression result8 = testSubject0.getInverse();
        boolean result9 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result10 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result11 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result12 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result13 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result14 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result15 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result16 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result17 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result18 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result19 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result20 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result21 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result22 = testSubject0
                .getInverses(Utils.getMockOntology());
        OWLObjectPropertyExpression result23 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result24 = testSubject0.getSimplified();
        boolean result26 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result27 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result28 = testSubject0.isDataPropertyExpression();
        boolean result29 = testSubject0.isObjectPropertyExpression();
        Set<OWLObjectPropertyExpression> result30 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result31 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result32 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result33 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result34 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result35 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result36 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result37 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLObjectPropertyExpression> result38 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result39 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result40 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result41 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        String result42 = testSubject0.toString();
        Set<OWLEntity> result45 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result46 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result47 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result48 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result49 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result50 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result51 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectMaxCardinalityImpl() throws Exception {
        OWLObjectMaxCardinalityImpl testSubject0 = new OWLObjectMaxCardinalityImpl(
                Utils.mockObjectProperty(), 0, Utils.mockAnonClass());
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
        OWLObjectPropertyExpression result8 = testSubject0.getProperty();
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
        Set<OWLEntity> result23 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectMinCardinalityImpl() throws Exception {
        OWLObjectMinCardinalityImpl testSubject0 = new OWLObjectMinCardinalityImpl(
                Utils.mockObjectProperty(), 0, Utils.mockAnonClass());
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
        OWLObjectPropertyExpression result8 = testSubject0.getProperty();
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
        Set<OWLEntity> result23 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectOneOfImpl() throws Exception {
        OWLObjectOneOfImpl testSubject0 = new OWLObjectOneOfImpl(
                Utils.mockSet(mock(OWLIndividual.class)));
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Set<OWLIndividual> result2 = testSubject0.getIndividuals();
        ClassExpressionType result3 = testSubject0.getClassExpressionType();
        boolean result4 = testSubject0.isClassExpressionLiteral();
        boolean result6 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result7 = testSubject0.asOWLClass();
            }
        }
        boolean result9 = testSubject0.isOWLThing();
        boolean result10 = testSubject0.isOWLNothing();
        OWLClassExpression result12 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result13 = testSubject0.asConjunctSet();
        boolean result14 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result15 = testSubject0.asDisjunctSet();
        String result16 = testSubject0.toString();
        Set<OWLEntity> result19 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectPropertyAssertionAxiomImpl()
            throws Exception {
        OWLObjectPropertyAssertionAxiomImpl testSubject0 = new OWLObjectPropertyAssertionAxiomImpl(
                mock(OWLIndividual.class), Utils.mockObjectProperty(),
                mock(OWLIndividual.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLObjectPropertyAssertionAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLObjectPropertyAssertionAxiom result7 = testSubject0.getSimplified();
        OWLSubClassOfAxiom result8 = testSubject0.asOWLSubClassOfAxiom();
        boolean result9 = testSubject0.isInSimplifiedForm();
        OWLObjectPropertyExpression result10 = testSubject0.getProperty();
        OWLPropertyAssertionObject result11 = testSubject0.getObject();
        OWLIndividual result12 = testSubject0.getSubject();
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
        Set<OWLEntity> result25 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result26 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result27 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result28 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result29 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result30 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result31 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectPropertyCharacteristicAxiomImpl()
            throws Exception {
        OWLObjectPropertyCharacteristicAxiomImpl testSubject0 = new OWLObjectPropertyCharacteristicAxiomImpl(
                Utils.mockObjectProperty(),
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
        };
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLObjectPropertyDomainAxiomImpl() throws Exception {
        OWLObjectPropertyDomainAxiomImpl testSubject0 = new OWLObjectPropertyDomainAxiomImpl(
                Utils.mockObjectProperty(), Utils.mockAnonClass(),
                Utils.mockSet(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLObjectPropertyDomainAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLClassExpression result8 = testSubject0.getDomain();
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
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectPropertyExpressionImpl() throws Exception {
        OWLObjectPropertyExpressionImpl testSubject0 = new OWLObjectPropertyExpressionImpl() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public OWLObjectProperty asOWLObjectProperty() {
                return null;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public void accept(OWLPropertyExpressionVisitor visitor) {}

            @Override
            public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public boolean isOWLTopObjectProperty() {
                return false;
            }

            @Override
            public boolean isOWLBottomObjectProperty() {
                return false;
            }

            @Override
            public boolean isOWLTopDataProperty() {
                return false;
            }

            @Override
            public boolean isOWLBottomDataProperty() {
                return false;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>>
                    getSubPropertyAxiomsForRHS(OWLOntology ont) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        boolean result0 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result1 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result2 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result3 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result4 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result5 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result6 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result7 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result8 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result9 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result10 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result11 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result12 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result13 = testSubject0
                .getInverses(Utils.getMockOntology());
        boolean result17 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result18 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result19 = testSubject0.isDataPropertyExpression();
        boolean result20 = testSubject0.isObjectPropertyExpression();
        Set<OWLObjectPropertyExpression> result21 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result24 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result25 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result26 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result27 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result28 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLObjectPropertyExpression> result29 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result30 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result31 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result32 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        String result33 = testSubject0.toString();
        Set<OWLEntity> result36 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result37 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result38 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result39 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result40 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result41 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result42 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result47 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result48 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result49 = testSubject0.isAnonymous();
        boolean result50 = testSubject0.isOWLTopObjectProperty();
        boolean result51 = testSubject0.isOWLBottomObjectProperty();
        boolean result52 = testSubject0.isOWLTopDataProperty();
        boolean result53 = testSubject0.isOWLBottomDataProperty();
        if (!testSubject0.isAnonymous()) {
            OWLObjectProperty result54 = testSubject0.asOWLObjectProperty();
        }
    }

    @Test
    public void shouldTestOWLObjectPropertyImpl() throws Exception {
        OWLObjectPropertyImpl testSubject0 = new OWLObjectPropertyImpl(
                IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        testSubject0.accept(mock(OWLEntityVisitor.class));
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockEntity());
        Object result3 = testSubject0.accept(Utils.mockPropertyExpression());
        Object result4 = testSubject0.accept(Utils.mockObject());
        boolean result5 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result9 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAxiom> result10 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
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
        if (testSubject0.isOWLDataProperty()) {
            if (testSubject0.isOWLDataProperty()) {
                OWLDataProperty result21 = testSubject0.asOWLDataProperty();
            }
        }
        if (testSubject0.isOWLNamedIndividual()) {
            if (testSubject0.isOWLNamedIndividual()) {
                OWLNamedIndividual result23 = testSubject0
                        .asOWLNamedIndividual();
            }
        }
        if (testSubject0.isOWLDatatype()) {
            if (testSubject0.isOWLDatatype()) {
                OWLDatatype result25 = testSubject0.asOWLDatatype();
            }
        }
        if (testSubject0.isOWLAnnotationProperty()) {
            if (testSubject0.isOWLAnnotationProperty()) {
                OWLAnnotationProperty result27 = testSubject0
                        .asOWLAnnotationProperty();
            }
        }
        String result28 = testSubject0.toStringID();
        boolean result29 = testSubject0.isOWLTopObjectProperty();
        boolean result30 = testSubject0.isOWLBottomObjectProperty();
        boolean result31 = testSubject0.isOWLTopDataProperty();
        boolean result32 = testSubject0.isOWLBottomDataProperty();
        boolean result33 = testSubject0.isInverseFunctional(Utils
                .getMockOntology());
        boolean result34 = testSubject0.isInverseFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result35 = testSubject0.isSymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result36 = testSubject0.isSymmetric(Utils.getMockOntology());
        boolean result37 = testSubject0.isAsymmetric(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result38 = testSubject0.isAsymmetric(Utils.getMockOntology());
        boolean result39 = testSubject0.isReflexive(Utils.getMockOntology());
        boolean result40 = testSubject0.isReflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result41 = testSubject0.isIrreflexive(Utils.getMockOntology());
        boolean result42 = testSubject0.isIrreflexive(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result43 = testSubject0.isTransitive(Utils.getMockOntology());
        boolean result44 = testSubject0.isTransitive(Utils.mockSet(Utils
                .getMockOntology()));
        Set<OWLObjectPropertyExpression> result45 = testSubject0
                .getInverses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result46 = testSubject0
                .getInverses(Utils.getMockOntology());
        OWLObjectPropertyExpression result47 = testSubject0
                .getInverseProperty();
        OWLObjectPropertyExpression result48 = testSubject0.getSimplified();
        OWLObjectProperty result49 = testSubject0.getNamedProperty();
        boolean result50 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result51 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result52 = testSubject0.isDataPropertyExpression();
        boolean result53 = testSubject0.isObjectPropertyExpression();
        Set<OWLObjectPropertyExpression> result54 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result55 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result56 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result57 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result58 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result59 = testSubject0.getDomains(Utils
                .getMockOntology());
        Set<OWLClassExpression> result60 = testSubject0.getRanges(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result61 = testSubject0.getRanges(Utils
                .getMockOntology());
        Set<OWLObjectPropertyExpression> result62 = testSubject0
                .getEquivalentProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result63 = testSubject0
                .getEquivalentProperties(Utils.getMockOntology());
        Set<OWLObjectPropertyExpression> result64 = testSubject0
                .getDisjointProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLObjectPropertyExpression> result65 = testSubject0
                .getDisjointProperties(Utils.getMockOntology());
        String result66 = testSubject0.toString();
        Set<OWLEntity> result69 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result70 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result71 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result72 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result73 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result74 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result75 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectPropertyRangeAxiomImpl() throws Exception {
        OWLObjectPropertyRangeAxiomImpl testSubject0 = new OWLObjectPropertyRangeAxiomImpl(
                Utils.mockObjectProperty(), Utils.mockAnonClass(),
                Utils.mockSet(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLObjectPropertyRangeAxiom result2 = testSubject0
                .getAxiomWithoutAnnotations();
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result7 = testSubject0.asOWLSubClassOfAxiom();
        OWLPropertyRange result8 = testSubject0.getRange();
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
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLObjectSomeValuesFromImpl() throws Exception {
        OWLObjectSomeValuesFromImpl testSubject0 = new OWLObjectSomeValuesFromImpl(
                Utils.mockObjectProperty(), Utils.mockAnonClass());
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        boolean result3 = testSubject0.isObjectRestriction();
        boolean result4 = testSubject0.isDataRestriction();
        OWLPropertyRange result5 = testSubject0.getFiller();
        OWLObjectPropertyExpression result6 = testSubject0.getProperty();
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
        Set<OWLEntity> result21 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLObjectUnionOfImpl() throws Exception {
        OWLObjectUnionOfImpl testSubject0 = new OWLObjectUnionOfImpl(
                Utils.mockSet(Utils.mockAnonClass()));
        Object result0 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result2 = testSubject0.getClassExpressionType();
        Set<OWLClassExpression> result3 = testSubject0.asDisjunctSet();
        boolean result4 = testSubject0.isClassExpressionLiteral();
        Set<OWLClassExpression> result5 = testSubject0.getOperands();
        List<OWLClassExpression> result6 = testSubject0.getOperandsAsList();
        boolean result7 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result8 = testSubject0.asOWLClass();
            }
        }
        boolean result10 = testSubject0.isOWLThing();
        boolean result11 = testSubject0.isOWLNothing();
        OWLClassExpression result13 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result14 = testSubject0.asConjunctSet();
        boolean result15 = testSubject0.containsConjunct(Utils.mockAnonClass());
        String result16 = testSubject0.toString();
        Set<OWLEntity> result19 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLOntologyImpl() throws Exception {
        OWLOntologyImpl testSubject0 = new OWLOntologyImpl(
                Utils.getMockManager(), new OWLOntologyID());
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0.isEmpty();
        Set<OWLAnnotation> result2 = testSubject0.getAnnotations();
        Set<OWLEntity> result3 = testSubject0.getSignature();
        Set<OWLEntity> result4 = testSubject0.getSignature(false);
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        Object result5 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLOntologyManager result6 = testSubject0.getOWLOntologyManager();
        Set<OWLAnonymousIndividual> result7 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result8 = testSubject0.getClassesInSignature(false);
        Set<OWLClass> result9 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result10 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLDataProperty> result11 = testSubject0
                .getDataPropertiesInSignature(false);
        Set<OWLObjectProperty> result12 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLObjectProperty> result13 = testSubject0
                .getObjectPropertiesInSignature(false);
        Set<OWLNamedIndividual> result14 = testSubject0
                .getIndividualsInSignature();
        Set<OWLNamedIndividual> result15 = testSubject0
                .getIndividualsInSignature(false);
        Set<OWLDatatype> result16 = testSubject0.getDatatypesInSignature();
        Set<OWLDatatype> result17 = testSubject0.getDatatypesInSignature(false);
        OWLOntologyID result18 = testSubject0.getOntologyID();
        boolean result19 = testSubject0.isAnonymous();
    }

    @Test
    public void shouldTestOWLOntologyIRIMapperImpl() throws Exception {
        OWLOntologyIRIMapperImpl testSubject0 = new OWLOntologyIRIMapperImpl();
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
        testSubject0.addMapping(IRI("urn:aFake"), IRI("urn:aFake"));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyManagerImpl() throws Exception {
        OWLOntologyManagerImpl testSubject0 = new OWLOntologyManagerImpl(
                mock(OWLDataFactory.class));
        boolean result0 = testSubject0.contains(IRI("urn:aFake"));
        boolean result1 = testSubject0.contains(Utils.getMockOntology());
        boolean result2 = testSubject0.contains(new OWLOntologyID());
    }

    @Test
    public void shouldTestOWLPropertyAxiomImpl() throws Exception {
        OWLPropertyAxiomImpl testSubject0 = new OWLPropertyAxiomImpl(
                new ArrayList<OWLAnnotation>()) {

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
        Set<OWLEntity> result12 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result13 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result14 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result15 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result16 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result17 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result18 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result23 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result24 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result25 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result27 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLPropertyDomainAxiomImpl() throws Exception {
        OWLPropertyDomainAxiomImpl<OWLObjectPropertyExpression> testSubject0 = new OWLPropertyDomainAxiomImpl<OWLObjectPropertyExpression>(
                Utils.mockObjectProperty(), Utils.mockAnonClass(),
                Utils.mockSet(mock(OWLAnnotation.class))) {

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
        OWLClassExpression result0 = testSubject0.getDomain();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result30 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestOWLPropertyExpressionImpl() throws Exception {
        OWLPropertyExpressionImpl<OWLClassExpression, OWLObjectPropertyExpression> testSubject0 = new OWLPropertyExpressionImpl<OWLClassExpression, OWLObjectPropertyExpression>() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public boolean isFunctional(OWLOntology ontology) {
                return false;
            }

            @Override
            public boolean isFunctional(Set<OWLOntology> ontologies) {
                return false;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public void accept(OWLPropertyExpressionVisitor visitor) {}

            @Override
            public <O> O accept(OWLPropertyExpressionVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public boolean isDataPropertyExpression() {
                return false;
            }

            @Override
            public boolean isObjectPropertyExpression() {
                return false;
            }

            @Override
            public boolean isOWLTopObjectProperty() {
                return false;
            }

            @Override
            public boolean isOWLBottomObjectProperty() {
                return false;
            }

            @Override
            public boolean isOWLTopDataProperty() {
                return false;
            }

            @Override
            public boolean isOWLBottomDataProperty() {
                return false;
            }

            @Override
            public void accept(OWLObjectVisitor visitor) {}

            @Override
            public <O> O accept(OWLObjectVisitorEx<O> visitor) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>>
                    getSubPropertyAxiomsForRHS(OWLOntology ont) {
                return null;
            }

            @Override
            protected Set<? extends OWLPropertyDomainAxiom<?>> getDomainAxioms(
                    OWLOntology ontology) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLPropertyRangeAxiom<OWLObjectPropertyExpression, OWLClassExpression>>
                    getRangeAxioms(OWLOntology ontology) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>>
                    getSubPropertyAxioms(OWLOntology ontology) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>>
                    getEquivalentPropertiesAxioms(OWLOntology ontology) {
                return null;
            }

            @Override
            protected
                    Set<? extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>>
                    getDisjointPropertiesAxioms(OWLOntology ontology) {
                return null;
            }

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        Set<OWLObjectPropertyExpression> result0 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result4 = testSubject0.getDomains(Utils
                .mockSet(Utils.getMockOntology()));
        String result12 = testSubject0.toString();
        Set<OWLEntity> result15 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result16 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result17 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result18 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result19 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result20 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result21 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLPropertyExpressionVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockPropertyExpression());
        boolean result28 = testSubject0.isAnonymous();
        boolean result29 = testSubject0.isFunctional(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result30 = testSubject0.isFunctional(Utils.getMockOntology());
        boolean result31 = testSubject0.isDataPropertyExpression();
        boolean result32 = testSubject0.isObjectPropertyExpression();
        boolean result33 = testSubject0.isOWLTopObjectProperty();
        boolean result34 = testSubject0.isOWLBottomObjectProperty();
        boolean result35 = testSubject0.isOWLTopDataProperty();
        boolean result36 = testSubject0.isOWLBottomDataProperty();
    }

    @Test
    public void shouldTestOWLPropertyRangeAxiomImpl() throws Exception {
        OWLPropertyRangeAxiomImpl<OWLObjectPropertyExpression, OWLPropertyRange> testSubject0 = new OWLPropertyRangeAxiomImpl<OWLObjectPropertyExpression, OWLPropertyRange>(
                Utils.mockObjectProperty(), mock(OWLPropertyRange.class),
                Utils.mockSet(mock(OWLAnnotation.class))) {

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
        OWLPropertyRange result0 = testSubject0.getRange();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
        OWLSubClassOfAxiom result30 = testSubject0.asOWLSubClassOfAxiom();
    }

    @Test
    public void shouldTestOWLQuantifiedDataRestrictionImpl() throws Exception {
        OWLQuantifiedDataRestrictionImpl testSubject0 = new OWLQuantifiedDataRestrictionImpl(
                mock(OWLDataPropertyExpression.class), mock(OWLDataRange.class)) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLDataPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isClassExpressionLiteral();
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
        }
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        String result13 = testSubject0.toString();
        Set<OWLEntity> result16 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result17 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result18 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result19 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result20 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result21 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result22 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        Object result28 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result29 = testSubject0.getClassExpressionType();
        boolean result30 = testSubject0.isObjectRestriction();
        boolean result31 = testSubject0.isDataRestriction();
    }

    @Test
    public void shouldTestOWLQuantifiedObjectRestrictionImpl() throws Exception {
        OWLQuantifiedObjectRestrictionImpl testSubject0 = new OWLQuantifiedObjectRestrictionImpl(
                Utils.mockObjectProperty(), Utils.mockAnonClass()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isClassExpressionLiteral();
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
        }
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        String result13 = testSubject0.toString();
        Set<OWLEntity> result16 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result17 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result18 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result19 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result20 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result21 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result22 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        Object result28 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result29 = testSubject0.getClassExpressionType();
        boolean result30 = testSubject0.isObjectRestriction();
        boolean result31 = testSubject0.isDataRestriction();
    }

    @Test
    public void shouldTestOWLQuantifiedRestrictionImpl() throws Exception {
        OWLQuantifiedRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> testSubject0 = new OWLQuantifiedRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression>(
                Utils.mockObjectProperty(), Utils.mockAnonClass()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        OWLPropertyRange result0 = testSubject0.getFiller();
        OWLObjectPropertyExpression result1 = testSubject0.getProperty();
        boolean result2 = testSubject0.isClassExpressionLiteral();
        boolean result3 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result4 = testSubject0.asOWLClass();
            }
        }
        boolean result6 = testSubject0.isOWLThing();
        boolean result7 = testSubject0.isOWLNothing();
        OWLClassExpression result9 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result10 = testSubject0.asConjunctSet();
        boolean result11 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result12 = testSubject0.asDisjunctSet();
        String result13 = testSubject0.toString();
        Set<OWLEntity> result16 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result17 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result18 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result19 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result20 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result21 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result22 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result27 = testSubject0.accept(Utils.mockObject());
        Object result28 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result29 = testSubject0.getClassExpressionType();
        boolean result30 = testSubject0.isObjectRestriction();
        boolean result31 = testSubject0.isDataRestriction();
    }

    @Test
    public void shouldTestOWLReflexiveObjectPropertyAxiomImpl()
            throws Exception {
        OWLReflexiveObjectPropertyAxiomImpl testSubject0 = new OWLReflexiveObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLReflexiveObjectPropertyAxiom result2 = testSubject0
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
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLRestrictionImpl() throws Exception {
        OWLRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLObject> testSubject0 = new OWLRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLObject>(
                Utils.mockObjectProperty()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

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

            @Override
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        boolean result1 = testSubject0.isClassExpressionLiteral();
        boolean result2 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result3 = testSubject0.asOWLClass();
            }
        }
        boolean result5 = testSubject0.isOWLThing();
        boolean result6 = testSubject0.isOWLNothing();
        OWLClassExpression result8 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result9 = testSubject0.asConjunctSet();
        boolean result10 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result11 = testSubject0.asDisjunctSet();
        String result12 = testSubject0.toString();
        Set<OWLEntity> result15 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result16 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result17 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result18 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result19 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result20 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result21 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockObject());
        Object result27 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        ClassExpressionType result28 = testSubject0.getClassExpressionType();
        boolean result29 = testSubject0.isObjectRestriction();
        boolean result30 = testSubject0.isDataRestriction();
    }

    @Test
    public void shouldTestOWLSameIndividualAxiomImpl() throws Exception {
        OWLSameIndividualAxiomImpl testSubject0 = new OWLSameIndividualAxiomImpl(
                Utils.mockSet(mock(OWLIndividual.class)),
                Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockObject());
        Object result1 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSameIndividualAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        boolean result7 = testSubject0.containsAnonymousIndividuals();
        Set<OWLSameIndividualAxiom> result8 = testSubject0.asPairwiseAxioms();
        Set<OWLSubClassOfAxiom> result9 = testSubject0.asOWLSubClassOfAxioms();
        Set<OWLIndividual> result11 = testSubject0.getIndividuals();
        List<OWLIndividual> result12 = testSubject0.getIndividualsAsList();
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
        Set<OWLEntity> result25 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result26 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result27 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result28 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result29 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result30 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result31 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLSubAnnotationPropertyOfAxiomImpl()
            throws Exception {
        OWLSubAnnotationPropertyOfAxiomImpl testSubject0 = new OWLSubAnnotationPropertyOfAxiomImpl(
                mock(OWLAnnotationProperty.class),
                mock(OWLAnnotationProperty.class),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSubAnnotationPropertyOfAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        boolean result6 = testSubject0.isLogicalAxiom();
        boolean result7 = testSubject0.isAnnotationAxiom();
        AxiomType<?> result8 = testSubject0.getAxiomType();
        OWLAnnotationProperty result9 = testSubject0.getSubProperty();
        OWLAnnotationProperty result10 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result21 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLSubClassOfAxiomImpl() throws Exception {
        OWLSubClassOfAxiomImpl testSubject0 = new OWLSubClassOfAxiomImpl(
                Utils.mockAnonClass(), Utils.mockAnonClass(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockObject());
        Object result2 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result3 = testSubject0.getAxiomWithoutAnnotations();
        OWLSubClassOfAxiom result4 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result7 = testSubject0.getAxiomType();
        OWLClassExpression result8 = testSubject0.getSubClass();
        OWLClassExpression result9 = testSubject0.getSuperClass();
        boolean result10 = testSubject0.isGCI();
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
        Set<OWLEntity> result25 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result26 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result27 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result28 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result29 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result30 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result31 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLSubDataPropertyOfAxiomImpl() throws Exception {
        OWLSubDataPropertyOfAxiomImpl testSubject0 = new OWLSubDataPropertyOfAxiomImpl(
                mock(OWLDataPropertyExpression.class),
                mock(OWLDataPropertyExpression.class),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSubDataPropertyOfAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLDataPropertyExpression result7 = testSubject0.getSubProperty();
        OWLDataPropertyExpression result8 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result21 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLSubObjectPropertyOfAxiomImpl() throws Exception {
        OWLSubObjectPropertyOfAxiomImpl testSubject0 = new OWLSubObjectPropertyOfAxiomImpl(
                Utils.mockObjectProperty(), Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSubObjectPropertyOfAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLObjectPropertyExpression result7 = testSubject0.getSubProperty();
        OWLObjectPropertyExpression result8 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result21 = testSubject0.getSignature();
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
    }

    @Test
    public void shouldTestOWLSubPropertyAxiomImpl() throws Exception {
        OWLSubPropertyAxiomImpl<OWLObjectPropertyExpression> testSubject0 = new OWLSubPropertyAxiomImpl<OWLObjectPropertyExpression>(
                Utils.mockObjectProperty(), Utils.mockObjectProperty(),
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
        };
        OWLObjectPropertyExpression result0 = testSubject0.getSubProperty();
        OWLObjectPropertyExpression result1 = testSubject0.getSuperProperty();
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
        Set<OWLEntity> result14 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result15 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result16 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result17 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result18 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result19 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result20 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result26 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result27 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result29 = testSubject0.getAxiomType();
    }

    @Test
    public void shouldTestOWLSubPropertyChainAxiomImpl() throws Exception {
        OWLSubPropertyChainAxiomImpl testSubject0 = new OWLSubPropertyChainAxiomImpl(
                new ArrayList<OWLObjectPropertyExpression>(),
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSubPropertyChainOfAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        OWLObjectPropertyExpression result7 = testSubject0.getSuperProperty();
        List<OWLObjectPropertyExpression> result8 = testSubject0
                .getPropertyChain();
        boolean result9 = testSubject0.isEncodingOfTransitiveProperty();
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result12 = testSubject0.getAnnotations();
        Set<OWLAnnotation> result13 = testSubject0
                .getAnnotations(mock(OWLAnnotationProperty.class));
        testSubject0.accept(Utils.mockObject());
        boolean result14 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result15 = testSubject0.isAnnotated();
        boolean result16 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result17 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result19 = testSubject0.toString();
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLSymmetricObjectPropertyAxiomImpl()
            throws Exception {
        OWLSymmetricObjectPropertyAxiomImpl testSubject0 = new OWLSymmetricObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLSymmetricObjectPropertyAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result6 = testSubject0.getAxiomType();
        boolean result10 = testSubject0.isLogicalAxiom();
        boolean result11 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result12 = testSubject0.getAnnotations();
        testSubject0.accept(Utils.mockAxiom());
        boolean result14 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result15 = testSubject0.isAnnotated();
        boolean result16 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result17 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result19 = testSubject0.toString();
        Set<OWLEntity> result22 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result23 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result24 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result25 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result26 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result27 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result28 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLTransitiveObjectPropertyAxiomImpl()
            throws Exception {
        OWLTransitiveObjectPropertyAxiomImpl testSubject0 = new OWLTransitiveObjectPropertyAxiomImpl(
                Utils.mockObjectProperty(),
                Utils.mockCollection(mock(OWLAnnotation.class)));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result0 = testSubject0.accept(Utils.mockAxiom());
        Object result1 = testSubject0.accept(Utils.mockObject());
        OWLAxiom result2 = testSubject0.getAxiomWithoutAnnotations();
        OWLTransitiveObjectPropertyAxiom result3 = testSubject0
                .getAxiomWithoutAnnotations();
        AxiomType<?> result5 = testSubject0.getAxiomType();
        OWLObjectPropertyExpression result6 = testSubject0.getProperty();
        OWLObjectPropertyExpression result7 = testSubject0.getProperty();
        boolean result8 = testSubject0.isLogicalAxiom();
        boolean result9 = testSubject0.isAnnotationAxiom();
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
        Set<OWLEntity> result20 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result21 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result22 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result23 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result24 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result25 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result26 = testSubject0.getDatatypesInSignature();
    }

    @Test
    public void shouldTestOWLUnaryPropertyAxiomImpl() throws Exception {
        OWLUnaryPropertyAxiomImpl<OWLObjectPropertyExpression> testSubject0 = new OWLUnaryPropertyAxiomImpl<OWLObjectPropertyExpression>(
                Utils.mockObjectProperty(),
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
            protected int compareObjectOfSameType(OWLObject object) {
                return 0;
            }
        };
        OWLObjectPropertyExpression result0 = testSubject0.getProperty();
        boolean result1 = testSubject0.isLogicalAxiom();
        boolean result2 = testSubject0.isAnnotationAxiom();
        Set<OWLAnnotation> result3 = testSubject0.getAnnotations();
        testSubject0.accept(Utils.<OWLAnnotation> mockCollContainer());
        boolean result5 = testSubject0
                .equalsIgnoreAnnotations(mock(OWLAxiom.class));
        boolean result6 = testSubject0.isAnnotated();
        boolean result7 = testSubject0.isOfType(AxiomType.CLASS_ASSERTION);
        boolean result8 = testSubject0.isOfType(AxiomType.SUBCLASS_OF);
        String result10 = testSubject0.toString();
        Set<OWLEntity> result13 = testSubject0.getSignature();
        Set<OWLAnonymousIndividual> result14 = testSubject0
                .getAnonymousIndividuals();
        Set<OWLClass> result15 = testSubject0.getClassesInSignature();
        Set<OWLDataProperty> result16 = testSubject0
                .getDataPropertiesInSignature();
        Set<OWLObjectProperty> result17 = testSubject0
                .getObjectPropertiesInSignature();
        Set<OWLNamedIndividual> result18 = testSubject0
                .getIndividualsInSignature();
        Set<OWLDatatype> result19 = testSubject0.getDatatypesInSignature();
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result24 = testSubject0.accept(Utils.mockObject());
        testSubject0.accept(mock(OWLAxiomVisitor.class));
        Object result25 = testSubject0.accept(Utils.mockAxiom());
        OWLAxiom result26 = testSubject0.getAxiomWithoutAnnotations();
        AxiomType<?> result28 = testSubject0.getAxiomType();
    }
}
