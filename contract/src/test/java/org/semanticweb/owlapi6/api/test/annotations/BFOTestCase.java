package org.semanticweb.owlapi6.api.test.annotations;

import org.junit.Test;
import org.semanticweb.owlapi6.api.test.TestFiles;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParserFactory;

public class BFOTestCase extends TestBase {

    @Test
    public void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadOntologyFromString(TestFiles.BFO, new RDFXMLDocumentFormat());
    }
}
