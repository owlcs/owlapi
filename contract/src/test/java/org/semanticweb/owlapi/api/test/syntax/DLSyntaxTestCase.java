package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;

@SuppressWarnings("javadoc")
public class DLSyntaxTestCase extends TestBase {

    @Test
    public void testCommasOnDisjointThree() {
        OWLClass a = df.getOWLClass("urn:A");
        OWLClass b = df.getOWLClass("urn:B");
        OWLClass c = df.getOWLClass("urn:C");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwo() {
        OWLClass a = df.getOWLClass("urn:A");
        OWLClass b = df.getOWLClass("urn:B");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFour() {
        OWLClass a = df.getOWLClass("urn:A");
        OWLClass b = df.getOWLClass("urn:B");
        OWLClass c = df.getOWLClass("urn:C");
        OWLClass d = df.getOWLClass("urn:D");
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c, d);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D",
                render);
    }
}
