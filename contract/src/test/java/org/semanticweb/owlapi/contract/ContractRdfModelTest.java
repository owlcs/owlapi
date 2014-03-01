package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.Writer;
import java.util.Collection;
import java.util.Set;

import org.coode.owlapi.rdf.model.AbstractTranslator;
import org.coode.owlapi.rdf.model.RDFGraph;
import org.coode.owlapi.rdf.model.RDFLiteralNode;
import org.coode.owlapi.rdf.model.RDFNode;
import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.owlapi.rdf.model.RDFTranslator;
import org.coode.owlapi.rdf.model.RDFTriple;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractRdfModelTest {

    @Test
    public void shouldTestAbstractTranslator() throws Exception {
        @SuppressWarnings("rawtypes")
        AbstractTranslator testSubject0 = new AbstractTranslator(
                Utils.getMockManager(), Utils.getMockOntology(), false) {

            @Override
            protected Object getResourceNode(IRI IRI) {
                return null;
            }

            @Override
            protected Object getPredicateNode(IRI IRI) {
                return null;
            }

            @Override
            protected Object getAnonymousNode(Object key) {
                return null;
            }

            @Override
            protected Object getLiteralNode(OWLLiteral literal) {
                return null;
            }

            @Override
            protected void
                    addTriple(Object subject, Object pred, Object object) {}
        };
        String result0 = testSubject0.toString();
    }

    public void shouldTestRDFGraph() throws Exception {
        RDFGraph testSubject0 = new RDFGraph();
        testSubject0.addTriple(mock(RDFTriple.class));
        Collection<RDFTriple> result1 = testSubject0.getTriplesForSubject(
                mock(RDFNode.class), false);
        boolean result2 = testSubject0
                .isAnonymousNodeSharedSubject(mock(RDFResourceNode.class));
        Set<RDFResourceNode> result3 = testSubject0.getRootAnonymousNodes();
        testSubject0.dumpTriples(mock(Writer.class));
        String result4 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFLiteralNode() throws Exception {
        RDFLiteralNode testSubject0 = new RDFLiteralNode("", IRI("urn:aFake"));
        RDFLiteralNode testSubject1 = new RDFLiteralNode("", "");
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0.isAnonymous();
        IRI result2 = testSubject0.getIRI();
        String result3 = testSubject0.getLiteral();
        boolean result4 = testSubject0.isLiteral();
        IRI result5 = testSubject0.getDatatype();
        String result6 = testSubject0.getLang();
        boolean result7 = testSubject0.isTyped();
    }

    @Test
    public void shouldTestRDFNode() throws Exception {
        RDFNode testSubject0 = new RDFNode() {

            @Override
            public boolean isLiteral() {
                return false;
            }

            @Override
            public IRI getIRI() {
                return null;
            }

            @Override
            public boolean isAnonymous() {
                return false;
            }

            @Override
            public int compareTo(RDFNode o) {
                return 0;
            }
        };
        boolean result0 = testSubject0.isAnonymous();
        IRI result1 = testSubject0.getIRI();
        boolean result2 = testSubject0.isLiteral();
        String result3 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFResourceNode() throws Exception {
        RDFResourceNode testSubject0 = new RDFResourceNode(0);
        RDFResourceNode testSubject1 = new RDFResourceNode(IRI("urn:aFake"));
        String result0 = testSubject0.toString();
        boolean result1 = testSubject0.isAnonymous();
        IRI result2 = testSubject0.getIRI();
        boolean result3 = testSubject0.isLiteral();
    }

    public void shouldTestRDFTranslator() throws Exception {
        RDFTranslator testSubject0 = new RDFTranslator(Utils.getMockManager(),
                Utils.getMockOntology(), false);
        testSubject0.reset();
        RDFGraph result0 = testSubject0.getGraph();
        RDFLiteralNode result1 = RDFTranslator
                .translateLiteralNode(mock(OWLLiteral.class));
        String result2 = testSubject0.toString();
    }

    @Test
    public void shouldTestRDFTriple() throws Exception {
        RDFTriple testSubject0 = new RDFTriple(mock(RDFResourceNode.class),
                mock(RDFResourceNode.class), mock(RDFNode.class));
        RDFResourceNode result0 = testSubject0.getProperty();
        String result1 = testSubject0.toString();
        RDFNode result2 = testSubject0.getObject();
        RDFResourceNode result3 = testSubject0.getSubject();
    }
}
