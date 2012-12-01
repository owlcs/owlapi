package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;

public class StrangeLiteralsTestCase {
    @Test
    public void testFloatZeros() throws Exception {
        // +0 and -0 are not equal
        OWLDataFactory df = Factory.getFactory();
        OWLDatatype type = df.getFloatOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("0.0", type);
        OWLLiteral lit2 = df.getOWLLiteral("-0.0", type);
        assertFalse(lit1.equals(lit2));
    }

    @Test
    public void testIntegerRange2_4() throws Exception {
        OWLDataFactory df = Factory.getFactory();
        OWLDatatype type = df.getIntegerOWLDatatype();
        df.getOWLLiteral("2147483648", type);
    }

    @Test
    public void testEnumInt_5() {
        OWLDataFactory df = Factory.getFactory();
        OWLDatatype type = df.getIntegerOWLDatatype();
        df.getOWLLiteral("1000000000000000000000000000000000000000", type);
    }

    @Test
    public void testGetDataPropertyValues() {
        OWLDataFactory df = Factory.getFactory();
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("01", type);
        OWLLiteral lit2 = df.getOWLLiteral("1", type);
        assertFalse(lit1.equals(lit2));
    }
}
