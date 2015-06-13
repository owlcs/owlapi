package org.obolibrary.macro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class ExpandWithAnnotationsTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OWLOntology ontology = convert(parseOBOFile("annotated_no_overlap.obo"));
        MacroExpansionVisitor mev = new MacroExpansionVisitor(ontology, true, true);
        OWLOntology gciOntology = mev.expandAll();
        for (OWLDisjointClassesAxiom disjointClassesAxiom : gciOntology.getAxioms(AxiomType.DISJOINT_CLASSES)) {
            assertEquals("annotation count", 2, disjointClassesAxiom.getAnnotations().size());
        }
    }
}
