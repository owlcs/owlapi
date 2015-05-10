package org.semanticweb.owlapi.api.test.annotations;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParserFactory;

public class BFOTestCase extends TestBase {

    @Test
    public void shouldparseBFO() {
        m.getOntologyParsers().set(new RDFXMLParserFactory());
        loadOntology("bforeduced.owl");
    }
}
