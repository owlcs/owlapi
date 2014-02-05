package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.File;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormatFactory;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

@SuppressWarnings("javadoc")
public class ManchesterImportTestCase extends AbstractOWLAPITestCase {
    private final String str = "http://owlapitestontologies.com/thesuperont";
    private final String superpath = "/imports/thesuperont.omn";
    private final String subpath = "/imports/thesubont.omn";

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        copyResourceToFile(testDataFolder, superpath);
        copyResourceToFile(testDataFolder, subpath);
    }
    
    @Test
    public void testManualImports() throws Exception {
        OWLOntologyManager manager = getMappedManager();
        manager.loadOntologyFromOntologyDocument(new FileDocumentSource(new File(testDataFolder, superpath), new ManchesterOWLSyntaxOntologyFormatFactory()));
        assertNotNull(manager.getOntology(IRI(str)));
    }

    public OWLOntologyManager getMappedManager() {
        OWLOntologyManager nextManager = this.getManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(testDataFolder, "imports"), true);
        nextManager.addIRIMapper(mapper);
        return nextManager;
    }

    @Test
    public void testRemoteIsParseable() throws Exception {
        OWLOntologyManager manager = getMappedManager();
        IRI iri = IRI(str);
        OWLOntology ontology = manager.loadOntology(iri);
        assertTrue("Manager did not load ontology as Manchester syntax: "+ manager.getOntologyFormat(ontology),
                manager.getOntologyFormat(ontology) instanceof ManchesterOWLSyntaxOntologyFormat);
        assertEquals(1, ontology.getAxioms().size());
        assertEquals(ontology.getOntologyID().getOntologyIRI(), iri);
        assertNotNull(manager.getOntology(iri));
    }

    @Test
    public void testEquivalentLoading() throws Exception {
        OWLOntologyManager managerStart = getMappedManager();
        OWLOntology manualImport = managerStart
                .loadOntologyFromOntologyDocument(new FileDocumentSource(new File(testDataFolder, superpath), new ManchesterOWLSyntaxOntologyFormatFactory()));
        OWLOntologyManager managerTest = getManager();
        OWLOntology iriImport = managerTest.loadOntology(IRI(str));
        assertEquals(manualImport.getAxioms(), iriImport.getAxioms());
        assertEquals(manualImport.getOntologyID(), iriImport.getOntologyID());
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntologyManager manager = getMappedManager();
        manager.loadOntologyFromOntologyDocument(new FileDocumentSource(new File(testDataFolder, subpath), new ManchesterOWLSyntaxOntologyFormatFactory()));
    }
}
