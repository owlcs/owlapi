package org.semanticweb.owlapi.api.test.ontology;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/** @author Peter Ansell p_ansell@yahoo.com */
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTestCase {
    private IRI v2;
    private IRI v1;
    private IRI _139;
    private String input;
    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        v2 = IRI("http://test.example.org/ontology/0139/version:2");
        v1 = IRI("http://test.example.org/ontology/0139/version:1");
        _139 = IRI("http://test.example.org/ontology/0139");
        input = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF\n"
                + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
                + "  <rdf:Description rdf:about=\"http://test.example.org/ontology/0139\">\n"
                + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\" />\n"
                + "    <owl:versionIRI rdf:resource=\"http://test.example.org/ontology/0139/version:1\" />\n"
                + "  </rdf:Description>  \n" + "</rdf:RDF>";
        manager = Factory.getManager();
    }

    @Test
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParserFactory().createParser(manager);
        initialParser.parse(documentSource, initialOntology);
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
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
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
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        StringDocumentSource secondDocumentSource = new StringDocumentSource(input);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() throws Exception {
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntology initialOntology = manager.createOntology();
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        StringDocumentSource secondDocumentSource = new StringDocumentSource(input);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() throws Exception {
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        StringDocumentSource secondDocumentSource = new StringDocumentSource(input);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(_139, v2);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory().createParser(manager);
        // the following throws the exception
        secondParser.parse(documentSource, secondOntology);
        Assert.assertEquals(_139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        StringDocumentSource documentSource = new StringDocumentSource(input);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(_139, v1);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(_139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }
}
