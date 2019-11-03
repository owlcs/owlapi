package org.semanticweb.owlapi6.apitest.imports;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.AddImport;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLOntology;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10 Jul 16
 */
public class ImportsCacheTestCase extends TestBase {

    private OWLOntology ontA;
    private OWLOntology ontB;
    private IRI ontBDocIri;
    private OWLImportsDeclaration ontBDocumentIriImportsDeclaration;

    @Before
    public void setUpOntologies() throws Exception {
        ontA = m.createOntology(df.getIRI("http://ont.com/ontA"));
        ontB = m.createOntology(df.getIRI("http://ont.com/ontB"));
        ontBDocIri = df.getIRI("http://docs.ont.com/ontB");
        ontBDocumentIriImportsDeclaration = df.getOWLImportsDeclaration(ontBDocIri);
    }

    /**
     * Retrieves the imports closure of ontA, where ontA imports ontB via its documentIRI. The
     * document IRI is set BEFORE adding the imports declaration.
     */
    @Test
    public void shouldRetrieveImportsClosureByDocumentIri() {
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
    public void shouldRetrieveImportsClosureByDocumentIriAfterDocumentIriChange() {
        // OntA imports OntB by a document IRI rather than its ontology IRI
        ontA.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        // Update the document IRI for ontB (AFTER we haved added the import)
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        assertTrue(contains(ontA.importsClosure(), ontA));
        assertTrue(contains(ontA.importsClosure(), ontB));
    }
}
