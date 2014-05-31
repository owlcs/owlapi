package org.semanticweb.owlapi.api.test.ontology;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.Reader;
import java.io.StringReader;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/** @author Peter Ansell p_ansell@yahoo.com */
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTestCase {

    private static final IRI v2 = IRI("http://test.example.org/ontology/0139/version:2");
    private static final IRI o = IRI("http://base.example.com/");
    private static final IRI v1 = IRI("http://test.example.org/ontology/0139/version:1");
    private static final IRI _139 = IRI("http://test.example.org/ontology/0139");
    String input = "<?xml version=\"1.0\"?>\n"
            + "<rdf:RDF\n"
            + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
            + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
            + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
            + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
            + "  <rdf:Description rdf:about=\"http://test.example.org/ontology/0139\">\n"
            + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\" />\n"
            + "    <owl:versionIRI rdf:resource=\"http://test.example.org/ontology/0139/version:1\" />\n"
            + "  </rdf:Description>  \n" + "</rdf:RDF>";
    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        manager = Factory.getManager();
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource initialDocumentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParserFactory()
                .createParser(manager);
        initialParser.parse(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            Assert.fail("Did not receive expected OWLOntologyDocumentAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(new OWLOntologyID(_139, v2), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadNoChange() throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            Assert.fail("Did not receive expected OWLOntologyAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(new OWLOntologyID(_139, v1), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        Reader secondInputReader = new StringReader(input);
        ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, o);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime()
            throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntology initialOntology = manager.createOntology();
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        Reader secondInputReader = new StringReader(input);
        ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, o);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        Reader secondInputReader = new StringReader(input);
        ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, o);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        Reader secondInputReader = new StringReader(input);
        ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, o);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        // the following throws the exception
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        Reader initialInputReader = new StringReader(input);
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, o);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }
}
