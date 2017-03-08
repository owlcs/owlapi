package org.semanticweb.owlapi.api.test.imports;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.owlapi.OWLImportsDeclarationImpl;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10 Jul 16
 */
@SuppressWarnings({"javadoc", "null"})
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
     * Retrieves the imports closure of ontA, where ontA imports ontB via its documentIRI. The
     * document IRI is set BEFORE adding the imports declaration.
     */
    @Test
    public void shouldRetrieveImportsClosureByDocumentIri() {
        // Update the document IRI for ontB BEFORE we add the import
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        // OntA imports OntB by a document IRI rather than its ontology IRI
        m.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
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
        m.applyChange(new AddImport(ontA, ontBDocumentIriImportsDeclaration));
        // Update the document IRI for ontB (AFTER we haved added the import)
        m.setOntologyDocumentIRI(ontB, ontBDocIri);
        assertTrue(contains(ontA.importsClosure(), ontA));
        assertTrue(contains(ontA.importsClosure(), ontB));
    }
}
