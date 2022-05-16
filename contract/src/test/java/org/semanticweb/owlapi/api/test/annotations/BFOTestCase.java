package org.semanticweb.owlapi.api.test.annotations;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.TestFiles;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;

public class BFOTestCase extends TestBase {

    @Test
    public void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadOntologyFromString(TestFiles.BFO, new RDFXMLDocumentFormat());
    }
}
