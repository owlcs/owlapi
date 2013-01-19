package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

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
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxParserException;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxParserFactory;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer.Token;
import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.junit.Test;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractManchesterowlsyntaxTest {
    @Test
    public void shouldTestManchesterOWLSyntax() throws Exception {
        ManchesterOWLSyntax testSubject0 = ManchesterOWLSyntax.AND;
        String result0 = testSubject0.toString();
        ManchesterOWLSyntax[] result1 = ManchesterOWLSyntax.values();
        boolean result3 = testSubject0.isFrameKeyword();
        boolean result4 = testSubject0.isSectionKeyword();
        boolean result5 = testSubject0.isAxiomKeyword();
        boolean result6 = testSubject0.isClassExpressionConnectiveKeyword();
        boolean result7 = testSubject0.isClassExpressionQuantiferKeyword();
        String result8 = testSubject0.name();
        int result13 = testSubject0.ordinal();
    }

    public void shouldTestManchesterOWLSyntaxClassExpressionParser() throws Exception {
        ManchesterOWLSyntaxClassExpressionParser testSubject0 = new ManchesterOWLSyntaxClassExpressionParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        OWLClassExpression result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        String result2 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxClassFrameParser() throws Exception {
        ManchesterOWLSyntaxClassFrameParser testSubject0 = new ManchesterOWLSyntaxClassFrameParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        Set<OntologyAxiomPair> result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        String result2 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxEditorParser() throws Exception {
        ManchesterOWLSyntaxEditorParser testSubject0 = new ManchesterOWLSyntaxEditorParser(
                new OWLOntologyLoaderConfiguration(), mock(OWLDataFactory.class), "");
        ManchesterOWLSyntaxEditorParser testSubject1 = new ManchesterOWLSyntaxEditorParser(
                mock(OWLDataFactory.class), "");
        Set<OntologyAxiomPair> result0 = testSubject0
                .parseAnnotations(mock(OWLAnnotationSubject.class));
        IRI result1 = testSubject0.getIRI("");
        OWLOntology result2 = testSubject0.getOntology("");
        int result3 = testSubject0.parseInteger();
        OWLClass result4 = testSubject0.getOWLClass("");
        OWLObjectProperty result5 = testSubject0.getOWLObjectProperty("");
        OWLDataProperty result6 = testSubject0.getOWLDataProperty("");
        OWLAnnotationProperty result7 = testSubject0.getOWLAnnotationProperty("");
        OWLDatatype result8 = testSubject0.getOWLDatatype("");
        OWLDataFactory result9 = testSubject0.getDataFactory();
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        OWLIndividual result10 = testSubject0.getOWLIndividual("");
        Token result11 = testSubject0.getToken();
        OWLClassExpression result12 = testSubject0.parseClassExpression();
        Set<OntologyAxiomPair> result13 = testSubject0.parseClassFrameEOF();
        boolean result14 = testSubject0.isAllowEmptyFrameSections();
        testSubject0.setAllowEmptyFrameSections(false);
        testSubject0.initialiseClassFrameSections();
        testSubject0.initialiseIndividualFrameSections();
        String result15 = testSubject0.getBase();
        testSubject0.setBase("");
        OWLEntityChecker result16 = testSubject0.getOWLEntityChecker();
        boolean result17 = testSubject0.isOntologyName("");
        boolean result18 = testSubject0.isClassName("");
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        boolean result19 = testSubject0.isObjectPropertyName("");
        boolean result20 = testSubject0.isAnnotationPropertyName("");
        boolean result21 = testSubject0.isDataPropertyName("");
        boolean result22 = testSubject0.isIndividualName("");
        boolean result23 = testSubject0.isDatatypeName("");
        boolean result24 = testSubject0.isSWRLBuiltin("");
        int result25 = testSubject0.getTokenPos();
        int result26 = testSubject0.getTokenCol();
        int result27 = testSubject0.getTokenRow();
        OWLClassExpression result28 = testSubject0.parseIntersection();
        OWLClassExpression result29 = testSubject0.parseUnion();
        OWLClassExpression result30 = testSubject0.parseNonNaryClassExpression();
        OWLObjectPropertyExpression result31 = testSubject0
                .parseObjectPropertyExpression(false);
        OWLObjectPropertyExpression result32 = testSubject0
                .parseObjectPropertyExpression();
        OWLPropertyExpression<?, ?> result33 = testSubject0.parsePropertyExpression();
        OWLDataProperty result34 = testSubject0.parseDataProperty();
        OWLClassExpression result35 = testSubject0.parseRestriction();
        OWLClassExpression result36 = testSubject0.parseObjectRestriction();
        OWLClassExpression result37 = testSubject0.parseDataRestriction();
        OWLClassExpression result38 = testSubject0.parseObjectOneOf();
        Set<OWLClassExpression> result39 = testSubject0.parseClassExpressionList("", "");
        Set<OWLClassExpression> result40 = testSubject0.parseClassExpressionList(false);
        OWLDataRange result41 = testSubject0.parseDataRange();
        OWLFacet result43 = testSubject0.parseFacet();
        OWLDatatype result44 = testSubject0.parseDatatype();
        OWLDataRange result45 = testSubject0.parseDataIntersectionOf();
        OWLDataRange result46 = testSubject0.parseDataUnionOf();
        Set<OWLDataRange> result47 = testSubject0.parseDataRangeList();
        OWLLiteral result48 = testSubject0.parseLiteral();
        String result49 = testSubject0.getLineCol();
        OWLIndividual result50 = testSubject0.parseIndividual();
        OWLIndividual result51 = testSubject0.parseIndividual(false);
        Set<OntologyAxiomPair> result52 = testSubject0.parseFrames();
        Set<OntologyAxiomPair> result53 = testSubject0.parseClassFrame();
        Set<OntologyAxiomPair> result54 = testSubject0.parseObjectPropertyFrame(false);
        Set<OntologyAxiomPair> result55 = testSubject0.parseObjectPropertyFrame();
        Set<OntologyAxiomPair> result56 = testSubject0.parseDataPropertyFrame();
        Set<OntologyAxiomPair> result57 = testSubject0.parseAnnotationPropertyFrame();
        Set<OntologyAxiomPair> result58 = testSubject0.parseIndividualFrame();
        Set<OntologyAxiomPair> result59 = testSubject0.parseDatatypeFrame();
        Set<OntologyAxiomPair> result60 = testSubject0.parseValuePartitionFrame();
        Set<OntologyAxiomPair> result61 = testSubject0.parseRuleFrame();
        Set<OntologyAxiomPair> result62 = testSubject0.parseNaryEquivalentClasses();
        Set<OntologyAxiomPair> result63 = testSubject0.parseNaryEquivalentProperties();
        Set<OWLPropertyExpression<?, ?>> result64 = testSubject0.parsePropertyList();
        OWLAnnotationProperty result65 = testSubject0.parseAnnotationProperty();
        IRI result66 = testSubject0.parseIRI();
        testSubject0.setDefaultOntology(Utils.getMockOntology());
        Set<OWLOntology> mock = Utils.mockSet(Utils.getMockOntology());
        Set<OntologyAxiomPair> result67 = testSubject0.parseValuePartitionValues(mock,
                mock(OWLClass.class));
        List<SWRLAtom> result68 = testSubject0.parseRuleAtoms();
        SWRLAtom result69 = testSubject0.parseRuleAtom();
        SWRLAtom result70 = testSubject0.parseClassAtom();
        SWRLAtom result71 = testSubject0.parseObjectPropertyAtom();
        SWRLAtom result72 = testSubject0.parseDataPropertyAtom();
        SWRLDifferentIndividualsAtom result73 = testSubject0.parseDifferentFromAtom();
        SWRLSameIndividualAtom result74 = testSubject0.parseSameAsAtom();
        SWRLBuiltInAtom result75 = testSubject0.parseBuiltInAtom();
        SWRLIArgument result76 = testSubject0.parseIObject();
        SWRLDArgument result77 = testSubject0.parseDObject();
        SWRLIndividualArgument result78 = testSubject0.parseIIndividualObject();
        SWRLVariable result79 = testSubject0.parseIVariable();
        IRI result80 = testSubject0.getVariableIRI("");
        IRI result81 = testSubject0.parseVariable();
        SWRLVariable result82 = testSubject0.parseDVariable();
        SWRLLiteralArgument result83 = testSubject0.parseLiteralObject();
        Set<OntologyAxiomPair> result84 = testSubject0.parseDisjointClasses();
        Set<OntologyAxiomPair> result85 = testSubject0.parseSameIndividual();
        Set<OWLIndividual> result86 = testSubject0.parseIndividualList();
        Set<OntologyAxiomPair> result87 = testSubject0.parseDisjointProperties();
        Set<OntologyAxiomPair> result88 = testSubject0.parseDifferentIndividuals();
        Set<OWLAxiom> result89 = testSubject0.parseObjectPropertyCharacteristicList(Utils
                .mockObjectProperty());
        Map<OWLClassExpression, Set<Set<OWLAnnotation>>> result90 = testSubject0
                .parseAnnotatedClassExpressionList();
        Set<OWLDataProperty> result91 = testSubject0.parseDataPropertyList();
        Map<OWLDataProperty, Set<OWLAnnotation>> result92 = testSubject0
                .parseAnnotatedDataPropertyList();
        Set<OWLAnnotationProperty> result93 = testSubject0.parseAnnotationPropertyList();
        Map<OWLPropertyExpression<?, ?>, Set<OWLAnnotation>> result94 = testSubject0
                .parseAnnotatedPropertyList();
        Set<OWLObjectPropertyExpression> result95 = testSubject0
                .parseObjectPropertyList();
        Map<OWLObjectPropertyExpression, Set<OWLAnnotation>> result96 = testSubject0
                .parseAnnotatedObjectPropertyList();
        List<OWLObjectPropertyExpression> result97 = testSubject0
                .parseObjectPropertyChain();
        OWLSubPropertyChainOfAxiom result98 = testSubject0
                .parsePropertyChainSubPropertyAxiom();
        OWLClassAxiom result99 = testSubject0.parseClassAxiom();
        OWLObjectPropertyAxiom result100 = testSubject0.parseObjectPropertyAxiom();
        Map<String, IRI> result101 = testSubject0.parsePrefixDeclaration();
        OWLImportsDeclaration result102 = testSubject0.parseImportsDeclaration(Utils
                .getMockOntology());
        Set<IRI> result103 = testSubject0.parseNameList();
        ManchesterOWLSyntaxOntologyFormat result105 = testSubject0.parseOntology(Utils
                .getMockOntology());
        ManchesterOWLSyntaxOntologyHeader result106 = testSubject0
                .parseOntologyHeader(false);
        OWLAxiom result107 = testSubject0.parseAxiom();
        OWLAxiom result108 = testSubject0.parseAxiomWithClassExpressionStart();
        OWLAxiom result109 = testSubject0.parseAxiomWithObjectPropertyStart();
        OWLAxiom result110 = testSubject0.parseAxiomWithDataPropertyStart();
        OWLAxiom result111 = testSubject0.parseFunctionPropertyAxiom();
        OWLAxiom result112 = testSubject0.parseInverseFunctionalPropertyAxiom();
        OWLAxiom result113 = testSubject0.parseSymmetricPropertyAxiom();
        OWLAxiom result114 = testSubject0.parseAsymmetricPropertyAxiom();
        OWLAxiom result115 = testSubject0.parseTransitivePropertyAxiom();
        OWLAxiom result116 = testSubject0.parseReflexivePropertyAxiom();
        OWLAxiom result117 = testSubject0.parseIrreflexivePropertyAxiom();
        OWLAxiom result118 = testSubject0.parseClassAxiomRemainder(Utils.mockAnonClass());
        OWLDataPropertyExpression result119 = testSubject0
                .parseDataPropertyExpression(false);
        OWLDataPropertyExpression result120 = testSubject0.parseDataPropertyExpression();
        String result121 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxFramesParser() throws Exception {
        ManchesterOWLSyntaxFramesParser testSubject0 = new ManchesterOWLSyntaxFramesParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        Set<OntologyAxiomPair> result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        testSubject0.setDefaultOntology(Utils.getMockOntology());
        String result2 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxInlineAxiomParser() throws Exception {
        ManchesterOWLSyntaxInlineAxiomParser testSubject0 = new ManchesterOWLSyntaxInlineAxiomParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        OWLAxiom result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOntologyFormat() throws Exception {
        ManchesterOWLSyntaxOntologyFormat testSubject0 = new ManchesterOWLSyntaxOntologyFormat();
        String result0 = testSubject0.toString();
        String result1 = testSubject0.getPrefix("");
        IRI result2 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
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
    public void shouldTestManchesterOWLSyntaxOntologyHeader() throws Exception {
        Set<OWLAnnotation> mock1 = Utils.mockSet(mock(OWLAnnotation.class));
        Set<OWLImportsDeclaration> mock2 = Utils
                .mockSet(mock(OWLImportsDeclaration.class));
        ManchesterOWLSyntaxOntologyHeader testSubject0 = new ManchesterOWLSyntaxOntologyHeader(
                IRI("urn:aFake"), IRI("urn:aFake"), mock1, mock2);
        Collection<OWLAnnotation> result0 = testSubject0.getAnnotations();
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Collection<OWLImportsDeclaration> result2 = testSubject0.getImportsDeclarations();
        String result3 = testSubject0.toString();
    }

    public void shouldTestManchesterOWLSyntaxOntologyParser() throws Exception {
        ManchesterOWLSyntaxOntologyParser testSubject0 = new ManchesterOWLSyntaxOntologyParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxParserException() throws Exception {
        ManchesterOWLSyntaxParserException testSubject0 = new ManchesterOWLSyntaxParserException(
                "", 0, 0);
        ManchesterOWLSyntaxParserException testSubject1 = new ManchesterOWLSyntaxParserException(
                "", new RuntimeException(), 0, 0);
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxParserFactory() throws Exception {
        ManchesterOWLSyntaxParserFactory testSubject0 = new ManchesterOWLSyntaxParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxTokenizer() throws Exception {
        ManchesterOWLSyntaxTokenizer testSubject0 = new ManchesterOWLSyntaxTokenizer("");
        List<Token> result0 = testSubject0.tokenize();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOntologyAxiomPair() throws Exception {
        OntologyAxiomPair testSubject0 = new OntologyAxiomPair(Utils.getMockOntology(),
                mock(OWLAxiom.class));
        String result0 = testSubject0.toString();
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
    }
}
