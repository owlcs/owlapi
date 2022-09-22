/**
 *
 */
package org.semanticweb.owlapi.riotest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.FileDocumentSource;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rio.RioParserImpl;
import org.semanticweb.owlapi.rio.RioRDFXMLParserFactory;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormatFactory;

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
            getStream("/koala.owl").acceptParser(owlapiParser, owlapiOntologyPrimer, config);
        assertEquals(70, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology ontology =
            create(iri("http://protege.stanford.edu/plugins/owl/owl-library/", "koala.owl"), m1);
        OWLDocumentFormat rioOntologyFormat =
            getStream("/koala.owl").acceptParser(rioParser, ontology, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, ontology);
        assertEquals(70, ontology.getAxiomCount());
    }

    @Test
    void testParsePrimer() throws OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = createAnon();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLDocumentFormat format = getStream("/primer.rdfxml.xml").acceptParser(owlapiParser,
            owlapiOntologyPrimer, config);
        assertEquals(94, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology rioOntologyPrimer =
            m1.createOntology(iri("http://example.com/owl/", "families"));
        OWLDocumentFormat rioOntologyFormat =
            getStream("/primer.rdfxml.xml").acceptParser(rioParser, rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(94, rioOntologyPrimer.getAxiomCount());
    }

    FileDocumentSource getStream(String name) {
        try {
            return new FileDocumentSource(new File(getClass().getResource(name).toURI()));
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

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
            getStream(MINIMAL_RDF).acceptParser(owlapiParser, owlapiOntologyPrimer, config);
        assertEquals(4, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLDocumentFormat(), format);
        RioParserImpl rioParser = new RioParserImpl(new RioRDFXMLDocumentFormatFactory());
        OWLOntology rioOntologyPrimer = create(iri("http://example.com/owl/", "families"), m1);
        OWLDocumentFormat rioOntologyFormat =
            getStream(MINIMAL_RDF).acceptParser(rioParser, rioOntologyPrimer, config);
        assertEquals(new RioRDFXMLDocumentFormat(), rioOntologyFormat);
        equal(owlapiOntologyPrimer, rioOntologyPrimer);
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
    }
}
