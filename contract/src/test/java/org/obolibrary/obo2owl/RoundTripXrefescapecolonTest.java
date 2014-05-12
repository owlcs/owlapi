package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class RoundTripXrefescapecolonTest extends RoundTripTest {

    @Nonnull
    String file = "xref_escapecolon.obo";

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile(file, true);
    }

    @Test
    public void shouldContainExpectedAnnotation() {
        OBODoc oboFile = parseOBOFile(file);
        OWLOntology o = convert(oboFile);
        IRI expected = IRI
                .create("http://purl.obolibrary.org/obo/GO_0042062%3A");
        assertEquals(18, o.getAnnotationAssertionAxioms(expected).size());
    }
}
