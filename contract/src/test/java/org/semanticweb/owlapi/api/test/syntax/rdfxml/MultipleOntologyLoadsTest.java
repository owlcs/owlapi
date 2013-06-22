package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;

import org.coode.owlapi.rdfxml.parser.RDFXMLParserFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/** Tests the loading of a single ontology multiple times, using the same
 * ontologyIRI in the {@link OWLOntologyID} as that used in the actual ontology
 * that is being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com */
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTest {
    private static final IRI CREATEV1 = IRI("http://test.example.org/ontology/0139/version:1");
    private static final IRI CREATEV2 = IRI("http://test.example.org/ontology/0139/version:2");
    private static final IRI CREATE0139 = IRI("http://test.example.org/ontology/0139");
    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        manager = Factory.getManager();
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI() throws OWLOntologyCreationException,
            OWLOntologyChangeException, OWLParserException, IOException {
        // given
        OWLOntologyDocumentSource initialDocumentSource = getDocumentSource();
        OWLOntologyID expected = new OWLOntologyID(CREATE0139, CREATEV2);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        parseOnto(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
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
    public void testMultipleVersionLoadNoChange() throws OWLException, IOException {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID expected = new OWLOntologyID(CREATE0139, CREATEV1);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV1);
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV1);
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
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws OWLException,
            IOException {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV1);
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() throws OWLException,
            IOException {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = manager.createOntology();
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws OWLException, IOException {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139);
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI());
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws OWLException, IOException {
        // given
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology secondOntology = manager.createOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        Assert.assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws OWLException, IOException {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(CREATE0139, CREATEV1);
        // when
        OWLOntology initialOntology = manager.createOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        // then
        Assert.assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI());
    }

    private void parseOnto(OWLOntologyDocumentSource initialDocumentSource,
            OWLOntology initialOntology) throws OWLParserException, IOException,
            UnloadableImportException {
        OWLParser initialParser = new RDFXMLParserFactory().createParser(manager);
        initialParser.parse(initialDocumentSource, initialOntology);
    }

    private OWLOntologyDocumentSource getDocumentSource() {
        StreamDocumentSource documentSource = new StreamDocumentSource(this.getClass()
                .getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
        return documentSource;
    }
}
