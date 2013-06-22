package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.xml.sax.SAXException;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.ConsoleTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.NullTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.OWLRDFConsumerAdapter;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParserFactory;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractTurtleParserTest {
    private static final String URN_A_FAKE = "urn:aFake";

    public void shouldTestConsoleTripleHandler() throws OWLException {
        ConsoleTripleHandler testSubject0 = new ConsoleTripleHandler();
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestNullTripleHandler() throws OWLException {
        NullTripleHandler testSubject0 = new NullTripleHandler();
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLRDFConsumerAdapter() throws OWLException, SAXException {
        OWLRDFConsumerAdapter testSubject0 = new OWLRDFConsumerAdapter(
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        OWLRDFConsumerAdapter testSubject1 = new OWLRDFConsumerAdapter(
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        testSubject0.handle(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handle(IRI(URN_A_FAKE), IRI(URN_A_FAKE), mock(OWLLiteral.class));
        testSubject0.addFirst(IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.addFirst(IRI(URN_A_FAKE), mock(OWLLiteral.class));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        Set<IRI> result1 = testSubject0.getOntologies();
        OWLOntology result2 = testSubject0.getOntology();
        testSubject0.addAxiom(IRI(URN_A_FAKE));
        RDFOntologyFormat result3 = testSubject0.getOntologyFormat();
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        OWLOntologyLoaderConfiguration result4 = testSubject0.getConfiguration();
        OWLDataFactory result5 = testSubject0.getDataFactory();
        testSubject0.addOntology(IRI(URN_A_FAKE));
        testSubject0.addTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), mock(OWLLiteral.class));
        testSubject0.addTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        Set<OWLAnnotation> result6 = testSubject0.translateAnnotations(IRI(URN_A_FAKE));
        IRI result7 = testSubject0.getResourceObject(IRI(URN_A_FAKE), IRI(URN_A_FAKE),
                false);
        IRI result8 = testSubject0.getResourceObject(IRI(URN_A_FAKE),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result9 = testSubject0.getLiteralObject(IRI(URN_A_FAKE),
                OWLRDFVocabulary.OWL_ALL_DIFFERENT, false);
        OWLLiteral result10 = testSubject0.getLiteralObject(IRI(URN_A_FAKE),
                IRI(URN_A_FAKE), false);
        boolean result11 = testSubject0.isRestriction(IRI(URN_A_FAKE));
        boolean result12 = testSubject0.isClassExpression(IRI(URN_A_FAKE));
        boolean result13 = testSubject0.isDataRange(IRI(URN_A_FAKE));
        boolean result14 = testSubject0.isParsedAllTriples();
        testSubject0.importsClosureChanged();
        testSubject0.setIRIProvider(mock(IRIProvider.class));
        testSubject0.setExpectedAxioms(0);
        Set<OWLAnnotation> result15 = testSubject0.getPendingAnnotations();
        testSubject0.setPendingAnnotations(Utils.mockSet(mock(OWLAnnotation.class)));
        OWLAxiom result16 = testSubject0.getLastAddedAxiom();
        testSubject0.addClassExpression(IRI(URN_A_FAKE), false);
        testSubject0.addObjectProperty(IRI(URN_A_FAKE), false);
        testSubject0.addDataProperty(IRI(URN_A_FAKE), false);
        testSubject0.addDataRange(IRI(URN_A_FAKE), false);
        testSubject0.addAnnotatedSource(IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        Set<IRI> result17 = testSubject0
                .getAnnotatedSourceAnnotationMainNodes(IRI(URN_A_FAKE));
        boolean result18 = testSubject0.isTriplePresent(IRI(URN_A_FAKE), IRI(URN_A_FAKE),
                mock(OWLLiteral.class), false);
        boolean result19 = testSubject0.isTriplePresent(IRI(URN_A_FAKE), IRI(URN_A_FAKE),
                IRI(URN_A_FAKE), false);
        OWLIndividual result20 = testSubject0.translateIndividual(IRI(URN_A_FAKE));
        OWLClassExpression result21 = testSubject0
                .translateClassExpression(IRI(URN_A_FAKE));
        testSubject0.startModel("");
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        IRI result22 = testSubject0.getSynonym(IRI(URN_A_FAKE));
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
        OWLDataRange result23 = testSubject0.translateDataRange(IRI(URN_A_FAKE));
        Set<OWLDataRange> result24 = testSubject0
                .translateToDataRangeSet(IRI(URN_A_FAKE));
        Set<OWLLiteral> result25 = testSubject0.translateToConstantSet(IRI(URN_A_FAKE));
        Set<OWLFacetRestriction> result26 = testSubject0
                .translateToFacetRestrictionSet(IRI(URN_A_FAKE));
        OWLDataPropertyExpression result27 = testSubject0
                .translateDataPropertyExpression(IRI(URN_A_FAKE));
        OWLObjectPropertyExpression result28 = testSubject0
                .translateObjectPropertyExpression(IRI(URN_A_FAKE));
        Set<IRI> result29 = testSubject0.getPredicatesBySubject(IRI(URN_A_FAKE));
        Set<IRI> result30 = testSubject0.getResourceObjects(IRI(URN_A_FAKE),
                IRI(URN_A_FAKE));
        Set<OWLLiteral> result31 = testSubject0.getLiteralObjects(IRI(URN_A_FAKE),
                IRI(URN_A_FAKE));
        OWLClassExpression result32 = testSubject0
                .getClassExpressionIfTranslated(IRI(URN_A_FAKE));
        List<OWLObjectPropertyExpression> result33 = testSubject0
                .translateToObjectPropertyList(IRI(URN_A_FAKE));
        List<OWLDataPropertyExpression> result34 = testSubject0
                .translateToDataPropertyList(IRI(URN_A_FAKE));
        Set<OWLClassExpression> result35 = testSubject0
                .translateToClassExpressionSet(IRI(URN_A_FAKE));
        Set<OWLIndividual> result36 = testSubject0
                .translateToIndividualSet(IRI(URN_A_FAKE));
        boolean result37 = testSubject0.hasPredicate(IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.addRest(IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        IRI result38 = testSubject0.getFirstResource(IRI(URN_A_FAKE), false);
        OWLLiteral result39 = testSubject0.getFirstLiteral(IRI(URN_A_FAKE));
        IRI result40 = testSubject0.getRest(IRI(URN_A_FAKE), false);
        boolean result41 = testSubject0.isAxiom(IRI(URN_A_FAKE));
        String result42 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceTripleHandler() throws OWLException {
        TripleHandler testSubject0 = mock(TripleHandler.class);
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
    }

    @Test
    public void shouldTestTurtleOntologyParserFactory() throws OWLException {
        TurtleOntologyParserFactory testSubject0 = new TurtleOntologyParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }
}
