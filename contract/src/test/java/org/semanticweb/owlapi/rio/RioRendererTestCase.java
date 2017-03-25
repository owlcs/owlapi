/**
 *
 */
package org.semanticweb.owlapi.rio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings({"javadoc", "null"})
public class RioRendererTestCase extends TestBase {

    private final @Nonnull IRI testOntologyUri1 = IRI.create("urn:test:ontology:uri:1", "");
    private SimpleValueFactory vf;
    private @Nonnull OWLOntology testOntologyEmpty;
    private @Nonnull OWLOntology testOntologyKoala;
    private Statement testOntologyEmptyStatement;
    private StatementCollector testHandlerStatementCollector;
    private StringWriter testRdfXmlStringWriter;
    private RDFWriter testRdfXmlRioWriter;
    private StringWriter testTurtleStringWriter;
    private RDFWriter testTurtleRioWriter;
    private StringWriter testNTriplesStringWriter;
    private RDFWriter testNTriplesRioWriter;

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

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyEmpty, testHandlerStatementCollector);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(1, testHandlerStatementCollector.getStatements().size());
        // Verify that the RDF:TYPE OWL:ONTOLOGY statement was generated for the
        // otherwise empty
        // ontology
        assertEquals(testOntologyEmptyStatement,
            testHandlerStatementCollector.getStatements().iterator().next());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyRdfXmlWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result, result.length() > 560);
        assertTrue("Result was larger than expected:" + result, result.length() < 590);
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyTurtleWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result, result.length() > 420);
        assertTrue("Result was larger than expected:" + result, result.length() < 450);
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderEmptyNTriplesWriter() {
        RioRenderer testRenderer = new RioRenderer(testOntologyEmpty, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        assertTrue("Result was smaller than expected:" + result, result.length() > 190);
        assertTrue("Result was larger than expected:" + result, result.length() < 220);
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaStatementCollector() {
        RioRenderer testRenderer =
            new RioRenderer(testOntologyKoala, testHandlerStatementCollector);
        testRenderer.render();
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171, resultStatements.size());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaRdfXmlWriter() throws Exception {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testRdfXmlRioWriter);
        testRenderer.render();
        // testRdfXmlRioWriter outputs its results to testRdfXmlStringWriter
        String result = testRdfXmlStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
            result.length() > 24000);
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
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
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171, resultStatements.size());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaTurtleWriter() throws Exception {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testTurtleRioWriter);
        testRenderer.render();
        // testTurtleRioWriter outputs its results to testTurtleStringWriter
        String result = testTurtleStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
            result.length() > 7600);
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
            result.length() < 9500);
        RDFParser parser = Rio.createParser(RDFFormat.TURTLE, vf);
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(result), "");
        assertEquals(6, testHandlerStatementCollector.getNamespaces().size());
        assertEquals(171, testHandlerStatementCollector.getStatements().size());
        // check for duplicate statements
        HashSet<Statement> resultStatements =
            new HashSet<>(testHandlerStatementCollector.getStatements());
        assertEquals("Duplicate statements were emitted", 171, resultStatements.size());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioRenderer#render(org.semanticweb.owlapi.io.RDFResource)}
     */
    @Test
    public void testRenderKoalaNTriplesWriter() throws Exception {
        RioRenderer testRenderer = new RioRenderer(testOntologyKoala, testNTriplesRioWriter);
        testRenderer.render();
        // testNTriplesRioWriter outputs its results to testNTriplesStringWriter
        String result = testNTriplesStringWriter.toString();
        // actual length depends on the length of dynamically assigned blank
        // node identifiers, so we
        // only test a minimum length and a maximum length
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
            result.length() > 25500);
        assertTrue("result.length()=" + result.length() + " was not inside the expected bounds",
            result.length() < 27500);
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
        assertEquals("Duplicate statements were emitted", 171, resultStatements.size());
    }

    @Test
    public void testRioOWLRDFParser() throws Exception {
        RDFParser parser = new RioManchesterSyntaxParserFactory().getParser();
        String inputManSyntax = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
            + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
            + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
            + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
            + "Ontology: <http://www.owl-ontologies.com/Ontology1307394066.owl>\n"
            + "Datatype: xsd:decimal\n" + "Datatype: xsd:int\n" + "Datatype: xsd:dateTime\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n"
            + "    Characteristics: \n" + "        Functional\n" + "    Range: \n"
            + "        xsd:int\n"
            + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n"
            + "    Range: \n" + "        xsd:dateTime\n"
            + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
            + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n"
            + "    Types: \n"
            + "        <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n" + "Rule: \n"
            + "    xsd:decimal(?x), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?p, ?x) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?p)";
        parser.setRDFHandler(testHandlerStatementCollector);
        parser.parse(new StringReader(inputManSyntax),
            "http://www.owl-ontologies.com/Ontology1307394066.owl");
        assertEquals(36, testHandlerStatementCollector.getStatements().size());
    }
}
