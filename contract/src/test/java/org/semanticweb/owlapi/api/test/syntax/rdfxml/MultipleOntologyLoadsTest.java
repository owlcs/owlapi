package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 * Tests the loading of a single ontology multiple times, using the same
 * ontologyIRI in the {@link OWLOntologyID} as that used in the actual ontology
 * that is being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTest {

    private static final IRI EXAMPLE = IRI("http://base.example.com/");
    private static final IRI CREATEv1 = IRI("http://test.example.org/ontology/0139/version:1");
    private static final IRI CREATEv2 = IRI("http://test.example.org/ontology/0139/version:2");
    private static final IRI CREATE0139 = IRI("http://test.example.org/ontology/0139");
    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        manager = Factory.getManager();
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        // given
        ReaderDocumentSource initialDocumentSource = getDocumentSource();
        OWLOntologyID expected = new OWLOntologyID(CREATE0139, CREATEv2);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                CREATE0139, CREATEv2);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        parseOnto(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv2);
        // when
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            // then
            Assert.assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadNoChange() throws Exception {
        // given
        ReaderDocumentSource documentSource = getDocumentSource();
        OWLOntologyID expected = new OWLOntologyID(CREATE0139, CREATEv1);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                CREATE0139, CREATEv1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv1);
        // when
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            // then
            Assert.assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        // given
        ReaderDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                CREATE0139, CREATEv1);
        ReaderDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv2);
        // when
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv1, initialOntology.getOntologyID()
                .getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv2, secondOntology.getOntologyID()
                .getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime()
            throws Exception {
        // given
        ReaderDocumentSource documentSource = getDocumentSource();
        ReaderDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv2);
        // when
        OWLOntology initialOntology = manager.createOntology();
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv1, initialOntology.getOntologyID()
                .getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv2, secondOntology.getOntologyID()
                .getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws Exception {
        // given
        ReaderDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139);
        ReaderDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv2);
        // when
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv1, initialOntology.getOntologyID()
                .getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv2, secondOntology.getOntologyID()
                .getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        // given
        ReaderDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139,
                CREATEv2);
        // when
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv2, secondOntology.getOntologyID()
                .getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        // given
        ReaderDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(
                CREATE0139, CREATEv1);
        // when
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(CREATEv1, initialOntology.getOntologyID()
                .getVersionIRI());
    }

    private void parseOnto(ReaderDocumentSource initialDocumentSource,
            OWLOntology initialOntology) throws OWLParserException,
            IOException, UnloadableImportException {
        OWLParser initialParser = new RDFXMLParserFactory()
                .createParser(manager);
        initialParser.parse(initialDocumentSource, initialOntology);
    }

    private ReaderDocumentSource getDocumentSource() {
        Reader initialInputReader = new InputStreamReader(this.getClass()
                .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        ReaderDocumentSource documentSource = new ReaderDocumentSource(
                initialInputReader, EXAMPLE);
        return documentSource;
    }
}
