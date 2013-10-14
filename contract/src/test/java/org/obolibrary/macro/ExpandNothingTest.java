package org.obolibrary.macro;

import static junit.framework.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

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
		for (OWLAxiom axiom : axioms) {
			System.out.println(axiom);
			// TODO - do actual tests
		}
	}
}
