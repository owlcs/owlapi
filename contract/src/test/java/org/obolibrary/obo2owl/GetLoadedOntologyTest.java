package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings({"javadoc",})
public class GetLoadedOntologyTest extends TestBase {

    @Test
    public void testConvert() throws Exception {
        String input =
            "Prefix(:=<http://www.example.org/#>)\nOntology(<http://example.org/>\nSubClassOf(:a :b) )";
        StringDocumentSource source = new StringDocumentSource(input);
        OWLOntology origOnt = m.loadOntologyFromOntologyDocument(source);
        assertNotNull(origOnt);
        assertEquals(1, m.getOntologies().size());
        assertFalse(origOnt.getOntologyID().getVersionIRI().isPresent());
        assertTrue(origOnt.getAxiomCount() > 0);
        assertTrue(origOnt.getOntologyID().getOntologyIRI().isPresent());
        OWLOntology newOnt = m.getOntology(origOnt.getOntologyID().getOntologyIRI().get());
        assertNotNull(newOnt);
    }
}
