package org.semanticweb.owlapi6.apitest.syntax;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyManager;

public class ParserBanningTestCase extends TestBase {

    @Test(expected = OWLOntologyCreationException.class)
    public void shouldFailWithBanningOfTriX() throws OWLOntologyCreationException {
        // This ontology is malformed RDF/XML but does not fail under a regular
        // parsing because the
        // TriX parser does not throw an exception reading it (although it does
        // not recognise any axioms)
        // This test ensures that TriX can be banned from parsing
        OWLOntologyManager manager = setupManager();
        // org.semanticweb.owlapi6.rio.RioBinaryRdfParserFactory
        // org.semanticweb.owlapi6.rio.RioJsonLDParserFactory
        // org.semanticweb.owlapi6.rio.RioJsonParserFactory
        // org.semanticweb.owlapi6.rio.RioN3ParserFactory
        // org.semanticweb.owlapi6.rio.RioNQuadsParserFactory
        // org.semanticweb.owlapi6.rio.RioNTriplesParserFactory
        // org.semanticweb.owlapi6.rio.RioRDFaParserFactory
        // org.semanticweb.owlapi6.rio.RioRDFXMLParserFactory
        // org.semanticweb.owlapi6.rio.RioTrigParserFactory
        // org.semanticweb.owlapi6.rio.RioTrixParserFactory
        // org.semanticweb.owlapi6.rio.RioTurtleParserFactory
        String name = "org.semanticweb.owlapi6.rio.RioTrixParserFactory";
        manager.getOntologyConfigurator().withBannedParsers(name);
        manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.failWhenTrixBanned, new RDFXMLDocumentFormat()));
    }
}
