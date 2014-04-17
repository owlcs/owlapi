package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class RoundTripVersionIRITest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("version_iri_test.obo", true);
    }

    @Test
    public void testConvert() throws Exception {
        OWLOntology owlOnt = convertOBOFile("version_iri_test.obo");
        assertNotNull(owlOnt);
        IRI v = owlOnt.getOntologyID().getVersionIRI().get();
        assertEquals("http://purl.obolibrary.org/obo/go/2012-01-01/go.owl",
                v.toString());
    }
}
