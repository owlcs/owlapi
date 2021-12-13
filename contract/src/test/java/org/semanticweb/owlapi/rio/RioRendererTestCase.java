/**
 *
 */
package org.semanticweb.owlapi.rio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class RioRendererTestCase extends TestBase {

    private static final String RESULT_LENGTH = "result.length()=";
    private static final String WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS = " was not inside the expected bounds";
    private static final String RESULT_WAS_LARGER_THAN_EXPECTED = "Result was larger than expected:";
    private static final String RESULT_WAS_SMALLER_THAN_EXPECTED = "Result was smaller than expected:";
    private static final String DUPLICATE_STATEMENTS = "Duplicate statements were emitted";
    private final IRI testOntologyUri1 = iri("urn:test:ontology:uri:1", "");
    private SimpleValueFactory vf;
    private OWLOntology testOntologyEmpty;
    private OWLOntology testOntologyKoala;
    private Statement testOntologyEmptyStatement;
    private StatementCollector testHandlerStatementCollector;
    private StringWriter testRdfXmlStringWriter;
    private RDFWriter testRdfXmlRioWriter;
    private StringWriter testTurtleStringWriter;
    private RDFWriter testTurtleRioWriter;
    private StringWriter testNTriplesStringWriter;
    private RDFWriter testNTriplesRioWriter;

    @BeforeEach
    void setUp() {
        vf = SimpleValueFactory.getInstance();
        m.getOntologyStorers().set(new RioNTriplesStorerFactory(), new RioRDFXMLStorerFactory(),
            new RioTurtleStorerFactory());
        testOntologyEmpty = create(testOntologyUri1);
        testOntologyKoala = loadFrom(getClass().getResourceAsStream("/koala.owl"));
        assertEquals(70, testOntologyKoala.getAxiomCount());
        testHandlerStatementCollector = new StatementCollector();
        testOntologyEmptyStatement =
            vf.createStatement(vf.createIRI("urn:test:ontology:uri:1"), RDF.TYPE, OWL.ONTOLOGY);
        testRdfXmlStringWriter = new StringWriter();
        testRdfXmlRioWriter = Rio.createWriter(RDFFormat.RDFXML, testRdfXmlStringWriter);
        testTurtleStringWriter = new StringWriter();
        testTurtleRioWriter = Rio.createWriter(RDFFormat.TURTLE, testTurtleStringWriter);
        testNTriplesStringWriter = new StringWriter();
        testNTriplesRioWriter = Rio.createWriter(RDFFormat.NTRIPLES, testNTriplesStringWriter);
    }

    @Test
    void testRenderEmptyStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, testHandlerStatementCollector, null);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(1, testHandlerStatementCollector.getStatements().size());
        // Verify that the RDF:TYPE OWL:ONTOLOGY statement was generated for the
        // otherwise empty
        // ontology
        assertEquals(testOntologyEmptyStatement,
            testHandlerStatementCollector.getStatements().iterator().next());
    }

    @Test
    void testRenderEmptyRdfXmlWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testRdfXmlRioWriter, null);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        assertTrue(result.length() > 560, RESULT_WAS_SMALLER_THAN_EXPECTED + result);
        assertTrue(result.length() < 590, RESULT_WAS_LARGER_THAN_EXPECTED + result);
    }

    @Test
    void testRenderEmptyTurtleWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testTurtleRioWriter, null);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        assertTrue(result.length() > 420, RESULT_WAS_SMALLER_THAN_EXPECTED + result);
        assertTrue(result.length() < 450, RESULT_WAS_LARGER_THAN_EXPECTED + result);
    }

    @Test
    void testRenderEmptyNTriplesWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testNTriplesRioWriter, null);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        assertTrue(result.length() > 190, RESULT_WAS_SMALLER_THAN_EXPECTED + result);
        assertTrue(result.length() < 220, RESULT_WAS_LARGER_THAN_EXPECTED + result);
    }

    @Test
    void testRenderKoalaStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, testHandlerStatementCollector, null);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaRdfXmlWriter() throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testRdfXmlRioWriter, null);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 24000,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
        assertTrue(result.length() < 26000,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
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
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaTurtleWriter() throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testTurtleRioWriter, null);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 8000,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
        assertTrue(result.length() < 9500,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRenderKoalaNTriplesWriter()
        throws RDFParseException, RDFHandlerException, IOException {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testNTriplesRioWriter, null);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 26200,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
        assertTrue(result.length() < 27500,
            RESULT_LENGTH + result.length() + WAS_NOT_INSIDE_THE_EXPECTED_BOUNDS);
        RDFParser parser = Rio.createParser(RDFFormat.NTRIPLES, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        // NTriples does not contain namespaces, so we will not find any when
        // parsing the document
        assertEquals(0, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(171, resultStatements.size(), DUPLICATE_STATEMENTS);
    }

    @Test
    void testRioOWLRDFParser() throws RDFParseException, RDFHandlerException, IOException {
        RDFParser parser = new RioManchesterSyntaxParserFactory().getParser();
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(TestFiles.inputManSyntax),
            "http://www.owl-ontologies.com/Ontology1307394066.owl");
        assertEquals(36, testHandlerStatementCollector.getStatements().size());
    }
}
