package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.File;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

@SuppressWarnings("javadoc")
public class ManchesterImportTestCase {

    private static final String str = "http://owlapitestontologies.com/thesuperont";
    private static final String superpath = "/imports/thesuperont.omn";
    private static final String subpath = "/imports/thesubont.omn";
    private static final File RESOURCES;
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
        assertNotNull(manager.getOntology(IRI(str)));
    }

    private OWLOntologyManager getManager() {
        OWLOntologyManager manager = Factory.getManager();
        AutoIRIMapper mapper = new AutoIRIMapper(
                new File(RESOURCES, "imports"), true);
        manager.addIRIMapper(mapper);
        return manager;
    }

    @Test
    public void testRemoteIsParseable() throws Exception {
        OWLOntologyManager manager = getManager();
        IRI iri = IRI(str);
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
        OWLOntology iriImport = managerTest.loadOntology(IRI(str));
        assertEquals(manualImport.getAxioms(), iriImport.getAxioms());
        assertEquals(manualImport.getOntologyID(), iriImport.getOntologyID());
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntologyManager manager = getManager();
        manager.loadOntologyFromOntologyDocument(new File(RESOURCES, subpath));
    }
}
