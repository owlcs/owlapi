package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;

public class OWLLiteralCorruptionTest extends TestCase{


    @Test
    public void testmain() throws Exception{
    	String TEST_STRING;
        StringBuilder sb = new StringBuilder();
        int count = 17;
        while (count-- > 0) {
            sb.append("200 \u00B5Liters + character above U+0FFFF = ");
            sb.appendCodePoint(0x10192);  // happens to be "ROMAN SEMUNCIA SIGN"
            sb.append("\n");
        }
        TEST_STRING = sb.toString();

        OWLDataFactory factory = OWLManager.getOWLDataFactory();
        OWLLiteral literal = factory.getOWLLiteral(TEST_STRING);

        assertEquals("Out = in ? false", literal.getLiteral(),TEST_STRING);
    }
}
