package org.obolibrary.macro;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class ExpandSynapsedToTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OWLOntology ontology = convert(parseOBOFile("synapsed_to.obo"));
        MacroExpansionGCIVisitor mev = new MacroExpansionGCIVisitor(m, ontology, false);
        OWLOntology gciOntology = mev.createGCIOntology();
        int axiomCount = gciOntology.getAxiomCount();
        assertTrue(axiomCount > 0);
        Set<OWLAxiom> axioms = gciOntology.getAxioms();
        assertEquals(4, axioms.size());
    }
}
