package org.semanticweb.owlapi.contract;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.coode.owlapi.obo.parser.AbstractTagValueHandler;
import org.coode.owlapi.obo.parser.AltIdTagValueHandler;
import org.coode.owlapi.obo.parser.AsymmetricHandler;
import org.coode.owlapi.obo.parser.DataVersionTagValueHandler;
import org.coode.owlapi.obo.parser.DefTagValueHandler;
import org.coode.owlapi.obo.parser.DefaultNamespaceTagValueHandler;
import org.coode.owlapi.obo.parser.DisjointFromHandler;
import org.coode.owlapi.obo.parser.DomainHandler;
import org.coode.owlapi.obo.parser.IDSpaceManager;
import org.coode.owlapi.obo.parser.IDSpaceTagValueHandler;
import org.coode.owlapi.obo.parser.IDTagValueHandler;
import org.coode.owlapi.obo.parser.IntersectionOfHandler;
import org.coode.owlapi.obo.parser.InverseHandler;
import org.coode.owlapi.obo.parser.IsATagValueHandler;
import org.coode.owlapi.obo.parser.IsObsoleteTagValueHandler;
import org.coode.owlapi.obo.parser.Modifiers;
import org.coode.owlapi.obo.parser.NameTagValueHandler;
import org.coode.owlapi.obo.parser.OBOConsumer;
import org.coode.owlapi.obo.parser.OBOFrame;
import org.coode.owlapi.obo.parser.OBOIdTranslator;
import org.coode.owlapi.obo.parser.OBOIdType;
import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.coode.owlapi.obo.parser.OBOParser;
import org.coode.owlapi.obo.parser.OBOParserConstants;
import org.coode.owlapi.obo.parser.OBOParserFactory;
import org.coode.owlapi.obo.parser.OBOParserHandler;
import org.coode.owlapi.obo.parser.OBOParserHandlerAdapter;
import org.coode.owlapi.obo.parser.OBOParserTokenManager;
import org.coode.owlapi.obo.parser.OBOPrefix;
import org.coode.owlapi.obo.parser.OBOTagValuePair;
import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.coode.owlapi.obo.parser.OntologyTagValueHandler;
import org.coode.owlapi.obo.parser.ParseException;
import org.coode.owlapi.obo.parser.RawFrameHandler;
import org.coode.owlapi.obo.parser.ReflexiveHandler;
import org.coode.owlapi.obo.parser.RelationshipTagValueHandler;
import org.coode.owlapi.obo.parser.SymmetricTagValueHandler;
import org.coode.owlapi.obo.parser.SynonymScope;
import org.coode.owlapi.obo.parser.SynonymTagValueHandler;
import org.coode.owlapi.obo.parser.SynonymTypeDefTagHandler;
import org.coode.owlapi.obo.parser.TagValueHandler;
import org.coode.owlapi.obo.parser.Token;
import org.coode.owlapi.obo.parser.TransitiveOverHandler;
import org.coode.owlapi.obo.parser.TransitiveTagValueHandler;
import org.coode.owlapi.obo.parser.XRefTagHandler;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOboParserTest {
    private OBOConsumer mock;
    private AddAxiom mockChange;
    private OWLOntology mockonto;
    private OWLDataFactory df;
    private OWLOntologyManager manager;

    @Before
    public void setUp() throws OWLOntologyCreationException {
        manager = Factory.getManager();
        df = manager.getOWLDataFactory();
        mock = mock(OBOConsumer.class);
        when(mock.getOWLOntologyManager()).thenReturn(manager);
        when(mock.getCurrentId()).thenReturn("urn:test:test2");
        when(mock.getIRIFromOBOId(anyString())).thenReturn(IRI("urn:test:test3"));
        when(mock.getIRIFromTagName(anyString())).thenReturn(IRI("urn:test:test3"));
        when(mock.getCurrentEntity()).thenReturn(Class(IRI("urn:test:test3")));
        when(mock.parseXRef(anyString())).thenReturn(
                df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("fake")));
        mockonto = manager.createOntology();
        when(mock.getOntology()).thenReturn(mockonto);
        when(mock.getRelationIRIFromSymbolicIdOrOBOId(anyString())).thenReturn(
                IRI("urn:p:prop"));
        mockChange = new AddAxiom(mockonto,
                df.getOWLDeclarationAxiom(Class(IRI("urn:test#test"))));
    }

    @Test
    public void shouldTestAbstractTagValueHandler() throws OWLException {
        AbstractTagValueHandler testSubject0 = new AbstractTagValueHandler("", mock) {
            @Override
            public void handle(String currentId, String value, String qualifierBlock,
                    String comment) {}
        };
        String result4 = testSubject0.getTagName();
        testSubject0.handle("", "", "", "");
    }

    @Test
    public void shouldTestAltIdTagValueHandler() throws OWLException {
        AltIdTagValueHandler testSubject0 = new AltIdTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestAsymmetricHandler() throws OWLException {
        AsymmetricHandler testSubject0 = new AsymmetricHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestDataVersionTagValueHandler() throws OWLException {
        DataVersionTagValueHandler testSubject0 = new DataVersionTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestDefaultNamespaceTagValueHandler() throws OWLException {
        DefaultNamespaceTagValueHandler testSubject0 = new DefaultNamespaceTagValueHandler(
                mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestDefTagValueHandler() throws OWLException {
        DefTagValueHandler testSubject0 = new DefTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestDisjointFromHandler() throws OWLException {
        DisjointFromHandler testSubject0 = new DisjointFromHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestDomainHandler() throws OWLException {
        DomainHandler testSubject0 = new DomainHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestIDSpaceManager() throws OWLException {
        IDSpaceManager testSubject0 = new IDSpaceManager();
        IDSpaceManager testSubject1 = new IDSpaceManager(testSubject0);
        testSubject0.setIRIPrefix("", "");
        String result0 = testSubject0.getIRIPrefix("");
        String result1 = testSubject0.getDefaultIRIPrefix();
    }

    @Test
    public void shouldTestIDSpaceTagValueHandler() throws OWLException {
        IDSpaceTagValueHandler testSubject0 = new IDSpaceTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestIDTagValueHandler() throws OWLException {
        IDTagValueHandler testSubject0 = new IDTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestIntersectionOfHandler() throws OWLException {
        IntersectionOfHandler testSubject0 = new IntersectionOfHandler(mock);
        testSubject0.handle("", "test test", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestInverseHandler() throws OWLException {
        InverseHandler testSubject0 = new InverseHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestIsATagValueHandler() throws OWLException {
        IsATagValueHandler testSubject0 = new IsATagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestIsObsoleteTagValueHandler() throws OWLException {
        IsObsoleteTagValueHandler testSubject0 = new IsObsoleteTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestModifiers() throws OWLException {
        Modifiers testSubject0 = new Modifiers();
        Modifiers result0 = Modifiers.parseModifiers("");
        testSubject0.addModifier("", "");
        Set<String> result1 = testSubject0.getModifierNames();
        Set<String> result2 = testSubject0.getModifierValues("");
    }

    @Test
    public void shouldTestNameTagValueHandler() throws OWLException {
        NameTagValueHandler testSubject0 = new NameTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestOBOConsumer() throws OWLException {
        OBOConsumer testSubject0 = new OBOConsumer(mockonto,
                new OWLOntologyLoaderConfiguration(), IRI("urn:aFake"));
        testSubject0.setCurrentId("");
        OBOConsumer testSubject1 = new OBOConsumer(mockonto,
                new OWLOntologyLoaderConfiguration(), IRI("urn:aFake"));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        IRI result3 = testSubject0.getIRIFromTagName("");
        IRI result4 = testSubject0.getIRIFromOBOId("");
        String result5 = testSubject0.getCurrentId();
        IRI result6 = testSubject0.getRelationIRIFromSymbolicIdOrOBOId("");
        testSubject0.addSymbolicIdMapping("", IRI("urn:aFake"));
        testSubject0.setDefaultNamespaceTagValue("");
        String result7 = testSubject0.getDefaultNamespaceTagValue();
        testSubject0.setOntologyTagValue("");
        testSubject0.setDataVersionTagValue("");
        testSubject0.registerIdSpace("", "");
        IDSpaceManager result8 = testSubject0.getIdSpaceManager();
        String result9 = testSubject0.getStanzaType();
        boolean result10 = testSubject0.isTerm();
        boolean result11 = testSubject0.isTypedef();
        boolean result12 = testSubject0.isInstanceType();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.setCurrentId("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        String result13 = testSubject0.unescapeTagValue("");
        OWLEntity result14 = testSubject0.getCurrentEntity();
        OWLAnnotation result15 = testSubject0.parseXRef("");
        testSubject0.addUnionOfOperand(Utils.mockAnonClass());
        testSubject0.addIntersectionOfOperand(Utils.mockAnonClass());
    }

    @Test
    public void shouldTestOBOFrame() throws OWLException {
        OBOFrame testSubject0 = new OBOFrame(Utils.mockList(mock(OBOTagValuePair.class)));
        OBOFrame testSubject1 = new OBOFrame("",
                Utils.mockList(mock(OBOTagValuePair.class)));
        String result0 = testSubject0.getFrameType();
        List<OBOTagValuePair> result1 = testSubject0.getTagValuePairs();
        boolean result2 = testSubject0.isHeaderFrame();
        boolean result3 = testSubject0.isTypeDefFrame();
    }

    @Test
    public void shouldTestOBOIdTranslator() throws OWLException {
        OBOIdTranslator testSubject0 = new OBOIdTranslator();
        IRI result0 = testSubject0.getIRIFromOBOId("");
        String result1 = testSubject0.getOBOIdFromIRI();
    }

    @Test
    public void shouldTestOBOIdType() throws OWLException {
        OBOIdType testSubject0 = OBOIdType.CANONICAL_PREFIXED_ID;
        OBOIdType[] result0 = OBOIdType.values();
        Pattern result2 = testSubject0.getPattern();
        IRI result3 = testSubject0.getIRIFromOBOId(new OWLOntologyID(),
                mock(IDSpaceManager.class), "AZ:27");
        OBOIdType result4 = OBOIdType.getIdType("");
        String result5 = testSubject0.name();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOBOOntologyFormat() throws OWLException {
        OBOOntologyFormat testSubject0 = new OBOOntologyFormat();
        OBOOntologyFormat testSubject1 = new OBOOntologyFormat(mock(IDSpaceManager.class));
        IDSpaceManager result1 = testSubject0.getIdSpaceManager();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result2 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result3 = testSubject0.isPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result5 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestOBOParser() throws OWLException, ParseException {
        StringDocumentTarget target = new StringDocumentTarget();
        mockonto.getOWLOntologyManager().addAxiom(mockonto,
                df.getOWLDeclarationAxiom(Class(IRI("urn:c:test"))));
        manager.saveOntology(mockonto, new OBOOntologyFormat(), target);
        String string = target.toString();
        OBOParser testSubject0 = new OBOParser(new InputStreamReader(
                new ByteArrayInputStream(string.getBytes())));
        OBOParser testSubject1 = new OBOParser(mock(OBOParserTokenManager.class));
        OBOParser testSubject2 = new OBOParser(mock(InputStream.class));
        OBOParser testSubject3 = new OBOParser(mock(InputStream.class), "UTF-8");
        testSubject0.setHandler(mock(OBOParserHandler.class));
        testSubject0.parse();
        testSubject0.ReInit(mock(InputStream.class), "UTF-8");
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(OBOParserTokenManager.class));
        Token result1 = testSubject0.getNextToken();
        Token result3 = testSubject0.getToken(0);
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
    }

    @Test
    public void shouldTestInterfaceOBOParserConstants() throws OWLException {
        OBOParserConstants testSubject0 = mock(OBOParserConstants.class);
    }

    @Test
    public void shouldTestOBOParserFactory() throws OWLException {
        OBOParserFactory testSubject0 = new OBOParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }

    @Test
    public void shouldTestInterfaceOBOParserHandler() throws OWLException {
        OBOParserHandler testSubject0 = mock(OBOParserHandler.class);
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
    }

    @Test
    public void shouldTestOBOParserHandlerAdapter() throws OWLException {
        OBOParserHandlerAdapter testSubject0 = new OBOParserHandlerAdapter();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
    }

    @Test
    public void shouldTestOBOPrefix() throws OWLException {
        OBOPrefix testSubject0 = OBOPrefix.IAO;
        OBOPrefix[] result0 = OBOPrefix.values();
        String result2 = testSubject0.getPrefix();
        String result3 = testSubject0.name();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOBOTagValuePair() throws OWLException {
        OBOTagValuePair testSubject0 = new OBOTagValuePair("", "", "", "");
        String result0 = testSubject0.getValue();
        String result1 = testSubject0.getComment();
        String result2 = testSubject0.getTagName();
        String result3 = testSubject0.getQualifier();
    }

    @Test
    public void shouldTestOBOVocabulary() throws OWLException {
        OBOVocabulary testSubject0 = OBOVocabulary.ALT_ID;
        OBOVocabulary[] result1 = OBOVocabulary.values();
        String result3 = testSubject0.getName();
        IRI result4 = testSubject0.getIRI();
        boolean result5 = OBOVocabulary.isOBOIRI(IRI("urn:aFake"));
        List<OBOVocabulary> result6 = OBOVocabulary.getHeaderTags();
        List<OBOVocabulary> result7 = OBOVocabulary.getTermStanzaTags();
        List<OBOVocabulary> result8 = OBOVocabulary.getTypeDefStanzaTags();
        List<OBOVocabulary> result9 = OBOVocabulary.getInstanceStanzaTags();
        String result10 = testSubject0.name();
        int result15 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOntologyTagValueHandler() throws OWLException {
        OntologyTagValueHandler testSubject0 = new OntologyTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestRawFrameHandler() throws OWLException {
        RawFrameHandler testSubject0 = new RawFrameHandler();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        List<OBOFrame> result0 = testSubject0.getTypeDefFrames();
        List<OBOFrame> result1 = testSubject0.getNonTypeDefFrames();
        OBOFrame result2 = testSubject0.getHeaderFrame();
    }

    @Test
    public void shouldTestReflexiveHandler() throws OWLException {
        ReflexiveHandler testSubject0 = new ReflexiveHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestRelationshipTagValueHandler() throws OWLException {
        RelationshipTagValueHandler testSubject0 = new RelationshipTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestSymmetricTagValueHandler() throws OWLException {
        SymmetricTagValueHandler testSubject0 = new SymmetricTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestSynonymScope() throws OWLException {
        SynonymScope testSubject0 = SynonymScope.BROAD;
        SynonymScope[] result0 = SynonymScope.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSynonymTagValueHandler() throws OWLException {
        SynonymTagValueHandler testSubject0 = new SynonymTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestSynonymTypeDefTagHandler() throws OWLException {
        SynonymTypeDefTagHandler testSubject0 = new SynonymTypeDefTagHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestInterfaceTagValueHandler() throws OWLException {
        TagValueHandler testSubject0 = mock(TagValueHandler.class);
        testSubject0.handle("", "", "", "");
        String result0 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestToken() throws OWLException {
        Token testSubject0 = new Token();
        Token testSubject1 = new Token(0);
        Token testSubject2 = new Token(0, "");
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    @Test
    public void shouldTestTransitiveOverHandler() throws OWLException {
        TransitiveOverHandler testSubject0 = new TransitiveOverHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestTransitiveTagValueHandler() throws OWLException {
        TransitiveTagValueHandler testSubject0 = new TransitiveTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestXRefTagHandler() throws OWLException {
        XRefTagHandler testSubject0 = new XRefTagHandler(mock);
        testSubject0.handle("", "", "", "");
        String result4 = testSubject0.getTagName();
    }
}
