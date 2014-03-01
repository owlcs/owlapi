package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.coode.owlapi.functionalparser.JJTOWLFunctionalSyntaxParserState;
import org.coode.owlapi.functionalparser.Node;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxOWLParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserConstants;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserFactory;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserTreeConstants;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalparserTest {

    public void shouldTestJJTOWLFunctionalSyntaxParserState() throws Exception {
        JJTOWLFunctionalSyntaxParserState testSubject0 = new JJTOWLFunctionalSyntaxParserState();
        testSubject0.reset();
        boolean result0 = testSubject0.nodeCreated();
        Node result1 = testSubject0.rootNode();
        testSubject0.pushNode(mock(Node.class));
        Node result2 = testSubject0.popNode();
        Node result3 = testSubject0.peekNode();
        int result4 = testSubject0.nodeArity();
        testSubject0.clearNodeScope(mock(Node.class));
        testSubject0.openNodeScope(mock(Node.class));
        testSubject0.closeNodeScope(mock(Node.class), 0);
        testSubject0.closeNodeScope(mock(Node.class), false);
        String result5 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceNode() throws Exception {
        Node testSubject0 = mock(Node.class);
        testSubject0.jjtOpen();
        testSubject0.jjtSetParent(mock(Node.class));
        testSubject0.jjtAddChild(mock(Node.class), 0);
        testSubject0.jjtClose();
        Node result0 = testSubject0.jjtGetParent();
        Node result1 = testSubject0.jjtGetChild(0);
        int result2 = testSubject0.jjtGetNumChildren();
    }

    public void shouldTestOWLFunctionalSyntaxOWLParser() throws Exception {
        OWLFunctionalSyntaxOWLParser testSubject0 = new OWLFunctionalSyntaxOWLParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserConstants()
            throws Exception {
        OWLFunctionalSyntaxParserConstants testSubject0 = mock(OWLFunctionalSyntaxParserConstants.class);
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxParserFactory() throws Exception {
        OWLFunctionalSyntaxParserFactory testSubject0 = new OWLFunctionalSyntaxParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    public void shouldTestInterfaceOWLFunctionalSyntaxParserTreeConstants()
            throws Exception {
        OWLFunctionalSyntaxParserTreeConstants testSubject0 = mock(OWLFunctionalSyntaxParserTreeConstants.class);
    }
}
