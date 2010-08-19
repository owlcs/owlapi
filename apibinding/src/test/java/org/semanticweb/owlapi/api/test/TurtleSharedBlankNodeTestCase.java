package org.semanticweb.owlapi.api.test;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19-Aug-2010
 */
public class TurtleSharedBlankNodeTestCase extends AbstractFileRoundTrippingTestCase {

    @Override
    protected String getFileName() {
        return "annotatedpropertychain.ttl.rdf";
    }
}
