/**
 *
 */
package org.semanticweb.owlapi.rio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class RioParserTestCase extends TestBase {

    private static final String MINIMAL_RDF = "/rioParserTest1-minimal.rdf";

    @BeforeEach
    void setUpManager() {
        // Use non-Rio storers
        // limit to Rio parsers for RioParserImpl Test
        m.getOntologyParsers().set(new RioRDFXMLParserFactory());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.
     * OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    void testParse() {
        OWLOntology owlapiOntologyPrimer = createAnon();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLDocumentFormat format =
            owlapiParser.parse(getStream("/koala.owl"), owlapiOntologyPrimer, config);
        assertEquals(70, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology ontology =
            m.getOntology(iri("http://protege.stanford.edu/plugins/owl/owl-library/", "koala.owl"));
        OWLDocumentFormat rioOntologyFormat =
            rioParser.parse(getStream("/koala.owl"), ontology, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, ontology);
        assertEquals(70, ontology.getAxiomCount());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.
     * OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    void testParsePrimer() throws OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = createAnon();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLDocumentFormat format =
            owlapiParser.parse(getStream("/primer.rdfxml.xml"), owlapiOntologyPrimer, config);
        assertEquals(93, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology rioOntologyPrimer =
            m1.createOntology(iri("http://example.com/owl/", "families"));
        OWLDocumentFormat rioOntologyFormat =
            rioParser.parse(getStream("/primer.rdfxml.xml"), rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(93, rioOntologyPrimer.getAxiomCount());
    }

    StreamDocumentSource getStream(String name) {
        return new StreamDocumentSource(getClass().getResourceAsStream(name));
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.
     * OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    void testParsePrimerSubset() {
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
        OWLOntology owlapiOntologyPrimer = createAnon();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLDocumentFormat format =
            owlapiParser.parse(getStream(MINIMAL_RDF), owlapiOntologyPrimer, config);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology rioOntologyPrimer = m.getOntology(iri("http://example.com/owl/", "families"));
        OWLDocumentFormat rioOntologyFormat =
            rioParser.parse(getStream(MINIMAL_RDF), rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }

    /*
     * Test method for {@link
     * org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.
     * OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     */
    @Test
    void testParsePrimerMinimalSubset() {
        OWLOntology owlapiOntologyPrimer = createAnon();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLDocumentFormat format =
            owlapiParser.parse(getStream(MINIMAL_RDF), owlapiOntologyPrimer, config);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology rioOntologyPrimer = m.getOntology(iri("http://example.com/owl/", "families"));
        OWLDocumentFormat rioOntologyFormat =
            rioParser.parse(getStream(MINIMAL_RDF), rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }
}
