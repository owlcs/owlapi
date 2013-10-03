package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassFrameParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxFramesParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxInlineAxiomParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyHeader;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxParserFactory;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer.Token;
import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractManchesterowlsyntaxTest {
    @Test
    public void shouldTestManchesterOWLSyntax() throws OWLException {
        ManchesterOWLSyntax testSubject0 = ManchesterOWLSyntax.AND;

        ManchesterOWLSyntax[] result1 = ManchesterOWLSyntax.values();
        boolean result3 = testSubject0.isFrameKeyword();
        boolean result4 = testSubject0.isSectionKeyword();
        boolean result5 = testSubject0.isAxiomKeyword();
        boolean result6 = testSubject0.isClassExpressionConnectiveKeyword();
        boolean result7 = testSubject0.isClassExpressionQuantiferKeyword();
        String result8 = testSubject0.name();
        int result13 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxClassExpressionParser() throws OWLException {
        ManchesterOWLSyntaxClassExpressionParser testSubject0 = new ManchesterOWLSyntaxClassExpressionParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxClassFrameParser() throws OWLException {
        ManchesterOWLSyntaxClassFrameParser testSubject0 = new ManchesterOWLSyntaxClassFrameParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
    }

    @Ignore
    @Test
    public void shouldTestManchesterOWLSyntaxEditorParser() throws OWLException,
            ParserException {
        ManchesterOWLSyntaxEditorParser testSubject0 = new ManchesterOWLSyntaxEditorParser(
                new OWLOntologyLoaderConfiguration(), mock(OWLDataFactory.class), "");
        ManchesterOWLSyntaxEditorParser testSubject1 = new ManchesterOWLSyntaxEditorParser(
                mock(OWLDataFactory.class), "");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        OWLClassExpression result12 = testSubject0.parseClassExpression();
        Set<OntologyAxiomPair> result13 = testSubject0.parseClassFrameEOF();
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        Set<OntologyAxiomPair> result52 = testSubject0.parseFrames();
        testSubject0.setDefaultOntology(Utils.getMockOntology());
        Set<OWLOntology> mock = Utils.mockSet(Utils.getMockOntology());
        ManchesterOWLSyntaxOntologyFormat result105 = testSubject0.parseOntology(Utils
                .getMockOntology());
    }

    @Test
    public void shouldTestManchesterOWLSyntaxFramesParser() throws OWLException,
            ParserException {
        ManchesterOWLSyntaxFramesParser testSubject0 = new ManchesterOWLSyntaxFramesParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        Set<OntologyAxiomPair> result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        testSubject0.setDefaultOntology(Utils.getMockOntology());

    }

    @Test
    public void shouldTestManchesterOWLSyntaxInlineAxiomParser() throws OWLException {
        ManchesterOWLSyntaxInlineAxiomParser testSubject0 = new ManchesterOWLSyntaxInlineAxiomParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOntologyFormat() throws OWLException {
        ManchesterOWLSyntaxOntologyFormat testSubject0 = new ManchesterOWLSyntaxOntologyFormat();

        String result1 = testSubject0.getPrefix("");
        IRI result2 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clear();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result3 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result4 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result5 = testSubject0.containsPrefixMapping("");
        String result6 = testSubject0.getDefaultPrefix();
        String result7 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result8 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result9 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result10 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result11 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOntologyHeader() throws OWLException {
        Set<OWLAnnotation> mock1 = Utils.mockSet(mock(OWLAnnotation.class));
        Set<OWLImportsDeclaration> mock2 = Utils
                .mockSet(mock(OWLImportsDeclaration.class));
        ManchesterOWLSyntaxOntologyHeader testSubject0 = new ManchesterOWLSyntaxOntologyHeader(
                IRI("urn:aFake"), IRI("urn:aFake"), mock1, mock2);
        Collection<OWLAnnotation> result0 = testSubject0.getAnnotations();
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Collection<OWLImportsDeclaration> result2 = testSubject0.getImportsDeclarations();

    }

    public void shouldTestManchesterOWLSyntaxOntologyParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        ManchesterOWLSyntaxOntologyParser testSubject0 = new ManchesterOWLSyntaxOntologyParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());

    }

    @Test
    public void shouldTestManchesterOWLSyntaxParserFactory() throws OWLException {
        ManchesterOWLSyntaxParserFactory testSubject0 = new ManchesterOWLSyntaxParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());

    }

    @Test
    public void shouldTestManchesterOWLSyntaxTokenizer() throws OWLException {
        ManchesterOWLSyntaxTokenizer testSubject0 = new ManchesterOWLSyntaxTokenizer("");
        List<Token> result0 = testSubject0.tokenize();

    }

    @Test
    public void shouldTestOntologyAxiomPair() throws OWLException {
        OntologyAxiomPair testSubject0 = new OntologyAxiomPair(Utils.getMockOntology(),
                mock(OWLAxiom.class));

        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
    }
}
