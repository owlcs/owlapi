package org.semanticweb.owlapi.api.test;

import java.io.Reader;
import java.io.StringReader;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/** @author Peter Ansell p_ansell@yahoo.com */
public class MultipleOntologyLoadsTestCase {
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

    /** @throws java.lang.Exception */
    @Before
    public void setUp() throws Exception {
        manager = OWLManager.createOWLOntologyManager();
    }

    /** @throws java.lang.Exception */
    @After
    public void tearDown() throws Exception {
        manager = null;
    }

    @Test
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource initialDocumentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        final OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        final OWLParser initialParser = new RDFXMLParserFactory().createParser(manager);
        initialParser.parse(initialDocumentSource, initialOntology);
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            Assert.fail("Did not receive expected OWLOntologyDocumentAlreadyExistsException");
        } catch (final OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(
                    new OWLOntologyID(
                            IRI.create("http://test.example.org/ontology/0139"),
                            IRI.create("http://test.example.org/ontology/0139/version:2")),
                            e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadNoChange() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:1"));
        final OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        final OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:1"));
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            Assert.fail("Did not receive expected OWLOntologyAlreadyExistsException");
        } catch (final OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(
                    new OWLOntologyID(
                            IRI.create("http://test.example.org/ontology/0139"),
                            IRI.create("http://test.example.org/ontology/0139/version:1")),
                            e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:1"));
        final OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        final OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:1"),
                initialOntology.getOntologyID().getVersionIRI());
        final Reader secondInputReader = new StringReader(input);
        // new InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        final OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        final OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        // NOTE: The following call throws the OWLOntologyRenameException before
        // the patch to
        // TypeOntologyHandler and TPVersionIRIHandler
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:2"),
                secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntology initialOntology = manager.createOntology();
        final OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:1"),
                initialOntology.getOntologyID().getVersionIRI());
        final Reader secondInputReader = new StringReader(input);
        // new InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        final OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        final OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        // NOTE: The following call throws the OWLOntologyRenameException before
        // the patch to
        // TypeOntologyHandler and TPVersionIRIHandler
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:2"),
                secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"));
        final OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        final OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:1"),
                initialOntology.getOntologyID().getVersionIRI());
        final Reader secondInputReader = new StringReader(input);
        // new InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        final OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        final OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        // NOTE: The following call throws the OWLOntologyRenameException before
        // the patch to
        // TypeOntologyHandler and TPVersionIRIHandler
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:2"),
                secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        // final Reader secondInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader secondInputReader = new StringReader(input);
        final ReaderDocumentSource secondDocumentSource = new ReaderDocumentSource(
                secondInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:2"));
        final OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        final OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        // the following throws the exception
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:2"),
                secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        // final Reader initialInputReader = new
        // InputStreamReader(this.getClass()
        // .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        final Reader initialInputReader = new StringReader(input);
        final ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, IRI.create("http://base.example.com/"));
        final OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                IRI.create("http://test.example.org/ontology/0139"),
                IRI.create("http://test.example.org/ontology/0139/version:1"));
        final OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        final OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI.create("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(
                IRI.create("http://test.example.org/ontology/0139/version:1"),
                initialOntology.getOntologyID().getVersionIRI());
    }
}
