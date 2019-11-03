package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.apitest.TestEntities.Q;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.LatexDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;

public class ToSyntaxTestCase extends TestBase {

    OWLClassExpression c = df.getOWLObjectIntersectionOf(df.getOWLObjectAllValuesFrom(P, A),
        df.getOWLObjectSomeValuesFrom(Q, B));

    @Test
    public void shouldFormatToFunctional() {
        assertEquals(
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<http://www.semanticweb.org/owlapi/test#q> <http://www.semanticweb.org/owlapi/test#B>) ObjectAllValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#A>))",
            c.toFunctionalSyntax());
    }

    @Test
    public void shouldFormatToDL() {
        assertEquals("(∃ q.B) ⊓ (∀ p.A)", c.toSyntax(new DLSyntaxDocumentFormat()));
    }

    @Test
    public void shouldFormatToManchester() {
        assertEquals(
            "(<http://www.semanticweb.org/owlapi/test#q> some <http://www.semanticweb.org/owlapi/test#B>)\n and (<http://www.semanticweb.org/owlapi/test#p> only <http://www.semanticweb.org/owlapi/test#A>)",
            c.toManchesterSyntax());
        PrefixManager pm = new PrefixManagerImpl().withDefaultPrefix("http://www.semanticweb.org/owlapi/test#");
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
            "ObjectIntersectionOf(ObjectSomeValuesFrom(<http://www.semanticweb.org/owlapi/test#q> <http://www.semanticweb.org/owlapi/test#B>) ObjectAllValuesFrom(<http://www.semanticweb.org/owlapi/test#p> <http://www.semanticweb.org/owlapi/test#A>))",
            c.toString());
    }
}
