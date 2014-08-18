package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;


@SuppressWarnings("javadoc")
public class DLSyntaxTestCase {

    OWLDataFactory df = OWLManager.getOWLDataFactory();
    @Test
    public void testCommasOnDisjointThree() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, B ⊑ ¬ C", render);
    }

    @Test
    public void testCommasOnDisjointTwo() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B", render);
    }

    @Test
    public void testCommasOnDisjointFour() {
        OWLClass a = df.getOWLClass(IRI.create("urn:A"));
        OWLClass b = df.getOWLClass(IRI.create("urn:B"));
        OWLClass c = df.getOWLClass(IRI.create("urn:C"));
        OWLClass d = df.getOWLClass(IRI.create("urn:D"));
        OWLAxiom ax = df.getOWLDisjointClassesAxiom(a, b, c, d);
        DLSyntaxObjectRenderer visitor = new DLSyntaxObjectRenderer();
        String render = visitor.render(ax);
        assertEquals("A ⊑ ¬ B, A ⊑ ¬ C, A ⊑ ¬ D, B ⊑ ¬ C, B ⊑ ¬ D, C ⊑ ¬ D",
                render);
    }
}
