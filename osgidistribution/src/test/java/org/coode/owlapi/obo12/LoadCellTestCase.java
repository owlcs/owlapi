package org.coode.owlapi.obo12;

import static org.junit.Assert.assertEquals;

import org.coode.owlapi.obo12.parser.OBO12DocumentFormat;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class LoadCellTestCase {

    @Test
    public void shouldParse() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        assertEquals(20, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/celltype.obo"),
                "obo", new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    public void shouldParseOBO12() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        assertEquals(20, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"),
                "obo", new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    public void shouldParseGenericOBO() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        assertEquals(20, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"),
                IRI.generateDocumentIRI(), new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }
}
