/**
 *
 */
package org.semanticweb.owlapi6.riotest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.rio.RioManchesterSyntaxParserFactory;
import org.semanticweb.owlapi6.rio.RioNTriplesStorerFactory;
import org.semanticweb.owlapi6.rio.RioRDFXMLStorerFactory;
import org.semanticweb.owlapi6.rio.RioRenderer;
import org.semanticweb.owlapi6.rio.RioTurtleStorerFactory;
import org.semanticweb.owlapi6.rioformats.RioRDFXMLDocumentFormat;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RioRendererTestCase extends TestBase {

    private static final String DUPLICATE_STATEMENTS = "Duplicate statements were emitted";
    private final IRI testOntologyUri1 = df.getIRI("urn:test:ontology:uri:1", "");
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
    private final RioRDFXMLDocumentFormat format = new RioRDFXMLDocumentFormat();

    @Before
    public void setUp() throws Exception {
        vf = SimpleValueFactory.getInstance();
        m.getOntologyStorers().set(new RioNTriplesStorerFactory(), new RioRDFXMLStorerFactory(),
            new RioTurtleStorerFactory());
        testOntologyEmpty = m.createOntology(testOntologyUri1);
        testOntologyKoala =
            m.loadOntologyFromOntologyDocument(getClass().getResourceAsStream("/koala.owl"));
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
    public void testRenderEmptyStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, format, testHandlerStatementCollector);
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
    public void testRenderEmptyRdfXmlWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, format, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        assertTrue(result.length() > 560);
        assertTrue(result.length() < 590);
    }

    @Test
    public void testRenderEmptyTurtleWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, format, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        assertTrue(result.length() > 420);
        assertTrue(result.length() < 450);
    }

    @Test
    public void testRenderEmptyNTriplesWriter() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, format, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        assertTrue(result.length() > 190);
        assertTrue(result.length() < 220);
    }

    @Test
    public void testRenderKoalaStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, format, testHandlerStatementCollector);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(DUPLICATE_STATEMENTS, 171, resultStatements.size());
    }

    @Test
    public void testRenderKoalaRdfXmlWriter() throws Exception {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, format, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 23500);
        assertTrue(result.length() < 26000);
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
        assertEquals(DUPLICATE_STATEMENTS, 171, resultStatements.size());
    }

    @Test
    public void testRenderKoalaTurtleWriter() throws Exception {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, format, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 7500);
        assertTrue(result.length() < 9500);
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals(DUPLICATE_STATEMENTS, 171, resultStatements.size());
    }

    @Test
    public void testRenderKoalaNTriplesWriter() throws Exception {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, format, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue(result.length() > 25500);
        assertTrue(result.length() < 27500);
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
        assertEquals(DUPLICATE_STATEMENTS, 171, resultStatements.size());
    }

    @Test
    public void testRioOWLRDFParser() throws Exception {
        RDFParser parser = new RioManchesterSyntaxParserFactory().getParser();
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(TestFiles.inputManSyntax),
            "http://www.owl-ontologies.com/Ontology1307394066.owl");
        assertEquals(36, testHandlerStatementCollector.getStatements().size());
    }
}
