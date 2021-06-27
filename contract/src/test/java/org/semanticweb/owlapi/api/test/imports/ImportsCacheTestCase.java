package org.semanticweb.owlapi.api.test.imports;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10 Jul 16
 */
class ImportsCacheTestCase extends TestBase {

    private OWLOntology ontA;
    private OWLOntology ontB;
    private IRI ontBDocIri;
    private OWLImportsDeclaration ontBDocumentIriImportsDeclaration;

    @BeforeEach
    void setUpOntologies() throws Exception {
        ontA = m.createOntology(iri("http://ont.com/", "ontA"));
        ontB = m.createOntology(iri("http://ont.com/", "ontB"));
        ontBDocIri = iri("http://docs.ont.com/ontB");
        ontBDocumentIriImportsDeclaration = df.getOWLImportsDeclaration(ontBDocIri);
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
}
