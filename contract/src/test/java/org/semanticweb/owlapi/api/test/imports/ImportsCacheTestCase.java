package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.OWLImportsDeclarationImpl;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10 Jul
 * 16
 */
@SuppressWarnings("javadoc")
public class ImportsCacheTestCase extends TestBase {

    private OWLOntology ontA;
    private OWLOntology ontB;
    private IRI ontBDocIri;
    private OWLImportsDeclarationImpl ontBDocumentIriImportsDeclaration;

    @Before
    public void setUpOntologies() throws Exception {
        ontA = m.createOntology(IRI.create("http://ont.com/ontA"));
        ontB = m.createOntology(IRI.create("http://ont.com/ontB"));
        ontBDocIri = IRI.create("http://docs.ont.com/ontB");
        ontBDocumentIriImportsDeclaration = new OWLImportsDeclarationImpl(ontBDocIri);
    }

    /**
     * Retrieves the imports closure of ontA, where ontA imports ontB via its
     * documentIRI. The document IRI is set BEFORE adding the imports
     * declaration.
     */
    @Test
    public void shouldRetrieveImportsClosureByDocumentIri() {
        // Update the document IRI for ontB BEFORE we add the import
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        // OntA imports OntB by a document IRI rather than its ontology IRI
        m.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        assertTrue(ontA.getImportsClosure().contains(ontA));
        assertTrue(ontA.getImportsClosure().contains(ontB));
    }

    /**
     * Retrieves the imports closure of ontA, where ontA imports ontB via its
     * documentIRI. The document IRI is set AFTER adding the imports
     * declaration.
     */
    @Test
    public void shouldRetrieveImportsClosureByDocumentIriAfterDocumentIriChange() {
        // OntA imports OntB by a document IRI rather than its ontology IRI
        m.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        // Update the document IRI for ontB (AFTER we haved added the import)
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        assertTrue(ontA.getImportsClosure().contains(ontA));
        assertTrue(ontA.getImportsClosure().contains(ontB));
    }

    @Test
    public void testImportsWhenRemovingAndReloading() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        AutoIRIMapper mapper = new AutoIRIMapper(new File(RESOURCES, "imports"), true);
        man.getIRIMappers().add(mapper);
        String name = "/imports/thesubont.omn";
        OWLOntology root = man.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(name));
        assertEquals(1, root.getImports().size());
        for (OWLOntology ontology : man.getOntologies()) {
            man.removeOntology(ontology);
        }
        assertEquals(0, man.getOntologies().size());
        root = man.loadOntologyFromOntologyDocument(getClass().getResourceAsStream(name));
        assertEquals(1, root.getImports().size());
    }
}
