package org.semanticweb.owlapi6.apitest.annotations;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParserFactory;

class BFOTestCase extends TestBase {

    @Test
    void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadFrom(TestFiles.BFO, new RDFXMLDocumentFormat());
    }
}
