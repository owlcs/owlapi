package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.InputStreamReader;
import java.io.Reader;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
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

/**
 * Tests the loading of a single ontology multiple times, using a different
 * ontologyIRI in the OWLOntologyID as that used in the actual ontology that is
 * being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class MultipleDistinctOntologyLoadsTest {

    private OWLOntologyManager manager;
    IRI JB = IRI("http://example.purl.org.au/domainontology/JB_000007");
    IRI v1 = IRI("http://test.example.org/ontology/0139/version:1");
    IRI v2 = IRI("http://test.example.org/ontology/0139/version:2");

    @Before
    public void setUp() {
        manager = Factory.getManager();
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        ReaderDocumentSource initialDocumentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParserFactory()
                .createParser(manager);
        initialParser.parse(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(new OWLOntologyID(JB, v2), e.getOntologyID());
            throw e;
        }
    }

    private ReaderDocumentSource getDocument() {
        Reader initialInputReader = new InputStreamReader(this.getClass()
                .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        ReaderDocumentSource initialDocumentSource = new ReaderDocumentSource(
                initialInputReader, IRI("http://base.example.com/"));
        return initialDocumentSource;
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadNoChange() throws Exception {
        ReaderDocumentSource documentSource = getDocument();
        OWLOntologyID expected = new OWLOntologyID(JB, v1);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(JB, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v1);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        ReaderDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(JB, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(JB, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        ReaderDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(JB, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime()
            throws Exception {
        ReaderDocumentSource documentSource = getDocument();
        OWLOntology initialOntology = manager.createOntology();
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        ReaderDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(JB, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws Exception {
        ReaderDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(JB);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(JB, initialOntology.getOntologyID()
                .getOntologyIRI());
        // FIXME: versionIRI is null for some reason even though it was in the
        // document
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        ReaderDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        // NOTE: The following call throws the OWLOntologyRenameException before
        // the patch to TypeOntologyHandler and TPVersionIRIHandler
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(JB, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        ReaderDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(JB, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParserFactory()
                .createParser(manager);
        // the following throws the exception
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(JB, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        ReaderDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(JB, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParserFactory().createParser(manager);
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(JB, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }
}
