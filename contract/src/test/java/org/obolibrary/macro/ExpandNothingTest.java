package org.obolibrary.macro;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class ExpandNothingTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OWLOntology ontology = convert(parseOBOFile("nothing_expansion_test.obo"));
        MacroExpansionGCIVisitor mev = new MacroExpansionGCIVisitor(
                OWLManager.createOWLOntologyManager(), ontology, false);
        OWLOntology gciOntology = mev.createGCIOntology();
        int axiomCount = gciOntology.getAxiomCount();
        assertTrue(axiomCount > 0);
        assertEquals(2, gciOntology.axioms().count());
    }
}
