package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;

import org.coode.owlapi.functionalparser.Node;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParser;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserConstants;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserFactory;
import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxParserTreeConstants;
import org.coode.owlapi.functionalparser.SimpleNode;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalparserTest {
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


    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserConstants() throws Exception {
        OWLFunctionalSyntaxParserConstants testSubject0 = mock(OWLFunctionalSyntaxParserConstants.class);
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxParserFactory() throws Exception {
        OWLFunctionalSyntaxParserFactory testSubject0 = new OWLFunctionalSyntaxParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
        String result1 = testSubject0.toString();
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserTreeConstants()
            throws Exception {
        OWLFunctionalSyntaxParserTreeConstants testSubject0 = mock(OWLFunctionalSyntaxParserTreeConstants.class);
    }



    @Test
    public void shouldTestSimpleNode() throws Exception {
        SimpleNode testSubject0 = new SimpleNode(mock(OWLFunctionalSyntaxParser.class), 0);
        SimpleNode testSubject1 = new SimpleNode(0);
        String result0 = testSubject0.toString();
        String result1 = testSubject0.toString("");
        testSubject0.dump("");
        testSubject0.jjtOpen();
        testSubject0.jjtSetParent(mock(Node.class));
        testSubject0.jjtAddChild(mock(Node.class), 0);
        testSubject0.jjtClose();
        Node result2 = testSubject0.jjtGetParent();
        Node result3 = testSubject0.jjtGetChild(0);
        int result4 = testSubject0.jjtGetNumChildren();
        testSubject0.jjtSetValue(mock(Object.class));
        Object result5 = testSubject0.jjtGetValue();
    }

}
