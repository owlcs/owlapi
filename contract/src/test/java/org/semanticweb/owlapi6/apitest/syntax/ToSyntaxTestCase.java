package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.LatexDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;

class ToSyntaxTestCase extends TestBase {

    OWLClassExpression c = df.getOWLObjectIntersectionOf(df.getOWLObjectAllValuesFrom(P, A),
        df.getOWLObjectSomeValuesFrom(Q, B));

    @Test
    void shouldFormatToFunctional() {
        assertEquals(
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<http://www.semanticweb.org/owlapi/test#q> <http://www.semanticweb.org/owlapi/test#B>) ObjectAllValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#A>))",
            c.toFunctionalSyntax());
    }

    @Test
    void shouldFormatToDL() {
        assertEquals("(∃ q.B) ⊓ (∀ p.A)", c.toSyntax(new DLSyntaxDocumentFormat()));
    }

    @Test
    void shouldFormatToManchester() {
        assertEquals(
            "(<http://www.semanticweb.org/owlapi/test#q> some <http://www.semanticweb.org/owlapi/test#B>)\n and (<http://www.semanticweb.org/owlapi/test#p> only <http://www.semanticweb.org/owlapi/test#A>)",
            c.toManchesterSyntax());
        PrefixManager pm =
            new PrefixManagerImpl().withDefaultPrefix("http://www.semanticweb.org/owlapi/test#");
        assertEquals("(:q some :B)\n and (:p only :A)", c.toManchesterSyntax(pm));
    }

    @Test
    void shouldFormatToLatex() {
        assertEquals("\\ensuremath{\\exists}q.B~\\ensuremath{\\sqcap}~\\ensuremath{\\forall}p.A",
            c.toSyntax(new LatexDocumentFormat()));
    }

    @Test
    void shouldFormatToSimple() {
        assertEquals(
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<http://www.semanticweb.org/owlapi/test#q> <http://www.semanticweb.org/owlapi/test#B>) ObjectAllValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#A>))",
            c.toString());
    }
}
