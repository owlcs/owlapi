package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.List;
import java.util.Set;

import org.coode.owlapi.rdfxml.parser.*;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.xml.sax.SAXException;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractRdfxmlParserTest {

    @Test
    public void shouldTestAbstractClassExpressionTranslator() throws Exception {
        AbstractClassExpressionTranslator testSubject0 = new AbstractClassExpressionTranslator(
                Utils.mockOWLRDFConsumer()) {

            @Override
            public boolean matchesStrict(IRI mainNode) {
                return false;
            }

            @Override
            public boolean matchesLax(IRI mainNode) {
                return false;
            }

            @Override
            public OWLClassExpression translate(IRI mainNode) {
                return null;
            }
        };
        boolean result0 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result1 = testSubject0.getConsumer();
        String result2 = testSubject0.toString();
        OWLClassExpression result3 = testSubject0.translate(IRI("urn:aFake"));
        boolean result4 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result5 = testSubject0.matchesStrict(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestAbstractLiteralTripleHandler() throws Exception {
        AbstractLiteralTripleHandler testSubject0 = new AbstractLiteralTripleHandler(
                Utils.mockOWLRDFConsumer()) {

            @Override
            public void handleTriple(IRI subject, IRI predicate,
                    OWLLiteral object) {}

            @Override
            public boolean canHandle(IRI subject, IRI predicate,
                    OWLLiteral object) {
                return false;
            }

            @Override
            public boolean canHandleStreaming(IRI subject, IRI predicate,
                    OWLLiteral object) {
                return false;
            }
        };
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    public void shouldTestAbstractNamedEquivalentClassAxiomHandler()
            throws Exception {
        AbstractNamedEquivalentClassAxiomHandler testSubject0 = new AbstractNamedEquivalentClassAxiomHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {

            @Override
            protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
                return null;
            }
        };
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractResourceTripleHandler() throws Exception {
        AbstractResourceTripleHandler testSubject0 = new AbstractResourceTripleHandler(
                Utils.mockOWLRDFConsumer()) {

            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}

            @Override
            public boolean canHandleStreaming(IRI subject, IRI predicate,
                    IRI object) throws UnloadableImportException {
                return false;
            }

            @Override
            public boolean canHandle(IRI subject, IRI predicate, IRI object) {
                return false;
            }
        };
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestAbstractTripleHandler() throws Exception {
        AbstractTripleHandler testSubject0 = new AbstractTripleHandler(
                Utils.mockOWLRDFConsumer());
        OWLRDFConsumer result0 = testSubject0.getConsumer();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceAnonymousNodeChecker() throws Exception {
        AnonymousNodeChecker testSubject0 = mock(AnonymousNodeChecker.class);
        boolean result0 = testSubject0.isAnonymousNode(IRI("urn:aFake"));
        boolean result1 = testSubject0.isAnonymousNode("");
        boolean result2 = testSubject0.isAnonymousSharedNode("");
    }

    @Test
    public void shouldTestBuiltInTypeHandler() throws Exception {
        BuiltInTypeHandler testSubject0 = new BuiltInTypeHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {

            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}
        };
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestClassExpressionListItemTranslator() throws Exception {
        ClassExpressionListItemTranslator testSubject0 = new ClassExpressionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLClassExpression result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceClassExpressionTranslator() throws Exception {
        ClassExpressionTranslator testSubject0 = mock(ClassExpressionTranslator.class);
        boolean result0 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestDataAllValuesFromTranslator() throws Exception {
        DataAllValuesFromTranslator testSubject0 = new DataAllValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataAllValuesFrom result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataCardinalityTranslator() throws Exception {
        DataCardinalityTranslator testSubject0 = new DataCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataExactCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataHasValueTranslator() throws Exception {
        DataHasValueTranslator testSubject0 = new DataHasValueTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataHasValue result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataMaxCardinalityTranslator() throws Exception {
        DataMaxCardinalityTranslator testSubject0 = new DataMaxCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMaxCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataMaxQualifiedCardinalityTranslator()
            throws Exception {
        DataMaxQualifiedCardinalityTranslator testSubject0 = new DataMaxQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMaxCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataMinCardinalityTranslator() throws Exception {
        DataMinCardinalityTranslator testSubject0 = new DataMinCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMinCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataMinQualifiedCardinalityTranslator()
            throws Exception {
        DataMinQualifiedCardinalityTranslator testSubject0 = new DataMinQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMinCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataPropertyListItemTranslator() throws Exception {
        DataPropertyListItemTranslator testSubject0 = new DataPropertyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataPropertyExpression result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLDataPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataQualifiedCardinalityTranslator() throws Exception {
        DataQualifiedCardinalityTranslator testSubject0 = new DataQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataExactCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataRangeListItemTranslator() throws Exception {
        DataRangeListItemTranslator testSubject0 = new DataRangeListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataRange result0 = testSubject0.translate(mock(OWLLiteral.class));
        OWLDataRange result1 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataSomeValuesFromTranslator() throws Exception {
        DataSomeValuesFromTranslator testSubject0 = new DataSomeValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataSomeValuesFrom result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    public void shouldTestGTPAnnotationLiteralHandler() throws Exception {
        GTPAnnotationLiteralHandler testSubject0 = new GTPAnnotationLiteralHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    public void shouldTestGTPAnnotationResourceTripleHandler() throws Exception {
        GTPAnnotationResourceTripleHandler testSubject0 = new GTPAnnotationResourceTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    public void shouldTestGTPDataPropertyAssertionHandler() throws Exception {
        GTPDataPropertyAssertionHandler testSubject0 = new GTPDataPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestGTPLiteralTripleHandler() throws Exception {
        GTPLiteralTripleHandler testSubject0 = new GTPLiteralTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestGTPObjectPropertyAssertionHandler() throws Exception {
        GTPObjectPropertyAssertionHandler testSubject0 = new GTPObjectPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestGTPResourceTripleHandler() throws Exception {
        GTPResourceTripleHandler testSubject0 = new GTPResourceTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestHasKeyListItemTranslator() throws Exception {
        HasKeyListItemTranslator testSubject0 = new HasKeyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLPropertyExpression<?, ?> result0 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLPropertyExpression<?, ?> result1 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
        String result4 = testSubject0.toString();
    }

    public void shouldTestIndividualListItemTranslator() throws Exception {
        IndividualListItemTranslator testSubject0 = new IndividualListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLIndividual result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLIndividual result1 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceIRIProvider() throws Exception {
        IRIProvider testSubject0 = mock(IRIProvider.class);
        IRI result0 = testSubject0.getIRI("");
    }

    @Test
    public void shouldTestInterfaceListItemTranslator() throws Exception {
        ListItemTranslator<OWLObject> testSubject0 = mock(ListItemTranslator.class);
        OWLObject result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result1 = testSubject0.translate(mock(OWLLiteral.class));
    }

    @Test
    public void shouldTestNamedClassTranslator() throws Exception {
        NamedClassTranslator testSubject0 = new NamedClassTranslator(
                Utils.mockOWLRDFConsumer());
        OWLClass result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectAllValuesFromTranslator() throws Exception {
        ObjectAllValuesFromTranslator testSubject0 = new ObjectAllValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectAllValuesFrom result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectCardinalityTranslator() throws Exception {
        ObjectCardinalityTranslator testSubject0 = new ObjectCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectExactCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectComplementOfTranslator() throws Exception {
        ObjectComplementOfTranslator testSubject0 = new ObjectComplementOfTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectComplementOf result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectHasSelfTranslator() throws Exception {
        ObjectHasSelfTranslator testSubject0 = new ObjectHasSelfTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectHasSelf result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectHasValueTranslator() throws Exception {
        ObjectHasValueTranslator testSubject0 = new ObjectHasValueTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectHasValue result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectMaxCardinalityTranslator() throws Exception {
        ObjectMaxCardinalityTranslator testSubject0 = new ObjectMaxCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMaxCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectMaxQualifiedCardinalityTranslator()
            throws Exception {
        ObjectMaxQualifiedCardinalityTranslator testSubject0 = new ObjectMaxQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMaxCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectMinCardinalityTranslator() throws Exception {
        ObjectMinCardinalityTranslator testSubject0 = new ObjectMinCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMinCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectMinQualifiedCardinalityTranslator()
            throws Exception {
        ObjectMinQualifiedCardinalityTranslator testSubject0 = new ObjectMinQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMinCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    public void shouldTestObjectPropertyListItemTranslator() throws Exception {
        ObjectPropertyListItemTranslator testSubject0 = new ObjectPropertyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectPropertyExpression result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLObjectPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectQualifiedCardinalityTranslator()
            throws Exception {
        ObjectQualifiedCardinalityTranslator testSubject0 = new ObjectQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectExactCardinality result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestObjectSomeValuesFromTranslator() throws Exception {
        ObjectSomeValuesFromTranslator testSubject0 = new ObjectSomeValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectSomeValuesFrom result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLRDFConsumer result5 = testSubject0.getConsumer();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestOptimisedListTranslator() throws Exception {
        OptimisedListTranslator<OWLObject> testSubject0 = new OptimisedListTranslator<OWLObject>(
                Utils.mockOWLRDFConsumer(), mock(ListItemTranslator.class)) {};
        List<OWLObject> result0 = testSubject0.translateList(IRI("urn:aFake"));
        Set<OWLObject> result1 = testSubject0.translateToSet(IRI("urn:aFake"));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLFacetRestrictionListItemTranslator()
            throws Exception {
        OWLFacetRestrictionListItemTranslator testSubject0 = new OWLFacetRestrictionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLFacetRestriction result0 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLFacetRestriction result1 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
        String result4 = testSubject0.toString();
    }

    public void shouldTestOWLObjectPropertyExpressionListItemTranslator()
            throws Exception {
        OWLObjectPropertyExpressionListItemTranslator testSubject0 = new OWLObjectPropertyExpressionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectPropertyExpression result0 = testSubject0
                .translate(IRI("urn:aFake"));
        OWLObjectPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLRDFConsumer() throws Exception {
        OWLRDFConsumer testSubject0 = new OWLRDFConsumer(
                Utils.getMockOntology(), mock(AnonymousNodeChecker.class),
                new OWLOntologyLoaderConfiguration());
        testSubject0.handle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        testSubject0.handle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        testSubject0.addFirst(IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.addFirst(IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        Set<IRI> result1 = testSubject0.getOntologies();
        OWLOntology result2 = testSubject0.getOntology();
        testSubject0.addAxiom(IRI("urn:aFake"));
        RDFOntologyFormat result3 = testSubject0.getOntologyFormat();
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        OWLOntologyLoaderConfiguration result4 = testSubject0
                .getConfiguration();
        OWLDataFactory result5 = testSubject0.getDataFactory();
        testSubject0.addOntology(IRI("urn:aFake"));
        testSubject0.addTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        testSubject0.addTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        Set<OWLAnnotation> result6 = testSubject0
                .translateAnnotations(IRI("urn:aFake"));
        IRI result7 = testSubject0.getResourceObject(IRI("urn:aFake"),
                IRI("urn:aFake"), false);
        IRI result8 = testSubject0.getResourceObject(IRI("urn:aFake"),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result9 = testSubject0.getLiteralObject(IRI("urn:aFake"),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result10 = testSubject0.getLiteralObject(IRI("urn:aFake"),
                IRI("urn:aFake"), false);
        boolean result11 = testSubject0.isRestriction(IRI("urn:aFake"));
        boolean result12 = testSubject0.isClassExpression(IRI("urn:aFake"));
        boolean result13 = testSubject0.isDataRange(IRI("urn:aFake"));
        boolean result14 = testSubject0.isParsedAllTriples();
        testSubject0.importsClosureChanged();
        testSubject0.setIRIProvider(mock(IRIProvider.class));
        testSubject0.setExpectedAxioms(0);
        Set<OWLAnnotation> result15 = testSubject0.getPendingAnnotations();
        testSubject0.setPendingAnnotations(Utils
                .mockSet(mock(OWLAnnotation.class)));
        OWLAxiom result16 = testSubject0.getLastAddedAxiom();
        testSubject0.addClassExpression(IRI("urn:aFake"), false);
        testSubject0.addObjectProperty(IRI("urn:aFake"), false);
        testSubject0.addDataProperty(IRI("urn:aFake"), false);
        testSubject0.addDataRange(IRI("urn:aFake"), false);
        testSubject0.addAnnotatedSource(IRI("urn:aFake"), IRI("urn:aFake"));
        Set<IRI> result17 = testSubject0
                .getAnnotatedSourceAnnotationMainNodes(IRI("urn:aFake"));
        boolean result18 = testSubject0.isTriplePresent(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class), false);
        boolean result19 = testSubject0.isTriplePresent(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"), false);
        OWLIndividual result20 = testSubject0
                .translateIndividual(IRI("urn:aFake"));
        OWLClassExpression result21 = testSubject0
                .translateClassExpression(IRI("urn:aFake"));
        testSubject0.startModel("");
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        IRI result22 = testSubject0.getSynonym(IRI("urn:aFake"));
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
        OWLDataRange result23 = testSubject0
                .translateDataRange(IRI("urn:aFake"));
        Set<OWLDataRange> result24 = testSubject0
                .translateToDataRangeSet(IRI("urn:aFake"));
        Set<OWLLiteral> result25 = testSubject0
                .translateToConstantSet(IRI("urn:aFake"));
        Set<OWLFacetRestriction> result26 = testSubject0
                .translateToFacetRestrictionSet(IRI("urn:aFake"));
        OWLDataPropertyExpression result27 = testSubject0
                .translateDataPropertyExpression(IRI("urn:aFake"));
        OWLObjectPropertyExpression result28 = testSubject0
                .translateObjectPropertyExpression(IRI("urn:aFake"));
        Set<IRI> result29 = testSubject0
                .getPredicatesBySubject(IRI("urn:aFake"));
        Set<IRI> result30 = testSubject0.getResourceObjects(IRI("urn:aFake"),
                IRI("urn:aFake"));
        Set<OWLLiteral> result31 = testSubject0.getLiteralObjects(
                IRI("urn:aFake"), IRI("urn:aFake"));
        OWLClassExpression result32 = testSubject0
                .getClassExpressionIfTranslated(IRI("urn:aFake"));
        List<OWLObjectPropertyExpression> result33 = testSubject0
                .translateToObjectPropertyList(IRI("urn:aFake"));
        List<OWLDataPropertyExpression> result34 = testSubject0
                .translateToDataPropertyList(IRI("urn:aFake"));
        Set<OWLClassExpression> result35 = testSubject0
                .translateToClassExpressionSet(IRI("urn:aFake"));
        Set<OWLIndividual> result36 = testSubject0
                .translateToIndividualSet(IRI("urn:aFake"));
        boolean result37 = testSubject0.hasPredicate(IRI("urn:aFake"),
                IRI("urn:aFake"));
        testSubject0.addRest(IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result38 = testSubject0.getFirstResource(IRI("urn:aFake"), false);
        OWLLiteral result39 = testSubject0.getFirstLiteral(IRI("urn:aFake"));
        IRI result40 = testSubject0.getRest(IRI("urn:aFake"), false);
        boolean result41 = testSubject0.isAxiom(IRI("urn:aFake"));
        String result42 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLRDFParserException() throws Exception {
        OWLRDFParserException testSubject0 = new OWLRDFParserException();
        OWLRDFParserException testSubject1 = new OWLRDFParserException("");
        OWLRDFParserException testSubject2 = new OWLRDFParserException("",
                new RuntimeException());
        OWLRDFParserException testSubject3 = new OWLRDFParserException(
                new RuntimeException());
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLRDFXMLParserException() throws Exception {
        OWLRDFXMLParserException testSubject0 = new OWLRDFXMLParserException("");
        OWLRDFXMLParserException testSubject1 = new OWLRDFXMLParserException(
                "", new RuntimeException());
        OWLRDFXMLParserException testSubject2 = new OWLRDFXMLParserException(
                new RuntimeException());
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLRDFXMLParserMalformedNodeException()
            throws Exception {
        OWLRDFXMLParserMalformedNodeException testSubject0 = new OWLRDFXMLParserMalformedNodeException(
                new RuntimeException());
        OWLRDFXMLParserMalformedNodeException testSubject1 = new OWLRDFXMLParserMalformedNodeException(
                "", new RuntimeException());
        OWLRDFXMLParserMalformedNodeException testSubject2 = new OWLRDFXMLParserMalformedNodeException(
                "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLRDFXMLParserSAXException() throws Exception {
        OWLRDFXMLParserSAXException testSubject0 = new OWLRDFXMLParserSAXException(
                mock(SAXException.class));
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestRDFXMLParserFactory() throws Exception {
        RDFXMLParserFactory testSubject0 = new RDFXMLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    public void shouldTestSKOSClassTripleHandler() throws Exception {
        SKOSClassTripleHandler testSubject0 = new SKOSClassTripleHandler(
                Utils.mockOWLRDFConsumer(), SKOSVocabulary.ALTLABEL);
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestSWRLRuleTranslator() throws Exception {
        SWRLRuleTranslator testSubject0 = new SWRLRuleTranslator(
                Utils.mockOWLRDFConsumer());
        testSubject0.translateRule(IRI("urn:aFake"));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPAllValuesFromHandler() throws Exception {
        TPAllValuesFromHandler testSubject0 = new TPAllValuesFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPAnnotatedPropertyHandler() throws Exception {
        TPAnnotatedPropertyHandler testSubject0 = new TPAnnotatedPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPAnnotatedSourceHandler() throws Exception {
        TPAnnotatedSourceHandler testSubject0 = new TPAnnotatedSourceHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPAnnotatedTargetHandler() throws Exception {
        TPAnnotatedTargetHandler testSubject0 = new TPAnnotatedTargetHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPComplementOfHandler() throws Exception {
        TPComplementOfHandler testSubject0 = new TPComplementOfHandler(
                Utils.mockOWLRDFConsumer());
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPDatatypeComplementOfHandler() throws Exception {
        TPDatatypeComplementOfHandler testSubject0 = new TPDatatypeComplementOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPDeclaredAsHandler() throws Exception {
        TPDeclaredAsHandler testSubject0 = new TPDeclaredAsHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPDifferentFromHandler() throws Exception {
        TPDifferentFromHandler testSubject0 = new TPDifferentFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPDisjointUnionHandler() throws Exception {
        TPDisjointUnionHandler testSubject0 = new TPDisjointUnionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPDisjointWithHandler() throws Exception {
        TPDisjointWithHandler testSubject0 = new TPDisjointWithHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPDistinctMembersHandler() throws Exception {
        TPDistinctMembersHandler testSubject0 = new TPDistinctMembersHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPEquivalentClassHandler() throws Exception {
        TPEquivalentClassHandler testSubject0 = new TPEquivalentClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPEquivalentPropertyHandler() throws Exception {
        TPEquivalentPropertyHandler testSubject0 = new TPEquivalentPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPFirstLiteralHandler() throws Exception {
        TPFirstLiteralHandler testSubject0 = new TPFirstLiteralHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
        OWLRDFConsumer result2 = testSubject0.getConsumer();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPFirstResourceHandler() throws Exception {
        TPFirstResourceHandler testSubject0 = new TPFirstResourceHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPHasKeyHandler() throws Exception {
        TPHasKeyHandler testSubject0 = new TPHasKeyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPHasValueHandler() throws Exception {
        TPHasValueHandler testSubject0 = new TPHasValueHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPImportsHandler() throws Exception {
        TPImportsHandler testSubject0 = new TPImportsHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPIntersectionOfHandler() throws Exception {
        TPIntersectionOfHandler testSubject0 = new TPIntersectionOfHandler(
                Utils.mockOWLRDFConsumer());
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPInverseOfHandler() throws Exception {
        TPInverseOfHandler testSubject0 = new TPInverseOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.setAxiomParsingMode(false);
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.isAxiomParsingMode();
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPOnClassHandler() throws Exception {
        TPOnClassHandler testSubject0 = new TPOnClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPOnDataRangeHandler() throws Exception {
        TPOnDataRangeHandler testSubject0 = new TPOnDataRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPOneOfHandler() throws Exception {
        TPOneOfHandler testSubject0 = new TPOneOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPOnPropertyHandler() throws Exception {
        TPOnPropertyHandler testSubject0 = new TPOnPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPPropertyChainAxiomHandler() throws Exception {
        TPPropertyChainAxiomHandler testSubject0 = new TPPropertyChainAxiomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPPropertyDisjointWithHandler() throws Exception {
        TPPropertyDisjointWithHandler testSubject0 = new TPPropertyDisjointWithHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPPropertyDomainHandler() throws Exception {
        TPPropertyDomainHandler testSubject0 = new TPPropertyDomainHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPPropertyRangeHandler() throws Exception {
        TPPropertyRangeHandler testSubject0 = new TPPropertyRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPRestHandler() throws Exception {
        TPRestHandler testSubject0 = new TPRestHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPSameAsHandler() throws Exception {
        TPSameAsHandler testSubject0 = new TPSameAsHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPSomeValuesFromHandler() throws Exception {
        TPSomeValuesFromHandler testSubject0 = new TPSomeValuesFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPSubClassOfHandler() throws Exception {
        TPSubClassOfHandler testSubject0 = new TPSubClassOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPSubPropertyOfHandler() throws Exception {
        TPSubPropertyOfHandler testSubject0 = new TPSubPropertyOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPTypeHandler() throws Exception {
        TPTypeHandler testSubject0 = new TPTypeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    public void shouldTestTPUnionOfHandler() throws Exception {
        TPUnionOfHandler testSubject0 = new TPUnionOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTPVersionIRIHandler() throws Exception {
        TPVersionIRIHandler testSubject0 = new TPVersionIRIHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTranslatedOntologyChangeException() throws Exception {
        TranslatedOntologyChangeException testSubject0 = new TranslatedOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.toString();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadedImportException() throws Exception {
        TranslatedUnloadedImportException testSubject0 = new TranslatedUnloadedImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.toString();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceTriplePatternMatcher() throws Exception {
        TriplePatternMatcher testSubject0 = mock(TriplePatternMatcher.class);
        boolean result0 = testSubject0.matches(Utils.mockOWLRDFConsumer(),
                IRI("urn:aFake"));
        OWLObject result1 = testSubject0.createObject(Utils
                .mockOWLRDFConsumer());
    }

    @Test
    public void shouldTestTriplePredicateHandler() throws Exception {
        TriplePredicateHandler testSubject0 = new TriplePredicateHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {

            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}

            @Override
            public boolean canHandleStreaming(IRI subject, IRI predicate,
                    IRI object) throws UnloadableImportException {
                return false;
            }
        };
        IRI result0 = testSubject0.getPredicateIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result3 = testSubject0.getConsumer();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeAllDifferentHandler() throws Exception {
        TypeAllDifferentHandler testSubject0 = new TypeAllDifferentHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getTypeIRI();
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeAllDisjointClassesHandler() throws Exception {
        TypeAllDisjointClassesHandler testSubject0 = new TypeAllDisjointClassesHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getTypeIRI();
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeAnnotationHandler() throws Exception {
        TypeAnnotationHandler testSubject0 = new TypeAnnotationHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeAnnotationPropertyHandler() throws Exception {
        TypeAnnotationPropertyHandler testSubject0 = new TypeAnnotationPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeAsymmetricPropertyHandler() throws Exception {
        TypeAsymmetricPropertyHandler testSubject0 = new TypeAsymmetricPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeAxiomHandler() throws Exception {
        TypeAxiomHandler testSubject0 = new TypeAxiomHandler(
                Utils.mockOWLRDFConsumer());
        TypeAxiomHandler testSubject1 = new TypeAxiomHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeClassHandler() throws Exception {
        TypeClassHandler testSubject0 = new TypeClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeDataPropertyHandler() throws Exception {
        TypeDataPropertyHandler testSubject0 = new TypeDataPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeDataRangeHandler() throws Exception {
        TypeDataRangeHandler testSubject0 = new TypeDataRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeDatatypeHandler() throws Exception {
        TypeDatatypeHandler testSubject0 = new TypeDatatypeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypedConstantListItemTranslator() throws Exception {
        TypedConstantListItemTranslator testSubject0 = new TypedConstantListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLLiteral result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLLiteral result1 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
        String result4 = testSubject0.toString();
    }

    public void shouldTestTypeDeprecatedClassHandler() throws Exception {
        TypeDeprecatedClassHandler testSubject0 = new TypeDeprecatedClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeDeprecatedPropertyHandler() throws Exception {
        TypeDeprecatedPropertyHandler testSubject0 = new TypeDeprecatedPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeFunctionalPropertyHandler() throws Exception {
        TypeFunctionalPropertyHandler testSubject0 = new TypeFunctionalPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeInverseFunctionalPropertyHandler()
            throws Exception {
        TypeInverseFunctionalPropertyHandler testSubject0 = new TypeInverseFunctionalPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeIrreflexivePropertyHandler() throws Exception {
        TypeIrreflexivePropertyHandler testSubject0 = new TypeIrreflexivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeListHandler() throws Exception {
        TypeListHandler testSubject0 = new TypeListHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeNamedIndividualHandler() throws Exception {
        TypeNamedIndividualHandler testSubject0 = new TypeNamedIndividualHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeNegativeDataPropertyAssertionHandler()
            throws Exception {
        TypeNegativeDataPropertyAssertionHandler testSubject0 = new TypeNegativeDataPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeNegativePropertyAssertionHandler()
            throws Exception {
        TypeNegativePropertyAssertionHandler testSubject0 = new TypeNegativePropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeObjectPropertyHandler() throws Exception {
        TypeObjectPropertyHandler testSubject0 = new TypeObjectPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeOntologyHandler() throws Exception {
        TypeOntologyHandler testSubject0 = new TypeOntologyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeOntologyPropertyHandler() throws Exception {
        TypeOntologyPropertyHandler testSubject0 = new TypeOntologyPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypePropertyHandler() throws Exception {
        TypePropertyHandler testSubject0 = new TypePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeRDFSClassHandler() throws Exception {
        TypeRDFSClassHandler testSubject0 = new TypeRDFSClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeReflexivePropertyHandler() throws Exception {
        TypeReflexivePropertyHandler testSubject0 = new TypeReflexivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeRestrictionHandler() throws Exception {
        TypeRestrictionHandler testSubject0 = new TypeRestrictionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSelfRestrictionHandler() throws Exception {
        TypeSelfRestrictionHandler testSubject0 = new TypeSelfRestrictionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLAtomListHandler() throws Exception {
        TypeSWRLAtomListHandler testSubject0 = new TypeSWRLAtomListHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLBuiltInAtomHandler() throws Exception {
        TypeSWRLBuiltInAtomHandler testSubject0 = new TypeSWRLBuiltInAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLBuiltInHandler() throws Exception {
        TypeSWRLBuiltInHandler testSubject0 = new TypeSWRLBuiltInHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLClassAtomHandler() throws Exception {
        TypeSWRLClassAtomHandler testSubject0 = new TypeSWRLClassAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLDataRangeAtomHandler() throws Exception {
        TypeSWRLDataRangeAtomHandler testSubject0 = new TypeSWRLDataRangeAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLDataValuedPropertyAtomHandler()
            throws Exception {
        TypeSWRLDataValuedPropertyAtomHandler testSubject0 = new TypeSWRLDataValuedPropertyAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLDifferentIndividualsAtomHandler()
            throws Exception {
        TypeSWRLDifferentIndividualsAtomHandler testSubject0 = new TypeSWRLDifferentIndividualsAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLImpHandler() throws Exception {
        TypeSWRLImpHandler testSubject0 = new TypeSWRLImpHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLIndividualPropertyAtomHandler()
            throws Exception {
        TypeSWRLIndividualPropertyAtomHandler testSubject0 = new TypeSWRLIndividualPropertyAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLSameIndividualAtomHandler() throws Exception {
        TypeSWRLSameIndividualAtomHandler testSubject0 = new TypeSWRLSameIndividualAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestTypeSWRLVariableHandler() throws Exception {
        TypeSWRLVariableHandler testSubject0 = new TypeSWRLVariableHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeSymmetricPropertyHandler() throws Exception {
        TypeSymmetricPropertyHandler testSubject0 = new TypeSymmetricPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }

    public void shouldTestTypeTransitivePropertyHandler() throws Exception {
        TypeTransitivePropertyHandler testSubject0 = new TypeTransitivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
        testSubject0.inferTypes(IRI("urn:aFake"), IRI("urn:aFake"));
        OWLRDFConsumer result4 = testSubject0.getConsumer();
        String result5 = testSubject0.toString();
    }
}
