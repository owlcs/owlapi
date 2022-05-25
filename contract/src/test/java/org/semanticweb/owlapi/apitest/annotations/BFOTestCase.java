package org.semanticweb.owlapi.apitest.annotations;

import org.junit.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;

public class BFOTestCase extends TestBase {

    @Test
    public void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadOntologyFromString(TestFiles.BFO, new RDFXMLDocumentFormat());
    }
}
