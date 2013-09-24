package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.RDFOntologyFormat;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.xml.sax.SAXException;

import uk.ac.manchester.cs.owl.owlapi.turtle.parser.ConsoleTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.NullTripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.OWLRDFConsumerAdapter;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TripleHandler;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParserFactory;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractTurtleParserTest {
    private static final String URN_A_FAKE = "urn:aFake";

    public void shouldTestConsoleTripleHandler() throws OWLException {
        ConsoleTripleHandler testSubject0 = new ConsoleTripleHandler();
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
    }

    @Test
    public void shouldTestNullTripleHandler() throws OWLException {
        NullTripleHandler testSubject0 = new NullTripleHandler();
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
    }

    @Test
    public void shouldTestOWLRDFConsumerAdapter() throws OWLException, SAXException {
        OWLRDFConsumerAdapter testSubject0 = new OWLRDFConsumerAdapter(
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        OWLRDFConsumerAdapter testSubject1 = new OWLRDFConsumerAdapter(
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        testSubject0.startModel("");
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI("");
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
    }

    @Test
    public void shouldTestInterfaceTripleHandler() throws OWLException {
        TripleHandler testSubject0 = mock(TripleHandler.class);
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", IRI(URN_A_FAKE));
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), "", "");
        testSubject0.handleTriple(IRI(URN_A_FAKE), IRI(URN_A_FAKE), IRI(URN_A_FAKE));
        testSubject0.handlePrefixDirective("", "");
        testSubject0.handleBaseDirective("");
        testSubject0.handleComment("");
        testSubject0.handleEnd();
    }

    @Test
    public void shouldTestTurtleOntologyParserFactory() throws OWLException {
        TurtleOntologyParserFactory testSubject0 = new TurtleOntologyParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }
}
