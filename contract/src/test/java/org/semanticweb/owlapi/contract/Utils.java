package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.owlxmlparser.AbstractClassExpressionElementHandler;
import org.coode.owlapi.owlxmlparser.AbstractIRIElementHandler;
import org.coode.owlapi.owlxmlparser.AbstractOWLAxiomElementHandler;
import org.coode.owlapi.owlxmlparser.AbstractOWLDataRangeHandler;
import org.coode.owlapi.owlxmlparser.AbstractOWLIndividualOperandAxiomElementHandler;
import org.coode.owlapi.owlxmlparser.OWLAnnotationElementHandler;
import org.coode.owlapi.owlxmlparser.OWLAnnotationPropertyElementHandler;
import org.coode.owlapi.owlxmlparser.OWLAnonymousIndividualElementHandler;
import org.coode.owlapi.owlxmlparser.OWLDataPropertyElementHandler;
import org.coode.owlapi.owlxmlparser.OWLDatatypeElementHandler;
import org.coode.owlapi.owlxmlparser.OWLElementHandler;
import org.coode.owlapi.owlxmlparser.OWLLiteralElementHandler;
import org.coode.owlapi.owlxmlparser.OWLObjectPropertyElementHandler;
import org.coode.owlapi.owlxmlparser.OWLSubAnnotationPropertyOfElementHandler;
import org.coode.owlapi.owlxmlparser.OWLSubClassAxiomElementHandler;
import org.coode.owlapi.owlxmlparser.OWLXMLParserException;
import org.coode.owlapi.owlxmlparser.OWLXMLParserHandler;
import org.coode.owlapi.owlxmlparser.SWRLAtomElementHandler;
import org.coode.owlapi.owlxmlparser.SWRLAtomListElementHandler;
import org.coode.owlapi.owlxmlparser.SWRLVariableElementHandler;
import org.coode.owlapi.rdfxml.parser.AnonymousNodeChecker;
import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLObjectVisitorExAdapter;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import uk.ac.manchester.cs.owl.owlapi.CollectionContainer;
import uk.ac.manchester.cs.owl.owlapi.CollectionContainerVisitor;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class Utils {

    public static AxiomType<OWLAnnotationAssertionAxiom> mockAxiomType() {
        return AxiomType.ANNOTATION_ASSERTION;
    }

    public static <T> Collection<T> mockCollection(T... values) {
        return new ArrayList<T>();
    }

    public static <T> Set<T> mockSet(T... values) {
        return new HashSet<T>();
    }

    public static Set<Set<OWLAxiom>> mockSetSetAxiom() {
        return new HashSet<Set<OWLAxiom>>();
    }

    public static <T> List<T> mockList(T... values) {
        return new ArrayList<T>();
    }

    public static <T extends OWLObject> Node<T> mockNode(Class<T> classT) {
        return Mockito.mock(Node.class);
    }

    public static <T extends OWLObject> NodeSet<T> mockNodeSet(Class<T> classT) {
        return Mockito.mock(NodeSet.class);
    }

    public static SWRLObjectVisitorEx<OWLObject> mockSWRLObject() {
        return new SWRLObjectVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(SWRLRule node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLClassAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLDataRangeAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLObjectPropertyAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLDataPropertyAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLBuiltInAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLVariable node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLIndividualArgument node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLLiteralArgument node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLSameIndividualAtom node) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLDifferentIndividualsAtom node) {
                return null;
            }
        };
    }

    public static <T> CollectionContainerVisitor<T> mockCollContainer() {
        return new CollectionContainerVisitor<T>() {

            @Override
            public void visitItem(T c) {}

            @Override
            public void visit(CollectionContainer<T> c) {}
        };
    }

    public static OWLXMLParserHandler mockHandler() throws OWLParserException {
        OWLXMLParserHandler mock = mock(OWLXMLParserHandler.class);
        Mockito.when(mock.getIRI(Matchers.anyString())).thenReturn(
                IRI("urn:otherfake"));
        Mockito.when(mock.getDataFactory()).thenReturn(
                mock(OWLDataFactory.class));
        Mockito.when(mock.getOWLOntologyManager()).thenReturn(
                Utils.getMockManager());
        Mockito.when(mock.getOntology()).thenReturn(getMockOntology());
        return mock;
    }

    public static OWLElementHandler<OWLObject> mockElementHandler()
            throws OWLXMLParserException {
        OWLElementHandler<OWLObject> mock = mock(OWLElementHandler.class);
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLThing());
        return mock;
    }

    public static AbstractOWLIndividualOperandAxiomElementHandler
            mockIndividualHandler() throws OWLParserException {
        AbstractOWLIndividualOperandAxiomElementHandler mock = mock(AbstractOWLIndividualOperandAxiomElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLDeclarationAxiom(
                        Class(IRI("urn:fake"))));
        return mock;
    }

    public static OWLAnnotationElementHandler mockAnnotationHandler()
            throws OWLParserException {
        OWLAnnotationElementHandler mock = mock(OWLAnnotationElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLAnnotation(
                        OWLManager.getOWLDataFactory().getRDFSLabel(),
                        OWLManager.getOWLDataFactory().getOWLLiteral(0)));
        return mock;
    }

    public static AbstractOWLDataRangeHandler mockDataRangeHandler()
            throws OWLParserException {
        AbstractOWLDataRangeHandler mock = mock(AbstractOWLDataRangeHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(mock(OWLDatatype.class));
        return mock;
    }

    public static OWLAnonymousIndividualElementHandler
            mockAnonymousIndividualHandler() throws OWLParserException {
        OWLAnonymousIndividualElementHandler mock = mock(OWLAnonymousIndividualElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLAnonymousIndividual());
        return mock;
    }

    public static OWLLiteralElementHandler mockLiteralHandler()
            throws OWLParserException {
        OWLLiteralElementHandler mock = mock(OWLLiteralElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLLiteral(false));
        return mock;
    }

    public static OWLSubAnnotationPropertyOfElementHandler
            mockSubAnnotationPropertyOfHandler() throws OWLParserException {
        OWLSubAnnotationPropertyOfElementHandler mock = mock(OWLSubAnnotationPropertyOfElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLDeclarationAxiom(
                        OWLManager.getOWLDataFactory().getOWLThing()));
        return mock;
    }

    public static OWLSubClassAxiomElementHandler
            mockSubObjectPropertyChainHandler() throws OWLParserException {
        OWLSubClassAxiomElementHandler mock = mock(OWLSubClassAxiomElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLDeclarationAxiom(
                        OWLManager.getOWLDataFactory().getOWLThing()));
        return mock;
    }

    public static OWLDatatypeElementHandler
            mockDatatypeFacetRestrictionHandler() throws OWLParserException {
        OWLDatatypeElementHandler mock = mock(OWLDatatypeElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLDatatype(
                        OWL2Datatype.OWL_REAL.getIRI()));
        return mock;
    }

    public static OWLAnnotationPropertyElementHandler
            mockAnnotationPropertyHandler() throws OWLParserException {
        OWLAnnotationPropertyElementHandler mock = mock(OWLAnnotationPropertyElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getRDFSComment());
        return mock;
    }

    public static AbstractClassExpressionElementHandler mockClassHandler()
            throws OWLParserException {
        AbstractClassExpressionElementHandler mock = mock(AbstractClassExpressionElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLThing());
        return mock;
    }

    public static OWLDataPropertyElementHandler mockDataPropertyHandler()
            throws OWLParserException {
        OWLDataPropertyElementHandler mock = mock(OWLDataPropertyElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLTopDataProperty());
        return mock;
    }

    public static AbstractIRIElementHandler mockAbstractIRIHandler()
            throws OWLParserException {
        AbstractIRIElementHandler mock = mock(AbstractIRIElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(IRI(""));
        return mock;
    }

    public static AbstractOWLAxiomElementHandler mockAxiomHandler()
            throws OWLParserException {
        AbstractOWLAxiomElementHandler mock = mock(AbstractOWLAxiomElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLDeclarationAxiom(
                        OWLManager.getOWLDataFactory()
                                .getOWLTopObjectProperty()));
        return mock;
    }

    public static SWRLAtomElementHandler mockSWRLAtomHandler()
            throws OWLParserException {
        SWRLAtomElementHandler mock = mock(SWRLAtomElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(mock(SWRLAtom.class));
        return mock;
    }

    public static SWRLVariableElementHandler mockSWRLVariableHandler()
            throws OWLParserException {
        SWRLVariableElementHandler mock = mock(SWRLVariableElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(mock(SWRLVariable.class));
        return mock;
    }

    public static SWRLAtomListElementHandler mockSWRLAtomListHandler()
            throws OWLParserException {
        SWRLAtomListElementHandler mock = mock(SWRLAtomListElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                mockList(mock(SWRLAtom.class)));
        return mock;
    }

    public static OWLObjectPropertyElementHandler mockObjectPropertyHandler()
            throws OWLParserException {
        OWLObjectPropertyElementHandler mock = mock(OWLObjectPropertyElementHandler.class);
        Mockito.when(
                mock.getIRIFromAttribute(Matchers.anyString(),
                        Matchers.anyString())).thenReturn(IRI("urn:otherfake"));
        Mockito.when(mock.getOWLObject()).thenReturn(
                OWLManager.getOWLDataFactory().getOWLTopObjectProperty());
        return mock;
    }

    public static OWLAnnotationAxiomVisitorEx<OWLObject> mockAnnotationAxiom() {
        return new OWLAnnotationAxiomVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLAnnotationAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubAnnotationPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyRangeAxiom axiom) {
                return null;
            }
        };
    }

    public static OWLObjectVisitorEx<OWLObject> mockObject() {
        return new OWLObjectVisitorExAdapter<OWLObject>();
    }

    public static OWLClassExpressionVisitorEx<OWLObject> mockClassExpression() {
        return new OWLClassExpressionVisitorExAdapter<OWLObject>();
    }

    public static OWLAnnotationObjectVisitorEx<OWLObject>
            mockAnnotationObject() {
        return new OWLAnnotationObjectVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLAnnotationAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubAnnotationPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(IRI iri) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnonymousIndividual individual) {
                return null;
            }

            @Override
            public OWLObject visit(OWLLiteral literal) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotation node) {
                return null;
            }
        };
    }

    public static OWLAnnotationSubjectVisitorEx<OWLObject>
            mockAnnotationSubject() {
        return new OWLAnnotationSubjectVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(IRI iri) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnonymousIndividual individual) {
                return null;
            }
        };
    }

    public static OWLAnnotationValueVisitorEx<OWLObject> mockAnnotationValue() {
        return new OWLAnnotationValueVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(IRI iri) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnonymousIndividual individual) {
                return null;
            }

            @Override
            public OWLObject visit(OWLLiteral literal) {
                return null;
            }
        };
    }

    public static OWLDataRangeVisitorEx<OWLObject> mockDataRange() {
        return new OWLDataRangeVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLDatatype node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataOneOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataComplementOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataIntersectionOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataUnionOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDatatypeRestriction node) {
                return null;
            }
        };
    }

    public static OWLPropertyExpressionVisitorEx<OWLObject>
            mockPropertyExpression() {
        return new OWLPropertyExpressionVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLObjectProperty property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectInverseOf property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataProperty property) {
                return null;
            }
        };
    }

    public static OWLLogicalAxiomVisitorEx<OWLObject> mockLogicalAxiom() {
        return new OWLLogicalAxiomVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLSubClassOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject
                    visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAsymmetricObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLReflexiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointClassesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDifferentIndividualsAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointDataPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLFunctionalObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubObjectPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointUnionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSymmetricObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLFunctionalDataPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentDataPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLClassAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentClassesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLTransitiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubDataPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject
                    visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSameIndividualAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubPropertyChainOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLInverseObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLHasKeyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLRule rule) {
                return null;
            }
        };
    }

    public static OWLNamedObjectVisitorEx<OWLObject> mockNamedObject() {
        return new OWLNamedObjectVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLClass owlClass) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectProperty property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataProperty property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLNamedIndividual owlIndividual) {
                return null;
            }

            @Override
            public OWLObject visit(OWLOntology ontology) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDatatype datatype) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationProperty property) {
                return null;
            }
        };
    }

    public static OWLEntity mockOWLEntity() {
        OWLEntity mock = mock(OWLEntity.class);
        when(mock.getIRI()).thenReturn(IRI("urn:fakeiri#fakefragment"));
        return mock;
    }

    public static OWLEntityVisitorEx<OWLObject> mockEntity() {
        return new OWLEntityVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLClass cls) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectProperty property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataProperty property) {
                return null;
            }

            @Override
            public OWLObject visit(OWLNamedIndividual individual) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDatatype datatype) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationProperty property) {
                return null;
            }
        };
    }

    public static OWLIndividualVisitorEx<OWLObject> mockIndividual() {
        return new OWLIndividualVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLNamedIndividual individual) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnonymousIndividual individual) {
                return null;
            }
        };
    }

    public static OWLOntologyChangeVisitorEx<OWLObject> mockOntologyChange() {
        return new OWLOntologyChangeVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(AddAxiom change) {
                return null;
            }

            @Override
            public OWLObject visit(RemoveAxiom change) {
                return null;
            }

            @Override
            public OWLObject visit(SetOntologyID change) {
                return null;
            }

            @Override
            public OWLObject visit(AddImport change) {
                return null;
            }

            @Override
            public OWLObject visit(RemoveImport change) {
                return null;
            }

            @Override
            public OWLObject visit(AddOntologyAnnotation change) {
                return null;
            }

            @Override
            public OWLObject visit(RemoveOntologyAnnotation change) {
                return null;
            }
        };
    }

    public static OWLAxiomVisitorEx<OWLObject> mockAxiom() {
        return new OWLAxiomVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLSubAnnotationPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubClassOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject
                    visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAsymmetricObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLReflexiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointClassesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyDomainAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDifferentIndividualsAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointDataPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLObjectPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLFunctionalObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubObjectPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDisjointUnionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDeclarationAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLAnnotationAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSymmetricObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyRangeAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLFunctionalDataPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentDataPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLClassAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLEquivalentClassesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataPropertyAssertionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLTransitiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubDataPropertyOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject
                    visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSameIndividualAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLSubPropertyChainOfAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLInverseObjectPropertiesAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLHasKeyAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDatatypeDefinitionAxiom axiom) {
                return null;
            }

            @Override
            public OWLObject visit(SWRLRule rule) {
                return null;
            }
        };
    }

    public static OWLDataVisitorEx<OWLObject> mockData() {
        return new OWLDataVisitorEx<OWLObject>() {

            @Override
            public OWLObject visit(OWLDatatype node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataComplementOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataOneOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataIntersectionOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDataUnionOf node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLDatatypeRestriction node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLLiteral node) {
                return null;
            }

            @Override
            public OWLObject visit(OWLFacetRestriction node) {
                return null;
            }
        };
    }

    public static OWLOntologyManager getMockManager() {
        return OWLManager.createOWLOntologyManager();
    }

    public static OWLOntologyManager getRealMockManager() {
        OWLOntologyManager mock = mock(OWLOntologyManager.class);
        when(mock.getOWLDataFactory()).thenReturn(
                OWLManager.getOWLDataFactory());
        return mock;
    }

    public static OWLOntologyManager getMockManagerMockFactory() {
        OWLOntologyManager mock = mock(OWLOntologyManager.class);
        OWLDataFactory f = mock(OWLDataFactory.class);
        when(mock.getOWLDataFactory()).thenReturn(f);
        return mock;
    }

    public static OWLOntology getMockOntology() {
        try {
            return OWLManager.createOWLOntologyManager().createOntology();
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public static OWLClassExpression mockAnonClass() {
        OWLClassExpression mock = mock(OWLClassExpression.class);
        when(mock.isAnonymous()).thenReturn(true);
        return mock;
    }

    public static OWLRDFConsumer mockOWLRDFConsumer() {
        OWLOntology mockOntology = mock(OWLOntology.class);
        when(mockOntology.getOntologyID()).thenReturn(
                new OWLOntologyID(IRI("urn:test:test"),
                        IRI("urn:test:othertest")));
        OWLOntologyManager man = getMockManagerMockFactory();
        when(mockOntology.getOWLOntologyManager()).thenReturn(man);
        OWLRDFConsumer c = new OWLRDFConsumer(mockOntology,
                new AnonymousNodeChecker() {

                    @Override
                    public boolean isAnonymousSharedNode(String iri) {
                        return false;
                    }

                    @Override
                    public boolean isAnonymousNode(String iri) {
                        return false;
                    }

                    @Override
                    public boolean isAnonymousNode(IRI iri) {
                        return false;
                    }
                }, new OWLOntologyLoaderConfiguration());
        c.setOntologyFormat(new RDFOntologyFormat() {

            private static final long serialVersionUID = 30406L;
        });
        return c;
    }

    public static OWLReasoner structReasoner() {
        OWLReasoner r;
        try {
            r = new StructuralReasoner(OWLManager.createOWLOntologyManager()
                    .createOntology(), new SimpleConfiguration(),
                    BufferingMode.BUFFERING);
        } catch (OWLOntologyCreationException e) {
            throw new RuntimeException(e);
        }
        return r;
    }

    public static OWLObjectPropertyExpression mockObjectProperty() {
        return OWLManager.getOWLDataFactory().getOWLTopObjectProperty();
    }

    public static OWLLiteral mockLiteral() {
        return OWLManager.getOWLDataFactory().getOWLLiteral(false);
    }
}
