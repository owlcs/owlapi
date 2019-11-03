package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.D;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi6.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.DLSyntaxHTMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;

public class DLSyntaxTestCase extends TestBase {

    @Test
    public void testCommasOnDisjointThree() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwo() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFour() {
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }

    @Test
    public void testCommasOnDisjointThreeOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(df.getIRI("urn:test:onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjointInHTML, render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    public void testCommasOnDisjointTwoOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(df.getIRI("urn:test:onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjointTwoInHTML, render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    public void testCommasOnDisjointFourOntologyHTML() throws Exception {
        OWLOntology o = m.createOntology(df.getIRI("urn:test:onto"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxHTMLDocumentFormat()).toString();
        assertEquals(TestFiles.disjoint4InHTML, render.replace(System.getProperty("line.separator"), "\n"));
    }

    @Test
    public void testCommasOnDisjointThreeOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwoOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFourOntology() throws Exception {
        OWLOntology o = m.createOntology();
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(A, B, C, D);
        o.addAxiom(ax);
        String render = saveOntology(o, new DLSyntaxDocumentFormat()).toString();
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D", render);
    }
}
