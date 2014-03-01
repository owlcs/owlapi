package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.ArrayList;
import java.util.Map;
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
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
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
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualVisitor;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactory.OWLOntologyCreationHandler;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.AbstractInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.AbstractInternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.ClassAxiomByClassPointer;
import uk.ac.manchester.cs.owl.owlapi.CollectionContainer;
import uk.ac.manchester.cs.owl.owlapi.CollectionContainerVisitor;
import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.ImplUtils;
import uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory;
import uk.ac.manchester.cs.owl.owlapi.Internals;
import uk.ac.manchester.cs.owl.owlapi.Internals.Pointer;
import uk.ac.manchester.cs.owl.owlapi.InternalsImpl;
import uk.ac.manchester.cs.owl.owlapi.InternalsNoCache;
import uk.ac.manchester.cs.owl.owlapi.MapPointer;
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
    public void shouldTestAbstractInMemOWLOntologyFactory() throws Exception {
        AbstractInMemOWLOntologyFactory testSubject0 = new AbstractInMemOWLOntologyFactory() {

            private static final long serialVersionUID = 30406L;

            @Override
            public OWLOntology loadOWLOntology(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntologyCreationHandler handler)
                    throws OWLOntologyCreationException {
                return null;
            }

            @Override
            public OWLOntology loadOWLOntology(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntologyCreationHandler handler,
                    OWLOntologyLoaderConfiguration configuration)
                    throws OWLOntologyCreationException {
                return null;
            }

            @Override
            public boolean canLoad(OWLOntologyDocumentSource documentSource) {
                return false;
            }
        };
        testSubject0.setOWLOntologyManager(Utils.getMockManager());
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.createOWLOntology(
                new OWLOntologyID(), IRI("urn:aFake"),
                mock(OWLOntologyCreationHandler.class));
        boolean result2 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
        String result3 = testSubject0.toString();
        OWLOntology result4 = testSubject0.loadOWLOntology(
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class));
        OWLOntology result5 = testSubject0.loadOWLOntology(
                mock(OWLOntologyDocumentSource.class),
                mock(OWLOntologyCreationHandler.class),
                new OWLOntologyLoaderConfiguration());
        boolean result6 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
    }

    @Test
    public void shouldTestAbstractInternalsImpl() throws Exception {
        AbstractInternalsImpl testSubject0 = new AbstractInternalsImpl() {

            private static final long serialVersionUID = 30406L;

            @Override
            public Pointer<AxiomType<?>, OWLAxiom> getAxiomsByType() {
                return null;
            }

            @Override
            public <K, V extends OWLAxiom> boolean add(Pointer<K, V> pointer,
                    K key, V value) {
                return false;
            }

            @Override
            public void addGeneralClassAxioms(OWLClassAxiom ax) {}

            @Override
            public boolean addImportsDeclaration(
                    OWLImportsDeclaration importDeclaration) {
                return false;
            }

            @Override
            public boolean addOntologyAnnotation(OWLAnnotation ann) {
                return false;
            }

            @Override
            public void addPropertyChainSubPropertyAxioms(
                    OWLSubPropertyChainOfAxiom ax) {}

            @Override
            public <K, V extends OWLAxiom> boolean contains(
                    Pointer<K, V> pointer, K k) {
                return false;
            }

            @Override
            public <K, V extends OWLAxiom> boolean contains(
                    Pointer<K, V> pointer, K k, V v) {
                return false;
            }

            @Override
            public int getAxiomCount() {
                return 0;
            }

            @Override
            public <T extends OWLAxiom> int
                    getAxiomCount(AxiomType<T> axiomType) {
                return 0;
            }

            @Override
            public Set<OWLAxiom> getAxioms() {
                return null;
            }

            @Override
            public Pointer<OWLEntity, OWLDeclarationAxiom>
                    getDeclarationsByEntity() {
                return null;
            }

            @Override
            public Set<OWLClassAxiom> getGeneralClassAxioms() {
                return null;
            }

            @Override
            public Set<OWLImportsDeclaration> getImportsDeclarations() {
                return null;
            }

            @Override
            public int getLogicalAxiomCount() {
                return 0;
            }

            @Override
            public Set<OWLLogicalAxiom> getLogicalAxioms() {
                return null;
            }

            @Override
            public Set<OWLAnnotation> getOntologyAnnotations() {
                return null;
            }

            @Override
            public Pointer<OWLAnnotationProperty, OWLAxiom>
                    getOwlAnnotationPropertyReferences() {
                return null;
            }

            @Override
            public Pointer<OWLAnonymousIndividual, OWLAxiom>
                    getOwlAnonymousIndividualReferences() {
                return null;
            }

            @Override
            public Pointer<OWLClass, OWLAxiom> getOwlClassReferences() {
                return null;
            }

            @Override
            public Pointer<OWLDataProperty, OWLAxiom>
                    getOwlDataPropertyReferences() {
                return null;
            }

            @Override
            public Pointer<OWLDatatype, OWLAxiom> getOwlDatatypeReferences() {
                return null;
            }

            @Override
            public Pointer<OWLNamedIndividual, OWLAxiom>
                    getOwlIndividualReferences() {
                return null;
            }

            @Override
            public Pointer<OWLObjectProperty, OWLAxiom>
                    getOwlObjectPropertyReferences() {
                return null;
            }

            @Override
            public <K, V extends OWLAxiom> Set<V> getValues(
                    Pointer<K, V> pointer, K key) {
                return null;
            }

            @Override
            public <K, V extends OWLAxiom> boolean hasValues(
                    Pointer<K, V> pointer, K key) {
                return false;
            }

            @Override
            public <K, V extends OWLAxiom> Set<K> getKeyset(
                    Pointer<K, V> pointer) {
                return null;
            }

            @Override
            public <T extends OWLAxiom, K> Set<T> filterAxioms(
                    OWLAxiomSearchFilter<T, K> filter, K key) {
                return null;
            }

            @Override
            public boolean isDeclared(OWLDeclarationAxiom ax) {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean addAxiom(OWLAxiom axiom) {
                return false;
            }

            @Override
            public boolean removeAxiom(OWLAxiom axiom) {
                return false;
            }

            @Override
            public void removeGeneralClassAxioms(OWLClassAxiom ax) {}

            @Override
            public boolean removeImportsDeclaration(
                    OWLImportsDeclaration importDeclaration) {
                return false;
            }

            @Override
            public boolean removeOntologyAnnotation(OWLAnnotation ann) {
                return false;
            }

            @Override
            public <K, V extends OWLAxiom> boolean remove(
                    Pointer<K, V> pointer, K k, V v) {
                return false;
            }

            @Override
            public void removePropertyChainSubPropertyAxioms(
                    OWLSubPropertyChainOfAxiom ax) {}
        };
        MapPointer<OWLClass, OWLClassAxiom> result0 = testSubject0
                .getClassAxiomsByClass();
        MapPointer<OWLClass, OWLSubClassOfAxiom> result2 = testSubject0
                .getSubClassAxiomsByLHS();
        MapPointer<OWLClass, OWLSubClassOfAxiom> result4 = testSubject0
                .getSubClassAxiomsByRHS();
        MapPointer<OWLClass, OWLEquivalentClassesAxiom> result7 = testSubject0
                .getEquivalentClassesAxiomsByClass();
        MapPointer<OWLClass, OWLDisjointClassesAxiom> result9 = testSubject0
                .getDisjointClassesAxiomsByClass();
        MapPointer<OWLClass, OWLDisjointUnionAxiom> result10 = testSubject0
                .getDisjointUnionAxiomsByClass();
        MapPointer<OWLClass, OWLHasKeyAxiom> result13 = testSubject0
                .getHasKeyAxiomsByClass();
        MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result14 = testSubject0
                .getObjectSubPropertyAxiomsByLHS();
        MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result16 = testSubject0
                .getObjectSubPropertyAxiomsByRHS();
        MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> result19 = testSubject0
                .getEquivalentObjectPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> result20 = testSubject0
                .getDisjointObjectPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> result23 = testSubject0
                .getObjectPropertyDomainAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> result25 = testSubject0
                .getObjectPropertyRangeAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> result26 = testSubject0
                .getFunctionalObjectPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> result29 = testSubject0
                .getInverseFunctionalPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> result31 = testSubject0
                .getSymmetricPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> result32 = testSubject0
                .getAsymmetricPropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> result34 = testSubject0
                .getReflexivePropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> result37 = testSubject0
                .getIrreflexivePropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> result38 = testSubject0
                .getTransitivePropertyAxiomsByProperty();
        MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> result40 = testSubject0
                .getInversePropertyAxiomsByProperty();
        MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result43 = testSubject0
                .getDataSubPropertyAxiomsByLHS();
        MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result45 = testSubject0
                .getDataSubPropertyAxiomsByRHS();
        MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> result46 = testSubject0
                .getEquivalentDataPropertyAxiomsByProperty();
        MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> result49 = testSubject0
                .getDisjointDataPropertyAxiomsByProperty();
        MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> result50 = testSubject0
                .getDataPropertyDomainAxiomsByProperty();
        MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> result53 = testSubject0
                .getDataPropertyRangeAxiomsByProperty();
        MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> result55 = testSubject0
                .getFunctionalDataPropertyAxiomsByProperty();
        MapPointer<OWLIndividual, OWLClassAssertionAxiom> result57 = testSubject0
                .getClassAssertionAxiomsByIndividual();
        MapPointer<OWLClassExpression, OWLClassAssertionAxiom> result59 = testSubject0
                .getClassAssertionAxiomsByClass();
        MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> result60 = testSubject0
                .getObjectPropertyAssertionsByIndividual();
        MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom> result62 = testSubject0
                .getDataPropertyAssertionsByIndividual();
        MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> result64 = testSubject0
                .getNegativeObjectPropertyAssertionAxiomsByIndividual();
        MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> result66 = testSubject0
                .getNegativeDataPropertyAssertionAxiomsByIndividual();
        MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom> result69 = testSubject0
                .getDifferentIndividualsAxiomsByIndividual();
        MapPointer<OWLIndividual, OWLSameIndividualAxiom> result70 = testSubject0
                .getSameIndividualsAxiomsByIndividual();
        MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> result72 = testSubject0
                .getAnnotationAssertionAxiomsBySubject();
        String result74 = testSubject0.toString();
        boolean result75 = testSubject0.add(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result76 = testSubject0.isEmpty();
        boolean result77 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class));
        boolean result78 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result79 = testSubject0.remove(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        Set<OWLImportsDeclaration> result80 = testSubject0
                .getImportsDeclarations();
        Set<OWLAxiom> result81 = testSubject0.getAxioms();
        int result82 = testSubject0.getAxiomCount(Utils.mockAxiomType());
        int result83 = testSubject0.getAxiomCount();
        Set<OWLLogicalAxiom> result84 = testSubject0.getLogicalAxioms();
        int result85 = testSubject0.getLogicalAxiomCount();
        Set<OWLClassAxiom> result86 = testSubject0.getGeneralClassAxioms();
        boolean result87 = testSubject0
                .isDeclared(mock(OWLDeclarationAxiom.class));
        boolean result88 = testSubject0.addAxiom(mock(OWLAxiom.class));
        boolean result89 = testSubject0.removeAxiom(mock(OWLAxiom.class));
        Set<OWLClass> result90 = testSubject0.getValues(mock(MapPointer.class),
                mock(Object.class));
        Pointer<AxiomType<?>, OWLAxiom> result91 = testSubject0
                .getAxiomsByType();
        testSubject0.addGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result92 = testSubject0
                .addImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result93 = testSubject0
                .addOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .addPropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
        Pointer<OWLEntity, OWLDeclarationAxiom> result94 = testSubject0
                .getDeclarationsByEntity();
        Set<OWLAnnotation> result95 = testSubject0.getOntologyAnnotations();
        Pointer<OWLAnnotationProperty, OWLAxiom> result96 = testSubject0
                .getOwlAnnotationPropertyReferences();
        Pointer<OWLAnonymousIndividual, OWLAxiom> result97 = testSubject0
                .getOwlAnonymousIndividualReferences();
        Pointer<OWLClass, OWLAxiom> result98 = testSubject0
                .getOwlClassReferences();
        Pointer<OWLDataProperty, OWLAxiom> result99 = testSubject0
                .getOwlDataPropertyReferences();
        Pointer<OWLDatatype, OWLAxiom> result100 = testSubject0
                .getOwlDatatypeReferences();
        Pointer<OWLNamedIndividual, OWLAxiom> result101 = testSubject0
                .getOwlIndividualReferences();
        Pointer<OWLObjectProperty, OWLAxiom> result102 = testSubject0
                .getOwlObjectPropertyReferences();
        boolean result103 = testSubject0.hasValues(mock(MapPointer.class),
                mock(Object.class));
        Set<OWLClass> result104 = testSubject0
                .getKeyset(mock(MapPointer.class));
        Set<OWLClass> result105 = testSubject0.filterAxioms(
                mock(OWLAxiomSearchFilter.class), mock(Object.class));
        testSubject0.removeGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result106 = testSubject0
                .removeImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result107 = testSubject0
                .removeOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .removePropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
    }

    @Test
    public void shouldTestClassAxiomByClassPointer() throws Exception {
        ClassAxiomByClassPointer testSubject0 = new ClassAxiomByClassPointer(
                Utils.mockAxiomType(), Utils.mockAxiom(), false,
                mock(Internals.class));
        testSubject0.init();
        boolean result0 = testSubject0.put(mock(OWLClass.class),
                mock(OWLClassAxiom.class));
        String result1 = testSubject0.toString();
        boolean result2 = testSubject0.contains(mock(OWLClass.class),
                mock(OWLClassAxiom.class));
        int result3 = testSubject0.size();
        boolean result4 = testSubject0.remove(mock(OWLClass.class),
                mock(OWLClassAxiom.class));
        Set<OWLClass> result5 = testSubject0.keySet();
        boolean result6 = testSubject0.containsKey(mock(OWLClass.class));
        Set<OWLClassAxiom> result7 = testSubject0
                .getValues(mock(OWLClass.class));
        Set<OWLClassAxiom> result8 = testSubject0.getAllValues();
        boolean result9 = testSubject0.hasValues(mock(OWLClass.class));
        boolean result10 = testSubject0.isInitialized();
    }

    @Test
    public void shouldTestInterfaceCollectionContainer() throws Exception {
        CollectionContainer<Object> testSubject0 = mock(CollectionContainer.class);
        testSubject0.accept(Utils.mockCollContainer());
    }

    @Test
    public void shouldTestInterfaceCollectionContainerVisitor()
            throws Exception {
        CollectionContainerVisitor<Object> testSubject0 = Utils
                .mockCollContainer();
    }

    @Test
    public void shouldTestEmptyInMemOWLOntologyFactory() throws Exception {
        EmptyInMemOWLOntologyFactory testSubject0 = new EmptyInMemOWLOntologyFactory();
        OWLOntology result0 = testSubject0.createOWLOntology(
                new OWLOntologyID(), IRI("urn:aFake"),
                mock(OWLOntologyCreationHandler.class));
        boolean result3 = testSubject0
                .canLoad(mock(OWLOntologyDocumentSource.class));
        testSubject0.setOWLOntologyManager(Utils.getMockManager());
        OWLOntologyManager result4 = testSubject0.getOWLOntologyManager();
        boolean result5 = testSubject0
                .canCreateFromDocumentIRI(IRI("urn:aFake"));
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestImplUtils() throws Exception {
        ImplUtils testSubject0 = new ImplUtils();
        Set<OWLAnnotation> result0 = ImplUtils.getAnnotations(
                Utils.mockOWLEntity(), Utils.mockSet(Utils.getMockOntology()));
        Set<OWLAnnotation> result1 = ImplUtils.getAnnotations(
                Utils.mockOWLEntity(), mock(OWLAnnotationProperty.class),
                Utils.mockSet(Utils.getMockOntology()));
        Set<OWLAnnotationAssertionAxiom> result2 = ImplUtils
                .getAnnotationAxioms(Utils.mockOWLEntity(),
                        Utils.mockSet(Utils.getMockOntology()));
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestInitVisitorFactory() throws Exception {
        InitVisitorFactory testSubject0 = new InitVisitorFactory();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceInternals() throws Exception {
        Internals testSubject0 = mock(Internals.class);
        boolean result0 = testSubject0.add(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result1 = testSubject0.isEmpty();
        boolean result2 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class));
        boolean result3 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result4 = testSubject0.remove(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        Set<OWLImportsDeclaration> result5 = testSubject0
                .getImportsDeclarations();
        Set<OWLAxiom> result6 = testSubject0.getAxioms();
        int result7 = testSubject0.getAxiomCount(Utils.mockAxiomType());
        int result8 = testSubject0.getAxiomCount();
        Set<OWLLogicalAxiom> result9 = testSubject0.getLogicalAxioms();
        int result10 = testSubject0.getLogicalAxiomCount();
        Set<OWLClassAxiom> result11 = testSubject0.getGeneralClassAxioms();
        boolean result12 = testSubject0
                .isDeclared(mock(OWLDeclarationAxiom.class));
        boolean result13 = testSubject0.addAxiom(mock(OWLAxiom.class));
        boolean result14 = testSubject0.removeAxiom(mock(OWLAxiom.class));
        Set<OWLClass> result15 = testSubject0.getValues(mock(MapPointer.class),
                mock(Object.class));
        Pointer<OWLClass, OWLClassAxiom> result16 = testSubject0
                .getClassAxiomsByClass();
        Pointer<OWLClass, OWLSubClassOfAxiom> result17 = testSubject0
                .getSubClassAxiomsByLHS();
        Pointer<OWLClass, OWLSubClassOfAxiom> result18 = testSubject0
                .getSubClassAxiomsByRHS();
        Pointer<OWLClass, OWLEquivalentClassesAxiom> result19 = testSubject0
                .getEquivalentClassesAxiomsByClass();
        Pointer<OWLClass, OWLDisjointClassesAxiom> result20 = testSubject0
                .getDisjointClassesAxiomsByClass();
        Pointer<OWLClass, OWLDisjointUnionAxiom> result21 = testSubject0
                .getDisjointUnionAxiomsByClass();
        Pointer<OWLClass, OWLHasKeyAxiom> result22 = testSubject0
                .getHasKeyAxiomsByClass();
        Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result23 = testSubject0
                .getObjectSubPropertyAxiomsByLHS();
        Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result24 = testSubject0
                .getObjectSubPropertyAxiomsByRHS();
        Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> result25 = testSubject0
                .getEquivalentObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> result26 = testSubject0
                .getDisjointObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> result27 = testSubject0
                .getObjectPropertyDomainAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> result28 = testSubject0
                .getObjectPropertyRangeAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> result29 = testSubject0
                .getFunctionalObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> result30 = testSubject0
                .getInverseFunctionalPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> result31 = testSubject0
                .getSymmetricPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> result32 = testSubject0
                .getAsymmetricPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> result33 = testSubject0
                .getReflexivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> result34 = testSubject0
                .getIrreflexivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> result35 = testSubject0
                .getTransitivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> result36 = testSubject0
                .getInversePropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result37 = testSubject0
                .getDataSubPropertyAxiomsByLHS();
        Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result38 = testSubject0
                .getDataSubPropertyAxiomsByRHS();
        Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> result39 = testSubject0
                .getEquivalentDataPropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> result40 = testSubject0
                .getDisjointDataPropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> result41 = testSubject0
                .getDataPropertyDomainAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> result42 = testSubject0
                .getDataPropertyRangeAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> result43 = testSubject0
                .getFunctionalDataPropertyAxiomsByProperty();
        Pointer<OWLIndividual, OWLClassAssertionAxiom> result44 = testSubject0
                .getClassAssertionAxiomsByIndividual();
        Pointer<OWLClassExpression, OWLClassAssertionAxiom> result45 = testSubject0
                .getClassAssertionAxiomsByClass();
        Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> result46 = testSubject0
                .getObjectPropertyAssertionsByIndividual();
        Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom> result47 = testSubject0
                .getDataPropertyAssertionsByIndividual();
        Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> result48 = testSubject0
                .getNegativeObjectPropertyAssertionAxiomsByIndividual();
        Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> result49 = testSubject0
                .getNegativeDataPropertyAssertionAxiomsByIndividual();
        Pointer<OWLIndividual, OWLDifferentIndividualsAxiom> result50 = testSubject0
                .getDifferentIndividualsAxiomsByIndividual();
        Pointer<OWLIndividual, OWLSameIndividualAxiom> result51 = testSubject0
                .getSameIndividualsAxiomsByIndividual();
        Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> result52 = testSubject0
                .getAnnotationAssertionAxiomsBySubject();
        Pointer<AxiomType<?>, OWLAxiom> result53 = testSubject0
                .getAxiomsByType();
        testSubject0.addGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result54 = testSubject0
                .addImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result55 = testSubject0
                .addOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .addPropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
        Pointer<OWLEntity, OWLDeclarationAxiom> result56 = testSubject0
                .getDeclarationsByEntity();
        Set<OWLAnnotation> result57 = testSubject0.getOntologyAnnotations();
        Pointer<OWLAnnotationProperty, OWLAxiom> result58 = testSubject0
                .getOwlAnnotationPropertyReferences();
        Pointer<OWLAnonymousIndividual, OWLAxiom> result59 = testSubject0
                .getOwlAnonymousIndividualReferences();
        Pointer<OWLClass, OWLAxiom> result60 = testSubject0
                .getOwlClassReferences();
        Pointer<OWLDataProperty, OWLAxiom> result61 = testSubject0
                .getOwlDataPropertyReferences();
        Pointer<OWLDatatype, OWLAxiom> result62 = testSubject0
                .getOwlDatatypeReferences();
        Pointer<OWLNamedIndividual, OWLAxiom> result63 = testSubject0
                .getOwlIndividualReferences();
        Pointer<OWLObjectProperty, OWLAxiom> result64 = testSubject0
                .getOwlObjectPropertyReferences();
        boolean result65 = testSubject0.hasValues(mock(MapPointer.class),
                mock(Object.class));
        Set<OWLClass> result66 = testSubject0.getKeyset(mock(MapPointer.class));
        Set<OWLClass> result67 = testSubject0.filterAxioms(
                mock(OWLAxiomSearchFilter.class), mock(Object.class));
        testSubject0.removeGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result68 = testSubject0
                .removeImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result69 = testSubject0
                .removeOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .removePropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
    }

    @Test
    public void shouldTestInternalsImpl() throws Exception {
        InternalsImpl testSubject0 = new InternalsImpl();
        boolean result0 = testSubject0.add(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result1 = testSubject0.isEmpty();
        boolean result2 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class));
        boolean result3 = testSubject0.contains(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        boolean result4 = testSubject0.remove(mock(MapPointer.class),
                mock(Object.class), mock(OWLAxiom.class));
        Set<OWLImportsDeclaration> result5 = testSubject0
                .getImportsDeclarations();
        Set<OWLAxiom> result6 = testSubject0.getAxioms();
        int result7 = testSubject0.getAxiomCount(Utils.mockAxiomType());
        int result8 = testSubject0.getAxiomCount();
        Set<OWLLogicalAxiom> result9 = testSubject0.getLogicalAxioms();
        int result10 = testSubject0.getLogicalAxiomCount();
        Set<OWLClassAxiom> result11 = testSubject0.getGeneralClassAxioms();
        boolean result12 = testSubject0
                .isDeclared(mock(OWLDeclarationAxiom.class));
        boolean result13 = testSubject0.addAxiom(mock(OWLAxiom.class));
        boolean result14 = testSubject0.removeAxiom(mock(OWLAxiom.class));
        Set<OWLClass> result15 = testSubject0.getValues(mock(MapPointer.class),
                mock(Object.class));
        Pointer<OWLClass, OWLClassAxiom> result16 = testSubject0
                .getClassAxiomsByClass();
        Pointer<OWLClass, OWLSubClassOfAxiom> result17 = testSubject0
                .getSubClassAxiomsByLHS();
        Pointer<OWLClass, OWLSubClassOfAxiom> result18 = testSubject0
                .getSubClassAxiomsByRHS();
        Pointer<OWLClass, OWLEquivalentClassesAxiom> result19 = testSubject0
                .getEquivalentClassesAxiomsByClass();
        Pointer<OWLClass, OWLDisjointClassesAxiom> result20 = testSubject0
                .getDisjointClassesAxiomsByClass();
        Pointer<OWLClass, OWLDisjointUnionAxiom> result21 = testSubject0
                .getDisjointUnionAxiomsByClass();
        Pointer<OWLClass, OWLHasKeyAxiom> result22 = testSubject0
                .getHasKeyAxiomsByClass();
        Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result23 = testSubject0
                .getObjectSubPropertyAxiomsByLHS();
        Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> result24 = testSubject0
                .getObjectSubPropertyAxiomsByRHS();
        Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> result25 = testSubject0
                .getEquivalentObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> result26 = testSubject0
                .getDisjointObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> result27 = testSubject0
                .getObjectPropertyDomainAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> result28 = testSubject0
                .getObjectPropertyRangeAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> result29 = testSubject0
                .getFunctionalObjectPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> result30 = testSubject0
                .getInverseFunctionalPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> result31 = testSubject0
                .getSymmetricPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> result32 = testSubject0
                .getAsymmetricPropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> result33 = testSubject0
                .getReflexivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> result34 = testSubject0
                .getIrreflexivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> result35 = testSubject0
                .getTransitivePropertyAxiomsByProperty();
        Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> result36 = testSubject0
                .getInversePropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result37 = testSubject0
                .getDataSubPropertyAxiomsByLHS();
        Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> result38 = testSubject0
                .getDataSubPropertyAxiomsByRHS();
        Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> result39 = testSubject0
                .getEquivalentDataPropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> result40 = testSubject0
                .getDisjointDataPropertyAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> result41 = testSubject0
                .getDataPropertyDomainAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> result42 = testSubject0
                .getDataPropertyRangeAxiomsByProperty();
        Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> result43 = testSubject0
                .getFunctionalDataPropertyAxiomsByProperty();
        Pointer<OWLIndividual, OWLClassAssertionAxiom> result44 = testSubject0
                .getClassAssertionAxiomsByIndividual();
        Pointer<OWLClassExpression, OWLClassAssertionAxiom> result45 = testSubject0
                .getClassAssertionAxiomsByClass();
        Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> result46 = testSubject0
                .getObjectPropertyAssertionsByIndividual();
        Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom> result47 = testSubject0
                .getDataPropertyAssertionsByIndividual();
        Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> result48 = testSubject0
                .getNegativeObjectPropertyAssertionAxiomsByIndividual();
        Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> result49 = testSubject0
                .getNegativeDataPropertyAssertionAxiomsByIndividual();
        Pointer<OWLIndividual, OWLDifferentIndividualsAxiom> result50 = testSubject0
                .getDifferentIndividualsAxiomsByIndividual();
        Pointer<OWLIndividual, OWLSameIndividualAxiom> result51 = testSubject0
                .getSameIndividualsAxiomsByIndividual();
        Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> result52 = testSubject0
                .getAnnotationAssertionAxiomsBySubject();
        Pointer<AxiomType<?>, OWLAxiom> result53 = testSubject0
                .getAxiomsByType();
        testSubject0.addGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result54 = testSubject0
                .addImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result55 = testSubject0
                .addOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .addPropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
        Pointer<OWLEntity, OWLDeclarationAxiom> result56 = testSubject0
                .getDeclarationsByEntity();
        Set<OWLAnnotation> result57 = testSubject0.getOntologyAnnotations();
        Pointer<OWLAnnotationProperty, OWLAxiom> result58 = testSubject0
                .getOwlAnnotationPropertyReferences();
        Pointer<OWLAnonymousIndividual, OWLAxiom> result59 = testSubject0
                .getOwlAnonymousIndividualReferences();
        Pointer<OWLClass, OWLAxiom> result60 = testSubject0
                .getOwlClassReferences();
        Pointer<OWLDataProperty, OWLAxiom> result61 = testSubject0
                .getOwlDataPropertyReferences();
        Pointer<OWLDatatype, OWLAxiom> result62 = testSubject0
                .getOwlDatatypeReferences();
        Pointer<OWLNamedIndividual, OWLAxiom> result63 = testSubject0
                .getOwlIndividualReferences();
        Pointer<OWLObjectProperty, OWLAxiom> result64 = testSubject0
                .getOwlObjectPropertyReferences();
        boolean result65 = testSubject0.hasValues(mock(MapPointer.class),
                mock(Object.class));
        Set<OWLClass> result66 = testSubject0.getKeyset(mock(MapPointer.class));
        Set<OWLClass> result67 = testSubject0.filterAxioms(
                mock(OWLAxiomSearchFilter.class), mock(Object.class));
        testSubject0.removeGeneralClassAxioms(mock(OWLClassAxiom.class));
        boolean result68 = testSubject0
                .removeImportsDeclaration(mock(OWLImportsDeclaration.class));
        boolean result69 = testSubject0
                .removeOntologyAnnotation(mock(OWLAnnotation.class));
        testSubject0
                .removePropertyChainSubPropertyAxioms(mock(OWLSubPropertyChainOfAxiom.class));
        String result116 = testSubject0.toString();
    }

    @Test
    public void shouldTestInternalsNoCache() throws Exception {
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
        OWLDatatype mock = mock(OWLDatatype.class);
        when(mock.getIRI()).thenReturn(OWL2Datatype.XSD_STRING.getIRI());
        OWLLiteral result13 = testSubject0.getOWLLiteral("", mock);
        OWLLiteral result14 = testSubject0.getOWLLiteral(0F);
        OWLLiteral result15 = testSubject0.getOWLLiteral("");
        OWLLiteral result16 = testSubject0.getOWLLiteral("", "");
        OWLLiteral result17 = testSubject0.getOWLLiteral(0);
        OWLLiteral result18 = testSubject0.getOWLLiteral(false);
        testSubject0.purge();
        String result19 = testSubject0.toString();
    }

    @Test
    public void shouldTestMapPointer() throws Exception {
        MapPointer<OWLClass, OWLAxiom> testSubject0 = new MapPointer<OWLClass, OWLAxiom>(
                Utils.mockAxiomType(), Utils.mockAxiom(), false,
                mock(Internals.class));
        boolean result0 = testSubject0.put(mock(OWLClass.class),
                mock(OWLAxiom.class));
        String result1 = testSubject0.toString();
        boolean result2 = testSubject0.contains(mock(OWLClass.class),
                mock(OWLAxiom.class));
        int result3 = testSubject0.size();
        boolean result4 = testSubject0.remove(mock(OWLClass.class),
                mock(OWLAxiom.class));
        testSubject0.init();
        Set<OWLClass> result5 = testSubject0.keySet();
        boolean result6 = testSubject0.containsKey(mock(OWLClass.class));
        Set<OWLAxiom> result7 = testSubject0.getValues(mock(OWLClass.class));
        Set<OWLAxiom> result8 = testSubject0.getAllValues();
        boolean result9 = testSubject0.hasValues(mock(OWLClass.class));
        boolean result10 = testSubject0.isInitialized();
    }

    @Test
    public void shouldTestOWLAnnotationAssertionAxiomImpl() throws Exception {
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
    public void shouldTestOWLAnnotationImpl() throws Exception {
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
    public void shouldTestOWLAnnotationPropertyDomainAxiomImpl()
            throws Exception {
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
    public void shouldTestOWLAnnotationPropertyImpl() throws Exception {
        OWLAnnotationPropertyImpl testSubject0 = new OWLAnnotationPropertyImpl(
                IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        testSubject0.accept(mock(OWLEntityVisitor.class));
        testSubject0.accept(mock(OWLNamedObjectVisitor.class));
        testSubject0.accept(mock(OWLObjectVisitor.class));
        Object result2 = testSubject0.accept(Utils.mockObject());
        Object result3 = testSubject0.accept(Utils.mockEntity());
        boolean result4 = testSubject0.isType(EntityType.CLASS);
        Set<OWLAxiom> result5 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAxiom> result6 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationAssertionAxiom> result7 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
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
        Set<OWLAnnotationProperty> result28 = testSubject0
                .getSubProperties(Utils.getMockOntology());
        Set<OWLAnnotationProperty> result29 = testSubject0.getSubProperties(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationProperty> result30 = testSubject0
                .getSubProperties(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLAnnotationProperty> result31 = testSubject0
                .getSuperProperties(Utils.getMockOntology());
        Set<OWLAnnotationProperty> result32 = testSubject0.getSuperProperties(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationProperty> result33 = testSubject0
                .getSuperProperties(Utils.mockSet(Utils.getMockOntology()));
        String result34 = testSubject0.toString();
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
            throws Exception {
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
    public void shouldTestOWLAnonymousClassExpressionImpl() throws Exception {
        OWLAnonymousClassExpressionImpl testSubject0 = new OWLAnonymousClassExpressionImpl() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public ClassExpressionType getClassExpressionType() {
                return null;
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
        };
        boolean result0 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result1 = testSubject0.asOWLClass();
            }
        }
        boolean result3 = testSubject0.isOWLThing();
        boolean result4 = testSubject0.isOWLNothing();
        OWLClassExpression result6 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result7 = testSubject0.asConjunctSet();
        boolean result8 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result9 = testSubject0.asDisjunctSet();
        String result10 = testSubject0.toString();
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
    public void shouldTestOWLAnonymousIndividualImpl() throws Exception {
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
        Set<OWLClassExpression> result19 = testSubject0.getTypes(Utils
                .getMockOntology());
        Set<OWLClassExpression> result20 = testSubject0.getTypes(Utils
                .mockSet(Utils.getMockOntology()));
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result21 = testSubject0
                .getObjectPropertyValues(Utils.getMockOntology());
        Set<OWLIndividual> result22 = testSubject0.getObjectPropertyValues(
                Utils.mockObjectProperty(), Utils.getMockOntology());
        boolean result23 = testSubject0.hasObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        boolean result24 = testSubject0.hasNegativeObjectPropertyValue(
                Utils.mockObjectProperty(), mock(OWLIndividual.class),
                Utils.getMockOntology());
        Map<OWLObjectPropertyExpression, Set<OWLIndividual>> result25 = testSubject0
                .getNegativeObjectPropertyValues(Utils.getMockOntology());
        Set<OWLLiteral> result26 = testSubject0.getDataPropertyValues(
                mock(OWLDataPropertyExpression.class), Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result27 = testSubject0
                .getDataPropertyValues(Utils.getMockOntology());
        Map<OWLDataPropertyExpression, Set<OWLLiteral>> result28 = testSubject0
                .getNegativeDataPropertyValues(Utils.getMockOntology());
        boolean result29 = testSubject0.hasNegativeDataPropertyValue(
                mock(OWLDataPropertyExpression.class), mock(OWLLiteral.class),
                Utils.getMockOntology());
        Set<OWLIndividual> result30 = testSubject0.getSameIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result31 = testSubject0
                .getDifferentIndividuals(Utils.getMockOntology());
        String result32 = testSubject0.toString();
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
            throws Exception {
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
    public void shouldTestOWLAxiomImpl() throws Exception {
        OWLAxiomImpl testSubject0 = new OWLAxiomImpl(
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
    public void shouldTestOWLCardinalityRestrictionImpl() throws Exception {
        OWLCardinalityRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> testSubject0 = new OWLCardinalityRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression>(
                Utils.mockObjectProperty(), 0, Utils.mockAnonClass()) {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public boolean isQualified() {
                return false;
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
        int result0 = testSubject0.getCardinality();
        OWLPropertyRange result1 = testSubject0.getFiller();
        OWLObjectPropertyExpression result2 = testSubject0.getProperty();
        boolean result3 = testSubject0.isClassExpressionLiteral();
        boolean result4 = testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result5 = testSubject0.asOWLClass();
            }
        }
        boolean result7 = testSubject0.isOWLThing();
        boolean result8 = testSubject0.isOWLNothing();
        OWLClassExpression result10 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result11 = testSubject0.asConjunctSet();
        boolean result12 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result13 = testSubject0.asDisjunctSet();
        String result14 = testSubject0.toString();
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
    public void shouldTestOWLClassAssertionImpl() throws Exception {
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
    public void shouldTestOWLClassAxiomImpl() throws Exception {
        OWLClassAxiomImpl testSubject0 = new OWLClassAxiomImpl(
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
    public void shouldTestOWLClassExpressionImpl() throws Exception {
        OWLClassExpressionImpl testSubject0 = new OWLClassExpressionImpl() {

            /**
             * 
             */
            private static final long serialVersionUID = 30406L;

            @Override
            public ClassExpressionType getClassExpressionType() {
                return null;
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
                return null;
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
                return null;
            }

            @Override
            public OWLClassExpression getComplementNNF() {
                return null;
            }

            @Override
            public OWLClassExpression getObjectComplementOf() {
                return null;
            }

            @Override
            public Set<OWLClassExpression> asConjunctSet() {
                return null;
            }

            @Override
            public boolean containsConjunct(OWLClassExpression ce) {
                return false;
            }

            @Override
            public Set<OWLClassExpression> asDisjunctSet() {
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
        String result0 = testSubject0.toString();
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
            if (!testSubject0.isAnonymous()) {
                OWLClass result17 = testSubject0.asOWLClass();
            }
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
    public void shouldTestOWLClassImpl() throws Exception {
        OWLClassImpl testSubject0 = new OWLClassImpl(IRI("urn:aFake"));
        Set<OWLAnnotation> result0 = testSubject0.getAnnotations(Utils
                .getMockOntology());
        Set<OWLAnnotation> result1 = testSubject0.getAnnotations(
                Utils.getMockOntology(), mock(OWLAnnotationProperty.class));
        boolean result2 = testSubject0.isDefined(Utils.getMockOntology());
        boolean result3 = testSubject0.isDefined(Utils.mockSet(Utils
                .getMockOntology()));
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
        Set<OWLAxiom> result11 = testSubject0.getReferencingAxioms(Utils
                .getMockOntology());
        Set<OWLAxiom> result12 = testSubject0.getReferencingAxioms(
                Utils.getMockOntology(), false);
        Set<OWLAnnotationAssertionAxiom> result13 = testSubject0
                .getAnnotationAssertionAxioms(Utils.getMockOntology());
        IRI result17 = testSubject0.getIRI();
        boolean result18 = testSubject0.isBuiltIn();
        EntityType<?> result19 = testSubject0.getEntityType();
        boolean result21 = !testSubject0.isAnonymous();
        if (!testSubject0.isAnonymous()) {
            if (!testSubject0.isAnonymous()) {
                OWLClass result22 = testSubject0.asOWLClass();
            }
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
        Set<OWLClassExpression> result35 = testSubject0.getSuperClasses(Utils
                .getMockOntology());
        Set<OWLClassExpression> result36 = testSubject0.getSuperClasses(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result37 = testSubject0.getSubClasses(Utils
                .getMockOntology());
        Set<OWLClassExpression> result38 = testSubject0.getSubClasses(Utils
                .mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result39 = testSubject0
                .getEquivalentClasses(Utils.getMockOntology());
        Set<OWLClassExpression> result40 = testSubject0
                .getEquivalentClasses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result41 = testSubject0
                .getDisjointClasses(Utils.mockSet(Utils.getMockOntology()));
        Set<OWLClassExpression> result42 = testSubject0
                .getDisjointClasses(Utils.getMockOntology());
        Set<OWLIndividual> result43 = testSubject0.getIndividuals(Utils
                .getMockOntology());
        Set<OWLIndividual> result44 = testSubject0.getIndividuals(Utils
                .mockSet(Utils.getMockOntology()));
        ClassExpressionType result45 = testSubject0.getClassExpressionType();
        boolean result46 = testSubject0.isClassExpressionLiteral();
        boolean result47 = testSubject0.isOWLThing();
        boolean result48 = testSubject0.isOWLNothing();
        OWLClassExpression result50 = testSubject0.getObjectComplementOf();
        Set<OWLClassExpression> result51 = testSubject0.asConjunctSet();
        boolean result52 = testSubject0.containsConjunct(Utils.mockAnonClass());
        Set<OWLClassExpression> result53 = testSubject0.asDisjunctSet();
        String result55 = testSubject0.toString();
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
    public void shouldTestOWLDataAllValuesFromImpl() throws Exception {
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
    public void shouldTestOWLDataCardinalityRestrictionImpl() throws Exception {
        OWLDataCardinalityRestrictionImpl testSubject0 = new OWLDataCardinalityRestrictionImpl(
                mock(OWLDataPropertyExpression.class), 0,
                mock(OWLDataRange.class)) {

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
        OWLDataPropertyExpression result5 = testSubject0.getProperty();
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
    public void shouldTestOWLDataComplementOfImpl() throws Exception {
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
    public void shouldTestOWLDataExactCardinalityImpl() throws Exception {
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
}
