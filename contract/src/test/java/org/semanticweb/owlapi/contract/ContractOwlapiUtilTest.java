package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyCharacteristicAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologySetProvider;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.*;
import org.semanticweb.owlapi.util.DLExpressivityChecker.Construct;
import org.semanticweb.owlapi.util.ImportsStructureObjectSorter.ObjectSelector;
import org.semanticweb.owlapi.vocab.OWLFacet;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractOwlapiUtilTest {

    @Test
    public void shouldTestAbstractOWLOntologyStorer() throws Exception {
        AbstractOWLOntologyStorer testSubject0 = new AbstractOWLOntologyStorer() {

            private static final long serialVersionUID = 30406L;

            @Override
            public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
                return false;
            }

            @Override
            protected void storeOntology(OWLOntologyManager manager,
                    OWLOntology ontology, Writer writer,
                    OWLOntologyFormat format)
                    throws OWLOntologyStorageException {}

            @Override
            protected void storeOntology(OWLOntology ontology, Writer writer,
                    OWLOntologyFormat format)
                    throws OWLOntologyStorageException {}
        };
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestAnnotationValueShortFormProvider() throws Exception {
        AnnotationValueShortFormProvider testSubject0 = new AnnotationValueShortFormProvider(
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class), mock(OWLOntologySetProvider.class));
        AnnotationValueShortFormProvider testSubject1 = new AnnotationValueShortFormProvider(
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class), mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class));
        AnnotationValueShortFormProvider testSubject2 = new AnnotationValueShortFormProvider(
                mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class),
                mock(IRIShortFormProvider.class),
                Utils.mockList(mock(OWLAnnotationProperty.class)),
                mock(Map.class));
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        List<OWLAnnotationProperty> result1 = testSubject0
                .getAnnotationProperties();
        Map<OWLAnnotationProperty, List<String>> result2 = testSubject0
                .getPreferredLanguageMap();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestAxiomSubjectProvider() throws Exception {
        AxiomSubjectProvider testSubject0 = new AxiomSubjectProvider();
        OWLObject result0 = testSubject0.getSubject(mock(OWLAxiom.class));
        String result1 = testSubject0.toString();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldTestAxiomTypeProvider() throws Exception {
        AxiomTypeProvider testSubject0 = new AxiomTypeProvider();
        AxiomType<?> result0 = testSubject0.getAxiomType(mock(OWLAxiom.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceBidirectionalShortFormProvider()
            throws Exception {
        BidirectionalShortFormProvider testSubject0 = mock(BidirectionalShortFormProvider.class);
        Set<OWLEntity> result0 = testSubject0.getEntities("");
        OWLEntity result1 = testSubject0.getEntity("");
        Set<String> result2 = testSubject0.getShortForms();
        testSubject0.dispose();
        String result3 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestBidirectionalShortFormProviderAdapter()
            throws Exception {
        BidirectionalShortFormProviderAdapter testSubject0 = new BidirectionalShortFormProviderAdapter(
                mock(ShortFormProvider.class));
        BidirectionalShortFormProviderAdapter testSubject1 = new BidirectionalShortFormProviderAdapter(
                Utils.mockSet(Utils.getMockOntology()),
                mock(ShortFormProvider.class));
        BidirectionalShortFormProviderAdapter testSubject2 = new BidirectionalShortFormProviderAdapter(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()),
                mock(ShortFormProvider.class));
        testSubject0.dispose();
        testSubject0.add(Utils.mockOWLEntity());
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Set<OWLEntity> result1 = testSubject0.getEntities("");
        OWLEntity result2 = testSubject0.getEntity("");
        Set<String> result3 = testSubject0.getShortForms();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestCachingBidirectionalShortFormProvider()
            throws Exception {
        CachingBidirectionalShortFormProvider testSubject0 = new CachingBidirectionalShortFormProvider() {

            @Override
            protected String generateShortForm(OWLEntity entity) {
                return null;
            }
        };
        testSubject0.add(Utils.mockOWLEntity());
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Set<OWLEntity> result1 = testSubject0.getEntities("");
        OWLEntity result2 = testSubject0.getEntity("");
        Set<String> result3 = testSubject0.getShortForms();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestCollectionFactory() throws Exception {
        CollectionFactory testSubject0 = new CollectionFactory();
        Map<Object, Object> result0 = CollectionFactory.createMap();
        Set<Object> result1 = CollectionFactory
                .getCopyOnRequestSetFromMutableCollection(Utils
                        .mockCollection());
        Set<Object> result2 = CollectionFactory
                .getCopyOnRequestSetFromImmutableCollection(Utils
                        .mockCollection());
        CollectionFactory.setExpectedThreads(8);
        Set<Object> result3 = CollectionFactory.createSet(Utils
                .mockCollection());
        Set<OWLClass> result4 = CollectionFactory.createSet(0);
        Set<Object> result5 = CollectionFactory.createSet(new Object());
        Set<OWLClass> result6 = CollectionFactory.createSet();
        List<Object> result7 = CollectionFactory.createList();
        Set<OWLClass> result8 = CollectionFactory.createSyncSet();
        Set<Object> result10 = CollectionFactory.getCopyOnRequestSet(Utils
                .mockCollection());
        Set<OWLOntology> result11 = CollectionFactory
                .getThreadSafeCopyOnRequestSet(Utils.mockSet(Utils
                        .getMockOntology()));
        String result12 = testSubject0.toString();
    }

    @Test
    public void shouldTestCommonBaseIRIMapper() throws Exception {
        CommonBaseIRIMapper testSubject0 = new CommonBaseIRIMapper(
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
        testSubject0.addMapping(IRI("urn:aFake"), "");
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestDefaultPrefixManager() throws Exception {
        DefaultPrefixManager testSubject0 = new DefaultPrefixManager(
                new DefaultPrefixManager());
        DefaultPrefixManager testSubject1 = new DefaultPrefixManager();
        DefaultPrefixManager testSubject2 = new DefaultPrefixManager("");
        testSubject0.clear();
        String result0 = testSubject0.getPrefix("");
        testSubject0.dispose();
        IRI result1 = testSubject0.getIRI("");
        testSubject0.setPrefix(":", "test");
        Map<String, String> result2 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result3 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result4 = testSubject0.containsPrefixMapping("");
        String result5 = testSubject0.getDefaultPrefix();
        String result6 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        String result7 = testSubject0.getShortForm(IRI("urn:aFake"));
        String result8 = testSubject0.getShortForm(Utils.mockOWLEntity());
        testSubject0.unregisterNamespace("");
        String result9 = testSubject0.toString();
    }

    @Test
    public void shouldTestDelegatingObjectVisitorEx() throws Exception {
        DelegatingObjectVisitorEx<OWLObject> testSubject0 = new DelegatingObjectVisitorEx<OWLObject>(
                Utils.mockObject());
        OWLObject result0 = testSubject0
                .visit(mock(OWLAsymmetricObjectPropertyAxiom.class));
        OWLObject result9 = testSubject0
                .visit(mock(OWLDisjointDataPropertiesAxiom.class));
        OWLObject result10 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        OWLObject result14 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        OWLObject result15 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        OWLObject result16 = testSubject0
                .visit(mock(OWLFunctionalDataPropertyAxiom.class));
        OWLObject result17 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        OWLObject result18 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        OWLObject result19 = testSubject0
                .visit(mock(OWLInverseObjectPropertiesAxiom.class));
        OWLObject result20 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        OWLObject result21 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        OWLObject result22 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        OWLObject result23 = testSubject0
                .visit(mock(OWLObjectPropertyAssertionAxiom.class));
        OWLObject result28 = testSubject0
                .visit(mock(OWLReflexiveObjectPropertyAxiom.class));
        OWLObject result31 = testSubject0
                .visit(mock(OWLSymmetricObjectPropertyAxiom.class));
        OWLObject result32 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        OWLObject result73 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        OWLObject result74 = testSubject0
                .visit(mock(OWLAnnotationPropertyRangeAxiom.class));
        OWLObject result75 = testSubject0
                .visit(mock(OWLSubAnnotationPropertyOfAxiom.class));
        String result84 = testSubject0.toString();
    }

    @Test
    public void shouldTestDLExpressivityChecker() throws Exception {
        DLExpressivityChecker testSubject0 = new DLExpressivityChecker(
                Utils.mockSet(Utils.getMockOntology()));
        List<Construct> result0 = testSubject0.getConstructs();
        String result1 = testSubject0.getDescriptionLogicName();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestFilteringOWLOntologyChangeListener() throws Exception {
        FilteringOWLOntologyChangeListener testSubject0 = new FilteringOWLOntologyChangeListener();
        testSubject0.ontologiesChanged(Utils
                .mockList(mock(OWLOntologyChange.class)));
        testSubject0.processChanges(Utils
                .mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestHashCode() throws Exception {
        HashCode testSubject0 = new HashCode();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestHornAxiomVisitorEx() throws Exception {
        HornAxiomVisitorEx testSubject0 = new HornAxiomVisitorEx();
    }

    @Test
    public void shouldTestImportsStructureEntitySorter() throws Exception {
        ImportsStructureEntitySorter testSubject0 = new ImportsStructureEntitySorter(
                Utils.getMockOntology(), Utils.getMockManager());
    }

    @Test
    public void shouldTestImportsStructureObjectSorter() throws Exception {
        ImportsStructureObjectSorter<Object> testSubject0 = new ImportsStructureObjectSorter<Object>(
                Utils.getMockOntology(), Utils.getMockManager(),
                mock(ObjectSelector.class));
    }

    @Test
    public void shouldTestInterfaceInferredAxiomGenerator() throws Exception {
        InferredAxiomGenerator<OWLAxiom> testSubject0 = mock(InferredAxiomGenerator.class);
        Set<OWLAxiom> result0 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
        String result1 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredClassAssertionAxiomGenerator()
            throws Exception {
        InferredClassAssertionAxiomGenerator testSubject0 = new InferredClassAssertionAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLClassAssertionAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredClassAxiomGenerator() throws Exception {
        InferredClassAxiomGenerator<OWLClassAxiom> testSubject0 = new InferredClassAxiomGenerator<OWLClassAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLClass entity, OWLReasoner reasoner,
                    OWLDataFactory dataFactory, Set<OWLClassAxiom> result) {}
        };
        String result0 = testSubject0.toString();
        Set<OWLClassAxiom> result1 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredDataPropertyAxiomGenerator() throws Exception {
        InferredDataPropertyAxiomGenerator<OWLDataPropertyAxiom> testSubject0 = new InferredDataPropertyAxiomGenerator<OWLDataPropertyAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLDataProperty entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLDataPropertyAxiom> result) {}
        };
        String result0 = testSubject0.toString();
        Set<OWLDataPropertyAxiom> result1 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredDataPropertyCharacteristicAxiomGenerator()
            throws Exception {
        InferredDataPropertyCharacteristicAxiomGenerator testSubject0 = new InferredDataPropertyCharacteristicAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLDataPropertyCharacteristicAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredDisjointClassesAxiomGenerator()
            throws Exception {
        InferredDisjointClassesAxiomGenerator testSubject0 = new InferredDisjointClassesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLDisjointClassesAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentClassAxiomGenerator()
            throws Exception {
        InferredEquivalentClassAxiomGenerator testSubject0 = new InferredEquivalentClassAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLEquivalentClassesAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentDataPropertiesAxiomGenerator()
            throws Exception {
        InferredEquivalentDataPropertiesAxiomGenerator testSubject0 = new InferredEquivalentDataPropertiesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLEquivalentDataPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredEquivalentObjectPropertyAxiomGenerator()
            throws Exception {
        InferredEquivalentObjectPropertyAxiomGenerator testSubject0 = new InferredEquivalentObjectPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLEquivalentObjectPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredIndividualAxiomGenerator() throws Exception {
        InferredIndividualAxiomGenerator<OWLIndividualAxiom> testSubject0 = new InferredIndividualAxiomGenerator<OWLIndividualAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLNamedIndividual entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLIndividualAxiom> result) {}
        };
        String result0 = testSubject0.toString();
        Set<OWLIndividualAxiom> result1 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredInverseObjectPropertiesAxiomGenerator()
            throws Exception {
        InferredInverseObjectPropertiesAxiomGenerator testSubject0 = new InferredInverseObjectPropertiesAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLInverseObjectPropertiesAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredObjectPropertyAxiomGenerator()
            throws Exception {
        InferredObjectPropertyAxiomGenerator<OWLObjectPropertyAxiom> testSubject0 = new InferredObjectPropertyAxiomGenerator<OWLObjectPropertyAxiom>() {

            @Override
            public String getLabel() {
                return null;
            }

            @Override
            protected void addAxioms(OWLObjectProperty entity,
                    OWLReasoner reasoner, OWLDataFactory dataFactory,
                    Set<OWLObjectPropertyAxiom> result) {}
        };
        String result0 = testSubject0.toString();
        Set<OWLObjectPropertyAxiom> result1 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
        String result2 = testSubject0.getLabel();
    }

    @Test
    public void shouldTestInferredObjectPropertyCharacteristicAxiomGenerator()
            throws Exception {
        InferredObjectPropertyCharacteristicAxiomGenerator testSubject0 = new InferredObjectPropertyCharacteristicAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLObjectPropertyCharacteristicAxiom> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredOntologyGenerator() throws Exception {
        InferredOntologyGenerator testSubject0 = new InferredOntologyGenerator(
                Utils.structReasoner(),
                new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>());
        InferredOntologyGenerator testSubject1 = new InferredOntologyGenerator(
                Utils.structReasoner());
        List<InferredAxiomGenerator<?>> result0 = testSubject0
                .getAxiomGenerators();
        testSubject0.addGenerator(mock(InferredAxiomGenerator.class));
        testSubject0.removeGenerator(mock(InferredAxiomGenerator.class));
        testSubject0.fillOntology(Utils.getMockManager(),
                Utils.getMockOntology());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInferredPropertyAssertionGenerator() throws Exception {
        InferredPropertyAssertionGenerator testSubject0 = new InferredPropertyAssertionGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLPropertyAssertionAxiom<?, ?>> result2 = testSubject0
                .createAxioms(Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredSubClassAxiomGenerator() throws Exception {
        InferredSubClassAxiomGenerator testSubject0 = new InferredSubClassAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLSubClassOfAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredSubDataPropertyAxiomGenerator()
            throws Exception {
        InferredSubDataPropertyAxiomGenerator testSubject0 = new InferredSubDataPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLSubDataPropertyOfAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInferredSubObjectPropertyAxiomGenerator()
            throws Exception {
        InferredSubObjectPropertyAxiomGenerator testSubject0 = new InferredSubObjectPropertyAxiomGenerator();
        String result0 = testSubject0.getLabel();
        String result1 = testSubject0.toString();
        Set<OWLSubObjectPropertyOfAxiom> result2 = testSubject0.createAxioms(
                Utils.getMockManager(), Utils.structReasoner());
    }

    @Test
    public void shouldTestInterfaceIRIShortFormProvider() throws Exception {
        IRIShortFormProvider testSubject0 = mock(IRIShortFormProvider.class);
        String result0 = testSubject0.getShortForm(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInterfaceMonitorable() throws Exception {
        Monitorable testSubject0 = mock(Monitorable.class);
        testSubject0.interrupt();
        testSubject0.setProgressMonitor(mock(ProgressMonitor.class));
        boolean result0 = testSubject0.canInterrupt();
    }

    @Test
    public void shouldTestMultiMap() throws Exception {
        MultiMap<Object, Object> testSubject0 = new MultiMap<Object, Object>(
                false);
        MultiMap<Object, Object> testSubject1 = new MultiMap<Object, Object>(
                false, false);
        MultiMap<Object, Object> testSubject2 = new MultiMap<Object, Object>();
        Collection<Object> result0 = testSubject0.get(mock(Object.class));
        boolean result1 = testSubject0.put(mock(Object.class),
                mock(Object.class));
        String result2 = testSubject0.toString();
        testSubject0.clear();
        boolean result3 = testSubject0.contains(mock(Object.class),
                mock(Object.class));
        int result4 = testSubject0.size();
        testSubject0.putAll(new MultiMap<Object, Object>());
        testSubject0.putAll(mock(Object.class), Utils.mockCollection());
        boolean result5 = testSubject0.remove(mock(Object.class),
                mock(Object.class));
        boolean result6 = testSubject0.remove(mock(Object.class));
        Set<Object> result7 = testSubject0.keySet();
        boolean result8 = testSubject0.containsValue(mock(Object.class));
        boolean result9 = testSubject0.containsKey(mock(Object.class));
        testSubject0.setEntry(mock(Object.class), Utils.mockCollection());
        Set<Object> result10 = testSubject0.getAllValues();
        boolean result11 = testSubject0.isValueSetsEqual();
    }

    @Test
    public void shouldTestNamedConjunctChecker() throws Exception {
        NamedConjunctChecker testSubject0 = new NamedConjunctChecker();
        boolean result0 = testSubject0.isNamedConjunct(mock(OWLClass.class),
                Utils.mockAnonClass());
        boolean result1 = testSubject0.hasNamedConjunct(Utils.mockAnonClass());
        Set<OWLClass> result2 = testSubject0.getNamedConjuncts(Utils
                .mockAnonClass());
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestNNF() throws Exception {
        NNF testSubject0 = new NNF(mock(OWLDataFactory.class));
        testSubject0.reset();
    }

    @Test
    public void shouldTestNonMappingOntologyIRIMapper() throws Exception {
        NonMappingOntologyIRIMapper testSubject0 = new NonMappingOntologyIRIMapper();
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestNullProgressMonitor() throws Exception {
        NullProgressMonitor testSubject0 = new NullProgressMonitor();
        testSubject0.setSize(0L);
        testSubject0.setStarted();
        testSubject0.setProgress(0L);
        testSubject0.setMessage("");
        testSubject0.setIndeterminate(false);
        testSubject0.setFinished();
        boolean result0 = testSubject0.isCancelled();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectPropertySimplifier() throws Exception {
        ObjectPropertySimplifier testSubject0 = new ObjectPropertySimplifier(
                mock(OWLDataFactory.class));
        OWLObjectPropertyExpression result0 = testSubject0.getSimplified(Utils
                .mockObjectProperty());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOntologyIRIShortFormProvider() throws Exception {
        OntologyIRIShortFormProvider testSubject0 = new OntologyIRIShortFormProvider();
        String result0 = testSubject0.getShortForm(Utils.getMockOntology());
        String result1 = testSubject0.getShortForm(IRI("urn:aFake"));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLAxiomFilter() throws Exception {
        OWLAxiomFilter testSubject0 = mock(OWLAxiomFilter.class);
        boolean result0 = testSubject0.passes(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestInterfaceOWLAxiomSearchFilter() throws Exception {
        OWLAxiomSearchFilter<OWLAxiom, Object> testSubject0 = mock(OWLAxiomSearchFilter.class);
        AxiomType<?> result0 = testSubject0.getAxiomType();
        boolean result1 = testSubject0.pass(mock(OWLAxiom.class),
                mock(Object.class));
    }

    @Test
    public void shouldTestOWLAxiomTypeProcessor() throws Exception {
        OWLAxiomTypeProcessor testSubject0 = new OWLAxiomTypeProcessor() {

            @Override
            public void visit(OWLDatatypeDefinitionAxiom axiom) {}

            @Override
            protected void process(OWLAxiom axiom, AxiomType<?> type) {}
        };
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLAxiomVisitorAdapter() throws Exception {
        OWLAxiomVisitorAdapter testSubject0 = new OWLAxiomVisitorAdapter();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLClassExpressionCollector() throws Exception {
        OWLClassExpressionCollector testSubject0 = new OWLClassExpressionCollector();
    }

    @Test
    public void shouldTestOWLClassExpressionVisitorAdapter() throws Exception {
        OWLClassExpressionVisitorAdapter testSubject0 = new OWLClassExpressionVisitorAdapter();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLClassExpressionVisitorExAdapter() throws Exception {
        OWLClassExpressionVisitorExAdapter<Object> testSubject0 = new OWLClassExpressionVisitorExAdapter<Object>();
        String result18 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLClassLiteralCollector() throws Exception {
        OWLClassLiteralCollector testSubject0 = new OWLClassLiteralCollector(
                Utils.mockSet(mock(OWLObject.class)));
        OWLClassLiteralCollector testSubject1 = new OWLClassLiteralCollector(
                Utils.mockSet(mock(OWLObject.class)), false);
        Set<OWLClass> result0 = testSubject0.getPositiveLiterals();
        Set<OWLClass> result1 = testSubject0.getNegativeLiterals();
        OWLAnnotation result2 = testSubject0.getAnnotation();
        OWLOntology result3 = testSubject0.getOntology();
        OWLAxiom result4 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result5 = testSubject0
                .getClassExpressionPath();
        boolean result6 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result7 = testSubject0.getDataRangePath();
        String result8 = testSubject0.toString();
    }

    public void shouldTestOWLDataUtil() throws Exception {
        OWLDataUtil testSubject0 = new OWLDataUtil();
        OWLDatatype result1 = OWLDataUtil
                .getIntDatatype(mock(OWLDataFactory.class));
        OWLDatatype result2 = OWLDataUtil
                .getLongDatatype(mock(OWLDataFactory.class));
        OWLDatatype result3 = OWLDataUtil
                .getFloatDatatype(mock(OWLDataFactory.class));
        OWLDatatype result4 = OWLDataUtil
                .getDoubleDatatype(mock(OWLDataFactory.class));
        Set<OWLFacetRestriction> result5 = OWLDataUtil.getFacetRestrictionSet(
                mock(OWLDataFactory.class), OWLFacet.MAX_INCLUSIVE,
                mock(OWLLiteral.class));
        OWLLiteral result6 = OWLDataUtil.getTypedConstant(
                mock(OWLDataFactory.class), mock(Number.class));
        OWLDataRange result7 = OWLDataUtil.getMinInclusiveRestrictedInt(
                mock(OWLDataFactory.class), mock(Number.class));
        OWLDataRange result8 = OWLDataUtil.getMinExclusiveRestrictedInt(
                mock(OWLDataFactory.class), mock(Number.class));
        OWLDataRange result9 = OWLDataUtil.getMaxInclusiveRestrictedInteger(
                mock(OWLDataFactory.class), mock(Number.class));
        OWLDataRange result10 = OWLDataUtil.getMaxExclusiveRestrictedInteger(
                mock(OWLDataFactory.class), mock(Number.class));
        OWLDataRange result11 = OWLDataUtil
                .getMinMaxInclusiveRestrictedInteger(
                        mock(OWLDataFactory.class), mock(Number.class),
                        mock(Number.class));
        OWLDataRange result12 = OWLDataUtil
                .getMinMaxExclusiveRestrictedInteger(
                        mock(OWLDataFactory.class), mock(Number.class),
                        mock(Number.class));
        String result13 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityCollectingOntologyChangeListener()
            throws Exception {
        OWLEntityCollectingOntologyChangeListener testSubject0 = new OWLEntityCollectingOntologyChangeListener() {

            @Override
            public void ontologiesChanged() throws OWLException {}
        };
        Set<OWLEntity> result0 = testSubject0.getEntities();
        testSubject0.ontologiesChanged(Utils
                .mockList(mock(OWLOntologyChange.class)));
        testSubject0.ontologiesChanged();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityCollector() throws Exception {
        OWLEntityCollector testSubject0 = new OWLEntityCollector(
                Utils.mockSet(Utils.mockOWLEntity()),
                Utils.mockCollection(mock(OWLAnonymousIndividual.class)));
        OWLEntityCollector testSubject1 = new OWLEntityCollector(
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
    public void shouldTestOWLEntityComparator() throws Exception {
        OWLEntityComparator testSubject0 = new OWLEntityComparator(
                mock(ShortFormProvider.class));
    }

    @Test
    public void shouldTestOWLEntityRemover() throws Exception {
        OWLEntityRemover testSubject0 = new OWLEntityRemover(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()));
        testSubject0.reset();
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityRenamer() throws Exception {
        OWLEntityRenamer testSubject0 = new OWLEntityRenamer(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()));
        List<OWLOntologyChange> result0 = testSubject0.changeIRI(
                IRI("urn:aFake"), IRI("urn:aFake"));
        List<OWLOntologyChange> result1 = testSubject0.changeIRI(
                Utils.mockOWLEntity(), IRI("urn:aFake"));
        List<OWLOntologyChange> result2 = testSubject0
                .changeIRI(mock(Map.class));
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLEntitySetProvider() throws Exception {
        OWLEntitySetProvider<OWLClass> testSubject0 = mock(OWLEntitySetProvider.class);
        Set<OWLClass> result0 = testSubject0.getEntities();
    }

    @Test
    public void shouldTestOWLEntityTinyURIConversionStrategy() throws Exception {
        OWLEntityTinyURIConversionStrategy testSubject0 = new OWLEntityTinyURIConversionStrategy();
        OWLEntityTinyURIConversionStrategy testSubject1 = new OWLEntityTinyURIConversionStrategy(
                "");
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityURIConverter() throws Exception {
        OWLEntityURIConverter testSubject0 = new OWLEntityURIConverter(
                Utils.getMockManager(), Utils.mockSet(Utils.getMockOntology()),
                mock(OWLEntityURIConverterStrategy.class));
        List<OWLOntologyChange> result0 = testSubject0.getChanges();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLEntityURIConverterStrategy()
            throws Exception {
        OWLEntityURIConverterStrategy testSubject0 = mock(OWLEntityURIConverterStrategy.class);
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestOWLEntityURIUnderscores2CamelBackConverterStrategy()
            throws Exception {
        OWLEntityURIUnderscores2CamelBackConverterStrategy testSubject0 = new OWLEntityURIUnderscores2CamelBackConverterStrategy();
        IRI result0 = testSubject0.getConvertedIRI(Utils.mockOWLEntity());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityVisitorAdapter() throws Exception {
        OWLEntityVisitorAdapter testSubject0 = new OWLEntityVisitorAdapter();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLEntityVisitorExAdapter() throws Exception {
        OWLEntityVisitorExAdapter<Object> testSubject0 = new OWLEntityVisitorExAdapter<Object>(
                mock(Object.class));
        OWLEntityVisitorExAdapter<Object> testSubject1 = new OWLEntityVisitorExAdapter<Object>();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectComponentCollector() throws Exception {
        OWLObjectComponentCollector testSubject0 = new OWLObjectComponentCollector();
        Set<OWLObject> result0 = testSubject0
                .getComponents(mock(OWLObject.class));
        Set<OWLObject> result1 = testSubject0.getResult();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectDuplicator() throws Exception {
        OWLObjectDuplicator testSubject0 = new OWLObjectDuplicator(
                mock(OWLDataFactory.class));
        OWLObjectDuplicator testSubject1 = new OWLObjectDuplicator(
                mock(OWLDataFactory.class), mock(Map.class));
        OWLObjectDuplicator testSubject2 = new OWLObjectDuplicator(
                mock(Map.class), mock(OWLDataFactory.class));
        OWLObject result0 = testSubject0.duplicateObject(mock(OWLObject.class));
        String result1 = testSubject0.toString();
    }

    public void shouldTestOWLObjectPropertyManager() throws Exception {
        OWLObjectPropertyManager testSubject0 = new OWLObjectPropertyManager(
                Utils.getMockManager(), Utils.getMockOntology());
        testSubject0.dispose();
        Collection<Set<OWLObjectPropertyExpression>> result0 = OWLObjectPropertyManager
                .getEquivalentObjectProperties(Utils.mockSet(Utils
                        .getMockOntology()));
        Collection<Set<OWLObjectPropertyExpression>> result1 = testSubject0
                .getEquivalentObjectProperties();
        boolean result2 = testSubject0.isComposite(Utils.mockObjectProperty());
        Set<OWLObjectPropertyExpression> result3 = testSubject0
                .getCompositeProperties();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result4 = testSubject0
                .getPropertyHierarchy();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result5 = testSubject0
                .getHierarchyReflexiveTransitiveClosure();
        boolean result6 = testSubject0.isSubPropertyOf(
                Utils.mockObjectProperty(), Utils.mockObjectProperty());
        boolean result7 = testSubject0.isNonSimple(Utils.mockObjectProperty());
        Set<OWLObjectPropertyExpression> result8 = testSubject0
                .getNonSimpleProperties();
        Map<OWLObjectPropertyExpression, Set<OWLObjectPropertyExpression>> result9 = testSubject0
                .getPropertyPartialOrdering();
        boolean result10 = testSubject0.isLessThan(Utils.mockObjectProperty(),
                Utils.mockObjectProperty());
        OWLObjectPropertyManager.tarjan(Utils.mockSet(Utils.getMockOntology()),
                Utils.mockObjectProperty(), 0, mock(Stack.class),
                mock(Map.class), mock(Map.class),
                Utils.mockSet(Utils.mockSet(Utils.mockObjectProperty())),
                Utils.mockSet(Utils.mockObjectProperty()),
                Utils.mockSet(Utils.mockObjectProperty()));
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectTypeIndexProvider() throws Exception {
        OWLObjectTypeIndexProvider testSubject0 = new OWLObjectTypeIndexProvider();
        int result0 = testSubject0.getTypeIndex(mock(OWLObject.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectVisitorAdapter() throws Exception {
        OWLObjectVisitorAdapter testSubject0 = new OWLObjectVisitorAdapter();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectVisitorExAdapter() throws Exception {
        OWLObjectVisitorExAdapter<Object> testSubject0 = new OWLObjectVisitorExAdapter<Object>(
                mock(Object.class));
        OWLObjectVisitorExAdapter<Object> testSubject1 = new OWLObjectVisitorExAdapter<Object>();
        Object result12 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result13 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        Object result15 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        Object result17 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        Object result19 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result20 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        Object result21 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        Object result32 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result65 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        String result84 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLObjectWalker() throws Exception {
        OWLObjectWalker<OWLOntology> testSubject0 = new OWLObjectWalker<OWLOntology>(
                Utils.mockSet(Utils.getMockOntology()));
        OWLObjectWalker<OWLOntology> testSubject1 = new OWLObjectWalker<OWLOntology>(
                Utils.mockSet(Utils.getMockOntology()), false);
        OWLAnnotation result0 = testSubject0.getAnnotation();
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result3 = testSubject0
                .getClassExpressionPath();
        boolean result4 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result5 = testSubject0.getDataRangePath();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyChangeFilter() throws Exception {
        OWLOntologyChangeFilter testSubject0 = new OWLOntologyChangeFilter();
        testSubject0.processChanges(Utils
                .mockList(mock(OWLOntologyChange.class)));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyChangeVisitorAdapter() throws Exception {
        OWLOntologyChangeVisitorAdapter testSubject0 = new OWLOntologyChangeVisitorAdapter();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyChangeVisitorAdapterEx() throws Exception {
        OWLOntologyChangeVisitorAdapterEx<Object> testSubject0 = new OWLOntologyChangeVisitorAdapterEx<Object>();
        String result7 = testSubject0.toString();
    }

    public void shouldTestOWLOntologyImportsClosureSetProvider()
            throws Exception {
        OWLOntologyImportsClosureSetProvider testSubject0 = new OWLOntologyImportsClosureSetProvider(
                Utils.getMockManager(), Utils.getMockOntology());
        Set<OWLOntology> result0 = testSubject0.getOntologies();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyMerger() throws Exception {
        OWLOntologyMerger testSubject0 = new OWLOntologyMerger(
                mock(OWLOntologySetProvider.class));
        OWLOntologyMerger testSubject1 = new OWLOntologyMerger(
                mock(OWLOntologySetProvider.class), false);
        OWLOntologyMerger testSubject2 = new OWLOntologyMerger(
                mock(OWLOntologySetProvider.class), mock(OWLAxiomFilter.class));
        boolean result0 = testSubject0.passes(mock(OWLAxiom.class));
        OWLOntology result1 = testSubject0.createMergedOntology(
                Utils.getMockManager(), IRI("urn:aFake"));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologySingletonSetProvider() throws Exception {
        OWLOntologySingletonSetProvider testSubject0 = new OWLOntologySingletonSetProvider(
                Utils.getMockOntology());
        Set<OWLOntology> result0 = testSubject0.getOntologies();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyURIChanger() throws Exception {
        OWLOntologyURIChanger testSubject0 = new OWLOntologyURIChanger(
                Utils.getMockManager());
        List<OWLOntologyChange> result0 = testSubject0.getChanges(
                Utils.getMockOntology(), IRI("urn:aFake"));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyWalker() throws Exception {
        OWLOntologyWalker testSubject0 = new OWLOntologyWalker(
                Utils.mockSet(Utils.getMockOntology()));
        OWLAnnotation result0 = testSubject0.getAnnotation();
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
        testSubject0.walkStructure(Utils.mockObject());
        List<OWLClassExpression> result3 = testSubject0
                .getClassExpressionPath();
        boolean result4 = testSubject0.isFirstClassExpressionInPath(Utils
                .mockAnonClass());
        List<OWLDataRange> result5 = testSubject0.getDataRangePath();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLOntologyWalkerVisitor() throws Exception {
        OWLOntologyWalkerVisitor<Object> testSubject0 = new OWLOntologyWalkerVisitor<Object>(
                mock(OWLOntologyWalker.class));
        OWLAxiom result0 = testSubject0.getCurrentAxiom();
        OWLOntology result1 = testSubject0.getCurrentOntology();
        OWLAnnotation result2 = testSubject0.getCurrentAnnotation();
        Object result12 = testSubject0
                .visit(mock(OWLDisjointObjectPropertiesAxiom.class));
        Object result15 = testSubject0
                .visit(mock(OWLEquivalentDataPropertiesAxiom.class));
        Object result16 = testSubject0
                .visit(mock(OWLEquivalentObjectPropertiesAxiom.class));
        Object result18 = testSubject0
                .visit(mock(OWLFunctionalObjectPropertyAxiom.class));
        Object result20 = testSubject0
                .visit(mock(OWLInverseFunctionalObjectPropertyAxiom.class));
        Object result22 = testSubject0
                .visit(mock(OWLIrreflexiveObjectPropertyAxiom.class));
        Object result23 = testSubject0
                .visit(mock(OWLNegativeDataPropertyAssertionAxiom.class));
        Object result24 = testSubject0
                .visit(mock(OWLNegativeObjectPropertyAssertionAxiom.class));
        Object result35 = testSubject0
                .visit(mock(OWLTransitiveObjectPropertyAxiom.class));
        Object result68 = testSubject0
                .visit(mock(OWLAnnotationPropertyDomainAxiom.class));
        String result87 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceProgressMonitor() throws Exception {
        ProgressMonitor testSubject0 = mock(ProgressMonitor.class);
        testSubject0.setSize(0L);
        testSubject0.setStarted();
        testSubject0.setProgress(0L);
        testSubject0.setMessage("");
        testSubject0.setIndeterminate(false);
        testSubject0.setFinished();
        boolean result0 = testSubject0.isCancelled();
    }

    @Test
    public void shouldTestPropertyAssertionValueShortFormProvider()
            throws Exception {
        PropertyAssertionValueShortFormProvider testSubject0 = new PropertyAssertionValueShortFormProvider(
                new ArrayList<OWLPropertyExpression<?, ?>>(), mock(Map.class),
                mock(OWLOntologySetProvider.class));
        PropertyAssertionValueShortFormProvider testSubject1 = new PropertyAssertionValueShortFormProvider(
                new ArrayList<OWLPropertyExpression<?, ?>>(), mock(Map.class),
                mock(OWLOntologySetProvider.class),
                mock(ShortFormProvider.class));
        List<OWLPropertyExpression<?, ?>> result0 = testSubject0
                .getProperties();
        testSubject0.dispose();
        String result1 = testSubject0.getShortForm(Utils.mockOWLEntity());
        Map<OWLDataPropertyExpression, List<String>> result2 = testSubject0
                .getPreferredLanguageMap();
        String result3 = testSubject0.toString();
    }

    public void shouldTestQNameShortFormProvider() throws Exception {
        QNameShortFormProvider testSubject0 = new QNameShortFormProvider();
        QNameShortFormProvider testSubject1 = new QNameShortFormProvider(
                mock(Map.class));
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestReferencedEntitySetProvider() throws Exception {
        ReferencedEntitySetProvider testSubject0 = new ReferencedEntitySetProvider(
                Utils.mockSet(Utils.getMockOntology()));
        Set<OWLEntity> result0 = testSubject0.getEntities();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceRootClassChecker() throws Exception {
        RootClassChecker testSubject0 = mock(RootClassChecker.class);
        boolean result0 = testSubject0.isRootClass(mock(OWLClass.class));
    }

    @Test
    public void shouldTestInterfaceShortFormProvider() throws Exception {
        ShortFormProvider testSubject0 = mock(ShortFormProvider.class);
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestSimpleIRIMapper() throws Exception {
        SimpleIRIMapper testSubject0 = new SimpleIRIMapper(IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getDocumentIRI(IRI("urn:aFake"));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestSimpleIRIShortFormProvider() throws Exception {
        SimpleIRIShortFormProvider testSubject0 = new SimpleIRIShortFormProvider();
        String result0 = testSubject0.getShortForm(IRI("urn:aFake"));
        String result1 = testSubject0.toString();
    }

    public void shouldTestSimpleRenderer() throws Exception {
        SimpleRenderer testSubject0 = new SimpleRenderer();
        String result0 = testSubject0.toString();
        testSubject0.reset();
        String result1 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setPrefix("", "");
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        String result2 = testSubject0.getShortForm(IRI("urn:aFake"));
        testSubject0.resetShortFormProvider();
        boolean result3 = testSubject0.isUsingDefaultShortFormProvider();
        testSubject0.setPrefixesFromOntologyFormat(Utils.getMockOntology(),
                Utils.getMockManager(), false);
        testSubject0.writeAnnotations(mock(OWLAxiom.class));
    }

    @Test
    public void shouldTestSimpleRootClassChecker() throws Exception {
        SimpleRootClassChecker testSubject0 = new SimpleRootClassChecker(
                Utils.mockSet(Utils.getMockOntology()));
        boolean result0 = testSubject0.isRootClass(mock(OWLClass.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestSimpleShortFormProvider() throws Exception {
        SimpleShortFormProvider testSubject0 = new SimpleShortFormProvider();
        testSubject0.dispose();
        String result0 = testSubject0.getShortForm(Utils.mockOWLEntity());
    }

    @Test
    public void shouldTestStructuralTransformation() throws Exception {
        StructuralTransformation testSubject0 = new StructuralTransformation(
                mock(OWLDataFactory.class));
        Set<OWLAxiom> result0 = testSubject0.getTransformedAxioms(Utils
                .mockSet(mock(OWLAxiom.class)));
    }

    @Test
    public void shouldTestSWRLVariableExtractor() throws Exception {
        SWRLVariableExtractor testSubject0 = new SWRLVariableExtractor();
        Set<SWRLVariable> result0 = testSubject0.getVariables();
    }

    @Test
    public void shouldTestInterfaceSWRLVariableShortFormProvider()
            throws Exception {
        SWRLVariableShortFormProvider testSubject0 = mock(SWRLVariableShortFormProvider.class);
        String result0 = testSubject0.getShortForm(mock(SWRLVariable.class));
    }

    @Test
    public void shouldTestVersion() throws Exception {
        Version testSubject0 = new Version(0, 0, 0, 0);
        int result0 = testSubject0.getMajor();
        int result1 = testSubject0.getMinor();
        int result2 = testSubject0.getPatch();
        int result3 = testSubject0.getBuild();
    }

    @Test
    public void shouldTestVersionInfo() throws Exception {
        VersionInfo testSubject0 = VersionInfo.getVersionInfo();
        String result0 = testSubject0.toString();
        String result1 = testSubject0.getVersion();
        VersionInfo result2 = VersionInfo.getVersionInfo();
        String result3 = testSubject0.getGeneratedByMessage();
    }

    @Test
    public void shouldTestWeakCache() throws Exception {
        WeakCache<Object> testSubject0 = new WeakCache<Object>();
        Object result0 = testSubject0.cache(mock(Object.class));
        testSubject0.clear();
        boolean result1 = testSubject0.contains(mock(Object.class));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestWeakIndexCache() throws Exception {
        WeakIndexCache<Object, Object> testSubject0 = new WeakIndexCache<Object, Object>();
        Object result0 = testSubject0.get(mock(Object.class));
        Object result1 = testSubject0.cache(mock(Object.class),
                mock(Object.class));
        testSubject0.clear();
        boolean result2 = testSubject0.contains(mock(Object.class));
    }
}
