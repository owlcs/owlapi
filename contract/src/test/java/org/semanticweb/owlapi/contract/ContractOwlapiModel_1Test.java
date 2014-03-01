package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiModel_1Test {

    @Test
    public void shouldTestAddAxiom() throws Exception {
        AddAxiom testSubject0 = new AddAxiom(Utils.getMockOntology(),
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

    @Test
    public void shouldTestAddImport() throws Exception {
        AddImport testSubject0 = new AddImport(Utils.getMockOntology(),
                mock(OWLImportsDeclaration.class));
        String result0 = testSubject0.toString();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result1 = testSubject0.accept(Utils.mockOntologyChange());
        boolean result3 = testSubject0.isAxiomChange();
        boolean result4 = testSubject0.isImportChange();
        OWLImportsDeclaration result5 = testSubject0.getImportDeclaration();
        OWLOntology result6 = testSubject0.getOntology();
    }

    @Test
    public void shouldTestAddOntologyAnnotation() throws Exception {
        AddOntologyAnnotation testSubject0 = new AddOntologyAnnotation(
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

    @Test
    public void shouldTestAxiomType() throws Exception {
        AxiomType<?> testSubject0 = AxiomType.ANNOTATION_ASSERTION;
        String result0 = testSubject0.toString();
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
    public void shouldTestClassExpressionType() throws Exception {
        ClassExpressionType testSubject0 = ClassExpressionType.DATA_ALL_VALUES_FROM;
        String result0 = testSubject0.toString();
        ClassExpressionType[] result1 = ClassExpressionType.values();
        String result3 = testSubject0.getName();
        String result4 = testSubject0.name();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDataRangeType() throws Exception {
        DataRangeType testSubject0 = DataRangeType.DATA_COMPLEMENT_OF;
        DataRangeType[] result0 = DataRangeType.values();
        String result2 = testSubject0.getName();
        String result3 = testSubject0.name();
        String result4 = testSubject0.toString();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDefaultChangeBroadcastStrategy() throws Exception {
        DefaultChangeBroadcastStrategy testSubject0 = new DefaultChangeBroadcastStrategy();
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestDefaultImpendingChangeBroadcastStrategy()
            throws Exception {
        DefaultImpendingChangeBroadcastStrategy testSubject0 = new DefaultImpendingChangeBroadcastStrategy();
        testSubject0.broadcastChanges(
                mock(ImpendingOWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestEDTChangeBroadcastStrategy() throws Exception {
        EDTChangeBroadcastStrategy testSubject0 = new EDTChangeBroadcastStrategy();
        testSubject0.broadcastChanges(mock(OWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestEntityType() throws Exception {
        EntityType<?> testSubject0 = EntityType.ANNOTATION_PROPERTY;
        String result0 = testSubject0.toString();
        List<EntityType<?>> result1 = EntityType.values();
        String result2 = testSubject0.getName();
        OWLRDFVocabulary result3 = testSubject0.getVocabulary();
    }

    @Test
    public void shouldTestImmutableOWLOntologyChangeException()
            throws Exception {
        ImmutableOWLOntologyChangeException testSubject0 = new ImmutableOWLOntologyChangeException(
                mock(OWLOntologyChange.class));
        OWLOntologyChange result0 = testSubject0.getChange();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void
            shouldTestInterfaceImpendingOWLOntologyChangeBroadcastStrategy()
                    throws Exception {
        ImpendingOWLOntologyChangeBroadcastStrategy testSubject0 = mock(ImpendingOWLOntologyChangeBroadcastStrategy.class);
        testSubject0.broadcastChanges(
                mock(ImpendingOWLOntologyChangeListener.class),
                Utils.mockList(mock(OWLOntologyChange.class)));
    }

    @Test
    public void shouldTestInterfaceImpendingOWLOntologyChangeListener()
            throws Exception {
        ImpendingOWLOntologyChangeListener testSubject0 = mock(ImpendingOWLOntologyChangeListener.class);
        testSubject0.handleImpendingOntologyChanges(Utils
                .mockList(mock(OWLOntologyChange.class)));
    }

    @Test
    public void shouldTestImportChange() throws Exception {
        ImportChange testSubject0 = new ImportChange(Utils.getMockOntology(),
                mock(OWLImportsDeclaration.class)) {

            @Override
            public void accept(OWLOntologyChangeVisitor visitor) {}

            @Override
            public <O> O accept(OWLOntologyChangeVisitorEx<O> visitor) {
                return null;
            }

            @Override
            public OWLOntologyChangeData getChangeData() {
                return mock(OWLOntologyChangeData.class);
            }
        };
        boolean result1 = testSubject0.isAxiomChange();
        boolean result2 = testSubject0.isImportChange();
        OWLImportsDeclaration result3 = testSubject0.getImportDeclaration();
        testSubject0.accept(mock(OWLOntologyChangeVisitor.class));
        Object result4 = testSubject0.accept(Utils.mockOntologyChange());
        OWLOntology result5 = testSubject0.getOntology();
        String result6 = testSubject0.toString();
    }

    public void shouldTestIRI() throws Exception {
        IRI testSubject0 = IRI("");
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
        String result17 = testSubject0.toString();
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
    public void shouldTestMissingImportEvent() throws Exception {
        MissingImportEvent testSubject0 = new MissingImportEvent(
                IRI("urn:aFake"), mock(OWLOntologyCreationException.class));
        IRI result0 = testSubject0.getImportedOntologyURI();
        OWLOntologyCreationException result1 = testSubject0
                .getCreationException();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestMissingImportHandlingStrategy() throws Exception {
        MissingImportHandlingStrategy testSubject0 = MissingImportHandlingStrategy.SILENT;
        MissingImportHandlingStrategy[] result0 = MissingImportHandlingStrategy
                .values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestInterfaceMissingImportListener() throws Exception {
        MissingImportListener testSubject0 = mock(MissingImportListener.class);
        testSubject0.importMissing(mock(MissingImportEvent.class));
    }

    @Test
    public void shouldTestNodeID() throws Exception {
        NodeID testSubject0 = NodeID.getNodeID("_:test1");
        String result0 = testSubject0.getID();
        NodeID result1 = NodeID.getNodeID("");
        NodeID result2 = NodeID.getNodeID(null);
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotation() throws Exception {
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
            throws Exception {
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
    public void shouldTestInterfaceOWLAnnotationAxiom() throws Exception {
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
    public void shouldTestInterfaceOWLAnnotationAxiomVisitor() throws Exception {
        OWLAnnotationAxiomVisitor testSubject0 = mock(OWLAnnotationAxiomVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationAxiomVisitorEx()
            throws Exception {
        OWLAnnotationAxiomVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationAxiom();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationObject() throws Exception {
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
            throws Exception {
        OWLAnnotationObjectVisitor testSubject0 = mock(OWLAnnotationObjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationObjectVisitorEx()
            throws Exception {
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
    public void shouldTestInterfaceOWLAnnotationProperty() throws Exception {
        OWLAnnotationProperty testSubject0 = mock(OWLAnnotationProperty.class);
        boolean result0 = testSubject0.isBuiltIn();
        boolean result1 = testSubject0.isComment();
        boolean result2 = testSubject0.isLabel();
        boolean result3 = testSubject0.isDeprecated();
        Set<OWLAnnotationProperty> result4 = testSubject0.getSubProperties(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationProperty> result5 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLAnnotationProperty> result6 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLAnnotationProperty> result7 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLAnnotationProperty> result8 = testSubject0.getSuperProperties(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationProperty> result9 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLAnnotation> result10 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result11 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result12 = testSubject0.accept(Utils.mockEntity());
        boolean result13 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result14 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result15 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result16 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
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
            throws Exception {
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
            throws Exception {
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
    public void shouldTestInterfaceOWLAnnotationSubject() throws Exception {
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
            throws Exception {
        OWLAnnotationSubjectVisitor testSubject0 = mock(OWLAnnotationSubjectVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationSubjectVisitorEx()
            throws Exception {
        OWLAnnotationSubjectVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationSubject();
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationValue() throws Exception {
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
    public void shouldTestInterfaceOWLAnnotationValueVisitor() throws Exception {
        OWLAnnotationValueVisitor testSubject0 = mock(OWLAnnotationValueVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAnnotationValueVisitorEx()
            throws Exception {
        OWLAnnotationValueVisitorEx<OWLObject> testSubject0 = Utils
                .mockAnnotationValue();
    }

    @Test
    public void shouldTestInterfaceOWLAnonymousClassExpression()
            throws Exception {
        OWLAnonymousClassExpression testSubject0 = mock(OWLAnonymousClassExpression.class);
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result2 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLAnonymousIndividual() throws Exception {
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
        Set<OWLClassExpression> result7 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result8 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result9 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result10 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result11 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result12 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result13 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result14 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result15 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result16 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result17 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result18 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result19 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
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
            throws Exception {
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
    public void shouldTestInterfaceOWLAxiom() throws Exception {
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
    public void shouldTestOWLAxiomChange() throws Exception {
        OWLAxiomChange testSubject0 = new OWLAxiomChange(
                Utils.getMockOntology(), mock(OWLAxiom.class)) {

            @Override
            public boolean isAddAxiom() {
                return false;
            }

            @Override
            public OWLOntologyChangeData getChangeData() {
                return mock(OWLOntologyChangeData.class);
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
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLAxiomVisitor() throws Exception {
        OWLAxiomVisitor testSubject0 = mock(OWLAxiomVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLAxiomVisitorEx() throws Exception {
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
    public void shouldTestInterfaceOWLBooleanClassExpression() throws Exception {
        OWLBooleanClassExpression testSubject0 = mock(OWLBooleanClassExpression.class);
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result2 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLCardinalityRestriction() throws Exception {
        OWLCardinalityRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> testSubject0 = mock(OWLCardinalityRestriction.class);
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
    public void shouldTestInterfaceOWLClass() throws Exception {
        OWLClass testSubject0 = mock(OWLClass.class);
        boolean result0 = testSubject0.isDefined(Utils.mockSet(Utils
                .getMockOntology()));
        boolean result1 = testSubject0.isDefined(Utils.getMockOntology());
        Set<OWLClassExpression> result2 = testSubject0.getSuperClasses(Utils
                .getMockOntology());
        Set<OWLClassExpression> result3 = testSubject0.getSuperClasses(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result4 = testSubject0.getSubClasses(Utils
                .getMockOntology());
        Set<OWLClassExpression> result5 = testSubject0.getSubClasses(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result6 = testSubject0
                .getEquivalentClasses(Utils.getMockOntology());
        Set<OWLClassExpression> result7 = testSubject0
                .getEquivalentClasses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result8 = testSubject0.getDisjointClasses(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result9 = testSubject0.getDisjointClasses(Utils
                .getMockOntology());
        Set<OWLIndividual> result10 = testSubject0.getIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result11 = testSubject0.getIndividuals(Utils
                .mockSet(Utils.getMockOntology()));
        Object result12 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result13 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result14 = testSubject0.asOWLClass();
            }
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
        Set<OWLAnnotation> result37 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result38 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        testSubject0.accept(mock(OWLEntityVisitor.class));
        Object result39 = testSubject0.accept(Utils.mockEntity());
        boolean result40 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result41 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAxiom> result42 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAnnotationAssertionAxiom> result43 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        boolean result44 = testSubject0.isBuiltIn();
        EntityType<?> result45 = testSubject0.getEntityType();
        boolean result47 = !testSubject0.isAnonymous();
        if (!!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result48 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLClassAssertionAxiom() throws Exception {
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
    public void shouldTestInterfaceOWLClassAxiom() throws Exception {
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
    public void shouldTestInterfaceOWLClassExpression() throws Exception {
        OWLClassExpression testSubject0 = Utils.mockAnonClass();
        Object result0 = testSubject0.accept(Utils.mockClassExpression());
        testSubject0.accept(mock(OWLClassExpressionVisitor.class));
        boolean result1 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result2 = testSubject0.asOWLClass();
            }
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
    public void shouldTestInterfaceOWLClassExpressionVisitor() throws Exception {
        OWLClassExpressionVisitor testSubject0 = mock(OWLClassExpressionVisitor.class);
    }

    @Test
    public void shouldTestInterfaceOWLClassExpressionVisitorEx()
            throws Exception {
        OWLClassExpressionVisitorEx<OWLObject> testSubject0 = Utils
                .mockClassExpression();
    }

    @Test
    public void shouldTestInterfaceOWLDataAllValuesFrom() throws Exception {
        OWLDataAllValuesFrom testSubject0 = mock(OWLDataAllValuesFrom.class);
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
            throws Exception {
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
    public void shouldTestInterfaceOWLDataComplementOf() throws Exception {
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
    public void shouldTestInterfaceOWLDataExactCardinality() throws Exception {
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

    public void shouldTestInterfaceOWLDataFactory() throws Exception {
        OWLDataFactory testSubject0 = mock(OWLDataFactory.class);
        OWLEntity result0 = testSubject0.getOWLEntity(EntityType.CLASS,
                IRI("urn:aFake"));
        OWLClass result1 = testSubject0.getOWLClass(IRI("urn:aFake"));
        OWLClass result2 = testSubject0.getOWLClass("",
                new DefaultPrefixManager());
        OWLEquivalentClassesAxiom result3 = testSubject0
                .getOWLEquivalentClassesAxiom(
                        Utils.mockSet(Utils.mockAnonClass()),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentClassesAxiom result4 = testSubject0
                .getOWLEquivalentClassesAxiom(Utils.mockAnonClass(),
                        Utils.mockAnonClass());
        OWLEquivalentClassesAxiom result5 = testSubject0
                .getOWLEquivalentClassesAxiom(Utils.mockAnonClass(),
                        Utils.mockAnonClass(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentClassesAxiom result6 = testSubject0
                .getOWLEquivalentClassesAxiom(Utils.mockSet(Utils
                        .mockAnonClass()));
        OWLEquivalentClassesAxiom result7 = testSubject0
                .getOWLEquivalentClassesAxiom(Utils.mockAnonClass());
        OWLDisjointClassesAxiom result8 = testSubject0
                .getOWLDisjointClassesAxiom(
                        Utils.mockSet(Utils.mockAnonClass()),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDisjointClassesAxiom result9 = testSubject0
                .getOWLDisjointClassesAxiom(Utils.mockSet(Utils.mockAnonClass()));
        OWLDisjointClassesAxiom result10 = testSubject0
                .getOWLDisjointClassesAxiom(Utils.mockAnonClass());
        OWLClass result11 = testSubject0.getOWLThing();
        OWLClass result12 = testSubject0.getOWLNothing();
        OWLObjectProperty result13 = testSubject0.getOWLTopObjectProperty();
        OWLDataProperty result14 = testSubject0.getOWLTopDataProperty();
        OWLObjectProperty result15 = testSubject0.getOWLBottomObjectProperty();
        OWLDataProperty result16 = testSubject0.getOWLBottomDataProperty();
        OWLDatatype result17 = testSubject0.getTopDatatype();
        OWLObjectProperty result18 = testSubject0.getOWLObjectProperty("",
                new DefaultPrefixManager());
        OWLObjectProperty result19 = testSubject0
                .getOWLObjectProperty(IRI("urn:aFake"));
        OWLObjectInverseOf result20 = testSubject0.getOWLObjectInverseOf(Utils
                .mockObjectProperty());
        OWLDataProperty result21 = testSubject0.getOWLDataProperty("",
                new DefaultPrefixManager());
        OWLDataProperty result22 = testSubject0
                .getOWLDataProperty(IRI("urn:aFake"));
        OWLNamedIndividual result23 = testSubject0.getOWLNamedIndividual("",
                new DefaultPrefixManager());
        OWLNamedIndividual result24 = testSubject0
                .getOWLNamedIndividual(IRI("urn:aFake"));
        OWLAnonymousIndividual result25 = testSubject0
                .getOWLAnonymousIndividual("");
        OWLAnonymousIndividual result26 = testSubject0
                .getOWLAnonymousIndividual();
        OWLAnnotationProperty result27 = testSubject0
                .getOWLAnnotationProperty(IRI("urn:aFake"));
        OWLAnnotationProperty result28 = testSubject0.getOWLAnnotationProperty(
                "", new DefaultPrefixManager());
        OWLAnnotationProperty result29 = testSubject0.getRDFSLabel();
        OWLAnnotationProperty result30 = testSubject0.getRDFSComment();
        OWLAnnotationProperty result31 = testSubject0.getRDFSSeeAlso();
        OWLAnnotationProperty result32 = testSubject0.getRDFSIsDefinedBy();
        OWLAnnotationProperty result33 = testSubject0.getOWLVersionInfo();
        OWLAnnotationProperty result34 = testSubject0
                .getOWLBackwardCompatibleWith();
        OWLAnnotationProperty result35 = testSubject0.getOWLIncompatibleWith();
        OWLAnnotationProperty result36 = testSubject0.getOWLDeprecated();
        OWLDatatype result37 = testSubject0.getRDFPlainLiteral();
        OWLDatatype result38 = testSubject0.getOWLDatatype(IRI("urn:aFake"));
        OWLDatatype result39 = testSubject0.getOWLDatatype("",
                new DefaultPrefixManager());
        OWLDatatype result40 = testSubject0.getIntegerOWLDatatype();
        OWLDatatype result41 = testSubject0.getFloatOWLDatatype();
        OWLDatatype result42 = testSubject0.getDoubleOWLDatatype();
        OWLDatatype result43 = testSubject0.getBooleanOWLDatatype();
        OWLLiteral result44 = testSubject0.getOWLLiteral(0);
        OWLLiteral result45 = testSubject0.getOWLLiteral(0D);
        OWLLiteral result46 = testSubject0.getOWLLiteral(false);
        OWLLiteral result47 = testSubject0.getOWLLiteral(0F);
        OWLLiteral result48 = testSubject0.getOWLLiteral("");
        OWLLiteral result49 = testSubject0.getOWLLiteral("", "en");
        OWLLiteral result50 = testSubject0.getOWLLiteral("",
                OWL2Datatype.XSD_STRING);
        OWLLiteral result51 = testSubject0.getOWLLiteral("",
                mock(OWLDatatype.class));
        OWLDataOneOf result61 = testSubject0.getOWLDataOneOf(Utils
                .mockSet(mock(OWLLiteral.class)));
        OWLDataOneOf result62 = testSubject0
                .getOWLDataOneOf(mock(OWLLiteral[].class));
        OWLDataComplementOf result63 = testSubject0
                .getOWLDataComplementOf(mock(OWLDataRange.class));
        OWLDatatypeRestriction result64 = testSubject0
                .getOWLDatatypeRestriction(mock(OWLDatatype.class),
                        mock(OWLFacetRestriction[].class));
        OWLDatatypeRestriction result65 = testSubject0
                .getOWLDatatypeRestriction(mock(OWLDatatype.class),
                        OWLFacet.MAX_INCLUSIVE, mock(OWLLiteral.class));
        OWLDatatypeRestriction result66 = testSubject0
                .getOWLDatatypeRestriction(mock(OWLDatatype.class),
                        Utils.mockSet(mock(OWLFacetRestriction.class)));
        OWLDatatypeRestriction result67 = testSubject0
                .getOWLDatatypeMinInclusiveRestriction(0D);
        OWLDatatypeRestriction result68 = testSubject0
                .getOWLDatatypeMinInclusiveRestriction(0);
        OWLDatatypeRestriction result69 = testSubject0
                .getOWLDatatypeMaxInclusiveRestriction(0);
        OWLDatatypeRestriction result70 = testSubject0
                .getOWLDatatypeMaxInclusiveRestriction(0D);
        OWLDatatypeRestriction result71 = testSubject0
                .getOWLDatatypeMinMaxInclusiveRestriction(0, 0);
        OWLDatatypeRestriction result72 = testSubject0
                .getOWLDatatypeMinMaxInclusiveRestriction(0D, 0D);
        OWLDatatypeRestriction result73 = testSubject0
                .getOWLDatatypeMinExclusiveRestriction(0D);
        OWLDatatypeRestriction result74 = testSubject0
                .getOWLDatatypeMinExclusiveRestriction(0);
        OWLDatatypeRestriction result75 = testSubject0
                .getOWLDatatypeMaxExclusiveRestriction(0D);
        OWLDatatypeRestriction result76 = testSubject0
                .getOWLDatatypeMaxExclusiveRestriction(0);
        OWLDatatypeRestriction result77 = testSubject0
                .getOWLDatatypeMinMaxExclusiveRestriction(0, 0);
        OWLDatatypeRestriction result78 = testSubject0
                .getOWLDatatypeMinMaxExclusiveRestriction(0D, 0D);
        OWLFacetRestriction result79 = testSubject0.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, 0D);
        OWLFacetRestriction result80 = testSubject0.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, 0F);
        OWLFacetRestriction result81 = testSubject0.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, mock(OWLLiteral.class));
        OWLFacetRestriction result82 = testSubject0.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, 0);
        OWLDataUnionOf result83 = testSubject0
                .getOWLDataUnionOf(mock(OWLDataRange[].class));
        OWLDataUnionOf result84 = testSubject0.getOWLDataUnionOf(Utils
                .mockSet(mock(OWLDatatype.class)));
        OWLDataIntersectionOf result85 = testSubject0
                .getOWLDataIntersectionOf(Utils
                        .mockSet(mock(OWLDatatype.class)));
        OWLDataIntersectionOf result86 = testSubject0
                .getOWLDataIntersectionOf(mock(OWLDataRange[].class));
        OWLObjectIntersectionOf result87 = testSubject0
                .getOWLObjectIntersectionOf(Utils.mockSet(Utils.mockAnonClass()));
        OWLObjectIntersectionOf result88 = testSubject0
                .getOWLObjectIntersectionOf(Utils.mockAnonClass());
        OWLDataSomeValuesFrom result89 = testSubject0
                .getOWLDataSomeValuesFrom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLDataAllValuesFrom result90 = testSubject0
                .getOWLDataAllValuesFrom(mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLDataExactCardinality result91 = testSubject0
                .getOWLDataExactCardinality(0,
                        mock(OWLDataPropertyExpression.class));
        OWLDataExactCardinality result92 = testSubject0
                .getOWLDataExactCardinality(0,
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLDataMaxCardinality result93 = testSubject0.getOWLDataMaxCardinality(
                0, mock(OWLDataPropertyExpression.class),
                mock(OWLDataRange.class));
        OWLDataMaxCardinality result94 = testSubject0.getOWLDataMaxCardinality(
                0, mock(OWLDataPropertyExpression.class));
        OWLDataMinCardinality result95 = testSubject0.getOWLDataMinCardinality(
                0, mock(OWLDataPropertyExpression.class));
        OWLDataMinCardinality result96 = testSubject0.getOWLDataMinCardinality(
                0, mock(OWLDataPropertyExpression.class),
                mock(OWLDataRange.class));
        OWLDataHasValue result97 = testSubject0.getOWLDataHasValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class));
        OWLObjectComplementOf result98 = testSubject0
                .getOWLObjectComplementOf(Utils.mockAnonClass());
        OWLObjectOneOf result99 = testSubject0.getOWLObjectOneOf(Utils
                .mockSet(mock(OWLIndividual.class)));
        OWLObjectOneOf result100 = testSubject0
                .getOWLObjectOneOf(mock(OWLIndividual[].class));
        OWLObjectAllValuesFrom result101 = testSubject0
                .getOWLObjectAllValuesFrom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectSomeValuesFrom result102 = testSubject0
                .getOWLObjectSomeValuesFrom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectExactCardinality result103 = testSubject0
                .getOWLObjectExactCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectExactCardinality result104 = testSubject0
                .getOWLObjectExactCardinality(0, Utils.mockObjectProperty());
        OWLObjectMinCardinality result105 = testSubject0
                .getOWLObjectMinCardinality(0, Utils.mockObjectProperty());
        OWLObjectMinCardinality result106 = testSubject0
                .getOWLObjectMinCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectMaxCardinality result107 = testSubject0
                .getOWLObjectMaxCardinality(0, Utils.mockObjectProperty());
        OWLObjectMaxCardinality result108 = testSubject0
                .getOWLObjectMaxCardinality(0, Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectHasSelf result109 = testSubject0.getOWLObjectHasSelf(Utils
                .mockObjectProperty());
        OWLObjectHasValue result110 = testSubject0.getOWLObjectHasValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class));
        OWLObjectUnionOf result111 = testSubject0.getOWLObjectUnionOf(Utils
                .mockAnonClass());
        OWLObjectUnionOf result112 = testSubject0.getOWLObjectUnionOf(Utils
                .mockSet(Utils.mockAnonClass()));
        OWLDeclarationAxiom result113 = testSubject0
                .getOWLDeclarationAxiom(Utils.mockOWLEntity(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDeclarationAxiom result114 = testSubject0
                .getOWLDeclarationAxiom(Utils.mockOWLEntity());
        OWLSubClassOfAxiom result115 = testSubject0.getOWLSubClassOfAxiom(
                Utils.mockAnonClass(), Utils.mockAnonClass());
        OWLSubClassOfAxiom result116 = testSubject0.getOWLSubClassOfAxiom(
                Utils.mockAnonClass(), Utils.mockAnonClass(),
                Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDisjointUnionAxiom result117 = testSubject0
                .getOWLDisjointUnionAxiom(mock(OWLClass.class),
                        Utils.mockSet(Utils.mockAnonClass()),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDisjointUnionAxiom result118 = testSubject0
                .getOWLDisjointUnionAxiom(mock(OWLClass.class),
                        Utils.mockSet(Utils.mockAnonClass()));
        OWLSubObjectPropertyOfAxiom result119 = testSubject0
                .getOWLSubObjectPropertyOfAxiom(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty());
        OWLSubObjectPropertyOfAxiom result120 = testSubject0
                .getOWLSubObjectPropertyOfAxiom(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLSubPropertyChainOfAxiom result121 = testSubject0
                .getOWLSubPropertyChainOfAxiom(
                        Utils.mockList(Utils.mockObjectProperty()),
                        Utils.mockObjectProperty());
        OWLSubPropertyChainOfAxiom result122 = testSubject0
                .getOWLSubPropertyChainOfAxiom(
                        Utils.mockList(Utils.mockObjectProperty()),
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentObjectPropertiesAxiom result123 = testSubject0
                .getOWLEquivalentObjectPropertiesAxiom(
                        Utils.mockSet(Utils.mockObjectProperty()),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentObjectPropertiesAxiom result124 = testSubject0
                .getOWLEquivalentObjectPropertiesAxiom(Utils.mockSet(Utils
                        .mockObjectProperty()));
        OWLEquivalentObjectPropertiesAxiom result125 = testSubject0
                .getOWLEquivalentObjectPropertiesAxiom(mock(OWLObjectPropertyExpression[].class));
        OWLEquivalentObjectPropertiesAxiom result126 = testSubject0
                .getOWLEquivalentObjectPropertiesAxiom(
                        Utils.mockObjectProperty(), Utils.mockObjectProperty());
        OWLEquivalentObjectPropertiesAxiom result127 = testSubject0
                .getOWLEquivalentObjectPropertiesAxiom(
                        Utils.mockObjectProperty(), Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDisjointObjectPropertiesAxiom result128 = testSubject0
                .getOWLDisjointObjectPropertiesAxiom(Utils.mockSet(Utils
                        .mockObjectProperty()));
        OWLDisjointObjectPropertiesAxiom result129 = testSubject0
                .getOWLDisjointObjectPropertiesAxiom(mock(OWLObjectPropertyExpression[].class));
        OWLDisjointObjectPropertiesAxiom result130 = testSubject0
                .getOWLDisjointObjectPropertiesAxiom(
                        Utils.mockSet(Utils.mockObjectProperty()),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLInverseObjectPropertiesAxiom result131 = testSubject0
                .getOWLInverseObjectPropertiesAxiom(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty());
        OWLInverseObjectPropertiesAxiom result132 = testSubject0
                .getOWLInverseObjectPropertiesAxiom(Utils.mockObjectProperty(),
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLObjectPropertyDomainAxiom result133 = testSubject0
                .getOWLObjectPropertyDomainAxiom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectPropertyDomainAxiom result134 = testSubject0
                .getOWLObjectPropertyDomainAxiom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLObjectPropertyRangeAxiom result135 = testSubject0
                .getOWLObjectPropertyRangeAxiom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass());
        OWLObjectPropertyRangeAxiom result136 = testSubject0
                .getOWLObjectPropertyRangeAxiom(Utils.mockObjectProperty(),
                        Utils.mockAnonClass(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLFunctionalObjectPropertyAxiom result137 = testSubject0
                .getOWLFunctionalObjectPropertyAxiom(
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLFunctionalObjectPropertyAxiom result138 = testSubject0
                .getOWLFunctionalObjectPropertyAxiom(Utils.mockObjectProperty());
        OWLInverseFunctionalObjectPropertyAxiom result139 = testSubject0
                .getOWLInverseFunctionalObjectPropertyAxiom(Utils
                        .mockObjectProperty());
        OWLInverseFunctionalObjectPropertyAxiom result140 = testSubject0
                .getOWLInverseFunctionalObjectPropertyAxiom(
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLReflexiveObjectPropertyAxiom result141 = testSubject0
                .getOWLReflexiveObjectPropertyAxiom(Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLReflexiveObjectPropertyAxiom result142 = testSubject0
                .getOWLReflexiveObjectPropertyAxiom(Utils.mockObjectProperty());
        OWLIrreflexiveObjectPropertyAxiom result143 = testSubject0
                .getOWLIrreflexiveObjectPropertyAxiom(Utils
                        .mockObjectProperty());
        OWLIrreflexiveObjectPropertyAxiom result144 = testSubject0
                .getOWLIrreflexiveObjectPropertyAxiom(
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLSymmetricObjectPropertyAxiom result145 = testSubject0
                .getOWLSymmetricObjectPropertyAxiom(Utils.mockObjectProperty());
        OWLSymmetricObjectPropertyAxiom result146 = testSubject0
                .getOWLSymmetricObjectPropertyAxiom(Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAsymmetricObjectPropertyAxiom result147 = testSubject0
                .getOWLAsymmetricObjectPropertyAxiom(Utils.mockObjectProperty());
        OWLAsymmetricObjectPropertyAxiom result148 = testSubject0
                .getOWLAsymmetricObjectPropertyAxiom(
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLTransitiveObjectPropertyAxiom result149 = testSubject0
                .getOWLTransitiveObjectPropertyAxiom(
                        Utils.mockObjectProperty(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLTransitiveObjectPropertyAxiom result150 = testSubject0
                .getOWLTransitiveObjectPropertyAxiom(Utils.mockObjectProperty());
        OWLSubDataPropertyOfAxiom result151 = testSubject0
                .getOWLSubDataPropertyOfAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataPropertyExpression.class));
        OWLSubDataPropertyOfAxiom result152 = testSubject0
                .getOWLSubDataPropertyOfAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataPropertyExpression.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentDataPropertiesAxiom result153 = testSubject0
                .getOWLEquivalentDataPropertiesAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataPropertyExpression.class));
        OWLEquivalentDataPropertiesAxiom result154 = testSubject0
                .getOWLEquivalentDataPropertiesAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataPropertyExpression.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentDataPropertiesAxiom result155 = testSubject0
                .getOWLEquivalentDataPropertiesAxiom(Utils
                        .mockSet(mock(OWLDataPropertyExpression.class)));
        OWLEquivalentDataPropertiesAxiom result156 = testSubject0
                .getOWLEquivalentDataPropertiesAxiom(
                        Utils.mockSet(mock(OWLDataPropertyExpression.class)),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLEquivalentDataPropertiesAxiom result157 = testSubject0
                .getOWLEquivalentDataPropertiesAxiom(mock(OWLDataPropertyExpression[].class));
        OWLDisjointDataPropertiesAxiom result158 = testSubject0
                .getOWLDisjointDataPropertiesAxiom(
                        Utils.mockSet(mock(OWLDataPropertyExpression.class)),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDisjointDataPropertiesAxiom result159 = testSubject0
                .getOWLDisjointDataPropertiesAxiom(mock(OWLDataPropertyExpression[].class));
        OWLDisjointDataPropertiesAxiom result160 = testSubject0
                .getOWLDisjointDataPropertiesAxiom(Utils
                        .mockSet(mock(OWLDataPropertyExpression.class)));
        OWLDataPropertyDomainAxiom result161 = testSubject0
                .getOWLDataPropertyDomainAxiom(
                        mock(OWLDataPropertyExpression.class),
                        Utils.mockAnonClass(),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDataPropertyDomainAxiom result162 = testSubject0
                .getOWLDataPropertyDomainAxiom(
                        mock(OWLDataPropertyExpression.class),
                        Utils.mockAnonClass());
        OWLDataPropertyRangeAxiom result163 = testSubject0
                .getOWLDataPropertyRangeAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDataPropertyRangeAxiom result164 = testSubject0
                .getOWLDataPropertyRangeAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLDataRange.class));
        OWLFunctionalDataPropertyAxiom result165 = testSubject0
                .getOWLFunctionalDataPropertyAxiom(
                        mock(OWLDataPropertyExpression.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLFunctionalDataPropertyAxiom result166 = testSubject0
                .getOWLFunctionalDataPropertyAxiom(mock(OWLDataPropertyExpression.class));
        OWLHasKeyAxiom result167 = testSubject0.getOWLHasKeyAxiom(
                Utils.mockAnonClass(),
                new HashSet<OWLPropertyExpression<?, ?>>(),
                Utils.mockSet(mock(OWLAnnotation.class)));
        OWLHasKeyAxiom result168 = testSubject0.getOWLHasKeyAxiom(
                Utils.mockAnonClass(), mock(OWLPropertyExpression[].class));
        OWLHasKeyAxiom result169 = testSubject0.getOWLHasKeyAxiom(
                Utils.mockAnonClass(),
                new HashSet<OWLPropertyExpression<?, ?>>());
        OWLDatatypeDefinitionAxiom result170 = testSubject0
                .getOWLDatatypeDefinitionAxiom(mock(OWLDatatype.class),
                        mock(OWLDataRange.class));
        OWLDatatypeDefinitionAxiom result171 = testSubject0
                .getOWLDatatypeDefinitionAxiom(mock(OWLDatatype.class),
                        mock(OWLDataRange.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLSameIndividualAxiom result172 = testSubject0
                .getOWLSameIndividualAxiom(Utils
                        .mockSet(mock(OWLIndividual.class)));
        OWLSameIndividualAxiom result173 = testSubject0
                .getOWLSameIndividualAxiom(mock(OWLIndividual[].class));
        OWLSameIndividualAxiom result174 = testSubject0
                .getOWLSameIndividualAxiom(
                        Utils.mockSet(mock(OWLIndividual.class)),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDifferentIndividualsAxiom result175 = testSubject0
                .getOWLDifferentIndividualsAxiom(mock(OWLIndividual[].class));
        OWLDifferentIndividualsAxiom result176 = testSubject0
                .getOWLDifferentIndividualsAxiom(Utils
                        .mockSet(mock(OWLIndividual.class)));
        OWLDifferentIndividualsAxiom result177 = testSubject0
                .getOWLDifferentIndividualsAxiom(
                        Utils.mockSet(mock(OWLIndividual.class)),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLClassAssertionAxiom result178 = testSubject0
                .getOWLClassAssertionAxiom(Utils.mockAnonClass(),
                        mock(OWLIndividual.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLClassAssertionAxiom result179 = testSubject0
                .getOWLClassAssertionAxiom(Utils.mockAnonClass(),
                        mock(OWLIndividual.class));
        OWLObjectPropertyAssertionAxiom result180 = testSubject0
                .getOWLObjectPropertyAssertionAxiom(Utils.mockObjectProperty(),
                        mock(OWLIndividual.class), mock(OWLIndividual.class));
        OWLObjectPropertyAssertionAxiom result181 = testSubject0
                .getOWLObjectPropertyAssertionAxiom(Utils.mockObjectProperty(),
                        mock(OWLIndividual.class), mock(OWLIndividual.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLNegativeObjectPropertyAssertionAxiom result182 = testSubject0
                .getOWLNegativeObjectPropertyAssertionAxiom(
                        Utils.mockObjectProperty(), mock(OWLIndividual.class),
                        mock(OWLIndividual.class));
        OWLNegativeObjectPropertyAssertionAxiom result183 = testSubject0
                .getOWLNegativeObjectPropertyAssertionAxiom(
                        Utils.mockObjectProperty(), mock(OWLIndividual.class),
                        mock(OWLIndividual.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDataPropertyAssertionAxiom result184 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), false);
        OWLDataPropertyAssertionAxiom result185 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class));
        OWLDataPropertyAssertionAxiom result186 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLDataPropertyAssertionAxiom result187 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), 0);
        OWLDataPropertyAssertionAxiom result188 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), 0D);
        OWLDataPropertyAssertionAxiom result189 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), 0F);
        OWLDataPropertyAssertionAxiom result190 = testSubject0
                .getOWLDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), "");
        OWLNegativeDataPropertyAssertionAxiom result191 = testSubject0
                .getOWLNegativeDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class));
        OWLNegativeDataPropertyAssertionAxiom result192 = testSubject0
                .getOWLNegativeDataPropertyAssertionAxiom(
                        mock(OWLDataPropertyExpression.class),
                        mock(OWLIndividual.class), mock(OWLLiteral.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotation result193 = testSubject0.getOWLAnnotation(
                mock(OWLAnnotationProperty.class),
                mock(OWLAnnotationValue.class));
        OWLAnnotation result194 = testSubject0.getOWLAnnotation(
                mock(OWLAnnotationProperty.class),
                mock(OWLAnnotationValue.class),
                Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotationAssertionAxiom result195 = testSubject0
                .getOWLAnnotationAssertionAxiom(
                        mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationSubject.class),
                        mock(OWLAnnotationValue.class));
        OWLAnnotationAssertionAxiom result196 = testSubject0
                .getOWLAnnotationAssertionAxiom(
                        mock(OWLAnnotationSubject.class),
                        mock(OWLAnnotation.class));
        OWLAnnotationAssertionAxiom result197 = testSubject0
                .getOWLAnnotationAssertionAxiom(
                        mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationSubject.class),
                        mock(OWLAnnotationValue.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotationAssertionAxiom result198 = testSubject0
                .getOWLAnnotationAssertionAxiom(
                        mock(OWLAnnotationSubject.class),
                        mock(OWLAnnotation.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotationAssertionAxiom result199 = testSubject0
                .getDeprecatedOWLAnnotationAssertionAxiom(IRI("urn:aFake"));
        OWLImportsDeclaration result200 = testSubject0
                .getOWLImportsDeclaration(IRI("urn:aFake"));
        OWLAnnotationPropertyDomainAxiom result201 = testSubject0
                .getOWLAnnotationPropertyDomainAxiom(
                        mock(OWLAnnotationProperty.class), IRI("urn:aFake"));
        OWLAnnotationPropertyDomainAxiom result202 = testSubject0
                .getOWLAnnotationPropertyDomainAxiom(
                        mock(OWLAnnotationProperty.class), IRI("urn:aFake"),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAnnotationPropertyRangeAxiom result203 = testSubject0
                .getOWLAnnotationPropertyRangeAxiom(
                        mock(OWLAnnotationProperty.class), IRI("urn:aFake"));
        OWLAnnotationPropertyRangeAxiom result204 = testSubject0
                .getOWLAnnotationPropertyRangeAxiom(
                        mock(OWLAnnotationProperty.class), IRI("urn:aFake"),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        OWLSubAnnotationPropertyOfAxiom result205 = testSubject0
                .getOWLSubAnnotationPropertyOfAxiom(
                        mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationProperty.class));
        OWLSubAnnotationPropertyOfAxiom result206 = testSubject0
                .getOWLSubAnnotationPropertyOfAxiom(
                        mock(OWLAnnotationProperty.class),
                        mock(OWLAnnotationProperty.class),
                        Utils.mockSet(mock(OWLAnnotation.class)));
        testSubject0.purge();
        SWRLRule result209 = testSubject0.getSWRLRule(
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(SWRLAtom.class)));
        SWRLRule result210 = testSubject0.getSWRLRule(
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(SWRLAtom.class)),
                Utils.mockSet(mock(OWLAnnotation.class)));
        SWRLClassAtom result211 = testSubject0.getSWRLClassAtom(
                Utils.mockAnonClass(), mock(SWRLIArgument.class));
        SWRLDataRangeAtom result212 = testSubject0.getSWRLDataRangeAtom(
                mock(OWLDataRange.class), mock(SWRLDArgument.class));
        SWRLObjectPropertyAtom result213 = testSubject0
                .getSWRLObjectPropertyAtom(Utils.mockObjectProperty(),
                        mock(SWRLIArgument.class), mock(SWRLIArgument.class));
        SWRLDataPropertyAtom result214 = testSubject0.getSWRLDataPropertyAtom(
                mock(OWLDataPropertyExpression.class),
                mock(SWRLIArgument.class), mock(SWRLDArgument.class));
        SWRLBuiltInAtom result215 = testSubject0.getSWRLBuiltInAtom(
                IRI("urn:aFake"), Utils.mockList(mock(SWRLDArgument.class)));
        SWRLVariable result216 = testSubject0.getSWRLVariable(IRI("urn:aFake"));
        SWRLIndividualArgument result217 = testSubject0
                .getSWRLIndividualArgument(mock(OWLIndividual.class));
        SWRLLiteralArgument result218 = testSubject0
                .getSWRLLiteralArgument(mock(OWLLiteral.class));
        SWRLSameIndividualAtom result219 = testSubject0
                .getSWRLSameIndividualAtom(mock(SWRLIArgument.class),
                        mock(SWRLIArgument.class));
        SWRLDifferentIndividualsAtom result220 = testSubject0
                .getSWRLDifferentIndividualsAtom(mock(SWRLIArgument.class),
                        mock(SWRLIArgument.class));
    }

    @Test
    public void shouldTestInterfaceOWLDataHasValue() throws Exception {
        OWLDataHasValue testSubject0 = mock(OWLDataHasValue.class);
        OWLObject result0 = testSubject0.getValue();
        OWLClassExpression result1 = testSubject0.asSomeValuesFrom();
        OWLDataPropertyExpression result2 = testSubject0.getProperty();
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
    public void shouldTestInterfaceOWLDataIntersectionOf() throws Exception {
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
    public void shouldTestInterfaceOWLDataMaxCardinality() throws Exception {
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
    public void shouldTestInterfaceOWLDataMinCardinality() throws Exception {
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
    public void shouldTestInterfaceOWLDataOneOf() throws Exception {
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
