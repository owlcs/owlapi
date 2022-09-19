package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

class ParserBanningTestCase extends TestBase {

    @Test
    void shouldFailWithBanningOfTriX() {
        // This ontology is malformed RDF/XML but does not fail under a regular
        // parsing because the
        // TriX parser does not throw an exception reading it (although it does
        // not recognise any axioms)
        // This test ensures that TriX can be banned from parsing
        OWLOntologyManager manager = setupManager();
        // org.semanticweb.owlapi.rio.RioBinaryRdfParserFactory
        // org.semanticweb.owlapi.rio.RioJsonLDParserFactory
        // org.semanticweb.owlapi.rio.RioJsonParserFactory
        // org.semanticweb.owlapi.rio.RioN3ParserFactory
        // org.semanticweb.owlapi.rio.RioNQuadsParserFactory
        // org.semanticweb.owlapi.rio.RioNTriplesParserFactory
        // org.semanticweb.owlapi.rio.RioRDFaParserFactory
        // org.semanticweb.owlapi.rio.RioRDFXMLParserFactory
        // org.semanticweb.owlapi.rio.RioTrigParserFactory
        // org.semanticweb.owlapi.rio.RioTrixParserFactory
        // org.semanticweb.owlapi.rio.RioTurtleParserFactory
        String name = "org.semanticweb.owlapi.rio.RioTrixParserFactory";
        manager.getOntologyConfigurator().withBannedParsers(name);
        assertThrows(OWLOntologyCreationException.class,
            () -> manager.loadOntologyFromOntologyDocument(new StringDocumentSource(
                TestFiles.failWhenTrixBanned, new RDFXMLDocumentFormat())));
    }
}
