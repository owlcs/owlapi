/**
 * 
 */
package org.semanticweb.owlapi.rio.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormatFactory;
import org.semanticweb.owlapi.formats.RioRDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rio.RioParserImpl;
import org.semanticweb.owlapi.rio.RioRDFXMLParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RioParserTest {

    private final Logger logger = LoggerFactory.getLogger(RioParserTest.class);
    private OWLOntologyManager testManager;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Use non-Rio storers
        // limit to Rio parsers for RioParserImpl Test
        // testManager = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager(
        // OWLOntologyManagerFactoryRegistry.getOWLDataFactory(),
        // storerRegistry, parserRegistry);
        testManager = OWLManager.createOWLOntologyManager();
        testManager.getOntologyParsers()
                .set(new RioRDFXMLParserFactory().get());
        // testOntologyKoala =
        // testManager.loadOntologyFromOntologyDocument(this.getClass().getResourceAsStream("/koala.owl"));
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {}

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     * .
     * 
     * @throws IOException
     * @throws OWLParserException
     * @throws OWLOntologyCreationException
     */
    @Test
    public void testParse() throws OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = testManager.createOntology();
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
        OWLOntology ontology = OWLManager.createOWLOntologyManager()
                .createOntology(
                        IRI.create("urn:test:rioparser:ontology:koala:1"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/koala.owl")), ontology);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        List<String> missingAxioms = new ArrayList<String>();
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!ontology.getAxioms().contains(nextAxiom)) {
                missingAxioms.add(nextAxiom.toString());
            }
        }
        // Sort the axioms so that they display nicely
        Collections.sort(missingAxioms);
        for (String nextMissingAxiom : missingAxioms) {
            logger.error("RioParserImpl did not contain expected axiom: {}",
                    nextMissingAxiom);
        }
        logger.error("RioParserImpl was missing {} expected axioms",
                missingAxioms.size());
        for (OWLAxiom nextAxiom : ontology.getAxioms()) {
            if (!owlapiOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl contained unexpected axiom: {}",
                        nextAxiom);
            }
        }
        assertEquals(70, ontology.getAxiomCount());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     * .
     * 
     * @throws IOException
     * @throws OWLParserException
     * @throws OWLOntologyCreationException
     */
    @Test
    public void testParsePrimer() throws OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = testManager.createOntology();
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
        OWLOntology rioOntologyPrimer = OWLManager.createOWLOntologyManager()
                .createOntology(
                        IRI.create("urn:test:rioparser:ontology:primer:1"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/primer.rdfxml.xml")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        List<String> missingAxioms = new ArrayList<String>();
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                missingAxioms.add(nextAxiom.toString());
            }
        }
        // Sort the axioms so that they display nicely
        Collections.sort(missingAxioms);
        for (String nextMissingAxiom : missingAxioms) {
            logger.error("RioParserImpl did not contain expected axiom: {}",
                    nextMissingAxiom);
        }
        logger.error("RioParserImpl was missing {} expected axioms",
                missingAxioms.size());
        for (OWLAxiom nextAxiom : rioOntologyPrimer.getAxioms()) {
            if (!owlapiOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl contained unexpected axiom: {}",
                        nextAxiom);
            }
        }
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl was missing an expected axiom: {}",
                        nextAxiom);
            }
        }
        assertEquals(93, rioOntologyPrimer.getAxiomCount());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     * .
     * 
     * @throws IOException
     * @throws OWLParserException
     * @throws OWLOntologyCreationException
     */
    @Ignore
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
        OWLOntology owlapiOntologyPrimer = testManager.createOntology();
        RDFXMLParser owlapiParser = new RDFXMLParser();
        OWLOntologyFormat owlapiOntologyFormat = owlapiParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1.rdf")), owlapiOntologyPrimer);
        assertEquals(2, owlapiOntologyPrimer.getAxiomCount());
        assertEquals(new RDFXMLOntologyFormat(), owlapiOntologyFormat);
        RioParserImpl rioParser = new RioParserImpl(
                new RDFXMLOntologyFormatFactory());
        // OWLOntology rioOntologyPrimer = OWLOntologyManagerFactoryRegistry
        // .createOWLOntologyManager().createOntology(
        OWLOntology rioOntologyPrimer = OWLManager.createOWLOntologyManager()
                .createOntology(
                        IRI.create("urn:test:rioparser:ontology:primer:1"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1.rdf")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        List<String> missingAxioms = new ArrayList<String>();
        System.out.println("RioParserTest.testParsePrimerSubset() "
                + rioOntologyPrimer.getAxioms());
        System.out.println("RioParserTest.testParsePrimerSubset() "
                + owlapiOntologyPrimer.getAxioms());
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                missingAxioms.add(nextAxiom.toString());
            }
        }
        // Sort the axioms so that they display nicely
        Collections.sort(missingAxioms);
        for (String nextMissingAxiom : missingAxioms) {
            logger.error("RioParserImpl did not contain expected axiom: {}",
                    nextMissingAxiom);
        }
        if (!missingAxioms.isEmpty()) {
            logger.error("RioParserImpl was missing {} expected axioms",
                    missingAxioms.size());
        }
        for (OWLAxiom nextAxiom : rioOntologyPrimer.getAxioms()) {
            if (!owlapiOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl contained unexpected axiom: {}",
                        nextAxiom);
            }
        }
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl was missing an expected axiom: {}",
                        nextAxiom);
            }
        }
        assertEquals(2, rioOntologyPrimer.getAxiomCount());
        assertTrue("There were missing axioms " + missingAxioms,
                missingAxioms.isEmpty());
    }

    /**
     * Test method for
     * {@link org.semanticweb.owlapi.rio.RioParserImpl#parse(org.semanticweb.owlapi.io.OWLOntologyDocumentSource, org.semanticweb.owlapi.model.OWLOntology)}
     * .
     * 
     * @throws IOException
     * @throws OWLParserException
     * @throws OWLOntologyCreationException
     */
    @Test
    public void testParsePrimerMinimalSubset() throws OWLParserException,
            IOException, OWLOntologyCreationException {
        OWLOntology owlapiOntologyPrimer = testManager.createOntology();
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
                .createOntology(
                        IRI.create("urn:test:rioparser:ontology:primer:1"));
        OWLOntologyFormat rioOntologyFormat = rioParser.parse(
                new StreamDocumentSource(this.getClass().getResourceAsStream(
                        "/rioParserTest1-minimal.rdf")), rioOntologyPrimer);
        assertEquals(new RioRDFXMLOntologyFormat(RDFFormat.RDFXML),
                rioOntologyFormat);
        List<String> missingAxioms = new ArrayList<String>();
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                missingAxioms.add(nextAxiom.toString());
            }
        }
        // Sort the axioms so that they display nicely
        Collections.sort(missingAxioms);
        for (String nextMissingAxiom : missingAxioms) {
            logger.error("RioParserImpl did not contain expected axiom: {}",
                    nextMissingAxiom);
        }
        if (!missingAxioms.isEmpty()) {
            logger.error("RioParserImpl was missing {} expected axioms",
                    missingAxioms.size());
        }
        for (OWLAxiom nextAxiom : rioOntologyPrimer.getAxioms()) {
            if (!owlapiOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl contained unexpected axiom: {}",
                        nextAxiom);
            }
        }
        for (OWLAxiom nextAxiom : owlapiOntologyPrimer.getAxioms()) {
            if (!rioOntologyPrimer.getAxioms().contains(nextAxiom)) {
                logger.error("RioParserImpl was missing an expected axiom: {}",
                        nextAxiom);
            }
        }
        assertEquals(4, rioOntologyPrimer.getAxiomCount());
        assertTrue("There were missing axioms", missingAxioms.isEmpty());
    }
}
