package org.semanticweb.owlapi6.apitest.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.io.File;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFilenames;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.utility.AutoIRIMapper;

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
        AutoIRIMapper mapper = new AutoIRIMapper(rootDirectory, true, df);
        man.getIRIMappers().add(mapper);
        File subont = new File(rootDirectory, TestFilenames.THESUBONT_OMN);
        OWLOntology root = loadFrom(subont, man);
        assertEquals(1, root.imports().count());
        for (OWLOntology ontology : man.ontologies().collect(Collectors.toList())) {
            man.removeOntology(ontology);
        }
        assertEquals(0, man.ontologies().count());
        root = loadFrom(subont, man);
        assertEquals(1, root.imports().count());
    }
}
