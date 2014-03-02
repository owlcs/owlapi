/**
 * 
 */
package org.semanticweb.owlapi.rio.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.OWL;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.rio.RioNTriplesOntologyStorerFactory;
import org.semanticweb.owlapi.rio.RioRDFXMLOntologyStorerFactory;
import org.semanticweb.owlapi.rio.RioRenderer;
import org.semanticweb.owlapi.rio.RioTurtleOntologyStorerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class RioRendererTest {

    private ValueFactory vf;
    private OWLOntologyManager testManager;
    private OWLOntology testOntologyEmpty;
    private OWLOntology testOntologyKoala;
    private Statement testOntologyEmptyStatement;
    private IRI testOntologyUri1;
    private StatementCollector testHandlerStatementCollector;
    private StringWriter testRdfXmlStringWriter;
    private RDFWriter testRdfXmlRioWriter;
    private StringWriter testTurtleStringWriter;
    private RDFWriter testTurtleRioWriter;
    private StringWriter testNTriplesStringWriter;
    private RDFWriter testNTriplesRioWriter;

    @Before
    public void setUp() throws Exception {
        vf = new ValueFactoryImpl();
        // limit the storers to known Rio OntologyStorers to minimise false
        // negative results
        // use all parsers for renderer test
        // OWLParserFactoryRegistry parserRegistry = new
        // OWLParserFactoryRegistry();
        // XXX update registry
        // testManager = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager(
        // OWLOntologyManagerFactoryRegistry.getOWLDataFactory(),
        // storerRegistry, parserRegistry);
        testManager = OWLManager.createOWLOntologyManager();
        testManager.getOntologyStorers().set(
                new RioNTriplesOntologyStorerFactory().get(),
                new RioRDFXMLOntologyStorerFactory().get(),
                new RioTurtleOntologyStorerFactory().get());
        testOntologyUri1 = IRI.create("urn:test:ontology:uri:1");
        testOntologyEmpty = testManager.createOntology(testOntologyUri1);
        testOntologyKoala = testManager.loadOntologyFromOntologyDocument(this
                .getClass().getResourceAsStream("/koala.owl"));
        assertEquals(70, testOntologyKoala.getAxiomCount());
        testHandlerStatementCollector = new StatementCollector();
        testOntologyEmptyStatement = vf
                .createStatement(vf.createURI("urn:test:ontology:uri:1"),
                        RDF.TYPE, OWL.ONTOLOGY);
        testRdfXmlStringWriter = new StringWriter();
        testRdfXmlRioWriter = Rio.createWriter(RDFFormat.RDFXML,
                testRdfXmlStringWriter);
        testTurtleStringWriter = new StringWriter();
        testTurtleRioWriter = Rio.createWriter(RDFFormat.TURTLE,
                testTurtleStringWriter);
        testNTriplesStringWriter = new StringWriter();
        testNTriplesRioWriter = Rio.createWriter(RDFFormat.NTRIPLES,
                testNTriplesStringWriter);
    }

    /* 
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyStatementCollector() throws IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty,
                testHandlerStatementCollector, null);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(1, testHandlerStatementCollector.getStatements().size());
        // Verify that the RDF:TYPE OWL:ONTOLOGY statement was generated for the
        // otherwise empty
        // ontology
        assertEquals(testOntologyEmptyStatement, testHandlerStatementCollector
                .getStatements().iterator().next());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyRdfXmlWriter() throws IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty,
                testRdfXmlRioWriter, null);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result,
                result.length() > 560);
        assertTrue("Result was larger than expected:" + result,
                result.length() < 590);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyTurtleWriter() throws IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty,
                testTurtleRioWriter, null);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result,
                result.length() > 420);
        assertTrue("Result was larger than expected:" + result,
                result.length() < 450);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyNTriplesWriter() throws IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty,
                testNTriplesRioWriter, null);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result,
                result.length() > 190);
        assertTrue("Result was larger than expected:" + result,
                result.length() < 220);
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaStatementCollector() throws IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala,
                testHandlerStatementCollector, null);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements = new HashSet<Statement>(
                testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171,
                resultStatements.size());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaRdfXmlWriter() throws IOException,
            RDFParseException, RDFHandlerException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala,
                testRdfXmlRioWriter, null);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds",
                result.length() > 24000);
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds",
                result.length() < 26000);
        RDFParser parser = Rio.createParser(RDFFormat.RDFXML, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        // NOTE: The base xmlns: does not get counted as a namespace by the Rio
        // RDFXML parser, which
        // is why it counts to 5, compared to direct StatementCollector result
        // and the turtle result
        assertEquals(5, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements = new HashSet<Statement>(
                testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171,
                resultStatements.size());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaTurtleWriter() throws IOException,
            RDFParseException, RDFHandlerException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala,
                testTurtleRioWriter, null);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds", result.length() > 8500);
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds", result.length() < 9500);
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements = new HashSet<Statement>(
                testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171,
                resultStatements.size());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaNTriplesWriter() throws IOException,
            RDFParseException, RDFHandlerException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala,
                testNTriplesRioWriter, null);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds",
                result.length() > 26500);
        assertTrue("result.length()=" + result.length()
                + " was not inside the expected bounds",
                result.length() < 27500);
        RDFParser parser = Rio.createParser(RDFFormat.NTRIPLES, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        // NTriples does not contain namespaces, so we will not find any when
        // parsing the document
        assertEquals(0, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements = new HashSet<Statement>(
                testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171,
                resultStatements.size());
    }
}
