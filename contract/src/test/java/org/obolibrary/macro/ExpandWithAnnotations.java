package org.obolibrary.macro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

public class ExpandWithAnnotations extends OboFormatTestBasics {

    @Test
    public void testExpand() throws Exception {
        OWLOntology ontology = convert(parseOBOFile("annotated_no_overlap.obo"));
        OWLDataFactory df = ontology.getOWLOntologyManager()
                .getOWLDataFactory();
        MacroExpansionVisitor mev = new MacroExpansionVisitor(ontology);
        OWLOntology gciOntology = mev.expandAll();
        for (OWLDisjointClassesAxiom disjointClassesAxiom : gciOntology
                .getAxioms(AxiomType.DISJOINT_CLASSES)) {
            assertEquals("annotation count", 2, disjointClassesAxiom
                    .getAnnotations().size());
        }
    }
}
