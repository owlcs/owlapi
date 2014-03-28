/**
 * 
 */
package org.semanticweb.owlapi.rio.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.RioRDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rio.RioParserImpl;
import org.semanticweb.owlapi.rio.RioRDFXMLParserFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class RioParserTest extends TestBase {

    @Before
    public void setUp() {
        // Use non-Rio storers
        // limit to Rio parsers for RioParserImpl Test
        // testManager = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager(
        // OWLOntologyManagerFactoryRegistry.getOWLDataFactory(),
        // storerRegistry, parserRegistry);
        m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().set(new RioRDFXMLParserFactory().get());
        // testOntologyKoala =
        // testManager.loadOntologyFromOntologyDocument(this.getClass().getResourceAsStream("/koala.owl"));
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParse() throws OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/koala.owl")), owlapiOntologyPrimer);
        assertEquals(70, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RDFXMLOntologyFormatFactory());
        // OWLOntology ontology = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology ontology = m1
                .createOntology(IRI
                        .create("http://protege.stanford.edu/plugins/owl/owl-library/koala.owl"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/koala.owl")), ontology);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        equal(owlapiOntologyPrimer, ontology);
        assertEquals(70, ontology.getAxiomCount());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimer() throws OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/primer.rdfxml.xml")), owlapiOntologyPrimer);
        assertEquals(93, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager()
        OWLOntology rioOntologyPrimer = m1.createOntology(IRI
                .create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/primer.rdfxml.xml")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(93, rioOntologyPrimer.getAxiomCount());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimerSubset() throws OWLParserException, IOException,
            OWLOntologyCreationException {
        // XXX this test does not work yet
        // output:
        // Rio:
        // DatatypeDefinition(<http://example.com/owl/families/majorAge>
        // DataIntersectionOf(<http://org.semanticweb.owlapi/error#Error1>
        // DataComplementOf(<http://example.com/owl/families/minorAge>) ))
        // OWLAPI:
        // DatatypeDefinition(<http://example.com/owl/families/majorAge>
        // DataIntersectionOf(<http://example.com/owl/families/personAge>
        // DataComplementOf(<http://example.com/owl/families/minorAge>) ))]
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1-minimal.rdf")), owlapiOntologyPrimer);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology rioOntologyPrimer = OWLManager.createOWLOntologyManager()
                .createOntology(IRI.create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1-minimal.rdf")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimerMinimalSubset() throws OWLParserException,
            IOException, OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1-minimal.rdf")), owlapiOntologyPrimer);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology rioOntologyPrimer = m1.createOntology(IRI
                .create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1-minimal.rdf")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }
}
