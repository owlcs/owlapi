package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

class DLSyntaxTestCase extends TestBase {

    @Test
    void testAxiomRender() {
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", visitor.render(DisjointClasses(A, B, C)));
        assertEquals("A ⊑ ¬ B", visitor.render(DisjointClasses(A, B)));
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D",
            visitor.render(DisjointClasses(A, B, C, D)));
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
        assertionsForHTML(DisjointClasses(A, B, C), TestFiles.disjointInHTML);
    }

    @Test
    void testCommasOnDisjointTwoOntologyHTML() {
        assertionsForHTML(DisjointClasses(A, B), TestFiles.disjointTwoInHTML);
    }

    @Test
    void testCommasOnDisjointFourOntologyHTML() {
        assertionsForHTML(DisjointClasses(A, B, C, D), TestFiles.disjoint4InHTML);
    }

    @Test
    void testCommasOnDisjointThreeOntology() {
        assertionsForDL(DisjointClasses(A, B, C), "A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C");
    }


    @Test
    void testCommasOnDisjointTwoOntology() {
        assertionsForDL(DisjointClasses(A, B), "A ⊑ ¬ B");
    }

    @Test
    void testCommasOnDisjointFourOntology() {
        assertionsForDL(DisjointClasses(A, B, C, D),
            "A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D");
    }
}
