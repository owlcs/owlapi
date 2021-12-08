package org.semanticweb.owlapi.api.test.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10 Jul 16
 */
class ImportsCacheTestCase extends TestBase {

    private OWLOntology ontA;
    private OWLOntology ontB;
    private IRI ontBDocIri;
    private OWLImportsDeclaration ontBDocumentIriImportsDeclaration;

    @BeforeEach
    void setUpOntologies() {
        ontA = create(iri("http://ont.com/", "ontA"));
        ontB = create(iri("http://ont.com/", "ontB"));
        ontBDocIri = iri("http://docs.ont.com/", "ontB");
        ontBDocumentIriImportsDeclaration = ImportsDeclaration(ontBDocIri);
    }

    /**
     * Retrieves the imports closure of ontA, where ontA imports ontB via its documentIRI. The
     * document IRI is set BEFORE adding the imports declaration.
     */
    @Test
    void shouldRetrieveImportsClosureByDocumentIri() {
        // Update the document IRI for ontB BEFORE we add the import
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        // OntA imports OntB by a document IRI rather than its ontology IRI
        ontA.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        assertTrue(contains(ontA.importsClosure(), ontA));
        assertTrue(contains(ontA.importsClosure(), ontB));
    }

    /**
     * Retrieves the imports closure of ontA, where ontA imports ontB via its documentIRI. The
     * document IRI is set AFTER adding the imports declaration.
     */
    @Test
    void shouldRetrieveImportsClosureByDocumentIriAfterDocumentIriChange() {
        // OntA imports OntB by a document IRI rather than its ontology IRI
        ontA.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        // Update the document IRI for ontB (AFTER we haved added the import)
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        assertTrue(contains(ontA.importsClosure(), ontA));
        assertTrue(contains(ontA.importsClosure(), ontB));
    }

    @Test
    void testImportsWhenRemovingAndReloading() {
        OWLOntologyManager man = setupManager();
        File rootDirectory = new File(RESOURCES, "imports");
        AutoIRIMapper mapper = new AutoIRIMapper(rootDirectory, true);
        man.getIRIMappers().add(mapper);
        File subont = new File(rootDirectory, TestFilenames.THESUBONT_OMN);
        OWLOntology root = loadFrom(subont, man);
        assertEquals(1, root.getImports().size());
        for (OWLOntology ontology : man.getOntologies()) {
            man.removeOntology(ontology);
        }
        assertEquals(0, man.getOntologies().size());
        root = loadFrom(subont, man);
        assertEquals(1, root.getImports().size());
    }
}
