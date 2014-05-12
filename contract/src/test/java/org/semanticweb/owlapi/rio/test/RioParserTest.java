/**
 * 
 */
package org.semanticweb.owlapi.rio.test;

import static org.junit.Assert.assertEquals;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLOntologyFormatFactory;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rio.RioParserImpl;
import org.semanticweb.owlapi.rio.RioRDFXMLParserFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings({ "javadoc", "null" })
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
    public void testParse() throws Exception {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                getStream("/koala.owl"), owlapiOntologyPrimer, config);
        assertEquals(70, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RioRDFXMLOntologyFormatFactory());
        // OWLOntology ontology = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology ontology = m1
                .createOntology(IRI
                        .create("http://protege.stanford.edu/plugins/owl/owl-library/koala.owl"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                getStream("/koala.owl"), ontology, config);
        assertEquals(new RioRDFXMLOntologyFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, ontology);
        assertEquals(70, ontology.getAxiomCount());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimer() throws Exception {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                getStream("/primer.rdfxml.xml"), owlapiOntologyPrimer, config);
        assertEquals(93, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RioRDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager()
        OWLOntology rioOntologyPrimer = m1.createOntology(IRI
                .create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                getStream("/primer.rdfxml.xml"), rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLOntologyFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(93, rioOntologyPrimer.getAxiomCount());
    }

    /**
     * @return stream
     */
    @Nonnull
    StreamDocumentSource getStream(String name) {
        return new StreamDocumentSource(getClass().getResourceAsStream(name));
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimerSubset() throws Exception {
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
                getStream("/rioParserTest1-minimal.rdf"), owlapiOntologyPrimer,
                config);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RioRDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology rioOntologyPrimer = OWLManager.createOWLOntologyManager()
                .createOntology(IRI.create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                getStream("/rioParserTest1-minimal.rdf"), rioOntologyPrimer,
                config);
        assertEquals(new RioRDFXMLOntologyFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }

    /*
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    public void testParsePrimerMinimalSubset() throws Exception {
        OWLOntology owlapiOntologyPrimer = m.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                getStream("/rioParserTest1-minimal.rdf"), owlapiOntologyPrimer,
                config);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RioRDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology rioOntologyPrimer = m1.createOntology(IRI
                .create("http://example.com/owl/families"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                getStream("/rioParserTest1-minimal.rdf"), rioOntologyPrimer,
                config);
        assertEquals(new RioRDFXMLOntologyFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }
}
