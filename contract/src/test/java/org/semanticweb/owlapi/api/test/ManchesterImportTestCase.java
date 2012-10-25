package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

@SuppressWarnings("javadoc")

public class ManchesterImportTestCase {
    final String str = "http://owlapitestontologies.com/thesuperont";
    final String superpath = "/imports/thesuperont.omn";
    final String subpath = "/imports/thesubont.omn";
    public static final File RESOURCES;
    static {
        File f = new File("contract/src/test/resources/");
        if (f.exists()) {
            RESOURCES = f;
        } else {
            f = new File("src/test/resources/");
            if (f.exists()) {
                RESOURCES = f;
            } else {
                RESOURCES = null;
                System.out
                .println("ManchesterImportTestCase: NO RESOURCE FOLDER ACCESSIBLE");
            }
        }
    }

    @Test
    public void testManualImports() throws Exception {
        OWLOntologyManager manager = getManager();
        manager.loadOntologyFromOntologyDocument(new File(RESOURCES, superpath));
        assertNotNull(manager.getOntology(IRI.create(str)));
    }

    private OWLOntologyManager getManager() {
        OWLOntologyManager manager = Factory.getManager();
        final AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, "imports"), true);
        manager.addIRIMapper(mapper);
        return manager;
    }

    @Test
    public void testRemoteIsParseable() throws Exception {
        OWLOntologyManager manager = getManager();
        final IRI iri = IRI.create(str);
        OWLOntology ontology = manager.loadOntology(iri);
        assertEquals(1, ontology.getAxioms().size());
        assertEquals(ontology.getOntologyID().getOntologyIRI(), iri);
        assertNotNull(manager.getOntology(iri));
    }

    @Test
    public void testEquivalentLoading() throws Exception {
        OWLOntologyManager managerStart = getManager();
        OWLOntology manualImport = managerStart
                .loadOntologyFromOntologyDocument(new File(RESOURCES, superpath));
        OWLOntologyManager managerTest = getManager();
        OWLOntology iriImport = managerTest.loadOntology(IRI.create(str));
        assertEquals(manualImport.getAxioms(), iriImport.getAxioms());
        assertEquals(manualImport.getOntologyID(), iriImport.getOntologyID());
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntologyManager manager = getManager();
        manager.loadOntologyFromOntologyDocument(new File(RESOURCES, subpath));
    }
}
