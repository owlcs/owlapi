package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 */
public class BuiltInDatatypesTestCase extends TestCase {

    public void testBuiltInDatatypes() {
        try {
            OWL2Datatype dt = OWL2Datatype.getDatatype(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
            assertNotNull(dt);
        }
        catch (RuntimeException e) {
            fail(e.getMessage());
        }
    }
}
