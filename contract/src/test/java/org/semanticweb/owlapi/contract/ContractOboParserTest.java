package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.InputStream;
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
import org.coode.owlapi.obo.parser.JavaCharStream;
import org.coode.owlapi.obo.parser.Modifiers;
import org.coode.owlapi.obo.parser.NameTagValueHandler;
import org.coode.owlapi.obo.parser.OBOConsumer;
import org.coode.owlapi.obo.parser.OBOFrame;
import org.coode.owlapi.obo.parser.OBOIdTranslator;
import org.coode.owlapi.obo.parser.OBOIdType;
import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.coode.owlapi.obo.parser.OBOParser;
import org.coode.owlapi.obo.parser.OBOParserConstants;
import org.coode.owlapi.obo.parser.OBOParserException;
import org.coode.owlapi.obo.parser.OBOParserFactory;
import org.coode.owlapi.obo.parser.OBOParserHandler;
import org.coode.owlapi.obo.parser.OBOParserHandlerAdapter;
import org.coode.owlapi.obo.parser.OBOParserTokenManager;
import org.coode.owlapi.obo.parser.OBOPrefix;
import org.coode.owlapi.obo.parser.OBOTagValuePair;
import org.coode.owlapi.obo.parser.OBOVocabulary;
import org.coode.owlapi.obo.parser.OWLOBOParser;
import org.coode.owlapi.obo.parser.OntologyTagValueHandler;
import org.coode.owlapi.obo.parser.ParseException;
import org.coode.owlapi.obo.parser.PartOfTagValueHandler;
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
import org.coode.owlapi.obo.parser.UnionOfHandler;
import org.coode.owlapi.obo.parser.XRefTagHandler;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

@SuppressWarnings("unused")
public class ContractOboParserTest {
    public void shouldTestAbstractTagValueHandler() throws Exception {
        AbstractTagValueHandler testSubject0 = new AbstractTagValueHandler("",
                mock(OBOConsumer.class)) {
            @Override
            public void handle(final String currentId, final String value,
                    final String qualifierBlock, final String comment) {}
        };
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
        testSubject0.handle("", "", "", "");
    }

    public void shouldTestAltIdTagValueHandler() throws Exception {
        AltIdTagValueHandler testSubject0 = new AltIdTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestAsymmetricHandler() throws Exception {
        AsymmetricHandler testSubject0 = new AsymmetricHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestDataVersionTagValueHandler() throws Exception {
        DataVersionTagValueHandler testSubject0 = new DataVersionTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestDefaultNamespaceTagValueHandler() throws Exception {
        DefaultNamespaceTagValueHandler testSubject0 = new DefaultNamespaceTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestDefTagValueHandler() throws Exception {
        DefTagValueHandler testSubject0 = new DefTagValueHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestDisjointFromHandler() throws Exception {
        DisjointFromHandler testSubject0 = new DisjointFromHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestDomainHandler() throws Exception {
        DomainHandler testSubject0 = new DomainHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestIDSpaceManager() throws Exception {
        IDSpaceManager testSubject0 = new IDSpaceManager();
        IDSpaceManager testSubject1 = new IDSpaceManager(mock(IDSpaceManager.class));
        testSubject0.setIRIPrefix("", "");
        String result0 = testSubject0.getIRIPrefix("");
        String result1 = testSubject0.getDefaultIRIPrefix();
        String result2 = testSubject0.toString();
    }

    public void shouldTestIDSpaceTagValueHandler() throws Exception {
        IDSpaceTagValueHandler testSubject0 = new IDSpaceTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestIDTagValueHandler() throws Exception {
        IDTagValueHandler testSubject0 = new IDTagValueHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestIntersectionOfHandler() throws Exception {
        IntersectionOfHandler testSubject0 = new IntersectionOfHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestInverseHandler() throws Exception {
        InverseHandler testSubject0 = new InverseHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestIsATagValueHandler() throws Exception {
        IsATagValueHandler testSubject0 = new IsATagValueHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestIsObsoleteTagValueHandler() throws Exception {
        IsObsoleteTagValueHandler testSubject0 = new IsObsoleteTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestJavaCharStream() throws Exception {
        JavaCharStream testSubject0 = new JavaCharStream(mock(InputStream.class), "", 0,
                0, 0);
        JavaCharStream testSubject1 = new JavaCharStream(mock(InputStream.class), "", 0,
                0);
        JavaCharStream testSubject2 = new JavaCharStream(mock(Reader.class), 0, 0, 0);
        JavaCharStream testSubject3 = new JavaCharStream(mock(Reader.class), 0, 0);
        char result0 = testSubject0.readChar();
        testSubject0.backup(0);
        char result1 = testSubject0.BeginToken();
        int result2 = testSubject0.getColumn();
        int result3 = testSubject0.getLine();
        int result4 = testSubject0.getEndColumn();
        int result5 = testSubject0.getEndLine();
        int result6 = testSubject0.getBeginColumn();
        int result7 = testSubject0.getBeginLine();
        testSubject0.ReInit(mock(Reader.class), 0, 0, 0);
        testSubject0.ReInit(mock(InputStream.class), "", 0, 0, 0);
        testSubject0.ReInit(mock(InputStream.class), "", 0, 0);
        testSubject0.ReInit(mock(Reader.class), 0, 0);
        String result8 = testSubject0.GetImage();
        char[] result9 = testSubject0.GetSuffix(0);
        testSubject0.Done();
        testSubject0.adjustBeginLineColumn(0, 0);
        String result10 = testSubject0.toString();
    }

    public void shouldTestModifiers() throws Exception {
        Modifiers testSubject0 = new Modifiers();
        Modifiers result0 = Modifiers.parseModifiers("");
        testSubject0.addModifier("", "");
        Set<String> result1 = testSubject0.getModifierNames();
        Set<String> result2 = testSubject0.getModifierValues("");
        String result3 = testSubject0.toString();
    }

    public void shouldTestNameTagValueHandler() throws Exception {
        NameTagValueHandler testSubject0 = new NameTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestOBOConsumer() throws Exception {
        OBOConsumer testSubject0 = new OBOConsumer(Utils.getMockManager(),
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration(),
                IRI.create("urn:aFake"));
        OBOConsumer testSubject1 = new OBOConsumer(Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration(), IRI.create("urn:aFake"));
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        OWLClass result2 = testSubject0.getCurrentClass();
        IRI result3 = testSubject0.getIRIFromTagName("");
        IRI result4 = testSubject0.getIRIFromOBOId("");
        String result5 = testSubject0.getCurrentId();
        IRI result6 = testSubject0.getRelationIRIFromSymbolicIdOrOBOId("");
        testSubject0.addSymbolicIdMapping("", IRI.create("urn:aFake"));
        testSubject0.setDefaultNamespaceTagValue("");
        String result7 = testSubject0.getDefaultNamespaceTagValue();
        testSubject0.setOntologyTagValue("");
        testSubject0.setDataVersionTagValue("");
        testSubject0.registerIdSpace("", "");
        IDSpaceManager result8 = testSubject0.getIdSpaceManager();
        testSubject0.setCurrentId("");
        testSubject0.addUnionOfOperand(Utils.mockAnonClass());
        testSubject0.addIntersectionOfOperand(Utils.mockAnonClass());
        String result9 = testSubject0.getStanzaType();
        boolean result10 = testSubject0.isTerm();
        boolean result11 = testSubject0.isTypedef();
        boolean result12 = testSubject0.isInstanceType();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        String result13 = testSubject0.unescapeTagValue("");
        OWLEntity result14 = testSubject0.getCurrentEntity();
        OWLAnnotation result15 = testSubject0.parseXRef("");
        String result16 = testSubject0.toString();
    }

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

    public void shouldTestOBOIdTranslator() throws Exception {
        OBOIdTranslator testSubject0 = new OBOIdTranslator();
        IRI result0 = testSubject0.getIRIFromOBOId("");
        String result1 = testSubject0.getOBOIdFromIRI();
        String result2 = testSubject0.toString();
    }

    public void shouldTestOBOIdType() throws Exception {
        OBOIdType testSubject0 = OBOIdType.CANONICAL_PREFIXED_ID;
        OBOIdType[] result0 = OBOIdType.values();
        Pattern result2 = testSubject0.getPattern();
        IRI result3 = testSubject0.getIRIFromOBOId(new OWLOntologyID(),
                mock(IDSpaceManager.class), "");
        OBOIdType result4 = OBOIdType.getIdType("");
        String result5 = testSubject0.name();
        String result6 = testSubject0.toString();
        int result11 = testSubject0.ordinal();
    }

    public void shouldTestOBOOntologyFormat() throws Exception {
        OBOOntologyFormat testSubject0 = new OBOOntologyFormat();
        OBOOntologyFormat testSubject1 = new OBOOntologyFormat(mock(IDSpaceManager.class));
        String result0 = testSubject0.toString();
        IDSpaceManager result1 = testSubject0.getIdSpaceManager();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result2 = testSubject0
                .getParameter(mock(Object.class), mock(Object.class));
        boolean result3 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result4 = testSubject0.asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result5 = testSubject0.getOntologyLoaderMetaData();
        testSubject0.setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestOBOParser() throws Exception {
        OBOParser testSubject0 = new OBOParser(mock(Reader.class));
        OBOParser testSubject1 = new OBOParser(mock(OBOParserTokenManager.class));
        OBOParser testSubject2 = new OBOParser(mock(InputStream.class));
        OBOParser testSubject3 = new OBOParser(mock(InputStream.class), "");
        testSubject0.parse();
        String result0 = testSubject0.Comment();
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(OBOParserTokenManager.class));
        Token result1 = testSubject0.getNextToken();
        ParseException result2 = testSubject0.generateParseException();
        Token result3 = testSubject0.getToken(0);
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
        testSubject0.setHandler(mock(OBOParserHandler.class));
        testSubject0.Header();
        testSubject0.Stanza();
        testSubject0.TagValuePair();
        String result4 = testSubject0.toString();
    }

    public void shouldTestInterfaceOBOParserConstants() throws Exception {
        OBOParserConstants testSubject0 = mock(OBOParserConstants.class);
    }

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

    public void shouldTestOBOParserFactory() throws Exception {
        OBOParserFactory testSubject0 = new OBOParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    public void shouldTestInterfaceOBOParserHandler() throws Exception {
        OBOParserHandler testSubject0 = mock(OBOParserHandler.class);
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
    }

    public void shouldTestOBOParserHandlerAdapter() throws Exception {
        OBOParserHandlerAdapter testSubject0 = new OBOParserHandlerAdapter();
        testSubject0.startHeader();
        testSubject0.endHeader();
        testSubject0.startFrame("");
        testSubject0.endFrame();
        testSubject0.handleTagValue("", "", "", "");
        String result0 = testSubject0.toString();
    }

    public void shouldTestOBOPrefix() throws Exception {
        OBOPrefix testSubject0 = OBOPrefix.IAO;
        OBOPrefix[] result0 = OBOPrefix.values();
        String result2 = testSubject0.getPrefix();
        String result3 = testSubject0.name();
        String result4 = testSubject0.toString();
        int result9 = testSubject0.ordinal();
    }

    public void shouldTestOBOTagValuePair() throws Exception {
        OBOTagValuePair testSubject0 = new OBOTagValuePair("", "", "", "");
        String result0 = testSubject0.getValue();
        String result1 = testSubject0.getComment();
        String result2 = testSubject0.getTagName();
        String result3 = testSubject0.getQualifier();
        String result4 = testSubject0.toString();
    }

    public void shouldTestOBOVocabulary() throws Exception {
        OBOVocabulary testSubject0 = OBOVocabulary.ALT_ID;
        String result0 = testSubject0.toString();
        OBOVocabulary[] result1 = OBOVocabulary.values();
        String result3 = testSubject0.getName();
        IRI result4 = testSubject0.getIRI();
        boolean result5 = OBOVocabulary.isOBOIRI(IRI.create("urn:aFake"));
        List<OBOVocabulary> result6 = OBOVocabulary.getHeaderTags();
        List<OBOVocabulary> result7 = OBOVocabulary.getTermStanzaTags();
        List<OBOVocabulary> result8 = OBOVocabulary.getTypeDefStanzaTags();
        List<OBOVocabulary> result9 = OBOVocabulary.getInstanceStanzaTags();
        String result10 = testSubject0.name();
        int result15 = testSubject0.ordinal();
    }

    public void shouldTestOntologyTagValueHandler() throws Exception {
        OntologyTagValueHandler testSubject0 = new OntologyTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestOWLOBOParser() throws Exception {
        OWLOBOParser testSubject0 = new OWLOBOParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI.create("urn:aFake"),
                Utils.getMockOntology());
        testSubject0.setOWLOntologyManager(Utils.getMockManager());
        OWLOntologyManager result3 = testSubject0.getOWLOntologyManager();
        String result4 = testSubject0.toString();
    }

    public void shouldTestParseException() throws Exception {
        ParseException testSubject0 = new ParseException(mock(Token.class),
                mock(int[][].class), new String[0]);
        ParseException testSubject1 = new ParseException();
        ParseException testSubject2 = new ParseException("");
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestPartOfTagValueHandler() throws Exception {
        PartOfTagValueHandler testSubject0 = new PartOfTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

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

    public void shouldTestReflexiveHandler() throws Exception {
        ReflexiveHandler testSubject0 = new ReflexiveHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestRelationshipTagValueHandler() throws Exception {
        RelationshipTagValueHandler testSubject0 = new RelationshipTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestSymmetricTagValueHandler() throws Exception {
        SymmetricTagValueHandler testSubject0 = new SymmetricTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestSynonymScope() throws Exception {
        SynonymScope testSubject0 = SynonymScope.BROAD;
        SynonymScope[] result0 = SynonymScope.values();
        String result2 = testSubject0.name();
        String result3 = testSubject0.toString();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestSynonymTagValueHandler() throws Exception {
        SynonymTagValueHandler testSubject0 = new SynonymTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestSynonymTypeDefTagHandler() throws Exception {
        SynonymTypeDefTagHandler testSubject0 = new SynonymTypeDefTagHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestInterfaceTagValueHandler() throws Exception {
        TagValueHandler testSubject0 = mock(TagValueHandler.class);
        testSubject0.handle("", "", "", "");
        String result0 = testSubject0.getTagName();
    }

    public void shouldTestToken() throws Exception {
        Token testSubject0 = new Token();
        Token testSubject1 = new Token(0);
        Token testSubject2 = new Token(0, "");
        String result0 = testSubject0.toString();
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    public void shouldTestTransitiveOverHandler() throws Exception {
        TransitiveOverHandler testSubject0 = new TransitiveOverHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestTransitiveTagValueHandler() throws Exception {
        TransitiveTagValueHandler testSubject0 = new TransitiveTagValueHandler(
                mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestUnionOfHandler() throws Exception {
        UnionOfHandler testSubject0 = new UnionOfHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }

    public void shouldTestXRefTagHandler() throws Exception {
        XRefTagHandler testSubject0 = new XRefTagHandler(mock(OBOConsumer.class));
        testSubject0.handle("", "", "", "");
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.applyChange(mock(OWLOntologyChange.class));
        OWLDataFactory result2 = testSubject0.getDataFactory();
        OWLClass result3 = testSubject0.getCurrentClass();
        String result4 = testSubject0.getTagName();
        OBOConsumer result5 = testSubject0.getConsumer();
        IRI result6 = testSubject0.getTagIRI(mock(OBOVocabulary.class));
        IRI result7 = testSubject0.getTagIRI("");
        IRI result8 = testSubject0.getIRIFromOBOId("");
        OWLAnnotation result9 = testSubject0.getAnnotationForTagValuePair("", "");
        OWLClass result10 = testSubject0.getClassFromId("");
        String result11 = testSubject0.toString();
    }
}
