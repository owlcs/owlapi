package org.coode.owlapi.obo12;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.coode.owlapi.obo12.parser.OBO12DocumentFormat;
import org.coode.owlapi.obo12.parser.OBO12ParserFactory;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class LoadCellTestCase {

    @Test
    void shouldParse() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(21, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/celltype.obo"), "obo",
                new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    void shouldParseOBO12() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(21, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"), "obo",
                new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }

    @Test
    void shouldParseGenericOBO() throws OWLOntologyCreationException {
        OWLOntologyManager m = OWLManager.createOWLOntologyManager();
        m.getOntologyParsers().add(new OBO12ParserFactory());
        assertEquals(21, m.getOntologyParsers().size());
        OWLOntologyDocumentSource source =
            new StreamDocumentSource(getClass().getResourceAsStream("/behavior.obo"),
                IRI.generateDocumentIRI(), new OBO12DocumentFormat(), null);
        m.loadOntologyFromOntologyDocument(source);
    }
}
