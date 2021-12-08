package org.semanticweb.owlapi.api.test.annotations;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;

class BFOTestCase extends TestBase {

    @Test
    void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadFrom(TestFiles.BFO, new RDFXMLDocumentFormat());
    }
}
