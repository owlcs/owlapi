package org.semanticweb.owlapi.contract;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

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
import org.coode.owlapi.obo.parser.OBOParserConstants;
import org.coode.owlapi.obo.parser.OBOParserException;
import org.coode.owlapi.obo.parser.OBOParserFactory;
import org.coode.owlapi.obo.parser.OBOParserHandler;
import org.coode.owlapi.obo.parser.OBOParserHandlerAdapter;
import org.coode.owlapi.obo.parser.OBOPrefix;
import org.coode.owlapi.obo.parser.OBOTagValuePair;
import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.coode.owlapi.obo.parser.OntologyTagValueHandler;
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
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOboParserTest {
    private OBOConsumer mock;
    private OWLOntologyChange mockChange;
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
    public void shouldTestAbstractTagValueHandler() throws Exception {
        AbstractTagValueHandler testSubject0 = new AbstractTagValueHandler("", mock) {
            @Override
            public void handle(String currentId, String value, String qualifierBlock,
                    String comment) {}
        };
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
        testSubject0.handle("", "", "", "");
    }

    @Test
    public void shouldTestAltIdTagValueHandler() throws Exception {
        AltIdTagValueHandler testSubject0 = new AltIdTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestAsymmetricHandler() throws Exception {
        AsymmetricHandler testSubject0 = new AsymmetricHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestDataVersionTagValueHandler() throws Exception {
        DataVersionTagValueHandler testSubject0 = new DataVersionTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestDefaultNamespaceTagValueHandler() throws Exception {
        DefaultNamespaceTagValueHandler testSubject0 = new DefaultNamespaceTagValueHandler(
                mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestDefTagValueHandler() throws Exception {
        DefTagValueHandler testSubject0 = new DefTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestDisjointFromHandler() throws Exception {
        DisjointFromHandler testSubject0 = new DisjointFromHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestDomainHandler() throws Exception {
        DomainHandler testSubject0 = new DomainHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestIDSpaceManager() throws Exception {
        IDSpaceManager testSubject0 = new IDSpaceManager();
        IDSpaceManager testSubject1 = new IDSpaceManager(testSubject0);
        testSubject0.setIRIPrefix("", "");
        String result0 = testSubject0.getIRIPrefix("");
        String result1 = testSubject0.getDefaultIRIPrefix();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestIDSpaceTagValueHandler() throws Exception {
        IDSpaceTagValueHandler testSubject0 = new IDSpaceTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestIDTagValueHandler() throws Exception {
        IDTagValueHandler testSubject0 = new IDTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestIntersectionOfHandler() throws Exception {
        IntersectionOfHandler testSubject0 = new IntersectionOfHandler(mock);
        testSubject0.handle("", "test test", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestInverseHandler() throws Exception {
        InverseHandler testSubject0 = new InverseHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestIsATagValueHandler() throws Exception {
        IsATagValueHandler testSubject0 = new IsATagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestIsObsoleteTagValueHandler() throws Exception {
        IsObsoleteTagValueHandler testSubject0 = new IsObsoleteTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestModifiers() throws Exception {
        Modifiers testSubject0 = new Modifiers();
        Modifiers result0 = Modifiers.parseModifiers("");
        testSubject0.addModifier("", "");
        Set<String> result1 = testSubject0.getModifierNames();
        Set<String> result2 = testSubject0.getModifierValues("");
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestNameTagValueHandler() throws Exception {
        NameTagValueHandler testSubject0 = new NameTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOConsumer() throws Exception {
        OBOConsumer testSubject0 = new OBOConsumer(manager, mockonto,
                new OWLOntologyLoaderConfiguration(), IRI("urn:aFake"));
        testSubject0.setCurrentId("");
        OBOConsumer testSubject1 = new OBOConsumer(mockonto,
                new OWLOntologyLoaderConfiguration(), IRI("urn:aFake"));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        OWLClass result2 = testSubject0.getCurrentClass();
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
        String result16 = testSubject0.toString();
        testSubject0.addUnionOfOperand(Utils.mockAnonClass());
        testSubject0.addIntersectionOfOperand(Utils.mockAnonClass());
    }

    @Test
    public void shouldTestOBOFrame() throws Exception {
        OBOFrame testSubject0 = new OBOFrame(Utils.mockList(mock(OBOTagValuePair.class)));
        OBOFrame testSubject1 = new OBOFrame("",
                Utils.mockList(mock(OBOTagValuePair.class)));
        String result0 = testSubject0.getFrameType();
        List<OBOTagValuePair> result1 = testSubject0.getTagValuePairs();
        boolean result2 = testSubject0.isHeaderFrame();
        boolean result3 = testSubject0.isTypeDefFrame();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOIdTranslator() throws Exception {
        OBOIdTranslator testSubject0 = new OBOIdTranslator();
        IRI result0 = testSubject0.getIRIFromOBOId("");
        String result1 = testSubject0.getOBOIdFromIRI();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOIdType() throws Exception {
        OBOIdType testSubject0 = OBOIdType.CANONICAL_PREFIXED_ID;
        OBOIdType[] result0 = OBOIdType.values();
        Pattern result2 = testSubject0.getPattern();
        IRI result3 = testSubject0.getIRIFromOBOId(new OWLOntologyID(),
                mock(IDSpaceManager.class), "AZ:27");
        OBOIdType result4 = OBOIdType.getIdType("");
        String result5 = testSubject0.name();
        String result6 = testSubject0.toString();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOBOOntologyFormat() throws Exception {
        OBOOntologyFormat testSubject0 = new OBOOntologyFormat();
        String result0 = testSubject0.toString();
        IDSpaceManager result1 = testSubject0.getIdSpaceManager();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result2 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result3 = testSubject0.isPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result5 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }


    @Test
    public void shouldTestInterfaceOBOParserConstants() throws Exception {
        OBOParserConstants testSubject0 = mock(OBOParserConstants.class);
    }

    @Test
    public void shouldTestOBOParserException() throws Exception {
        OBOParserException testSubject0 = new OBOParserException("");
        OBOParserException testSubject1 = new OBOParserException("",
                new RuntimeException());
        OBOParserException testSubject2 = new OBOParserException(new RuntimeException());
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result6 = testSubject0.toString();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOBOParserFactory() throws Exception {
        OBOParserFactory testSubject0 = new OBOParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOBOParserHandler() throws Exception {
        OBOParserHandler testSubject0 = mock(OBOParserHandler.class);
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
    }

    @Test
    public void shouldTestOBOParserHandlerAdapter() throws Exception {
        OBOParserHandlerAdapter testSubject0 = new OBOParserHandlerAdapter();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOPrefix() throws Exception {
        OBOPrefix testSubject0 = OBOPrefix.IAO;
        OBOPrefix[] result0 = OBOPrefix.values();
        String result2 = testSubject0.getPrefix();
        String result3 = testSubject0.name();
        String result4 = testSubject0.toString();
        int result9 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOBOTagValuePair() throws Exception {
        OBOTagValuePair testSubject0 = new OBOTagValuePair("", "", "", "");
        String result0 = testSubject0.getValue();
        String result1 = testSubject0.getComment();
        String result2 = testSubject0.getTagName();
        String result3 = testSubject0.getQualifier();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestOBOVocabulary() throws Exception {
        OBOVocabulary testSubject0 = OBOVocabulary.ALT_ID;
        String result0 = testSubject0.toString();
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
    public void shouldTestOntologyTagValueHandler() throws Exception {
        OntologyTagValueHandler testSubject0 = new OntologyTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestRawFrameHandler() throws Exception {
        RawFrameHandler testSubject0 = new RawFrameHandler();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        List<OBOFrame> result0 = testSubject0.getTypeDefFrames();
        List<OBOFrame> result1 = testSubject0.getNonTypeDefFrames();
        OBOFrame result2 = testSubject0.getHeaderFrame();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestReflexiveHandler() throws Exception {
        ReflexiveHandler testSubject0 = new ReflexiveHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestRelationshipTagValueHandler() throws Exception {
        RelationshipTagValueHandler testSubject0 = new RelationshipTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestSymmetricTagValueHandler() throws Exception {
        SymmetricTagValueHandler testSubject0 = new SymmetricTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestSynonymScope() throws Exception {
        SynonymScope testSubject0 = SynonymScope.BROAD;
        SynonymScope[] result0 = SynonymScope.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSynonymTagValueHandler() throws Exception {
        SynonymTagValueHandler testSubject0 = new SynonymTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestSynonymTypeDefTagHandler() throws Exception {
        SynonymTypeDefTagHandler testSubject0 = new SynonymTypeDefTagHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceTagValueHandler() throws Exception {
        TagValueHandler testSubject0 = mock(TagValueHandler.class);
        testSubject0.handle("", "", "", "");
        String result0 = testSubject0.getTagName();
    }

    @Test
    public void shouldTestToken() throws Exception {
        Token testSubject0 = new Token();
        Token testSubject1 = new Token(0);
        Token testSubject2 = new Token(0, "");
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    @Test
    public void shouldTestTransitiveOverHandler() throws Exception {
        TransitiveOverHandler testSubject0 = new TransitiveOverHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestTransitiveTagValueHandler() throws Exception {
        TransitiveTagValueHandler testSubject0 = new TransitiveTagValueHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    @Test
    public void shouldTestXRefTagHandler() throws Exception {
        XRefTagHandler testSubject0 = new XRefTagHandler(mock);
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mockChange);
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(OBOVocabulary.IS_A);
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }
}
