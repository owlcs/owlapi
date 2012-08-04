package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Set;

import org.coode.owlapi.rdfxml.parser.AnonymousNodeChecker;
import org.coode.owlapi.rdfxml.parser.IRIProvider;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.ConsoleTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.NullTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.OWLRDFConsumerAdapter;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParserFactory;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleParserConstants;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleParserException;

@SuppressWarnings("unused")
public class ContractTurtleParserTest {
    public void shouldTestConsoleTripleHandler() throws Exception {
        ConsoleTripleHandler testSubject0 = new ConsoleTripleHandler();
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                IRI.create("urn:aFake"));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestNullTripleHandler() throws Exception {
        NullTripleHandler testSubject0 = new NullTripleHandler();
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                IRI.create("urn:aFake"));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLRDFConsumerAdapter() throws Exception {
        OWLRDFConsumerAdapter testSubject0 = new OWLRDFConsumerAdapter(
                Utils.getMockManager(), Utils.getMockOntology(),
                mock(AnonymousNodeChecker.class), new OWLOntologyLoaderConfiguration());
        OWLRDFConsumerAdapter testSubject1 = new OWLRDFConsumerAdapter(
                Utils.getMockOntology(), mock(AnonymousNodeChecker.class),
                new OWLOntologyLoaderConfiguration());
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                IRI.create("urn:aFake"));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        testSubject0.handle(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.handle(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                mock(OWLLiteral.class));
        testSubject0.addFirst(IRI.create("urn:aFake"), IRI.create("urn:aFake"));
        testSubject0.addFirst(IRI.create("urn:aFake"), mock(OWLLiteral.class));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        Set<IRI> result1 = testSubject0.getOntologies();
        OWLOntology result2 = testSubject0.getOntology();
        testSubject0.addAxiom(IRI.create("urn:aFake"));
        RDFOntologyFormat result3 = testSubject0.getOntologyFormat();
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        OWLOntologyLoaderConfiguration result4 = testSubject0.getConfiguration();
        OWLDataFactory result5 = testSubject0.getDataFactory();
        testSubject0.addOntology(IRI.create("urn:aFake"));
        testSubject0.addTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                mock(OWLLiteral.class));
        testSubject0.addTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        Set<OWLAnnotation> result6 = testSubject0.translateAnnotations(IRI
                .create("urn:aFake"));
        IRI result7 = testSubject0.getResourceObject(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"), false);
        IRI result8 = testSubject0.getResourceObject(IRI.create("urn:aFake"),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result9 = testSubject0.getLiteralObject(IRI.create("urn:aFake"),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result10 = testSubject0.getLiteralObject(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"), false);
        boolean result11 = testSubject0.isRestriction(IRI.create("urn:aFake"));
        boolean result12 = testSubject0.isClassExpression(IRI.create("urn:aFake"));
        boolean result13 = testSubject0.isDataRange(IRI.create("urn:aFake"));
        boolean result14 = testSubject0.isParsedAllTriples();
        testSubject0.importsClosureChanged();
        testSubject0.setIRIProvider(mock(IRIProvider.class));
        testSubject0.setExpectedAxioms(0);
        Set<OWLAnnotation> result15 = testSubject0.getPendingAnnotations();
        testSubject0.setPendingAnnotations(Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAxiom result16 = testSubject0.getLastAddedAxiom();
        testSubject0.addClassExpression(IRI.create("urn:aFake"), false);
        testSubject0.addObjectProperty(IRI.create("urn:aFake"), false);
        testSubject0.addDataProperty(IRI.create("urn:aFake"), false);
        testSubject0.addDataRange(IRI.create("urn:aFake"), false);
        testSubject0.addAnnotatedSource(IRI.create("urn:aFake"), IRI.create("urn:aFake"));
        Set<IRI> result17 = testSubject0.getAnnotatedSourceAnnotationMainNodes(IRI
                .create("urn:aFake"));
        boolean result18 = testSubject0.isTriplePresent(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"), mock(OWLLiteral.class), false);
        boolean result19 = testSubject0.isTriplePresent(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"), IRI.create("urn:aFake"), false);
        OWLIndividual result20 = testSubject0
                .translateIndividual(IRI.create("urn:aFake"));
        OWLClassExpression result21 = testSubject0.translateClassExpression(IRI
                .create("urn:aFake"));
        testSubject0.startModel("");
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        IRI result22 = testSubject0.getSynonym(IRI.create("urn:aFake"));
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
        OWLDataRange result23 = testSubject0.translateDataRange(IRI.create("urn:aFake"));
        Set<OWLDataRange> result24 = testSubject0.translateToDataRangeSet(IRI
                .create("urn:aFake"));
        Set<OWLLiteral> result25 = testSubject0.translateToConstantSet(IRI
                .create("urn:aFake"));
        Set<OWLFacetRestriction> result26 = testSubject0
                .translateToFacetRestrictionSet(IRI.create("urn:aFake"));
        OWLDataPropertyExpression result27 = testSubject0
                .translateDataPropertyExpression(IRI.create("urn:aFake"));
        OWLObjectPropertyExpression result28 = testSubject0
                .translateObjectPropertyExpression(IRI.create("urn:aFake"));
        Set<IRI> result29 = testSubject0.getPredicatesBySubject(IRI.create("urn:aFake"));
        Set<IRI> result30 = testSubject0.getResourceObjects(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        Set<OWLLiteral> result31 = testSubject0.getLiteralObjects(
                IRI.create("urn:aFake"), IRI.create("urn:aFake"));
        OWLClassExpression result32 = testSubject0.getClassExpressionIfTranslated(IRI
                .create("urn:aFake"));
        List<OWLObjectPropertyExpression> result33 = testSubject0
                .translateToObjectPropertyList(IRI.create("urn:aFake"));
        List<OWLDataPropertyExpression> result34 = testSubject0
                .translateToDataPropertyList(IRI.create("urn:aFake"));
        Set<OWLClassExpression> result35 = testSubject0.translateToClassExpressionSet(IRI
                .create("urn:aFake"));
        Set<OWLIndividual> result36 = testSubject0.translateToIndividualSet(IRI
                .create("urn:aFake"));
        boolean result37 = testSubject0.hasPredicate(IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.addRest(IRI.create("urn:aFake"), IRI.create("urn:aFake"));
        IRI result38 = testSubject0.getFirstResource(IRI.create("urn:aFake"), false);
        OWLLiteral result39 = testSubject0.getFirstLiteral(IRI.create("urn:aFake"));
        IRI result40 = testSubject0.getRest(IRI.create("urn:aFake"), false);
        boolean result41 = testSubject0.isAxiom(IRI.create("urn:aFake"));
        String result42 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceTripleHandler() throws Exception {
        TripleHandler testSubject0 = mock(TripleHandler.class);
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                IRI.create("urn:aFake"));
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"), "",
                "");
        testSubject0.handleTriple(IRI.create("urn:aFake"), IRI.create("urn:aFake"),
                IRI.create("urn:aFake"));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
    }

    @Test
    public void shouldTestTurtleOntologyParserFactory() throws Exception {
        TurtleOntologyParserFactory testSubject0 = new TurtleOntologyParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceTurtleParserConstants() throws Exception {
        TurtleParserConstants testSubject0 = mock(TurtleParserConstants.class);
    }

    @Test
    public void shouldTestTurtleParserException() throws Exception {
        TurtleParserException testSubject0 = new TurtleParserException("");
        TurtleParserException testSubject1 = new TurtleParserException("",
                new RuntimeException());
        TurtleParserException testSubject2 = new TurtleParserException(
                new RuntimeException());
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }
}
