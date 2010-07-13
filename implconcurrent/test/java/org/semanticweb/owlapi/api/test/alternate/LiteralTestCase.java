package org.semanticweb.owlapi.api.test.alternate;


import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 07-Jul-2010
 */
public class LiteralTestCase extends AbstractAxiomsRoundTrippingTestCase {


    @Override
    protected Set<? extends OWLAxiom> createAxioms() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        OWLClass cls = getOWLClass("A");
        OWLAnnotationProperty prop = getOWLAnnotationProperty("prop");
        OWLAnnotationAssertionAxiom ax = getFactory().getOWLAnnotationAssertionAxiom(prop, cls.getIRI(), literalWithLang);
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        axioms.add(ax);
        return axioms;
    }

    public void testHasLangMethod() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertTrue(literalWithLang.hasLang());
        OWLLiteral literalWithoutLang = getFactory().getOWLLiteral("abc", "");
        assertFalse(literalWithoutLang.hasLang());
    }

    public void testGetLangMethod() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertEquals(literalWithLang.getLang(), "en");
        OWLLiteral literalWithoutLang = getFactory().getOWLLiteral("abc", "");
        assertEquals(literalWithoutLang.getLang(), "");
    }

    public void testNormalisation() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "EN");
        assertEquals(literalWithLang.getLang(), "en");
        assertTrue(literalWithLang.hasLang("EN"));
    }

    public void testPlainLiteralWithLang() {
        OWLLiteral literalWithLang = getFactory().getOWLLiteral("abc", "en");
        assertTrue(literalWithLang.getDatatype().getIRI().isPlainLiteral());
        assertTrue(literalWithLang.isRDFPlainLiteral());
    }

    public void testPlainLiteralWithEmbeddedLang() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@en", getFactory().getRDFPlainLiteral());
        assertTrue(literal.hasLang());
        assertEquals(literal.getLang(), "en");
        assertEquals(literal.getLiteral(), "abc");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }

    public void tesPlainLiteralWithEmbeddedEmptyLang() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@", getFactory().getRDFPlainLiteral());
        assertTrue(!literal.hasLang());
        assertEquals(literal.getLang(), "");
        assertEquals(literal.getLiteral(), "abc");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }

    public void tesPlainLiteralWithDoubleSep() {
        OWLLiteral literal = getFactory().getOWLLiteral("abc@@en", getFactory().getRDFPlainLiteral());
        assertEquals(literal.getLang(), "en");
        assertEquals(literal.getLiteral(), "abc@");
        assertEquals(literal.getDatatype(), getFactory().getRDFPlainLiteral());
    }



    public void testBoolean() {
        OWLLiteral literal = getFactory().getOWLLiteral(true);
        assertTrue(literal.isBoolean());
        assertTrue(literal.parseBoolean());

        OWLLiteral trueLiteral = getFactory().getOWLLiteral("true", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(trueLiteral.isBoolean());
        assertTrue(trueLiteral.parseBoolean());


        OWLLiteral falseLiteral = getFactory().getOWLLiteral("false", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(falseLiteral.isBoolean());
        assertTrue(!falseLiteral.parseBoolean());

        OWLLiteral oneLiteral = getFactory().getOWLLiteral("1", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(oneLiteral.isBoolean());
        assertTrue(oneLiteral.parseBoolean());

        OWLLiteral zeroLiteral = getFactory().getOWLLiteral("0", OWL2Datatype.XSD_BOOLEAN);
        assertTrue(zeroLiteral.isBoolean());
        assertTrue(!zeroLiteral.parseBoolean());
    }

}
