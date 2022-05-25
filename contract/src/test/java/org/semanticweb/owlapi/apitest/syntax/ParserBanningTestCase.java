package org.semanticweb.owlapi.apitest.syntax;

import org.junit.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class ParserBanningTestCase extends TestBase {

    @Test(expected = OWLOntologyCreationException.class)
    public void shouldFailWithBanningOfTriX() throws OWLOntologyCreationException {
        // This ontology is malformed RDF/XML but does not fail under a regular
        // parsing because the
        // TriX parser does not throw an exception reading it (although it does
        // not recognise any axioms)
        // This test ensures that TriX can be banned from parsing
        OWLOntologyManager manager = setupManager();
        // org.semanticweb.owlapi.impl.rio.RioBinaryRdfParserFactory
        // org.semanticweb.owlapi.impl.rio.RioJsonLDParserFactory
        // org.semanticweb.owlapi.impl.rio.RioJsonParserFactory
        // org.semanticweb.owlapi.impl.rio.RioN3ParserFactory
        // org.semanticweb.owlapi.impl.rio.RioNQuadsParserFactory
        // org.semanticweb.owlapi.impl.rio.RioNTriplesParserFactory
        // org.semanticweb.owlapi.impl.rio.RioRDFaParserFactory
        // org.semanticweb.owlapi.impl.rio.RioRDFXMLParserFactory
        // org.semanticweb.owlapi.impl.rio.RioTrigParserFactory
        // org.semanticweb.owlapi.impl.rio.RioTrixParserFactory
        // org.semanticweb.owlapi.impl.rio.RioTurtleParserFactory
        String name = "org.semanticweb.owlapi.impl.rio.RioTrixParserFactory";
        manager.getOntologyConfigurator().withBannedParsers(name);
        manager.loadOntologyFromOntologyDocument(
            new StringDocumentSource(TestFiles.failWhenTrixBanned, new RDFXMLDocumentFormat()));
    }
}
