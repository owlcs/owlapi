package org.semanticweb.owlapi.api.test;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11/03/2011
 */
public class TestParser08TestCase extends AbstractFileRoundTrippingTestCase {

    @Override
    protected String getFileName() {
        return "TestParser08.rdf";
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        // Annotation assertion not on an entity - cannot do!
//        super.testManchesterOWLSyntax();
    }
}
