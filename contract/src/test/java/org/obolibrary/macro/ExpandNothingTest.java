package org.obolibrary.macro;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class ExpandNothingTest extends OboFormatTestBasics {

    @Test
    public void testExpand() throws Exception {
        OWLOntology ontology = convert(parseOBOFile("nothing_expansion_test.obo"));
        MacroExpansionGCIVisitor mev = new MacroExpansionGCIVisitor(ontology,
                OWLManager.createOWLOntologyManager());
        OWLOntology gciOntology = mev.createGCIOntology();
        int axiomCount = gciOntology.getAxiomCount();
        assertTrue(axiomCount > 0);
        Set<OWLAxiom> axioms = gciOntology.getAxioms();
        assertEquals(2, axioms.size());
    }
}
