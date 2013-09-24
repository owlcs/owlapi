package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.List;
import java.util.Set;

import org.coode.owlapi.rdfxml.parser.*;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLException;
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
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.xml.sax.SAXException;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractRdfxmlParserTest {
    @Test
    public void shouldTestAbstractClassExpressionTranslator() throws OWLException {
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
        OWLClassExpression result3 = testSubject0.translate(IRI("urn:aFake"));
        boolean result4 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result5 = testSubject0.matchesStrict(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestAbstractLiteralTripleHandler() throws OWLException {
        AbstractLiteralTripleHandler testSubject0 = new AbstractLiteralTripleHandler(
                Utils.mockOWLRDFConsumer()) {
            @Override
            public void handleTriple(IRI subject, IRI predicate, OWLLiteral object) {}

            @Override
            public boolean canHandle(IRI subject, IRI predicate, OWLLiteral object) {
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
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
    }

    public void shouldTestAbstractNamedEquivalentClassAxiomHandler() throws OWLException {
        AbstractNamedEquivalentClassAxiomHandler testSubject0 = new AbstractNamedEquivalentClassAxiomHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {
            @Override
            protected OWLClassExpression translateEquivalentClass(IRI mainNode) {
                return null;
            }
        };
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestAbstractResourceTripleHandler() throws OWLException {
        AbstractResourceTripleHandler testSubject0 = new AbstractResourceTripleHandler(
                Utils.mockOWLRDFConsumer()) {
            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}

            @Override
            public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {
                return false;
            }

            @Override
            public boolean canHandle(IRI subject, IRI predicate, IRI object) {
                return false;
            }
        };
    }

    @Test
    public void shouldTestBuiltInTypeHandler() throws OWLException {
        BuiltInTypeHandler testSubject0 = new BuiltInTypeHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {
            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}
        };
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestClassExpressionListItemTranslator() throws OWLException {
        ClassExpressionListItemTranslator testSubject0 = new ClassExpressionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLClassExpression result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInterfaceClassExpressionTranslator() throws OWLException {
        ClassExpressionTranslator testSubject0 = mock(ClassExpressionTranslator.class);
        boolean result0 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
    }

    @Ignore
    @Test
    public void shouldTestDataAllValuesFromTranslator() throws OWLException {
        DataAllValuesFromTranslator testSubject0 = new DataAllValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataAllValuesFrom result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataCardinalityTranslator() throws OWLException {
        DataCardinalityTranslator testSubject0 = new DataCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataExactCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataHasValueTranslator() throws OWLException {
        DataHasValueTranslator testSubject0 = new DataHasValueTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataHasValue result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataMaxCardinalityTranslator() throws OWLException {
        DataMaxCardinalityTranslator testSubject0 = new DataMaxCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMaxCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataMaxQualifiedCardinalityTranslator() throws OWLException {
        DataMaxQualifiedCardinalityTranslator testSubject0 = new DataMaxQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMaxCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataMinCardinalityTranslator() throws OWLException {
        DataMinCardinalityTranslator testSubject0 = new DataMinCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMinCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestDataMinQualifiedCardinalityTranslator() throws OWLException {
        DataMinQualifiedCardinalityTranslator testSubject0 = new DataMinQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataMinCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Test
    public void shouldTestDataPropertyListItemTranslator() throws OWLException {
        DataPropertyListItemTranslator testSubject0 = new DataPropertyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataPropertyExpression result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLDataPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    @Ignore
    @Test
    public void shouldTestDataQualifiedCardinalityTranslator() throws OWLException {
        DataQualifiedCardinalityTranslator testSubject0 = new DataQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataExactCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Test
    public void shouldTestDataRangeListItemTranslator() throws OWLException {
        DataRangeListItemTranslator testSubject0 = new DataRangeListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataRange result0 = testSubject0.translate(mock(OWLLiteral.class));
        OWLDataRange result1 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
    }

    @Ignore
    @Test
    public void shouldTestDataSomeValuesFromTranslator() throws OWLException {
        DataSomeValuesFromTranslator testSubject0 = new DataSomeValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLDataSomeValuesFrom result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    public void shouldTestGTPAnnotationLiteralHandler() throws OWLException {
        GTPAnnotationLiteralHandler testSubject0 = new GTPAnnotationLiteralHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
    }

    public void shouldTestGTPAnnotationResourceTripleHandler() throws OWLException {
        GTPAnnotationResourceTripleHandler testSubject0 = new GTPAnnotationResourceTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
    }

    public void shouldTestGTPDataPropertyAssertionHandler() throws OWLException {
        GTPDataPropertyAssertionHandler testSubject0 = new GTPDataPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
    }

    @Test
    public void shouldTestGTPLiteralTripleHandler() throws OWLException {
        GTPLiteralTripleHandler testSubject0 = new GTPLiteralTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
    }

    @Test
    public void shouldTestGTPObjectPropertyAssertionHandler() throws OWLException {
        GTPObjectPropertyAssertionHandler testSubject0 = new GTPObjectPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
    }

    @Test
    public void shouldTestGTPResourceTripleHandler() throws OWLException {
        GTPResourceTripleHandler testSubject0 = new GTPResourceTripleHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
    }

    @Test
    public void shouldTestHasKeyListItemTranslator() throws OWLException {
        HasKeyListItemTranslator testSubject0 = new HasKeyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLPropertyExpression result0 = testSubject0.translate(mock(OWLLiteral.class));
        OWLPropertyExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
    }

    public void shouldTestIndividualListItemTranslator() throws OWLException {
        IndividualListItemTranslator testSubject0 = new IndividualListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLIndividual result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLIndividual result1 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestInterfaceIRIProvider() throws OWLException {
        IRIProvider testSubject0 = mock(IRIProvider.class);
        IRI result0 = testSubject0.getIRI("");
    }

    @Test
    public void shouldTestInterfaceListItemTranslator() throws OWLException {
        ListItemTranslator<OWLObject> testSubject0 = mock(ListItemTranslator.class);
        OWLObject result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result1 = testSubject0.translate(mock(OWLLiteral.class));
    }

    @Test
    public void shouldTestNamedClassTranslator() throws OWLException {
        NamedClassTranslator testSubject0 = new NamedClassTranslator(
                Utils.mockOWLRDFConsumer());
        OWLClass result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectAllValuesFromTranslator() throws OWLException {
        ObjectAllValuesFromTranslator testSubject0 = new ObjectAllValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectAllValuesFrom result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectCardinalityTranslator() throws OWLException {
        ObjectCardinalityTranslator testSubject0 = new ObjectCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectExactCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectComplementOfTranslator() throws OWLException {
        ObjectComplementOfTranslator testSubject0 = new ObjectComplementOfTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectComplementOf result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectHasSelfTranslator() throws OWLException {
        ObjectHasSelfTranslator testSubject0 = new ObjectHasSelfTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectHasSelf result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectHasValueTranslator() throws OWLException {
        ObjectHasValueTranslator testSubject0 = new ObjectHasValueTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectHasValue result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectMaxCardinalityTranslator() throws OWLException {
        ObjectMaxCardinalityTranslator testSubject0 = new ObjectMaxCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMaxCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectMaxQualifiedCardinalityTranslator() throws OWLException {
        ObjectMaxQualifiedCardinalityTranslator testSubject0 = new ObjectMaxQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMaxCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectMinCardinalityTranslator() throws OWLException {
        ObjectMinCardinalityTranslator testSubject0 = new ObjectMinCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMinCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectMinQualifiedCardinalityTranslator() throws OWLException {
        ObjectMinQualifiedCardinalityTranslator testSubject0 = new ObjectMinQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectMinCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    public void shouldTestObjectPropertyListItemTranslator() throws OWLException {
        ObjectPropertyListItemTranslator testSubject0 = new ObjectPropertyListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectPropertyExpression result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLObjectPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    @Ignore
    @Test
    public void shouldTestObjectQualifiedCardinalityTranslator() throws OWLException {
        ObjectQualifiedCardinalityTranslator testSubject0 = new ObjectQualifiedCardinalityTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectExactCardinality result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Ignore
    @Test
    public void shouldTestObjectSomeValuesFromTranslator() throws OWLException {
        ObjectSomeValuesFromTranslator testSubject0 = new ObjectSomeValuesFromTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectSomeValuesFrom result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLClassExpression result1 = testSubject0.translate(IRI("urn:aFake"));
        boolean result2 = testSubject0.matchesLax(IRI("urn:aFake"));
        boolean result3 = testSubject0.matchesStrict(IRI("urn:aFake"));
        boolean result4 = testSubject0.matches(IRI("urn:aFake"), Mode.LAX);
    }

    @Test
    public void shouldTestOptimisedListTranslator() throws OWLException {
        OptimisedListTranslator<OWLObject> testSubject0 = new OptimisedListTranslator<OWLObject>(
                Utils.mockOWLRDFConsumer(), mock(ListItemTranslator.class)) {};
        List<OWLObject> result0 = testSubject0.translateList(IRI("urn:aFake"));
        Set<OWLObject> result1 = testSubject0.translateToSet(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLFacetRestrictionListItemTranslator() throws OWLException {
        OWLFacetRestrictionListItemTranslator testSubject0 = new OWLFacetRestrictionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLFacetRestriction result0 = testSubject0.translate(mock(OWLLiteral.class));
        OWLFacetRestriction result1 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result2 = testSubject0.translate(IRI("urn:aFake"));
        OWLObject result3 = testSubject0.translate(mock(OWLLiteral.class));
    }

    public void shouldTestOWLObjectPropertyExpressionListItemTranslator()
            throws OWLException {
        OWLObjectPropertyExpressionListItemTranslator testSubject0 = new OWLObjectPropertyExpressionListItemTranslator(
                Utils.mockOWLRDFConsumer());
        OWLObjectPropertyExpression result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLObjectPropertyExpression result1 = testSubject0
                .translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestOWLRDFConsumer() throws OWLException, SAXException {
        OWLRDFConsumer testSubject0 = new OWLRDFConsumer(Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        testSubject0.startModel("");
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
    }

    @Test
    public void shouldTestOWLRDFXMLParserSAXException() throws OWLException {
        OWLRDFXMLParserSAXException testSubject0 = new OWLRDFXMLParserSAXException(
                mock(SAXException.class));
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestRDFXMLParserFactory() throws OWLException {
        RDFXMLParserFactory testSubject0 = new RDFXMLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }

    public void shouldTestSKOSClassTripleHandler() throws OWLException {
        SKOSClassTripleHandler testSubject0 = new SKOSClassTripleHandler(
                Utils.mockOWLRDFConsumer(), SKOSVocabulary.ALTLABEL);
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestSWRLRuleTranslator() throws OWLException {
        SWRLRuleTranslator testSubject0 = new SWRLRuleTranslator(
                Utils.mockOWLRDFConsumer());
        testSubject0.translateRule(IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPAllValuesFromHandler() throws OWLException {
        TPAllValuesFromHandler testSubject0 = new TPAllValuesFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPAnnotatedPropertyHandler() throws OWLException {
        TPAnnotatedPropertyHandler testSubject0 = new TPAnnotatedPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPAnnotatedSourceHandler() throws OWLException {
        TPAnnotatedSourceHandler testSubject0 = new TPAnnotatedSourceHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPAnnotatedTargetHandler() throws OWLException {
        TPAnnotatedTargetHandler testSubject0 = new TPAnnotatedTargetHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPComplementOfHandler() throws OWLException {
        TPComplementOfHandler testSubject0 = new TPComplementOfHandler(
                Utils.mockOWLRDFConsumer());
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPDatatypeComplementOfHandler() throws OWLException {
        TPDatatypeComplementOfHandler testSubject0 = new TPDatatypeComplementOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPDifferentFromHandler() throws OWLException {
        TPDifferentFromHandler testSubject0 = new TPDifferentFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPDisjointUnionHandler() throws OWLException {
        TPDisjointUnionHandler testSubject0 = new TPDisjointUnionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPDisjointWithHandler() throws OWLException {
        TPDisjointWithHandler testSubject0 = new TPDisjointWithHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPDistinctMembersHandler() throws OWLException {
        TPDistinctMembersHandler testSubject0 = new TPDistinctMembersHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPEquivalentClassHandler() throws OWLException {
        TPEquivalentClassHandler testSubject0 = new TPEquivalentClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPEquivalentPropertyHandler() throws OWLException {
        TPEquivalentPropertyHandler testSubject0 = new TPEquivalentPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPFirstLiteralHandler() throws OWLException {
        TPFirstLiteralHandler testSubject0 = new TPFirstLiteralHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                mock(OWLLiteral.class));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), mock(OWLLiteral.class));
    }

    @Test
    public void shouldTestTPFirstResourceHandler() throws OWLException {
        TPFirstResourceHandler testSubject0 = new TPFirstResourceHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPHasKeyHandler() throws OWLException {
        TPHasKeyHandler testSubject0 = new TPHasKeyHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPHasValueHandler() throws OWLException {
        TPHasValueHandler testSubject0 = new TPHasValueHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Ignore
    @Test
    public void shouldTestTPImportsHandler() throws OWLException {
        TPImportsHandler testSubject0 = new TPImportsHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPIntersectionOfHandler() throws OWLException {
        TPIntersectionOfHandler testSubject0 = new TPIntersectionOfHandler(
                Utils.mockOWLRDFConsumer());
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPInverseOfHandler() throws OWLException {
        TPInverseOfHandler testSubject0 = new TPInverseOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        testSubject0.setAxiomParsingMode(false);
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result2 = testSubject0.isAxiomParsingMode();
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPOnClassHandler() throws OWLException {
        TPOnClassHandler testSubject0 = new TPOnClassHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPOnDataRangeHandler() throws OWLException {
        TPOnDataRangeHandler testSubject0 = new TPOnDataRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPOneOfHandler() throws OWLException {
        TPOneOfHandler testSubject0 = new TPOneOfHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPOnPropertyHandler() throws OWLException {
        TPOnPropertyHandler testSubject0 = new TPOnPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPPropertyChainAxiomHandler() throws OWLException {
        TPPropertyChainAxiomHandler testSubject0 = new TPPropertyChainAxiomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPPropertyDisjointWithHandler() throws OWLException {
        TPPropertyDisjointWithHandler testSubject0 = new TPPropertyDisjointWithHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPPropertyDomainHandler() throws OWLException {
        TPPropertyDomainHandler testSubject0 = new TPPropertyDomainHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPPropertyRangeHandler() throws OWLException {
        TPPropertyRangeHandler testSubject0 = new TPPropertyRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPRestHandler() throws OWLException {
        TPRestHandler testSubject0 = new TPRestHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPSameAsHandler() throws OWLException {
        TPSameAsHandler testSubject0 = new TPSameAsHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPSomeValuesFromHandler() throws OWLException {
        TPSomeValuesFromHandler testSubject0 = new TPSomeValuesFromHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTPSubClassOfHandler() throws OWLException {
        TPSubClassOfHandler testSubject0 = new TPSubClassOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTPSubPropertyOfHandler() throws OWLException {
        TPSubPropertyOfHandler testSubject0 = new TPSubPropertyOfHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPTypeHandler() throws OWLException {
        TPTypeHandler testSubject0 = new TPTypeHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getPredicateIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    public void shouldTestTPUnionOfHandler() throws OWLException {
        TPUnionOfHandler testSubject0 = new TPUnionOfHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTPVersionIRIHandler() throws OWLException {
        TPVersionIRIHandler testSubject0 = new TPVersionIRIHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTranslatedOntologyChangeException() throws OWLException {
        TranslatedOntologyChangeException testSubject0 = new TranslatedOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadedImportException() throws OWLException {
        TranslatedUnloadedImportException testSubject0 = new TranslatedUnloadedImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceTriplePatternMatcher() throws OWLException {
        TriplePatternMatcher testSubject0 = mock(TriplePatternMatcher.class);
        boolean result0 = testSubject0.matches(Utils.mockOWLRDFConsumer(),
                IRI("urn:aFake"));
        OWLObject result1 = testSubject0.createObject(Utils.mockOWLRDFConsumer());
    }

    @Test
    public void shouldTestTriplePredicateHandler() throws OWLException {
        TriplePredicateHandler testSubject0 = new TriplePredicateHandler(
                Utils.mockOWLRDFConsumer(), IRI("urn:aFake")) {
            @Override
            public void handleTriple(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {}

            @Override
            public boolean canHandleStreaming(IRI subject, IRI predicate, IRI object)
                    throws UnloadableImportException {
                return false;
            }
        };
        IRI result0 = testSubject0.getPredicateIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
    }

    @Test
    public void shouldTestTypeAllDifferentHandler() throws OWLException {
        TypeAllDifferentHandler testSubject0 = new TypeAllDifferentHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getTypeIRI();
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeAllDisjointClassesHandler() throws OWLException {
        TypeAllDisjointClassesHandler testSubject0 = new TypeAllDisjointClassesHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result1 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result2 = testSubject0.getTypeIRI();
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeAnnotationHandler() throws OWLException {
        TypeAnnotationHandler testSubject0 = new TypeAnnotationHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeAnnotationPropertyHandler() throws OWLException {
        TypeAnnotationPropertyHandler testSubject0 = new TypeAnnotationPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeAsymmetricPropertyHandler() throws OWLException {
        TypeAsymmetricPropertyHandler testSubject0 = new TypeAsymmetricPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeAxiomHandler() throws OWLException {
        TypeAxiomHandler testSubject0 = new TypeAxiomHandler(Utils.mockOWLRDFConsumer());
        TypeAxiomHandler testSubject1 = new TypeAxiomHandler(Utils.mockOWLRDFConsumer(),
                IRI("urn:aFake"));
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeClassHandler() throws OWLException {
        TypeClassHandler testSubject0 = new TypeClassHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeDataPropertyHandler() throws OWLException {
        TypeDataPropertyHandler testSubject0 = new TypeDataPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeDataRangeHandler() throws OWLException {
        TypeDataRangeHandler testSubject0 = new TypeDataRangeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeDatatypeHandler() throws OWLException {
        TypeDatatypeHandler testSubject0 = new TypeDatatypeHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypedConstantListItemTranslator() throws OWLException {
        TypedConstantListItemTranslator testSubject0 = new TypedConstantListItemTranslator();
        OWLLiteral result0 = testSubject0.translate(IRI("urn:aFake"));
        OWLLiteral result1 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result2 = testSubject0.translate(mock(OWLLiteral.class));
        OWLObject result3 = testSubject0.translate(IRI("urn:aFake"));
    }

    public void shouldTestTypeDeprecatedClassHandler() throws OWLException {
        TypeDeprecatedClassHandler testSubject0 = new TypeDeprecatedClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeDeprecatedPropertyHandler() throws OWLException {
        TypeDeprecatedPropertyHandler testSubject0 = new TypeDeprecatedPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeFunctionalPropertyHandler() throws OWLException {
        TypeFunctionalPropertyHandler testSubject0 = new TypeFunctionalPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeInverseFunctionalPropertyHandler() throws OWLException {
        TypeInverseFunctionalPropertyHandler testSubject0 = new TypeInverseFunctionalPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeIrreflexivePropertyHandler() throws OWLException {
        TypeIrreflexivePropertyHandler testSubject0 = new TypeIrreflexivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeListHandler() throws OWLException {
        TypeListHandler testSubject0 = new TypeListHandler(Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeNamedIndividualHandler() throws OWLException {
        TypeNamedIndividualHandler testSubject0 = new TypeNamedIndividualHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeNegativeDataPropertyAssertionHandler() throws OWLException {
        TypeNegativeDataPropertyAssertionHandler testSubject0 = new TypeNegativeDataPropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeNegativePropertyAssertionHandler() throws OWLException {
        TypeNegativePropertyAssertionHandler testSubject0 = new TypeNegativePropertyAssertionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeObjectPropertyHandler() throws OWLException {
        TypeObjectPropertyHandler testSubject0 = new TypeObjectPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeOntologyHandler() throws OWLException {
        TypeOntologyHandler testSubject0 = new TypeOntologyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeOntologyPropertyHandler() throws OWLException {
        TypeOntologyPropertyHandler testSubject0 = new TypeOntologyPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypePropertyHandler() throws OWLException {
        TypePropertyHandler testSubject0 = new TypePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeRDFSClassHandler() throws OWLException {
        TypeRDFSClassHandler testSubject0 = new TypeRDFSClassHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeReflexivePropertyHandler() throws OWLException {
        TypeReflexivePropertyHandler testSubject0 = new TypeReflexivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeRestrictionHandler() throws OWLException {
        TypeRestrictionHandler testSubject0 = new TypeRestrictionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSelfRestrictionHandler() throws OWLException {
        TypeSelfRestrictionHandler testSubject0 = new TypeSelfRestrictionHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLAtomListHandler() throws OWLException {
        TypeSWRLAtomListHandler testSubject0 = new TypeSWRLAtomListHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLBuiltInAtomHandler() throws OWLException {
        TypeSWRLBuiltInAtomHandler testSubject0 = new TypeSWRLBuiltInAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLBuiltInHandler() throws OWLException {
        TypeSWRLBuiltInHandler testSubject0 = new TypeSWRLBuiltInHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLClassAtomHandler() throws OWLException {
        TypeSWRLClassAtomHandler testSubject0 = new TypeSWRLClassAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLDataRangeAtomHandler() throws OWLException {
        TypeSWRLDataRangeAtomHandler testSubject0 = new TypeSWRLDataRangeAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLDataValuedPropertyAtomHandler() throws OWLException {
        TypeSWRLDataValuedPropertyAtomHandler testSubject0 = new TypeSWRLDataValuedPropertyAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLDifferentIndividualsAtomHandler() throws OWLException {
        TypeSWRLDifferentIndividualsAtomHandler testSubject0 = new TypeSWRLDifferentIndividualsAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLImpHandler() throws OWLException {
        TypeSWRLImpHandler testSubject0 = new TypeSWRLImpHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLIndividualPropertyAtomHandler() throws OWLException {
        TypeSWRLIndividualPropertyAtomHandler testSubject0 = new TypeSWRLIndividualPropertyAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLSameIndividualAtomHandler() throws OWLException {
        TypeSWRLSameIndividualAtomHandler testSubject0 = new TypeSWRLSameIndividualAtomHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    @Test
    public void shouldTestTypeSWRLVariableHandler() throws OWLException {
        TypeSWRLVariableHandler testSubject0 = new TypeSWRLVariableHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result0 = testSubject0.getTypeIRI();
        boolean result1 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        boolean result2 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeSymmetricPropertyHandler() throws OWLException {
        TypeSymmetricPropertyHandler testSubject0 = new TypeSymmetricPropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }

    public void shouldTestTypeTransitivePropertyHandler() throws OWLException {
        TypeTransitivePropertyHandler testSubject0 = new TypeTransitivePropertyHandler(
                Utils.mockOWLRDFConsumer());
        testSubject0.handleTriple(IRI("urn:aFake"), IRI("urn:aFake"), IRI("urn:aFake"));
        boolean result0 = testSubject0.canHandleStreaming(IRI("urn:aFake"),
                IRI("urn:aFake"), IRI("urn:aFake"));
        IRI result1 = testSubject0.getTypeIRI();
        boolean result2 = testSubject0.canHandle(IRI("urn:aFake"), IRI("urn:aFake"),
                IRI("urn:aFake"));
        IRI result3 = testSubject0.getPredicateIRI();
    }
}
