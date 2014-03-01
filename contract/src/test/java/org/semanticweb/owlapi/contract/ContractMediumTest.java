package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.coode.xml.IllegalElementNameException;
import org.coode.xml.OWLOntologyXMLNamespaceManager;
import org.coode.xml.XMLWriter;
import org.coode.xml.XMLWriterFactory;
import org.coode.xml.XMLWriterImpl;
import org.coode.xml.XMLWriterNamespaceManager;
import org.coode.xml.XMLWriterPreferences;
import org.junit.Test;
import org.semanticweb.owlapi.expression.OWLClassExpressionParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLExpressionParser;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner;
import org.semanticweb.owlapi.reasoner.knowledgeexploration.OWLKnowledgeExplorerReasoner.RootNode;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.Version;

import uk.ac.manchester.cs.bhig.util.MutableTree;
import uk.ac.manchester.cs.bhig.util.NodeRenderer;
import uk.ac.manchester.cs.bhig.util.Tree;
import uk.ac.manchester.cs.owl.explanation.ordering.AlphaExplanationOrderer;
import uk.ac.manchester.cs.owl.explanation.ordering.EntailedAxiomTree;
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationOrderer;
import uk.ac.manchester.cs.owl.explanation.ordering.ExplanationTree;
import uk.ac.manchester.cs.owl.explanation.ordering.NullExplanationOrderer;
import uk.ac.manchester.cs.owlapi.dlsyntax.parser.DLSyntaxParser;
import uk.ac.manchester.cs.owlapi.dlsyntax.parser.DLSyntaxParserConstants;
import uk.ac.manchester.cs.owlapi.dlsyntax.parser.DLSyntaxParserTokenManager;

@SuppressWarnings({ "unused", "javadoc", "unchecked" })
public class ContractMediumTest {

    @Test
    public void shouldTestInterfaceOWLClassExpressionParser() throws Exception {
        OWLClassExpressionParser testSubject0 = mock(OWLClassExpressionParser.class);
        Object result0 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestInterfaceOWLEntityChecker() throws Exception {
        OWLEntityChecker testSubject0 = mock(OWLEntityChecker.class);
        OWLClass result0 = testSubject0.getOWLClass("");
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty("");
        OWLDataProperty result2 = testSubject0.getOWLDataProperty("");
        OWLAnnotationProperty result3 = testSubject0
                .getOWLAnnotationProperty("");
        OWLDatatype result4 = testSubject0.getOWLDatatype("");
        OWLNamedIndividual result5 = testSubject0.getOWLIndividual("");
    }

    @Test
    public void shouldTestInterfaceOWLExpressionParser() throws Exception {
        OWLExpressionParser<OWLObject> testSubject0 = mock(OWLExpressionParser.class);
        Object result0 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestInterfaceOWLOntologyChecker() throws Exception {
        OWLOntologyChecker testSubject0 = mock(OWLOntologyChecker.class);
        OWLOntology result0 = testSubject0.getOntology("");
    }

    @Test
    public void shouldTestShortFormEntityChecker() throws Exception {
        ShortFormEntityChecker testSubject0 = new ShortFormEntityChecker(
                mock(BidirectionalShortFormProvider.class));
        OWLClass result0 = testSubject0.getOWLClass("");
        OWLObjectProperty result1 = testSubject0.getOWLObjectProperty("");
        OWLDataProperty result2 = testSubject0.getOWLDataProperty("");
        OWLAnnotationProperty result3 = testSubject0
                .getOWLAnnotationProperty("");
        OWLDatatype result4 = testSubject0.getOWLDatatype("");
        OWLNamedIndividual result5 = testSubject0.getOWLIndividual("");
        String result6 = testSubject0.toString();
    }

    @Test
    public void shouldTestAlphaExplanationOrderer() throws Exception {
        AlphaExplanationOrderer testSubject0 = new AlphaExplanationOrderer(
                mock(OWLObjectRenderer.class));
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
        String result1 = testSubject0.toString();
    }

    public void shouldTestEntailedAxiomTree() throws Exception {
        EntailedAxiomTree testSubject0 = new EntailedAxiomTree(
                mock(OWLAxiom.class));
        boolean result0 = testSubject0.isEntailed();
        String result1 = testSubject0.toString();
        Tree<OWLAxiom> result2 = testSubject0.getParent();
        int result3 = testSubject0.getSize();
        testSubject0.setParent(mock(MutableTree.class));
        Tree<OWLAxiom> result4 = testSubject0.getRoot();
        boolean result5 = testSubject0.isRoot();
        List<Tree<OWLAxiom>> result6 = testSubject0.getChildren();
        Object result7 = testSubject0.getUserObject();
        testSubject0.addChild(mock(MutableTree.class));
        testSubject0.addChild(mock(MutableTree.class), mock(Object.class));
        testSubject0.removeChild(mock(MutableTree.class));
        Object result8 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        testSubject0.clearChildren();
        int result9 = testSubject0.getChildCount();
        boolean result10 = testSubject0.isLeaf();
        List<Tree<OWLAxiom>> result11 = testSubject0.getPathToRoot();
        List<OWLAxiom> result12 = testSubject0.getUserObjectPathToRoot();
        Set<OWLAxiom> result13 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<OWLAxiom> result14 = testSubject0.fillDepthFirst();
        int result15 = testSubject0.getMaxDepth();
    }

    @Test
    public void shouldTestInterfaceExplanationOrderer() throws Exception {
        ExplanationOrderer testSubject0 = mock(ExplanationOrderer.class);
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
    }

    public void shouldTestExplanationTree() throws Exception {
        ExplanationTree testSubject0 = new ExplanationTree(mock(OWLAxiom.class));
        boolean result0 = testSubject0.isEntailed();
        String result1 = testSubject0.toString();
        Tree<OWLAxiom> result2 = testSubject0.getParent();
        int result3 = testSubject0.getSize();
        testSubject0.setParent(mock(MutableTree.class));
        Tree<OWLAxiom> result4 = testSubject0.getRoot();
        boolean result5 = testSubject0.isRoot();
        List<Tree<OWLAxiom>> result6 = testSubject0.getChildren();
        Object result7 = testSubject0.getUserObject();
        testSubject0.addChild(mock(MutableTree.class));
        testSubject0.addChild(mock(MutableTree.class), mock(Object.class));
        testSubject0.removeChild(mock(MutableTree.class));
        Object result8 = testSubject0.getEdge(mock(Tree.class));
        testSubject0.sortChildren(mock(Comparator.class));
        testSubject0.clearChildren();
        int result9 = testSubject0.getChildCount();
        boolean result10 = testSubject0.isLeaf();
        List<Tree<OWLAxiom>> result11 = testSubject0.getPathToRoot();
        List<OWLAxiom> result12 = testSubject0.getUserObjectPathToRoot();
        Set<OWLAxiom> result13 = testSubject0.getUserObjectClosure();
        testSubject0.dump(mock(PrintWriter.class), 0);
        testSubject0.dump(mock(PrintWriter.class));
        testSubject0.setNodeRenderer(mock(NodeRenderer.class));
        List<OWLAxiom> result14 = testSubject0.fillDepthFirst();
        int result15 = testSubject0.getMaxDepth();
    }

    @Test
    public void shouldTestNullExplanationOrderer() throws Exception {
        NullExplanationOrderer testSubject0 = new NullExplanationOrderer();
        ExplanationTree result0 = testSubject0.getOrderedExplanation(
                mock(OWLAxiom.class), Utils.mockSet(mock(OWLAxiom.class)));
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLKnowledgeExplorerReasoner()
            throws Exception {
        OWLKnowledgeExplorerReasoner testSubject0 = mock(OWLKnowledgeExplorerReasoner.class);
        RootNode result0 = testSubject0.getRoot(Utils.mockAnonClass());
        Node<? extends OWLObjectPropertyExpression> result1 = testSubject0
                .getObjectNeighbours(mock(RootNode.class), false);
        Collection<RootNode> result2 = testSubject0.getObjectNeighbours(
                mock(RootNode.class), mock(OWLObjectProperty.class));
        Node<OWLDataProperty> result3 = testSubject0.getDataNeighbours(
                mock(RootNode.class), false);
        Collection<RootNode> result4 = testSubject0.getDataNeighbours(
                mock(RootNode.class), mock(OWLDataProperty.class));
        Node<? extends OWLClassExpression> result5 = testSubject0
                .getObjectLabel(mock(RootNode.class), false);
        Node<? extends OWLDataRange> result6 = testSubject0.getDataLabel(
                mock(RootNode.class), false);
        testSubject0.interrupt();
        testSubject0.flush();
        testSubject0.dispose();
        NodeSet<OWLClass> result7 = testSubject0.getTypes(
                mock(OWLNamedIndividual.class), false);
        NodeSet<OWLNamedIndividual> result8 = testSubject0
                .getObjectPropertyValues(mock(OWLNamedIndividual.class),
                        Utils.mockObjectProperty());
        Set<OWLLiteral> result9 = testSubject0.getDataPropertyValues(
                mock(OWLNamedIndividual.class), mock(OWLDataProperty.class));
        Node<OWLNamedIndividual> result10 = testSubject0
                .getSameIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLNamedIndividual> result11 = testSubject0
                .getDifferentIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLClass> result12 = testSubject0.getSuperClasses(
                Utils.mockAnonClass(), false);
        NodeSet<OWLClass> result13 = testSubject0.getSubClasses(
                Utils.mockAnonClass(), false);
        Node<OWLClass> result14 = testSubject0.getEquivalentClasses(Utils
                .mockAnonClass());
        NodeSet<OWLClass> result15 = testSubject0.getDisjointClasses(Utils
                .mockAnonClass());
        long result16 = testSubject0.getTimeOut();
        FreshEntityPolicy result17 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result18 = testSubject0
                .getIndividualNodeSetPolicy();
        String result19 = testSubject0.getReasonerName();
        Version result20 = testSubject0.getReasonerVersion();
        BufferingMode result21 = testSubject0.getBufferingMode();
        List<OWLOntologyChange> result22 = testSubject0.getPendingChanges();
        Set<OWLAxiom> result23 = testSubject0.getPendingAxiomAdditions();
        Set<OWLAxiom> result24 = testSubject0.getPendingAxiomRemovals();
        OWLOntology result25 = testSubject0.getRootOntology();
        testSubject0.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        boolean result26 = testSubject0
                .isPrecomputed(InferenceType.CLASS_HIERARCHY);
        Set<InferenceType> result27 = testSubject0
                .getPrecomputableInferenceTypes();
        boolean result28 = testSubject0.isConsistent();
        boolean result29 = testSubject0.isSatisfiable(Utils.mockAnonClass());
        Node<OWLClass> result30 = testSubject0.getUnsatisfiableClasses();
        boolean result31 = testSubject0.isEntailed(mock(OWLAxiom.class));
        boolean result32 = testSubject0.isEntailed(Utils
                .mockSet(mock(OWLAxiom.class)));
        boolean result33 = testSubject0
                .isEntailmentCheckingSupported(mock(AxiomType.class));
        Node<OWLClass> result34 = testSubject0.getTopClassNode();
        Node<OWLClass> result35 = testSubject0.getBottomClassNode();
        Node<OWLObjectPropertyExpression> result36 = testSubject0
                .getTopObjectPropertyNode();
        Node<OWLObjectPropertyExpression> result37 = testSubject0
                .getBottomObjectPropertyNode();
        NodeSet<OWLObjectPropertyExpression> result38 = testSubject0
                .getSubObjectProperties(Utils.mockObjectProperty(), false);
        NodeSet<OWLObjectPropertyExpression> result39 = testSubject0
                .getSuperObjectProperties(Utils.mockObjectProperty(), false);
        Node<OWLObjectPropertyExpression> result40 = testSubject0
                .getEquivalentObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLObjectPropertyExpression> result41 = testSubject0
                .getDisjointObjectProperties(Utils.mockObjectProperty());
        Node<OWLObjectPropertyExpression> result42 = testSubject0
                .getInverseObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLClass> result43 = testSubject0.getObjectPropertyDomains(
                Utils.mockObjectProperty(), false);
        NodeSet<OWLClass> result44 = testSubject0.getObjectPropertyRanges(
                Utils.mockObjectProperty(), false);
        Node<OWLDataProperty> result45 = testSubject0.getTopDataPropertyNode();
        Node<OWLDataProperty> result46 = testSubject0
                .getBottomDataPropertyNode();
        NodeSet<OWLDataProperty> result47 = testSubject0.getSubDataProperties(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLDataProperty> result48 = testSubject0
                .getSuperDataProperties(mock(OWLDataProperty.class), false);
        Node<OWLDataProperty> result49 = testSubject0
                .getEquivalentDataProperties(mock(OWLDataProperty.class));
        NodeSet<OWLDataProperty> result50 = testSubject0
                .getDisjointDataProperties(mock(OWLDataPropertyExpression.class));
        NodeSet<OWLClass> result51 = testSubject0.getDataPropertyDomains(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLNamedIndividual> result52 = testSubject0.getInstances(
                Utils.mockAnonClass(), false);
    }

    @Test
    public void shouldTestIllegalElementNameException() throws Exception {
        IllegalElementNameException testSubject0 = new IllegalElementNameException(
                "");
        String result0 = testSubject0.getElementName();
        Throwable result2 = testSubject0.getCause();
        String result4 = testSubject0.toString();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    public void shouldTestOWLOntologyXMLNamespaceManager() throws Exception {
        OWLOntologyXMLNamespaceManager testSubject0 = new OWLOntologyXMLNamespaceManager(
                Utils.getMockManager(), Utils.getMockOntology());
        OWLOntologyXMLNamespaceManager testSubject1 = new OWLOntologyXMLNamespaceManager(
                Utils.getMockOntology(), mock(OWLOntologyFormat.class));
        String result0 = testSubject0.getQName("");
        testSubject0.setPrefix("", "");
        String result1 = testSubject0.getDefaultPrefix();
        String result2 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result3 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result4 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
        Set<String> result5 = testSubject0.getPrefixes();
        Set<String> result6 = testSubject0.getNamespaces();
        Map<String, String> result7 = testSubject0.getPrefixNamespaceMap();
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceXMLWriter() throws Exception {
        XMLWriter testSubject0 = mock(XMLWriter.class);
        testSubject0.setEncoding("");
        testSubject0.startDocument(IRI.create("test"));
        testSubject0.endDocument();
        XMLWriterNamespaceManager result0 = testSubject0.getNamespacePrefixes();
        String result1 = testSubject0.getXMLBase();
        testSubject0.setWrapAttributes(false);
        testSubject0.writeStartElement("");
        testSubject0.writeEndElement();
        testSubject0.writeAttribute("", "");
        testSubject0.writeTextContent("");
        testSubject0.writeComment("");
    }

    @Test
    public void shouldTestXMLWriterFactory() throws Exception {
        XMLWriterFactory testSubject0 = XMLWriterFactory.getInstance();
        XMLWriterFactory result0 = XMLWriterFactory.getInstance();
        XMLWriter result1 = testSubject0.createXMLWriter(mock(Writer.class),
                mock(XMLWriterNamespaceManager.class), "");
        String result2 = testSubject0.toString();
    }

    public void shouldTestXMLWriterImpl() throws Exception {
        XMLWriterImpl testSubject0 = new XMLWriterImpl(mock(Writer.class),
                mock(XMLWriterNamespaceManager.class), "");
        testSubject0.setEncoding("");
        testSubject0.startDocument("");
        testSubject0.endDocument();
        String result0 = testSubject0.getDefaultNamespace();
        XMLWriterNamespaceManager result1 = testSubject0.getNamespacePrefixes();
        String result2 = testSubject0.getXMLBase();
        testSubject0.setWrapAttributes(false);
        testSubject0.writeStartElement("");
        testSubject0.writeEndElement();
        testSubject0.writeAttribute("", "");
        testSubject0.writeTextContent("");
        testSubject0.writeComment("");
        URI result3 = testSubject0.getXMLBaseAsURI();
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestXMLWriterNamespaceManager() throws Exception {
        XMLWriterNamespaceManager testSubject0 = new XMLWriterNamespaceManager(
                "");
        testSubject0.setPrefix("", "");
        String result0 = testSubject0.getDefaultPrefix();
        String result1 = testSubject0.getQName("");
        String result2 = testSubject0.getDefaultNamespace();
        testSubject0.addWellKnownNamespace("", "");
        String result3 = testSubject0.getPrefixForNamespace("");
        testSubject0.setDefaultNamespace("");
        String result4 = testSubject0.getNamespaceForPrefix("");
        testSubject0.createPrefixForNamespace("");
        Set<String> result5 = testSubject0.getPrefixes();
        Set<String> result6 = testSubject0.getNamespaces();
        Map<String, String> result7 = testSubject0.getPrefixNamespaceMap();
        String result8 = testSubject0.toString();
    }

    @Test
    public void shouldTestXMLWriterPreferences() throws Exception {
        XMLWriterPreferences testSubject0 = XMLWriterPreferences.getInstance();
        XMLWriterPreferences result0 = XMLWriterPreferences.getInstance();
        boolean result1 = testSubject0.isUseNamespaceEntities();
        testSubject0.setUseNamespaceEntities(false);
        boolean result2 = testSubject0.isIndenting();
        testSubject0.setIndenting(false);
        int result3 = testSubject0.getIndentSize();
        testSubject0.setIndentSize(0);
        String result4 = testSubject0.toString();
    }

    public void shouldTestStructuralReasoner() throws Exception {
        StructuralReasoner testSubject0 = new StructuralReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class),
                BufferingMode.NON_BUFFERING);
        testSubject0.interrupt();
        NodeSet<OWLClass> result0 = testSubject0.getTypes(
                mock(OWLNamedIndividual.class), false);
        NodeSet<OWLNamedIndividual> result1 = testSubject0
                .getObjectPropertyValues(mock(OWLNamedIndividual.class),
                        Utils.mockObjectProperty());
        Set<OWLLiteral> result2 = testSubject0.getDataPropertyValues(
                mock(OWLNamedIndividual.class), mock(OWLDataProperty.class));
        Node<OWLNamedIndividual> result3 = testSubject0
                .getSameIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLNamedIndividual> result4 = testSubject0
                .getDifferentIndividuals(mock(OWLNamedIndividual.class));
        NodeSet<OWLClass> result5 = testSubject0.getSuperClasses(
                Utils.mockAnonClass(), false);
        NodeSet<OWLClass> result6 = testSubject0.getSubClasses(
                Utils.mockAnonClass(), false);
        Node<OWLClass> result7 = testSubject0.getEquivalentClasses(Utils
                .mockAnonClass());
        NodeSet<OWLClass> result8 = testSubject0.getDisjointClasses(Utils
                .mockAnonClass());
        FreshEntityPolicy result9 = testSubject0.getFreshEntityPolicy();
        IndividualNodeSetPolicy result10 = testSubject0
                .getIndividualNodeSetPolicy();
        String result11 = testSubject0.getReasonerName();
        Version result12 = testSubject0.getReasonerVersion();
        testSubject0.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        boolean result13 = testSubject0
                .isPrecomputed(InferenceType.CLASS_HIERARCHY);
        Set<InferenceType> result14 = testSubject0
                .getPrecomputableInferenceTypes();
        boolean result15 = testSubject0.isConsistent();
        boolean result16 = testSubject0.isSatisfiable(Utils.mockAnonClass());
        Node<OWLClass> result17 = testSubject0.getUnsatisfiableClasses();
        boolean result18 = testSubject0.isEntailed(Utils
                .mockSet(mock(OWLAxiom.class)));
        boolean result19 = testSubject0.isEntailed(mock(OWLAxiom.class));
        boolean result20 = testSubject0
                .isEntailmentCheckingSupported(mock(AxiomType.class));
        Node<OWLClass> result21 = testSubject0.getTopClassNode();
        Node<OWLClass> result22 = testSubject0.getBottomClassNode();
        Node<OWLObjectPropertyExpression> result23 = testSubject0
                .getTopObjectPropertyNode();
        Node<OWLObjectPropertyExpression> result24 = testSubject0
                .getBottomObjectPropertyNode();
        NodeSet<OWLObjectPropertyExpression> result25 = testSubject0
                .getSubObjectProperties(Utils.mockObjectProperty(), false);
        NodeSet<OWLObjectPropertyExpression> result26 = testSubject0
                .getSuperObjectProperties(Utils.mockObjectProperty(), false);
        Node<OWLObjectPropertyExpression> result27 = testSubject0
                .getEquivalentObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLObjectPropertyExpression> result28 = testSubject0
                .getDisjointObjectProperties(Utils.mockObjectProperty());
        Node<OWLObjectPropertyExpression> result29 = testSubject0
                .getInverseObjectProperties(Utils.mockObjectProperty());
        NodeSet<OWLClass> result30 = testSubject0.getObjectPropertyDomains(
                Utils.mockObjectProperty(), false);
        NodeSet<OWLClass> result31 = testSubject0.getObjectPropertyRanges(
                Utils.mockObjectProperty(), false);
        Node<OWLDataProperty> result32 = testSubject0.getTopDataPropertyNode();
        Node<OWLDataProperty> result33 = testSubject0
                .getBottomDataPropertyNode();
        NodeSet<OWLDataProperty> result34 = testSubject0.getSubDataProperties(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLDataProperty> result35 = testSubject0
                .getSuperDataProperties(mock(OWLDataProperty.class), false);
        Node<OWLDataProperty> result36 = testSubject0
                .getEquivalentDataProperties(mock(OWLDataProperty.class));
        NodeSet<OWLDataProperty> result37 = testSubject0
                .getDisjointDataProperties(mock(OWLDataPropertyExpression.class));
        NodeSet<OWLClass> result38 = testSubject0.getDataPropertyDomains(
                mock(OWLDataProperty.class), false);
        NodeSet<OWLNamedIndividual> result39 = testSubject0.getInstances(
                Utils.mockAnonClass(), false);
        testSubject0.prepareReasoner();
        testSubject0.dumpClassHierarchy(false);
        testSubject0.dumpObjectPropertyHierarchy(false);
        testSubject0.dumpDataPropertyHierarchy(false);
        testSubject0.flush();
        testSubject0.dispose();
        OWLDataFactory result40 = testSubject0.getOWLDataFactory();
        long result41 = testSubject0.getTimeOut();
        BufferingMode result42 = testSubject0.getBufferingMode();
        List<OWLOntologyChange> result43 = testSubject0.getPendingChanges();
        Set<OWLAxiom> result44 = testSubject0.getPendingAxiomAdditions();
        Set<OWLAxiom> result45 = testSubject0.getPendingAxiomRemovals();
        OWLOntology result46 = testSubject0.getRootOntology();
        OWLReasonerConfiguration result47 = testSubject0
                .getReasonerConfiguration();
        Collection<OWLAxiom> result48 = testSubject0.getReasonerAxioms();
        String result49 = testSubject0.toString();
    }

    @Test
    public void shouldTestStructuralReasonerFactory() throws Exception {
        StructuralReasonerFactory testSubject0 = new StructuralReasonerFactory();
        String result0 = testSubject0.getReasonerName();
        OWLReasoner result1 = testSubject0.createNonBufferingReasoner(Utils
                .getMockOntology());
        OWLReasoner result2 = testSubject0.createNonBufferingReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
        OWLReasoner result3 = testSubject0.createReasoner(Utils
                .getMockOntology());
        OWLReasoner result4 = testSubject0.createReasoner(
                Utils.getMockOntology(), mock(OWLReasonerConfiguration.class));
        String result5 = testSubject0.toString();
    }

    public void shouldTestDLSyntaxParser() throws Exception {
        DLSyntaxParser testSubject0 = new DLSyntaxParser(
                mock(InputStream.class));
        DLSyntaxParser testSubject1 = new DLSyntaxParser(
                mock(DLSyntaxParserTokenManager.class));
        DLSyntaxParser testSubject2 = new DLSyntaxParser(mock(Reader.class));
        DLSyntaxParser testSubject3 = new DLSyntaxParser(
                mock(InputStream.class), "UTF-8");
        IRI result0 = testSubject0.getIRI("");
        testSubject0.setDefaultNamespace("");
        testSubject0.ReInit(mock(InputStream.class), "UTF-8");
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(DLSyntaxParserTokenManager.class));
        OWLClassExpression result1 = testSubject0.And();
        OWLClassExpression result2 = testSubject0.Or();
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
        OWLClassExpression result6 = testSubject0.parseRestriction();
        OWLClassExpression result7 = testSubject0.parseObjectOneOf();
        OWLDataRange result8 = testSubject0.parseDataOneOf();
        OWLLiteral result9 = testSubject0.parseLiteral();
        OWLAxiom result10 = testSubject0.parseSameIndividual();
        OWLAxiom result11 = testSubject0.parseClassAxiom();
        OWLAxiom result12 = testSubject0.parseAxiom();
        testSubject0.setOWLDataFactory(mock(OWLDataFactory.class));
        testSubject0.setPrefixMapping("", "");
        IRI result13 = testSubject0.getIRIFromId("");
        OWLClassExpression result14 = testSubject0.parseDescription();
        OWLClassExpression result15 = testSubject0.parseClassDescription();
        Set<OWLAxiom> result16 = testSubject0.parseAxioms();
        OWLAxiom result17 = testSubject0.parsePropertyAxiom();
        OWLAxiom result18 = testSubject0.parseIndividualAxiom();
        OWLAxiom result19 = testSubject0.parseObjectPropertyAssertion();
        OWLAxiom result20 = testSubject0.parseDataPropertyAssertion();
        OWLAxiom result21 = testSubject0.parseDifferentIndividualsAxiom();
        OWLAxiom result22 = testSubject0.parseClassAssertion();
        OWLIndividual result23 = testSubject0.parseIndividualId();
        OWLObjectPropertyExpression result24 = testSubject0
                .parseObjectPropertyId();
        OWLDataPropertyExpression result25 = testSubject0.parseDataPropertyId();
        OWLAxiom result26 = testSubject0.parsePropertyChain();
        OWLClassExpression result27 = testSubject0.NonNaryBooleanDescription();
        OWLClassExpression result28 = testSubject0.parseObjectComplementOf();
        OWLClassExpression result29 = testSubject0
                .NamedClassOrNestedDescription();
        IRI result30 = testSubject0.parseId();
        OWLClassExpression result31 = testSubject0.parseSomeRestriction();
        OWLClassExpression result32 = testSubject0.parseDataSomeRestriction();
        OWLClassExpression result33 = testSubject0.parseAllRestriction();
        OWLClassExpression result34 = testSubject0
                .parseCardinalityRestriction();
        OWLClass result35 = testSubject0.parseClassId();
        OWLClassExpression result36 = testSubject0.NestedClassDescription();
        String result37 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceDLSyntaxParserConstants() throws Exception {
        DLSyntaxParserConstants testSubject0 = mock(DLSyntaxParserConstants.class);
    }
}
