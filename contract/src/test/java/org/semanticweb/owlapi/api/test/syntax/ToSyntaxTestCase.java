package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.LatexDocumentFormat;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.utilities.PrefixManagerImpl;

@SuppressWarnings("javadoc")
public class ToSyntaxTestCase extends TestBase {
    String namespace = "urn:test:";
    OWLObjectProperty p = df.getOWLObjectProperty("urn:test:", "p");
    OWLObjectProperty q = df.getOWLObjectProperty("urn:test:", "q");
    OWLClass a = df.getOWLClass(namespace, "A");
    OWLClass b = df.getOWLClass(namespace, "B");
    OWLClassExpression c = df.getOWLObjectIntersectionOf(df.getOWLObjectAllValuesFrom(p, a),
        df.getOWLObjectSomeValuesFrom(q, b));

    @Test
    public void shouldFormatToFunctional() {
        assertEquals(
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<urn:test:q> <urn:test:B>) ObjectAllValuesFrom(<urn:test:p> <urn:test:A>))",
            c.toFunctionalSyntax());
    }

    @Test
    public void shouldFormatToDL() {
        assertEquals("(∃ q.B) ⊓ (∀ p.A)", c.toSyntax(new DLSyntaxDocumentFormat()));
    }

    @Test
    public void shouldFormatToManchester() {
        assertEquals("(<urn:test:q> some <urn:test:B>)\n and (<urn:test:p> only <urn:test:A>)",
            c.toManchesterSyntax());
        PrefixManager pm = new PrefixManagerImpl().withDefaultPrefix(namespace);
        assertEquals("(:q some :B)\n and (:p only :A)", c.toManchesterSyntax(pm));
    }

    @Test
    public void shouldFormatToLatex() {
        assertEquals("\\ensuremath{\\exists}q.B~\\ensuremath{\\sqcap}~\\ensuremath{\\forall}p.A",
            c.toSyntax(new LatexDocumentFormat()));
    }

    @Test
    public void shouldFormatToSimple() {
        assertEquals(
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<urn:test:q> <urn:test:B>) ObjectAllValuesFrom(<urn:test:p> <urn:test:A>))",
            c.toString());
    }
}
