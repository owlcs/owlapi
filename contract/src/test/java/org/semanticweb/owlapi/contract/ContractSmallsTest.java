package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.owlxml.renderer.OWLXMLObjectRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorageException;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorer;
import org.coode.owlapi.owlxml.renderer.OWLXMLRenderer;
import org.coode.owlapi.owlxml.renderer.OWLXMLWriter;
import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.rdfxml.RDFXMLNamespaceManager;
import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdf.rdfxml.RDFXMLRenderer;
import org.coode.owlapi.rdf.renderer.RDFRendererBase;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyStorer;
import org.coode.owlapi.turtle.TurtleRenderer;
import org.coode.xml.XMLWriterNamespaceManager;
import org.junit.Test;
import org.semanticweb.owlapi.debugging.AbstractOWLDebugger;
import org.semanticweb.owlapi.debugging.BlackBoxOWLDebugger;
import org.semanticweb.owlapi.debugging.DebuggerClassExpressionGenerator;
import org.semanticweb.owlapi.debugging.JustificationMap;
import org.semanticweb.owlapi.debugging.OWLDebugger;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.modularity.OntologySegmenter;
import org.semanticweb.owlapi.normalform.NegationalNormalFormConverter;
import org.semanticweb.owlapi.normalform.NormalFormRewriter;
import org.semanticweb.owlapi.normalform.OWLObjectComplementOfExtractor;
import org.semanticweb.owlapi.rdf.syntax.RDFConsumer;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.semanticweb.owlapi.rdf.syntax.RDFParserException;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

import uk.ac.manchester.cs.bhig.util.MutableTree;
import uk.ac.manchester.cs.bhig.util.NodeRenderer;
import uk.ac.manchester.cs.bhig.util.Tree;
import uk.ac.manchester.cs.owlapi.modularity.ModuleType;
import uk.ac.manchester.cs.owlapi.modularity.SyntacticLocalityModuleExtractor;

import com.clarkparsia.owlapi.explanation.io.ConciseExplanationRenderer;
import com.clarkparsia.owlapi.explanation.io.ExplanationRenderer;
import com.clarkparsia.owlapi.explanation.io.SilentExplanationRenderer;
import com.clarkparsia.owlapi.explanation.util.DefinitionTracker;
import com.clarkparsia.owlapi.explanation.util.ExplanationProgressMonitor;
import com.clarkparsia.owlapi.explanation.util.OntologyUtils;
import com.clarkparsia.owlapi.explanation.util.SilentExplanationProgressMonitor;
import com.clarkparsia.owlapi.modularity.locality.LocalityClass;
import com.clarkparsia.owlapi.modularity.locality.LocalityEvaluator;
import com.clarkparsia.owlapi.modularity.locality.SemanticLocalityEvaluator;
import com.clarkparsia.owlapi.modularity.locality.SyntacticLocalityEvaluator;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractSmallsTest {

    @Test
    public void shouldTestInterfaceRDFConstants() throws Exception {
        RDFConstants testSubject0 = mock(RDFConstants.class);
    }

    @Test
    public void shouldTestEscapeUtils() throws Exception {
        EscapeUtils testSubject0 = new EscapeUtils();
        String result1 = EscapeUtils.escapeString("");
        String result2 = EscapeUtils.unescapeString("");
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOntologySegmenter() throws Exception {
        OntologySegmenter testSubject0 = mock(OntologySegmenter.class);
        Set<OWLAxiom> result0 = testSubject0.extract(Utils.mockSet(Utils
                .mockOWLEntity()));
        Set<OWLAxiom> result1 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner());
        OWLOntology result2 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"));
        OWLOntology result3 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"), 0, 0,
                Utils.structReasoner());
    }

    public void shouldTestConciseExplanationRenderer() throws Exception {
        ConciseExplanationRenderer testSubject0 = new ConciseExplanationRenderer();
        testSubject0.render(mock(OWLAxiom.class), Utils.mockSetSetAxiom());
        testSubject0.startRendering(mock(Writer.class));
        testSubject0.endRendering();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceExplanationRenderer() throws Exception {
        ExplanationRenderer testSubject0 = mock(ExplanationRenderer.class);
        testSubject0.render(mock(OWLAxiom.class), Utils.mockSetSetAxiom());
        testSubject0.startRendering(mock(Writer.class));
        testSubject0.endRendering();
    }

    @Test
    public void shouldTestSilentExplanationRenderer() throws Exception {
        SilentExplanationRenderer testSubject0 = new SilentExplanationRenderer();
        testSubject0.render(mock(OWLAxiom.class), Utils.mockSetSetAxiom());
        testSubject0.startRendering(mock(Writer.class));
        testSubject0.endRendering();
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestLocalityClass() throws Exception {
        LocalityClass testSubject0 = LocalityClass.BOTTOM_BOTTOM;
        LocalityClass testSubject1 = LocalityClass.TOP_BOTTOM;
        LocalityClass testSubject2 = LocalityClass.TOP_TOP;
    }

    @Test
    public void shouldTestInterfaceLocalityEvaluator() throws Exception {
        LocalityEvaluator testSubject0 = mock(LocalityEvaluator.class);
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
    }

    @Test
    public void shouldTestSemanticLocalityEvaluator() throws Exception {
        SemanticLocalityEvaluator testSubject0 = new SemanticLocalityEvaluator(
                Utils.getMockManager(), mock(OWLReasonerFactory.class));
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
        String result1 = testSubject0.toString();
    }

    public void shouldTestSyntacticLocalityEvaluator() throws Exception {
        SyntacticLocalityEvaluator testSubject0 = new SyntacticLocalityEvaluator(
                mock(LocalityClass.class));
        boolean result0 = testSubject0.isLocal(mock(OWLAxiom.class),
                Utils.mockSet(Utils.mockOWLEntity()));
        Set<LocalityClass> result1 = testSubject0.supportedLocalityClasses();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestModuleType() throws Exception {
        ModuleType testSubject0 = ModuleType.BOT;
        String result0 = testSubject0.toString();
        ModuleType[] result1 = ModuleType.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestSyntacticLocalityModuleExtractor() throws Exception {
        SyntacticLocalityModuleExtractor testSubject0 = new SyntacticLocalityModuleExtractor(
                Utils.getMockManager(), Utils.getMockOntology(),
                Utils.mockSet(mock(OWLAxiom.class)), mock(ModuleType.class));
        SyntacticLocalityModuleExtractor testSubject1 = new SyntacticLocalityModuleExtractor(
                Utils.getMockManager(), Utils.getMockOntology(),
                mock(ModuleType.class));
        Set<OWLAxiom> result0 = testSubject0.extract(Utils.mockSet(Utils
                .mockOWLEntity()));
        Set<OWLAxiom> result1 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner(), false);
        Set<OWLAxiom> result2 = testSubject0.extract(
                Utils.mockSet(Utils.mockOWLEntity()), 0, 0,
                Utils.structReasoner());
        OWLOntology result3 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"), 0, 0,
                Utils.structReasoner());
        OWLOntology result4 = testSubject0.extractAsOntology(
                Utils.mockSet(Utils.mockOWLEntity()), IRI("urn:aFake"));
        testSubject0.setModuleType(mock(ModuleType.class));
        ModuleType result5 = testSubject0.getModuleType();
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestDefinitionTracker() throws Exception {
        DefinitionTracker testSubject0 = new DefinitionTracker(
                Utils.getMockOntology());
        boolean result0 = testSubject0.isDefined(Utils.mockOWLEntity());
        boolean result1 = testSubject0.isDefined(Utils.mockAnonClass());
        testSubject0.ontologiesChanged(Utils
                .mockList(mock(OWLOntologyChange.class)));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceExplanationProgressMonitor()
            throws Exception {
        ExplanationProgressMonitor testSubject0 = mock(ExplanationProgressMonitor.class);
        boolean result0 = testSubject0.isCancelled();
        testSubject0.foundExplanation(Utils.mockSet(mock(OWLAxiom.class)));
        testSubject0.foundAllExplanations();
    }

    @Test
    public void shouldTestOntologyUtils() throws Exception {
        OntologyUtils testSubject0 = new OntologyUtils();
        OntologyUtils.addAxiom(mock(OWLAxiom.class),
                Utils.mockSet(Utils.getMockOntology()), Utils.getMockManager());
        Set<OWLOntology> result0 = OntologyUtils.removeAxiom(
                mock(OWLAxiom.class), Utils.mockSet(Utils.getMockOntology()),
                Utils.getMockManager());
        boolean result1 = OntologyUtils.containsUnreferencedEntity(
                Utils.getMockOntology(), Utils.mockAnonClass());
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestSilentExplanationProgressMonitor() throws Exception {
        SilentExplanationProgressMonitor testSubject0 = new SilentExplanationProgressMonitor();
        boolean result0 = testSubject0.isCancelled();
        testSubject0.foundExplanation(Utils.mockSet(mock(OWLAxiom.class)));
        testSubject0.foundAllExplanations();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFRendererBase() throws Exception {
        RDFRendererBase testSubject0 = new RDFRendererBase(
                Utils.getMockOntology()) {

            @Override
            protected void beginDocument() throws IOException {}

            @Override
            protected void endDocument() throws IOException {}

            @Override
            protected void writeIndividualComments(OWLNamedIndividual ind)
                    throws IOException {}

            @Override
            protected void writeClassComment(OWLClass cls) throws IOException {}

            @Override
            protected void writeDataPropertyComment(OWLDataProperty prop)
                    throws IOException {}

            @Override
            protected void writeObjectPropertyComment(OWLObjectProperty prop)
                    throws IOException {}

            @Override
            protected void writeDatatypeComment(OWLDatatype datatype)
                    throws IOException {}

            @Override
            protected void writeAnnotationPropertyComment(
                    OWLAnnotationProperty prop) throws IOException {}

            @Override
            protected void writeBanner(String name) throws IOException {}

            @Override
            public void render(RDFResourceNode node) throws IOException {}
        };
        OWLOntology result0 = testSubject0.getOntology();
        testSubject0.render(mock(RDFResourceNode.class));
        testSubject0.render();
        RDFGraph result1 = testSubject0.getGraph();
        testSubject0.renderAnonRoots();
        String result2 = testSubject0.toString();
    }

    public void shouldTestRDFXMLNamespaceManager() throws Exception {
        RDFXMLNamespaceManager testSubject0 = new RDFXMLNamespaceManager(
                Utils.getMockManager(), Utils.getMockOntology());
        RDFXMLNamespaceManager testSubject1 = new RDFXMLNamespaceManager(
                Utils.getMockOntology(), mock(OWLOntologyFormat.class));
        Set<OWLEntity> result0 = testSubject0.getEntitiesWithInvalidQNames();
        String result1 = testSubject0.getQName("");
        testSubject0.setPrefix("", "");
        String result2 = testSubject0.getDefaultPrefix();
        String result3 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result4 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result5 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
        Set<String> result6 = testSubject0.getPrefixes();
        Set<String> result7 = testSubject0.getNamespaces();
        Map<String, String> result8 = testSubject0.getPrefixNamespaceMap();
        String result9 = testSubject0.toString();
    }

    public void shouldTestRDFXMLOntologyStorer() throws Exception {
        RDFXMLOntologyStorer testSubject0 = new RDFXMLOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    public void shouldTestRDFXMLRenderer() throws Exception {
        RDFXMLRenderer testSubject0 = new RDFXMLRenderer(
                Utils.getMockOntology(), mock(Writer.class));
        RDFXMLRenderer testSubject1 = new RDFXMLRenderer(
                Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(mock(RDFResourceNode.class));
        Set<OWLEntity> result0 = testSubject0.getUnserialisableEntities();
        OWLOntology result1 = testSubject0.getOntology();
        testSubject0.render();
        RDFGraph result2 = testSubject0.getGraph();
        testSubject0.renderAnonRoots();
        String result3 = testSubject0.toString();
    }

    public void shouldTestMutableTree() throws Exception {
        MutableTree<Object> testSubject0 = new MutableTree<Object>(
                mock(Object.class));
        String result0 = testSubject0.toString();
        Tree<Object> result1 = testSubject0.getParent();
        int result2 = testSubject0.getSize();
        testSubject0.setParent(mock(MutableTree.class));
        Tree<Object> result3 = testSubject0.getRoot();
        boolean result4 = testSubject0.isRoot();
        List<Tree<Object>> result5 = testSubject0.getChildren();
        Object result6 = testSubject0.getUserObject();
        testSubject0.addChild(mock(MutableTree.class));
        testSubject0.addChild(mock(MutableTree.class), mock(Object.class));
        testSubject0.removeChild(mock(MutableTree.class));
        Object result7 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        testSubject0.clearChildren();
        int result8 = testSubject0.getChildCount();
        boolean result9 = testSubject0.isLeaf();
        List<Tree<Object>> result10 = testSubject0.getPathToRoot();
        List<Object> result11 = testSubject0.getUserObjectPathToRoot();
        Set<Object> result12 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<Object> result13 = testSubject0.fillDepthFirst();
        int result14 = testSubject0.getMaxDepth();
    }

    @Test
    public void shouldTestInterfaceNodeRenderer() throws Exception {
        NodeRenderer<Object> testSubject0 = mock(NodeRenderer.class);
        String result0 = testSubject0.render(mock(Tree.class));
    }

    @Test
    public void shouldTestInterfaceTree() throws Exception {
        Tree<Object> testSubject0 = mock(Tree.class);
        Tree<Object> result0 = testSubject0.getParent();
        Tree<Object> result1 = testSubject0.getRoot();
        boolean result2 = testSubject0.isRoot();
        List<Tree<Object>> result3 = testSubject0.getChildren();
        Object result4 = testSubject0.getUserObject();
        Object result5 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        int result6 = testSubject0.getChildCount();
        boolean result7 = testSubject0.isLeaf();
        List<Tree<Object>> result8 = testSubject0.getPathToRoot();
        List<Object> result9 = testSubject0.getUserObjectPathToRoot();
        Set<Object> result10 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<Object> result11 = testSubject0.fillDepthFirst();
    }

    @Test
    public void shouldTestNegationalNormalFormConverter() throws Exception {
        NegationalNormalFormConverter testSubject0 = new NegationalNormalFormConverter(
                mock(OWLDataFactory.class));
        boolean result0 = testSubject0.isInNormalForm(Utils.mockAnonClass());
        OWLClassExpression result1 = testSubject0.convertToNormalForm(Utils
                .mockAnonClass());
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceNormalFormRewriter() throws Exception {
        NormalFormRewriter testSubject0 = mock(NormalFormRewriter.class);
        boolean result0 = testSubject0.isInNormalForm(Utils.mockAnonClass());
        OWLClassExpression result1 = testSubject0.convertToNormalForm(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestOWLObjectComplementOfExtractor() throws Exception {
        OWLObjectComplementOfExtractor testSubject0 = new OWLObjectComplementOfExtractor();
        testSubject0.reset();
        Set<OWLClassExpression> result0 = testSubject0
                .getComplementedClassExpressions(Utils.mockAnonClass());
        String result1 = testSubject0.toString();
    }

    public void shouldTestTurtleOntologyFormat() throws Exception {
        TurtleOntologyFormat testSubject0 = new TurtleOntologyFormat();
        String result0 = testSubject0.toString();
        RDFParserMetaData result1 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result2 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result3 = testSubject0.isAddMissingTypes();
        boolean result4 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result5 = testSubject0.getPrefix("");
        IRI result6 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clearPrefixes();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result7 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result8 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result9 = testSubject0.containsPrefixMapping("");
        String result10 = testSubject0.getDefaultPrefix();
        String result11 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result12 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result13 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result14 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestTurtleOntologyStorer() throws Exception {
        TurtleOntologyStorer testSubject0 = new TurtleOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    public void shouldTestTurtleRenderer() throws Exception {
        TurtleRenderer testSubject0 = new TurtleRenderer(
                Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(mock(RDFResourceNode.class));
        OWLOntology result0 = testSubject0.getOntology();
        testSubject0.render();
        RDFGraph result1 = testSubject0.getGraph();
        testSubject0.renderAnonRoots();
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceRDFConsumer() throws Exception {
        RDFConsumer testSubject0 = mock(RDFConsumer.class);
        testSubject0.startModel("");
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
    }

    public void shouldTestRDFParser() throws Exception {
        RDFParser testSubject0 = new RDFParser();
        testSubject0.fatalError(mock(SAXParseException.class));
        testSubject0.parse(mock(InputSource.class), mock(RDFConsumer.class));
        testSubject0.error(mock(SAXParseException.class));
        testSubject0.warning(mock(SAXParseException.class));
        IRI result0 = testSubject0.getIRI("");
        testSubject0.startElement("", "", "", mock(Attributes.class));
        testSubject0.setDocumentLocator(mock(Locator.class));
        testSubject0.startDocument();
        testSubject0.endDocument();
        testSubject0.endElement("", "", "");
        testSubject0.characters(new char[5], 0, 0);
        testSubject0.processingInstruction("", "");
        testSubject0.setErrorHandler(mock(ErrorHandler.class));
        boolean result1 = NodeID.isAnonymousNodeIRI("");
        boolean result2 = NodeID.isAnonymousNodeID("");
        InputSource result3 = testSubject0.resolveEntity("", "");
        testSubject0.notationDecl("", "", "");
        testSubject0.unparsedEntityDecl("", "", "", "");
        testSubject0.startPrefixMapping("", "");
        testSubject0.endPrefixMapping("");
        testSubject0.ignorableWhitespace(new char[5], 0, 0);
        testSubject0.skippedEntity("");
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFParserException() throws Exception {
        RDFParserException testSubject0 = new RDFParserException("");
        RDFParserException testSubject1 = new RDFParserException("",
                mock(Locator.class));
        RDFParserException testSubject2 = new RDFParserException("", "", "", 0,
                0);
        int result0 = testSubject0.getLineNumber();
        int result1 = testSubject0.getColumnNumber();
        String result2 = testSubject0.getPublicId();
        String result3 = testSubject0.getSystemId();
        Throwable result4 = testSubject0.getCause();
        String result5 = testSubject0.toString();
        String result6 = testSubject0.getMessage();
        Exception result7 = testSubject0.getException();
        String result10 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLObjectRenderer() throws Exception {
        OWLXMLObjectRenderer testSubject0 = new OWLXMLObjectRenderer(
                mock(OWLXMLWriter.class));
        String result0 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLXMLOntologyStorageException() throws Exception {
        OWLXMLOntologyStorageException testSubject0 = new OWLXMLOntologyStorageException(
                "");
        OWLXMLOntologyStorageException testSubject1 = new OWLXMLOntologyStorageException(
                "", new RuntimeException());
        OWLXMLOntologyStorageException testSubject2 = new OWLXMLOntologyStorageException(
                new RuntimeException());
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.toString();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestOWLXMLOntologyStorer() throws Exception {
        OWLXMLOntologyStorer testSubject0 = new OWLXMLOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockManager(),
                Utils.getMockOntology(), mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestOWLXMLRenderer() throws Exception {
        OWLXMLRenderer testSubject0 = new OWLXMLRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class),
                mock(OWLOntologyFormat.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        String result0 = testSubject0.toString();
    }

    public void shouldTestOWLXMLWriter() throws Exception {
        OWLXMLWriter testSubject0 = new OWLXMLWriter(mock(Writer.class),
                Utils.getMockOntology());
        testSubject0.startDocument(Utils.getMockOntology());
        testSubject0.endDocument();
        testSubject0.writeStartElement(OWLXMLVocabulary.COMMENT);
        testSubject0.writeEndElement();
        testSubject0.writeTextContent("");
        testSubject0.writePrefix("", "");
        testSubject0.writeIRIElement(IRI("urn:aFake"));
        testSubject0.writeNodeIDAttribute(mock(NodeID.class));
        testSubject0.writeIRIAttribute(IRI("urn:aFake"));
        testSubject0.writeCardinalityAttribute(0);
        testSubject0.writeFacetAttribute(OWLFacet.MAX_INCLUSIVE);
        testSubject0.writeLangAttribute("");
        testSubject0.writeDatatypeAttribute(mock(OWLDatatype.class));
        Map<String, String> result0 = testSubject0.getIRIPrefixMap();
        XMLWriterNamespaceManager result1 = testSubject0.getNamespaceManager();
        String result2 = testSubject0
                .getIRIString(IRI.create("urn:test", null));
        testSubject0.writeAnnotationURIAttribute(mock(URI.class));
    }

    @Test
    public void shouldTestAbstractOWLDebugger() throws Exception {
        AbstractOWLDebugger testSubject0 = new AbstractOWLDebugger(
                Utils.getRealMockManager(), Utils.getMockOntology()) {

            @Override
            public Set<OWLAxiom> getSOSForIncosistentClass(
                    OWLClassExpression cls) throws OWLException {
                return Collections.emptySet();
            }

            @Override
            public void dispose() {}

            @Override
            protected OWLClassExpression getCurrentClass() throws OWLException {
                return null;
            }
        };
        OWLOntologyManager result0 = testSubject0.getOWLOntologyManager();
        OWLOntology result1 = testSubject0.getOWLOntology();
        Set<Set<OWLAxiom>> result2 = testSubject0
                .getAllSOSForIncosistentClass(Utils.mockAnonClass());
        testSubject0.constructHittingSetTree(
                Utils.mockSet(mock(OWLAxiom.class)), Utils.mockSetSetAxiom(),
                Utils.mockSetSetAxiom(), Utils.mockSet(mock(OWLAxiom.class)));
        String result3 = testSubject0.toString();
        testSubject0.dispose();
        Set<OWLAxiom> result4 = testSubject0.getSOSForIncosistentClass(Utils
                .mockAnonClass());
    }

    public void shouldTestBlackBoxOWLDebugger() throws Exception {
        BlackBoxOWLDebugger testSubject0 = new BlackBoxOWLDebugger(
                Utils.getRealMockManager(), Utils.getMockOntology(),
                mock(OWLReasonerFactory.class));
        testSubject0.dispose();
        Set<OWLAxiom> result0 = testSubject0.getSOSForIncosistentClass(Utils
                .mockAnonClass());
        OWLOntologyManager result1 = testSubject0.getOWLOntologyManager();
        OWLOntology result2 = testSubject0.getOWLOntology();
        Set<Set<OWLAxiom>> result3 = testSubject0
                .getAllSOSForIncosistentClass(Utils.mockAnonClass());
        testSubject0.constructHittingSetTree(
                Utils.mockSet(mock(OWLAxiom.class)), Utils.mockSetSetAxiom(),
                Utils.mockSetSetAxiom(), Utils.mockSet(mock(OWLAxiom.class)));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestDebuggerClassExpressionGenerator() throws Exception {
        DebuggerClassExpressionGenerator testSubject0 = new DebuggerClassExpressionGenerator(
                mock(OWLDataFactory.class));
        OWLClassExpression result0 = testSubject0.getDebuggerClassExpression();
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestJustificationMap() throws Exception {
        JustificationMap testSubject0 = new JustificationMap(
                Utils.mockAnonClass(), Utils.mockSet(mock(OWLAxiom.class)));
        Set<OWLAxiom> result0 = testSubject0.getRootAxioms();
        Set<OWLAxiom> result1 = testSubject0
                .getChildAxioms(mock(OWLAxiom.class));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLDebugger() throws Exception {
        OWLDebugger testSubject0 = mock(OWLDebugger.class);
        testSubject0.dispose();
        OWLOntology result0 = testSubject0.getOWLOntology();
        Set<Set<OWLAxiom>> result1 = testSubject0
                .getAllSOSForIncosistentClass(Utils.mockAnonClass());
        Set<OWLAxiom> result2 = testSubject0.getSOSForIncosistentClass(Utils
                .mockAnonClass());
    }
}
