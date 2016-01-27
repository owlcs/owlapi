package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class RoundTripNamespaceIdRule extends RoundTripTest {

    @Test
    public void testWrite() throws Exception {
        OBODoc oboDoc = parseOBOFile("namespace-id-rule.obo");
        String oboString = renderOboToString(oboDoc);
        assertTrue(oboString.contains("\nnamespace-id-rule: * test:$sequence(7,0,9999999)$\n"));
    }

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("namespace-id-rule.obo", true);
    }

    @Test
    public void testWriteReadConvertedOWL() throws Exception {
        OBODoc oboDoc = parseOBOFile("namespace-id-rule.obo");
        OWLOntology owlOntology = convert(oboDoc);
        OWLOntologyManager manager = owlOntology.getOWLOntologyManager();
        StringDocumentTarget documentTarget = new StringDocumentTarget();
        manager.saveOntology(owlOntology, new OWLXMLDocumentFormat(), documentTarget);
        OWLOntology reloadedOwl = loadOntologyFromString(documentTarget);
        assertEquals(owlOntology.getAxiomCount(), reloadedOwl.getAxiomCount());
    }
}
