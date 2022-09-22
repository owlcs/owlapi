package org.semanticweb.owlapi.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

class DLSyntaxTestCase extends TestBase {

    @Test
    void testAxiomRender() {
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C",
            visitor.render(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C)));
        assertEquals("A ⊑ ¬ B", visitor.render(DisjointClasses(CLASSES.A, CLASSES.B)));
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D",
            visitor.render(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D)));
    }


    protected void assertionsForHTML(OWLDisjointClassesAxiom ax, String expected) {
        OWLOntology o = o(iri("urn:test:", "onto"), ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(expected, render.replace(System.getProperty("line.separator"), "\n"));
    }


    protected void assertionsForDL(OWLDisjointClassesAxiom ax, String expected) {
        String render = saveOntology(o(ax), new DLSyntaxDocumentFormat()).toString();
        assertEquals(expected, render);
    }

    @Test
    void testCommasOnDisjointThreeOntologyHTML() {
        assertionsForHTML(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C),
            TestFiles.disjointInHTML);
    }

    @Test
    void testCommasOnDisjointTwoOntologyHTML() {
        assertionsForHTML(DisjointClasses(CLASSES.A, CLASSES.B), TestFiles.disjointTwoInHTML);
    }

    @Test
    void testCommasOnDisjointFourOntologyHTML() {
        assertionsForHTML(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D),
            TestFiles.disjoint4InHTML);
    }

    @Test
    void testCommasOnDisjointThreeOntology() {
        assertionsForDL(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C),
            "A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C");
    }

    @Test
    void testCommasOnDisjointTwoOntology() {
        assertionsForDL(DisjointClasses(CLASSES.A, CLASSES.B), "A ⊑ ¬ B");
    }

    @Test
    void testCommasOnDisjointFourOntology() {
        assertionsForDL(DisjointClasses(CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D),
            "A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D");
    }
}
